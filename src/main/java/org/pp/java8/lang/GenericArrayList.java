package org.pp.java8.lang;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public class GenericArrayList<T> {
    private T[] ts;
    private int size = 0;

    public GenericArrayList(int len) {
        init(len);
    }

    public void init(int capacity) {
        Class<T> tClass = getSuperClassGenericType(getClass(), 0);
        this.ts = (T[]) Array.newInstance(tClass, capacity);
    }

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

    /**
     * 加法，就不扩容了
     *
     * @param t 假的元素
     * @return 加成功了就是true
     */
    public boolean add(T t) {
        ts[size++] = t;
        return true;
    }

    /**
     * 移除一个元素
     *
     * @param t 要移除的元素
     * @return 移除成功就是true
     */
    public boolean remove(T t) {
        // fastRemove
        for (int index = 0; index < size; index++) {
            if (t.equals(ts[index])) {
                int numMoved = size - index - 1;
                if (numMoved > 0)
                    System.arraycopy(ts, index + 1, ts, index, numMoved);
                ts[--size] = null; // clear to let GC do its work
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        return Arrays.toString(ts);
    }
}
