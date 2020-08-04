package org.social.it;

import org.social.it.domain.ExtractionPayLoad;
import org.social.it.entity.HospitalEntity;

import java.util.List;
import java.util.Optional;

public interface HospitalMatcher {

    Optional<HospitalEntity> match(ExtractionPayLoad extractionPayLoad, List<HospitalEntity> hospitals);

}
