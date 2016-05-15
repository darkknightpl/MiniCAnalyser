package com.jarzabek.minicanalyser.analyser.token;

public enum TokenType {
    BOOL("bool"),
    CHAR("char"),
    DOUBLE("double"),
    FLOAT("float"),
    INT("int"),
    LONG("long"),
    SHORT("short"),
    SIGNED("signed"),
    UNSIGNED("unsigned"),
    CONST("const"),
    CASE("case"),
    ELSE("else"),
    FOR("for"),
    IF("if"),
    SWITCH("switch"),
    WHILE("while"),
    BREAK("break"),
    RETURN("return"),
    TRUE("true"),
    FALSE("false"),
    MAIN("main"),
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    MODULO("%"),
    EQUAL("=="),
    GREATER_THEN(">"),
    GREATER_OR_EQUAL(">="),
    SMALLER_THEN("<"),
    SMALLER_OR_EQUAL("<="),
    OR("||"),
    AND("&&"),
    ASSIGN("="),
    COLON(":"),
    SEMICOLON(";"),
    COMMENT("//"),
    LEFT_BRACE("{"),
    RIGHT_BRACE("}"),
    LEFT_PARENTHESIS("("),
    RIGHT_PARENTHESIS(")"),
    IDENTIFIER(""),
    CHAR_CONSTANT(""),
    NUMERIC_CONSTANT("");

    private String type;

    TokenType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
