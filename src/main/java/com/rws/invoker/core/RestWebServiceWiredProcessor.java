package com.rws.invoker.core;

import java.lang.reflect.Field;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.rws.invoker.annotation.RestWebService;

@Component
public class RestWebServiceWiredProcessor implements BeanPostProcessor {
    
    @Autowired
    RestWebServiceInvokeHandler restWebServiceInvokeHandler;

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (bean != null && bean.getClass() != null) {
            Field[] declaredFields = bean.getClass().getDeclaredFields();
            if (declaredFields != null && declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    if (field.isAnnotationPresent(RestWebService.class)) {
                        RestWebServiceFactory.newInstance(bean, field, restWebServiceInvokeHandler);
                    }
                }
            }
        }

        return bean;
    }
}
