package com.example.ChatApp.Infrastructure.Persistence;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class DataSourceAspect {

    /**
     * READ → SLAVE
     */
    @Around("""
        (
            @annotation(com.example.ChatApp.Infrastructure.Persistence.ReadOnly)
            || @within(com.example.ChatApp.Infrastructure.Persistence.ReadOnly)
        )
        && within(com.example.ChatApp.Application..*)
    """)
    public Object useSlave(ProceedingJoinPoint pjp) throws Throwable {
        try {
            DataSourceContextHolder.set(DataSourceType.SLAVE);
            return pjp.proceed();
        } finally {
            DataSourceContextHolder.clear();
        }
    }

    /**
     * WRITE → MASTER
     */
    @Around("""
        @annotation(org.springframework.transaction.annotation.Transactional)
        && within(com.example.ChatApp.Application..*)
    """)
    public Object useMaster(ProceedingJoinPoint pjp) throws Throwable {
        try {
            DataSourceContextHolder.set(DataSourceType.MASTER);
            return pjp.proceed();
        } finally {
            DataSourceContextHolder.clear();
        }
    }
}
