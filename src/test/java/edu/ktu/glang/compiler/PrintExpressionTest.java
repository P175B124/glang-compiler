package edu.ktu.glang.compiler;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;

public class PrintExpressionTest {

    @Test
    void print_add_expression() throws Exception {
        String program = """
                         print(2+3);
                         """;

        String expected = """
                          5
                          """;

        String actual = TestUtils.executeCode(program);

        List<String> expectedLines = Arrays.asList(expected.split("\\R"));
        List<String> actualLines = Arrays.asList(actual.split("\\R"));

        assertLinesMatch(expectedLines, actualLines);
    }

    @Test
    void print_multiplication_expression() throws Exception {
        String program = """
                         print(2*3);
                         """;

        String expected = """
                          6
                          """;

        String actual = TestUtils.executeCode(program);

        List<String> expectedLines = Arrays.asList(expected.split("\\R"));
        List<String> actualLines = Arrays.asList(actual.split("\\R"));

        assertLinesMatch(expectedLines, actualLines);
    }

    @Test
    void print_complex1_expression() throws Exception {
        String program = """
                         print(1+2*3);
                         """;

        String expected = """
                          7
                          """;

        String actual = TestUtils.executeCode(program);

        List<String> expectedLines = Arrays.asList(expected.split("\\R"));
        List<String> actualLines = Arrays.asList(actual.split("\\R"));

        assertLinesMatch(expectedLines, actualLines);
    }

    @Test
    void print_complex2_expression() throws Exception {
        String program = """
                         print(8/2*(2+2));
                         """;

        String expected = """
                          16
                          """;

        String actual = TestUtils.executeCode(program);

        List<String> expectedLines = Arrays.asList(expected.split("\\R"));
        List<String> actualLines = Arrays.asList(actual.split("\\R"));

        assertLinesMatch(expectedLines, actualLines);
    }
}