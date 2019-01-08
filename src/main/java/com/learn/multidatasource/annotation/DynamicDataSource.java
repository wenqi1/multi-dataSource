package com.learn.multidatasource.annotation;

import com.learn.multidatasource.enums.DatabaseType;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DynamicDataSource {
    DatabaseType dataSourceType() default DatabaseType.ERP;
}
