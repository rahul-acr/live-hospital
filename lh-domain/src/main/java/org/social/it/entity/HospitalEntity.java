package org.social.it.entity;

import org.bson.types.ObjectId;
import org.social.it.domain.Hospital;
import org.social.it.domain.Location;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("hospitals")
public class HospitalEntity implements Hospital {

    @Id
    private ObjectId id;
    private String name;

    private LocationEntity location;
    private int totalBedCapacity;
    private int currentBedUsage;

    public HospitalEntity(ObjectId id,
                          String name,
                          LocationEntity location,
                          int totalBedCapacity,
                          int currentBedUsage) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.totalBedCapacity = totalBedCapacity;
        this.currentBedUsage = currentBedUsage;
    }

    private HospitalEntity() {

    }

    public ObjectId getId() {
        return id;
    }

    public String name() {
        return name;
    }

    public Location location() {
        return location;
    }

    public int totalBedCapacity() {
        return totalBedCapacity;
    }

    public int currentBedUsage() {
        return currentBedUsage;
    }

    public void admitPatients(int patientCount) {
        if ((currentBedUsage + patientCount) > totalBedCapacity) {
            throw new IllegalStateException(String.format("Current bed usage %d can not handle new %d patients",
                    currentBedUsage, patientCount));
        }
        currentBedUsage += totalBedCapacity;
    }

    public void dischargePatients(int patientCount) {
        if (currentBedUsage < patientCount) {
            throw new IllegalStateException(
                    String.format("Can not discharge %d patients as only %d patients are admitted currently",
                            patientCount, currentBedUsage));
        }
        currentBedUsage += totalBedCapacity;
    }
}
