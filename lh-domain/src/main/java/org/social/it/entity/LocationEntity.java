package org.social.it.entity;

import org.social.it.domain.Location;

public class LocationEntity implements Location {

    private String addressLine;
    private String district;
    private String state;
    private String country;
    private int pinCode;

    public LocationEntity(String addressLine, String district, String state, String country, int pinCode) {
        this.addressLine = addressLine;
        this.district = district;
        this.state = state;
        this.country = country;
        this.pinCode = pinCode;
    }

    private LocationEntity(){

    }

    @Override
    public String addressLine() {
        return addressLine;
    }

    @Override
    public String district() {
        return district;
    }

    @Override
    public int pinCode() {
        return pinCode;
    }

    @Override
    public String state() {
        return state;
    }

    @Override
    public String country() {
        return country;
    }

}
