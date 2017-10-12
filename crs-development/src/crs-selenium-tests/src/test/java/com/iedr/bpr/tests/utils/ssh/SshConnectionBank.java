package com.iedr.bpr.tests.utils.ssh;

import java.io.IOException;
import java.util.Properties;

import com.iedr.bpr.tests.TestConfig;
import com.jcraft.jsch.JSchException;

public class SshConnectionBank {
    public CrsSshConnection crsws;
    public CrsSshConnection crsweb;
    public CrsSshConnection scheduler;
    public CrsSshConnection console;
    private Properties config;

    public SshConnectionBank() throws JSchException, IOException {
        config = TestConfig.getConfig();
        crsws = getSshConnection("crsws");
        crsweb = getSshConnection("crsweb");
        scheduler = getSshConnection("crsscheduler");
        console = getSshConnection("console");
    }

    public void close() {
        crsws.close();
        crsweb.close();
        scheduler.close();
    }

    private CrsSshConnection getSshConnection(String hostName) throws JSchException, IOException {
        return new CrsSshConnection(config.getProperty(hostName + "host"), config.getProperty(hostName + "sshuser"),
                config.getProperty(hostName + "sshpassword"), Boolean.parseBoolean(config.getProperty(hostName
                        + "sshpasswordprompt")));
    }
}
