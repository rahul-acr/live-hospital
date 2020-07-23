package com.social.it.domain;

import com.social.it.DataFeed;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Stream;

public class PdfDataFeed implements DataFeed {
    final static String LINE_SEPARATOR = System.getProperty("line.separator");
    private final LocalDate feedDate;
    private final String[] lines;

    public PdfDataFeed(File file) throws IOException {
        final PDDocument document = PDDocument.load(file);
        final PDFTextStripper pdfTextStripper = new PDFTextStripper();
        final String extractedString = pdfTextStripper.getText(document);
        lines = extractedString.split(LINE_SEPARATOR);
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
    public Stream<String> feedData() {
        return Arrays.stream(lines);
    }
}
