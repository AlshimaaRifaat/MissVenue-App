package com.ibsvalleyn.missvenue.models.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Filter_elements {
    @Expose
    @SerializedName("Product_Count")
    private int Product_Count;
    @Expose
    @SerializedName("ControlType")
    private int ControlType;
    @Expose
    @SerializedName("values")
    private List<Values> values;
    @Expose
    @SerializedName("Name")
    private String Name;
    @Expose
    @SerializedName("Id")
    private int Id;

    public int getProduct_Count() {
        return Product_Count;
    }

    public void setProduct_Count(int Product_Count) {
        this.Product_Count = Product_Count;
    }

    public int getControlType() {
        return ControlType;
    }

    public void setControlType(int ControlType) {
        this.ControlType = ControlType;
    }

    public List<Values> getValues() {
        return values;
    }

    public void setValues(List<Values> values) {
        this.values = values;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }
}
