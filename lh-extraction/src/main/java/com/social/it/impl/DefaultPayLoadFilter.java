package com.social.it.impl;

import com.social.it.PayloadFilter;
import com.social.it.domain.ExtractionPayLoad;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component("defaultPayLoadFilter")
public class DefaultPayLoadFilter implements PayloadFilter {

    Predicate<ExtractionPayLoad> payLoadPredicate = (e) -> !e.hospitalName().toLowerCase().contains("total");

    @Override
    public boolean isValid(ExtractionPayLoad extractionPayLoad) {
        return payLoadPredicate.test(extractionPayLoad);
    }
}
