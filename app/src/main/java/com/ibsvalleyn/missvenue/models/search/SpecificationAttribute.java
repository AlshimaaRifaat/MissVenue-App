package com.ibsvalleyn.missvenue.models.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpecificationAttribute {
    @Expose
    @SerializedName("Specific_Id")
    private int Specific_Id;
    @Expose
    @SerializedName("Value")
    private String Value;
    @Expose
    @SerializedName("DisplayOrder")
    private int DisplayOrder;
    @Expose
    @SerializedName("ShowOnProductPage")
    private boolean ShowOnProductPage;
    @Expose
    @SerializedName("AllowFiltering")
    private boolean AllowFiltering;
    @Expose
    @SerializedName("Name")
    private String Name;
    @Expose
    @SerializedName("Id")
    private int Id;

    public int getSpecific_Id() {
        return Specific_Id;
    }

    public void setSpecific_Id(int Specific_Id) {
        this.Specific_Id = Specific_Id;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String Value) {
        this.Value = Value;
    }

    public int getDisplayOrder() {
        return DisplayOrder;
    }

    public void setDisplayOrder(int DisplayOrder) {
        this.DisplayOrder = DisplayOrder;
    }

    public boolean getShowOnProductPage() {
        return ShowOnProductPage;
    }

    public void setShowOnProductPage(boolean ShowOnProductPage) {
        this.ShowOnProductPage = ShowOnProductPage;
    }

    public boolean getAllowFiltering() {
        return AllowFiltering;
    }

    public void setAllowFiltering(boolean AllowFiltering) {
        this.AllowFiltering = AllowFiltering;
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
