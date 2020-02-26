package com.ibsvalleyn.missvenue.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("Address1")
    @Expose
    private String Address1;

    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(String address1) {
        Address1 = address1;
    }

    @SerializedName("City")
    @Expose
    private String City;

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    @SerializedName("LastName")
    @Expose
    private String lastName;

    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;

    @SerializedName("Country")
    @Expose
    private String country;

    @SerializedName("StateModel")
    @Expose
    private StateModel stateModel;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public StateModel getStateModel() {
        return stateModel;
    }

    public void setStateModel(StateModel stateModel) {
        this.stateModel = stateModel;
    }
}
