package org.social.it.service;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.it.converter.hospital.HospitalDomainDtoConverter;
import org.social.it.domain.Hospital;
import org.social.it.domain.vo.Coordinate;
import org.social.it.dto.HospitalDto;
import org.social.it.repository.DistrictMetaDataRepository;
import org.social.it.repository.HospitalRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

@Service
public class HospitalService extends BaseCrudService<Hospital, HospitalDto> implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(HospitalService.class);

    private final Map<String, Coordinate> districtCoordinateMap = new ConcurrentSkipListMap<>();

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private DistrictMetaDataRepository districtMetaDataRepository;

    @Autowired
    public HospitalService(HospitalDomainDtoConverter domainDtoConverter) {
        super(domainDtoConverter);
    }

    public Optional<HospitalDto> findById(ObjectId id) {
        return hospitalRepository.findById(id).map(this::convertToDto);
    }

    public List<HospitalDto> findAll() {
        return hospitalRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .sorted(Comparator.comparingInt(h -> h.usage.usagePercentage))
                .collect(Collectors.toList());
    }


    public List<HospitalDto> findNearestHospitals(double lat, double lon, int limit) {
        Coordinate userCoordinate = Coordinate.createFromDegrees(lat, lon);
        return hospitalRepository.findAll()
                .stream()
                .map(e -> {
                    Coordinate hospitalCoordinate = districtCoordinateMap.get(e.contact().district().trim());
                    if (hospitalCoordinate == null)
                        throw new RuntimeException("Coordinates not found for " + e.contact().district());
                    double distance = userCoordinate.distanceFrom(hospitalCoordinate);
                    LOG.debug("Distance to {} from {} is {} KMs", e.name(), userCoordinate, distance);
                    return new HospitalDistanceTuple(e, distance);
                })
                .sorted((o1, o2) -> {
                    if (o1.distance == o2.distance)
                        return Integer.compare(o1.hospital.usage().usagePercentage(), o2.hospital.usage().usagePercentage());
                    return Double.compare(o1.distance, o2.distance);
                })
                .map((e) -> this.convertToDto(e.hospital))
                .limit(limit)
                .collect(Collectors.toList());
    }

    public void afterPropertiesSet() {
        districtMetaDataRepository.findAll()
                .forEach((e) -> districtCoordinateMap.put(e.districtName(), e.primaryLocation()));
    }

    private static class HospitalDistanceTuple {
        private final Hospital hospital;
        private final double distance;

        public HospitalDistanceTuple(Hospital hospital, double distance) {
            this.hospital = hospital;
            this.distance = distance;
        }
    }
}
