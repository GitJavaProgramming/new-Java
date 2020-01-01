package org.pp.java8.concurrent.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface ThreadSafe {
    String value() default "is ThreadSafe";
}
