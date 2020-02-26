package com.ibsvalleyn.missvenue.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductOrder {

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
    private String imageUrl;
    @SerializedName("Type_label")
    @Expose
    private String typeLabel;
    @SerializedName("SKU")
    @Expose
    private String sKU;
    @SerializedName("IsGiftCard")
    @Expose
    private boolean isGiftCard;
    @SerializedName("IsFreeShipping")
    @Expose
    private boolean isFreeShipping;
    @SerializedName("StockQuantity")
    @Expose
    private double stockQuantity;
    @SerializedName("ShowStock")
    @Expose
    private boolean showStock;
    @SerializedName("Price")
    @Expose
    private double price;
    @SerializedName("Weight")
    @Expose
    private double weight;
    @SerializedName("Attributes")
    @Expose
    private List<Object> attributes = null;
    @SerializedName("SpecificationAttribute")
    @Expose
    private List<SpecificationAttribute> specificationAttribute = null;
    @SerializedName("Discounts")
    @Expose
    private List<Object> discounts = null;
    @SerializedName("SellingPrice")
    @Expose
    private double sellingPrice;
    @SerializedName("Images")
    @Expose
    private List<String> images = null;
    @SerializedName("RelationType")
    @Expose
    private int relationType;
    @SerializedName("RelatedProducts")
    @Expose
    private List<Object> relatedProducts = null;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTypeLabel() {
        return typeLabel;
    }

    public void setTypeLabel(String typeLabel) {
        this.typeLabel = typeLabel;
    }

    public String getSKU() {
        return sKU;
    }

    public void setSKU(String sKU) {
        this.sKU = sKU;
    }

    public boolean isIsGiftCard() {
        return isGiftCard;
    }

    public void setIsGiftCard(boolean isGiftCard) {
        this.isGiftCard = isGiftCard;
    }

    public boolean isIsFreeShipping() {
        return isFreeShipping;
    }

    public void setIsFreeShipping(boolean isFreeShipping) {
        this.isFreeShipping = isFreeShipping;
    }

    public double getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(double stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public boolean isShowStock() {
        return showStock;
    }

    public void setShowStock(boolean showStock) {
        this.showStock = showStock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public List<Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Object> attributes) {
        this.attributes = attributes;
    }

    public List<SpecificationAttribute> getSpecificationAttribute() {
        return specificationAttribute;
    }

    public void setSpecificationAttribute(List<SpecificationAttribute> specificationAttribute) {
        this.specificationAttribute = specificationAttribute;
    }

    public List<Object> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Object> discounts) {
        this.discounts = discounts;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getRelationType() {
        return relationType;
    }

    public void setRelationType(int relationType) {
        this.relationType = relationType;
    }

    public List<Object> getRelatedProducts() {
        return relatedProducts;
    }

    public void setRelatedProducts(List<Object> relatedProducts) {
        this.relatedProducts = relatedProducts;
    }


}
