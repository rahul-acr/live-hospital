package org.social.it.batch.bean;

import com.opencsv.bean.CsvBindByName;

public class HospitalCsvBean {

    @CsvBindByName(column = "Name")
    public String name;

    @CsvBindByName(column = "Alias1")
    public String alias1;

    @CsvBindByName(column = "Alias2")
    public String alias2;

    @CsvBindByName(column = "Alias3")
    public String alias3;

    @CsvBindByName(column = "Facility")
    public String facility;

    @CsvBindByName(column = "Info")
    public String additionalInfo;

    @CsvBindByName(column = "District")
    public String district;

    @CsvBindByName(column = "Address")
    public String address;

    @CsvBindByName(column = "Phone")
    public String phone;

    @CsvBindByName(column = "Private")
    public String isPrivate;
}

