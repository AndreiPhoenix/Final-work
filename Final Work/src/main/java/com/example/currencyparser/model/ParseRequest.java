package com.example.currencyparser.model;

import java.util.List;

public class ParseRequest {
    private List<String> urls;
    private boolean saveToDatabase;

    public ParseRequest() {}

    public ParseRequest(List<String> urls, boolean saveToDatabase) {
        this.urls = urls;
        this.saveToDatabase = saveToDatabase;
    }

    public List<String> getUrls() { return urls; }
    public void setUrls(List<String> urls) { this.urls = urls; }

    public boolean isSaveToDatabase() { return saveToDatabase; }
    public void setSaveToDatabase(boolean saveToDatabase) { this.saveToDatabase = saveToDatabase; }
}