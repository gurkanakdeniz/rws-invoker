package com.rws.invoker.core;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.rws.invoker.config.RestWebServiceHandler;

@Component
public class RestWebServiceFactory {
    
    static Logger logger = LoggerFactory.getLogger(RestWebServiceFactory.class);

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Object bean, Field field, RestWebServiceHandler restWebServiceInvokeHandler) {
        Object newInstance = null;
        try {
//            boolean accessible = field.canAccess(bean);
            field.setAccessible(true);

            newInstance = newInstance(field.getType(), restWebServiceInvokeHandler);
            field.set(bean, newInstance);

//            field.setAccessible(accessible);
        } catch (Exception e) {
            logger.error("New Instance Error : ", e);
        }

        return (T) newInstance;
    }

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<T> restWebService, RestWebServiceHandler restWebServiceHandler) {
        Object newProxyInstance = Proxy.newProxyInstance(RestWebServiceFactory.class.getClassLoader(),
                new Class[] { restWebService }, restWebServiceHandler);
        return (T) newProxyInstance;
    }

}
