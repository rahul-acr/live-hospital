package org.social.it.converter;

import org.social.it.domain.Hospital;
import org.social.it.dto.HospitalDto;
import org.springframework.stereotype.Component;

@Component
public class HospitalDtoDomainConverter implements DtoDomainConverter<HospitalDto, Hospital> {

    @Override
    public Hospital convert(HospitalDto input) {
        throw new UnsupportedOperationException("Converting hospital DTOs to Entity is not currently supported");
    }
}
