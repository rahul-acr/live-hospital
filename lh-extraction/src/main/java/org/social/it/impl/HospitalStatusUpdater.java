package org.social.it.impl;

import org.social.it.DataFeed;
import org.social.it.FeedExtractor;
import org.social.it.FeedProcessor;
import org.social.it.HospitalMatcher;
import org.social.it.domain.ExtractionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.it.entity.HospitalEntity;
import org.social.it.repository.HospitalRepository;
import org.social.it.repository.UnknownHospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class HospitalStatusUpdater implements FeedProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(HospitalStatusUpdater.class);

    @Autowired
    private FeedExtractor feedExtractor;

    @Autowired
    private HospitalMatcher hospitalMatcher;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private UnknownHospitalRepository unknownHospitalRepository;

    @Override
    public ExtractionResult process(DataFeed dataFeed) {
        // Load the data in memory rather querying as data volume is very low
        List<HospitalEntity> hospitals = hospitalRepository.findAll();
        final ExtractionResult extractionResult = new ExtractionResult(dataFeed);
        feedExtractor.extract(dataFeed).forEach((epd -> {
            Optional<HospitalEntity> matchedHospitalOpt = hospitalMatcher.match(epd, hospitals);
            if (matchedHospitalOpt.isPresent()) {
                HospitalEntity matchedHospital = matchedHospitalOpt.get();
                LOG.info("Updating usage of {} as per {}", matchedHospital, epd);
                matchedHospital.updateUsage(epd.vacantBeds(), epd.totalBeds(), dataFeed.feedDate());
                hospitalRepository.save(matchedHospitalOpt.get());
                extractionResult.addMatch();
            } else {
                LOG.warn("No match found for {}", epd);
                unknownHospitalRepository.save(epd.generateHospitalEntity());
                extractionResult.addFail();
            }
        }));
        return extractionResult;
    }

}
