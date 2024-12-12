package com.openwar.hbmapi.CSVManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HBMController {
    private final CSVWriter csvWriter;
    private final CSVReader csvReader;
    private final Map<UUID,Action> waitingrequests;
    private final Map<UUID,Long> expirationtime;
    public static HBMController generalController=null;
    public HBMController() {
        File hbmFile = new File(new File(".").getAbsolutePath() + "/plugins/OpenWar-Core/faction.csv");
        csvWriter = new CSVWriter(hbmFile);
        File factionFile = new File(new File(".").getAbsolutePath() + "/plugins/OpenWar-Core/hbm.csv");
        csvReader = new CSVReader(factionFile);
        waitingrequests=new ConcurrentHashMap<>();
        expirationtime=new ConcurrentHashMap<>();
    }
    public void askLvl(String playerId, int level, Action action){
        UUID requestId=UUID.randomUUID();
        String[] data= {String.valueOf(level)};
        waitingrequests.put(requestId,action);
        expirationtime.put(requestId,System.currentTimeMillis()+5000);
        csvWriter.writeCSV(requestId.toString(),"checkLvl",playerId,data);
/*
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
        return csvReader.hasResponse(requestId)?(CSVReader.BooleanResponse)csvReader.popResponse(requestId):null;*/
    }
    public void askRP(String playerId, String missile, int value, int x, int z, Action action) {
        UUID requestId=UUID.randomUUID();
        String[] data= {missile, String.valueOf(value), String.valueOf(x), String.valueOf(z)};
        System.out.println("[MISSILE] "+playerId+" tried to launch "+missile+" to " + x + " / " + z +" request id : "+requestId);
        waitingrequests.put(requestId,action);
        expirationtime.put(requestId,System.currentTimeMillis()+5000);
        csvWriter.writeCSV(requestId.toString(),"checkRP",playerId,data);
/*
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
        return csvReader.hasResponse(requestId)?(CSVReader.BooleanResponse)csvReader.popResponse(requestId):null;
        */
    }
    public void checkResponses(){
        if(!waitingrequests.isEmpty()){
            csvReader.checkForImmediateModification();
            for(UUID request : waitingrequests.keySet()){
                if(csvReader.hasResponse(request)){
                    System.out.println("Request "+request.toString()+" response got !");
                    waitingrequests.get(request).execute(csvReader.popResponse(request));
                    waitingrequests.remove(request);
                    expirationtime.remove(request);
                } else if (expirationtime.get(request)>System.currentTimeMillis()) {
                    waitingrequests.remove(request);
                    expirationtime.remove(request);
                    System.out.println("Request "+request.toString()+" expired !");
                }
            }
        }
    }
    public static void createControllerIfNotExist(){
        if(HBMController.generalController==null){
            HBMController.generalController = new HBMController();
        }
    }
    public static abstract class Action<T extends CSVReader.Response>{
        public abstract void execute(T response);
    }
}
