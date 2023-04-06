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

        super.visitProgram(ctx);

        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitMaxs(0, 0); // Automatically computed by ASM
        methodVisitor.visitEnd();

        classWriter.visitEnd();
        return classWriter.toByteArray();
    }

    @Override
    public Object visitPrintStatement(GLangParser.PrintStatementContext ctx) {
        methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        visit(ctx.expression());
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
        return null;
    }

    @Override
    public Integer visitIntExpression(GLangParser.IntExpressionContext ctx) {
        int value = Integer.parseInt(ctx.INT().getText());
        methodVisitor.visitLdcInsn(value);
        return value;
    }

    @Override
    public Object visitAssignment(GLangParser.AssignmentContext ctx) {
        String id = ctx.ID().getText();
        int varIndex;
        if (!symbolTable.contains(id)) {
            varIndex = symbolTable.size();
            symbolTable.put(id, varIndex);
        } else {
            varIndex = symbolTable.get(id);
        }
        visit(ctx.expression());
        methodVisitor.visitVarInsn(ISTORE, varIndex);
        return null;
    }

    @Override
    public Integer visitIdExpression(GLangParser.IdExpressionContext ctx) {
        String id = ctx.ID().getText();
        int varIndex = symbolTable.get(id);
        methodVisitor.visitVarInsn(ILOAD, varIndex);
        return null;
    }

    @Override
    public Object visitIntAddOpExpression(GLangParser.IntAddOpExpressionContext ctx) {
        visit(ctx.expression(0));
        visit(ctx.expression(1));
        String op = ctx.intAddOp().getText();
        if (op.equals("+")) {
            methodVisitor.visitInsn(IADD);
        } else if (op.equals("-")) {
            methodVisitor.visitInsn(ISUB);
        }
        return null;
    }

    @Override
    public Object visitIntMultiOpExpression(GLangParser.IntMultiOpExpressionContext ctx) {
        visit(ctx.expression(0));
        visit(ctx.expression(1));
        String op = ctx.intMultiOp().getText();
        if (op.equals("*")) {
            methodVisitor.visitInsn(IMUL);
        } else if (op.equals("/")) {
            methodVisitor.visitInsn(IDIV);
        } else if (op.equals("%")) {
            methodVisitor.visitInsn(IREM);
        }
        return null;
    }
}
