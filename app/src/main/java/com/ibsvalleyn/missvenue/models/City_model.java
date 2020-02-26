package com.ibsvalleyn.missvenue.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class City_model {


    @Expose
    @SerializedName("DisplayOrder")
    private int displayorder;
    @Expose
    @SerializedName("Published")
    private boolean published;
    @Expose
    @SerializedName("Abbreviation")
    private String abbreviation;
    @Expose
    @SerializedName("Name")
    private String name;
    @Expose
    @SerializedName("CountryId")
    private int countryid;
    @Expose
    @SerializedName("Id")
    private int id;

    public int getDisplayorder() {
        return displayorder;
    }

    public void setDisplayorder(int displayorder) {
        this.displayorder = displayorder;
    }

    public boolean getPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountryid() {
        return countryid;
    }

    public void setCountryid(int countryid) {
        this.countryid = countryid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
