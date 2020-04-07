package org.pp.java8.patterns.structural.adapter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * 适配器
 * 三大接口MouseListener, MouseWheelListener, MouseMotionListener都可以使用MouseAdapter类型作为访问入口，多态动态方法分派
 * 因为MouseAdapter是它们公共的子类型
 */
public class MouseHandler extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("mouseClicked.");
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
//        e.getWheelRotation()
        System.out.println("mouseWheelMoved.");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        e.getX()
//        System.out.println("mouseMoved.");
    }
}
