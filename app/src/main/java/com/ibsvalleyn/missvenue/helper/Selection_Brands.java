package com.ibsvalleyn.missvenue.helper;

import java.util.ArrayList;

public class Selection_Brands {


    private static final Selection_Brands selection_brands = new Selection_Brands();
    private ArrayList<String> ids = new ArrayList<>();


    public static Selection_Brands selection_brands() {

        return selection_brands;
    }

    public ArrayList<String> getIds() {
        return ids;
    }

//    public void setIds(ArrayList<Integer> ids) {
//        this.ids = ids;
//    }


}
