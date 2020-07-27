package org.social.it.entity;

import org.social.it.domain.UsageStatistics;

import java.time.LocalDate;

public class UsageStatisticsEntity implements UsageStatistics {

    private int totalBedCapacity;
    private int vacantBeds;
    private LocalDate lastUpdated;

    public UsageStatisticsEntity(int totalBedCapacity, int vacantBeds) {
        this.totalBedCapacity = totalBedCapacity;
        this.vacantBeds = vacantBeds;
        this.lastUpdated = LocalDate.now();
    }

    private UsageStatisticsEntity(){

    }

    public int totalBedCapacity() {
        return totalBedCapacity;
    }

    public int currentVacancy() {
        return vacantBeds;
    }

    public LocalDate lastUpdated() {
        return lastUpdated;
    }

}
