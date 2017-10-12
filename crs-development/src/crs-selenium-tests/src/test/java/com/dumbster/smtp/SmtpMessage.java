/*
 * Dumbster - a dummy SMTP server
 * Copyright 2004 Jason Paul Kitchen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dumbster.smtp;

import java.util.*;

/**
 * Container for a complete SMTP message - headers and message body.
 */
public class SmtpMessage {
    /** Headers: Map of List of String hashed on header name. */
    private Map headers;
    /** Message body. */
    private StringBuffer body;
    /** List of recipients. */
    private List<String> recipients;
    /**
     * The name of the last header encountered. Used to handle continued
     * headers.
     */
    private String lastHeaderName;

    /**
     * Constructor. Initializes headers Map and body buffer.
     */
    public SmtpMessage() {
        headers = new HashMap(10);
        body = new StringBuffer();
        recipients = new ArrayList<String>();
    }

    /**
     * Update the headers or body depending on the SmtpResponse object and line
     * of input.
     *
     * @param response
     *            SmtpResponse object
     * @param params
     *            remainder of input line after SMTP command has been removed
     */
    public void store(SmtpResponse response, String params) {
        if (params != null) {
            if (SmtpState.DATA_HDR.equals(response.getNextState())) {
                int headerNameEnd = params.indexOf(':');
                if (isContinuationLine(params)) {
                    continueHeader(params);
                } else if (headerNameEnd >= 0) {
                    String name = params.substring(0, headerNameEnd).trim();
                    String value = params.substring(headerNameEnd + 1).trim();
                    addHeader(name, value);
                }
            } else if (SmtpState.DATA_BODY == response.getNextState()) {
                body.append(params);
                if (!params.endsWith("\n")) {
                    body.append("\n");
                }
            } else if (SmtpState.RCPT == response.getNextState()) {
                recipients.add(params);
            }
        }
    }

    /**
     * Get an Iterator over the header names.
     *
     * @return an Iterator over the set of header names (String)
     */
    public Iterator getHeaderNames() {
        Set nameSet = headers.keySet();
        return nameSet.iterator();
    }

    /**
     * Get the value(s) associated with the given header name.
     *
     * @param name
     *            header name
     * @return value(s) associated with the header name
     */
    public String[] getHeaderValues(String name) {
        List values = (List) headers.get(name);
        if (values == null) {
            return new String[0];
        } else {
            return (String[]) values.toArray(new String[0]);
        }
    }

    /**
     * Get the first values associated with a given header name.
     *
     * @param name
     *            header name
     * @return first value associated with the header name
     */
    public String getHeaderValue(String name) {
        List values = (List) headers.get(name);
        if (values == null) {
            return null;
        } else {
            Iterator iterator = values.iterator();
            return (String) iterator.next();
        }
    }

    /**
     * Get the message body.
     *
     * @return message body
     */
    public String getBody() {
        return body.toString();
    }

    /**
     * Get the list of recipients.
     *
     * @return list of recipients
     */
    public List<String> getRecipients() {
        return new ArrayList<String>(recipients);
    }

    /**
     * Adds a header to the Map.
     *
     * @param name
     *            header name
     * @param value
     *            header value
     */
    private void addHeader(String name, String value) {
        List valueList = (List) headers.get(name);
        if (valueList == null) {
            valueList = new ArrayList(1);
            headers.put(name, valueList);
        }
        valueList.add(value);
        lastHeaderName = name;
    }

    /**
     * Indicates whether the given string contains a header continuation as
     * specified by RFC 822.
     *
     * @param candidate
     * @return true if it is a continuation line
     */
    private boolean isContinuationLine(String candidate) {
        return candidate.charAt(0) == 0x20 // ASCII SPACE
                || candidate.charAt(0) == 0x09; // ASCII HTAB
    }

    /**
     * Extends the last-added header to include the contents of the continuation
     * line
     *
     * @param continuation
     *            the continuation line
     */
    private void continueHeader(String continuation) {
        List valueList = (List) headers.get(lastHeaderName);
        if (valueList == null)
            return;
        int lastValueIndex = valueList.size() - 1;
        String lastValue = (String) valueList.get(lastValueIndex);
        valueList.set(lastValueIndex, lastValue + "\r\n" + continuation);
    }

    /**
     * String representation of the SmtpMessage.
     *
     * @return a String
     */
    public String toString() {
        StringBuffer msg = new StringBuffer();
        for (Iterator i = headers.keySet().iterator(); i.hasNext();) {
            String name = (String) i.next();
            List values = (List) headers.get(name);
            for (Iterator j = values.iterator(); j.hasNext();) {
                String value = (String) j.next();
                msg.append(name);
                msg.append(": ");
                msg.append(value);
                msg.append('\n');
            }
        }
        msg.append('\n');
        msg.append(body);
        msg.append('\n');
        return msg.toString();
    }
}
