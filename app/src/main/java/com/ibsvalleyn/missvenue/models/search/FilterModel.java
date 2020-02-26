package com.ibsvalleyn.missvenue.models.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilterModel {
    @Expose
    @SerializedName("RefineSearch")
    private RefineSearch RefineSearch;
    @Expose
    @SerializedName("categories")
    private List<Categories> categories;
    @Expose
    @SerializedName("Id")
    private int Id;
    @Expose
    @SerializedName("Brands")
    private List<Brands> Brands;
    @Expose
    @SerializedName("products")
    private List<Products> products;

    public RefineSearch getRefineSearch() {
        return RefineSearch;
    }

    public void setRefineSearch(RefineSearch RefineSearch) {
        this.RefineSearch = RefineSearch;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public List<Brands> getBrands() {
        return Brands;
    }

    public void setBrands(List<Brands> Brands) {
        this.Brands = Brands;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }
}
