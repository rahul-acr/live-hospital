package com.social.it.impl;

import com.social.it.DataFeed;
import com.social.it.FeedExtractor;
import com.social.it.FeedProcessor;
import com.social.it.HospitalMatcher;
import com.social.it.domain.ExtractionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.it.entity.HospitalEntity;
import org.social.it.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class HospitalStatusUpdater implements FeedProcessor {

    private static final Logger LOG = LoggerFactory
            .getLogger(HospitalStatusUpdater.class);

    @Autowired
    private FeedExtractor feedExtractor;

    @Autowired
    private HospitalMatcher hospitalMatcher;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Override
    public ExtractionResult process(DataFeed dataFeed) {
        // Load the data in memory rather querying as data volume is very low
        List<HospitalEntity> hospitals = hospitalRepository.findAll();
        final ExtractionResult extractionResult = new ExtractionResult();
        feedExtractor.extract(dataFeed).forEach((epd -> {
            Optional<HospitalEntity> matchedHospital = hospitalMatcher.match(epd, hospitals);
            if (matchedHospital.isPresent()) {
                LOG.info("Updating usage of {} as per {}", matchedHospital.get(), epd);
                matchedHospital.get().usage().updateUsage(epd.vacantBeds(), epd.totalBeds());
                extractionResult.addMatch();
            } else {
                LOG.warn("No match found for {}", epd);
                extractionResult.addFail();
            }
        }));
        return extractionResult;
    }

}
