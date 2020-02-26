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
import com.ibsvalleyn.missvenue.helper.Selection_Brands;
import com.ibsvalleyn.missvenue.models.search.Brands;

import java.util.List;

public class RectangleAdapter extends RecyclerView.Adapter<RectangleAdapter.NewsHolder> {

    private List<Brands> modelList;

    private List<String> ids = Selection_Brands.selection_brands().getIds();


    public RectangleAdapter(List<Brands> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    private Context context;


    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rectangle_row, parent, false);

        return new NewsHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int i) {

        holder.bt_un_selected.setText(modelList.get(i).getName());
        holder.bt_selected.setText(modelList.get(i).getName());


        holder.bt_selected.setOnClickListener(view -> {
            Log.i("dsadsadasd", "onBindViewHolder: " + i + true);

            for (int ii = 0; ii <ids.size() ; ii++) {
                if (ids.get(ii).equals(String.valueOf(modelList.get(i).getId()))){
                    ids.remove(String.valueOf(modelList.get(i).getId()));

                }
            }
//            if (ids.size() <= 1) {
//                ids.clear();
//
//            } else if (ids.size()==2){
//                ids.remove(i-1);
//
//            }
//            else {
//                ids.remove(i);
//            }
            holder.bt_un_selected.setVisibility(View.VISIBLE);
            holder.bt_selected.setVisibility(View.GONE);

        });
        holder.bt_un_selected.setOnClickListener(view -> {

            ids.add(String.valueOf(modelList.get(i).getId()));
            Log.i("dsadsadasd", "onBindViewHolder: " + i + false);


            holder.bt_selected.setVisibility(View.VISIBLE);

            holder.bt_un_selected.setVisibility(View.GONE);

        });

    }

    @Override
    public int getItemCount() {
        return modelList != null ? modelList.size() : 0;

    }


    class NewsHolder extends RecyclerView.ViewHolder {

        private TextView bt_selected,bt_un_selected;

        NewsHolder(@NonNull View itemView) {
            super(itemView);

            bt_selected = itemView.findViewById(R.id.bt_selected);
            bt_un_selected = itemView.findViewById(R.id.bt_un_selected);

        }}


}
