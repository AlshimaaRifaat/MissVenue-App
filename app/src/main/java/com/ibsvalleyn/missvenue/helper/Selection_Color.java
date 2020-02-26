package com.ibsvalleyn.missvenue.helper;

import java.util.ArrayList;

public class Selection_Color {


    private static final Selection_Color selection_color = new Selection_Color();
    private ArrayList<String> ids = new ArrayList<>();


    public static Selection_Color selection_color() {

        return selection_color;
    }

    public ArrayList<String> getIds() {
        return ids;
    }

//    public void setIds(ArrayList<Integer> ids) {
//        this.ids = ids;
//    }


}
