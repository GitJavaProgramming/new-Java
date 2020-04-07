package org.pp.java8.patterns.structural.composite;

import java.awt.*;

public class PLeaf extends Component {
    private final String name;

    public PLeaf(String name) {
        this.name = name;
//        requestFocus();
    }

    @Override
    public String toString() {
        return name;
    }
}
