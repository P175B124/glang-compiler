package edu.ktu.glang;

import org.antlr.v4.runtime.CharStreams;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DummyTest {
    @Test
    void print_works() {
        String program = """
                print(5);
                """;

        assertDoesNotThrow(
                () -> Main.execute(CharStreams.fromString(program))
        );
    }

    @Test
    void print_missing_semicolon_should_throw() {
        String program = """
                print(5)
                """;

        assertThrows(
                IllegalArgumentException.class,
                () -> Main.execute(CharStreams.fromString(program))
        );
    }
}
