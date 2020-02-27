package org.pp.java8.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ObjectFactory {

    /**
     * 浅拷贝，生产size个T类型对象，返回List<T>
     */
    public static <T> List<T> newList(T t, int size) throws IllegalAccessException, InstantiationException {
        List<T> result = new ArrayList<>(size);
        while (size-- > 0) {
            result.add((T) t.getClass().newInstance());
        }
        return Collections.unmodifiableList(result);
    }
}
