package org.pp.java8.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionUtl {

    public static Class getSuperClassGenericType(final Class clazz, final int index) {
        System.out.println(clazz.getSimpleName());
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }
}
