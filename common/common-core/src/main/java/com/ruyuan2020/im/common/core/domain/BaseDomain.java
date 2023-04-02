package com.ruyuan2020.im.common.core.domain;

import com.alibaba.fastjson.JSONObject;
import com.ruyuan2020.im.common.core.exception.SystemException;
import com.ruyuan2020.im.common.core.constant.CloneDirection;
import com.ruyuan2020.im.common.core.constant.DomainType;
import com.ruyuan2020.im.common.core.util.BeanCopierUtils;
import com.ruyuan2020.im.common.core.util.ReflectUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 领域模型抽象基类
 */
public abstract class BaseDomain implements Serializable {

    /**
     * 浅度克隆
     *
     * @param clazz 类对象
     * @param <T>   类型
     * @return clazz类型对象
     */
    public <T> T clone(Class<T> clazz) {
        try {
            T target = clazz.newInstance();
            BeanCopierUtils.copy(this, target);
            return target;
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    /**
     * 浅拷贝
     *
     * @param source 指定的对象源
     * @param <T>    类型
     */
    public <T> void copy(T source) {
        BeanCopierUtils.copy(source, this);
    }

    /**
     * 深度克隆
     *
     * @param clazz          类型
     * @param cloneDirection 克隆方向
     * @return <T>    类型
     */
    public <T> T clone(Class<T> clazz, CloneDirection cloneDirection) {

        try {
            // 先完成基本字段的浅克隆
            T target = clazz.newInstance();
            BeanCopierUtils.copy(this, target);

            // 完成所有List类型的深度克隆
            Class<?> thisClazz = this.getClass();

            Field[] fields = thisClazz.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);

                // 如果判断某个字段是List类型的
                Class<?> cloneTargetClazz = null;
                if (field.getType() == List.class) {
                    List<?> list = (List<?>) field.get(this);
                    if (list == null || list.size() == 0) {
                        continue;
                    }

                    // 获取List集合中的泛型类型
                    Class<?> listGenericClazz = getListGenericType(field);
                    // 获取要克隆的目标类型
                    // 假设CloneDirection是反向，此时获取到的就是VO

                    cloneTargetClazz = getCloneTargetClazz(listGenericClazz, cloneDirection);
                    if (cloneTargetClazz != null) {
                        // 将list集合克隆到目标list集合中去
                        List<?> clonedList = new ArrayList<>();
                        cloneList(list, clonedList, cloneTargetClazz, cloneDirection);

                        // 设置list类型的属性
                        ReflectUtils.invokeSetter(target, field.getName(), clonedList);
                    }
                } else if ((cloneTargetClazz = getCloneTargetClazz(field.getType(), cloneDirection)) != null) {
                    BaseDomain targetObject = (BaseDomain) field.get(this);
                    if (targetObject != null) {
                        BaseDomain clonedObject = (BaseDomain) targetObject.clone(cloneTargetClazz, cloneDirection);
                        ReflectUtils.invokeSetter(target, field.getName(), clonedObject);
                    }
                }
            }

            return target;
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    /**
     * 将对象转换成字符串
     *
     * @return 转换后的字符串
     */
    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE).toString();
    }

    public String toStringExceptGMT() {
        return (new ReflectionToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE) {
            @Override
            protected boolean accept(Field f) {
                return super.accept(f) && !"gmtCreate".equals(f.getName()) && !"gmtModified".equals(f.getName());
            }
        }).toString();
    }

    /**
     * 将对象转换成json字符串
     *
     * @return 转换后的json字符串
     */
    public String toJsonStr() {
        return JSONObject.toJSONString(this);
    }

    /**
     * 将一个list克隆到另外一个list
     *
     * @param sourceList       源list
     * @param targetList       目标list
     * @param cloneTargetClazz 克隆目标类型
     * @param cloneDirection   克隆方向
     */
    @SuppressWarnings("unchecked")
    private void cloneList(List sourceList, List targetList, Class<?> cloneTargetClazz, CloneDirection cloneDirection) {
        for (Object object : sourceList) {
            if (Objects.nonNull(object)) {
                BaseDomain targetObject = (BaseDomain) object;
                // 将集合中的RelationDTO，调用其clone()方法，将其往RelationVO去克隆
                BaseDomain clonedObject = (BaseDomain) targetObject.clone(cloneTargetClazz, cloneDirection);
                // RelationVO的集合
                targetList.add(clonedObject);
            }
        }
    }

    /**
     * 获取list集合的泛型类型
     *
     * @param field 属性对象
     * @return 类型
     */
    private Class<?> getListGenericType(Field field) {
        Type genericType = field.getGenericType();
        ParameterizedType parameterizedType = (ParameterizedType) genericType;
        return (Class<?>) parameterizedType.getActualTypeArguments()[0];
    }

    /**
     * 获取目标类名
     *
     * @param clazz          类型
     * @param cloneDirection 克隆方向
     * @return 类型
     */
    private Class<?> getCloneTargetClazz(Class<?> clazz, CloneDirection cloneDirection) {
        String cloneTargetClassName = null;

        String className = clazz.getName();

        if (cloneDirection.equals(CloneDirection.FORWARD)) {
            if (className.endsWith(DomainType.VO)) {
                cloneTargetClassName = className.substring(0, className.length() - 2) + "DTO";
            } else if (className.endsWith(DomainType.DTO)) {
                cloneTargetClassName = className.substring(0, className.length() - 3) + "DO";
            } else return null;
        } else if (cloneDirection.equals(CloneDirection.OPPOSITE)) {
            if (className.endsWith(DomainType.DO)) {
                cloneTargetClassName = className.substring(0, className.length() - 2) + "DTO";
            } else if (className.endsWith(DomainType.DTO)) {
                cloneTargetClassName = className.substring(0, className.length() - 3) + "VO";
            } else return null;
        }
        try {
            return Class.forName(cloneTargetClassName);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }
}