package com.hit.server;

import java.util.Map;
import java.lang.String;

public class Request<T> {

    private Map<String, String> headers;
    private T body;

    /** C'tor
     * @param headers the type of the request
     * @param body the content of the request
     */
    public Request(Map<String, String> headers, T body) {
        this.headers = headers;
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    /**
     * @return request as a string
     */
    @Override
    public String toString() {
        return "Request{" +
                "headers=" + headers +
                ", body=" + body +
                '}';
    }
}
