package org.social.it.entity;

import org.bson.types.ObjectId;
import org.social.it.domain.Contact;
import org.social.it.domain.Hospital;
import org.social.it.domain.UsageStatistics;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("hospitals")
public class HospitalEntity implements Hospital {
    @Id
    private ObjectId id;
    private String name;
    private String additionalInfo;
    private ContactEntity contact;
    private UsageStatisticsEntity usageStatistics;
    private boolean isPrivate;

    public HospitalEntity(ObjectId id,
                          String name,
                          String additionalInfo,
                          ContactEntity contact,
                          boolean isPrivate,
                          int totalBedCapacity,
                          int vacantBeds) {
        this.id = id;
        this.name = name;
        this.additionalInfo = additionalInfo;
        this.contact = contact;
        this.isPrivate = isPrivate;
        this.usageStatistics = new UsageStatisticsEntity(totalBedCapacity, vacantBeds);
    }

    public HospitalEntity(String name,
                          String additionalInfo,
                          ContactEntity contact,
                          boolean isPrivate,
                          int totalBedCapacity,
                          int vacantBeds) {
        this(null, name, additionalInfo, contact, isPrivate, totalBedCapacity, vacantBeds);
    }

    private HospitalEntity() {

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

    public Contact contact() {
        return contact;
    }

    public UsageStatistics usage() {
        return usageStatistics;
    }

    public boolean isPrivate() {
        return isPrivate;
    }
}
