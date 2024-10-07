package com.openwar.hbmapi.CSVManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {
    private File csvHBM;

    public CSVWriter() {
        this.csvHBM = new File(new File(".").getAbsolutePath() + "/plugins/OpenWar-Core/faction.csv");
    }

    public void writeCSV(String uniqueId, int value) {
        try (FileWriter writer = new FileWriter(csvHBM, true)) {
            writer.write(uniqueId + " " + value + "\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
