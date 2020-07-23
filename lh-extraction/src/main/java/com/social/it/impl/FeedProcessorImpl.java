package com.social.it.impl;

import com.social.it.DataFeed;
import com.social.it.FeedExtractor;
import com.social.it.FeedProcessor;
import com.social.it.domain.ExtractionPayLoad;
import com.social.it.exception.DataExtractionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.it.entity.HospitalEntity;
import org.social.it.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class FeedProcessorImpl implements FeedProcessor {

    private static final Logger LOG = LoggerFactory
            .getLogger(FeedProcessorImpl.class);

    @Autowired
    private FeedExtractor feedExtractor;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Override
    public void process(DataFeed dataFeed) {
        // Load the data in memory rather querying as data volume is very low
        List<HospitalEntity> hospitals = hospitalRepository.findAll();

        feedExtractor.extract(dataFeed).forEach((epd -> {
            Optional<HospitalEntity> matchedHospital = match(epd, hospitals);
            matchedHospital.ifPresent((h) -> {
                LOG.info("Updating usage of {} as per {}", h, epd);
                h.updateUsage(epd.vacantBeds(), epd.totalBeds());
            });
        }));
    }

    private Optional<HospitalEntity> match(ExtractionPayLoad extractionPayLoad, List<HospitalEntity> hospitals) {
        String[] keywords = extractionPayLoad.keywords();
        Map<HospitalEntity, Integer> matchMap = new HashMap<>();

        int highestMatch = 0;
        for (HospitalEntity hospital : hospitals) {
            int match = 0;
            for (String keyword : keywords) {
                if (hospital.name().toLowerCase().contains(keyword))
                    match++;
            }
            if (match > 0) matchMap.put(hospital, match);
            highestMatch = Math.max(match, highestMatch);
        }

        //TODO new hospital found ?
        if(highestMatch == 0) return Optional.empty();

        // If there are multiple highestMatch then there would be ambiguity
        int highestMatchCount = 0;
        HospitalEntity matchedHospital = null;
        for (HospitalEntity hospital : hospitals) {
            if(matchMap.get(hospital) == highestMatch){
                highestMatchCount++;
                matchedHospital = hospital;
            }
        }
        if(highestMatchCount > 1)
            throw new DataExtractionException("Ambiguous data found "+extractionPayLoad.hospitalName());

        return Optional.ofNullable(matchedHospital);
    }
}
