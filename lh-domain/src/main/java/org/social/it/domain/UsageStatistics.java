package org.social.it.domain;

import java.time.LocalDate;

public interface UsageStatistics {

    int totalBedCapacity();

    int currentVacancy();

    LocalDate lastUpdated();

    default int usagePercentage() {
        return ((totalBedCapacity() - currentVacancy()) * 100) / totalBedCapacity();
    }

    void updateUsage(int newVacancy, int newBedCapacity);

    default boolean isStale(){
        return lastUpdated() == null || !lastUpdated().isEqual(LocalDate.now());
    }
}
