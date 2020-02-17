package com.rws.invoker.model;

public class RestWebServiceInvokeException extends Exception {

    private static final long serialVersionUID = 1L;
    
    private String errorId;
    private String status;
    private String statusCode;
    private Throwable ex;

    public RestWebServiceInvokeException() {
        super();
    }

    public RestWebServiceInvokeException(String errorId, String status, String statusCode, Throwable ex) {
        super(ex);
        this.errorId = errorId;
        this.status = status;
        this.statusCode = statusCode;
        this.ex = ex;
    }

    public RestWebServiceInvokeException(Throwable ex) {
        super(ex);
        this.ex = ex;
    }

    public String getErrorId() {
        return errorId;
    }

    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Throwable getEx() {
        return ex;
    }

    public void setEx(Throwable ex) {
        this.ex = ex;
    }

}
