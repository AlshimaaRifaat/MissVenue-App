package com.ibsvalleyn.missvenue.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.activities.CheckOutActivity;
import com.ibsvalleyn.missvenue.activities.MainActivity;
import com.ibsvalleyn.missvenue.activities.SigningActivity;
import com.ibsvalleyn.missvenue.adapters.MyCartAdapter;
import com.ibsvalleyn.missvenue.api.RetrofitClient;
import com.ibsvalleyn.missvenue.helper.Static_variables;
import com.ibsvalleyn.missvenue.models.Add_to_cart;
import com.ibsvalleyn.missvenue.models.LocalCart;
import com.ibsvalleyn.missvenue.models.OrderCheck;
import com.ibsvalleyn.missvenue.models.ShoppingCarts;
import com.ibsvalleyn.missvenue.viewModels.MycartViewModel;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.ibsvalleyn.missvenue.activities.MainActivity.Counter;
import static com.ibsvalleyn.missvenue.helper.AppFunctions.Rails;
import static com.ibsvalleyn.missvenue.helper.AppFunctions.getKProgressHUD;
import static com.ibsvalleyn.missvenue.helper.AppFunctions.setMargins;

public class Navigation_My_Cart_Fragment extends Fragment implements View.OnClickListener {
    LinearLayout noItems;
    RecyclerView myCartRecyclerView;
    GridLayoutManager layoutManager;
    MyCartAdapter myOrdersAdapter;
    SharedPreferences dataSaver;
    LinearLayout place_order;
    TextView price_Mycart;
    int id;
    private MainActivity activity;
    public int total_quantity;
    int position;
    List<LocalCart> localCarts = new ArrayList<>();
    ShoppingCarts profile = new ShoppingCarts();
    private String device_id;
    TextView go_home;
    MycartViewModel model;
    private KProgressHUD kProgressHUD;
    private String email;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
        kProgressHUD = getKProgressHUD(activity);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_cart_fragment_layout, container, false);
        dataSaver = getDefaultSharedPreferences(activity);
        position = dataSaver.getInt("position", 0);
        id = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);
        email = dataSaver.getString(Static_variables.Email, "");
        Log.e("customer_id", "customer_id " + id);
        device_id = dataSaver.getString(Static_variables.DEVICE_ID, "");
        noItems = view.findViewById(R.id.no_items);
        go_home = view.findViewById(R.id.go_home);
        place_order = view.findViewById(R.id.place_order);
        price_Mycart = view.findViewById(R.id.price_Mycart);
        place_order.setOnClickListener(this);
        go_home.setOnClickListener(this);
        myCartRecyclerView = view.findViewById(R.id.my_cart_recycler);
        layoutManager = new GridLayoutManager(activity, 1);
        myCartRecyclerView.setLayoutManager(layoutManager);
        Calligrapher calligrapher = new Calligrapher(activity);
        calligrapher.setFont(activity, "Ubuntu-R.ttf", true);
        model = ViewModelProviders.of(this).get(MycartViewModel.class);
        model.setContext(activity);

        model.getMyCart(id).observe(activity, new Observer<ShoppingCarts>() {
            @Override
            public void onChanged(@Nullable ShoppingCarts heroList) {
                if (heroList.getItems().size() == 0) {
                    noItems.setVisibility(View.VISIBLE);
                    place_order.setVisibility(View.GONE);
                    if (activity.getNotificationsBadge1() != null) {
                        activity.getNotificationsBadge1().setText("" + total_quantity);
                    }//44300

                    dataSaver.edit()
                            .putInt("abdo", 0)
                            .apply();

                } else {
                    noItems.setVisibility(View.GONE);
                    place_order.setVisibility(View.VISIBLE);
                    total_quantity = heroList.getTotal_Quantity();
                    price_Mycart.setText(Rails(activity, (double) heroList.getSub_Total()));
                    myOrdersAdapter = new MyCartAdapter(activity, heroList, price_Mycart, Counter, total_quantity);
                    myCartRecyclerView.setAdapter(myOrdersAdapter);

                    Log.e("TAG", "total_fragment " + total_quantity);
                    if (activity.getNotificationsBadge1() != null) {
                        activity.getNotificationsBadge1().setText("" + total_quantity);
                    }
                    dataSaver.edit()
                            .putInt("abdo", heroList.getTotal_Quantity())
                            .apply();
                    dataSaver.edit()
                            .putFloat(Static_variables.Total_price, (float) heroList.getTotal())
                            .apply();

                    Log.e("TAG", "position " + position);
                    if (position == 0) {
                        myCartRecyclerView.getLayoutManager().scrollToPosition(position);
                    } else {
                        myCartRecyclerView.getLayoutManager().scrollToPosition(position - 1);
                    }
                    if (price_Mycart.getText().equals("")) {
                        place_order.setVisibility(View.GONE);
                    } else {
                        place_order.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        return view;
    }

    public void orderCheck() {
        kProgressHUD.show();
        Call<List<OrderCheck>> addEVEnt_call = RetrofitClient.getInstance(activity)
                .orderCheck(id);
        addEVEnt_call.enqueue(new Callback<List<OrderCheck>>() {
            @Override
            public void onResponse(Call<List<OrderCheck>> call, Response<List<OrderCheck>> response) {
                kProgressHUD.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().get(0).isResult() == true) {

                        startActivity(new Intent(activity, CheckOutActivity.class));
                        dataSaver.edit()
                                .putString("addressIdSapping", "")
                                .apply();

                        dataSaver.edit()
                                .putString("addressIdBilling", "")
                                .apply();

                        dataSaver.edit()
                                .putString("PayMethod", "")
                                .apply();

                    } else {
                        String message = "";
                        for (int i = 0; i < response.body().size(); i++) {
                            message = "";
                            message = response.body().get(i).getUser_message();
                            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                        }
                        if (response.body().get(0).getFalseType() == 1) {
                            verifyEmail();
                        } else {
                            updateOrder();

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<OrderCheck>> call, Throwable t) {
                Log.e("TAG ", "onFailure " + t.getMessage());
                kProgressHUD.dismiss();
                Toast.makeText(activity, "Check internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void orderItemUpdate() {
        kProgressHUD.show();
        Call<Add_to_cart> addEVEnt_call = RetrofitClient.getInstance(activity)
                .orderItemUpdate(id);
        addEVEnt_call.enqueue(new Callback<Add_to_cart>() {
            @Override
            public void onResponse(Call<Add_to_cart> call, Response<Add_to_cart> response) {
                kProgressHUD.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().isResult() == true) {

                        Toast.makeText(activity, response.body().getUser_message(), Toast.LENGTH_SHORT).show();
                        activity.loadFragment(new Navigation_My_Cart_Fragment());

                    } else {
                        Toast.makeText(activity, response.body().getUser_message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Add_to_cart> call, Throwable t) {
                Log.e("TAG ", "onFailure " + t.getMessage());
                kProgressHUD.dismiss();
                Toast.makeText(activity, "Check internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void verificationResend() {
        kProgressHUD.show();
        Call<OrderCheck> addEVEnt_call = RetrofitClient.getInstance(activity)
                .verifyEmail(id, email);
        addEVEnt_call.enqueue(new Callback<OrderCheck>() {
            @Override
            public void onResponse(Call<OrderCheck> call, Response<OrderCheck> response) {
                kProgressHUD.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().isResult() == true) {

                        //   startActivity(new Intent(activity, CheckOutActivity.class));
                        dataSaver.edit()
                                .putString("addressIdSapping", "")
                                .apply();

                        dataSaver.edit()
                                .putString("addressIdBilling", "")
                                .apply();

                        dataSaver.edit()
                                .putString("PayMethod", "")
                                .apply();
                        Toast.makeText(activity, response.body().getUser_message(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(activity, response.body().getUser_message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderCheck> call, Throwable t) {
                Log.e("TAG ", "onFailure " + t.getMessage());
                kProgressHUD.dismiss();
                Toast.makeText(activity, "Check internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateOrder() {
        final AlertDialog builder = new AlertDialog.Builder(activity).create();

        final View v2 = LayoutInflater.from(activity).inflate(R.layout.order_update_dialog, null);
        Calligrapher calligrapher = new Calligrapher(activity);
        calligrapher.setFont(activity, "Ubuntu-R.ttf", true);
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M) {
            setMargins(builder, 75, 150, 75, 120);
        }
        Button post = v2.findViewById(R.id.post);
        Button cancel = v2.findViewById(R.id.cancel);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderItemUpdate();
                builder.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder.dismiss();
            }
        });

        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder.setView(v2);
        builder.setCancelable(true);
        builder.show();
    }

    private void verifyEmail() {
        final AlertDialog builder = new AlertDialog.Builder(activity).create();

        final View v2 = LayoutInflater.from(activity).inflate(R.layout.verify_email_dialog, null);
        Calligrapher calligrapher = new Calligrapher(activity);
        calligrapher.setFont(activity, "Ubuntu-R.ttf", true);
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M) {
            setMargins(builder, 75, 150, 75, 120);
        }
        Button post = v2.findViewById(R.id.post);
        Button cancel = v2.findViewById(R.id.cancel);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificationResend();
                builder.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder.dismiss();
            }
        });

        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder.setView(v2);
        builder.setCancelable(true);
        builder.show();
    }

    @Override
    public void onClick(View view) {
        if (view == place_order) {
            if (!device_id.equals("")) {
                startActivity(new Intent(activity, SigningActivity.class));
            } else
                orderCheck();
        }
        if (view == go_home) {
            startActivity(new Intent(activity, MainActivity.class));
        }
    }
}