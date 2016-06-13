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
    DEFAULT("default"),
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
    IDENTIFIER("identifier_type"),
    CHAR_CONSTANT("char_const_type"),
    NUMERIC_CONSTANT("numeric_const_type");

    private String value;

    TokenType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
