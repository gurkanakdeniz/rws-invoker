package com.rws.invoker.core;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

import org.springframework.stereotype.Component;

import com.rws.invoker.config.RestWebServiceHandler;

@Component
public class RestWebServiceFactory {

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Object bean, Field field, RestWebServiceHandler restWebServiceInvokeHandler) {
        Object newInstance = null;
        try {
            boolean accessible = field.canAccess(bean);
            field.setAccessible(true);

            newInstance = newInstance(field.getType(), restWebServiceInvokeHandler);
            field.set(bean, newInstance);

            field.setAccessible(accessible);
        } catch (Exception e) {
            e.printStackTrace();
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
