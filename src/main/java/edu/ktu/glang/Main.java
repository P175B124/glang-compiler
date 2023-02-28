package edu.ktu.glang;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Main {
    public static void main(String[] args) {
        try {
            execute(CharStreams.fromFileName(args[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void execute(CharStream stream) {
        GLangLexer lexer = new GLangLexer(stream);
        GLangParser parser = new GLangParser(new CommonTokenStream(lexer));
        parser.setBuildParseTree(true);

        // Create a new instance of your error listener implementation
        DummyErrorListener errorListener = new DummyErrorListener();
        parser.addErrorListener(errorListener);

        ParseTree tree = parser.program();

        DummyVisitor visitor = new DummyVisitor();
        visitor.visit(tree);
    }
}
