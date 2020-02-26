package com.ibsvalleyn.missvenue.models.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefineSearch {
    @Expose
    @SerializedName("Max_Price")
    private double Max_Price;
    @Expose
    @SerializedName("Min_Price")
    private double Min_Price;
    @Expose
    @SerializedName("Color_Count")
    private int Color_Count;
    @Expose
    @SerializedName("Brand_Count")
    private int Brand_Count;
    @Expose
    @SerializedName("Category_Count")
    private int Category_Count;
    @Expose
    @SerializedName("Product_Count")
    private int Product_Count;
    @Expose
    @SerializedName("filter")
    private Filter filter;
    @Expose
    @SerializedName("LandingPage_Type")
    private int LandingPage_Type;

    public double getMax_Price() {
        return Max_Price;
    }

    public void setMax_Price(double Max_Price) {
        this.Max_Price = Max_Price;
    }

    public double getMin_Price() {
        return Min_Price;
    }

    public void setMin_Price(double Min_Price) {
        this.Min_Price = Min_Price;
    }

    public int getColor_Count() {
        return Color_Count;
    }

    public void setColor_Count(int Color_Count) {
        this.Color_Count = Color_Count;
    }

    public int getBrand_Count() {
        return Brand_Count;
    }

    public void setBrand_Count(int Brand_Count) {
        this.Brand_Count = Brand_Count;
    }

    public int getCategory_Count() {
        return Category_Count;
    }

    public void setCategory_Count(int Category_Count) {
        this.Category_Count = Category_Count;
    }

    public int getProduct_Count() {
        return Product_Count;
    }

    public void setProduct_Count(int Product_Count) {
        this.Product_Count = Product_Count;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public int getLandingPage_Type() {
        return LandingPage_Type;
    }

    public void setLandingPage_Type(int LandingPage_Type) {
        this.LandingPage_Type = LandingPage_Type;
    }
}
