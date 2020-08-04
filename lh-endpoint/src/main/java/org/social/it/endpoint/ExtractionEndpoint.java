package org.social.it.endpoint;

import org.social.it.domain.ExtractionResult;
import org.social.it.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

// TODO secure this
@RestController
@RequestMapping("api/extraction")
public class ExtractionEndpoint {

    @Autowired
    private FeedService feedService;

    @PostMapping("/feed")
    public ExtractionResult postFeed(@RequestParam("url") String url, @RequestParam("date") LocalDate date) throws IOException {
        return feedService.feed(new URL(url), date);
    }

    @PostMapping("/auto")
    public List<ExtractionResult> autoFeed(@RequestParam(value = "days") int days ) throws IOException {
        return feedService.autoFeedDataForDays(days);
    }
}
