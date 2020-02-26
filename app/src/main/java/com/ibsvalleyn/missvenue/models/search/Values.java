package com.ibsvalleyn.missvenue.models.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Values {
    @Expose
    @SerializedName("Product_Count")
    private int Product_Count;
    @Expose
    @SerializedName("value")
    private String value;

    public int getProduct_Count() {
        return Product_Count;
    }

    public void setProduct_Count(int Product_Count) {
        this.Product_Count = Product_Count;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
