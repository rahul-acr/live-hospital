package org.social.it.converter.hospital;

import org.social.it.converter.DomainDtoConverter;
import org.social.it.domain.UsageStatistics;
import org.social.it.dto.UsageStatsDto;
import org.springframework.stereotype.Component;

@Component
public class UsageStatDomainDtoConverter implements DomainDtoConverter<UsageStatistics, UsageStatsDto> {
    @Override
    public UsageStatsDto convert(UsageStatistics usage) {
        if (usage == null) return null;
        UsageStatsDto dto = new UsageStatsDto();
        dto.vacantBeds = usage.currentVacancy();
        dto.totalBedCapacity = usage.totalBedCapacity();
        dto.isStale = usage.isStale();
        dto.usagePercentage = usage.usagePercentage();
        dto.lastUpdated = usage.lastUpdated();
        return dto;
    }
}
