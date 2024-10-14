package com.openwar.hbmapi.CSVManager;

import java.io.File;

public class HBMController {
    private CSVWriter csvWriter;
    private CSVReader csvReader;

    public HBMController() {
        this.csvWriter = new CSVWriter();
        File factionFile = new File(new File(".").getAbsolutePath() + "/plugins/OpenWar-Core/hbm.csv");
        this.csvReader = new CSVReader(factionFile);
    }

    public boolean execute(String uniqueId, int value, int x, int z) {
        csvWriter.writeCSV(uniqueId, value, x, z);
        boolean result = false;

        long startTime = System.currentTimeMillis();
        long timeout = 5000;

        while (System.currentTimeMillis() - startTime < timeout) {
            result = csvReader.checkForImmediateModification();
            if (result || csvReader.isFalseResponse()) {
                break;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        csvReader.resetResponse();
        return result;
    }
}
