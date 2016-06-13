package com.jarzabek.minicanalyser.analyser;

import com.jarzabek.minicanalyser.analyser.exception.UnexpectedCharException;
import com.jarzabek.minicanalyser.analyser.exception.UnexpectedTokenException;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ParserTest {
    private Scanner scanner = new Scanner();
    private Lexer lexer = new Lexer(scanner);
    private Parser parser = new Parser();
    private static final String testValidSourceFilename = "SingleIfElseProgram.txt";
    private static final String testSyntacticallyInvalidSourceFilename = "SyntacticallyInvalidProgram.txt";


    @Test
    public void parseProgramShouldParseInputWithoutExceptionIfInputIsValid()
            throws UnexpectedCharException, UnexpectedTokenException {
        File testFile = new File(getClass().getResource(testValidSourceFilename).getFile());
        scanner.loadFile(testFile);
        lexer.createTokenList();
        parser.loadTokenList(lexer.getTokenList());
        parser.parseProgram();
    }

    @Test
    public void parseProgramShouldThrowExceptionIfInputIsInvalid() throws UnexpectedCharException {
        String expectedExceptionMessage = "Unexpected token at: [7,15]. Found: '='(Type: ASSIGN), expected: ';'(Type: SEMICOLON)";
        File testFile = new File(getClass().getResource(testSyntacticallyInvalidSourceFilename).getFile());
        scanner.loadFile(testFile);
        lexer.createTokenList();
        parser.loadTokenList(lexer.getTokenList());
        try {
            parser.parseProgram();
        } catch (UnexpectedTokenException e) {
            assertEquals("parseProgram() have thrown exception with correct message",expectedExceptionMessage,e.getMessage());
        }
    }
}