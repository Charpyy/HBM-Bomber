package com.openwar.hbmapi.CSVManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CSVReader {
    private final File csvFile;
    private long lastModifiedTime;
    private boolean hasResponse;  // Ajout pour vérifier la réponse
    private boolean responseValue; // Pour stocker la valeur de la réponse

    public CSVReader(File csvFile) {
        this.csvFile = csvFile;
        this.lastModifiedTime = csvFile.lastModified();
        this.hasResponse = false; // Initialisé à false
    }

    public boolean checkForImmediateModification() {
        if (csvFile.exists()) {
            long currentModifiedTime = csvFile.lastModified();
            if (currentModifiedTime > lastModifiedTime) {
                lastModifiedTime = currentModifiedTime;
                return readCSV();
            }
        }
        return false;
    }

    private boolean readCSV() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(csvFile.getPath()));
            for (String line : lines) {
                if (line.contains("true")) {
                    clearCSVContents();
                    hasResponse = true;
                    responseValue = true;
                    System.out.println("HBM A BIEN RECU LA REPONSE TRUE");
                    return true;
                } else if (line.contains("false")) {
                    clearCSVContents();
                    hasResponse = true;
                    responseValue = false;
                    System.out.println("HBM A BIEN RECU LA REPONSE FALSE");
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void clearCSVContents() {
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isFalseResponse() {
        return hasResponse && !responseValue;
    }

    public void resetResponse() {
        hasResponse = false;
    }
}
