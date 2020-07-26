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

    public String addressLine() {
        return addressLine;
    }

    public String district() {
        return district;
    }

    public int pinCode() {
        return pinCode;
    }

    public String state() {
        return state;
    }

    public String country() {
        return country;
    }

}
