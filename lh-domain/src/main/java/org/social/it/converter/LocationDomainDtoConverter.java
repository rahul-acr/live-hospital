package org.social.it.converter;

import org.social.it.domain.Location;
import org.social.it.dto.LocationDto;
import org.springframework.stereotype.Component;

@Component
public class LocationDomainDtoConverter implements DomainDtoConverter<Location, LocationDto>{
    @Override
    public LocationDto convert(Location input) {
        if(input == null) return null;
        LocationDto dto = new LocationDto();
        dto.addressLine = input.addressLine();
        dto.district = input.district();
        dto.state = input.state();
        dto.country = input.country();
        dto.pinCode = input.pinCode();
        return dto;
    }
}
