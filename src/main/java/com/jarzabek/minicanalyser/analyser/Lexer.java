package com.jarzabek.minicanalyser.analyser;

import com.jarzabek.minicanalyser.analyser.exception.UnexpectedCharException;
import com.jarzabek.minicanalyser.analyser.token.Token;
import com.jarzabek.minicanalyser.analyser.token.TokenCategorizer;
import com.jarzabek.minicanalyser.analyser.token.TokenPosition;
import java.util.HashSet;

class Lexer {
    private Scanner scanner;
    private StringBuffer tokenBuffer = new StringBuffer();
    private TokenCategorizer tokenCategorizer = new TokenCategorizer();
    private static final HashSet<Character> splitterCharacters = new HashSet<>();
    private static final HashSet<String> twoSignTokens = new HashSet<>();

    static {
        splitterCharacters.add(' '); splitterCharacters.add('\t');
        splitterCharacters.add(';'); splitterCharacters.add('+');
        splitterCharacters.add('-'); splitterCharacters.add('*');
        splitterCharacters.add('/'); splitterCharacters.add('%');
        splitterCharacters.add('='); splitterCharacters.add('<');
        splitterCharacters.add('>'); splitterCharacters.add('|');
        splitterCharacters.add('&'); splitterCharacters.add('{');
        splitterCharacters.add('('); splitterCharacters.add(')');
        splitterCharacters.add('\'');

        twoSignTokens.add("=="); twoSignTokens.add("<=");
        twoSignTokens.add(">="); twoSignTokens.add("||");
        twoSignTokens.add("&&"); twoSignTokens.add("//");
    }

    Lexer(Scanner scanner) {
        this.scanner = scanner;
    }

    Token getNextToken() throws UnexpectedCharException {
        Token token;
        TokenPosition tokenPosition;
        readWhiteSpaces();
        if(!scanner.hasInputChar())
            return null;
        else {
            tokenPosition = new TokenPosition(scanner.getCurrentLineNumber(), scanner.getCurrentCharNumber());
            getAllCharsUntilSplitter();
            if (isTokenBufferEmpty()) {
                token = buildSplitterTypeToken(tokenPosition);
            } else {
                token = tryBuildToken(tokenPosition);
            }
            return token;
        }
    }

    private void readWhiteSpaces() {
        while(scanner.hasInputChar() && isWhiteSpace(scanner.checkInputChar()))
            scanner.getInputChar();
    }

    private boolean isWhiteSpace(char character) {
        return (character == ' ' || character == '\t');
    }

    private void getAllCharsUntilSplitter() {
        while(scanner.hasInputChar() && !isSplitterCharacter(scanner.checkInputChar()))
            tokenBuffer.append(scanner.getInputChar());
    }

    private boolean isSplitterCharacter(char character) {
        return splitterCharacters.contains(character);
    }

    private boolean isTokenBufferEmpty() {
        return tokenBuffer.length() == 0;
    }

    private Token buildSplitterTypeToken(TokenPosition tokenPosition) throws UnexpectedCharException {
        Token token;
        tokenBuffer.append(scanner.getInputChar()); //splitter character
        if(isTwoSignToken()) {
            tokenBuffer.append(scanner.getInputChar());
            token = tryBuildToken(tokenPosition);
        } else {
            token = tryBuildToken(tokenPosition);
        }
        return token;
    }

    private boolean isTwoSignToken() {
        return twoSignTokens.contains(tokenBuffer.toString() + scanner.checkInputChar());
    }

    private Token tryBuildToken(TokenPosition tokenPosition) throws UnexpectedCharException {
        Token token = tokenCategorizer.createToken(tokenBuffer.toString(), tokenPosition);
        if(token == null)
            throw new UnexpectedCharException(tokenPosition, tokenBuffer.toString());
        clearBuffer();
        return token;
    }

    private void clearBuffer() {
        tokenBuffer.delete(0,tokenBuffer.length());
    }
}
