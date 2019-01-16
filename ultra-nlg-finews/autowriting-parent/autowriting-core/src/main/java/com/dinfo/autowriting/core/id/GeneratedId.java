package com.dinfo.autowriting.core.id;

import java.lang.annotation.*;

/**
 * @author yangxf
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GeneratedId {

    String generatorName() default "";
    
    Class<? extends IdGenerator> generatorClass() default SnowflakeIdGenerator.class;
    
}