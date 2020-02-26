
package com.ibsvalleyn.missvenue.models.OrderView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RichContent {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("SystemName")
    @Expose
    private String systemName;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Body")
    @Expose
    private String body;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
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

}
