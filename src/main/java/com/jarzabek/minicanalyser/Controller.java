package com.jarzabek.minicanalyser;

import com.jarzabek.minicanalyser.analyser.Analyser;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import java.io.File;

public class Controller {
    private Analyser analyser = new Analyser();

    public void loadFile() {
        File selectedFile = openFile();
        if(selectedFile != null)
            analyser.loadFile(selectedFile);
    }

    public void runAnalysis() {
        if(analyser.isFileLoaded())
            analyser.runAnalysis();
        else
            displayNoFileLoadedError();
    }

    private File openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
                .addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                        new FileChooser.ExtensionFilter("Source files", "*.c"));
        return fileChooser.showOpenDialog(null);
    }

    private void displayNoFileLoadedError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("No file Error");
        alert.setHeaderText("There is no file loaded. Load file.");
        alert.setContentText("Use Menu File -> Load file...");
        alert.showAndWait();
    }


}
