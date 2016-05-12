package com.jarzabek.minicanalyser.analyser;

import org.junit.Test;
import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ScannerTest {
    private Scanner scanner = new Scanner();
    private static final String testSourceFileName = "SingleIfProgram.txt";
    private String[] testProgramLines = {
            "int main()",
            "{",
            "    int x;",
            "    if(x == 0)",
            "    {",
            "        x = x + 1;",
            "    }",
            "    return 0;",
            "}"
    };

    @Test
    public void scannerShouldReturnCorrectContent() {
        File testFile = new File(getClass().getResource(testSourceFileName).getFile());
        scanner.loadFile(testFile);
        for(int testCurrentLineNumber = 0; testCurrentLineNumber < testProgramLines.length; ++testCurrentLineNumber) {
            assertTrue("Line numbers are equals", testCurrentLineNumber == scanner.getCurrentLineNumber());
            int testCurrentLineLength = testProgramLines[testCurrentLineNumber].length();
            for(int testCurrentCharNumber = 0; testCurrentCharNumber < testCurrentLineLength; ++testCurrentCharNumber) {
                assertTrue("Char numbers are the same", testCurrentCharNumber == scanner.getCurrentCharNumber());
                char testChar = testProgramLines[testCurrentLineNumber].charAt(testCurrentCharNumber);
                assertTrue("Chars at the same position are equals", testChar == scanner.getNextChar());
            }
        }
        assertFalse("After reading last char there is no more characters", scanner.hasNextChar());
    }

}