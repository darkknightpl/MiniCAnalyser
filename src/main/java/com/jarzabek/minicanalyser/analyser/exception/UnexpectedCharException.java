package com.jarzabek.minicanalyser.analyser.exception;

import com.jarzabek.minicanalyser.analyser.token.TokenPosition;

public class UnexpectedCharException extends Exception {
    private TokenPosition position;
    private String contentFound;

    public UnexpectedCharException(TokenPosition position, String contentFound){
        this.position = position;
        this.contentFound = contentFound;
    }

    @Override
    public String getMessage() {
        return "Unexpected character. Invalid content: \'" + contentFound + "\' starting at " + position.toString();
    }
}
