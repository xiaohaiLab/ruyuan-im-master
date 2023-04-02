package com.ruyuan2020.im.common.core.util;

import com.ruyuan2020.im.common.core.constant.CloneDirection;
import com.ruyuan2020.im.common.core.domain.BaseDomain;
import com.ruyuan2020.im.common.core.domain.BasePage;
import net.sf.cglib.beans.BeanCopier;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * BeanCopier工具类
 */
public class BeanCopierUtils {

    /**
     * BeanCopier缓存
     */
    private static final Map<String, BeanCopier> BEAN_COPIER_CACHE = new ConcurrentHashMap<>();

    /**
     * 将source对象的属性拷贝到target对象中去
     *
     * @param source source对象
     * @param target target对象
     */
    public static void copy(Object source, Object target) {
        BeanCopier copier = getBeanCopier(source.getClass(), target.getClass());
        copier.copy(source, target, null);
    }

    public static <S extends BaseDomain, T> BasePage<T> convert(final BasePage<S> sourcePage, Class<T> target) {
        return convert(sourcePage, target, null);
    }

    public static <S extends BaseDomain, T extends BaseDomain> BasePage<S> convert(BasePage<T> page, Function<? super T, ? extends S> mapper) {
        List<T> list = page.getList();
        List<S> newList = list.stream().map(mapper).collect(Collectors.toList());
        return new BasePage<>(newList, page.getPagination());
    }

    public static <S extends BaseDomain, T> BasePage<T> convert(final BasePage<S> sourcePage, Class<T> target, CloneDirection cloneDirection) {
        List<T> targetList = new ArrayList<>(sourcePage.getList().size());
        if (null != sourcePage.getList() && !sourcePage.getList().isEmpty()) {
            for (S s : sourcePage.getList()) {
                if (Objects.isNull(cloneDirection)) {
                    targetList.add(s.clone(target));
                } else {
                    targetList.add(s.clone(target, cloneDirection));
                }
            }
        }
        BasePage<T> targetPage = new BasePage<>();
        BeanCopierUtils.copy(sourcePage, targetPage);
        targetPage.setList(targetList);
        return targetPage;
    }

    public static <S extends BaseDomain, T> List<T> convert(final List<S> sourceList, Class<T> target) {
        return convert(sourceList, target, null);
    }

    public static <S extends BaseDomain, T> List<T> convert(final List<S> sourceList, Class<T> target, CloneDirection cloneDirection) {
        if (null == sourceList || sourceList.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> targetList = new ArrayList<>(sourceList.size());
        for (S s : sourceList) {
            if (Objects.isNull(cloneDirection)) {
                targetList.add(s.clone(target));
            } else {
                targetList.add(s.clone(target, cloneDirection));
            }
        }
        return targetList;
    }

    private static BeanCopier getBeanCopier(Class<?> sourceClass, Class<?> targetClass) {
        String beanKey = generateKey(sourceClass, targetClass);
        BeanCopier copier;
        if (!BEAN_COPIER_CACHE.containsKey(beanKey)) {
            copier = BeanCopier.create(sourceClass, targetClass, false);
            BEAN_COPIER_CACHE.put(beanKey, copier);
        } else {
            copier = BEAN_COPIER_CACHE.get(beanKey);
        }
        return copier;
    }

    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.toString() + class2.toString();
    }
}
