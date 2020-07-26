package org.social.it.converter;

import org.social.it.domain.Hospital;
import org.social.it.dto.HospitalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HospitalDomainDtoConverter implements DomainDtoConverter<Hospital, HospitalDto> {

    @Autowired
    private LocationDomainDtoConverter locationDomainDtoConverter;

    @Autowired
    private UsageStatDomainDtoConverter usageStatDomainDtoConverter;

    @Override
    public HospitalDto convert(Hospital hospital) {
        HospitalDto dto = new HospitalDto();
        dto._id = hospital.getId();
        dto.name = hospital.name();
        dto.additionalInfo = hospital.additionalInfo();
        dto.location = locationDomainDtoConverter.convert(hospital.location());
        dto.usage = usageStatDomainDtoConverter.convert(hospital.usage());
        return dto;
    }
}
