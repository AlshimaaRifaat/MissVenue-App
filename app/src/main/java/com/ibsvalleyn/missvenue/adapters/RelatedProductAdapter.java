package com.ibsvalleyn.missvenue.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.activities.Product_details;
import com.ibsvalleyn.missvenue.api.RetrofitClient;
import com.bumptech.glide.Glide;
import com.ibsvalleyn.missvenue.data_room.AppDatabase;
import com.ibsvalleyn.missvenue.helper.Static_variables;
import com.ibsvalleyn.missvenue.models.Add_to_cart;
import com.ibsvalleyn.missvenue.models.FavoriteModel;
import com.ibsvalleyn.missvenue.models.RelatedProducts;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.ibsvalleyn.missvenue.helper.AppFunctions.Rails;

public class RelatedProductAdapter extends RecyclerView.Adapter<RelatedProductAdapter.ProductViewHolder> {
    Context context;
    List<RelatedProducts> products;
    SharedPreferences dataSaver;
    int customer_id;
    int wishlist_;
    TextView notification_counter;
    AppDatabase db;
    FavoriteModel favoriteModel;
    private boolean b11;
    private int quantaty1;

    public RelatedProductAdapter(Context context, List<RelatedProducts> products, TextView notification_counter, AppDatabase db
    ) {
        this.context = context;
        this.products = products;
        this.notification_counter = notification_counter;
        this.db = db;

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


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_layout, viewGroup, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Toast toast = new Toast(context);

        dataSaver = getDefaultSharedPreferences(context);
        customer_id = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);
        favoriteModel = new FavoriteModel();

        favoriteModel.setName(products.get(position).getName());
        favoriteModel.setId(products.get(position).getId());
        favoriteModel.setDescription(products.get(position).getDescription());
        favoriteModel.setPrice(products.get(position).getPrice());
        favoriteModel.setSellingPrice(products.get(position).getSellingPrice());
        favoriteModel.setImage_url(products.get(position).getImage_url());
        b11 = ifExist(products.get(position).getId());
        Log.i(TAG, "b11: " + b11);
        if (b11) {
            holder.wishlist.setImageResource(R.drawable.heart);

        } else {

            holder.wishlist.setImageResource(R.drawable.heart_add);

        }
        if (products.get(position).getPrice() > products.get(position).getSellingPrice()) {
            holder.product_price1.setVisibility(View.VISIBLE);
            holder.product_price1.setTextSize(10);
            holder.product_price.setTextSize(10);
        }

        if (products.get(position).getName().length() < 20) {
            holder.product_name.setText(products.get(position).getName());

        } else {
            String substring = products.get(position).getName().substring(0, 18) + ". . .";
            holder.product_name.setText(substring);
        }

        Glide.with(context).load(products.get(position).getImage_url()).into(holder.product_image);
        holder.product_price.setText(Rails(context, products.get(position).getSellingPrice()));
        holder.product_price1.setText(Rails(context, products.get(position).getPrice()));
        holder.product_price1.setPaintFlags(holder.product_price1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.product_image.setOnClickListener(view -> {
            Log.e("TAG", "product_id_related " + products.get(position).getId());

            Intent intent = new Intent(context, Product_details.class);
            intent.putExtra("related", 7);
            intent.putExtra("product_name", products.get(position).getName());
            intent.putExtra("flageformProOF", 2);
            intent.putExtra("product_id", products.get(position).getId());
            context.startActivity(intent);

        });

        holder.wishlist.setOnClickListener(view -> {
            int productId = products.get(position).getId();
            b11 = ifExist(products.get(position).getId());

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
                                    Toast.makeText(context, response.body().getUser_message(), Toast.LENGTH_SHORT).show();
                                }
                                quantaty1 = dataSaver.getInt("abdo1",0);
                                quantaty1 = quantaty1 -1;

                                dataSaver.edit()
                                        .putInt("abdo1", quantaty1)
                                        .apply();

                                favoriteModel = new FavoriteModel();

                                favoriteModel.setName(products.get(position).getName());
                                favoriteModel.setId(products.get(position).getId());
                                favoriteModel.setDescription(products.get(position).getDescription());
                                favoriteModel.setPrice(products.get(position).getPrice());
                                favoriteModel.setSellingPrice(products.get(position).getSellingPrice());
                                favoriteModel.setImage_url(products.get(position).getImage_url());

                                if (favoriteModel != null) {


                                    delete(products.get(position).getId());
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
            } else if (wishlist_ == 0) {
                products.get(position).setIswishlist(1);
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
                                quantaty1 = dataSaver.getInt("abdo1",0);
                                quantaty1 = quantaty1 +1;

                                dataSaver.edit()
                                        .putInt("abdo1", quantaty1)
                                        .apply();

                                favoriteModel = new FavoriteModel();

                                favoriteModel.setName(products.get(position).getName());
                                favoriteModel.setId(products.get(position).getId());
                                favoriteModel.setDescription(products.get(position).getDescription());
                                favoriteModel.setPrice(products.get(position).getPrice());
                                favoriteModel.setSellingPrice(products.get(position).getSellingPrice());
                                favoriteModel.setImage_url(products.get(position).getImage_url());
                                if (favoriteModel != null) {
                                    Log.e("TAG", "insert ");

                                    db.favoriteDao().insert(favoriteModel);
                                }

                                if (toast != null) {
                                    toast.cancel();

                                    Toast.makeText(context, response.body().getUser_message(), Toast.LENGTH_SHORT).show();
                                }
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
            int productId = products.get(position).getId();
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
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView product_price, product_price1, product_name;
        ImageView product_image, addToCart, wishlist;
        CardView product_card;
        Typeface customTypeOne = Typeface.createFromAsset(itemView.getContext().getAssets(), "Ubuntu-B.ttf");
        Typeface customTypeOne1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "Ubuntu-R.ttf");

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            product_price = itemView.findViewById(R.id.product_price);
            product_price1 = itemView.findViewById(R.id.product_price1);
            product_name = itemView.findViewById(R.id.product_name);
            product_image = itemView.findViewById(R.id.product_image);
            addToCart = itemView.findViewById(R.id.add_to_cart);
            wishlist = itemView.findViewById(R.id.wishlist);
            product_card = itemView.findViewById(R.id.product_card);
            product_name.setTypeface(customTypeOne1);
            product_price1.setTypeface(customTypeOne);
            product_price.setTypeface(customTypeOne);
        }
    }
}