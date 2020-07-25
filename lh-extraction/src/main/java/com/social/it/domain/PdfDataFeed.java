package com.social.it.domain;

import com.social.it.DataFeed;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class PdfDataFeed implements DataFeed {
    private final LocalDate feedDate;
    private final PDDocument document;

    public PdfDataFeed(File file) throws IOException {
        document = PDDocument.load(file);
        feedDate = LocalDate.now();
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
