package com.ibsvalleyn.missvenue.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeModel {
    @Expose
    @SerializedName("Categories")
    private List<Categories> categoriesList;
    @Expose
    @SerializedName("Products")
    public List<Products> products;

    public List<Categories> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<Categories> categoriesList) {
        this.categoriesList = categoriesList;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }
}
