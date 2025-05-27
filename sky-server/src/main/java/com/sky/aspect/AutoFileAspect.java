package com.sky.aspect;

import com.alibaba.druid.support.spring.stat.annotation.Stat;
import com.sky.anno.AutoFile;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.beans.beancontext.BeanContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @param
 * @return
 */
@Slf4j
@Aspect
@Component
public class AutoFileAspect {

    @Before("@annotation(com.sky.anno.AutoFile)")
    public void autoFileAspect(JoinPoint joinPoint){
        // 1、获取注解中的operation值
        // 1.1、先获取方法对象
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        // 1.2、使用方法对象获取注解中的参数
        AutoFile annotation = method.getAnnotation(AutoFile.class);
        OperationType value = annotation.value();

        // 2、获取目标方法的参数,也就是实体类
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0){
            return;
        }
        Object entity = args[0];

        // 3、根据operation值判断执行什么操作

        if (value == OperationType.INSERT){
            // 通过反射补充属性值
            try {
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method updateCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method updateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                setCreateTime.invoke(entity, LocalDateTime.now());
                updateCreateTime.invoke(entity,LocalDateTime.now());
                setUser.invoke(entity, BaseContext.getCurrentId());
                updateUser.invoke(entity, BaseContext.getCurrentId());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }else {
            // 4、update操作
            try {
                Method updateCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method updateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                updateCreateTime.invoke(entity,LocalDateTime.now());
                updateUser.invoke(entity, BaseContext.getCurrentId());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
