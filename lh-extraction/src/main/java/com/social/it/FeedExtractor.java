package com.social.it;

import com.social.it.domain.ExtractionPayLoad;

import java.util.List;

public interface FeedExtractor {

    List<ExtractionPayLoad> extract(DataFeed dataFeed);

}
