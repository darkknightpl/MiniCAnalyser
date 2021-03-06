package com.jarzabek.minicanalyser.analyser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class Scanner {
    private String[] lines;
    private int currentLineNumber = 0;
    private int currentCharNumber = 0;

    void loadFile(File selectedFile) {
        try {
            lines = Files.lines(selectedFile.toPath()).toArray(String[]::new);
        } catch (IOException e) {
            e.printStackTrace();
        }
        resetPositionCounters();
    }

    private void resetPositionCounters() {
        currentLineNumber = 0;
        currentCharNumber = 0;
    }

    boolean isFileLoaded() {
        return lines != null;
    }

    char getInputChar() {
        skipEmptyLines();
        char currentInputChar = lines[currentLineNumber].charAt(currentCharNumber);
        updatePositionCounters();
        return currentInputChar;
    }

    char checkInputChar() {
        skipEmptyLines();
        return lines[currentLineNumber].charAt(currentCharNumber);
    }

    private void updatePositionCounters() {
        currentCharNumber++;
        if(currentCharNumber == lines[currentLineNumber].length()) {
            currentCharNumber = 0;
            currentLineNumber++;
        }
    }

    private void skipEmptyLines() {
        while(lines[currentLineNumber].length() == 0)
            currentLineNumber++;
    }

    boolean hasInputChar() {
        return currentLineNumber < lines.length;
    }

    int getCurrentLineNumber() {
        return currentLineNumber;
    }

    int getCurrentCharNumber() {
        return currentCharNumber;
    }
}
