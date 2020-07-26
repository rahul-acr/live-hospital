package com.social.it.domain;

public class ExtractionResult {
    private int failedRecords;
    private int matchedRecords;

    public void addMatch() {
        matchedRecords++;
    }

    public void addFail() {
        failedRecords++;
    }

    public int getTotalRecords() {
        return matchedRecords + failedRecords;
    }

    public int getMatchedRecords() {
        return matchedRecords;
    }

    public int failedToMatch() {
        return failedRecords;
    }
}
