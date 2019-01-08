package com.learn.multidatasource.aop;

import com.learn.multidatasource.annotation.DynamicDataSource;
import com.learn.multidatasource.config.DatabaseContextHolder;
import com.learn.multidatasource.enums.DatabaseType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;

@Aspect
@Component
@Order(1)
public class HandlerDataSourceAop {
    @Pointcut("@within(com.learn.multidatasource.annotation.DynamicDataSource)||" +
            "@annotation(com.learn.multidatasource.annotation.DynamicDataSource)")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint)
    {
        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
        //获取方法上的注解
        DynamicDataSource annotationClass = method.getAnnotation(DynamicDataSource.class);
        if(annotationClass == null){
            //获取类上面的注解
            annotationClass = joinPoint.getTarget().getClass().getAnnotation(DynamicDataSource.class);
            if(annotationClass == null) {
                return;
            }
        }
        //获取注解上的数据源类型
        DatabaseType databaseType = annotationClass.dataSourceType();
        if(databaseType != null){
            //设置为指定的数据源
            DatabaseContextHolder.setDatabaseType(databaseType);
        }
    }

    @After("pointcut()")
    public void after(JoinPoint point) {
        //清理掉当前设置的数据源类型
        DatabaseContextHolder.clear();
    }

}
