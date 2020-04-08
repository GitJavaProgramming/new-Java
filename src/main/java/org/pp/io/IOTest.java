package org.pp.io;

import java.io.File;

public class IOTest {

    public static void main(String[] args) {
        File f = new File(""); // 文件结构/行为抽象 最后还是StringBuffer  file IO
        if(f.exists()) {
            System.out.println(f.getName());
        }
    }
}
