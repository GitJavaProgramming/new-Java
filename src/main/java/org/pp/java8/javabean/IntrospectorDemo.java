package org.pp.java8.javabean;

import java.beans.*;

public class IntrospectorDemo {

    public static void main(String[] args) throws ClassNotFoundException, IntrospectionException {
        Class<?> clazz = Class.forName("org.pp.java8.javabean.Colors");
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);

        System.out.println("Properties:");
        PropertyDescriptor[] propertyDescriptor = beanInfo.getPropertyDescriptors();
        for(PropertyDescriptor pd : propertyDescriptor) {
            System.out.println("\t" + pd.getDisplayName() + "-->" + pd.getPropertyType());
        }

        System.out.println("Events:");
        EventSetDescriptor[] esd = beanInfo.getEventSetDescriptors();
        for(EventSetDescriptor pd : esd) {
            System.out.println("\t" + pd.getDisplayName());
        }
    }
}
