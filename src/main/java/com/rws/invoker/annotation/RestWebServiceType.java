package com.rws.invoker.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.rws.invoker.model.RestWebServiceMethod;

@Target(value = { ElementType.METHOD, ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RestWebServiceType {

    RestWebServiceMethod value() default RestWebServiceMethod.GET;

}
