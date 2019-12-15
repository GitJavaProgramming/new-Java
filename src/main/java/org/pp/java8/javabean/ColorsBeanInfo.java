package org.pp.java8.javabean;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class ColorsBeanInfo extends SimpleBeanInfo {
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor rectangular = new PropertyDescriptor("rectangular", Colors.class);
            PropertyDescriptor[] pds = {rectangular};
            return pds;
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
