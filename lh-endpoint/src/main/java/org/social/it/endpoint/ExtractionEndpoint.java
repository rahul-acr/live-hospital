package org.social.it.endpoint;

import com.social.it.DataFeed;
import com.social.it.FeedProcessor;
import com.social.it.domain.ExtractionResult;
import com.social.it.domain.PdfDataFeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;

// TODO secure this
@RestController
@RequestMapping("api/extraction")
public class ExtractionEndpoint {

    @Autowired
    private FeedProcessor feedProcessor;

    @PostMapping("/feed")
    public ExtractionResult postFeed(@RequestParam("url") String url) throws IOException {
        DataFeed dataFeed = new PdfDataFeed(new URL(url));
        return feedProcessor.process(dataFeed);
    }
}
