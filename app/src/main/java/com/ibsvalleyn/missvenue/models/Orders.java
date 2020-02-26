package com.ibsvalleyn.missvenue.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Orders {

    @SerializedName("Id")
    @Expose
    private int id;

    @SerializedName("Created_At")
    @Expose
    private String date;

    @SerializedName("Order_Status_Label")
    @Expose
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void getStatus(String status) {
        this.status = status;
    }
}
