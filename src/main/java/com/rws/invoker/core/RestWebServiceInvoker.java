package com.rws.invoker.core;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.rws.invoker.model.RestWebServiceEndpoint;
import com.rws.invoker.model.RestWebServiceInvokeException;
import com.rws.invoker.model.RestWebServiceInvokeResponse;
import com.rws.invoker.model.RestWebServiceMethod;
import com.rws.invoker.model.RestWebServiceProperties;

public class RestWebServiceInvoker {
    
    private String status = null;
    private String statusCode = null;
    
    Logger logger = LoggerFactory.getLogger(RestWebServiceInvokeHandler.class);
    
    public Object invoke(Method m, Object request, RestWebServiceProperties properties,
            RestWebServiceMethod methodType, RestWebServiceEndpoint endpoint, Gson gson) throws RestWebServiceInvokeException {
        Object response = null;
        
        String url = RestWebServiceUtil.getUrl(endpoint.getUrl(), request);
        try {
            RestWebServiceInvokeResponse invokeResponse = null; 
            
            if (RestWebServiceMethod.GET.equals(methodType)) {
                invokeResponse = RestWebServiceInvokeUtil.get(url, endpoint.getConnectionTimeout(), endpoint.getReadTimeout());
            } else if (RestWebServiceMethod.PATCH.equals(methodType)) {
                invokeResponse = RestWebServiceInvokeUtil.patch(url, gson.toJson(request), endpoint.getConnectionTimeout(), endpoint.getReadTimeout());
            } else if (RestWebServiceMethod.POST.equals(methodType)) {
                invokeResponse = RestWebServiceInvokeUtil.post(url, gson.toJson(request), endpoint.getConnectionTimeout(), endpoint.getReadTimeout());
            } else if (RestWebServiceMethod.PUT.equals(methodType)) {
                invokeResponse = RestWebServiceInvokeUtil.put(url, gson.toJson(request), endpoint.getConnectionTimeout(), endpoint.getReadTimeout());
            }
            
            status = invokeResponse.getStatus();
            statusCode = String.valueOf(invokeResponse.getStatusCode());
            response = gson.fromJson((String) invokeResponse.getResponse(), m.getReturnType());
        } catch (Exception e) {
            logger.error("Rest Web Service Invoke Error : ", e);
            log(response, request, properties, gson, url, status, statusCode);
            throw new RestWebServiceInvokeException("42", status, statusCode, e);
        }
        
        log(response, request, properties, gson, url, status, statusCode);
        return response;
    }

    private void log(Object response, Object request, RestWebServiceProperties properties, Gson gson, String url,
            String status, String statusCode) {
        if (properties.isLogging()) {
            logger.info("");
            logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            logger.info("-------- REQUEST START ----------");
            logger.info("URL        : " + url);
            logger.info("REQ        : " + gson.toJson(request));
            logger.info("--------  REQUEST END  ----------");
            logger.info("");
            logger.info("-------- RESPONSE START ----------");
            logger.info("URL        : " + url);
            logger.info("STATUS     : " + status);
            logger.info("STATUSCODE : " + statusCode);
            logger.info("RES        : " + gson.toJson(response));
            logger.info("--------  RESPONSE END  ----------");
            logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            logger.info("");
        }
    }

    public String getStatus() {
        return status;
    }

    public String getStatusCode() {
        return statusCode;
    }

}
