package org.pp.asm.core.visitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Arrays;

public class MultiClassAdapter extends ClassVisitor {
    protected ClassVisitor[] cvs;

    public MultiClassAdapter(ClassVisitor... cvs) {
        super(Opcodes.ASM7);
        this.cvs = cvs;
    }

    @Override
    public void visit(int version, int access, String name,
                      String signature, String superName, String[] interfaces) {
        Arrays.stream(cvs).forEach(cv -> cv.visit(version, access, name, signature, superName, interfaces));
    }
}
