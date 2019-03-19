package com.jhonatansouza.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
public class SwapiPlanetsDTO {

    private int length;
    private int page;
    private LocalDateTime timeStamp;
    private List results;

    public SwapiPlanetsDTO() {
        this.timeStamp = LocalDateTime.now(ZoneId.systemDefault());
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getTimeStamp() {
        return timeStamp.toString();
    }


    public List getResults() {
        return results;
    }

    public void setResults(List results) {
        this.results = results;
    }
}
