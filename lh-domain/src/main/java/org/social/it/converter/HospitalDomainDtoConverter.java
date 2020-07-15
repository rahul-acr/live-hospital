package org.social.it.converter;

import org.social.it.domain.Hospital;
import org.social.it.dto.HospitalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HospitalDomainDtoConverter implements DomainDtoConverter<Hospital, HospitalDto> {

    @Autowired
    private LocationDomainDtoConverter locationDomainDtoConverter;

    @Override
    public HospitalDto convert(Hospital input) {
        HospitalDto dto = new HospitalDto();
        dto._id = input.getId();
        dto.totalBedCapacity = input.totalBedCapacity();
        dto.name = input.name();
        dto.location = locationDomainDtoConverter.convert(input.location());
        dto.currentBedUsage = input.currentBedUsage();
        return dto;
    }
}
