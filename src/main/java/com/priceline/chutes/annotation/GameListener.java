package com.priceline.chutes.annotation;

import java.lang.annotation.*;

/**
 * @author lzn
 * @Description Annotation that marks a method as a listener for application events
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GameListener {

    String condition() default "";
}
