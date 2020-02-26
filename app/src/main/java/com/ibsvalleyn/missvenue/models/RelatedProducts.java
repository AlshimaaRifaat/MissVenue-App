package com.ibsvalleyn.missvenue.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RelatedProducts {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("Description")
    @Expose
    private String description;

    @SerializedName("Name")
    @Expose
    private String name;

    @SerializedName("Price")
    @Expose
    private double price;

    @SerializedName("image_url")
    @Expose
    private String image_url;

    @SerializedName("Images")
    @Expose
    private List<String> images;

    @SerializedName("iswishlist")
    @Expose
    private Integer iswishlist;

    @SerializedName("SellingPrice")
    @Expose
    private double sellingPrice;

    @SerializedName("SpecificationAttribute")
    @Expose
    private List<SpecificationAttribute> specificationAttributes;

    public List<SpecificationAttribute> getSpecificationAttributes() {
        return specificationAttributes;
    }

    public void setSpecificationAttributes(List<SpecificationAttribute> specificationAttributes) {
        this.specificationAttributes = specificationAttributes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Integer getIswishlist() {
        return iswishlist;
    }

    public void setIswishlist(Integer iswishlist) {
        this.iswishlist = iswishlist;
    }
}
