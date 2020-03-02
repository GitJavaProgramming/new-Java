package org.pp.commons.apache.beanutils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;
import org.pp.commons.testbean.Person;
import org.pp.commons.testbean.User;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class TestBean {

    /**
     * 浅拷贝
     * BeanUtils.cloneBean(u) 调用 getPropertyUtils().copyProperties(newBean, bean);
     * PropertyUtils.copyProperties(person2, person)
     */
    @Test
    public void test() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Person person = new Person();
        PropertyUtils.setProperty(person, "name", "pluto");
        PropertyUtils.setProperty(person, "age", 1);
        User u = new User("password");
//        User user = (User) BeanUtils.cloneBean(u);
        PropertyUtils.setProperty(person, "user", u);
        String pu = (String) PropertyUtils.getNestedProperty(person, "user.password");
        System.out.println(pu);

        Person person1 = (Person) BeanUtils.cloneBean(person); // 浅拷贝
        PropertyUtils.setNestedProperty(person1, "user.password", "密码"); // 修改user.password
        // 比较两个对象的user.password属性
        pu = (String) PropertyUtils.getNestedProperty(person, "user.password");
        String pu2 = (String) PropertyUtils.getNestedProperty(person1, "user.password");
        // 结论 引用属性浅拷贝 user相同引用 可以参考源码证明
        System.out.println(pu);
        System.out.println(pu2);

        Person person2 = new Person();
        PropertyUtils.copyProperties(person2, person); // 复制属性  浅拷贝
        PropertyUtils.setNestedProperty(person2, "user.password", "修改密码");
        System.out.println(person2);

        pu = (String) PropertyUtils.getNestedProperty(person, "user.password");
        System.out.println(pu);

//        User user = (User) PropertyUtils.getSimpleProperty(person, "user");
//        User user2 = (User) PropertyUtils.getSimpleProperty(person2, "user");
//        System.out.println(user == user2);

        Map beanMap = BeanUtils.describe(person2);
        System.out.println(beanMap);
//        BeanUtils.populate(person2, beanMap); // exception
    }
}
