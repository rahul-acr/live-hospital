package org.social.it.domain;

import org.bson.types.ObjectId;

public interface Hospital extends Identifiable<ObjectId> {

    String name();

    String additionalInfo();

    Contact contact();

    UsageStatistics usage();

    boolean isPrivate();

    void updateUsage(int newVacancy, int newBedCapacity);

}
