package com.iedr.bpr.tests.utils.ssh;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.*;

import org.apache.commons.io.IOUtils;

import com.iedr.bpr.tests.TestConfig;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.db;
import static org.junit.Assert.assertTrue;

public class CrsSshConnection extends SshConnection {

    Properties config;

    public CrsSshConnection(String host, String userName, String password, boolean expectPasswordPrompt)
            throws JSchException, IOException {
        super(host, userName, password, expectPasswordPrompt);
        config = TestConfig.getConfig();
    }

    public String spoofCkDnsScript(List<String> nonAuthoritativeDomains) throws JSchException, IOException {
        return spoofCkDnsScript(nonAuthoritativeDomains, new HashMap<String, String>());
    }

    public String spoofCkDnsScript(Map<String, String> nonAuthoritativeDomainsSpecificOrigin)
            throws JSchException, IOException {
        return spoofCkDnsScript(new ArrayList<String>(), nonAuthoritativeDomainsSpecificOrigin);
    }

    public String spoofCkDnsScript(List<String> nonAuthoritativeDomains,
            Map<String, String> nonAuthoritativeDomainsSpecificOrigin) throws JSchException, IOException {
        Map<String, String> nonAuthoritativeDomainsMap = getDomainsMap(nonAuthoritativeDomains,
                nonAuthoritativeDomainsSpecificOrigin);
        String ckDnsScript = getCkdnsScript(nonAuthoritativeDomainsMap);
        return spoofCkDnsScript(ckDnsScript);
    }

    public String spoofCkDnsScriptToSleep(int seconds) throws JSchException, IOException {
        String ckDnsScript = "sleep " + seconds + "\n";
        return spoofCkDnsScript(ckDnsScript);
    }

    private String spoofCkDnsScript(String newCkDnsScript) throws JSchException, IOException {
        String ckDnsLocation = config.getProperty("ckdnslocation");
        String initialCkdnsScript = getCurrentCkdnsScript();
        swapCkDnsScript(newCkDnsScript, ckDnsLocation);
        return initialCkdnsScript;
    }

    public String getCurrentCkdnsScript() throws JSchException, IOException {
        String ckDnsLocation = config.getProperty("ckdnslocation");
        String currentCkdnsScript = execute("cat " + ckDnsLocation, false);
        return currentCkdnsScript;
    }

    public void restoreCkdnsScript(String initialCkdnsScript) throws JSchException, IOException {
        String ckDnsLocation = config.getProperty("ckdnslocation");
        swapCkDnsScript(initialCkdnsScript, ckDnsLocation);
    }

    public String getLogTail(String logLocation, int linesCount) throws JSchException, IOException {
        String logTail = execute(String.format("tail -n %s %s", linesCount, logLocation), false);
        return logTail;
    }

    public String getConsoleLogTail(int linesCount) throws IOException, JSchException {
        String logLocation = config.getProperty("consoleapplicationloglocation");
        String logTail = getLogTail(logLocation, linesCount);
        int remainingLinesCount = linesCount - logTail.split("\n").length;
        if (remainingLinesCount > 0) {
            // It's possible that a log rotation happened recently. In that case we have to get the remaining lines from
            // the archived log.
            logLocation += ".1";
            String previousLogTail = getLogTail(logLocation, remainingLinesCount);
            logTail = String.format("%s\n%s", previousLogTail, logTail);
        }
        return logTail;
    }

    public String getTomcatLogTail(int linesCount) throws JSchException, IOException {
        String logLocation = config.getProperty("applicationloglocation");
        return getLogTail(logLocation, linesCount);
    }

    private Map<String, String> getDomainsMap(List<String> nonAuthoritativeDomains,
            Map<String, String> nonAuthoritativeDomainsSpecificOrigin) {
        Map<String, String> map = new HashMap<String, String>();
        map.putAll(nonAuthoritativeDomainsSpecificOrigin);
        for (String domain : nonAuthoritativeDomains) {
            map.put(domain, "*");
        }
        return map;
    }

    private void swapCkDnsScript(String ckDnsScript, String ckDnsLocation) throws JSchException, IOException {
        echoToFile(ckDnsScript, ckDnsLocation, true);
    }

    private String getCkdnsScript(Map<String, String> nonAuthoritativeDomainsMap) throws IOException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(CrsSshConnection.class.getResourceAsStream("/ckdns_template"), writer, Charset.defaultCharset());
        String template = writer.toString();
        String ckDnsScript = template
                .replace("$NON_AUTHORITATIVE_DOMAINS_MAP$", getPerlMap(nonAuthoritativeDomainsMap));
        return ckDnsScript.replace("\n", "\\n");
    }

    private String getPerlMap(Map<String, String> nonAuthoritativeDomainsMap) {
        String map = "";
        for (Map.Entry<String, String> e : nonAuthoritativeDomainsMap.entrySet()) {
            map += String.format("\"%s\", \"%s\",\n", e.getKey(), e.getValue());
        }
        return map.replaceAll(",\n$", "");
    }

    public String getDoaTransactionXmlContent(String doaTransactionXmlFilePath)
            throws JSchException, IOException, SQLException {
        return getXmlContent(doaTransactionXmlFilePath, db().getDoaXmlFileName());
    }

    public String getAccountXmlContent(String accountXmlFilePath) throws JSchException, IOException, SQLException {
        return getXmlContent(accountXmlFilePath, db().getAccountXmlFileName());
    }

    private String getXmlContent(String filePath, String fileName) throws JSchException, IOException {
        String fileContent = executeUntrimmed("cat " + filePath, false);
        assertTrue(fileContent.contains(fileName.replace(".xml", "")));
        return fileContent;
    }

}
