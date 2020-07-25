package org.social.it.entity;

import org.bson.types.ObjectId;
import org.social.it.domain.Hospital;
import org.social.it.domain.Location;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("hospitals")
public class HospitalEntity implements Hospital {

    private String name;
    private String additionalInfo;
    private LocationEntity location;
    @Id
    private ObjectId id;
    private int totalBedCapacity;
    private int vacantBeds;

    public HospitalEntity(ObjectId id,
                          String name,
                          String additionalInfo,
                          LocationEntity location,
                          int totalBedCapacity,
                          int vacantBeds) {
        this.id = id;
        this.name = name;
        this.additionalInfo = additionalInfo;
        this.location = location;
        this.totalBedCapacity = totalBedCapacity;
        this.vacantBeds = vacantBeds;
    }

    public HospitalEntity(String name,
                          String additionalInfo,
                          LocationEntity location,
                          int totalBedCapacity,
                          int vacantBeds) {
        this(null, name, additionalInfo, location, totalBedCapacity, vacantBeds);
    }

    private HospitalEntity(){

    }

    public ObjectId getId() {
        return id;
    }

    public String name() {
        return name;
    }

    public String additionalInfo() {
        return additionalInfo;
    }

    public Location location() {
        return location;
    }

    public int totalBedCapacity() {
        return totalBedCapacity;
    }

    public int currentVacancy() {
        return vacantBeds;
    }

    @Override
    public void updateUsage(int newVacancy, int newBedCapacity) {
        if (newVacancy < 0) throw new IllegalArgumentException("Usage can not be negative");
        this.vacantBeds = newVacancy;
        if (newBedCapacity < 0) throw new IllegalArgumentException("Bed capacity can not be negative");
        this.totalBedCapacity = newBedCapacity;
    }

    @Override
    public String toString() {
        return "HospitalEntity{" +
                "name='" + name + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", location=" + location +
                ", id=" + id +
                ", totalBedCapacity=" + totalBedCapacity +
                ", vacantBeds=" + vacantBeds +
                '}';
    }
}
