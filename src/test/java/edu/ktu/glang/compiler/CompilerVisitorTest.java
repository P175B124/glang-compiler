package edu.ktu.glang.compiler;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;

class CompilerVisitorTest {

    @Test
    public void testOutput() throws Exception {
        String sourceCode = """
                            x=1;
                            """;
        String expected = """
                          Hello, world!
                          """;

        String actual = TestUtils.executeCode(sourceCode); //NOTE code is ignored for now, implementation is empty

        // Split the strings into lists of lines, it helps to handle line separator differences.
        List<String> expectedLines = Arrays.asList(expected.split("\\R"));
        List<String> actualLines = Arrays.asList(actual.split("\\R"));

        assertLinesMatch(expectedLines, actualLines);
    }
}