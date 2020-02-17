package com.rws.invoker.core;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rws.invoker.config.RestWebServiceConfig;
import com.rws.invoker.config.RestWebServiceHandler;
import com.rws.invoker.model.RestWebServiceEndpoint;
import com.rws.invoker.model.RestWebServiceMethod;

@Component
public class RestWebServiceInvokeHandler implements RestWebServiceHandler {

    @Autowired
    RestWebServiceConfig config;

    @Override
    public Object invoke(Object arg, Method m, Object[] arg2) throws Throwable {
        Object response = null;
        Object request = (arg2 != null && arg2.length > 0) ? arg2[0] : null;

        HashMap<String, RestWebServiceEndpoint> endpoints = config.endpoints();
        RestWebServiceMethod methodType = RestWebServiceUtil.getMethod(m);
        RestWebServiceEndpoint endpoint = RestWebServiceUtil.getEndpoint(m, endpoints, methodType);

        if (endpoint != null) {
            RestWebServiceInvoker invoker = new RestWebServiceInvoker();
            response = invoker.invoke(m, request, config.properties(), methodType, endpoint, config.mapper(endpoint));
        }

        return response;
    }

}
