package com.ibsvalleyn.missvenue.adapters;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.activities.MainActivity;
import com.ibsvalleyn.missvenue.activities.Product_details;
import com.ibsvalleyn.missvenue.api.RetrofitClient;
import com.ibsvalleyn.missvenue.data_room.AppDatabase;
import com.ibsvalleyn.missvenue.fragments.Navigation_Wishlist_Fragment;
import com.bumptech.glide.Glide;
import com.ibsvalleyn.missvenue.helper.Static_variables;
import com.ibsvalleyn.missvenue.models.Add_to_cart;
import com.ibsvalleyn.missvenue.models.FavoriteModel;
import com.ibsvalleyn.missvenue.models.Wishlists;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.ibsvalleyn.missvenue.helper.AppFunctions.Rails;

public class MywishlistAdapter extends RecyclerView.Adapter<MywishlistAdapter.NewsHolder> {
    private TextView productsTV;
    SharedPreferences dataSaver;
    int customer_id;
    List<FavoriteModel> modelList;  //  private TextView productsTV;
    int quantaty, quantaty1;
    int Counter;
    private MainActivity context;
    AppDatabase db;

    public MywishlistAdapter(List<FavoriteModel> modelList, MainActivity context, AppDatabase db, TextView productsTV) {
        this.modelList = modelList;
        this.context = context;
        this.db = db;
        this.productsTV = productsTV;
    }


    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_wishlist, parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int i) {
        Toast toast = new Toast(context);

        dataSaver = getDefaultSharedPreferences(context);
        quantaty = dataSaver.getInt("abdo", 0);
        customer_id = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);

        Glide.with(context).load(modelList.get(i).getImage_url()).into(holder.product_image);
        if (modelList.get(i).getPrice() > modelList.get(i).getSellingPrice()) {

            holder.product_price1.setVisibility(View.VISIBLE);
        }
        holder.product_price1.setText(Rails(context, modelList.get(i).getPrice()));
        holder.product_price1.setPaintFlags(holder.product_price1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        if (modelList.get(i).getName().length() < 20) {
            holder.product_name.setText(modelList.get(i).getName());

        } else {
            String substring = modelList.get(i).getName().substring(0, 18) + ". . .";
            holder.product_name.setText(substring);

        }
        holder.product_price.setText(Rails(context, modelList.get(i).getSellingPrice()));
        holder.product_image.setOnClickListener(view -> {

            Intent intent = new Intent(context, Product_details.class);
            intent.putExtra("product_id", modelList.get(i).getId());
            intent.putExtra("product_name", modelList.get(i).getName());
            dataSaver.edit()
                    .putInt("isSector", 66)
                    .apply();
            context.startActivity(intent);

        });
        holder.add_to_cart.setOnClickListener(view -> {
            int productId = modelList.get(i).getId();
            Call<Add_to_cart> addEVEnt_call = RetrofitClient.getInstance(context)
                    .addToCart(1, customer_id, productId, 1);
            Log.e("customer_id", "customer_id " + customer_id);
            Log.e("productId", "productId " + productId);
            ++Counter;

            addEVEnt_call.enqueue(new Callback<Add_to_cart>() {

                @Override
                public void onResponse(Call<Add_to_cart> call, Response<Add_to_cart> response) {

                    if (response.isSuccessful()) {

                        if (response.body().isResult() == true) {
                            Log.e("TAG", "isSuccessful");
                            context.loadFragment(new Navigation_Wishlist_Fragment());
                            dataSaver.edit()
                                    .putInt("abdo", quantaty + Counter)
                                    .apply();
                            if (context.getNotificationsBadge1() != null) {
                                context.getNotificationsBadge1().setText(String.valueOf(quantaty + Counter));

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
        });


        holder.wishlist.setOnClickListener(view -> {

            int productId = modelList.get(i).getId();
            quantaty1 = dataSaver.getInt("abdo1", 0);

            Call<Add_to_cart> addEVEnt_call = RetrofitClient.getInstance(context)
                    .updateCart(2, customer_id, productId, 0);
            Log.e("customer_id", "customer_id " + customer_id);
            Log.e("productId", "productId " + productId);

            addEVEnt_call.enqueue(new Callback<Add_to_cart>() {

                @Override
                public void onResponse(Call<Add_to_cart> call, Response<Add_to_cart> response) {

                    if (response.isSuccessful()) {

                        assert response.body() != null;
                        if (response.body().isResult()) {
                            modelList.remove(modelList.get(i));
                            notifyDataSetChanged();
                            context.getNotificationsBadge().setText(String.valueOf(modelList.size()));
                            quantaty1 = modelList.size();
                            productsTV.setText(modelList.size()+ " " + context.getResources().getString(R.string.products));
                            dataSaver.edit()
                                    .putInt("abdo1", quantaty1)
                                    .apply();
                            Log.e("TAG", "isSuccessful");

                            if (toast != null) {
                                toast.cancel();

                                Toast.makeText(context, response.body().getUser_message(), Toast.LENGTH_SHORT).show();
                            }
                            delete(productId);

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
        return modelList != null ? modelList.size() : 0;
    }

    class NewsHolder extends RecyclerView.ViewHolder {

        ImageView product_image, wishlist, add_to_cart;
        TextView product_name;
        TextView product_price;
        TextView product_price1;
        LinearLayout linearLayout;
        Typeface customTypeOne = Typeface.createFromAsset(itemView.getContext().getAssets(), "Ubuntu-B.ttf");
        Typeface customTypeOne1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "Ubuntu-R.ttf");

        NewsHolder(@NonNull View itemView) {
            super(itemView);

            product_image = itemView.findViewById(R.id.product_image);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            product_price1 = itemView.findViewById(R.id.product_price1);
            wishlist = itemView.findViewById(R.id.wishlist);
            add_to_cart = itemView.findViewById(R.id.add_to_cart);
            product_name.setTypeface(customTypeOne1);
            product_price1.setTypeface(customTypeOne);
            product_price.setTypeface(customTypeOne);
        }
    }

    private void delete(int id) {

        FavoriteModel sonyRec = db.favoriteDao().fetchById(id); // Read first


        db.favoriteDao().delete(sonyRec);
        // modelList.remove(id);
        //context.loadFragment(new Navigation_Wishlist_Fragment());

        notifyDataSetChanged();

    }

}
