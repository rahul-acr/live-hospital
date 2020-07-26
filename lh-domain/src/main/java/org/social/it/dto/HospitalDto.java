package org.social.it.dto;

import org.bson.types.ObjectId;

public class HospitalDto extends Identifiable<ObjectId>{
    public String name;
    public String additionalInfo;
    public LocationDto location;
    public UsageStatsDto usage;
}
