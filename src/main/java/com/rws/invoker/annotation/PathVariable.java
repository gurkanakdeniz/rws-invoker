package com.rws.invoker.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = { ElementType.METHOD, ElementType.TYPE, ElementType.FIELD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PathVariable {
    
    String name() default "";
    
    String dateFormat() default "yyyy-MM-dd HH:mm:ss";
}
