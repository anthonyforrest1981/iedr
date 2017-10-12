package com.iedr.bpr.tests.crsweb;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.pages.crsweb.ViewDomainPage;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.ssh;

public class UC047 extends SeleniumTest {

    private String initialPermissions;

    public UC047(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsweb/uc047_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsweb/uc047_data.sql";
    }

    @Test
    public void test_uc047_nosc01() {
        // UC#047: Perform DNS Check - UC#047: Perform DNS Check
        String domainName = "uc047-nosc01.ie";
        String message = "DNS Check compete: Passed";
        test_perform_dns_check(domainName, message);
    }

    @Test
    public void test_uc047_nosc02() throws IOException, JSchException {
        // UC#047: Perform DNS Check - UC#047: Perform DNS Check - an error is
        // reported
        String domainName = "uc047-nosc02.ie";
        String ckDnsLocation = config.getProperty("ckdnslocation");
        String message = String.format("Error performing DNS check: java.io.IOException: "
                + "Cannot run program \"%s\": " + "error=13, Permission denied", ckDnsLocation);
        initialPermissions = getCkDnsPermissions();
        setCkDnsPermissions("000");
        try {
            test_perform_dns_check(domainName, message);
        } finally {
            setCkDnsPermissions(initialPermissions);
        }
    }

    @Test
    public void test_uc047_nosc03() throws JSchException, IOException {
        // UC#047: Perform DNS Check - UC#047: Perform DNS Check for domain with
        // 7 nameservers - CRS-WEB
        String domainName = "uc047-nosc03.ie";
        String message = "DNS Check compete: Passed";
        test_perform_dns_check(domainName, message);
        String initialCkdnsScript = ssh().crsweb.spoofCkDnsScript(Arrays.asList(domainName));
        try {
            test_perform_dns_check(domainName, "DNS Check complete: failed");
        } finally {
            ssh().crsweb.restoreCkdnsScript(initialCkdnsScript);
        }
    }

    @Test
    public void test_uc047_nosc04() throws JSchException, IOException {
        // UC#047: Perform DNS Check - UC#047: Perform DNS Check - CRS-WEB - IPv6 support
        String domainName = "uc047-nosc04.ie";
        test_perform_dns_check(domainName, "DNS Check compete: Passed");
        Map<String, String> map = new HashMap<String, String>();
        map.put(domainName, "::ffff:10.10.1.1");
        String initialCkdnsScript = ssh().crsweb.spoofCkDnsScript(map);
        try {
            test_perform_dns_check(domainName, "DNS Check complete: failed");
        } finally {
            ssh().crsweb.restoreCkdnsScript(initialCkdnsScript);
        }
    }

    private void test_perform_dns_check(String domainName, String message) {
        crsweb().login(this.internal);
        ViewDomainPage vdp = new ViewDomainPage();
        vdp.performDnsCheck(domainName);
        crsweb().waitForTextPresentOnPage(message);
    }

    private String getCkDnsPermissions() throws IOException, JSchException {
        String ckDnsLocation = config.getProperty("ckdnslocation");
        return ssh().crsweb.execute(String.format("stat -c %%a %s", ckDnsLocation), false);
    }

    private void setCkDnsPermissions(String permissions) throws JSchException, IOException {
        String ckDnsLocation = config.getProperty("ckdnslocation");
        ssh().crsweb.execute(String.format("chmod %s %s", permissions, ckDnsLocation), true);
    }

}
