package com.ibsvalleyn.missvenue.helper;

import java.util.ArrayList;

public class Selection_Multi_Selected {


    private static final Selection_Multi_Selected selection_multi_selected = new Selection_Multi_Selected();
    private ArrayList<String> ids = new ArrayList<>();


    public static Selection_Multi_Selected selection_multi_selected() {

        return selection_multi_selected;
    }

    public ArrayList<String> getIds() {
        return ids;
    }

}
