package com.jarzabek.minicanalyser.analyser;

import java.io.File;

public class Analyser {
    private Scanner scanner = new Scanner();

    public void loadFile(File selectedFile) {
        scanner.loadFile(selectedFile);
    }

    public boolean isFileLoaded() {
        return scanner.isFileLoaded();
    }

    public void runAnalysis() {
    }
}
