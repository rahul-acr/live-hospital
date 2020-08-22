package org.social.it.endpoint;

import org.bson.types.ObjectId;
import org.social.it.dto.HospitalDto;
import org.social.it.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/api/hospital")
public class HospitalEndpoint {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping
    public List<HospitalDto> getAll(){
        return hospitalService.findAll();
    }

    @GetMapping("location-based")
    public List<HospitalDto> getNearestHospitals(@RequestParam(name = "lat") double latitude,
                                 @RequestParam(name = "lon") double longitude,
                                 @RequestParam(name = "limit", required = false, defaultValue = "10") int limit){
        return hospitalService.findNearestHospitals(latitude, longitude, limit);
    }

    @GetMapping("/{id}")
    public HospitalDto get(@PathVariable ObjectId id){
        return hospitalService.findById(id).orElseThrow(
                () -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Hospital not found"));
    }

}
