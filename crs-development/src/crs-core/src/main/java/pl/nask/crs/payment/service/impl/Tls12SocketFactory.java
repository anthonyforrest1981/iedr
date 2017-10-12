package pl.nask.crs.payment.service.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocket;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;
import org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory;

public class Tls12SocketFactory implements SecureProtocolSocketFactory {

    private static final String[] SUPPORTED_PROTOCOLS = new String[] {"TLSv1.2"};
    private static final String[] SUPPORTED_CIPHER_SUITES = new String[] {
            "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384",
            "TLS_RSA_WITH_AES_256_CBC_SHA",
            "TLS_RSA_WITH_AES_128_CBC_SHA"};

    private SSLProtocolSocketFactory factory;

    public Tls12SocketFactory() {
        factory = new SSLProtocolSocketFactory();
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localAddress, int localPort)
            throws IOException, UnknownHostException {
        return setupSocket(factory.createSocket(host, port, localAddress, localPort));
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localAddress, int localPort,
            HttpConnectionParams params) throws IOException, UnknownHostException, ConnectTimeoutException {
        return setupSocket(factory.createSocket(host, port, localAddress, localPort, params));
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        return setupSocket(factory.createSocket(host, port));
    }

    @Override
    public Socket createSocket(Socket origSocket, String host, int port, boolean autoClose)
            throws IOException, UnknownHostException {
        return setupSocket(factory.createSocket(origSocket, host, port, autoClose));
    }

    private Socket setupSocket(Socket socket) {
        SSLSocket sslsock = (SSLSocket) socket;
        sslsock.setEnabledProtocols(SUPPORTED_PROTOCOLS);
        sslsock.setEnabledCipherSuites(SUPPORTED_CIPHER_SUITES);
        return sslsock;
    }
}
