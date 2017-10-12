package pl.nask.crs.iedrapi;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.log4j.Logger;
import org.springframework.web.HttpRequestHandler;

import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.commons.utils.ServletUtils;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.iedrapi.exceptions.CommandFailed;
import pl.nask.crs.iedrapi.exceptions.CommandSyntaxErrorException;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.exceptions.Utf8ValidationException;
import pl.nask.crs.iedrapi.persistentcommands.PersistedCommandDAO;

import ie.domainregistry.ieapi_1.CommandType;
import ie.domainregistry.ieapi_1.ResponseType;

public class IedrApiRequestHandler implements HttpRequestHandler {
    private static final Logger log = Logger.getLogger(IedrApiRequestHandler.class);

    private final static String HTTP_CONTENT = "content";

    private final static List<String> RESTRICTED_KEYWORDS = Arrays.asList("pw", "account:cardHolderName",
            "account:cardNumber", "account:expiryDate", "account:cardType");

    private PersistedCommandDAO pcDAO;
    private CommandExecutor executor;

    private MyUnmarshaller unmarshaller;

    public IedrApiRequestHandler() throws JAXBException {
        unmarshaller = new MyUnmarshaller();
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        try {
            doHandle(request, response);
        } catch (Exception e) {
            log.error("Exception occurred while handling the request", e);
        } finally {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            log.info("Execution time is " + executionTime + "ms.");
            if (executionTime > 10000)
                log.warn("Long execution time " + executionTime + "ms.");
        }
    }

    private void doHandle(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String requestCmd = getRequestCmd(request);
        String loggableRequest = hideRestricted(requestCmd);
        HttpSession session = request.getSession(true);
        log.debug("Request: \n" + loggableRequest);
        String resultString;
        resultString = execute(requestCmd, session, ServletUtils.getRemoteIP(request));
        log.debug("Response: \n" + resultString);
        int contentLength = resultString.getBytes("UTF-8").length;
        response.setContentLength(contentLength);
        response.setContentType("application/xml; charset=UTF-8");
        response.getWriter().print(resultString);
        response.flushBuffer();
    }

    public String execute(String requestCmd, HttpSession session, String remoteAddr) {
        AuthData authData = AuthData.getInstance(session, remoteAddr);
        ResponseType result = ResponseTypeFactory.createResponse(2400);
        String normalizedCmd;

        try {
            normalizedCmd = getNormalizedAndValidatedCmd(requestCmd);
        } catch (Utf8ValidationException e) {
            return marshal(e.getResponseType());
        }
        String res = findResponse(authData, normalizedCmd);
        if (res != null) {
            log.info("Returning response found in Persisted Command storage");
            return res;
        }
        CommandType command = null;
        try {
            ValidationCallback callback = new ValidationCallback();
            command = unmarshaller.unmarshal(normalizedCmd, callback);
            // execute the command
            log.debug("executing...");
            result = executor.execute(authData, command, callback);
        } catch (CommandSyntaxErrorException e) {
            log.error("Unparseable command", e);
            result = e.getResponseType();
        } catch (IedrApiException e) {
            log.trace("Error executing command", e);
            result = e.getResponseType();
        } catch (Exception e) {
            log.warn("Error executing command", e);
            result = new CommandFailed(e).getResponseType();
        } finally {
            if (command != null)
                result.setTid(command.getTid());
            res = marshal(result);
            if (isStorable(command))
                storeResult(authData, normalizedCmd, res);
            return res;
        }
    }

    private String getRequestCmd(HttpServletRequest request) throws ServletException {
        // parse the request
        log.debug("parsing...");
        // get reader from multipart
        if (ServletFileUpload.isMultipartContent(request)) {
            return readFromMultipart(request);
        } else {
            return request.getParameter(HTTP_CONTENT);
        }
    }

    private String hideRestricted(String requestCmd) {
        String loggable = requestCmd;
        for (String keyword : RESTRICTED_KEYWORDS) {
            loggable = loggable.replaceAll("<" + keyword + ">.*?<\\/" + keyword + ">", "<" + keyword + ">######</"
                    + keyword + ">");
        }
        return loggable;
    }

    private String getNormalizedAndValidatedCmd(String requestCmd) throws Utf8ValidationException {
        try {
            return Validator.getNormalizedAndValidatedUtf8(requestCmd);
        } catch (IncorrectUtf8FormatException e) {
            throw new Utf8ValidationException();
        }
    }

    private String readFromMultipart(HttpServletRequest request) throws ServletException {
        try {
            ServletFileUpload svu = new ServletFileUpload();
            FileItemIterator iter;
            iter = svu.getItemIterator(request);
            while (iter.hasNext()) {
                FileItemStream item = iter.next();
                if (HTTP_CONTENT.equals(item.getFieldName())) {
                    InputStream is = item.openStream();
                    String content = Streams.asString(is, "UTF-8");
                    String loggableContent = hideRestricted(content);
                    log.info("ReqString: \n" + loggableContent);
                    return content;
                }
            }
        } catch (Exception e) {
            log.error("Error reading XML request", e);
            throw new ServletException(e);
        }
        return null;
    }

    private String marshal(ResponseType result) {
        MyMarshaller m;
        try {
            m = new MyMarshaller(result);
            return m.marshall();
        } catch (JAXBException e) {
            // should never happen in the runtime
            throw new RuntimeException(e);
        }
    }

    private String findResponse(AuthData authData, String requestCmd) {
        if (!authData.isUserLoggedIn())
            return null;
        try {
            return pcDAO.getResponse(authData.getUsername(), requestCmd);
        } catch (Exception e) {
            log.warn("Unable to get response from the PersistedCommands store", e);
            return null;
        }
    }

    private void storeResult(AuthData authData, String requestCmd, String response) {
        if (authData.isUserLoggedIn()) {
            try {
                pcDAO.storeResponse(authData.getUsername(), requestCmd, response);
            } catch (Exception e) {
                log.warn("Unable to store response in the PersistedCommands store", e);
            }
        }
    }

    private boolean isStorable(CommandType command) {
        if (command == null)
            return false;
        if (command.getTid() == null || !isTidValid(command.getTid()))
            return false;
        if (command.getLogin() != null || command.getLogout() != null)
            return false;
        return true;
    }

    private boolean isTidValid(String tid) {
        // TODO: make TID validation
        return true;
    }

    public void setExecutor(CommandExecutor executor) {
        this.executor = executor;
    }

    public void setPcDAO(PersistedCommandDAO pcDAO) {
        this.pcDAO = pcDAO;
    }

    public void setSchemaFilesList(List<String> schemaFileList) {
        this.unmarshaller.setSchemaFilesList(schemaFileList);
    }
}
