package com.ibsvalleyn.missvenue.models.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Brands {
    @Expose
    @SerializedName("Name")
    private String Name;
    @Expose
    @SerializedName("id")
    private int id;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
