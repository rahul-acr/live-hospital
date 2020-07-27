package com.social.it.domain;

import com.social.it.DataFeed;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

public class PdfDataFeed implements DataFeed {

    private static final Logger LOG = LoggerFactory
            .getLogger(PdfDataFeed.class);

    private final LocalDate feedDate;
    private final PDDocument document;
    private final String feedName;

    public PdfDataFeed(File file) throws IOException {
        document = PDDocument.load(file);
        feedDate = LocalDate.now();
        feedName = file.getName();
    }

    public PdfDataFeed(URL url, LocalDate feedDate) throws IOException {
        LOG.info("Loading PDF from url {}", url);
        document = PDDocument.load(url.openStream());
        this.feedDate = feedDate;
        feedName = url.toString();
        LOG.info("Loading completed");
    }

    public PdfDataFeed(URL url) throws IOException {
        this(url, LocalDate.now());
    }

    @Override
    public String feedName() {
        return feedName;
    }

    @Override
    public String feedType() {
        return "PDF";
    }

    @Override
    public LocalDate feedDate() {
        return feedDate;
    }

    @Override
    public PDDocument feedData() {
        return document;
    }

    @Override
    public Class<?> dataClass() {
        return PDDocument.class;
    }
}
