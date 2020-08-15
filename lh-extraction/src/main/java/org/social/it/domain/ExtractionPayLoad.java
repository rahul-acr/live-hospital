package org.social.it.domain;

import org.social.it.DataFeed;
import org.social.it.entity.UnknownHospitalEntity;

public class ExtractionPayLoad {

    private final String hospitalName;
    private final String facility;
    private final int totalBeds;
    private final int vacantBeds;
    private final DataFeed dataFeed;

    private ExtractionPayLoad(Builder builder) {
        if (builder.hospitalName == null || builder.hospitalName.isEmpty())
            throw new IllegalArgumentException("Invalid hospital name");
        this.hospitalName = builder.hospitalName.trim();

        this.facility = builder.additionalInfo == null ? null : builder.additionalInfo.trim() ;

        if (builder.totalBeds <= 0) throw new IllegalArgumentException("Total bed can not be zero");
        this.totalBeds = builder.totalBeds;

        if(builder.dataFeed == null) throw new IllegalArgumentException("Data feed can not be null");
        this.dataFeed = builder.dataFeed;

        this.vacantBeds = builder.vacantBeds;
    }

    public String hospitalName() {
        return hospitalName;
    }

    public String facility() {
        return facility;
    }

    public int totalBeds() {
        return totalBeds;
    }

    public int vacantBeds() {
        return vacantBeds;
    }

    public static Builder getBuilder(DataFeed dataFeed) {
        return new Builder(dataFeed);
    }

    public DataFeed dataFeed(){
        return dataFeed;
    }

    public UnknownHospitalEntity generateHospitalEntity(){
        return new UnknownHospitalEntity(hospitalName, facility, dataFeed.feedName(), dataFeed.feedDate());
    }

    @Override
    public String toString() {
        return "ExtractionPayLoad{" +
                "hospitalName='" + hospitalName + '\'' +
                ", additionalInfo='" + facility + '\'' +
                ", totalBeds=" + totalBeds +
                ", vacantBeds=" + vacantBeds +
                '}';
    }

    public static class Builder {

        private String hospitalName;
        private String additionalInfo;
        private int totalBeds;
        private int vacantBeds;
        private final DataFeed dataFeed;

        private Builder(DataFeed dataFeed){
            this.dataFeed = dataFeed;
        }

        public Builder withHospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
            return Builder.this;
        }

        public Builder withAdditionalInfo(String additionalInfo) {
            this.additionalInfo = additionalInfo;
            return Builder.this;
        }

        public Builder withTotalBeds(int totalBeds) {
            this.totalBeds = totalBeds;
            return Builder.this;
        }

        public Builder withVacantBeds(int vacantBeds) {
            this.vacantBeds = vacantBeds;
            return Builder.this;
        }

        public ExtractionPayLoad build() {
            return new ExtractionPayLoad(this);
        }
    }

}
