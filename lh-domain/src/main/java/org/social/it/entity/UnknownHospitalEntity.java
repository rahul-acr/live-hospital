package org.social.it.entity;

import org.bson.types.ObjectId;
import org.social.it.domain.UnknownHospital;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("unknown_hospitals")
public class UnknownHospitalEntity implements UnknownHospital {

    @Id
    private ObjectId id;
    private String hospitalName;
    private String additionalInfo;
    private String originatingFeedName;
    private LocalDate addDate;

    public UnknownHospitalEntity(String hospitalName,
                                 String additionalInfo,
                                 String originatingFeedName,
                                 LocalDate addDate) {
        this.hospitalName = hospitalName;
        this.additionalInfo = additionalInfo;
        this.originatingFeedName = originatingFeedName;
        this.addDate = addDate;
    }

    private UnknownHospitalEntity() {

    }

    public ObjectId getId() {
        return id;
    }

    public String name() {
        return hospitalName;
    }

    public String additionalInfo() {
        return additionalInfo;
    }

    public String originatingFeedName() {
        return originatingFeedName;
    }

    public LocalDate addDate() {
        return addDate;
    }

}
