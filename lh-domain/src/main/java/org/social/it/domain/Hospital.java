package org.social.it.domain;

public interface Hospital extends MongoIdentifiable {

    String name();

    String additionalInfo();

    Contact contact();

    UsageStatistics usage();

    boolean isPrivate();

    void updateUsage(int newVacancy, int newBedCapacity);

}
