package com.openwar.hbmapi.CSVManager;

import java.io.File;
import java.util.Map;
import java.util.UUID;

public class HBMController {
    private CSVWriter csvWriter;
    private CSVReader csvReader;
    private File factionFile;
    private File hbmFile;
    private Map<UUID,Object> waitingrequests;
    public static HBMController generalController;
    public HBMController() {
        hbmFile = new File(new File(".").getAbsolutePath() + "/plugins/OpenWar-Core/faction.csv");
        csvWriter = new CSVWriter(hbmFile);
        factionFile = new File(new File(".").getAbsolutePath() + "/plugins/OpenWar-Core/hbm.csv");
        csvReader = new CSVReader(factionFile);
    }
    public boolean askLvl(String playerId, int level){
        UUID requestId=UUID.randomUUID();
        String[] data= {String.valueOf(level)};
        csvWriter.writeCSV(requestId.toString(),"checkLvl",playerId,data);

        long startTime = System.currentTimeMillis();
        long timeout = 5000;

        while (System.currentTimeMillis() - startTime < timeout) {
            csvReader.checkForImmediateModification();
            if (csvReader.hasResponse(requestId)) {
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        return ((CSVReader.BooleanResponse)csvReader.popResponse(requestId)).getValue();
    }
    public boolean askRP(String playerId, String missile, int value, int x, int z) {
        UUID requestId=UUID.randomUUID();
        String[] data= {missile, String.valueOf(value), String.valueOf(x), String.valueOf(z)};
        csvWriter.writeCSV(requestId.toString(),"checkRP",playerId,data);

        long startTime = System.currentTimeMillis();
        long timeout = 5000;

        while (System.currentTimeMillis() - startTime < timeout) {
            csvReader.checkForImmediateModification();
            if (csvReader.hasResponse(requestId)) {
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        return ((CSVReader.BooleanResponse)csvReader.popResponse(requestId)).getValue();
    }
    public void checkResponses(){

    }
}
