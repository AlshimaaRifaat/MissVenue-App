//package com.ibsvalleyn.missvenue.adapters;
//
//import android.graphics.Color;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.ibsvalleyn.missvenue.R;
//import com.ibsvalleyn.missvenue.activities.ProductOfCategories;
//import com.ibsvalleyn.missvenue.helper.Selection_Color;
//
//import net.igenius.customcheckbox.CustomCheckBox;
//
//import java.util.List;
//
//public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.NewsHolder> {
//
//    private List<Colors> modelList;
//
//    public static final int CHECKED = 1;
//    public static final int NOT_CHECKED = 0;
//    private List<String> ids = Selection_Color.selection_color().getIds();
//
//    public ColorAdapter(List<Colors> modelList, ProductOfCategories context) {
//        this.modelList = modelList;
//        this.context = context;
//    }
//
//    private ProductOfCategories context;
//
//
//    @NonNull
//    @Override
//    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.color_row, parent, false);
//
//        return new NewsHolder(view);
//    }
//
//
//    @Override
//    public void onBindViewHolder(@NonNull NewsHolder holder, int i) {
//
//        holder.item.setCheckedColor(Color.parseColor(modelList.get(i).getColorrgb()));
//        holder.item.setUnCheckedColor(Color.parseColor(modelList.get(i).getColorrgb()));
//
//
//
//        holder.item.setOnCheckedChangeListener((checkBox, isChecked) ->{
//            if (isChecked) {
//                modelList.get(i).setIsChecked(CHECKED);
//
//                ids.add(modelList.get(i).getName());
//
//
//            } else if (ids.size() <= 1) {
//                ids.clear();
//
//                modelList.get(i).setIsChecked(NOT_CHECKED);
//
//            } else {
//                modelList.get(i).setIsChecked(NOT_CHECKED);
//                ids.remove(modelList.get(i).getId());
//
//            }
//
//        });
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
//        private CustomCheckBox item;
//
//        NewsHolder(@NonNull View itemView) {
//            super(itemView);
//
//            item = itemView.findViewById(R.id.item);
//
//        }
//    }
//
//
//}
