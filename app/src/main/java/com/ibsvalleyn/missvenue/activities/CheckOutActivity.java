package com.ibsvalleyn.missvenue.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.adapters.AddressConfirmationAdapter;
import com.ibsvalleyn.missvenue.adapters.AddressConfirmationAdapterBilling;
import com.ibsvalleyn.missvenue.adapters.MyCartConfirmationsAdapter;
import com.ibsvalleyn.missvenue.adapters.OrdersItemsAdapter;
import com.ibsvalleyn.missvenue.adapters.PersonAdapter;
import com.ibsvalleyn.missvenue.adapters.richContentAdapter;
import com.ibsvalleyn.missvenue.api.RetrofitClient;
import com.ibsvalleyn.missvenue.helper.Static_variables;
import com.ibsvalleyn.missvenue.models.City_model;
import com.ibsvalleyn.missvenue.models.Country_Model;
import com.ibsvalleyn.missvenue.models.OrderView.OrderView;
import com.ibsvalleyn.missvenue.models.OrdersModel;
import com.ibsvalleyn.missvenue.models.ShoppingCarts;
import com.ibsvalleyn.missvenue.models.User_address_model;
import com.ibsvalleyn.missvenue.models.address_add_model;
import com.ibsvalleyn.missvenue.models.customer_update_model;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.ibsvalleyn.missvenue.helper.AppFunctions.Rails;
import static com.ibsvalleyn.missvenue.helper.AppFunctions.getKProgressHUD;
import static com.ibsvalleyn.missvenue.helper.AppFunctions.setMargins;

public class CheckOutActivity extends AppCompatActivity {

    RecyclerView myOrdersRecyclerView;
    GridLayoutManager layoutManager;
    GridLayoutManager layoutManager2;
    MyCartConfirmationsAdapter myOrdersAdapter;
    SharedPreferences dataSaver;
    int id;
    private AddressConfirmationAdapter addressAdapter;
    private AddressConfirmationAdapterBilling AddressConfirmationAdapterBilling;

    private KProgressHUD kProgressHUD;
    private Spinner CitySp;
    float total_price;
    List<String> Governments = new ArrayList<>();
    List<String> Govern_IDs = new ArrayList<>();
    private String City_id = "??";
    private TextView add_address;
    private AlertDialog builder;
    private int CUSTOMER_ID;
    private static final String TAG = "klddcjaddsfsdlkjsald";
    private List<String> MainCats_name = new ArrayList<>();
    private List<String> MainCats_IDs = new ArrayList<>();
    private List<String> SubCats_IDs = new ArrayList<>();
    private List<String> SubCats_name = new ArrayList<>();
    private String subCatID;
    private EditText first_name;
    private EditText last_name;
    private TextView EmailAddress;
    private EditText SubMobileNumberCatSp;
    private TextView address;
    private TextView shipping_ground;
    private TextView sub_total_price;
    private TextView tax;
    private EditText postalCode;
    private String s;
    private Spinner MainCatSp, SubCatSp;
    private String Cites;
    private Button sendTo;
    CheckOutActivity activity;
    private Spinner getAllAddress;
    private String provName;
    private RecyclerView myCartRecyclerView;
    private String ip;
    private String PaymentMethod;
    private String ShppingId;
    private String BillingId;
    private EditText PromationCode;
    private double price;
    TextView totalPrice;
    private TextView payment_text;
    private RecyclerView rv_richContent;
    private RecyclerView rv_payment;
    private String Address;
    private TextView tvChange, ed_name;

    private RecyclerView my_address_recycler;
    private RecyclerView my_address_recyclerBilling;
    private View v;
    private String addressIdSapping = "";
    private String addressIdBilling = "";

    private int x = 0;
    PersonAdapter personAdapter;
    private String payment;
    private int newAddress;
    private EditText city, zip1;
    private String zip;
    private float tax_rate;
    private float sub_total;
    private float shipping_rate;
    private int order_id;
    private int payment_id;
    private String email_Address;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_confirmation_fragment);
        dataSaver = getDefaultSharedPreferences(this);
        findViewById(R.id.back).setOnClickListener(view1 -> this.onBackPressed());

        id = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);
        total_price = dataSaver.getFloat(Static_variables.Total_price, 0);
        totalPrice = findViewById(R.id.total_price);
        payment_text = findViewById(R.id.payment_text);
        add_address = findViewById(R.id.add_address);
        tvChange = findViewById(R.id.tvChange);
        ed_name = findViewById(R.id.ed_name);
        findViewById(R.id.ed_name).setOnClickListener(view -> {
//            Intent inty = new Intent(CheckOutActivity.this, CustomUIActivity.class);
//            inty.putExtra("price", "49.99");
//            startActivity(inty);

        });
        rv_payment = findViewById(R.id.rv_payment);
        rv_richContent = findViewById(R.id.rv_richContent);
        shipping_ground = findViewById(R.id.shipping_ground);
        sub_total_price = findViewById(R.id.sub_total_price);
        tax = findViewById(R.id.tax);

        kProgressHUD = getKProgressHUD(this);
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        findViewById(R.id.back).setOnClickListener(view1 -> this.onBackPressed());
        sendTo = findViewById(R.id.sendTo);
        getAllAddress = findViewById(R.id.getAllAddress);
        myCartRecyclerView = findViewById(R.id.my_cart_recycler);
        layoutManager = new GridLayoutManager(this, 1);
        myCartRecyclerView.setLayoutManager(layoutManager);
        address = findViewById(R.id.addressvvvv);
        PromationCode = findViewById(R.id.PromationCode);
        totalPrice.setText(Rails(CheckOutActivity.this, (double) total_price));

        if (getIntent().getIntExtra("newAddress", 0) != 0) {
            newAddress = getIntent().getIntExtra("newAddress", 0);
            getAddressById1(newAddress, 0);

        } else {
            getUserAddress();
        }
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Ubuntu-R.ttf", true);

        tvChange.setOnClickListener(view1 -> {

            ChangeAddressDialog();
        });
        sendTo.setOnClickListener(view1 -> {
            payment = dataSaver.getString("PayMethod", "");
            payment_id = dataSaver.getInt("PayMethod_id", 0);
//            addressIdSapping = dataSaver.getString("addressIdSapping", "");
//            //    Toast.makeText(this, "abdo " + addressIdSapping, Toast.LENGTH_SHORT).show();
//            addressIdBilling = dataSaver.getString("addressIdBilling", "");

            if (payment.equals("")) {
                Toast.makeText(CheckOutActivity.this, getString(R.string.you_must_chose_payment), Toast.LENGTH_SHORT).show();
            } else if (addressIdSapping.equals("") || addressIdBilling.equals("")) {
                Toast.makeText(CheckOutActivity.this, getString(R.string.you_must_selected_shipping_billing), Toast.LENGTH_SHORT).show();

            } else {
                PostOder();

            }

        });
        add_address.setOnClickListener(view1 -> addAddressDialog());
        payment_text.setText(PaymentMethod);
        getCart();

        PaymentMethod();
        rv_richContent.setLayoutManager(new LinearLayoutManager(this));
        rv_payment.setLayoutManager(new LinearLayoutManager(this));

    }

    private void getCart() {
        Call<ShoppingCarts> addEVEnt_call = RetrofitClient.getInstance(CheckOutActivity.this)
                .getCart(id);
        addEVEnt_call.enqueue(new Callback<ShoppingCarts>() {

            @Override
            public void onResponse(Call<ShoppingCarts> call, Response<ShoppingCarts> response) {

                if (response.isSuccessful()) {
                    Log.e("TAG", "isSuccessful");
                    myOrdersAdapter = new MyCartConfirmationsAdapter(CheckOutActivity.this, response.body());
                    myCartRecyclerView.setAdapter(myOrdersAdapter);
                    assert response.body() != null;
                    for (int i = 0; i < response.body().getItems().size(); i++) {
                        price += response.body().getItems().get(i).getTotalprice();
                    }
                    shipping_ground.setText(Rails(CheckOutActivity
                            .this, (double) response.body().getShipping_rate()));
                    sub_total_price.setText(Rails(CheckOutActivity
                            .this, (double) response.body().getSub_Total()));
                    tax.setText(Rails(CheckOutActivity
                            .this, (double) response.body().getTax_rate()));
                    tax_rate = response.body().getTax_rate();
                    sub_total = response.body().getSub_Total();
                    shipping_rate = response.body().getShipping_rate();
                } else {
                    Log.e("TAG", "notSuccessful");
                }
            }

            @Override
            public void onFailure(Call<ShoppingCarts> call, Throwable t) {
                Log.e("TAG ", "onFailure");
                Toast.makeText(CheckOutActivity.this, "Check internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void PostOder() {
        kProgressHUD.show();
        Log.i(TAG, "PostOder: " + id + "        " + price + "\n" + ShppingId + "\n" + BillingId + " \n" +
                PaymentMethod + " \n" + PromationCode.getText().toString() + "\n " + "\n" + ip + "\n" + price);
        Log.e("taxx", "taxx " + sub_total + tax_rate + shipping_rate);
        Call<customer_update_model> addEVEnt_call = RetrofitClient.getInstance(CheckOutActivity.this)
                .api_order_add(id, Integer.valueOf(addressIdSapping), Integer.valueOf(addressIdBilling),
                        payment, PromationCode.getText().toString(), ip, total_price, sub_total, tax_rate, shipping_rate);
        addEVEnt_call.enqueue(new Callback<customer_update_model>() {

            @Override
            public void onResponse(Call<customer_update_model> call, Response<customer_update_model> response) {
                kProgressHUD.dismiss();
                if (response.isSuccessful()) {
                    Log.e("TAG", "isSuccessful");
                    if (response.body().getResult()==true){
                        Toast.makeText(CheckOutActivity.this, "" + response.body().getUserMessage()
                                , Toast.LENGTH_SHORT).show();
                        dataSaver.edit()
                                .putInt("abdo", 0)
                                .apply();
                        order_id = response.body().getNewId();

                        if (payment_id == 2) {
                            Intent inty = new Intent(CheckOutActivity.this, WebViewActivity.class);
                            inty.putExtra("amount", total_price);
                            inty.putExtra("order_id", order_id);
                            startActivity(inty);
                            finishAffinity();
                        } else {
                            successfulOrderDialog(response.body().getNewId());

                        }

                        dataSaver.edit()
                                .putString("addressIdSapping", "")
                                .apply();

                        dataSaver.edit()
                                .putString("addressIdBilling", "")
                                .apply();
                        dataSaver.edit()
                                .putString("PayMethod", "")
                                .apply();

                    }
                    else {
                        Toast.makeText(CheckOutActivity.this, "" + response.body().getUserMessage(), Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Log.e("TAG", "notSuccessful");
                }
            }

            @Override
            public void onFailure(Call<customer_update_model> call, Throwable t) {
                Log.e("TAG ", "onFailure" + t.getMessage() + new Gson().toJson(t.getLocalizedMessage()));

                Toast.makeText(CheckOutActivity.this, "Check internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUserAddress() {
        kProgressHUD.show();

        Call<List<User_address_model>> provincesCall = RetrofitClient.getInstance(CheckOutActivity.this).User_address_model(id);

        provincesCall.enqueue(new Callback<List<User_address_model>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<User_address_model>> call, Response<List<User_address_model>> response) {

                kProgressHUD.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().size() != 0) {

                        add_address.setVisibility(View.GONE);
                        tvChange.setVisibility(View.VISIBLE);
                        addressIdSapping = String.valueOf(response.body().get(0).getAddressId());
                        addressIdBilling = String.valueOf(response.body().get(0).getAddressId());
                        address.setText(response.body().get(0).getFirstname() + "  " + response.body().get(0).getLastname()
                                + "\n" + response.body().get(0).getPhonenumber() + "\n" + response.body().get(0).getAddress1() + "\n" +
                                response.body().get(0).getCountry() + "," + response.body().get(0).getCity());
                    } else {

                        add_address.setVisibility(View.VISIBLE);

                        tvChange.setVisibility(View.GONE);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<User_address_model>> call, Throwable t) {
                Toast.makeText(CheckOutActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                kProgressHUD.dismiss();
            }
        });
    }

    private void getAddressById1(int addressId, int flag) {
        kProgressHUD.show();

        Call<List<User_address_model>> provincesCall = RetrofitClient.getInstance(CheckOutActivity.this).getAddressById(id, addressId);

        provincesCall.enqueue(new Callback<List<User_address_model>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<List<User_address_model>> call, @NotNull Response<List<User_address_model>> response) {

                kProgressHUD.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().size() != 0) {

                        add_address.setVisibility(View.GONE);
                        tvChange.setVisibility(View.VISIBLE);
                        String a = response.body().get(0).getFirstname() + "  " + response.body().get(0).getLastname()
                                + "\n" + response.body().get(0).getEmail() + "\n" + response.body().get(0).getPhonenumber() + "\n" + response.body().get(0).getAddress1()
                                + "," + response.body().get(0).getCity();

                        addressIdSapping = String.valueOf(response.body().get(0).getAddressId());
                        addressIdBilling = String.valueOf(response.body().get(0).getAddressId());
                        address.setVisibility(View.VISIBLE);
                        address.setText(a);
//                        if (flag == 2) {
//                            Intent intent = new Intent(CheckOutActivity.this, CheckOutActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
                        Log.e("TAG ", "abdo elmagic " + a + " abdo ");
                    } else {

                        add_address.setVisibility(View.VISIBLE);
                        tvChange.setVisibility(View.GONE);

                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<User_address_model>> call, @NotNull Throwable t) {
                Toast.makeText(CheckOutActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                kProgressHUD.dismiss();
            }
        });
    }

    private void getAddressById2(int addressId, int flag) {
        kProgressHUD.show();

        Call<List<User_address_model>> provincesCall = RetrofitClient.getInstance(CheckOutActivity.this).getAddressById(id, addressId);

        provincesCall.enqueue(new Callback<List<User_address_model>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<List<User_address_model>> call, @NotNull Response<List<User_address_model>> response) {

                kProgressHUD.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().size() != 0) {

                        /*
                        *  holder.fullName.setText(profile.get(position).getFirstname() + " " + profile.get(position).getLastname());
        if (profile.get(position).getPhonenumber() != null) {
            holder.phone.setText(profile.get(position).getPhonenumber());
        }
        holder.address.setText(profile.get(position).getAddress1() + "\n" + profile.get(position).getCountry() + "," + profile.get(position).getCity());

                        * */


                        add_address.setVisibility(View.GONE);
                        tvChange.setVisibility(View.VISIBLE);
                        String a = response.body().get(0).getFirstname() + "  " + response.body().get(0).getLastname()
                                + "\n" + response.body().get(0).getPhonenumber() + "\n" + response.body().get(0).getAddress1() + "\n" +
                                response.body().get(0).getCountry() + "," + response.body().get(0).getCity();
                        address.setVisibility(View.VISIBLE);
                        address.setText(a);
//                        if (flag == 2) {
                        Intent intent = new Intent(CheckOutActivity.this, CheckOutActivity.class);
                        intent.putExtra("newAddress", addressId);
                        startActivity(intent);
                        finish();
//                        dataSaver.edit()
//                                .putString("addressIdSapping", "")
//                                .apply();
//                        dataSaver.edit()
//                                .putString("addressIdBilling", "")
//                                .apply();
                        dataSaver.edit()
                                .putString("PayMethod", "")
                                .apply();
//                        }
                        Log.e("TAG ", "abdo elmagic " + a + " abdo ");
                    } else {

                        add_address.setVisibility(View.VISIBLE);
                        tvChange.setVisibility(View.GONE);

                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<User_address_model>> call, @NotNull Throwable t) {
                Toast.makeText(CheckOutActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                kProgressHUD.dismiss();
            }
        });
    }

    private void PaymentMethod() {
        kProgressHUD.show();

        Call<OrderView> provincesCall = RetrofitClient.getInstance(CheckOutActivity.this).ORDER_VIEW_CALL();

        provincesCall.enqueue(new Callback<OrderView>() {
            @Override
            public void onResponse(Call<OrderView> call, Response<OrderView> response) {

                kProgressHUD.dismiss();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    Log.i(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    rv_payment.setAdapter(new PersonAdapter(CheckOutActivity.this, response.body().getPaymentMethods(), ShppingId, BillingId));
                    rv_richContent.setAdapter(new richContentAdapter(response.body().getRichContent(), CheckOutActivity.this));

                }
            }

            @Override
            public void onFailure(Call<OrderView> call, Throwable t) {
                Toast.makeText(CheckOutActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                kProgressHUD.dismiss();

            }
        });
    }

    private void addAddressDialog() {

        builder = new AlertDialog.Builder(CheckOutActivity.this).create();
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M) {
            setMargins(builder, 75, 150, 75, 120);
        }
//        Calligrapher calligrapher = new Calligrapher(CheckOutActivity.this);
//        calligrapher.setFont(CheckOutActivity.this, "Ubuntu-R.ttf", true);
        final View v = LayoutInflater.from(CheckOutActivity.this).inflate(R.layout.add_adress_fragment_confrimation, null);
//        setMargins(builder, 75, 150, 75, 120);

        Button add_address = v.findViewById(R.id.add_address);
        dataSaver = getDefaultSharedPreferences(CheckOutActivity.this);
        CUSTOMER_ID = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);
        email_Address = dataSaver.getString(Static_variables.Email, "");
        Log.i(TAG, "addAddressDialog: " + CUSTOMER_ID);

        MainCatSp = v.findViewById(R.id.CountrySp);
        SubCatSp = v.findViewById(R.id.CitySp);
        first_name = v.findViewById(R.id.first_name);
        last_name = v.findViewById(R.id.last_name);
        EmailAddress = v.findViewById(R.id.EmailAddress);
        EmailAddress.setText(email_Address);
        city = v.findViewById(R.id.city);
        Button Shipping = v.findViewById(R.id.Shipping);
        Button Billing = v.findViewById(R.id.Billing);
//        Shipping.setOnClickListener(view ->{} );
//        Billing.setOnClickListener(view -> {});
        SubMobileNumberCatSp = v.findViewById(R.id.MobileNumber);
        address = v.findViewById(R.id.address);
        zip1 = v.findViewById(R.id.zip);

        // provinces Spinner Event
        MainCatSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String provName = MainCatSp.getSelectedItem().toString();
                if (!MainCats_IDs.isEmpty()) {
                    s = MainCats_IDs.get(i);
                    Log.i(TAG, "onItemSelected: " + s);
                    if (i == 0) {
                        SubCatSp.setEnabled(false);
                        SubCats_name.clear();
                        SubCats_IDs.clear();
                        SubCats_IDs.add("");
                        SubCats_name.add(getString(R.string.select_state));


                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(CheckOutActivity.this, R.layout.spinner_view_stamp, SubCats_name);

                        SubCatSp.setAdapter(spinnerArrayAdapter);

                    } else if (i != 0) {
                        SubCatSp.setEnabled(true);

                        kProgressHUD.show();
                        Log.i(TAG, "onItemSelected: " + s);
                        GetSubCate(s);
                    }
                } else {
                    Log.i("No Cities", "لا يوجد مناطق");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Cities Spinner Event
        SubCatSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s = SubCatSp.getSelectedItem().toString();

                Log.i("AreaName", s);
                if (SubCats_IDs.size() != 0) {
                    subCatID = SubCats_IDs.get(i);
//                    Servicesub_id = subCatID;
                    Log.i("AreaID2", subCatID);
//                    Cites = s;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        SubCats_IDs.add("");
        SubCats_name.add(getString(R.string.select_state));


        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(CheckOutActivity.this, R.layout.spinner_view_stamp, SubCats_name);

        SubCatSp.setAdapter(spinnerArrayAdapter);
        GetMainCat();
        add_address.setOnClickListener(view2 -> submitData());

        builder.setView(v);
        // dialog.setCancelable(false);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        builder.show();


    }

    private void ChangeAddressDialog() {

        builder = new AlertDialog.Builder(CheckOutActivity.this).create();
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M) {
            setMargins(builder, 75, 150, 75, 120);
        }
        Calligrapher calligrapher = new Calligrapher(CheckOutActivity.this);
        calligrapher.setFont(CheckOutActivity.this, "Ubuntu-R.ttf", true);
        v = LayoutInflater.from(CheckOutActivity.this).inflate(R.layout.change_adress_fragment_confrimation, null);
        my_address_recycler = v.findViewById(R.id.my_address_recycler);
        my_address_recyclerBilling = v.findViewById(R.id.my_address_recycler_Billing);

        layoutManager = new GridLayoutManager(CheckOutActivity.this, 1);
        layoutManager2 = new GridLayoutManager(CheckOutActivity.this, 1);
        my_address_recycler.setLayoutManager(layoutManager);
        my_address_recyclerBilling.setLayoutManager(layoutManager2);

        getUserAddressUSe();
        builder.setView(v);
//       v.findViewById(R.id.Shipping).setOnClickListener(view -> {


        Button Billing = v.findViewById(R.id.Billing);
        Button Shipping = v.findViewById(R.id.Shipping);
        Button add_address = v.findViewById(R.id.add_address);

        add_address.setOnClickListener(view1 -> {
            addressIdSapping = dataSaver.getString("addressIdSapping", "");
            //    Toast.makeText(this, "abdo " + addressIdSapping, Toast.LENGTH_SHORT).show();
            addressIdBilling = dataSaver.getString("addressIdBilling", "");

            if (addressIdBilling.equals("") || addressIdSapping.equals("")) {

                Toast.makeText(CheckOutActivity.this, getString(R.string.you_must_selected_shipping_billing), Toast.LENGTH_SHORT).show();

            } else {

//                addressIdBilling = AddressConfirmationAdapterBilling.getAddressIdBilling();
                //    Toast.makeText(this, "" + addressIdBilling + "    " + addressIdSapping, Toast.LENGTH_SHORT).show();

//            addressIdBilling = AddressConfirmationAdapterBilling.getAddressIdBilling();

//                   Toast.makeText(CheckOutActivity.this, ""+addressIdSapping, Toast.LENGTH_SHORT).show();
                getAddressById1(Integer.parseInt(addressIdSapping), 2);
                builder.cancel();

            }
        });


//        add_address.setOnClickListener(view11 -> {
//            x = 2;
//
////               Toast.makeText(CheckOutActivity.this, "" + addressIdBilling, Toast.LENGTH_SHORT).show();
//            builder.cancel();
//        });


        Shipping.setOnClickListener(view -> {
            addressIdSapping = addressAdapter.getAddressIdSapping();

            my_address_recycler.setVisibility(View.VISIBLE);
            my_address_recyclerBilling.setVisibility(View.GONE);
            Shipping.setBackgroundColor(Color.parseColor("#E5E5E5"));
            Billing.setBackgroundColor(Color.parseColor("#F6F6F6"));

        });
        Billing.setOnClickListener(view -> {
//            addressIdBilling = AddressConfirmationAdapterBilling.getAddressIdBilling();

            my_address_recycler.setVisibility(View.GONE);
            my_address_recyclerBilling.setVisibility(View.VISIBLE);
            Billing.setBackgroundColor(Color.parseColor("#E5E5E5"));
            Shipping.setBackgroundColor(Color.parseColor("#F6F6F6"));
//            getUserAddressUSe();

        });

        v.findViewById(R.id.add_addressNew).setOnClickListener(view ->
                createAddressDialog()

        );
        // dialog.setCancelable(false);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder.setCancelable(false);
        builder.show();


    }

    private void GetSubCate(String id) {
        Log.i(TAG, "GetSubCate: " + ".............." + id + ".............." + Integer.valueOf(id));


        Call<List<City_model>> areasCall = RetrofitClient.getInstance(CheckOutActivity.this).LIST_CALLCity_model(Integer.valueOf(id));

        areasCall.enqueue(new Callback<List<City_model>>() {

            @Override
            public void onResponse(Call<List<City_model>> call, Response<List<City_model>> response) {
                kProgressHUD.dismiss();

                SubCats_IDs = new ArrayList<>();
                SubCats_name = new ArrayList<>();
                SubCats_name.clear();
                SubCats_IDs.clear();
                SubCats_name.add(getString(R.string.select_state));
                SubCats_IDs.add("");
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().size() != 0) {

                        for (int i = 0; i < response.body().size(); i++) {

                            SubCats_name.add(response.body().get(i).getName());
                            SubCats_IDs.add(String.valueOf(response.body().get(i).getId()));
                        }

                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(CheckOutActivity.this, R.layout.spinner_view_stamp, SubCats_name); //selected item will look like a spinner set from XML
                        SubCatSp.setAdapter(spinnerArrayAdapter);
                        Log.i("City", SubCats_name.toString());
                        kProgressHUD.dismiss();
                    } else {
                        SubCats_name.clear();
                        SubCats_name.add(getString(R.string.no_state));
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(CheckOutActivity.this, R.layout.spinner_view_stamp, SubCats_name);

                        SubCatSp.setAdapter(spinnerArrayAdapter);
                    }
                } else {
//                    Toast.makeText(RegisterActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<City_model>> call, Throwable t) {
                Toast.makeText(CheckOutActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void GetMainCat() {
        kProgressHUD.show();

        Call<List<Country_Model>> provincesCall = RetrofitClient.getInstance(CheckOutActivity.this).COUNTRY_MODEL_CALL();

        provincesCall.enqueue(new Callback<List<Country_Model>>() {
            @Override
            public void onResponse(Call<List<Country_Model>> call, Response<List<Country_Model>> response) {
                kProgressHUD.dismiss();

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().size() != 0) {
                        String cityName = response.body().get(0).getName();
                        Log.i("ProvincesName", cityName);

                        MainCats_name = new ArrayList<>();
                        MainCats_IDs = new ArrayList<String>();
                        MainCats_name.clear();
                        MainCats_IDs.clear();
                        MainCats_IDs.add("");
                        MainCats_name.add(getString(R.string.select_country));
                        for (int i = 0; i < response.body().size(); i++) {
                            MainCats_name.add(response.body().get(i).getName());
                            MainCats_IDs.add(String.valueOf(response.body().get(i).getId()));
                        }

                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(CheckOutActivity.this, R.layout.spinner_view_stamp, MainCats_name); //selected item will look like a spinner set from XML
                        MainCatSp.setAdapter(spinnerArrayAdapter);
                        Log.i("Governments", MainCatSp.toString());
                    } else {
                        MainCats_name.clear();
                        MainCats_name.add(getString(R.string.no_country));
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(CheckOutActivity.this, R.layout.spinner_view_stamp, MainCats_name);
                        MainCatSp.setAdapter(spinnerArrayAdapter);
                    }

                } else {
//                    Toast.makeText(CheckOutActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
//                    progressDialog.dismiss();

                }
//
            }

            @Override
            public void onFailure(Call<List<Country_Model>> call, Throwable t) {
                Toast.makeText(CheckOutActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                kProgressHUD.dismiss();


            }
        });


    }

    private void submitData() {


        String firstName = first_name.getText().toString();
        String last_Name = last_name.getText().toString();
        String emailAddress = EmailAddress.getText().toString();
        String subMobileNumberCatSp = SubMobileNumberCatSp.getText().toString();
        String cityName = city.getText().toString();
        zip = zip1.getText().toString();
        Address = address.getText().toString().trim();


        if (zip.equals("") || firstName.equals("") || cityName.equals("") || last_Name.equals("") || emailAddress.equals("") || Address.equals("")) {
            Toast.makeText(CheckOutActivity.this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show();

        } else if (SubCatSp.getSelectedItemPosition() == 0 || MainCatSp.getSelectedItemPosition() == 0) {
            Toast.makeText(CheckOutActivity.this, getString(R.string.you_must_select_the_country_state), Toast.LENGTH_SHORT).show();

        } else {

            kProgressHUD.show();

            Call<address_add_model> addEVEnt_call = RetrofitClient.getInstance(CheckOutActivity.this)


                    .ADDRESS_ADD_MODEL_CALL(CUSTOMER_ID, firstName, last_Name,
                            emailAddress, Integer.valueOf(s), cityName, Integer.valueOf(subCatID),
                            Address, zip, subMobileNumberCatSp);
            addEVEnt_call.enqueue(new Callback<address_add_model>() {

                @Override
                public void onResponse(Call<address_add_model> call, Response<address_add_model> response) {
                    kProgressHUD.dismiss();

                    if (response.isSuccessful()) {

                        assert response.body() != null;
                        if (response.body().getResult()) {
                            builder.dismiss();
//                            if (pressToExit) {
//                                (new Handler()).postDelayed((() ->
//                            getUserAddressUSe();
                            getAddressById2(response.body().getNewId(), 1);
//                            Toast.makeText(CheckOutActivity.this, "" + response.body().getNewId(), Toast.LENGTH_SHORT).show();
//                            builder.dismiss();
                            City_id = String.valueOf(response.body().getNewId());
//                            if (i == 1) {
//                                getUserAddress1();
//                                Type = i;
//
//                            } else {
//                                Type = i;
//
//                                getUserAddress();
//                                getUserAddress1();
//
//
//                            }


//                                        pressToExit = false), 1000L);
//                            }
//                            getUserAddress();
                            Log.e("TAG", "response.body().getNewId()" + response.body().getNewId());


                            Toast.makeText(CheckOutActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        } else {
                            kProgressHUD.dismiss();
                            Log.e("TAG", "notSuccessful" + new Gson().toJson(response.body()));
                        }
                    }
                }

                @Override
                public void onFailure(Call<address_add_model> call, Throwable t) {
                    Log.e("TAG ", "onFailure " + t.getMessage());
                    kProgressHUD.dismiss();
                    Toast.makeText(CheckOutActivity.this, "Check internet connection!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public void getUserAddressUSe() {
        Call<List<User_address_model>> addEVEnt_call = RetrofitClient.getInstance(CheckOutActivity.this)
                .User_address_model(id);
        addEVEnt_call.enqueue(new Callback<List<User_address_model>>() {

            @Override
            public void onResponse(@NotNull Call<List<User_address_model>> call, @NotNull Response<List<User_address_model>> response) {

                if (response.isSuccessful()) {
                    Log.e("TAG", "isSuccessful");

                    addressAdapter = new AddressConfirmationAdapter(CheckOutActivity.this, response.body());
                    my_address_recycler.setAdapter(addressAdapter);

                    AddressConfirmationAdapterBilling = new AddressConfirmationAdapterBilling(CheckOutActivity.this, response.body());
                    my_address_recyclerBilling.setAdapter(AddressConfirmationAdapterBilling);

                } else {
                    Log.e("TAG", "notSuccessful");
                }
            }

            @Override
            public void onFailure(Call<List<User_address_model>> call, Throwable t) {
                Log.e("TAG ", "onFailure");
                Toast.makeText(CheckOutActivity.this, "Check internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createAddressDialog() {

        builder = new AlertDialog.Builder(CheckOutActivity.this).create();
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M) {
            setMargins(builder, 75, 150, 75, 120);
        }
        final View v = LayoutInflater.from(CheckOutActivity.this).inflate(R.layout.add_adress_fragment, null);
        Button add_address = v.findViewById(R.id.add_address);
        dataSaver = getDefaultSharedPreferences(CheckOutActivity.this);

        CUSTOMER_ID = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);
        email_Address = dataSaver.getString(Static_variables.Email, "");

        MainCatSp = v.findViewById(R.id.CountrySp);
        SubCatSp = v.findViewById(R.id.CitySp);
        first_name = v.findViewById(R.id.first_name);
        city = v.findViewById(R.id.city);
        zip1 = v.findViewById(R.id.zip);
        last_name = v.findViewById(R.id.last_name);
        EmailAddress = v.findViewById(R.id.EmailAddress);
        EmailAddress.setText(email_Address);
        SubMobileNumberCatSp = v.findViewById(R.id.MobileNumber);
        address = v.findViewById(R.id.address);
        MainCatSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String provName = MainCatSp.getSelectedItem().toString();
                if (!MainCats_IDs.isEmpty()) {
                    s = MainCats_IDs.get(i);
                    Log.i(TAG, "onItemSelected: " + s);
                    if (i == 0) {

                        SubCatSp.setEnabled(false);
                        SubCats_name.clear();
                        SubCats_IDs.clear();
                        SubCats_IDs.add("");
                        SubCats_name.add(getString(R.string.select_state));
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(CheckOutActivity.this, R.layout.spinner_view_stamp, SubCats_name);
                        SubCatSp.setAdapter(spinnerArrayAdapter);

                    } else if (i != 0) {
                        SubCatSp.setEnabled(true);

                        kProgressHUD.show();
                        Log.i(TAG, "onItemSelected: " + s);
                        GetSubCate(s);
                    }
                } else {
                    Log.i("No Cities", "لا يوجد مناطق");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        SubCatSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s = SubCatSp.getSelectedItem().toString();
                Log.i("AreaName", s);
                if (SubCats_IDs.size() != 0) {
                    subCatID = SubCats_IDs.get(i);
                    Cites = SubCats_name.get(i);
                    Log.i("AreaID2", Cites);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        SubCats_IDs.add("");
        SubCats_name.add(getString(R.string.select_state));
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(CheckOutActivity.this, R.layout.spinner_view_stamp, SubCats_name);
        SubCatSp.setAdapter(spinnerArrayAdapter);
        GetMainCat();
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        add_address.setOnClickListener(view2 -> submitData());
        builder.setView(v);
        builder.show();
    }

    private void successfulOrderDialog(int newId) {
        GridLayoutManager layoutManager;
        RecyclerView productRecyclerView;
        Button continue_shopping;
        SharedPreferences dataSaver;
        TextView total_price, successfully_ordered;
        int order;
        TextView shipping_ground;
        TextView sub_total_price, order_id;

        TextView tax;
        Button close;
        AlertDialog alertDialog = new AlertDialog.Builder(CheckOutActivity.this).create();
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M) {
            setMargins(alertDialog, 75, 150, 75, 120);
        }
        final View v = LayoutInflater.from(CheckOutActivity.this).inflate(R.layout.activity_order_details_dialog, null);
        total_price = v.findViewById(R.id.total_price);
        continue_shopping = v.findViewById(R.id.continue_shopping);
        successfully_ordered = v.findViewById(R.id.successfully_ordered);
        close = v.findViewById(R.id.close);
        order_id = v.findViewById(R.id.order_id);
        shipping_ground = v.findViewById(R.id.shipping_ground);
        sub_total_price = v.findViewById(R.id.sub_total_price);
        tax = v.findViewById(R.id.tax);
        productRecyclerView = v.findViewById(R.id.product_recycler);
        layoutManager = new GridLayoutManager(this, 1);
        productRecyclerView.setLayoutManager(layoutManager);
        order_id.setText(getString(R.string.order_id) + newId);
        alertDialog.setCancelable(false);
        close.setOnClickListener(view -> {
            Intent intent = new Intent(CheckOutActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.putExtra("order_id", response.body().getNewId());
//            intent.putExtra("order", 1);
            startActivity(intent);
            CheckOutActivity.this.finish();
            CheckOutActivity.this.finishAffinity();
            alertDialog.cancel();
        });
        Call<OrdersModel> addEVEnt_call = RetrofitClient.getInstance(CheckOutActivity.this)
                .getItemsOrders(newId);
        addEVEnt_call.enqueue(new Callback<OrdersModel>() {

            @Override
            public void onResponse(Call<OrdersModel> call, Response<OrdersModel> response) {

                if (response.isSuccessful()) {
                    Log.e("TAG", "isSuccessful");
                    OrdersItemsAdapter myOrdersAdapter1 = new OrdersItemsAdapter(CheckOutActivity.this, response.body());
                    productRecyclerView.setAdapter(myOrdersAdapter1);
                    total_price.setText(Rails(CheckOutActivity
                            .this, (double) response.body().getOrderTotal()));
                    shipping_ground.setText(Rails(CheckOutActivity
                            .this, (double) response.body().getShipping_rate()));
                    sub_total_price.setText(Rails(CheckOutActivity
                            .this, (double) response.body().getSub_Total()));
                    tax.setText(Rails(CheckOutActivity
                            .this, (double) response.body().getTax_rate()));


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
                Toast.makeText(CheckOutActivity.this, "Check internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.setView(v);
        alertDialog.show();
    }

    public boolean loadFragment(Fragment fragment, String itemId, String id) {
        Bundle bundle = new Bundle();
        bundle.putString("ShppingId", itemId);
        bundle.putString("BillingId", id);
        fragment.setArguments(bundle);
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_nav_host, fragment).addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }

    public boolean loadFragment(Fragment fragment, String itemId, String id, String PaymentMethod) {
        Bundle bundle = new Bundle();
        bundle.putString("itemId", itemId);
        bundle.putString("id", id);
        bundle.putString("PaymentMethod", PaymentMethod);
//            Log.i("cat_id_adapter", String.valueOf(itemId));

        fragment.setArguments(bundle);
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_nav_host, fragment).addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }

    public boolean loadFragmentNoStack(Fragment fragment) {

        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_nav_host, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}