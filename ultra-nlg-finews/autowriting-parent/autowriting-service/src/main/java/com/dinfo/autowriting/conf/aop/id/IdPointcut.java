package com.dinfo.autowriting.conf.aop.id;

import org.aspectj.lang.annotation.Pointcut;

/**
 * @author yangxf
 */
public class IdPointcut {
    
    @Pointcut("execution(public * com.dinfo.autowriting.mapper.*Mapper.add*(..)) || " +
            "execution(public * com.dinfo.autowriting.mapper.*Mapper.insert*(..)) ||" +
            "execution(public * com.dinfo.autowriting.mapper.*Mapper.create*(..)) ||" +
            "execution(public * com.dinfo.autowriting.mapper.*Mapper.put*(..))")
    void setId() {
    }

}