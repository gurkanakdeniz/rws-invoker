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

    public Gson mapper() {
        return new Gson();
    };

    /**
     * HashMap Key : {@link com.example.demo.core.model.RestWebServiceEndpoint#getEndpointKey()}
     */
    public abstract HashMap<String, RestWebServiceEndpoint> endpoints();

}
