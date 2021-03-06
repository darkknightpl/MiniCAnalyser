package com.jarzabek.minicanalyser.analyser.token;

import java.util.HashMap;
import java.util.regex.Pattern;
import static com.jarzabek.minicanalyser.analyser.token.TokenCategory.*;
import static com.jarzabek.minicanalyser.analyser.token.TokenType.*;

public class TokenCategorizer {
    private final static HashMap<String, TokenType> TOKEN_TYPES = new HashMap<>();
    private final static HashMap<TokenType, TokenCategory> TOKEN_CATEGORIES = new HashMap<>();
    private final static String IDENTIFIER_PATTERN = "[a-zA-Z]([a-zA-Z0-9])*";
    private final static String CHAR_CONSTANT_PATTERN = "'[a-zA-Z]'";
    private final static String NUMERIC_CONSTANT_PATTERN = "([0-9]|[1-9]([0-9])*)|([0-9]|[1-9]([0-9])*.([0-9])*)";

    static {
        TOKEN_TYPES.put(BOOL.getValue(), BOOL);
        TOKEN_TYPES.put(CHAR.getValue(), CHAR);
        TOKEN_TYPES.put(DOUBLE.getValue(), DOUBLE);
        TOKEN_TYPES.put(FLOAT.getValue(), FLOAT);
        TOKEN_TYPES.put(INT.getValue(), INT);
        TOKEN_TYPES.put(LONG.getValue(), LONG);
        TOKEN_TYPES.put(SHORT.getValue(), SHORT);
        TOKEN_TYPES.put(SIGNED.getValue(), SIGNED);
        TOKEN_TYPES.put(UNSIGNED.getValue(), UNSIGNED);
        TOKEN_TYPES.put(CONST.getValue(), CONST);
        TOKEN_TYPES.put(CASE.getValue(), CASE);
        TOKEN_TYPES.put(DEFAULT.getValue(), DEFAULT);
        TOKEN_TYPES.put(ELSE.getValue(), ELSE);
        TOKEN_TYPES.put(FOR.getValue(), FOR);
        TOKEN_TYPES.put(IF.getValue(), IF);
        TOKEN_TYPES.put(WHILE.getValue(), WHILE);
        TOKEN_TYPES.put(BREAK.getValue(), BREAK);
        TOKEN_TYPES.put(SWITCH.getValue(), SWITCH);
        TOKEN_TYPES.put(RETURN.getValue(), RETURN);
        TOKEN_TYPES.put(TRUE.getValue(), TRUE);
        TOKEN_TYPES.put(FALSE.getValue(), FALSE);
        TOKEN_TYPES.put(MAIN.getValue(), MAIN);
        TOKEN_TYPES.put(PLUS.getValue(), PLUS);
        TOKEN_TYPES.put(MINUS.getValue(), MINUS);
        TOKEN_TYPES.put(MULTIPLY.getValue(), MULTIPLY);
        TOKEN_TYPES.put(DIVIDE.getValue(), DIVIDE);
        TOKEN_TYPES.put(MODULO.getValue(), MODULO);
        TOKEN_TYPES.put(AND.getValue(), AND);
        TOKEN_TYPES.put(OR.getValue(), OR);
        TOKEN_TYPES.put(EQUAL.getValue(), EQUAL);
        TOKEN_TYPES.put(SMALLER_THEN.getValue(), SMALLER_THEN);
        TOKEN_TYPES.put(SMALLER_OR_EQUAL.getValue(), SMALLER_OR_EQUAL);
        TOKEN_TYPES.put(GREATER_THEN.getValue(), GREATER_THEN);
        TOKEN_TYPES.put(GREATER_OR_EQUAL.getValue(), GREATER_OR_EQUAL);
        TOKEN_TYPES.put(COLON.getValue(), COLON);
        TOKEN_TYPES.put(ASSIGN.getValue(), ASSIGN);
        TOKEN_TYPES.put(SEMICOLON.getValue(), SEMICOLON);
        TOKEN_TYPES.put(COMMENT.getValue(), COMMENT);
        TOKEN_TYPES.put(LEFT_BRACE.getValue(), LEFT_BRACE);
        TOKEN_TYPES.put(RIGHT_BRACE.getValue(), RIGHT_BRACE);
        TOKEN_TYPES.put(LEFT_PARENTHESIS.getValue(), LEFT_PARENTHESIS);
        TOKEN_TYPES.put(RIGHT_PARENTHESIS.getValue(), RIGHT_PARENTHESIS);

        TOKEN_CATEGORIES.put(BOOL, DATA_TYPES);
        TOKEN_CATEGORIES.put(CHAR, DATA_TYPES);
        TOKEN_CATEGORIES.put(DOUBLE, DATA_TYPES);
        TOKEN_CATEGORIES.put(FLOAT, DATA_TYPES);
        TOKEN_CATEGORIES.put(INT, DATA_TYPES);
        TOKEN_CATEGORIES.put(LONG, DATA_TYPES);
        TOKEN_CATEGORIES.put(SHORT, DATA_TYPES);
        TOKEN_CATEGORIES.put(SIGNED, DATA_TYPES);
        TOKEN_CATEGORIES.put(UNSIGNED, DATA_TYPES);
        TOKEN_CATEGORIES.put(CONST, DATA_TYPES);
        TOKEN_CATEGORIES.put(CASE, CONTROLS);
        TOKEN_CATEGORIES.put(DEFAULT, CONTROLS);
        TOKEN_CATEGORIES.put(ELSE, CONTROLS);
        TOKEN_CATEGORIES.put(FOR, CONTROLS);
        TOKEN_CATEGORIES.put(IF, CONTROLS);
        TOKEN_CATEGORIES.put(WHILE, CONTROLS);
        TOKEN_CATEGORIES.put(BREAK, CONTROLS);
        TOKEN_CATEGORIES.put(SWITCH, CONTROLS);
        TOKEN_CATEGORIES.put(RETURN, OTHERS);
        TOKEN_CATEGORIES.put(TRUE, OTHERS);
        TOKEN_CATEGORIES.put(FALSE, OTHERS);
        TOKEN_CATEGORIES.put(MAIN, OTHERS);
        TOKEN_CATEGORIES.put(PLUS, MATH_OPERATORS);
        TOKEN_CATEGORIES.put(MINUS, MATH_OPERATORS);
        TOKEN_CATEGORIES.put(MULTIPLY, MATH_OPERATORS);
        TOKEN_CATEGORIES.put(DIVIDE, MATH_OPERATORS);
        TOKEN_CATEGORIES.put(MODULO, MATH_OPERATORS);
        TOKEN_CATEGORIES.put(AND, BOOLEAN_OPERATORS);
        TOKEN_CATEGORIES.put(OR, BOOLEAN_OPERATORS);
        TOKEN_CATEGORIES.put(EQUAL, RELATION_OPERATORS);
        TOKEN_CATEGORIES.put(SMALLER_OR_EQUAL, RELATION_OPERATORS);
        TOKEN_CATEGORIES.put(SMALLER_THEN, RELATION_OPERATORS);
        TOKEN_CATEGORIES.put(GREATER_THEN, RELATION_OPERATORS);
        TOKEN_CATEGORIES.put(GREATER_OR_EQUAL, RELATION_OPERATORS);
        TOKEN_CATEGORIES.put(COLON, SPECIAL_SYMBOLS);
        TOKEN_CATEGORIES.put(ASSIGN, SPECIAL_SYMBOLS);
        TOKEN_CATEGORIES.put(SEMICOLON, SPECIAL_SYMBOLS);
        TOKEN_CATEGORIES.put(COMMENT, SPECIAL_SYMBOLS);
        TOKEN_CATEGORIES.put(LEFT_BRACE, SPECIAL_SYMBOLS);
        TOKEN_CATEGORIES.put(RIGHT_BRACE, SPECIAL_SYMBOLS);
        TOKEN_CATEGORIES.put(LEFT_PARENTHESIS, SPECIAL_SYMBOLS);
        TOKEN_CATEGORIES.put(RIGHT_PARENTHESIS, SPECIAL_SYMBOLS);
    }

    public Token createToken(String characters, TokenPosition tokenPosition) {
        TokenType tokenType;
        TokenCategory tokenCategory;
        String tokenValue = null;
        if (TOKEN_TYPES.containsKey(characters)) {
            tokenType = TOKEN_TYPES.get(characters);
            tokenCategory = TOKEN_CATEGORIES.get(tokenType);
        } else if(matchesPattern(IDENTIFIER_PATTERN,characters)) {
            tokenType = IDENTIFIER;
            tokenCategory = IDENTIFIERS;
            tokenValue = characters;
        } else if(matchesPattern(CHAR_CONSTANT_PATTERN,characters)) {
            tokenType = CHAR_CONSTANT;
            tokenCategory = CHAR_CONSTANTS;
            tokenValue = characters;
        } else if(matchesPattern(NUMERIC_CONSTANT_PATTERN,characters)) {
            tokenType = NUMERIC_CONSTANT;
            tokenCategory = NUMERIC_CONSTANTS;
            tokenValue = characters;
        } else {
            return null;
        }
        return new Token(tokenCategory,tokenType,tokenPosition,tokenValue);
    }

    private boolean matchesPattern(String pattern, String text) {
        return Pattern.matches(pattern, text);
    }
}



