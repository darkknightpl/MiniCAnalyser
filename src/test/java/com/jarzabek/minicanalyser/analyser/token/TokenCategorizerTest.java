package com.jarzabek.minicanalyser.analyser.token;

import org.junit.Test;

import static org.junit.Assert.*;

public class TokenCategorizerTest {
    private TokenCategorizer tokenCategorizer = new TokenCategorizer();

    @Test
    public void getTokenShouldReturnCorrectDataTypeToken() {
        String testText = "int";
        Token validToken = new Token(TokenCategory.DATA_TYPES,TokenType.INT,new TokenPosition(0,0));
        Token testToken = tokenCategorizer.createToken(testText,new TokenPosition(0,0));
        assertEquals("Test token and valid token have the same category",validToken.getCategory(),testToken.getCategory());
        assertEquals("Test token and valid token have the same type",validToken.getType(),testToken.getType());
    }

    @Test
    public void getTokenShouldReturnCorrectControlToken() {
        String testText = "if";
        Token validToken = new Token(TokenCategory.CONTROLS,TokenType.IF,new TokenPosition(0,0));
        Token testToken = tokenCategorizer.createToken(testText,new TokenPosition(0,0));
        assertEquals("Test token and valid token have the same category",validToken.getCategory(),testToken.getCategory());
        assertEquals("Test token and valid token have the same type",validToken.getType(),testToken.getType());
    }

    @Test
    public void getTokenShouldReturnCorrectIdentifierToken() {
        String testText = "variableId";
        Token validToken = new Token(TokenCategory.IDENTIFIERS,null,new TokenPosition(0,0),testText);
        Token testToken = tokenCategorizer.createToken(testText,new TokenPosition(0,0));
        assertEquals("Test token and valid token have the same category",validToken.getCategory(),testToken.getCategory());
        assertEquals("Test token and valid token have the same value",validToken.getValue(),testToken.getValue());
    }

    @Test
    public void getTokenShouldReturnNullIfTokenDoesNotExists() {
        String testText = "0asfd1";
        Token testToken = tokenCategorizer.createToken(testText,new TokenPosition(0,0));
        assertNull("Returned value is null", testToken);
    }
}