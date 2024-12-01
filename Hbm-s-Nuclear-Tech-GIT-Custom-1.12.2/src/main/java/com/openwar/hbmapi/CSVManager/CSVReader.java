package com.openwar.hbmapi.CSVManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CSVReader {
    private final File csvFile;
    private long lastModifiedTime;
    private Map<UUID,Response> responseList;

    public CSVReader(File csvFile) {
        this.csvFile = csvFile;
        this.lastModifiedTime = csvFile.lastModified();
        this.responseList=new HashMap<>();
    }

    public void checkForImmediateModification() {
        if (csvFile.exists()) {
            long currentModifiedTime = csvFile.lastModified();
            if (currentModifiedTime > lastModifiedTime) {
                lastModifiedTime = currentModifiedTime;
                readCSV();
            }
        }
    }

    private void readCSV() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(csvFile.getPath()));
            for (String line : lines) {
                String[] datas=line.split(",");
                if(datas.length>=2){
                    if(datas[0]=="bool"){
                        responseList.put(UUID.fromString(datas[1]), new BooleanResponse(datas));
                    }
                }
            }
            clearCSVContents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearCSVContents() {
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean hasResponse(UUID requestId){
        return responseList.containsKey(requestId);
    }
    public Response popResponse(UUID requestId) {
        return responseList.remove(requestId);
    }

    public void resetResponse() {
        responseList.clear();
    }
    public abstract static class Response{}
    public static class BooleanResponse extends Response{
        private final boolean value;
        public BooleanResponse(String[] datas){
            value=datas[3].equals("true");
        }
        public boolean getValue(){
            return value;
        }
    }
}
