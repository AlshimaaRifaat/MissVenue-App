package com.ibsvalleyn.missvenue.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User_address_model {

    @Expose
    @SerializedName("State")
    private String state;
    @Expose
    @SerializedName("Country")
    private String country;
    @Expose
    @SerializedName("CreatedOnUtc")
    private String createdonutc;
    @Expose
    @SerializedName("FaxNumber")
    private String faxnumber;
    @Expose
    @SerializedName("PhoneNumber")
    private String phonenumber;
    @Expose
    @SerializedName("ZipPostalCode")
    private String zippostalcode;
    @Expose
    @SerializedName("Address2")
    private String address2;
    @Expose
    @SerializedName("Address1")
    private String address1;
    @Expose
    @SerializedName("City")
    private String city;
    @Expose
    @SerializedName("StateProvinceId")
    private int stateprovinceid;
    @Expose
    @SerializedName("CountryId")
    private int countryid;
    @Expose
    @SerializedName("Company")
    private String company;
    @Expose
    @SerializedName("Email")
    private String email;
    @Expose
    @SerializedName("LastName")
    private String lastname;
    @Expose
    @SerializedName("FirstName")
    private String firstname;
    @Expose
    @SerializedName("Customer_Id")
    private int customerId;
    @Expose
    @SerializedName("address_Id")
    private int addressId;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCreatedonutc() {
        return createdonutc;
    }

    public void setCreatedonutc(String createdonutc) {
        this.createdonutc = createdonutc;
    }

    public String getFaxnumber() {
        return faxnumber;
    }

    public void setFaxnumber(String faxnumber) {
        this.faxnumber = faxnumber;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getZippostalcode() {
        return zippostalcode;
    }

    public void setZippostalcode(String zippostalcode) {
        this.zippostalcode = zippostalcode;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getStateprovinceid() {
        return stateprovinceid;
    }

    public void setStateprovinceid(int stateprovinceid) {
        this.stateprovinceid = stateprovinceid;
    }

    public int getCountryid() {
        return countryid;
    }

    public void setCountryid(int countryid) {
        this.countryid = countryid;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
}
