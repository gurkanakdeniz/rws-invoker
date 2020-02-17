package com.rws.invoker.core;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.rws.invoker.annotation.RestWebService;

@Component
public class RestWebServiceWiredProcessor implements BeanPostProcessor {

    @Autowired
    RestWebServiceInvokeHandler restWebServiceInvokeHandler;

    private static HashMap<Class<?>, Object> proxies = new HashMap<Class<?>, Object>();

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (bean != null && bean.getClass() != null) {
            Field[] declaredFields = bean.getClass().getDeclaredFields();
            if (declaredFields != null && declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    if (field.isAnnotationPresent(RestWebService.class)) {
                        synchronized (proxies) {
                            Class<?> type = field.getType();
                            Object proxy = proxies.get(type);
                            
                            if (proxy == null) {
                                proxy = RestWebServiceFactory.newInstance(bean, field, restWebServiceInvokeHandler);
                            }
                            
                            proxies.put(type, proxy);
                        }
                    }
                }
            }
        }

        return bean;
    }
}
