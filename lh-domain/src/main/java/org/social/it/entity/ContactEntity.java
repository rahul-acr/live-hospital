package org.social.it.entity;

import org.social.it.domain.Contact;

public class ContactEntity implements Contact {

    private String addressLine;
    private String district;
    private String state;
    private String country;
    private String phoneNumber;
    private int pinCode;

    public ContactEntity(String addressLine,
                         String district,
                         String state,
                         String country,
                         int pinCode,
                         String phoneNumber) {
        this.addressLine = addressLine;
        this.district = district;
        this.state = state;
        this.country = country;
        this.pinCode = pinCode;
        this.phoneNumber = phoneNumber;
    }

    private ContactEntity() {

    }

    @Override
    public String phoneNumber() {
        return phoneNumber;
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
