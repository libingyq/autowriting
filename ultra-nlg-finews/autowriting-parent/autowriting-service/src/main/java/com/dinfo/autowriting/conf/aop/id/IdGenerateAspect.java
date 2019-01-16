package com.dinfo.autowriting.conf.aop.id;

import com.dinfo.autowriting.core.id.GeneratedId;
import com.dinfo.autowriting.core.id.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

/**
 * 为标注了{@link GeneratedId}的字段设置id
 */
@Slf4j
@Aspect
@Component
public class IdGenerateAspect {

    private static final Method EMPTY_METHOD;
    private static final ConcurrentMap<Class<?>, Method> ID_SETTER_MAP = new ConcurrentHashMap<>();

    @Resource
    private IdGenerator idGenerator;

    @SuppressWarnings("unchecked")
    @Around("com.dinfo.autowriting.conf.aop.id.IdPointcut.setId()")
    public Object aroundMethod(ProceedingJoinPoint pjp) {
        Object[] args = pjp.getArgs();
        Signature signature = pjp.getSignature();
        String methodName = signature.toShortString();
        try {
            if (!(signature instanceof MethodSignature)) {
                log.warn(" WARN method: {} is not MethodSignature, skip.", methodName);
                return pjp.proceed(args);
            }


            if (args == null || args.length != 1) {
                log.warn(" WARN method: {} is not add method, skip.", methodName);
                return pjp.proceed(args);
            }

            Object obj = args[0];
            Class<?> aClass = obj.getClass();
            if (Collection.class.isAssignableFrom(aClass)) {
                Collection<Object> collection = (Collection<Object>) obj;
                if (!collection.isEmpty()) {
                    Object first = collection.stream().findFirst().get();
                    Method method = findMethod(first.getClass());
                    for (Object ele : collection) {
                        invokeMethod(ele, method);
                    }
                }
            } else {
                Method method = findMethod(aClass);
                invokeMethod(obj, method);
            }

            return pjp.proceed(args);
        } catch (Throwable throwable) {
            log.debug("ERROR method: {} error: {} {}", methodName, throwable.getClass().getSimpleName(), throwable.getMessage());
            throw new RuntimeException(throwable);
        }
    }

    private void invokeMethod(Object obj, Method method) throws IllegalAccessException, InvocationTargetException {
        if (method == EMPTY_METHOD) {
            method.invoke(this, obj.getClass());
        } else {
            method.invoke(obj, idGenerator.nextId());
        }
    }

    private Method findMethod(Class<?> aClass) {
        Method method = ID_SETTER_MAP.get(aClass);
        if (method == null) {
            synchronized (ID_SETTER_MAP) {
                method = ID_SETTER_MAP.get(aClass);
                if (method == null) {
                    Method setter = findSetter(aClass);
                    method = ID_SETTER_MAP.putIfAbsent(aClass, setter);
                    if (method == null) {
                        method = setter;
                    }
                }
            }
        }
        return method;
    }

    private Method findSetter(Class<?> aClass) {
        Field[] declaredFields = aClass.getDeclaredFields();
        Field[] fields = Stream.of(declaredFields)
                .filter(f -> f.isAnnotationPresent(GeneratedId.class))
                .toArray(Field[]::new);

        if (fields.length == 0) {
            return EMPTY_METHOD;
        } else if (fields.length == 1) {
            Field field = fields[0];
            Class<?> fieldType = field.getType();
            if (fieldType != Long.class && fieldType != long.class) {
                throw new IllegalArgumentException("id must be long or java.lang.Long.");
            }
            String fieldName = field.getName();
            char[] chars = fieldName.toCharArray();
            if (chars[0] >= 'a' && chars[0] <= 'z')
                chars[0] -= 32;

            String methodName = "set" + new String(chars);

            try {
                Method declaredMethod = aClass.getDeclaredMethod(methodName, fieldType);
                if (Modifier.isPublic(declaredMethod.getModifiers())) {
                    return declaredMethod;
                }

                throw new RuntimeException(methodName + " is not public.");
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

        } else {
            throw new RuntimeException("duplicate @GeneratedId error");
        }
    }

    private void empty(Class<?> cls) {
        log.warn("nothing annotated by @GeneratedId in {}", cls.getName());
    }

    static {
        Class<IdGenerateAspect> cls = IdGenerateAspect.class;
        try {
            EMPTY_METHOD = cls.getDeclaredMethod("empty", Class.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}