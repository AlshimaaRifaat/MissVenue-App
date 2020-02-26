package com.ibsvalleyn.missvenue.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class Country_Model {

    @Expose
    @SerializedName("LimitedToStores")
    private boolean limitedtostores;
    @Expose
    @SerializedName("DisplayOrder")
    private int displayorder;
    @Expose
    @SerializedName("Published")
    private boolean published;
    @Expose
    @SerializedName("SubjectToVat")
    private boolean subjecttovat;
    @Expose
    @SerializedName("NumericIsoCode")
    private int numericisocode;
    @Expose
    @SerializedName("ThreeLetterIsoCode")
    private String threeletterisocode;
    @Expose
    @SerializedName("TwoLetterIsoCode")
    private String twoletterisocode;
    @Expose
    @SerializedName("AllowsShipping")
    private boolean allowsshipping;
    @Expose
    @SerializedName("AllowsBilling")
    private boolean allowsbilling;
    @Expose
    @SerializedName("Name")
    private String name;
    @Expose
    @SerializedName("Id")
    private int id;

    public boolean getLimitedtostores() {
        return limitedtostores;
    }

    public void setLimitedtostores(boolean limitedtostores) {
        this.limitedtostores = limitedtostores;
    }

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

    public boolean getSubjecttovat() {
        return subjecttovat;
    }

    public void setSubjecttovat(boolean subjecttovat) {
        this.subjecttovat = subjecttovat;
    }

    public int getNumericisocode() {
        return numericisocode;
    }

    public void setNumericisocode(int numericisocode) {
        this.numericisocode = numericisocode;
    }

    public String getThreeletterisocode() {
        return threeletterisocode;
    }

    public void setThreeletterisocode(String threeletterisocode) {
        this.threeletterisocode = threeletterisocode;
    }

    public String getTwoletterisocode() {
        return twoletterisocode;
    }

    public void setTwoletterisocode(String twoletterisocode) {
        this.twoletterisocode = twoletterisocode;
    }

    public boolean getAllowsshipping() {
        return allowsshipping;
    }

    public void setAllowsshipping(boolean allowsshipping) {
        this.allowsshipping = allowsshipping;
    }

    public boolean getAllowsbilling() {
        return allowsbilling;
    }

    public void setAllowsbilling(boolean allowsbilling) {
        this.allowsbilling = allowsbilling;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
