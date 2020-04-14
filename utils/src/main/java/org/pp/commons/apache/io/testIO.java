package org.pp.commons.apache.io;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

/**
 * 参考
 * http://codingdict.com/article/8775
 */
public class testIO {

    @Test
    public void test(){
        System.out.println(FileUtils.getTempDirectoryPath());
        System.out.println(FileUtils.getUserDirectoryPath());
//        FileUtils.copyFile();
//        FileUtils.write();
//        FileUtils.readFileToByteArray()
//        FileUtils.readFileToString()

//        IOUtils.copy()
    }
}
