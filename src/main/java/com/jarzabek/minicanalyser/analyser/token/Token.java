package com.jarzabek.minicanalyser.analyser.token;

public class Token {
    private TokenCategory tokenCategory;
    private TokenType tokenType;
    private TokenPosition tokenPosition;
    private String value = null;

    public Token(TokenCategory tokenCategory, TokenType tokenType, TokenPosition tokenPosition, String value) {
        this.tokenCategory = tokenCategory;
        this.tokenType = tokenType;
        this.tokenPosition = tokenPosition;
        this.value = value;
    }

    public Token(TokenCategory tokenCategory, TokenType tokenType, TokenPosition tokenPosition) {
        this.tokenCategory = tokenCategory;
        this.tokenType = tokenType;
        this.tokenPosition = tokenPosition;
    }

    public TokenCategory getCategory() {
        return tokenCategory;
    }

    public TokenType getType() {
        return tokenType;
    }

    public String getValue() {
        return value;
    }

    public TokenPosition getPosition() {
        return tokenPosition;
    }
}
