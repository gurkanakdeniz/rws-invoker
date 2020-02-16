package com.rws.invoker.core;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.rws.invoker.config.RestWebServiceConfig;
import com.rws.invoker.config.RestWebServiceHandler;
import com.rws.invoker.model.RestWebServiceEndpoint;
import com.rws.invoker.model.RestWebServiceProperties;

@Component
public class RestWebServiceInvokeHandler implements RestWebServiceHandler {

    @Autowired
    RestWebServiceConfig config;

    @Override
    public Object invoke(Object arg0, Method arg1, Object[] arg2) throws Throwable {
        // TODO:GA:

        RestWebServiceProperties properties = config.properties();
        Gson gson = config.mapper();
        HashMap<String, RestWebServiceEndpoint> endpoints = config.endpoints();
        System.out.println("=============== invoke rest service =================");
        return null;
    }

}
