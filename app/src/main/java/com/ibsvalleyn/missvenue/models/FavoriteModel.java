package com.ibsvalleyn.missvenue.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavoriteModel {
    @PrimaryKey(autoGenerate = true)
    //private int uidd;

    private Integer id;

    public String name;
//
//    public int getUidd() {
//        return uidd;
//    }
//
//    public void setUidd(int uidd) {
//        this.uidd = uidd;
//    }

    private String description;

    private String image_url;


    private Integer iswishlist;

    private double sellingPrice;


    private double price;

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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


    public int getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(int isChecked) {
        this.isChecked = isChecked;
    }

    private int isChecked;
}
