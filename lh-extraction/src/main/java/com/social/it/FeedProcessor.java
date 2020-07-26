package com.social.it;

import com.social.it.domain.ExtractionResult;

public interface FeedProcessor {

    ExtractionResult process(DataFeed dataFeed);

}
