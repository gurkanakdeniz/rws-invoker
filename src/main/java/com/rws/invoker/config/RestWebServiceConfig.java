package com.rws.invoker.config;

import java.util.HashMap;

import com.google.gson.Gson;
import com.rws.invoker.model.RestWebServiceEndpoint;
import com.rws.invoker.model.RestWebServiceProperties;

public abstract class RestWebServiceConfig {

    public RestWebServiceProperties properties() {
        RestWebServiceProperties props = new RestWebServiceProperties();
        props.setLogging(true);

        return props;
    }

    /**
     * override if use custom mapper 
     * */
    public Gson mapper(RestWebServiceEndpoint endpoint) {
        return new Gson();
    };

    /**
     * HashMap Key : {@link com.example.demo.core.model.RestWebServiceEndpoint#getEndpointKey()}
     */
    public abstract HashMap<String, RestWebServiceEndpoint> endpoints();

}
