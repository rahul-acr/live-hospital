package com.social.it.impl;

import com.social.it.DataFeed;
import com.social.it.FeedExtractor;
import com.social.it.domain.ExtractionPayLoad;
import com.social.it.exception.DataExtractionException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import technology.tabula.ObjectExtractor;
import technology.tabula.Table;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TabulaBasedFeedExtractor implements FeedExtractor {

    private static final Logger LOG = LoggerFactory.getLogger(TabulaBasedFeedExtractor.class);

    private final NameAdditionalInfoExtractor nameAdditionalInfoExtractor = new NameAdditionalInfoExtractor();

    private static String getValueFor(Table table, int row, int col) {
        return table.getCell(row, col).getText();
    }

    public List<ExtractionPayLoad> extract(DataFeed dataFeed) {
        assert dataFeed.dataClass() == PDDocument.class;
        PDDocument document = (PDDocument) dataFeed.feedData();
        ObjectExtractor objectExtractor = new ObjectExtractor(document);
        SpreadsheetExtractionAlgorithm extractionAlgorithm = new SpreadsheetExtractionAlgorithm();

        // Currently there is only one page.
        DataExtractionException.throwIf(document.getNumberOfPages() != 1, "Expected only one page");
        List<Table> tables = extractionAlgorithm.extract(objectExtractor.extract(1));
        DataExtractionException.throwIf(tables.size() != 1, "Expected only one table");
        Table table = tables.get(0);

        LOG.info("{} - 4 total rows found ", table.getRowCount());
        final List<ExtractionPayLoad> payLoads = new ArrayList<>();
        for (int row = 4; row < table.getRowCount(); row++) {
            read(table, row).ifPresent(payLoad -> {
                payLoads.add(payLoad);
                LOG.info("Added payload {}", payLoad);
            });
        }

        LOG.info("Extracted {} payloads", payLoads.size());
        return payLoads;
    }

    private Optional<ExtractionPayLoad> read(Table table, int rowNum) {
        String[] nameInfo = nameAdditionalInfoExtractor.extract(getValueFor(table, rowNum, 2));
        // Ignore blank lines
        if (nameInfo[0].trim().isEmpty()) return Optional.empty();
        ExtractionPayLoad payLoad = ExtractionPayLoad.getBuilder()
                .hospitalName(nameInfo[0])
                .additionalInfo(nameInfo[1])
                .totalBeds(Integer.parseInt(getValueFor(table, rowNum, 3)))
                .vacantBeds(Integer.parseInt(getValueFor(table, rowNum, 4)))
                .build();
        return Optional.of(payLoad);
    }

    private static class NameAdditionalInfoExtractor {

        private static final String REGEX = "([a-zA-Z,\\s.]+)[\\-(\\[{]?([a-zA-Z\\s.]*)[})\\]]?";
        private static final Pattern PATTERN = Pattern.compile(REGEX);

        String[] extract(String line) {
            LOG.info("Extracting name & additional info '{}'", line);
            line = line.replace("#", "");
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.find()) {
                return new String[]{matcher.group(1), matcher.group(2)};
            }
            return new String[]{line, ""};
        }
    }
}
