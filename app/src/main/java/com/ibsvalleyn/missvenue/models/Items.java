package com.ibsvalleyn.missvenue.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Items implements Serializable {

    @SerializedName("Quantity")
    @Expose
    private int quantity;

    @SerializedName("Product")
    @Expose
    private Products product;

    @SerializedName("totalprice")
    @Expose
    private double totalprice;
    @SerializedName("totalselling")
    @Expose
    private double totalselling;

    public double getTotalselling() {
        return totalselling;
    }

    public void setTotalselling(double totalselling) {
        this.totalselling = totalselling;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
