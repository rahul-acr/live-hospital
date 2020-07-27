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

    // Sometimes other cell data can enter the number cell. Sanitize.
    private static int extractNumber(String data) {
        return Integer.parseInt(data.replaceAll("\\D", ""));
    }

    public List<ExtractionPayLoad> extract(DataFeed dataFeed) {
        assert dataFeed.dataClass() == PDDocument.class;
        PDDocument document = (PDDocument) dataFeed.feedData();
        ObjectExtractor objectExtractor = new ObjectExtractor(document);
        SpreadsheetExtractionAlgorithm extractionAlgorithm = new SpreadsheetExtractionAlgorithm();


        final List<ExtractionPayLoad> payLoads = new ArrayList<>();

        for (int pageNo = 1; pageNo <= document.getNumberOfPages(); pageNo++) {
            List<Table> tables = extractionAlgorithm.extract(objectExtractor.extract(pageNo));

            Table table = null;
            for (Table aTable : tables) {
                if (aTable.getRowCount() > 0) {
                    if (table != null) {
                        throw new DataExtractionException("Multiple tables found in page");
                    } else {
                        table = aTable;
                    }
                }
            }

            DataExtractionException.throwIf(table == null, "No valid tables found in page");

            LOG.info("{} - total rows found ", table.getRowCount());
            for (int row = 0; row < table.getRowCount(); row++) {
                try {
                    read(dataFeed, table, row).ifPresent(payLoad -> {
                        payLoads.add(payLoad);
                        LOG.info("Added payload {}", payLoad);
                    });
                } catch (Exception e) {
                    LOG.error("Failed to extract data ! row {} page {} of {}", row, pageNo, dataFeed.feedName());
                    LOG.error("Extraction failed for a row. Got a {} as {}", e.getClass(), e.getMessage());
                }
            }
        }
        LOG.info("Extracted {} payloads", payLoads.size());
        return payLoads;
    }

    private Optional<ExtractionPayLoad> read(DataFeed dataFeed, Table table, int rowNum) {
        String[] nameInfo = nameAdditionalInfoExtractor.extract(getValueFor(table, rowNum, 2));
        // Ignore blank lines
        if (nameInfo[0].trim().isEmpty()) return Optional.empty();
        ExtractionPayLoad payLoad = ExtractionPayLoad.getBuilder(dataFeed)
                .withHospitalName(nameInfo[0])
                .withAdditionalInfo(nameInfo[1])
                .withTotalBeds(extractNumber(getValueFor(table, rowNum, 3)))
                .withVacantBeds(extractNumber(getValueFor(table, rowNum, 4)))
                .build();
        return Optional.of(payLoad);
    }

    private static class NameAdditionalInfoExtractor {

        private static final String REGEX = "([a-zA-Z,&\\s.]+)-?[\\-(\\[{]?([a-zA-Z\\s.]*)[})\\]]?";
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
