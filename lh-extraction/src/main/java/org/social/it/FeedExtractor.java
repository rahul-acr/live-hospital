package org.social.it;

import org.social.it.domain.ExtractionPayLoad;

import java.util.List;

public interface FeedExtractor {

    List<ExtractionPayLoad> extract(DataFeed dataFeed);

}
