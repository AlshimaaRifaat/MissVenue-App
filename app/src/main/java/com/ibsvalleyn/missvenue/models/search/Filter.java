package com.ibsvalleyn.missvenue.models.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Filter {
    @Expose
    @SerializedName("filter_elements")
    private List<Filter_elements> filter_elements;
    @Expose
    @SerializedName("color_Id")
    private int color_Id;
    @Expose
    @SerializedName("Brand_Id")
    private int Brand_Id;
    @Expose
    @SerializedName("Category_Id")
    private int Category_Id;

    public List<Filter_elements> getFilter_elements() {
        return filter_elements;
    }

    public void setFilter_elements(List<Filter_elements> filter_elements) {
        this.filter_elements = filter_elements;
    }

    public int getColor_Id() {
        return color_Id;
    }

    public void setColor_Id(int color_Id) {
        this.color_Id = color_Id;
    }

    public int getBrand_Id() {
        return Brand_Id;
    }

    public void setBrand_Id(int Brand_Id) {
        this.Brand_Id = Brand_Id;
    }

    public int getCategory_Id() {
        return Category_Id;
    }

    public void setCategory_Id(int Category_Id) {
        this.Category_Id = Category_Id;
    }
}
