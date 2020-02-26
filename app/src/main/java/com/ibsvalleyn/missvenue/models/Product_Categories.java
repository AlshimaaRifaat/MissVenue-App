package com.ibsvalleyn.missvenue.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product_Categories {

    @Expose
    @SerializedName("products")
    public List<Products> products;

    @Expose
    @SerializedName("categories")
    public List<SupCategories> supCategories;

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }

    public List<SupCategories> getSupCategories() {
        return supCategories;
    }

    public void setSupCategories(List<SupCategories> supCategories) {
        this.supCategories = supCategories;
    }
}