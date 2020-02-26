package com.ibsvalleyn.missvenue.adapters;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.activities.ProductOfCategories;
import com.ibsvalleyn.missvenue.activities.Product_details;
import com.ibsvalleyn.missvenue.api.RetrofitClient;
import com.bumptech.glide.Glide;
import com.ibsvalleyn.missvenue.data_room.AppDatabase;
import com.ibsvalleyn.missvenue.helper.Static_variables;
import com.ibsvalleyn.missvenue.models.Add_to_cart;
import com.ibsvalleyn.missvenue.models.FavoriteModel;
import com.ibsvalleyn.missvenue.models.Products;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.ibsvalleyn.missvenue.helper.AppFunctions.Rails;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> implements Filterable {
    private int id;
    ProductOfCategories context;
    List<Products> products;
    List<Products> filterProduct;
    SharedPreferences dataSaver;
    int customer_id;
    int wishlist_;
    TextView notification_counter;
    AppDatabase db;
    FavoriteModel favoriteModel;

    public ProductAdapter(ProductOfCategories context, List<Products> products, TextView notification_counter, int id, AppDatabase db) {
        this.context = context;
        this.products = products;
        this.filterProduct = products;
        this.notification_counter = notification_counter;
        this.id = id;
        this.db = db;

    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_layout, viewGroup, false);
        return new ProductViewHolder(view);
    }


    private Boolean ifExist(Integer id) {
        favoriteModel = new FavoriteModel();

        try {

            //  modelList = db.favoriteDao().fetchByIdList(id);
            favoriteModel = db.favoriteDao().fetchById(id);
            Log.i(TAG, "fetchById: " + id);
            Log.i(TAG, "ifExist: " + new Gson().toJson(favoriteModel));


            if (favoriteModel.getId() != id) {
                Log.i(TAG, "else habl2: ");

                return true;

            }

        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private void delete(int id) {
        FavoriteModel sonyRec = db.favoriteDao().fetchById(id); // Read first
        db.favoriteDao().delete(sonyRec);

    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Toast toast = new Toast(context);

        dataSaver = getDefaultSharedPreferences(context);
        customer_id = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);
        favoriteModel = new FavoriteModel();

        favoriteModel.setName(filterProduct.get(position).getName());
        favoriteModel.setId(filterProduct.get(position).getId());
        favoriteModel.setDescription(filterProduct.get(position).getDescription());
        favoriteModel.setPrice(filterProduct.get(position).getPrice());
        favoriteModel.setSellingPrice(filterProduct.get(position).getSellingPrice());
        favoriteModel.setImage_url(filterProduct.get(position).getImage_url());
        boolean b11 = ifExist(filterProduct.get(position).getId());

        Log.i(TAG, "b11: " + b11);
        if (b11) {
            holder.wishlist.setImageResource(R.drawable.heart);

        } else {

            holder.wishlist.setImageResource(R.drawable.heart_add);

        }
        if (filterProduct.get(position).getIswishlist() != null) {
            wishlist_ = filterProduct.get(position).getIswishlist();
        } else {
            wishlist_ = 0;
        }
//        if (wishlist_ == 1) {
//            holder.wishlist.setImageResource(R.drawable.heart);
//        } else {
//            holder.wishlist.setImageResource(R.drawable.heart_add);
//        }

        if (filterProduct.get(position).getName().length() < 20) {
            holder.product_name.setText(filterProduct.get(position).getName());

        } else {
            String substring = filterProduct.get(position).getName().substring(0, 18) + ". . .";
            holder.product_name.setText(substring);

        }

        holder.product_price1.setText(Rails(context, filterProduct.get(position).getPrice()));
        holder.product_price1.setPaintFlags(holder.product_price1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        Glide.with(context).load(filterProduct.get(position).getImage_url()).into(holder.product_image);
        holder.product_price.setText(Rails(context, filterProduct.get(position).getSellingPrice()));

        if (products.get(position).getPrice() > products.get(position).getSellingPrice()) {
            holder.product_price1.setVisibility(View.VISIBLE);
            holder.product_price1.setTextSize(10);
            holder.product_price.setTextSize(10);
        }

        holder.product_image.setOnClickListener(view -> {
            Intent intent = new Intent(context, Product_details.class);
            intent.putExtra("product_id", filterProduct.get(position).getId());
            intent.putExtra("product_name", filterProduct.get(position).getName());
            intent.putExtra("flageformProOF", 2);
            intent.putExtra("idOfProductOf", id);
            dataSaver.edit()
                    .putInt("isSector", 0)
                    .apply();
            context.startActivity(intent);
            //   context.finish();
        });

        holder.wishlist.setOnClickListener(view -> {

            dataSaver.edit()
                    .putInt("flag", 1)
                    .apply();

            int productId = filterProduct.get(position).getId();
            Log.e("wishlist_", "wishlist_ " + wishlist_);

            if (filterProduct.get(position).getIswishlist() != null) {
                wishlist_ = filterProduct.get(position).getIswishlist();
            } else {
                wishlist_ = 0;
            }

            if (wishlist_ == 1) {
                filterProduct.get(position).setIswishlist(0);

                Call<Add_to_cart> addEVEnt_call = RetrofitClient.getInstance(context)
                        .updateCart(2, customer_id, productId, 0);
                Log.e("customer_id", "customer_id " + customer_id);
                Log.e("productId", "productId " + productId);

                addEVEnt_call.enqueue(new Callback<Add_to_cart>() {

                    @Override
                    public void onResponse(Call<Add_to_cart> call, Response<Add_to_cart> response) {

                        if (response.isSuccessful()) {

                            if (response.body().isResult() == true) {
                                holder.wishlist.setImageResource(R.drawable.heart_add);

                                Log.e("TAG", "isSuccessful");

                                if (toast != null) {
                                    toast.cancel();

                                    Toast.makeText(context, response.body().getUser_message(), Toast.LENGTH_SHORT).show();
                                }
                                if (favoriteModel != null) {


                                    delete(filterProduct.get(position).getId());
                                }
                            } else {

                                if (toast != null) {
                                    toast.cancel();

                                    Toast.makeText(context, response.body().getUser_message(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Log.e("TAG", "notSuccessful");

                            if (toast != null) {
                                toast.cancel();

                                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<Add_to_cart> call, Throwable t) {
                        Log.e("TAG ", "onFailure " + t.getMessage());

                        if (toast != null) {
                            toast.cancel();

                            Toast.makeText(context, "Check internet connection!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } else {
                filterProduct.get(position).setIswishlist(1);
                Call<Add_to_cart> addEVEnt_call = RetrofitClient.getInstance(context)
                        .addToCart(2, customer_id, productId, 1);
                Log.e("customer_id", "customer_id " + customer_id);
                Log.e("productId", "productId " + productId);

                addEVEnt_call.enqueue(new Callback<Add_to_cart>() {

                    @Override
                    public void onResponse(Call<Add_to_cart> call, Response<Add_to_cart> response) {

                        if (response.isSuccessful()) {

                            if (response.body().isResult() == true) {
                                holder.wishlist.setImageResource(R.drawable.heart);

                                Log.e("TAG", "isSuccessful");

                                if (toast != null) {
                                    toast.cancel();

                                }
                                favoriteModel = new FavoriteModel();

                                favoriteModel.setName(filterProduct.get(position).getName());
                                favoriteModel.setId(filterProduct.get(position).getId());
                                favoriteModel.setDescription(filterProduct.get(position).getDescription());
                                favoriteModel.setPrice(filterProduct.get(position).getPrice());
                                favoriteModel.setSellingPrice(filterProduct.get(position).getSellingPrice());
                                favoriteModel.setImage_url(filterProduct.get(position).getImage_url());

                                if (favoriteModel != null) {
                                    Log.e("TAG", "insert ");
                                    db.favoriteDao().insert(favoriteModel);
                                }

                                Toast.makeText(context, response.body().getUser_message(), Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(context, response.body().getUser_message(), Toast.LENGTH_SHORT).show();


                            }
                        } else {
                            Log.e("TAG", "notSuccessful");

                            if (toast != null) {
                                toast.cancel();

                                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<Add_to_cart> call, Throwable t) {
                        Log.e("TAG ", "onFailure " + t.getMessage());

                        if (toast != null) {
                            toast.cancel();

                            Toast.makeText(context, "Check internet connection!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        holder.addToCart.setOnClickListener(view -> {
            int productId = filterProduct.get(position).getId();
            Call<Add_to_cart> addEVEnt_call = RetrofitClient.getInstance(context)
                    .addToCart(1, customer_id, productId, 1);
            Log.e("customer_id", "customer_id " + customer_id);
            Log.e("productId", "productId " + productId);

            addEVEnt_call.enqueue(new Callback<Add_to_cart>() {

                @Override
                public void onResponse(Call<Add_to_cart> call, Response<Add_to_cart> response) {

                    if (response.isSuccessful()) {

                        if (response.body().isResult() == true) {
                            Log.e("TAG", "isSuccessful");

                            dataSaver.edit()
                                    .putInt("abdo", response.body().getTotal_cart_amount())
                                    .apply();
                            Log.e("TAG", "getTotal_cart_amount " + response.body().getTotal_cart_amount());

                            notification_counter.setText(String.valueOf(response.body().getTotal_cart_amount()));

                            if (toast != null) {
                                toast.cancel();
                                Toast.makeText(context, response.body().getUser_message(), Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            if (toast != null) {
                                toast.cancel();

                                Toast.makeText(context, response.body().getUser_message(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Log.e("TAG", "notSuccessful");

                        if (toast != null) {
                            toast.cancel();

                            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Add_to_cart> call, Throwable t) {
                    Log.e("TAG ", "onFailure " + t.getMessage());

                    if (toast != null) {
                        toast.cancel();

                        Toast.makeText(context, "Check internet connection!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return filterProduct.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String word = constraint.toString();
                Log.i("Word", "word " + word);

                if (word.isEmpty()) {

                    filterProduct = products;
                } else {

                    List<Products> mine = new ArrayList<>();
//                    for (int i = 0; i < products.size(); i++) {
//                        for (SpecificationAttribute itemModel : products.get(i).getAttribute()) {
//                            if (itemModel.getValue().contains(word)) {
//                                mine.add(products.get(i));
//
//                            }
//                        }
//
//                    }
                    Log.i("Word", "mine " + new Gson().toJson(mine));

                    filterProduct = mine;
                }

                FilterResults results = new FilterResults();
                results.values = filterProduct;
                Log.i("Word", "mine " + new Gson().toJson(results));

                return results;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterProduct = (List<Products>) results.values;
                Log.i("Word", "mine " + new Gson().toJson(results));
                notifyDataSetChanged();
            }
        };
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView product_price, product_name, product_price1;
        ImageView product_image, addToCart, wishlist;
        CardView product_card;
        Typeface customTypeOne = Typeface.createFromAsset(itemView.getContext().getAssets(), "Ubuntu-B.ttf");
        Typeface customTypeOne1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "Ubuntu-R.ttf");

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            product_price = itemView.findViewById(R.id.product_price);
            product_name = itemView.findViewById(R.id.product_name);
            product_image = itemView.findViewById(R.id.product_image);
            addToCart = itemView.findViewById(R.id.add_to_cart);
            wishlist = itemView.findViewById(R.id.wishlist);
            product_card = itemView.findViewById(R.id.product_card);
            product_price1 = itemView.findViewById(R.id.product_price1);
            product_name.setTypeface(customTypeOne1);
            product_price1.setTypeface(customTypeOne);
            product_price.setTypeface(customTypeOne);
        }
    }

    public void addInRecycle(List<Products> datum) {
        for (Products dt : datum) {
            filterProduct.add(dt);
        }
        notifyDataSetChanged();
    }
}