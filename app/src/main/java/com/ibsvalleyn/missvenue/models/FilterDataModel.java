package com.ibsvalleyn.missvenue.models;

public class FilterDataModel {
    int customerId;
    int category_id;
   String subcategoryfilter;
   String brandfilter;
   double minprice;
   double maxprice;
   String attributefilter;

    public FilterDataModel(int customerId, int category_id, String subcategoryfilter, String brandfilter, double minprice, double maxprice, String attributefilter) {
        this.customerId = customerId;
        this.category_id = category_id;
        this.subcategoryfilter = subcategoryfilter;
        this.brandfilter = brandfilter;
        this.minprice = minprice;
        this.maxprice = maxprice;
        this.attributefilter = attributefilter;
    }
}
