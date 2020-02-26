package com.ibsvalleyn.missvenue.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewModel {
    @Expose
    @SerializedName("Review_Lists")
    private List<Review_Lists> Review_Lists;

    @Expose
    @SerializedName("Total_Rate")
    private double Total_Rate;

    public List<Review_Lists> getReview_Lists() {
        return Review_Lists;
    }

    public void setReview_Lists(List<Review_Lists> Review_Lists) {
        this.Review_Lists = Review_Lists;
    }

    public double getTotal_Rate() {
        return Total_Rate;
    }

    public void setTotal_Rate(double Total_Rate) {
        this.Total_Rate = Total_Rate;
    }

    public static class Review_Lists {
        @Expose
        @SerializedName("Review")
        private String Review;
        @Expose

        @SerializedName("CustomerName")
        private String CustomerName;

        @SerializedName("Rate")
        private int rate;

        public String getReview() {
            return Review;
        }

        public void setReview(String Review) {
            this.Review = Review;
        }

        public int getRate() {
            return rate;
        }

        public void setRate(int rate) {
            this.rate = rate;
        }

        public String getCustomerName() {
            return CustomerName;
        }

        public void setCustomerName(String CustomerName) {
            this.CustomerName = CustomerName;
        }
    }
}