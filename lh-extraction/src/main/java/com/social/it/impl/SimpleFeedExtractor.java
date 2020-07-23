package com.social.it.impl;

import com.social.it.DataFeed;
import com.social.it.FeedExtractor;
import com.social.it.PayloadFilter;
import com.social.it.domain.ExtractionPayLoad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class SimpleFeedExtractor implements FeedExtractor {

    private final RegexBasedExtractor regexBasedExtractor = new RegexBasedExtractor();
    @Autowired
    private PayloadFilter payloadFilter;

    public List<ExtractionPayLoad> extract(DataFeed document) {
        return document.feedData()
                .map(regexBasedExtractor::extract)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(payloadFilter::isValid)
                .collect(Collectors.toList());
    }

    private static class RegexBasedExtractor {

        private static final String REGEX = "\\d*\\s*([a-zA-Z,\\s]+)\\s*[\\-(\\[]?\\s*([a-zA-Z\\s]*)\\s*[)\\]]?\\s*(\\d+)\\s+(\\d+)\\s*";
        private static final Pattern PATTERN = Pattern.compile(REGEX);

        Optional<ExtractionPayLoad> extract(String line) {
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.find()) {
                ExtractionPayLoad extractionPayLoad = ExtractionPayLoad.getBuilder()
                        .hospitalName(matcher.group(1))
                        .additionalInfo(matcher.group(2))
                        .totalBeds(Integer.parseInt(matcher.group(3)))
                        .vacantBeds(Integer.parseInt(matcher.group(4)))
                        .build();
                return Optional.of(extractionPayLoad);
            }
            return Optional.empty();
        }
    }
}
