package com.jarzabek.minicanalyser.analyser;

import com.jarzabek.minicanalyser.analyser.exception.UnexpectedCharException;
import com.jarzabek.minicanalyser.analyser.token.Token;
import com.jarzabek.minicanalyser.analyser.token.TokenCategory;
import com.jarzabek.minicanalyser.analyser.token.TokenPosition;
import com.jarzabek.minicanalyser.analyser.token.TokenType;
import org.junit.Test;
import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class LexerTest {
    private Scanner scanner = new Scanner();
    private Lexer lexer = new Lexer(scanner);
    private static final String testValidSourceFilename = "SingleIfProgram.txt";
    private static final String testEmptySourceFilename = "EmptyProgram.txt";
    private static final String testInvalidSourceFilename = "InvalidProgram.txt";
    private static final Token[] validTokenList = {
            new Token(TokenCategory.DATA_TYPES, TokenType.INT, new TokenPosition(0,0)),
            new Token(TokenCategory.OTHERS, TokenType.MAIN, new TokenPosition(0,4)),
            new Token(TokenCategory.SPECIAL_SYMBOLS, TokenType.LEFT_PARENTHESIS, new TokenPosition(0,8)),
            new Token(TokenCategory.SPECIAL_SYMBOLS, TokenType.RIGHT_PARENTHESIS, new TokenPosition(0,9)),
            new Token(TokenCategory.SPECIAL_SYMBOLS, TokenType.LEFT_BRACE, new TokenPosition(1,0)),
            new Token(TokenCategory.DATA_TYPES, TokenType.INT, new TokenPosition(2,4)),
            new Token(TokenCategory.IDENTIFIERS, TokenType.IDENTIFIER, new TokenPosition(2,8)),
            new Token(TokenCategory.SPECIAL_SYMBOLS, TokenType.SEMICOLON, new TokenPosition(2,9)),
            new Token(TokenCategory.CONTROLS, TokenType.IF, new TokenPosition(3,4)),
            new Token(TokenCategory.SPECIAL_SYMBOLS, TokenType.LEFT_PARENTHESIS, new TokenPosition(3,6)),
            new Token(TokenCategory.IDENTIFIERS, TokenType.IDENTIFIER, new TokenPosition(3,7)),
            new Token(TokenCategory.RELATION_OPERATORS, TokenType.EQUAL, new TokenPosition(3,9)),
            new Token(TokenCategory.NUMERIC_CONSTANTS, TokenType.NUMERIC_CONSTANT, new TokenPosition(3,12)),
            new Token(TokenCategory.SPECIAL_SYMBOLS, TokenType.RIGHT_PARENTHESIS, new TokenPosition(3,13)),
            new Token(TokenCategory.SPECIAL_SYMBOLS, TokenType.LEFT_BRACE, new TokenPosition(4,4)),
            new Token(TokenCategory.IDENTIFIERS, TokenType.IDENTIFIER, new TokenPosition(5,8)),
            new Token(TokenCategory.SPECIAL_SYMBOLS, TokenType.ASSIGN, new TokenPosition(5,10)),
            new Token(TokenCategory.IDENTIFIERS, TokenType.IDENTIFIER, new TokenPosition(5,12)),
            new Token(TokenCategory.MATH_OPERATORS, TokenType.PLUS, new TokenPosition(5,14)),
            new Token(TokenCategory.NUMERIC_CONSTANTS, TokenType.NUMERIC_CONSTANT, new TokenPosition(5,16)),
            new Token(TokenCategory.SPECIAL_SYMBOLS, TokenType.SEMICOLON, new TokenPosition(5,17)),
            new Token(TokenCategory.SPECIAL_SYMBOLS, TokenType.RIGHT_BRACE, new TokenPosition(6,4)),
            new Token(TokenCategory.OTHERS, TokenType.RETURN, new TokenPosition(7,4)),
            new Token(TokenCategory.NUMERIC_CONSTANTS, TokenType.NUMERIC_CONSTANT, new TokenPosition(7,11)),
            new Token(TokenCategory.SPECIAL_SYMBOLS, TokenType.SEMICOLON, new TokenPosition(7,12)),
            new Token(TokenCategory.SPECIAL_SYMBOLS, TokenType.RIGHT_BRACE, new TokenPosition(8,0))
    };

    @Test
    public void createTokenListShouldCreateCorrectTokenListIfInputIsValid() throws UnexpectedCharException {
        File testFile = new File(getClass().getResource(testValidSourceFilename).getFile());
        scanner.loadFile(testFile);
        lexer.createTokenList();
        ArrayList<Token> testTokenList = lexer.getTokenList();
        int tokenNumber = 0;
        Token validToken, testToken;
        while(tokenNumber < testTokenList.size()) {
            validToken = validTokenList[tokenNumber];
            testToken = testTokenList.get(tokenNumber);
            assertEquals("Test token and valid token have the same category",validToken.getCategory(),testToken.getCategory());
            assertEquals("Test token and valid token have the same type",validToken.getType(),testToken.getType());
            tokenNumber++;
        }
    }

    @Test
    public void getTokenShouldReturnCorrectTokenIfInputIsValid() throws UnexpectedCharException {
        File testFile = new File(getClass().getResource(testValidSourceFilename).getFile());
        scanner.loadFile(testFile);
        Token validToken = new Token(TokenCategory.DATA_TYPES, TokenType.INT,new TokenPosition(0,0));
        Token testToken = lexer.getNextToken();
        assertEquals("Test token and valid token have the same category",validToken.getCategory(),testToken.getCategory());
        assertEquals("Test token and valid token have the same type",validToken.getType(),testToken.getType());
    }

    @Test
    public void getTokenShouldReturnCorrectTokenListIfInputIsValid() throws UnexpectedCharException {
        File testFile = new File(getClass().getResource(testValidSourceFilename).getFile());
        scanner.loadFile(testFile);
        int validTokenNumber = 0;
        Token validToken;
        Token testToken = lexer.getNextToken();
        while(testToken != null) {
            validToken = validTokenList[validTokenNumber];
            assertEquals("Test token and valid token have the same category",validToken.getCategory(),testToken.getCategory());
            assertEquals("Test token and valid token have the same type",validToken.getType(),testToken.getType());
            validTokenNumber++;
            testToken = lexer.getNextToken();
        }
    }

    @Test
    public void getTokenShouldReturnNullIfInputIsEmpty() throws UnexpectedCharException {
        File testFile = new File(getClass().getResource(testEmptySourceFilename).getFile());
        scanner.loadFile(testFile);
        Token token = lexer.getNextToken();
        assertNull("getToken() method returned null",token);
    }

    @Test
    public void getTokenShouldThrowExceptionIfInputIsInvalid() {
        String expectedExceptionMessage = "Unexpected character. Invalid content: '1intblabla' starting at [1,5]";
        File testFile = new File(getClass().getResource(testInvalidSourceFilename).getFile());
        scanner.loadFile(testFile);
        try {
            lexer.getNextToken();
            lexer.getNextToken();
        } catch (UnexpectedCharException e) {
            assertEquals("getToken() have thrown exception with correct message",expectedExceptionMessage,e.getMessage());
        }
    }
}