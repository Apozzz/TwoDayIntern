package com.twoday.wms.shop.logging.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogMessage {
    String before() default "";
    String after() default "";
    Class<?> loggerClass() default Void.class;
}