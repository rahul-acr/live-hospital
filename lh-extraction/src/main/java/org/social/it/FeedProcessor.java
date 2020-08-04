package org.social.it;

import org.social.it.domain.ExtractionResult;

public interface FeedProcessor {

    ExtractionResult process(DataFeed dataFeed);

}
