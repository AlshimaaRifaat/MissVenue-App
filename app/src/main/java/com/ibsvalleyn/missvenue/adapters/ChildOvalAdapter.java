package com.ibsvalleyn.missvenue.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.helper.Hosting;
import com.ibsvalleyn.missvenue.helper.Selection_Multi_Selected;
import com.ibsvalleyn.missvenue.models.search.Values;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChildOvalAdapter extends RecyclerView.Adapter<ChildOvalAdapter.ChildHolder> {
    private List<String> ids = Selection_Multi_Selected.selection_multi_selected().getIds();

    public static final int CHECKED = 1;
    public static final int NOT_CHECKED = 0;
    private int id;
    private String value;
    private Hosting hosting1 = new Hosting();
    List<Hosting> list = new ArrayList<>();
    private String SS;

    public ChildOvalAdapter(List<Values> dataList, Context context, int id) {
        this.dataList = dataList;
        this.context = context;
        this.id = id;
    }

    List<Values> dataList;
    private Context context;

    @NonNull
    @Override
    public ChildHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new ChildHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.child_oval_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChildHolder holder, int i) {

        Values data = dataList.get(i);
        list = new ArrayList<>();
        Log.i("SAdsadsa", "onBindViewHolder: ." + id);
        holder.bt_un_selected.setText(dataList.get(i).getValue());
        holder.bt_selected.setText(dataList.get(i).getValue());
        holder.bt_selected.setOnClickListener(view -> {

            value = dataList.get(i).getValue();
            Log.e("ZSCZXc", "onBindViewHolder: " + dataList.get(i).getValue());
            holder.bt_un_selected.setVisibility(View.VISIBLE);
            holder.bt_selected.setVisibility(View.GONE);
            hosting1.setNameList(dataList.get(i).getValue());
            hosting1.setId(id);
            list.add(hosting1);
            HashMap<Integer, String> result1 = new HashMap<>();
            result1 = new HashMap<>();
            for (Hosting hosting : list) {
                SS = result1.put(hosting.getId(), hosting.getNameList());
            }
            if (ids.size() <= 1)
            {
                ids.clear();

            }else{
                ids.remove(result1.toString());

            }

            System.out.println("Result 1 : " + result1);
            Log.e("asdsadsad", "onBindViewHolder: " + result1);

        });
        holder.bt_un_selected.setOnClickListener(view -> {
            Log.e("ZSCZXc", "onBindViewHolder: " + dataList.get(i).getValue());
            value = dataList.get(i).getValue();

            holder.bt_selected.setVisibility(View.VISIBLE);

            holder.bt_un_selected.setVisibility(View.GONE);
            hosting1.setNameList(dataList.get(i).getValue());
            hosting1.setId(id);
            list.add(hosting1);
            HashMap<Integer, String> result1 = new HashMap<>();

            result1 = new HashMap<>();

            for (Hosting hosting : list) {
                SS =   result1.put(hosting.getId(), hosting.getNameList());
            }
            Log.e("asdsadsad", "onBindViewHolder: " + result1);
            ids.add(result1.toString());

        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ChildHolder extends RecyclerView.ViewHolder {
        private TextView bt_un_selected, bt_selected;

        public ChildHolder(@NonNull View itemView) {
            super(itemView);
            bt_selected = itemView.findViewById(R.id.bt_selected);
            bt_un_selected = itemView.findViewById(R.id.bt_un_selected);

        }
    }
}
