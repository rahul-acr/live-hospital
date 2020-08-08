package org.social.it.domain;

import java.time.LocalDate;

public interface Hospital extends MongoIdentifiable {

    String name();

    String additionalInfo();

    Contact contact();

    UsageStatistics usage();

    boolean isPrivate();

    void updateUsage(int newVacancy, int newBedCapacity, LocalDate updateDate);

}
