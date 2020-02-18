package com.rws.invoker.model;

import java.io.Serializable;

public class RestWebServiceBaseResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private transient String invokeStatus;
    private transient String invokeStatusCode;

    public String getInvokeStatus() {
        return invokeStatus;
    }

    public void setInvokeStatus(String invokeStatus) {
        this.invokeStatus = invokeStatus;
    }

    public String getInvokeStatusCode() {
        return invokeStatusCode;
    }

    public void setInvokeStatusCode(String invokeStatusCode) {
        this.invokeStatusCode = invokeStatusCode;
    }

}
