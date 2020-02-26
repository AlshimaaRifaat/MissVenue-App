package com.ibsvalleyn.missvenue.adapters;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.activities.MainActivity;
import com.ibsvalleyn.missvenue.activities.ProductOfCategories;
import com.ibsvalleyn.missvenue.helper.Static_variables;
import com.ibsvalleyn.missvenue.models.Categories;

import java.util.List;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ProductViewHolder> {
    private int id;
    MainActivity context;
    private List<Categories> categories;

    SharedPreferences dataSaver;
    int customer_id;

    public CategoryAdapter(MainActivity context, List<Categories> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_row, viewGroup, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Toast toast = new Toast(context);

        dataSaver = getDefaultSharedPreferences(context);
        customer_id = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);
        Glide.with(context).load(categories.get(position).getPictureurl()).into(holder.imageCategory);
        holder.nameCategory.setText(categories.get(position).getName());

        holder.parent.setOnClickListener(view -> {

            Intent intent = new Intent(context, ProductOfCategories.class);
            intent.putExtra("id", categories.get(position).getCategoryId());
            //intent.putExtra("sectors_name", sector_name);
            dataSaver.edit()
                    .putInt(Static_variables.Category_id, categories.get(position).getCategoryId())
                    .apply();

            dataSaver.edit()
                    .putString("sectors_name", categories.get(position).getName())
                    .apply();


            context.startActivity(intent);

           // context.finish();

        });


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView nameCategory;
        ImageView imageCategory;
        LinearLayout parent;
//        TextView product_price, product_name, product_price1;
//        ImageView product_image, addToCart, wishlist;
////        CardView product_card;
//        Typeface customTypeOne = Typeface.createFromAsset(itemView.getContext().getAssets(), "Ubuntu-B.ttf");
//        Typeface customTypeOne1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "Ubuntu-R.ttf");

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nameCategory = itemView.findViewById(R.id.nameCategory);
            imageCategory = itemView.findViewById(R.id.imageCategory);
            parent = itemView.findViewById(R.id.parent);
//            product_image = itemView.findViewById(R.id.product_image);
//            addToCart = itemView.findViewById(R.id.add_to_cart);
//            wishlist = itemView.findViewById(R.id.wishlist);
//            product_card = itemView.findViewById(R.id.product_card);
//            product_price1 = itemView.findViewById(R.id.product_price1);
//            product_name.setTypeface(customTypeOne1);
//            product_price1.setTypeface(customTypeOne);
//            product_price.setTypeface(customTypeOne);
        }
    }


}