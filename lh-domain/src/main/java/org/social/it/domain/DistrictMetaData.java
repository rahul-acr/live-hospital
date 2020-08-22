package org.social.it.domain;

import org.social.it.domain.vo.Coordinate;

public interface DistrictMetaData extends MongoIdentifiable {

    String districtName();

    Coordinate primaryLocation();

}
