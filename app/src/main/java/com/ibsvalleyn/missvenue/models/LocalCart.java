package com.ibsvalleyn.missvenue.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LocalCart implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("Name")
    @Expose
    private String name;

    @SerializedName("Description")
    @Expose
    private String description;

    @SerializedName("image_url")
    @Expose
    private String image_url;

    @SerializedName("iswishlist")
    @Expose
    private Integer iswishlist;

    @SerializedName("SellingPrice")
    @Expose
    private double sellingPrice;

    @SerializedName("Price")
    @Expose
    private double price;

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIswishlist() {
        return iswishlist;
    }

    public void setIswishlist(Integer iswishlist) {
        this.iswishlist = iswishlist;
    }


    private int isChecked;

    public int getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(int isChecked) {
        this.isChecked = isChecked;
    }
}


