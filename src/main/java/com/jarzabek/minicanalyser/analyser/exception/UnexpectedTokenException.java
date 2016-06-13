package com.jarzabek.minicanalyser.analyser.exception;

import com.jarzabek.minicanalyser.analyser.token.Token;
import com.jarzabek.minicanalyser.analyser.token.TokenType;

public class UnexpectedTokenException extends Exception {
    private Token tokenFound;
    private TokenType tokenTypeExpected;

    public UnexpectedTokenException(Token tokenFound, TokenType tokenTypeExpected) {
        this.tokenFound = tokenFound;
        this.tokenTypeExpected = tokenTypeExpected;
    }

    @Override
    public String getMessage() {
        return "Unexpected token at: " + tokenFound.getPosition().getOneBasedIndexingPosition()
                + ". Found: \'" + tokenFound.getType().getValue() + "\'(Type: " + tokenFound.getType() +
                "), expected: \'" + tokenTypeExpected.getValue() + "\'" + "(Type: " + tokenTypeExpected +")";
    }
}
