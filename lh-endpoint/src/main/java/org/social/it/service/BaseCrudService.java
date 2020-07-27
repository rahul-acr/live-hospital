package org.social.it.service;

import org.social.it.converter.DomainDtoConverter;

public abstract class BaseCrudService<D, DTO> {

    private final DomainDtoConverter<D, DTO> domainDtoConverter;

    protected BaseCrudService(DomainDtoConverter<D, DTO> domainDtoConverter) {
        this.domainDtoConverter = domainDtoConverter;
    }

    public DTO convertToDto(D domainObject){
        return domainDtoConverter.convert(domainObject);
    }

}
