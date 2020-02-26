package com.ibsvalleyn.missvenue.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.activities.Product_details;
import com.bumptech.glide.Glide;
import com.ibsvalleyn.missvenue.api.RetrofitClient;
import com.ibsvalleyn.missvenue.data_room.AppDatabase;
import com.ibsvalleyn.missvenue.helper.Static_variables;
import com.ibsvalleyn.missvenue.models.Add_to_cart;
import com.ibsvalleyn.missvenue.models.FavoriteModel;
import com.ibsvalleyn.missvenue.models.SearchProductCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.ibsvalleyn.missvenue.helper.AppFunctions.Rails;
import static com.ibsvalleyn.missvenue.helper.AppFunctions.toastMessage;

public class ProductFromSearchAdapter extends RecyclerView.Adapter<ProductFromSearchAdapter.ProductViewHolder> {
    private String SearchWord;
    Context context;
    List<SearchProductCategory> searchProductCategories;
    SharedPreferences dataSaver;
    int customer_id;
    int quantaty1;
    String idSubCat;
    int wishlist_;
    AppDatabase db;
    FavoriteModel favoriteModel;
    private boolean b11;

    public ProductFromSearchAdapter(Context context, List<SearchProductCategory> searchProductCategories, String idSubCat, String SearchWord, AppDatabase db) {
        this.context = context;
        this.searchProductCategories = searchProductCategories;
        this.idSubCat = idSubCat;
        this.SearchWord = SearchWord;
        this.db = db;

    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_from_search, viewGroup, false);
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
        dataSaver = getDefaultSharedPreferences(context);
        Toast toast = new Toast(context);
        customer_id = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);
        Glide.with(context).load(searchProductCategories.get(position).getImageUrl()).into(holder.cart_image);
        favoriteModel = new FavoriteModel();

        favoriteModel.setName(searchProductCategories.get(position).getProductName());
        favoriteModel.setId(searchProductCategories.get(position).getId());
        favoriteModel.setDescription(searchProductCategories.get(position).getDescription());
        favoriteModel.setPrice(searchProductCategories.get(position).getPrice());
        favoriteModel.setSellingPrice(searchProductCategories.get(position).getSellingPrice());
        favoriteModel.setImage_url(searchProductCategories.get(position).getImageUrl());
        b11 = ifExist(searchProductCategories.get(position).getId());
        Log.i(TAG, "b11: " + b11);
        if (b11) {
            holder.wishlist.setImageResource(R.drawable.heart);

        } else {

            holder.wishlist.setImageResource(R.drawable.heart_add);

        }

        if (searchProductCategories.get(position).getProductName().length() < 24) {
            holder.product_name.setText(searchProductCategories.get(position).getProductName());

        } else {
            String substring = searchProductCategories.get(position).getProductName().substring(0, 22) + ". . .";
            holder.product_name.setText(substring);
        }

        if ((int) searchProductCategories.get(position).getPrice() > (int) searchProductCategories.get(position).getSellingPrice()) {
            holder.product_price1.setVisibility(View.VISIBLE);
        }
        holder.product_price1.setText(Rails(context, searchProductCategories.get(position).getPrice()));
        holder.product_price1.setPaintFlags(holder.product_price1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        holder.product_price.setText(Rails(context, searchProductCategories.get(position).getSellingPrice()));
        holder.cart_image.setOnClickListener(view -> {
            Intent intent = new Intent(context, Product_details.class);
            intent.putExtra("product_id", searchProductCategories.get(position).getId());
            intent.putExtra("product_name", searchProductCategories.get(position).getProductName());
            intent.putExtra("search", 60);
            intent.putExtra("SearchWord", SearchWord);


            context.startActivity(intent);
        });
        holder.product_name.setOnClickListener(view -> {
            Intent intent = new Intent(context, Product_details.class);
            intent.putExtra("product_id", searchProductCategories.get(position).getId());
            intent.putExtra("product_name", searchProductCategories.get(position).getProductName());
            intent.putExtra("search", 60);
            intent.putExtra("SearchWord", SearchWord);

            context.startActivity(intent);
        });
        holder.product_price.setOnClickListener(view -> {
            Intent intent = new Intent(context, Product_details.class);
            intent.putExtra("product_id", searchProductCategories.get(position).getId());
            intent.putExtra("product_name", searchProductCategories.get(position).getProductName());
            intent.putExtra("search", 60);
            intent.putExtra("SearchWord", SearchWord);

            context.startActivity(intent);
        });

        holder.wishlist.setOnClickListener(view -> {
            int productId = searchProductCategories.get(position).getId();
            b11 = ifExist(searchProductCategories.get(position).getId());

//            Log.e("wishlist_", "wishlist_ " + wishlist_);
//
//            if (products.get(position).getIswishlist() != null) {
//                wishlist_ = products.get(position).getIswishlist();
//
//            } else {
//                wishlist_ = 0;
//            }
//            if (wishlist_ == 1) {
//                products.get(position).setIswishlist(0);
            if (b11) {

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
                                    toastMessage(context, response.body().getUser_message());
                                }
                                quantaty1 = dataSaver.getInt("abdo1", 0);
                                quantaty1 = quantaty1 - 1;
                                //      quantaty1 = response.body().getTotal_wish_amount();
                                Log.e("TAG", "quantaty1 " + quantaty1);


                                dataSaver.edit()
                                        .putInt("abdo1", quantaty1)
                                        .apply();
                                Log.e("TAG", "quantaty2 " + quantaty1);
                                favoriteModel = new FavoriteModel();

                                favoriteModel.setName(searchProductCategories.get(position).getProductName());
                                favoriteModel.setId(searchProductCategories.get(position).getId());
                                favoriteModel.setDescription(searchProductCategories.get(position).getDescription());
                                favoriteModel.setPrice(searchProductCategories.get(position).getPrice());
                                favoriteModel.setSellingPrice(searchProductCategories.get(position).getSellingPrice());
                                favoriteModel.setImage_url(searchProductCategories.get(position).getImageUrl());

                                if (favoriteModel != null) {


                                    delete(searchProductCategories.get(position).getId());
                                }
                            } else {
                                if (toast != null) {
                                    toast.cancel();
                                    toastMessage(context, response.body().getUser_message());
                                }
                            }
                        } else {
                            Log.e("TAG", "notSuccessful");
                            if (toast != null) {
                                toast.cancel();
                                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                                Log.i("onResponse ", "onResponse: false " + response.raw());
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
                                toastMessage(context, response.body().getUser_message());
//                                quantaty1 = dataSaver.getInt("abdo1", 0);
//
//                                quantaty1 = quantaty1 + 1;
                                // quantaty1 = response.body().getTotal_wish_amount();
                                quantaty1 = dataSaver.getInt("abdo1", 0);
                                quantaty1 = quantaty1 + 1;
                                Log.e("TAG", "quantaty1 " + quantaty1);

                                Log.e("TAG", "quantaty2 " + quantaty1);

                                dataSaver.edit()
                                        .putInt("abdo1", quantaty1)
                                        .apply();

                                favoriteModel = new FavoriteModel();

                                favoriteModel.setName(searchProductCategories.get(position).getProductName());
                                favoriteModel.setId(searchProductCategories.get(position).getId());
                                favoriteModel.setDescription(searchProductCategories.get(position).getDescription());
                                favoriteModel.setPrice(searchProductCategories.get(position).getPrice());
                                favoriteModel.setSellingPrice(searchProductCategories.get(position).getSellingPrice());
                                favoriteModel.setImage_url(searchProductCategories.get(position).getImageUrl());

                                if (favoriteModel != null) {
                                    Log.e("TAG", "insert ");

                                    db.favoriteDao().insert(favoriteModel);
                                }
                            } else {
//                                Toast.makeText(context, response.body().getUser_message(), Toast.LENGTH_SHORT).show();
                                if (toast != null) {
                                    toast.cancel();
                                    toastMessage(context, response.body().getUser_message());
                                    Log.i("onResponse ", "onResponse: false " + response.raw());
                                }
                            }
                        } else {
                            Log.e("TAG", "notSuccessful");
                            if (toast != null) {
                                toast.cancel();
                                toastMessage(context, "error");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Add_to_cart> call, Throwable t) {
                        Log.e("TAG ", "onFailure " + t.getMessage());
                        if (toast != null) {
                            toast.cancel();
                            toastMessage(context, "error");
                        }
                    }
                });
            }
        });


        holder.addToCart.setOnClickListener(view -> {
            dataSaver.edit()
                    .putInt("flag_search", 1)
                    .apply();
            int productId = searchProductCategories.get(position).getId();
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

//                            notification_counter.setText(String.valueOf(response.body().getTotal_cart_amount()));

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
        return searchProductCategories.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView product_price, product_price1, product_name;
        ImageView cart_image, addToCart, wishlist;
        RelativeLayout product_card;
        Typeface customTypeOne = Typeface.createFromAsset(itemView.getContext().getAssets(), "Ubuntu-R.ttf");
        Typeface customTypeOne1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "Ubuntu-B.ttf");

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            product_price = itemView.findViewById(R.id.product_price);
            product_name = itemView.findViewById(R.id.product_name);
            cart_image = itemView.findViewById(R.id.cart_image);
            addToCart = itemView.findViewById(R.id.add_to_cart);
            wishlist = itemView.findViewById(R.id.wishlist);
            product_card = itemView.findViewById(R.id.product_card);
            product_price1 = itemView.findViewById(R.id.product_price1);
            product_name.setTypeface(customTypeOne);
            addToCart = itemView.findViewById(R.id.add_to_cart);
            wishlist = itemView.findViewById(R.id.wishlist);
            product_price.setTypeface(customTypeOne1);
        }
    }

    public void addInRecycle(List<SearchProductCategory> datum) {
        for (SearchProductCategory dt : datum) {
            searchProductCategories.add(dt);
        }
        notifyDataSetChanged();
    }
}