//package com.ibsvalleyn.missvenue.adapters;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.ibsvalleyn.missvenue.R;
//import com.ibsvalleyn.missvenue.activities.ProductOfCategories;
//
//import java.util.List;
//
//public class OvalAdapter extends RecyclerView.Adapter<OvalAdapter.NewsHolder> {
//
//    private List<Colors> modelList;
//
//
//
//    public OvalAdapter(List<Colors> modelList, ProductOfCategories context) {
///*
//        this.modelList = modelList;
//*/
//        this.context = context;
//    }
//
//    private ProductOfCategories context;
//
//
//    @NonNull
//    @Override
//    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.oval_row, parent, false);
//
//        return new NewsHolder(view);
//    }
//
//
//    @Override
//    public void onBindViewHolder(@NonNull NewsHolder holder, int i) {
////        loadImage(context,modelList.get(i).getImage().get(0),holder.image_row_news);
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return modelList != null ? modelList.size() : 0;
//
//    }
//
//
//    class NewsHolder extends RecyclerView.ViewHolder {
//
//        private Button bt_selected;
//
//        NewsHolder(@NonNull View itemView) {
//            super(itemView);
//
//            bt_selected = itemView.findViewById(R.id.bt_selected);
//
//        }}
//
//
//}
