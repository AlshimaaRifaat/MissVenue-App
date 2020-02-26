package com.ibsvalleyn.missvenue.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class Password_recovery_model {


    @Expose
    @SerializedName("user_message")
    private String userMessage;
    @Expose
    @SerializedName("result")
    private boolean result;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("new_id")
    private int newId;

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNewId() {
        return newId;
    }

    public void setNewId(int newId) {
        this.newId = newId;
    }
}
