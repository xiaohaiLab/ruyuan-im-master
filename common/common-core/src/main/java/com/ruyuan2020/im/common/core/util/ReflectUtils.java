package com.ruyuan2020.im.common.core.util;

import com.ruyuan2020.im.common.core.exception.UtilException;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 反射辅助工具类
 */
public class ReflectUtils {

    public static <T> T invokeConstructor(Class<T> clazz, Class<?>[] argTypes, Object... args) {
        try{
            Constructor<T> constructor = clazz.getConstructor(argTypes);
            return constructor.newInstance(args);
        }catch (Exception e){
            throw new UtilException(e.getMessage(), e);
        }
    }

    public static <T> Class<?> getPropertyType(T target, String fieldName){
        try{
            PropertyDescriptor pd = new PropertyDescriptor(fieldName, target.getClass());
            return pd.getPropertyType();
        }catch (Exception e){
            throw new UtilException(e.getMessage(), e);
        }
    }

    public static <T> Object invokeGetter(T target, String fieldName) {
        try{
            PropertyDescriptor pd = new PropertyDescriptor(fieldName, target.getClass());
            Method method = pd.getReadMethod();
            return method.invoke(target);
        }catch (Exception e){
            throw new UtilException(e.getMessage(), e);
        }
    }

    public static <T> void invokeSetter(T target, String fieldName, Object arg) {
        try{
            PropertyDescriptor pd = new PropertyDescriptor(fieldName, target.getClass());
            Method method = pd.getWriteMethod();
            method.invoke(target, arg);
        }catch (Exception e){
            throw new UtilException(e.getMessage(), e);
        }
    }
}
