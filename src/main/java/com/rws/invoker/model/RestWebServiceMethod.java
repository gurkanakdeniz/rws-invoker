package com.rws.invoker.model;

public enum RestWebServiceMethod {

    GET("GET"), PATCH("PATCH"), POST("POST"), PUT("PUT");

    private String method;

    private RestWebServiceMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}
