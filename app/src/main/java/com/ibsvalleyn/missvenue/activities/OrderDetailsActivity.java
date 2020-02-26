package com.ibsvalleyn.missvenue.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.adapters.OrdersItemsAdapter;
import com.ibsvalleyn.missvenue.api.RetrofitClient;
import com.ibsvalleyn.missvenue.models.OrdersModel;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.ibsvalleyn.missvenue.helper.AppFunctions.Rails;

public class OrderDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private GridLayoutManager layoutManager;
    RecyclerView productRecyclerView;
    int id;
    Button continue_shopping;
    SharedPreferences dataSaver;
    OrdersItemsAdapter myOrdersAdapter;
    TextView total_price, successfully_ordered;
    int order;
    private TextView shipping_ground;
    private TextView sub_total_price;
    private TextView tax;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        dataSaver = getDefaultSharedPreferences(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("order_id", 0);
        order = intent.getIntExtra("order", 0);
        Log.e("order_id", "order_id " + id);
        total_price = findViewById(R.id.total_price);
        continue_shopping = findViewById(R.id.continue_shopping);
        successfully_ordered = findViewById(R.id.successfully_ordered);
        shipping_ground = findViewById(R.id.shipping_ground);
        sub_total_price = findViewById(R.id.sub_total_price);
        tax = findViewById(R.id.tax);
        productRecyclerView = findViewById(R.id.product_recycler);
        layoutManager = new GridLayoutManager(this, 1);
        productRecyclerView.setLayoutManager(layoutManager);
        continue_shopping.setOnClickListener(this);
        getCart();
        if (order == 1) {
            continue_shopping.setVisibility(View.VISIBLE);
            successfully_ordered.setVisibility(View.VISIBLE);
        } else {
            continue_shopping.setVisibility(View.GONE);
            successfully_ordered.setVisibility(View.GONE);
        }
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Ubuntu-R.ttf", true);
    }

    @Override
    public void onBackPressed() {
        if (order == 1) {
            Intent intent = new Intent(OrderDetailsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            super.onBackPressed();
        }
    }

    public void getCart() {
        Call<OrdersModel> addEVEnt_call = RetrofitClient.getInstance(OrderDetailsActivity.this)
                .getItemsOrders(id);
        addEVEnt_call.enqueue(new Callback<OrdersModel>() {

            @Override
            public void onResponse(Call<OrdersModel> call, Response<OrdersModel> response) {

                if (response.isSuccessful()) {
                    Log.e("TAG", "isSuccessful");
                    myOrdersAdapter = new OrdersItemsAdapter(OrderDetailsActivity.this, response.body());
                    productRecyclerView.setAdapter(myOrdersAdapter);
                    total_price.setText(Rails(OrderDetailsActivity.this, (double) response.body().getOrderTotal()));
                    shipping_ground.setText(Rails(OrderDetailsActivity.this, (double) response.body().getShipping_rate()));
                    sub_total_price.setText(Rails(OrderDetailsActivity.this, (double) response.body().getSub_Total()));
                    tax.setText(Rails(OrderDetailsActivity.this, (double) response.body().getTax_rate()));

                    assert response.body() != null;
                    for (int i = 0; i < response.body().getItems().size(); i++) {
                    }
                } else {
                    Log.e("TAG", "notSuccessful");
                }
            }

            @Override
            public void onFailure(Call<OrdersModel> call, Throwable t) {
                Log.e("TAG ", "onFailure");
                Toast.makeText(OrderDetailsActivity.this, "Check internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == continue_shopping) {
            Intent intent = new Intent(OrderDetailsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}