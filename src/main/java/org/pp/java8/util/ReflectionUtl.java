package org.pp.java8.util;

import org.pp.java8.algorithm.sort.MergeArraySort;

import java.lang.reflect.*;
import java.util.Arrays;

public class ReflectionUtl {

    /**
     * 只适用与类的继承 并且只能有一个泛型并且不能有上限 实现接口不适用
     * @param clazz
     * @param index
     * @return
     */
    public static Class getSuperClassGenericType(final Class clazz, final int index) {
//        System.out.println(clazz.getSimpleName());
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

    public static Class getSuperInterfaceGenericType(final Class clazz) {
        Type genType = clazz.getGenericInterfaces()[0];
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        TypeVariable typeVariable = (TypeVariable) params[0];
        Type[] types = typeVariable.getBounds();
        Type t = ((ParameterizedType) types[0]).getRawType();
        if(t instanceof  Class) {
            return (Class) t;
        }
        return null;
    }

    public static void printTypes(Type[] types) {
        boolean endFlag = Arrays.equals(types, new Type[]{Object.class});
        if (endFlag) {
            return;
        }
        int len = types.length;
        for (int i = 0; i < len; i++) {
            printType(types[i]);
        }

    }

    public static void printType(Type type) {
        if (type instanceof Class) {
            Class<?> clazz = (Class<?>) type;
            System.out.println("Class-name:" + clazz.getName());
        } else if (type instanceof TypeVariable) {
            TypeVariable typeVariable = (TypeVariable) type;
            System.out.println("TypeVariable-name:" + typeVariable.getName());
            System.out.println("------------------------------------TypeVariable bounds start------------------------------------");
            Type[] types = typeVariable.getBounds();
            printTypes(types);
            System.out.println("------------------------------------TypeVariable bounds end------------------------------------");
        } else if(type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            System.out.println("ParameterizedType:" + parameterizedType.getClass().getName());
            System.out.println("parameterizedType.toString()：" + parameterizedType.toString());
            System.out.println("声明此参数化类型的类或接口：");
            printType(parameterizedType.getRawType());
//            System.out.println("===参数化类型的实际类型参数begin===");
            Type[] types = parameterizedType.getActualTypeArguments();
            printTypes(types);
//            System.out.println("===参数化类型的实际类型参数end===");
        } else if (type instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) type;
            System.out.println("WildcardType:" + wildcardType.getClass().getName());
            printTypes(wildcardType.getLowerBounds());
            printTypes(wildcardType.getUpperBounds());
        } else if(type instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType) type;
            System.out.println("GenericArrayType:" + genericArrayType.getClass().getName());
            printType(genericArrayType.getGenericComponentType());
        }
    }


    public static void main(String[] args) {
        getSuperInterfaceGenericType(MergeArraySort.class);
    }
}
