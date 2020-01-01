package org.pp.java8.concurrent.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface NotThreadSafe {
    String value() default "is not ThreadSafe";

    String desc() default "why";
}
