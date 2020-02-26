package com.ibsvalleyn.missvenue.adapters;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.models.search.Filter_elements;
import com.ibsvalleyn.missvenue.models.search.Values;

import java.util.List;


public class ParentOvalAdapter extends RecyclerView.Adapter<ParentOvalAdapter.ParentHolder> {
    private List<Filter_elements> modelList;
    private Context context;

    public ParentOvalAdapter(List<Filter_elements> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ParentHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        return new ParentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.parent_oval_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ParentHolder holder, int i) {
        final RecyclerView rvChild = holder.childOval_rv;

        holder.size.setText(modelList.get(i).getName());

        List<Values> childObject = modelList.get(i).getValues();
        rvChild.setAdapter(new ChildOvalAdapter(childObject, context, modelList.get(i).getId()));
        holder.parentClick.setOnClickListener(v -> {
            Drawable img_down = context.getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp);
            Drawable img = context.getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp);

            if (rvChild.getVisibility() == View.VISIBLE) {
                rvChild.setVisibility(View.GONE);
                holder.size.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                rvChild.setAdapter(new ChildOvalAdapter(childObject, context, modelList.get(i).getId()));

            } else {
                rvChild.setVisibility(View.VISIBLE);
                holder.size.setCompoundDrawablesWithIntrinsicBounds(img_down, null, null, null);

            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    class ParentHolder extends RecyclerView.ViewHolder {

        private RecyclerView childOval_rv;
        private TextView size;
        private LinearLayout parentClick;

        ParentHolder(@NonNull View itemView) {
            super(itemView);

            childOval_rv = itemView.findViewById(R.id.childOval_rv);
            parentClick = itemView.findViewById(R.id.parentClick);
            size = itemView.findViewById(R.id.size);
            childOval_rv.setLayoutManager(new GridLayoutManager(context, 2));
        }
    }
}