package com.openwar.hbmapi.CSVManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {
    private File csvFile;

    public CSVWriter(File csvFile) {
        this.csvFile = csvFile;
    }

    public void writeCSV(String requestId,String action,String playerId, String[] datas) {
        try (FileWriter writer = new FileWriter(csvFile, true)) {

            writer.write(requestId + "," + action + ","+playerId);
            for(int i=0;i<datas.length;i++){
                writer.write(datas[i]+",");
            }
            writer.write("\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
