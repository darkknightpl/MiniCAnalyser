package com.jarzabek.minicanalyser.analyser;

import com.jarzabek.minicanalyser.analyser.exception.UnexpectedCharException;
import com.jarzabek.minicanalyser.analyser.token.Token;
import org.junit.Ignore;
import org.junit.Test;
import java.io.File;

@Ignore
public class LexerPrintOutTest {
    private Scanner scanner = new Scanner();
    private Lexer lexer = new Lexer(scanner);
    private static final String[] testSourceFilenames = {
        "SingleSwitchCaseProgram.txt",
        "SingleWhileProgram.txt",
        "SingleIfProgram.txt",
        "SingleForProgram.txt"
    };

    @Test
    public void shouldPrintOutTokenListsForSourceFiles() throws UnexpectedCharException {
        File testFile;
        for(String sourceFilename : testSourceFilenames) {
            System.out.println(sourceFilename);
            testFile = new File(getClass().getResource(sourceFilename).getFile());
            scanner.loadFile(testFile);
            Token testToken = lexer.getNextToken();
            while(testToken != null) {
                System.out.println("Type: <" + testToken.getType()
                        + "> \tCategory: <" + testToken.getCategory()
                        + "> \tPosition: <" + testToken.getPosition().getOneBasedIndexingPosition()
                        + "> \tValue: <" + testToken.getValue() + ">");
                testToken = lexer.getNextToken();
            }
            System.out.println("=============================================================================\n");
        }
    }
}