package com.rws.invoker.core;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.Gson;
import com.rws.invoker.config.RestWebServiceConfig;
import com.rws.invoker.config.RestWebServiceHandler;
import com.rws.invoker.model.RestWebServiceEndpoint;
import com.rws.invoker.model.RestWebServiceMethod;
import com.rws.invoker.model.RestWebServiceProperties;

@Component
public class RestWebServiceInvokeHandler implements RestWebServiceHandler {

    Logger logger = LoggerFactory.getLogger(RestWebServiceInvokeHandler.class);
    
    @Autowired
    RestWebServiceConfig config;

    @Override
    public Object invoke(Object arg, Method m, Object[] arg2) throws Throwable {
        Object response = null;
        Object request = null;

        Gson gson = config.mapper();
        RestWebServiceProperties properties = config.properties();
        HashMap<String, RestWebServiceEndpoint> endpoints = config.endpoints();
        RestWebServiceMethod methodType = RestWebServiceUtil.getMethod(m);
        RestWebServiceEndpoint endpoint = RestWebServiceUtil.getEndpoint(m, endpoints, methodType);

        if (endpoint != null) {
            // TODO:GA: exception handling
            // TODO:GA: prepare url, request body
            String url = endpoint.getUrl();
            WebClient webClient = RestWebServiceUtil.getWebClient(url, endpoint.getConnectionTimeout(),
                    endpoint.getReadTimeout());

            if (RestWebServiceMethod.GET.equals(methodType)) {
                response = webClient.get().retrieve().bodyToMono(m.getReturnType()).block();
            } else if (RestWebServiceMethod.POST.equals(methodType)) {
                response = webClient.post().retrieve().bodyToMono(m.getReturnType()).block();
            } else if (RestWebServiceMethod.PUT.equals(methodType)) {
                response = webClient.put().retrieve().bodyToMono(m.getReturnType()).block();
            } else if (RestWebServiceMethod.PATCH.equals(methodType)) {
                response = webClient.patch().retrieve().bodyToMono(m.getReturnType()).block();
            }
        }

        //TODO:GA: http codes
        if (properties.isLogging()) {
            logger.info("---------REQUEST START-----------");
            logger.info(gson.toJson(request));
            logger.info("--------- REQUEST END -----------");

            logger.info("---------RESPONSE START-----------");
            logger.info(gson.toJson(response));
            logger.info("--------- RESPONSE END -----------");
        }

        return response;
    }

}
