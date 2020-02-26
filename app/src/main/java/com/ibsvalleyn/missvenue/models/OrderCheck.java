package com.ibsvalleyn.missvenue.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderCheck {

    @SerializedName("new_id")
    @Expose
    private int new_id;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("user_message")
    @Expose
    private String user_message;

    @SerializedName("result")
    @Expose
    private boolean result;

    @SerializedName("falseType")
    @Expose
    private int falseType;

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

    public String getUser_message() {
        return user_message;
    }

    public void setUser_message(String user_message) {
        this.user_message = user_message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getFalseType() {
        return falseType;
    }

    public void setFalseType(int falseType) {
        this.falseType = falseType;
    }
}
