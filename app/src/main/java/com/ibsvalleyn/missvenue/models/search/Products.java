package com.ibsvalleyn.missvenue.models.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Products {
    @Expose
    @SerializedName("Images")
    private List<String> Images;
    @Expose
    @SerializedName("SellingPrice")
    private double SellingPrice;
    @Expose
    @SerializedName("SpecificationAttribute")
    private List<SpecificationAttribute> SpecificationAttribute;
    @Expose
    @SerializedName("Enable_Review")
    private int Enable_Review;
    @Expose
    @SerializedName("iscartlist")
    private int iscartlist;
    @Expose
    @SerializedName("iswishlist")
    private int iswishlist;
    @Expose
    @SerializedName("Weight")
    private double Weight;
    @Expose
    @SerializedName("Price")
    private double Price;
    @Expose
    @SerializedName("ShowStock")
    private boolean ShowStock;
    @Expose
    @SerializedName("StockQuantity")
    private int StockQuantity;
    @Expose
    @SerializedName("IsFreeShipping")
    private boolean IsFreeShipping;
    @Expose
    @SerializedName("IsGiftCard")
    private boolean IsGiftCard;
    @Expose
    @SerializedName("image_url")
    private String image_url;
    @Expose
    @SerializedName("Description")
    private String Description;
    @Expose
    @SerializedName("Name")
    private String Name;
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("base_url")
    private String base_url;

    public List<String> getImages() {
        return Images;
    }

    public void setImages(List<String> Images) {
        this.Images = Images;
    }

    public double getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(double SellingPrice) {
        this.SellingPrice = SellingPrice;
    }

    public List<SpecificationAttribute> getSpecificationAttribute() {
        return SpecificationAttribute;
    }

    public void setSpecificationAttribute(List<SpecificationAttribute> SpecificationAttribute) {
        this.SpecificationAttribute = SpecificationAttribute;
    }

    public int getEnable_Review() {
        return Enable_Review;
    }

    public void setEnable_Review(int Enable_Review) {
        this.Enable_Review = Enable_Review;
    }

    public int getIscartlist() {
        return iscartlist;
    }

    public void setIscartlist(int iscartlist) {
        this.iscartlist = iscartlist;
    }

    public int getIswishlist() {
        return iswishlist;
    }

    public void setIswishlist(int iswishlist) {
        this.iswishlist = iswishlist;
    }

    public double getWeight() {
        return Weight;
    }

    public void setWeight(double Weight) {
        this.Weight = Weight;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public boolean getShowStock() {
        return ShowStock;
    }

    public void setShowStock(boolean ShowStock) {
        this.ShowStock = ShowStock;
    }

    public int getStockQuantity() {
        return StockQuantity;
    }

    public void setStockQuantity(int StockQuantity) {
        this.StockQuantity = StockQuantity;
    }

    public boolean getIsFreeShipping() {
        return IsFreeShipping;
    }

    public void setIsFreeShipping(boolean IsFreeShipping) {
        this.IsFreeShipping = IsFreeShipping;
    }

    public boolean getIsGiftCard() {
        return IsGiftCard;
    }

    public void setIsGiftCard(boolean IsGiftCard) {
        this.IsGiftCard = IsGiftCard;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBase_url() {
        return base_url;
    }

    public void setBase_url(String base_url) {
        this.base_url = base_url;
    }
}
