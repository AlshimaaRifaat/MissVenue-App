package com.ibsvalleyn.missvenue.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.activities.MainActivity;
import com.ibsvalleyn.missvenue.activities.WebViewActivity;
import com.ibsvalleyn.missvenue.api.RetrofitClient;
import com.ibsvalleyn.missvenue.models.Orders;
import com.ibsvalleyn.missvenue.models.OrdersModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.GraphRequest.TAG;
import static com.ibsvalleyn.missvenue.helper.AppFunctions.Rails;
import static com.ibsvalleyn.missvenue.helper.AppFunctions.setMargins;


public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.MyOrdersViewHolder> {
    MainActivity context;
    List<Orders> customerProfile;
    private AlertDialog builder;
    private View v;
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
    private TextView payment_text;
    private Integer payment_status;
    private int orderId;
    private float orderTotal;
    private TextView pay_order;
    private int payment_type;


    public MyOrdersAdapter(MainActivity context, List<Orders> customerProfile) {
        this.context = context;
        this.customerProfile = customerProfile;
    }

    @NonNull
    @Override
    public MyOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_orders_layout, viewGroup, false);
        return new MyOrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrdersViewHolder holder, int position) {
        holder.orderState.setText(customerProfile.get(position).getStatus());
        holder.orderDate.setText(customerProfile.get(position).getDate());
        holder.orderId.setText(context.getResources().getString(R.string.order_id) + customerProfile.get(position).getId());
        holder.relative.setOnClickListener(view -> {
            orderId = customerProfile.get(position).getId();
//            Intent intent = new Intent(context, OrderDetailsActivity.class);
//            intent.putExtra("order_id", orderId);
//            context.startActivity(intent);
            ShowOderDetails(orderId);

        });
    }

    @Override
    public int getItemCount() {
        return customerProfile.size();
    }

    public class MyOrdersViewHolder extends RecyclerView.ViewHolder {
        TextView orderState, orderId, orderDate;
        RelativeLayout relative;
        Typeface customTypeOne = Typeface.createFromAsset(itemView.getContext().getAssets(), "Ubuntu-R.ttf");

        public MyOrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            orderState = itemView.findViewById(R.id.order_state);
            orderId = itemView.findViewById(R.id.order_id);
            orderDate = itemView.findViewById(R.id.order_date);
            relative = itemView.findViewById(R.id.relative);
            orderState.setTypeface(customTypeOne);
            orderId.setTypeface(customTypeOne);
            orderDate.setTypeface(customTypeOne);
        }
    }

    public void ShowOderDetails(int orderId) {

        builder = new AlertDialog.Builder(context).create();
        v = LayoutInflater.from(context).inflate(R.layout.order_details_layout, null);

        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M) {
            setMargins(builder, 75, 150, 75, 120);
        }

        TextView closeTV;
        total_price = v.findViewById(R.id.total_price);
        closeTV = v.findViewById(R.id.closeTV);
        pay_order = v.findViewById(R.id.pay);
        TextView order_number = v.findViewById(R.id.order_number);
        order_number.setText(context.getResources().getString(R.string.order_id) + orderId);
        successfully_ordered = v.findViewById(R.id.successfully_ordered);
        productRecyclerView = v.findViewById(R.id.product_recycler);
        shipping_ground = v.findViewById(R.id.shipping_ground);
        payment_text = v.findViewById(R.id.payment_text);
        sub_total_price = v.findViewById(R.id.sub_total_price);
        tax = v.findViewById(R.id.tax);
        layoutManager = new GridLayoutManager(context, 1);
        productRecyclerView.setLayoutManager(layoutManager);
        closeTV.setOnClickListener(view ->
                builder.cancel()

        );
        getCart(orderId);

        pay_order.setOnClickListener(view -> {
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("order_id", orderId);
                    intent.putExtra("amount", orderTotal);
                    context.startActivity(intent);
                }
        );
        Log.i(TAG, "ShowOderDetails: " + orderId);
        builder.setView(v);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder.setCancelable(false);
        builder.show();

    }

    private void getCart(int Id) {
        Call<OrdersModel> addEVEnt_call = RetrofitClient.getInstance(context)
                .getItemsOrders(Id);
        addEVEnt_call.enqueue(new Callback<OrdersModel>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<OrdersModel> call, @NotNull Response<OrdersModel> response) {

                if (response.isSuccessful()) {
                    Log.e("TAG", "isSuccessful");

                    myOrdersAdapter = new OrdersItemsAdapter(context, response.body());
                    productRecyclerView.setAdapter(myOrdersAdapter);
                    orderTotal = response.body().getOrderTotal();
                    total_price.setText(Rails(context, (double) orderTotal));
                    shipping_ground.setText(Rails(context, (double) response.body().getShipping_rate()));
                    sub_total_price.setText(Rails(context, (double) response.body().getSub_Total()));
                    payment_text.setText(response.body().getPaymentMethodName() + "(" + response.body().getPayment_Status_Label() + ")");
                    payment_status = response.body().getPayment_Status();
                    payment_type = response.body().getPaymentMethodType();
                    tax.setText(Rails(context, (double) response.body().getTax_rate()));
                    if ((payment_status == 50 || payment_status == 10) && payment_type == 2) {
                        pay_order.setVisibility(View.VISIBLE);
                    } else {
                        pay_order.setVisibility(View.GONE);
                    }
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
                Toast.makeText(context, "Check internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}