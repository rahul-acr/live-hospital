package org.social.it.domain;

import org.social.it.DataFeed;

import java.time.LocalDate;

public class ExtractionResult {
    private final DataFeed dataFeed;
    private int failedRecords;
    private int matchedRecords;

    public ExtractionResult(DataFeed dataFeed) {
        this.dataFeed = dataFeed;
    }

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

    public int getFailedRecords() {
        return failedRecords;
    }

    public String getFeedName() {
        return dataFeed.feedName();
    }

    public LocalDate getFeedDate() {
        return dataFeed.feedDate();
    }
}
