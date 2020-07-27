package org.social.it.domain;

import java.time.LocalDate;

public interface UnknownHospital extends MongoIdentifiable {

    String name();

    String additionalInfo();

    String originatingFeedName();

    LocalDate addDate();

}
