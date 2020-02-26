package com.ibsvalleyn.missvenue.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.adapters.OrdersItemsAdapter;
import com.ibsvalleyn.missvenue.api.RetrofitClient;
import com.ibsvalleyn.missvenue.models.OrdersModel;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.GraphRequest.TAG;
import static com.ibsvalleyn.missvenue.helper.AppFunctions.Rails;
import static com.ibsvalleyn.missvenue.helper.AppFunctions.setMargins;

public class WebViewActivity extends AppCompatActivity {

    private int order_id;
    private AlertDialog builder;
    private View v;
    private TextView total_price;
    private TextView successfully_ordered;
    private RecyclerView productRecyclerView;
    private TextView shipping_ground;
    private TextView payment_text;
    private TextView sub_total_price;
    private TextView tax;
    private GridLayoutManager layoutManager;
    private OrdersItemsAdapter myOrdersAdapter;
    private Integer payment_status;
    private int payment_type;
    private TextView pay_order;
    private float amount;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent intent = getIntent();
        order_id = intent.getIntExtra("order_id", 0);
        amount = intent.getFloatExtra("amount", 0);
        int amount1 = (int) amount;
        WebView webView = findViewById(R.id.webview);
        Log.i("sadsasad", "onCreate: " + order_id);
        Log.i("sadsasad", "onCreate: " + amount);
        webView.setWebViewClient(new WebViewClient());
        //8585 in test
        webView.loadUrl("http://40.85.116.121:8515/Checkout/CheckOut_Request?amount=" + amount1 + "&order_id=" + order_id);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(view -> {

            Intent inty = new Intent(WebViewActivity.this, MainActivity.class);
            startActivity(inty);
            finish();
       
        });
        setPusher();
    }

    private void setPusher() {
        PusherOptions options = new PusherOptions();
        options.setCluster("eu");
        Pusher pusher = new Pusher("ec118f8c3105164dfa1f", options);
        pusher.connect();
//        int user_id1 = user_data.getInt("user_id", 0);
        Channel channel = pusher.subscribe("my-channel" + order_id);

        channel.bind("my-event", event -> {
            try {
                JSONObject dataObject = new JSONObject(event.getData());
                String message = dataObject.getString("message");
                if (!message.equals("")) {
                    runOnUiThread(() ->
                            ShowOderDetails(order_id)
                    );
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("event ", " " + event);
        });
    }

    @Override
    public void onBackPressed() {
//        if (webView.canGoBack()) {
//            webView.goBack();
//        } else {
//        super.onBackPressed();
//        Intent inty = new Intent(WebViewActivity.this, MainActivity.class);
//        startActivity(inty);
//        finish();
        // }
    }

    public void ShowOderDetails(int orderId) {

        builder = new AlertDialog.Builder(WebViewActivity.this).create();
        v = LayoutInflater.from(WebViewActivity.this).inflate(R.layout.order_details_layout, null);

        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M) {
            setMargins(builder, 75, 150, 75, 120);
        }

        TextView closeTV;
        total_price = v.findViewById(R.id.total_price);
        closeTV = v.findViewById(R.id.closeTV);
        TextView order_number = v.findViewById(R.id.order_number);
        order_number.setText(getApplication().getResources().getString(R.string.order_id) + orderId);
        successfully_ordered = v.findViewById(R.id.successfully_ordered);
        productRecyclerView = v.findViewById(R.id.product_recycler);
        shipping_ground = v.findViewById(R.id.shipping_ground);
        payment_text = v.findViewById(R.id.payment_text);
        pay_order = v.findViewById(R.id.pay);

        sub_total_price = v.findViewById(R.id.sub_total_price);
        tax = v.findViewById(R.id.tax);
        layoutManager = new GridLayoutManager(WebViewActivity.this, 1);
        productRecyclerView.setLayoutManager(layoutManager);
        closeTV.setOnClickListener(view ->
        {
            builder.cancel();
            Intent intent = new Intent(WebViewActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        });
        getCart(orderId);

        pay_order.setOnClickListener(view -> {
                    Intent intent = new Intent(WebViewActivity.this, WebViewActivity.class);
                    intent.putExtra("order_id", orderId);
                    intent.putExtra("amount", amount);
                    startActivity(intent);
                    finish();
                }
        );
        Log.i(TAG, "ShowOderDetails: " + orderId);
        builder.setView(v);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder.setCancelable(false);
        builder.show();

    }

    private void getCart(int Id) {
        Call<OrdersModel> addEVEnt_call = RetrofitClient.getInstance(WebViewActivity.this)
                .getItemsOrders(Id);
        addEVEnt_call.enqueue(new Callback<OrdersModel>() {

            @Override
            public void onResponse(@NotNull Call<OrdersModel> call, @NotNull Response<OrdersModel> response) {

                if (response.isSuccessful()) {
                    Log.e("TAG", "isSuccessful");

                    myOrdersAdapter = new OrdersItemsAdapter(WebViewActivity.this, response.body());
                    productRecyclerView.setAdapter(myOrdersAdapter);
                    total_price.setText(Rails(WebViewActivity.this, (double) response.body().getOrderTotal()));
                    shipping_ground.setText(Rails(WebViewActivity.this, (double) response.body().getShipping_rate()));
                    sub_total_price.setText(Rails(WebViewActivity.this, (double) response.body().getSub_Total()));
                    payment_text.setText(response.body().getPaymentMethodName() + "(" + response.body().getPayment_Status_Label() + ")");
                    payment_status = response.body().getPayment_Status();
                    payment_type = response.body().getPaymentMethodType();
                    tax.setText(Rails(WebViewActivity.this, (double) response.body().getTax_rate()));
                    assert response.body() != null;
                    if ((payment_status == 50 || payment_status == 10) && payment_type == 2) {
                        pay_order.setVisibility(View.VISIBLE);
                    } else {
                        pay_order.setVisibility(View.GONE);
                    }
                    for (int i = 0; i < response.body().getItems().size(); i++) {
                    }
                } else {
                    Log.e("TAG", "notSuccessful");
                }
            }

            @Override
            public void onFailure(@NotNull Call<OrdersModel> call, Throwable t) {
                Log.e("TAG ", "onFailure");
                Toast.makeText(WebViewActivity.this, "Check internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}