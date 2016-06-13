package com.jarzabek.minicanalyser.analyser;

import com.jarzabek.minicanalyser.analyser.exception.UnexpectedTokenException;
import com.jarzabek.minicanalyser.analyser.token.Token;
import com.jarzabek.minicanalyser.analyser.token.TokenCategory;
import com.jarzabek.minicanalyser.analyser.token.TokenType;

import java.util.ArrayList;

class Parser {
    private ArrayList<Token> tokenList;
    private int currentTokenNumber;
    private Token currentToken;

    void loadTokenList(ArrayList<Token> tokenList) {
        this.tokenList = tokenList;
        currentTokenNumber = 0;
        currentToken = tokenList.get(currentTokenNumber);
    }

    void parseProgram() throws UnexpectedTokenException {
        TokenType[] expectedTokensTypes = {
                TokenType.INT, TokenType.MAIN, TokenType.LEFT_PARENTHESIS,
                TokenType.RIGHT_PARENTHESIS, TokenType.LEFT_BRACE
        };
        compareInputTokensTypesWithTypes(expectedTokensTypes);
        while(currentToken.getType() != TokenType.RETURN)
            parseStatement(TokenType.RETURN);
        compareInputTokenTypeWithType(TokenType.RETURN);
        checkIfInputTokenTypeIsOneOfTypes(new TokenType[]{TokenType.IDENTIFIER,TokenType.NUMERIC_CONSTANT});
        compareInputTokenTypeWithType(TokenType.SEMICOLON);
        compareInputTokenTypeWithType(TokenType.RIGHT_BRACE);
    }

    private void parseExpression(TokenType expectedTokenTypeIfFailed) throws UnexpectedTokenException {
        if (currentToken.getCategory() == TokenCategory.DATA_TYPES)
            parseDeclarationExpression();
        else if (currentToken.getType() == TokenType.IDENTIFIER) {
            acceptToken();
            if (currentToken.getType() == TokenType.ASSIGN) {
                parseAssignmentExpression();
                compareInputTokenTypeWithType(TokenType.SEMICOLON);
            }
            else
                parseTwoOperandExpression();
        }
        else if (currentToken.getType() == TokenType.NUMERIC_CONSTANT) {
            acceptToken();
            parseTwoOperandExpression();
        }
        else
            throw new UnexpectedTokenException(currentToken, expectedTokenTypeIfFailed);
    }

    private void parseAssignmentExpression() throws UnexpectedTokenException {
        acceptToken();  //accept assign token
        TokenType[] tokenTypes = {TokenType.IDENTIFIER, TokenType.NUMERIC_CONSTANT, TokenType.CHAR_CONSTANT};
        checkIfInputTokenTypeIsOneOfTypes(tokenTypes);
        TokenCategory currentTokenCategory = currentToken.getCategory();
        if (currentTokenCategory == TokenCategory.MATH_OPERATORS
                || currentTokenCategory == TokenCategory.RELATION_OPERATORS
                || currentTokenCategory == TokenCategory.BOOLEAN_OPERATORS)
            parseTwoOperandExpression();
    }

    private void parseTwoOperandExpression() throws UnexpectedTokenException {
        if (currentToken.getCategory() == TokenCategory.MATH_OPERATORS)
            parseArithmeticExpression();
        else if (currentToken.getCategory() == TokenCategory.BOOLEAN_OPERATORS)
            parseLogicalExpression();
        else if (currentToken.getCategory() == TokenCategory.RELATION_OPERATORS)
            parseRelationalExpression();
    }

    private void parseRelationalExpression() throws UnexpectedTokenException {
        acceptToken();  //accept token of relational operators category
        TokenType[] tokenTypes = {TokenType.IDENTIFIER, TokenType.NUMERIC_CONSTANT};
        checkIfInputTokenTypeIsOneOfTypes(tokenTypes);
    }

    private void parseLogicalExpression() throws UnexpectedTokenException {
        acceptToken();  //accept token of logical operators category
        TokenType[] tokenTypes = {TokenType.IDENTIFIER, TokenType.NUMERIC_CONSTANT};
        checkIfInputTokenTypeIsOneOfTypes(tokenTypes);
    }

    private void parseArithmeticExpression() throws UnexpectedTokenException {
        acceptToken();  //accept token of math operators category
        TokenType[] tokenTypes = {TokenType.IDENTIFIER, TokenType.NUMERIC_CONSTANT, TokenType.CHAR_CONSTANT};
        checkIfInputTokenTypeIsOneOfTypes(tokenTypes);
    }

    private void parseDeclarationExpression() throws UnexpectedTokenException {
        acceptToken();  //accept token of data types category
        compareInputTokenTypeWithType(TokenType.IDENTIFIER);
        if (currentToken.getType() == TokenType.ASSIGN)
            parseAssignmentExpression();
        compareInputTokenTypeWithType(TokenType.SEMICOLON);
    }

    private void parseStatement(TokenType expectedTokenTypeIfFailed) throws UnexpectedTokenException {
        TokenType currentTokenType = currentToken.getType();
        if(currentTokenType == TokenType.IF || currentTokenType == TokenType.SWITCH)
            parseSelectionStatement();
        else if(currentTokenType == TokenType.WHILE || currentTokenType == TokenType.FOR)
            parseIterationStatement();
        else
            parseExpression(expectedTokenTypeIfFailed);
    }

    private void parseIterationStatement() throws UnexpectedTokenException {
        TokenType currentTokenType = currentToken.getType();
        if(currentTokenType == TokenType.FOR)
            parseForStatement();
        else
            parseWhileStatement();
    }

    private void parseWhileStatement() throws UnexpectedTokenException {
        acceptToken();  //accept while token
        compareInputTokenTypeWithType(TokenType.LEFT_PARENTHESIS);
        compareInputTokenTypeWithType(TokenType.IDENTIFIER);
        if (currentToken.getCategory() != TokenCategory.RELATION_OPERATORS)
            throw new UnexpectedTokenException(currentToken, TokenType.GREATER_THEN); // just example of expected token
        parseRelationalExpression();
        compareInputTokenTypeWithType(TokenType.RIGHT_PARENTHESIS);
        compareInputTokenTypeWithType(TokenType.LEFT_BRACE);
        while (currentToken.getType() != TokenType.RIGHT_BRACE)
            parseStatement(TokenType.RIGHT_BRACE);
        compareInputTokenTypeWithType(TokenType.RIGHT_BRACE);
    }

    private void parseForStatement() throws UnexpectedTokenException {
        acceptToken();  //accept for token
        TokenType[] expectedTokensTypes = {
                TokenType.LEFT_PARENTHESIS, TokenType.IDENTIFIER, TokenType.ASSIGN,
                TokenType.NUMERIC_CONSTANT, TokenType.SEMICOLON
        };
        compareInputTokensTypesWithTypes(expectedTokensTypes);
        compareInputTokenTypeWithType(TokenType.IDENTIFIER);
        if (currentToken.getCategory() != TokenCategory.RELATION_OPERATORS)
            throw new UnexpectedTokenException(currentToken, TokenType.GREATER_THEN); // just example of expected token
        parseRelationalExpression();
        compareInputTokenTypeWithType(TokenType.SEMICOLON);
        compareInputTokenTypeWithType(TokenType.IDENTIFIER);
        if (currentToken.getType() != TokenType.ASSIGN)
            throw new UnexpectedTokenException(currentToken, TokenType.ASSIGN); // just example of expected token
        parseAssignmentExpression();
        compareInputTokenTypeWithType(TokenType.RIGHT_PARENTHESIS);
        compareInputTokenTypeWithType(TokenType.LEFT_BRACE);
        while (currentToken.getType() != TokenType.RIGHT_BRACE)
            parseStatement(TokenType.RIGHT_BRACE);
        compareInputTokenTypeWithType(TokenType.RIGHT_BRACE);
    }

    private void parseSelectionStatement() throws UnexpectedTokenException {
        TokenType currentTokenType = currentToken.getType();
        if(currentTokenType == TokenType.IF)
            parseIfStatement();
        else
            parseSwitchStatement();
    }

    private void parseIfStatement() throws UnexpectedTokenException {
        acceptToken(); //accept if token
        compareInputTokenTypeWithType(TokenType.LEFT_PARENTHESIS);
        parseExpression(TokenType.RIGHT_PARENTHESIS);
        compareInputTokenTypeWithType(TokenType.RIGHT_PARENTHESIS);
        compareInputTokenTypeWithType(TokenType.LEFT_BRACE);
        while (currentToken.getType() != TokenType.RIGHT_BRACE)
            parseStatement(TokenType.RIGHT_BRACE);
        compareInputTokenTypeWithType(TokenType.RIGHT_BRACE);
        if (currentToken.getType() == TokenType.ELSE) {
            parseElseStatement();
        }
    }

    private void parseElseStatement() throws UnexpectedTokenException {
        acceptToken(); //accept else token
        compareInputTokenTypeWithType(TokenType.LEFT_BRACE);
        while (currentToken.getType() != TokenType.RIGHT_BRACE)
            parseStatement(TokenType.RIGHT_BRACE);
        compareInputTokenTypeWithType(TokenType.RIGHT_BRACE);
    }

    private void parseSwitchStatement() throws UnexpectedTokenException {
        acceptToken();  //accept switch token
        compareInputTokenTypeWithType(TokenType.LEFT_PARENTHESIS);
        compareInputTokenTypeWithType(TokenType.IDENTIFIER);
        compareInputTokenTypeWithType(TokenType.RIGHT_PARENTHESIS);
        compareInputTokenTypeWithType(TokenType.LEFT_BRACE);
        while (currentToken.getType() != TokenType.RIGHT_BRACE) {
            parseCaseStatement();
            if(currentToken.getType() == TokenType.DEFAULT) {
                parseDefaultStatement();
                break;
            }
        }
        compareInputTokenTypeWithType(TokenType.RIGHT_BRACE);
    }

    private void parseDefaultStatement() throws UnexpectedTokenException {
        acceptToken();  //accept default token
        compareInputTokenTypeWithType(TokenType.COLON);
        while (currentToken.getType() != TokenType.RIGHT_BRACE) { //expected in parseSwitchStatement method
            parseStatement(TokenType.RIGHT_BRACE);
        }
    }

    private void parseCaseStatement() throws UnexpectedTokenException {
        compareInputTokenTypeWithType(TokenType.CASE);
        checkIfInputTokenTypeIsOneOfTypes(new TokenType[]{TokenType.NUMERIC_CONSTANT, TokenType.CHAR_CONSTANT});
        compareInputTokenTypeWithType(TokenType.COLON);
        while (currentToken.getType() != TokenType.BREAK)
            parseStatement(TokenType.BREAK);
        compareInputTokenTypeWithType(TokenType.BREAK);
        compareInputTokenTypeWithType(TokenType.SEMICOLON);
    }

    private void compareInputTokenTypeWithType(TokenType tokenType) throws UnexpectedTokenException {
        if (currentToken.getType() != tokenType)
            throw new UnexpectedTokenException(currentToken, tokenType);
        acceptToken();
    }

    private void compareInputTokensTypesWithTypes(TokenType[] tokenTypes) throws UnexpectedTokenException {
        for (TokenType tokenType : tokenTypes) {
            if (currentToken.getType() != tokenType)
                throw new UnexpectedTokenException(currentToken, tokenType);
            acceptToken();
        }
    }

    private void checkIfInputTokenTypeIsOneOfTypes(TokenType[] tokenTypes) throws UnexpectedTokenException {
        for (TokenType tokenType : tokenTypes) {
            if (currentToken.getType() == tokenType) {  //  accept first appearance
                acceptToken();
                return;
            }
        }
        throw new UnexpectedTokenException(currentToken, tokenTypes[0]);
    }

    // version with token list received from Lexer, can be easily replaced with usage of getNextToken() method (Lexer class)
    private void acceptToken() {
        currentTokenNumber++;
        if (currentTokenNumber < tokenList.size())
            currentToken = tokenList.get(currentTokenNumber);
    }
}
