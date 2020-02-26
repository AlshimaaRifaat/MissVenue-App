package com.ibsvalleyn.missvenue.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShoppingCarts  {

    @SerializedName("Total_Amount")
    @Expose
    private float total;

    @SerializedName("Sub_Total")
    @Expose
    private float Sub_Total;

    @SerializedName("Tax_rate")
    @Expose
    private float Tax_rate;

    @SerializedName("shipping_rate")
    @Expose
    private float shipping_rate;

    @SerializedName("Total_Quantity")
    @Expose
    private int Total_Quantity;

    public int getTotal_Quantity() {
        return Total_Quantity;
    }

    public void setTotal_Quantity(int total_Quantity) {
        Total_Quantity = total_Quantity;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    @SerializedName("Items")
    @Expose
    private List<Items> items;

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public float getSub_Total() {
        return Sub_Total;
    }

    public void setSub_Total(float sub_Total) {
        Sub_Total = sub_Total;
    }

    public float getTax_rate() {
        return Tax_rate;
    }

    public void setTax_rate(float tax_rate) {
        Tax_rate = tax_rate;
    }

    public float getShipping_rate() {
        return shipping_rate;
    }

    public void setShipping_rate(float shipping_rate) {
        this.shipping_rate = shipping_rate;
    }
}