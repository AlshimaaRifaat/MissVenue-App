package com.ibsvalleyn.missvenue.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Categories {

    @Expose
    @SerializedName("PictureUrl")
    private String pictureurl;
    @Expose
    @SerializedName("PictureId")
    private int pictureid;
    @Expose
    @SerializedName("MimeType")
    private String mimetype;
    @Expose
    @SerializedName("Name")
    private String name;
    @Expose
    @SerializedName("ShowOnHomePage")
    private boolean showonhomepage;
    @Expose
    @SerializedName("IncludeInTopMenu")
    private boolean includeintopmenu;
    @Expose
    @SerializedName("Category_Id")
    private int categoryId;

    public String getPictureurl() {
        return pictureurl;
    }

    public void setPictureurl(String pictureurl) {
        this.pictureurl = pictureurl;
    }

    public int getPictureid() {
        return pictureid;
    }

    public void setPictureid(int pictureid) {
        this.pictureid = pictureid;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getShowonhomepage() {
        return showonhomepage;
    }

    public void setShowonhomepage(boolean showonhomepage) {
        this.showonhomepage = showonhomepage;
    }

    public boolean getIncludeintopmenu() {
        return includeintopmenu;
    }

    public void setIncludeintopmenu(boolean includeintopmenu) {
        this.includeintopmenu = includeintopmenu;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
