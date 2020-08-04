package org.social.it.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.it.HospitalMatcher;
import org.social.it.WordDistanceCalculator;
import org.social.it.domain.ExtractionPayLoad;
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

    private static String trimToNull(String data) {
        if (data.trim().equals("")) return null;
        return data;
    }

    @Override
    public Optional<HospitalEntity> match(ExtractionPayLoad extractionPayLoad, List<HospitalEntity> hospitals) {
        HospitalEntity matchedHospital = null;
        int matchCount = 0;
        for (HospitalEntity hospital : hospitals) {
            if (!areHospitalsComparable(extractionPayLoad, hospital)) continue;

            // WARNING : Not considering additional info as it varies greatly feed to feed.
            // Also assuming one hospital does not have more than one specialization.
            int errorPercentage = calculateMinimumError(extractionPayLoad, hospital);
            if (errorPercentage <= SPELL_FAULT_TOLERANCE_PERCENTAGE) {
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

    private int calculateMinimumError(ExtractionPayLoad extractionPayLoad, HospitalEntity hospital) {
        String hospitalNameFromPayLoad = extractionPayLoad.hospitalName().trim().toLowerCase();
        int distance = (100 *
                wordDistanceCalculator.calculateDistance(hospitalNameFromPayLoad, hospital.name().toLowerCase()))
                / hospital.name().length();
        // Check against aliases
        for (String alias : hospital.aliases()) {
            if(alias.isEmpty()) continue;
            int dist = (wordDistanceCalculator.calculateDistance(hospitalNameFromPayLoad, alias.toLowerCase()) * 100) / alias.length();
            distance = Math.min(distance, dist);
        }
        return distance;
    }

    // If a hospital has special info (is specialized), don't compare it with regular hospitals
    private boolean areHospitalsComparable(ExtractionPayLoad extractionPayLoad, HospitalEntity hospital) {
        boolean payloadHasFacility = trimToNull(extractionPayLoad.facility()) != null;
        boolean hospitalHasFacility = trimToNull(hospital.facility()) != null;
        return payloadHasFacility == hospitalHasFacility;
    }

}
