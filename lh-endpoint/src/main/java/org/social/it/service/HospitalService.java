package org.social.it.service;

import org.bson.types.ObjectId;
import org.social.it.converter.HospitalDomainDtoConverter;
import org.social.it.converter.HospitalDtoDomainConverter;
import org.social.it.domain.Hospital;
import org.social.it.dto.HospitalDto;
import org.social.it.entity.HospitalEntity;
import org.social.it.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HospitalService extends BaseCrudService<Hospital, HospitalDto> {


    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    public HospitalService(HospitalDomainDtoConverter domainDtoConverter,
                           HospitalDtoDomainConverter dtoDomainConverter) {
        super(domainDtoConverter, dtoDomainConverter);
    }

    public Optional<HospitalDto> findById(ObjectId id){
        return hospitalRepository.findById(id).map(this::convertToDto);
    }

    public List<HospitalDto> findAll(){
        return hospitalRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

//    public HospitalDto update(HospitalDto hospitalDto){
//        Hospital hospital = convertToDomain(hospitalDto);
//        if(hospital.getId() == null)
//            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid update operation");
//        if(!hospitalRepository.existsById(hospital.getId()))
//            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Hospital not found");
//        return save(hospital);
//    }
//
//    private HospitalDto save(Hospital hospital){
//        return convertToDto(hospitalRepository.save((HospitalEntity) hospital));
//    }
//
//    public HospitalDto save(HospitalDto hospitalDto){
//        return save(convertToDomain(hospitalDto));
//    }

}
