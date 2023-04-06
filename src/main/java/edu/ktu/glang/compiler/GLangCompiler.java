package edu.ktu.glang.compiler;

import edu.ktu.glang.GLangLexer;
import edu.ktu.glang.GLangParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class GLangCompiler {
    public static void main(String[] args) throws IOException {
        String sourceFilename = args[0];
        String outputFilename = args[1];

        byte[] bytecode = compileFile(sourceFilename);

        buildJarFile(outputFilename, bytecode);
    }

    public static byte[] compileFile(String filename) throws IOException {
        return compile(CharStreams.fromFileName(filename));
    }

    public static byte[] compileCode(String sourceCode) throws IOException {
        return compile(CharStreams.fromString(sourceCode));
    }

    private static byte[] compile(CharStream stream) throws IOException {
        GLangLexer lexer = new GLangLexer(stream);
        GLangParser parser = new GLangParser(new CommonTokenStream(lexer));
        GLangParser.ProgramContext programContext = parser.program();

        SymbolTable symbolTable = new SymbolTable();
        CompilerVisitor compilerVisitor = new CompilerVisitor(symbolTable);
        return (byte[]) compilerVisitor.visit(programContext);
    }

    public static void buildJarFile(String outputFilename, byte[] bytecode) throws IOException {
        Manifest manifest = new Manifest();
        Attributes mainAttributes = manifest.getMainAttributes();
        mainAttributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
        mainAttributes.put(Attributes.Name.MAIN_CLASS, "compiled.Main");

        // Save the compiled bytecode to a JAR file
        try (FileOutputStream fos = new FileOutputStream(outputFilename);
             JarOutputStream jos = new JarOutputStream(fos, manifest)) {
            JarEntry mainClassEntry = new JarEntry("compiled/Main.class");
            jos.putNextEntry(mainClassEntry);
            jos.write(bytecode);
            jos.closeEntry();
        }
    }
}
