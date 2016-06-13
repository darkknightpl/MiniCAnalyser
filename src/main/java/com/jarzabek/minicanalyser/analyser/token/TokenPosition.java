package com.jarzabek.minicanalyser.analyser.token;

public class TokenPosition {
    private int lineNumber; //Line and char number are stored in zero-based numeric convention
    private int charNumber;

    public TokenPosition(int lineNumber, int charNumber) {
        this.lineNumber = lineNumber;
        this.charNumber = charNumber;
    }

    @Override
    public String toString() {
        return "[" + lineNumber + "," + charNumber + "]";
    }

    public String getOneBasedIndexingPosition() {
        return "[" + (lineNumber + 1) + "," + (charNumber + 1) + "]";
    }
}
