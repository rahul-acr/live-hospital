package com.social.it.impl;

import com.social.it.HospitalMatcher;
import com.social.it.WordDistanceCalculator;
import com.social.it.domain.ExtractionPayLoad;
import com.social.it.exception.DataExtractionException;
import org.social.it.entity.HospitalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SpellErrorTolerantHospitalMatcher implements HospitalMatcher {

    private final static int SPELL_FAULT_TOLERANCE_PERCENTAGE = 10;

    @Autowired
    private WordDistanceCalculator wordDistanceCalculator;

    @Override
    public Optional<HospitalEntity> match(ExtractionPayLoad extractionPayLoad, List<HospitalEntity> hospitals) {
        HospitalEntity matchedHospital = null;
        int matchCount = 0;
        for (HospitalEntity hospital : hospitals) {
            int dist = 100 * calculateDifference(extractionPayLoad, hospital);
            if (dist / hospital.name().length() <= SPELL_FAULT_TOLERANCE_PERCENTAGE) {
                matchCount++;
                matchedHospital = hospital;
            }
        }

        DataExtractionException.throwIf(matchCount > 1,
                "Ambiguous data found for : " + extractionPayLoad);

        return Optional.ofNullable(matchedHospital);
    }

    private static String emptyIfNull(String data){
        return data == null ? "" : data;
    }

    private int calculateDifference(ExtractionPayLoad extractionPayLoad, HospitalEntity hospital) {
        return wordDistanceCalculator.calculateDistance(
                extractionPayLoad.hospitalName() + " " + emptyIfNull(extractionPayLoad.additionalInfo()),
                hospital.name() + " " + emptyIfNull(hospital.additionalInfo()));
    }
}
