package com.ibsvalleyn.missvenue.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrdersModel {

    @SerializedName("Id")
    @Expose
    private int id;

    public String getPayment_Status_Label() {
        return Payment_Status_Label;
    }

    public void setPayment_Status_Label(String payment_Status_Label) {
        Payment_Status_Label = payment_Status_Label;
    }

    @SerializedName("Payment_Status_Label")
    @Expose
    private String Payment_Status_Label;

    @SerializedName("PaymentMethodType")
    @Expose
    private int PaymentMethodType;

    @SerializedName("Payment_Status")
    @Expose
    private Integer Payment_Status;

    public Integer getPayment_Status() {
        return Payment_Status;
    }

    public void setPayment_Status(Integer payment_Status) {
        Payment_Status = payment_Status;
    }

    @SerializedName("PaymentMethodName")
    @Expose
    private String PaymentMethodName;

    public String getPaymentMethodName() {
        return PaymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        PaymentMethodName = paymentMethodName;
    }

    @SerializedName("OrderTotal")
    @Expose
    private float OrderTotal;

    @SerializedName("Items")
    @Expose
    private List<Items1> items;

    @SerializedName("Sub_Total")
    @Expose
    private float Sub_Total;

    @SerializedName("Tax_rate")
    @Expose
    private float Tax_rate;

    @SerializedName("shipping_rate")
    @Expose
    private float shipping_rate;

    public List<Items1> getItems() {
        return items;
    }

    public void setItems(List<Items1> items) {
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getOrderTotal() {
        return OrderTotal;
    }

    public void setOrderTotal(float orderTotal) {
        OrderTotal = orderTotal;
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

    public int getPaymentMethodType() {
        return PaymentMethodType;
    }

    public void setPaymentMethodType(int paymentMethodType) {
        PaymentMethodType = paymentMethodType;
    }
}