package com.social.it.impl;

import com.social.it.HospitalMatcher;
import com.social.it.WordDistanceCalculator;
import com.social.it.domain.ExtractionPayLoad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.it.entity.HospitalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SpellErrorTolerantHospitalMatcher implements HospitalMatcher {

    private static final Logger LOG = LoggerFactory.getLogger(SpellErrorTolerantHospitalMatcher.class);

    private final static int SPELL_FAULT_TOLERANCE_PERCENTAGE = 10;

    @Autowired
    private WordDistanceCalculator wordDistanceCalculator;

    private static String trimToNull(String data){
        if(data.trim().equals("")) return null;
        return data;
    }

    @Override
    public Optional<HospitalEntity> match(ExtractionPayLoad extractionPayLoad, List<HospitalEntity> hospitals) {
        HospitalEntity matchedHospital = null;
        int matchCount = 0;
        for (HospitalEntity hospital : hospitals) {
            if(!areHospitalsComparable(extractionPayLoad, hospital)) continue;

            // WARNING : Not considering additional info as it varies greatly feed to feed.
            // Also assuming one hospital does not have more than one specialization.
            int dist = 100 * wordDistanceCalculator.calculateDistance(extractionPayLoad.hospitalName(), hospital.name());
            if (dist / hospital.name().length() <= SPELL_FAULT_TOLERANCE_PERCENTAGE) {
                matchCount++;
                matchedHospital = hospital;
            }
        }

        if (matchCount > 1) {
            LOG.warn("Ambiguous data found for {} ", extractionPayLoad);
            return Optional.empty();
        }

        return Optional.ofNullable(matchedHospital);
    }

    // If a hospital has special info (is specialized), don't compare it with regular hospitals
    private boolean areHospitalsComparable(ExtractionPayLoad extractionPayLoad, HospitalEntity hospital){
        boolean payloadHasAddInfo = trimToNull(extractionPayLoad.additionalInfo()) != null;
        boolean hospitalHasAddInfo = trimToNull(hospital.additionalInfo()) != null;
        return payloadHasAddInfo == hospitalHasAddInfo;
    }

}
