package org.social.it.domain;

import org.bson.types.ObjectId;

public interface Hospital extends Identifiable<ObjectId> {

    String name();

    String additionalInfo();

    Location location();

    int totalBedCapacity();

    int currentVacancy();

    default int usagePercentage() {
        return ((totalBedCapacity() - currentVacancy()) * 100) / totalBedCapacity();
    }

    void updateUsage(int newVacancy, int newBedCapacity);

}
