package org.social.it.converter;

import org.social.it.domain.Hospital;
import org.social.it.dto.HospitalDto;
import org.social.it.entity.HospitalEntity;
import org.social.it.entity.LocationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HospitalDtoDomainConverter implements DtoDomainConverter<HospitalDto, Hospital> {

    @Autowired
    private LocationDtoDomainConverter locationDtoDomainConverter;

    @Override
    public Hospital convert(HospitalDto input) {
        return new HospitalEntity(input._id,
                input.name,
                input.additionalInfo, (LocationEntity) locationDtoDomainConverter.convert(input.location),
                input.totalBedCapacity,
                input.currentBedUsage);
    }
}
