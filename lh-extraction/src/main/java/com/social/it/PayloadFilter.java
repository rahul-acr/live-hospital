package com.social.it;

import com.social.it.domain.ExtractionPayLoad;

public interface PayloadFilter {
    boolean isValid(ExtractionPayLoad extractionPayLoad);
}
