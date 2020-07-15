package org.social.it.converter;

import org.social.it.domain.Location;
import org.social.it.dto.LocationDto;
import org.social.it.entity.LocationEntity;
import org.springframework.stereotype.Component;

@Component
public class LocationDtoDomainConverter implements DtoDomainConverter<LocationDto, Location> {

    @Override
    public Location convert(LocationDto dto) {
        return new LocationEntity(dto.addressLine, dto.district, dto.state, dto.country, dto.pinCode);
    }
}
