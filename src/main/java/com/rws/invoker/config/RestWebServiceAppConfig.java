package com.rws.invoker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.rws.invoker.core.RestWebServiceInvokeHandler;
import com.rws.invoker.core.RestWebServiceWiredProcessor;

@Import({ RestWebServiceInvokeHandler.class, RestWebServiceWiredProcessor.class })
@Configuration
public class RestWebServiceAppConfig {
    

}