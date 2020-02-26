package com.ibsvalleyn.missvenue.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.models.ProductDetails;

import java.util.List;

public class DescriptionAdapter extends RecyclerView.Adapter<DescriptionAdapter.DescriptionViewHolder> {
    List<ProductDetails> productDetails;
    Context context;

    public DescriptionAdapter(Context context, List<ProductDetails> productDetails) {
        this.context = context;
        this.productDetails = productDetails;
    }

    @NonNull
    @Override
    public DescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.description_layout, parent, false);
        DescriptionViewHolder descriptionViewHolder = new DescriptionViewHolder(view);
        return descriptionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DescriptionViewHolder holder, int position) {
        holder.name.setText(productDetails.get(0).getSpecificationAttributes().get(position).getName() + ":");
        holder.value.setText(productDetails.get(0).getSpecificationAttributes().get(position).getValue());
    }

    @Override
    public int getItemCount() {
        if (productDetails != null) {
            return productDetails.get(0).getSpecificationAttributes().size();

        } else {
            return 0;
        }
    }

    public class DescriptionViewHolder extends RecyclerView.ViewHolder {
        TextView name, value;
        Typeface customTypeOne = Typeface.createFromAsset(itemView.getContext().getAssets(), "Ubuntu-R.ttf");

        public DescriptionViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            value = itemView.findViewById(R.id.value);
            name.setTypeface(customTypeOne);
            value.setTypeface(customTypeOne);
        }
    }
}
