package com.iedr.bpr.tests.utils.ssh;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import com.jcraft.jsch.*;

public class SshConnection {

    private final static int TIMEOUT = 10000;
    private final static String passwordPrompt = "$>";

    Session session;
    Channel channel;

    String userName;
    String password;
    boolean expectPasswordPrompt;

    public SshConnection(String host, String userName, String password, boolean expectPasswordPrompt)
            throws JSchException {
        this.userName = userName;
        this.password = password;
        this.expectPasswordPrompt = expectPasswordPrompt;
        this.session = getSshSession(host);
    }

    public String execute(String command, boolean sudoRequired) throws JSchException, IOException {
        return executeUntrimmed(command, sudoRequired).trim();
    }

    public String executeUntrimmed(String command, boolean sudoRequired) throws JSchException, IOException {
        command = prepareCommand(command, sudoRequired);
        return executeCommand(command, sudoRequired);
    }

    public String echoToFile(String content, String file, boolean sudoRequired) throws IOException, JSchException {
        String command;
        if (sudoRequired) {
            command = String.format("echo -e '%s' | sudo -p '%s' tee %s", content, passwordPrompt, file);
        } else {
            command = String.format("echo -e '%s' > %s", content, file);
        }
        return executeCommand(command, sudoRequired);
    }

    public String moveFile(String srcPath, String dstPath, boolean sudoRequired) throws IOException, JSchException {
        return execute(String.format("mv %s %s", srcPath, dstPath), sudoRequired);
    }

    private Session getSshSession(String host) throws JSchException {
        JSch jsch = new JSch();
        int port = 22;
        if (host.contains(":")) {
            List<String> values = Arrays.asList(host.split(":"));
            host = values.get(0);
            port = Integer.valueOf(values.get(1));
        }
        Session session = jsch.getSession(userName, host, port);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.setPassword(password);
        session.connect();
        return session;
    }

    private String prepareCommand(String command, boolean sudoRequired) {
        if (sudoRequired) {
            command = escape(command);
            command = String.format("sudo -S -p '%s' %s", passwordPrompt, command);
        }
        return command;
    }

    private String executeCommand(String command, boolean usesSudo) throws IOException, JSchException {
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        channel = setUpChannel(command, err);
        InputStream channelIn = channel.getInputStream();
        OutputStream channelOut = channel.getOutputStream();
        channel.connect();
        if (usesSudo && expectPasswordPrompt) {
            waitForPrompt(channelIn);
            channelOut.write(String.format("%s\n", password).getBytes());
            channelOut.flush();
        }
        String response = readResponse(channelIn, err);
        channel.disconnect();
        return response;
    }

    private String escape(String command) {
        return command.replace("\"", "\\\"").replace("$", "\\$");
    }

    private Channel setUpChannel(String command, OutputStream err) throws JSchException {
        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(command);
        ((ChannelExec) channel).setPty(true);
        channel.setInputStream(null);
        ((ChannelExec) channel).setErrStream(err);
        return channel;
    }

    private void waitForPrompt(InputStream channelIn) throws IOException, JSchException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] tmp = new byte[1024];
        boolean readingStarted = false;
        long start = System.currentTimeMillis();
        while (true) {
            while (channelIn.available() > 0) {
                readingStarted = true;
                int i = channelIn.read(tmp, 0, 1024);
                if (i < 0)
                    break;
                if (output != null)
                    output.write(tmp, 0, i);
            }
            if (passwordPrompt.equals(output.toString())) {
                break;
            } else if (readingStarted) {
                throw new JSchException(output.toString());
            }
            try {
                Thread.sleep(100);
            } catch (Exception ee) {
            }
            if (System.currentTimeMillis() > start + TIMEOUT) {
                throw new JSchException("Timeout reached waiting for response");
            }
        }
    }

    private String readResponse(InputStream channelIn, OutputStream err) throws IOException, JSchException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] tmp = new byte[1024];
        long start = System.currentTimeMillis();
        while (true) {
            while (channelIn.available() > 0) {
                int i = channelIn.read(tmp, 0, 1024);
                if (i < 0)
                    break;
                if (output != null)
                    output.write(tmp, 0, i);
            }
            if (channel.isClosed()) {
                break;
            }
            try {
                Thread.sleep(100);
            } catch (Exception ee) {
            }
            if (System.currentTimeMillis() > start + TIMEOUT) {
                throw new JSchException("Timeout reached waiting for response");
            }
        }
        if (err.toString().length() > 0) {
            throw new JSchException(err.toString());
        } else if (channel.getExitStatus() > 0) {
            throw new JSchException(output.toString());
        }
        return output.toString();
    }

    public void close() {
        if (channel != null && channel.isConnected()) {
            channel.disconnect();
        }
        if (session.isConnected()) {
            session.disconnect();
        }
    }

}
