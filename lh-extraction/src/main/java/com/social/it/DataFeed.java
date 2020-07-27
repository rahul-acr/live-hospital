package com.social.it;

import java.time.LocalDate;

public interface DataFeed {

    String feedName();

    String feedType();

    LocalDate feedDate();

    Object feedData();

    Class<?> dataClass();

}
