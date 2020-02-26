package com.ibsvalleyn.missvenue.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StaticsModel {

    @SerializedName("Id")
    @Expose
    private int id;

    @SerializedName("Title")
    @Expose
    private String title;

    @SerializedName("Body")
    @Expose
    private String body;

    @SerializedName("Image_url")
    @Expose
    private String image_url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
