package org.social.it.entity;

import org.bson.types.ObjectId;
import org.social.it.domain.DistrictMetaData;
import org.social.it.domain.vo.Coordinate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("district_meta_data")
public class DistrictMetaDataEntity implements DistrictMetaData {

    @Id
    private ObjectId id;
    private String district;
    private double latitude, longitude;

    private DistrictMetaDataEntity(){

    }

    public String districtName() {
        return district;
    }

    public Coordinate primaryLocation() {
        return Coordinate.createFromDegrees(latitude, longitude);
    }

    public ObjectId getId() {
        return id;
    }
}
