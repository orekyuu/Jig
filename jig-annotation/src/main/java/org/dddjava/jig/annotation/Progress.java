package org.dddjava.jig.annotation;

import java.lang.annotation.*;

/**
 * 進捗状況
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
public @interface Progress {
    String value();
}