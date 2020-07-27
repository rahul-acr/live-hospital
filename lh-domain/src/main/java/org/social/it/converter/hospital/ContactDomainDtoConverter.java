package org.social.it.converter.hospital;

import org.social.it.converter.DomainDtoConverter;
import org.social.it.domain.Contact;
import org.social.it.dto.ContactDto;
import org.springframework.stereotype.Component;

@Component
public class ContactDomainDtoConverter implements DomainDtoConverter<Contact, ContactDto> {
    @Override
    public ContactDto convert(Contact input) {
        if(input == null) return null;
        ContactDto dto = new ContactDto();
        dto.addressLine = input.addressLine();
        dto.district = input.district();
        dto.state = input.state();
        dto.country = input.country();
        dto.pinCode = input.pinCode();
        dto.phone = input.phoneNumber();
        return dto;
    }
}
