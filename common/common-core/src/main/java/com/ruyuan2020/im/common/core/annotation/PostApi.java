package com.ruyuan2020.im.common.core.annotation;

import java.lang.annotation.*;

/**
 * @author case
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PostApi {

    String value() default "";
}
