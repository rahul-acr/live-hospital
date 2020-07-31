package org.social.it.service;

import com.social.it.DataFeed;
import com.social.it.FeedProcessor;
import com.social.it.domain.ExtractionResult;
import com.social.it.domain.PdfDataFeed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeedService {

    private static final Logger LOG = LoggerFactory.getLogger(FeedService.class);

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    private FeedProcessor feedProcessor;

    public List<ExtractionResult> autoFeedDataForDays(int days) throws IOException {
        List<ExtractionResult> results = new ArrayList<>();
        results.addAll(feedForResource(resourceLoader.getResource("classpath:pvt_hospital_url_patterns"), days));
        results.addAll(feedForResource(resourceLoader.getResource("classpath:pvt_hospital_url_patterns"), days));
        return results;
    }

    public ExtractionResult feed(URL url, LocalDate date) throws IOException {
        DataFeed dataFeed = new PdfDataFeed(url, date);
        return feedProcessor.process(dataFeed);
    }

    private List<ExtractionResult> feedForResource(Resource resource, int days) throws IOException {
        LOG.info("Auto extracting feed for {} days", days);
        ArrayList<DateTimeFormatter> formats = new ArrayList<>();
        ArrayList<ExtractionResult> results = new ArrayList<>();

        {
            File file = resource.getFile();
            BufferedReader br = new BufferedReader(new FileReader(file));
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                formats.add(DateTimeFormatter.ofPattern(line));
            }
        }

        LocalDate date = LocalDate.now().minusDays(days);
        for (int i = 0; i < days; i++, date = date.plusDays(1)) {
            URL resolvedUrl = null;
            for (DateTimeFormatter format : formats) {
                URL url = new URL(date.format(format));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("HEAD");
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    resolvedUrl = url;
                    break;
                }
            }

            if (resolvedUrl == null) {
                LOG.error("Feed could not identified by {} for {}", resource, date);
                continue;
            }

            results.add(this.feed(resolvedUrl, date));
        }

        return results;
    }
}
