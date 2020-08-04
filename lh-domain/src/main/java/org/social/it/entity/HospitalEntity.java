package org.social.it.entity;

import org.bson.types.ObjectId;
import org.social.it.domain.Contact;
import org.social.it.domain.Hospital;
import org.social.it.domain.UsageStatistics;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("hospitals")
public class HospitalEntity implements Hospital {
    @Id
    private ObjectId id;
    private String name;
    private String[] aliases;
    private String facility;
    private String additionalInfo;
    private ContactEntity contact;
    private UsageStatisticsEntity usageStatistics;
    private boolean isPrivate;

    public HospitalEntity(String name,
                          String[] aliases, String additionalInfo, String facility,
                          ContactEntity contact,
                          boolean isPrivate) {
        this.name = name;
        this.aliases = aliases;
        this.facility = facility;
        this.additionalInfo = additionalInfo;
        this.contact = contact;
        this.isPrivate = isPrivate;
    }


    private HospitalEntity() {

    }

    public ObjectId getId() {
        return id;
    }

    public String name() {
        return name;
    }

    public String[] aliases() {
        return aliases;
    }

    public String facility() {
        return facility;
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

    public void updateUsage(int newVacancy, int newBedCapacity, LocalDate updateDate) {
        if (newVacancy < 0) throw new IllegalArgumentException("Vacancy can not be negative");
        if (newBedCapacity <= 0) throw new IllegalArgumentException("Bed capacity can not be negative or zero");
        this.usageStatistics = new UsageStatisticsEntity(newBedCapacity, newVacancy, updateDate);
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    @Override
    public String toString() {
        return "HospitalEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", contact=" + contact +
                ", usageStatistics=" + usageStatistics +
                ", isPrivate=" + isPrivate +
                '}';
    }
}
