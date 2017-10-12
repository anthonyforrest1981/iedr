package pl.nask.crs.payment.service.impl;

import java.io.IOException;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.log4j.Logger;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.payment.exceptions.PaymentSenderException;
import pl.nask.crs.payment.service.PaymentSender;

public class PaymentSenderImpl implements PaymentSender {

    private static Logger log = Logger.getLogger(PaymentSenderImpl.class);
    private String strURL;
    private String proxyHost;
    private int proxyPort;

    public PaymentSenderImpl() {
        ProtocolSocketFactory factory = new Tls12SocketFactory();
        Protocol.registerProtocol("https", new Protocol("https", factory, 443));
    }

    public void setStrURL(String strURL) {
        this.strURL = strURL;
        log.info("Using URL: " + strURL);
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String send(String commandXML) throws PaymentSenderException {
        if (this.strURL == null) {
            throw new IllegalStateException("strURL not set: use setStrURL before sending any command");
        }
        HttpClient httpClient = new HttpClient();
        if (!Validator.isEmpty(proxyHost))
            setProxy(httpClient);
        httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT, "API System of IEDR");
        httpClient.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 30000);
        PostMethod post = new PostMethod(strURL);
        post.setRequestEntity(new StringRequestEntity(commandXML));
        String response = null;
        try {
            int statusCode = httpClient.executeMethod(post);
            if (statusCode == HttpStatus.SC_OK) {
                response = post.getResponseBodyAsString();
            } else {
                log.error("Method failed: " + post.getStatusLine());
            }
        } catch (IOException e) {
            log.error("Exception in PaymentSenderImpl", e);
            throw new PaymentSenderException("Error sending payment command to the remote system", e);
        } finally {
            post.releaseConnection();
        }
        return response;
    }

    private void setProxy(HttpClient httpClient) {
        HostConfiguration config = httpClient.getHostConfiguration();
        config.setProxy(proxyHost, proxyPort);
        log.info("Using proxy : " + proxyHost + ":" + proxyPort);
    }

}
