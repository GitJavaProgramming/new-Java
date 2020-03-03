package org.pp.asm.core;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.pp.asm.core.visitor.ChangeAccessAdapter;
import org.pp.asm.core.visitor.ClassPrintVisitor;
import org.pp.asm.core.visitor.RemovingClassesVisitor;

import java.io.*;

public class ClassesPrintTest {

    /**
     * 移除方法
     */
    @Test
    public void testChangeClass2() throws IOException {
        ClassReader cr = new ClassReader("org.pp.asm.core.Task");
        ClassWriter cw = new ClassWriter(0);
        ClassVisitor cv = new RemovingClassesVisitor(cw);
        cr.accept(cv, 0);
        byte[] toByte = cw.toByteArray();// byt 和toByte其实是相同的数组
        // 输出到class文件
        File file = new File("D:\\IdeaProjects\\corejava\\target\\classes\\org\\pp\\asm\\core\\Task.class");
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(toByte);
        fout.close();
    }

    /**
     * 修改字节码信息
     * 测试并查看class文件
     */
    @Test
    public void changeClass() throws IOException {
        String filePathName = "D:\\IdeaProjects\\corejava\\target\\classes\\org\\pp\\asm\\core\\ChildClass.class";
        File file = new File(filePathName);
        InputStream input = new FileInputStream(file);
        // 构造一个byte数组
        byte[] byt = new byte[input.available()];
        input.read(byt);
        ClassWriter cw = new ClassWriter(0);
//        ClassVisitor cv = new ClassVisitor(Opcodes.ASM7){};
        //  改变class的访问修饰
          ClassVisitor cv = new ChangeAccessAdapter(cw);
        ClassReader cr = new ClassReader(byt);
        cr.accept(cv, 0);
        byte[] toByte = cw.toByteArray();// byt 和toByte其实是相同的数组
        // 输出到class文件
        File tofile = new File(filePathName);
        FileOutputStream fout = new FileOutputStream(tofile);
        fout.write(toByte);
        fout.close();
    }

    /**
     * 生成字节码
     */
    private static byte[] gen() {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(Opcodes.V1_8/*JDK版本*/, Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT,
                "org/pp/asm/core/ChildClass", null, "java/lang/Object", new String[]{"java/lang/Cloneable"});

        cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, "zero", "I", null, new Integer(0))
                .visitEnd();

        cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT, "compareTo", "(Ljava/lang/Object;)I", null, null)
                .visitEnd();
        cw.visitEnd();
        return cw.toByteArray();
    }

    /**
     * 读取/解析class字节码
     */
    @Test
    public void testClassRead() {
        try {
            ClassReader cr = new ClassReader("org.pp.asm.core.Task");
            ClassPrintVisitor cp = new ClassPrintVisitor(); // vistor
            /**
             * parsingOptions
             * the options to use to parse this class. One or more of {@link #SKIP_CODE}
             * , {@link #SKIP_DEBUG}, {@link #SKIP_FRAMES} or {@link #EXPAND_FRAMES}.
             */
            cr.accept(cp, ClassReader.SKIP_CODE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
