package com.social.it;

import java.time.LocalDate;
import java.util.stream.Stream;

public interface DataFeed {

    String feedType();

    LocalDate feedDate();

    Stream<String> feedData();

}
