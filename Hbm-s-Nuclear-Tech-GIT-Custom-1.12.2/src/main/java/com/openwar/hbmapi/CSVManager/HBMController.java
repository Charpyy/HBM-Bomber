package com.openwar.hbmapi.CSVManager;

import java.io.File;
import java.util.UUID;

public class HBMController {
    private static CSVWriter csvWriter;
    private static CSVReader csvReader;
    private static File factionFile;
    private static File hbmFile;
    private String actiontype;
    public HBMController(String type) {
        this.actiontype=type;
        if(hbmFile==null){
            hbmFile = new File(new File(".").getAbsolutePath() + "/plugins/OpenWar-Core/faction.csv");
        }
        if(csvWriter==null){
            csvWriter = new CSVWriter(hbmFile);
        }
        if(factionFile==null){
            factionFile = new File(new File(".").getAbsolutePath() + "/plugins/OpenWar-Core/hbm.csv");
        }
        if(csvReader==null){
            csvReader = new CSVReader(factionFile);
        }
    }

    public boolean askRP(String playerId, String missile, int value, int x, int z) {
        UUID requestId=UUID.randomUUID();
        String[] data= {missile, String.valueOf(value), String.valueOf(x), String.valueOf(z)};
        csvWriter.writeCSV(requestId.toString(),actiontype,playerId,data);

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
}
