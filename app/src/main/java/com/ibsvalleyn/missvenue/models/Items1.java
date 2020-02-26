package com.ibsvalleyn.missvenue.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Items1 {

    @SerializedName("Quantity")
    @Expose
    private int quantity;

    @SerializedName("Prodcut")
    @Expose
    private ProductOrder product;

    @SerializedName("PriceExclTax")
    @Expose
    private double priceExclTax;

    public double getPriceExclTax() {
        return priceExclTax;
    }

    public void setPriceExclTax(double priceExclTax) {
        this.priceExclTax = priceExclTax;
    }

    public ProductOrder getProduct() {
        return product;
    }

    public void setProduct(ProductOrder product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
