package com.social.it.domain;

public class ExtractionPayLoad {

    private final String hospitalName;
    private final String additionalInfo;
    private final int totalBeds;
    private final int vacantBeds;

    private ExtractionPayLoad(Builder builder) {
        if (builder.hospitalName == null || builder.hospitalName.isEmpty())
            throw new IllegalArgumentException("Invalid hospital name");
        this.hospitalName = builder.hospitalName.trim();

        this.additionalInfo = builder.additionalInfo;

        if (builder.totalBeds <= 0)
            throw new IllegalArgumentException("Total bed can not be zero");
        this.totalBeds = builder.totalBeds;

        this.vacantBeds = builder.vacantBeds;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public String[] keywords() {
        return hospitalName.split(" ");
    }

    public String hospitalName() {
        return hospitalName;
    }

    public String additionalInfo() {
        return additionalInfo;
    }

    public int totalBeds() {
        return totalBeds;
    }

    public int vacantBeds() {
        return vacantBeds;
    }

    @Override
    public String toString() {
        return "ExtractionPayLoad{" +
                "hospitalName='" + hospitalName + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", totalBeds=" + totalBeds +
                ", vacantBeds=" + vacantBeds +
                '}';
    }

    public static class Builder {

        private String hospitalName;
        private String additionalInfo;
        private int totalBeds;
        private int vacantBeds;

        public Builder hospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
            return Builder.this;
        }

        public Builder additionalInfo(String additionalInfo) {
            this.additionalInfo = additionalInfo;
            return Builder.this;
        }

        public Builder totalBeds(int totalBeds) {
            this.totalBeds = totalBeds;
            return Builder.this;
        }

        public Builder vacantBeds(int vacantBeds) {
            this.vacantBeds = vacantBeds;
            return Builder.this;
        }

        public ExtractionPayLoad build() {
            return new ExtractionPayLoad(this);
        }
    }

}
