package com.ibsvalleyn.missvenue.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Add_to_cart {

    @SerializedName("new_id")
    @Expose
    private int new_id;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("result")
    @Expose
    private boolean result;

    @SerializedName("total_cart_amount")
    @Expose
    private int total_cart_amount;

    @SerializedName("total_wish_amount")
    @Expose
    private int total_wish_amount;

    @SerializedName("user_message")
    @Expose
    private String user_message;

    @SerializedName("total_amount")
    @Expose
    private String total_amount;

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public int getNew_id() {
        return new_id;
    }

    public void setNew_id(int new_id) {
        this.new_id = new_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getUser_message() {
        return user_message;
    }

    public void setUser_message(String user_message) {
        this.user_message = user_message;
    }

    public int getTotal_cart_amount() {
        return total_cart_amount;
    }

    public void setTotal_cart_amount(int total_cart_amount) {
        this.total_cart_amount = total_cart_amount;
    }

    public int getTotal_wish_amount() {
        return total_wish_amount;
    }

    public void setTotal_wish_amount(int total_wish_amount) {
        this.total_wish_amount = total_wish_amount;
    }
}
