package com.ibsvalleyn.missvenue.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDetails {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("Enable_Review")
    @Expose
    private int Enable_Review;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("SKU")
    @Expose
    private String sKU;
    @SerializedName("image_url")
    @Expose
    private String image_url;
    @SerializedName("ProductUrl")
    @Expose
    private String ProductUrl;

    public String getProductUrl() {
        return ProductUrl;
    }

    public void setProductUrl(String productUrl) {
        ProductUrl = productUrl;
    }

    @SerializedName("Name")
    @Expose
    private String name;

    @SerializedName("Price")
    @Expose
    private double price;

    @SerializedName("iswishlist")
    @Expose
    private Integer iswishlist;

    @SerializedName("Images")
    @Expose
    private List<String> images;

    @SerializedName("SellingPrice")
    @Expose
    private double sellingPrice;

//    @SerializedName("RelatedProducts")
//    @Expose
//    private List<RelatedProducts> relatedProducts;

    @SerializedName("SpecificationAttribute")
    @Expose
    private List<SpecificationAttribute> specificationAttributes;

    public Integer getIswishlist() {
        return iswishlist;
    }

    public void setIswishlist(Integer iswishlist) {
        this.iswishlist = iswishlist;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

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

    public int getEnable_Review() {
        return Enable_Review;
    }

    public void setEnable_Review(int enable_Review) {
        Enable_Review = enable_Review;
    }

    public String getsKU() {
        return sKU;
    }

    public void setsKU(String sKU) {
        this.sKU = sKU;
    }
}
