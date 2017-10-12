package com.iedr.bpr.tests.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Set;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

public class FileDownloader {

    private CookieStore cookieStore;

    public FileDownloader(WebDriver wd) {
        Set<Cookie> seleniumCookies = wd.manage().getCookies();
        cookieStore = getCookieStore(seleniumCookies);
    }

    public String downloadFile(String fileToDownloadLocation)
            throws IOException, NullPointerException, URISyntaxException, KeyManagementException,
            NoSuchAlgorithmException, KeyStoreException {
        URL fileToDownload = new URL(fileToDownloadLocation);
        return getFileContents(fileToDownload.toURI());
    }

    private CookieStore getCookieStore(Set<Cookie> seleniumCookies) {
        CookieStore store = new BasicCookieStore();
        for (Cookie seleniumCookie : seleniumCookies) {
            BasicClientCookie cookie = new BasicClientCookie(seleniumCookie.getName(), seleniumCookie.getValue());
            cookie.setDomain(seleniumCookie.getDomain());
            cookie.setExpiryDate(seleniumCookie.getExpiry());
            cookie.setPath(seleniumCookie.getPath());
            cookie.setSecure(seleniumCookie.isSecure());
            store.addCookie(cookie);
        }
        return store;
    }

    private String getFileContents(URI fileUri)
            throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, ClientProtocolException,
            IOException {
        HttpClientBuilder builder = HttpClients.custom().setDefaultCookieStore(cookieStore);
        if (fileUri.toString().startsWith("https://")) {
            SSLConnectionSocketFactory sslsf = getSslSocketFactory();
            builder.setSSLSocketFactory(sslsf);
        }
        CloseableHttpClient httpclient = builder.build();
        try {
            HttpGet httpget = new HttpGet(fileUri);

            ResponseHandler<String> responseHandler = getResponseHandler(fileUri);
            return httpclient.execute(httpget, responseHandler);
        } finally {
            httpclient.close();
        }
    }

    private SSLConnectionSocketFactory getSslSocketFactory()
            throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        // Trust all certificates
        SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
            public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                return true;
            }
        }).build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] {"TLSv1"}, null,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        return sslsf;
    }

    private ResponseHandler<String> getResponseHandler(final URI fileUri) {
        return new ResponseHandler<String>() {

            public String handleResponse(final HttpResponse response) throws IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new RuntimeException(String.format("Unexpected response status %s for url: %s", status,
                            fileUri));
                }
            }

        };
    }

}
