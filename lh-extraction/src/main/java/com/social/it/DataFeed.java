package com.social.it;

import java.time.LocalDate;

public interface DataFeed {

    String feedType();

    LocalDate feedDate();

    Object feedData();

    Class<?> dataClass();

}
