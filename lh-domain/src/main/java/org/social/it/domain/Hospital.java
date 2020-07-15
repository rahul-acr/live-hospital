package org.social.it.domain;

import org.bson.types.ObjectId;

public interface Hospital extends Identifiable<ObjectId> {

    String name();

    Location location();

    int totalBedCapacity();

    int currentBedUsage();

    default int usagePercentage() {
        return (currentBedUsage() * 100) / totalBedCapacity();
    }

    void admitPatients(int patientCount);

    void dischargePatients(int patientCount);
}
