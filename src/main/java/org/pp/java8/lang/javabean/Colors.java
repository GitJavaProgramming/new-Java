package org.pp.java8.lang.javabean;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

public class Colors extends Canvas implements Serializable {

    transient private Color color;
    private boolean rectangular;

    public Colors() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                change();
            }
        });
        rectangular = false;
        setSize(200, 100);
        change();
    }

    private void change() {
        color = randomColor();
        repaint();
    }

    private Color randomColor() {
        int r = (int) (Math.random() * 255);
        int g = (int) (Math.random() * 255);
        int b = (int) (Math.random() * 255);
        return new Color(r, g, b);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Dimension d = getSize();
        int h = d.height;
        int w = d.width;
        g.setColor(color);
        if (rectangular) {
            g.fillRect(0, 0, w - 1, h - 1);
        } else {
            g.fillOval(0, 0, w - 1, h - 1);
        }
    }

    public boolean isRectangular() {
        return rectangular;
    }

    public void setRectangular(boolean rectangular) {
        this.rectangular = rectangular;
        repaint();
    }

    public static void main(String[] args) {

    }

}
