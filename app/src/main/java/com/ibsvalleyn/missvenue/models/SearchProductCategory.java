package com.ibsvalleyn.missvenue.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchProductCategory {


    @Expose
    @SerializedName("IswishList")
    private Integer IswishList;

    public Integer getIswishList() {
        return IswishList;
    }

    public void setIswishList(Integer iswishList) {
        IswishList = iswishList;
    }

    @Expose
    @SerializedName("image_url")
    private String imageUrl;
    @Expose
    @SerializedName("Description")
    private String description;
    @Expose
    @SerializedName("category_Name")
    private String categoryName;
    @Expose
    @SerializedName("product_Name")
    private String productName;
    @Expose
    @SerializedName("id")
    private int id;
    @SerializedName("SellingPrice")
    @Expose
    private double sellingPrice;

    @SerializedName("price")
    @Expose
    private double price;

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
