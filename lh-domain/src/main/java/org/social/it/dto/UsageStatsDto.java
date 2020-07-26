package org.social.it.dto;

import java.time.LocalDate;

public class UsageStatsDto {
    public int totalBedCapacity;
    public int vacantBeds;
    public LocalDate lastUpdated;
    public boolean isStale;
    public int usagePercentage;
}
