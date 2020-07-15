package org.social.it.service;

import org.social.it.converter.DomainDtoConverter;
import org.social.it.converter.DtoDomainConverter;

public abstract class BaseCrudService<D, DTO> {

    private final DomainDtoConverter<D, DTO> domainDtoConverter;
    private final DtoDomainConverter<DTO, D> dtoDomainConverter;

    protected BaseCrudService(DomainDtoConverter<D, DTO> domainDtoConverter, DtoDomainConverter<DTO, D> dtoDomainConverter) {
        this.domainDtoConverter = domainDtoConverter;
        this.dtoDomainConverter = dtoDomainConverter;
    }

    public DTO convertToDto(D domainObject){
        return domainDtoConverter.convert(domainObject);
    }

    public D convertToDomain(DTO dto){
        return dtoDomainConverter.convert(dto);
    }
}
