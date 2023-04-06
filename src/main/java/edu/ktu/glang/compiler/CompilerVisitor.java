package edu.ktu.glang.compiler;

import edu.ktu.glang.GLangBaseVisitor;
import edu.ktu.glang.GLangParser;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class CompilerVisitor extends GLangBaseVisitor<Object> implements Opcodes {

    private final SymbolTable symbolTable;

    private final ClassWriter classWriter;
    private MethodVisitor methodVisitor;

    public CompilerVisitor(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
        this.classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
    }

    @Override
    public Object visitProgram(GLangParser.ProgramContext ctx) {
        classWriter.visit(V1_8, ACC_PUBLIC + ACC_SUPER, "compiled/Main", null, "java/lang/Object", null);

        methodVisitor = classWriter.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        methodVisitor.visitCode();

        // TODO this will print "Hello, world!"
        methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        methodVisitor.visitLdcInsn("Hello, world!");
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        // END
        super.visitProgram(ctx);
        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitMaxs(0, 0); // Automatically computed by ASM
        methodVisitor.visitEnd();

        classWriter.visitEnd();
        return classWriter.toByteArray();
    }
}
