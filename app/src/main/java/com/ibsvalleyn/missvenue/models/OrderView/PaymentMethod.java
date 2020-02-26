
package com.ibsvalleyn.missvenue.models.OrderView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentMethod {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Fixed_Amount_Fees")
    @Expose
    private Integer fixedAmountFees;
    @SerializedName("Percentage_Amount_Fees")
    @Expose
    private Integer percentageAmountFees;
    private int flag;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getFixedAmountFees() {
        return fixedAmountFees;
    }

    public void setFixedAmountFees(Integer fixedAmountFees) {
        this.fixedAmountFees = fixedAmountFees;
    }

    public Integer getPercentageAmountFees() {
        return percentageAmountFees;
    }

    public void setPercentageAmountFees(Integer percentageAmountFees) {
        this.percentageAmountFees = percentageAmountFees;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
