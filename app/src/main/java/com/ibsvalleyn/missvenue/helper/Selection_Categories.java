package com.ibsvalleyn.missvenue.helper;

import java.util.ArrayList;

public class Selection_Categories {


    private static final Selection_Categories selection_categories = new Selection_Categories();
    private ArrayList<String> ids = new ArrayList<>();


    public static Selection_Categories selection_categories() {

        return selection_categories;
    }

    public ArrayList<String> getIds() {
        return ids;
    }

//    public void setIds(ArrayList<Integer> ids) {
//        this.ids = ids;
//    }



}
