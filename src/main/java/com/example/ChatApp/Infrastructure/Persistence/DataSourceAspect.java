package com.example.ChatApp.Infrastructure.Persistence;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAspect {

    @Before("@annotation(ReadOnly)")
    public void setReadDataSource() {
        DataSourceContextHolder.set(DataSourceType.SLAVE);
    }

    @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void setWriteDataSource() {
        DataSourceContextHolder.set(DataSourceType.MASTER);
    }

    @After("execution(* *(..))")
    public void clear() {
        DataSourceContextHolder.clear();
    }
}
