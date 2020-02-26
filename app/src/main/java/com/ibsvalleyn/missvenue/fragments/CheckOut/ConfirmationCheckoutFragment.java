//package com.ibsvalleyn.missvenue.fragments.CheckOut;
//
//import android.annotation.SuppressLint;
//import android.app.AlertDialog;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.net.wifi.WifiManager;
//import android.os.Bundle;
//import android.text.format.Formatter;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.gson.Gson;
//import com.ibsvalleyn.missvenue.R;
//import com.ibsvalleyn.missvenue.activities.CheckOutActivity;
//import com.ibsvalleyn.missvenue.activities.OrderDetailsActivity;
//import com.ibsvalleyn.missvenue.adapters.AddressAdapter;
//import com.ibsvalleyn.missvenue.adapters.AddressConfirmationAdapter;
//import com.ibsvalleyn.missvenue.adapters.MyCartConfirmationsAdapter;
//import com.ibsvalleyn.missvenue.adapters.PersonAdapter;
//import com.ibsvalleyn.missvenue.adapters.richContentAdapter;
//import com.ibsvalleyn.missvenue.api.RetrofitClient;
//import com.ibsvalleyn.missvenue.helper.Static_variables;
//import com.ibsvalleyn.missvenue.models.City_model;
//import com.ibsvalleyn.missvenue.models.Country_Model;
//import com.ibsvalleyn.missvenue.models.OrderView.OrderView;
//import com.ibsvalleyn.missvenue.models.ShoppingCarts;
//import com.ibsvalleyn.missvenue.models.User_address_model;
//import com.ibsvalleyn.missvenue.models.address_add_model;
//import com.ibsvalleyn.missvenue.models.customer_update_model;
//import com.kaopiz.kprogresshud.KProgressHUD;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import me.anwarshahriar.calligrapher.Calligrapher;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//import static android.content.Context.WIFI_SERVICE;
//import static android.preference.PreferenceManager.getDefaultSharedPreferences;
//import static com.facebook.FacebookSdk.getApplicationContext;
//import static com.ibsvalleyn.missvenue.helper.AppFunctions.Rails;
//import static com.ibsvalleyn.missvenue.helper.AppFunctions.getKProgressHUD;
//import static com.ibsvalleyn.missvenue.helper.AppFunctions.setMargins;
//
//public class ConfirmationCheckoutFragment extends Fragment {
//    RecyclerView myOrdersRecyclerView;
//    GridLayoutManager layoutManager;
//    MyCartConfirmationsAdapter myOrdersAdapter;
//    SharedPreferences dataSaver;
//    int id;
//    private AddressConfirmationAdapter addressAdapter;
//
//    private KProgressHUD kProgressHUD;
//    private Spinner CitySp;
//    float total_price;
//    List<String> Governments = new ArrayList<>();
//    List<String> Govern_IDs = new ArrayList<>();
//    private String City_id = "??";
//    private TextView add_address;
//    private AlertDialog builder;
//    private int CUSTOMER_ID;
//    private static final String TAG = "klddcjaddsfsdlkjsald";
//    private List<String> MainCats_name = new ArrayList<>();
//    private List<String> MainCats_IDs = new ArrayList<>();
//    private List<String> SubCats_IDs = new ArrayList<>();
//    private List<String> SubCats_name = new ArrayList<>();
//    private String subCatID;
//    private EditText first_name;
//    private EditText last_name;
//    private EditText EmailAddress;
//    private EditText SubMobileNumberCatSp;
//    private TextView address;
//    private EditText postalCode;
//    private String s;
//    private Spinner MainCatSp, SubCatSp;
//    private String Cites;
//    private Button sendTo;
//    CheckOutActivity activity;
//    private Spinner getAllAddress;
//    private String provName;
//    private RecyclerView myCartRecyclerView;
//    private String ip;
//    private String PaymentMethod;
//    private String ShppingId;
//    private String BillingId;
//    private EditText PromationCode;
//    private double price;
//    TextView totalPrice;
//    private TextView payment_text;
//    private RecyclerView rv_richContent;
//    private RecyclerView rv_payment;
//    private String Address;
//    private TextView tvChange, ed_name;
//
//    private RecyclerView my_address_recycler;
//    private View v;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        activity = (CheckOutActivity) getActivity();
//    }
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.checkout_confirmation_fragment, container, false);
//        dataSaver = getDefaultSharedPreferences(activity);
//        assert getArguments() != null;
//        Log.i(TAG, "onCreateView: " + BillingId);
//
//        view.findViewById(R.id.back).setOnClickListener(view1 -> activity.onBackPressed());
//
//        id = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);
//        total_price = dataSaver.getFloat(Static_variables.Total_price, 0);
//        assert getArguments() != null;
//        totalPrice = view.findViewById(R.id.total_price);
//        payment_text = view.findViewById(R.id.payment_text);
//        add_address = view.findViewById(R.id.add_address);
//        tvChange = view.findViewById(R.id.tvChange);
//        ed_name = view.findViewById(R.id.ed_name);
//
//        rv_payment = view.findViewById(R.id.rv_payment);
//        rv_richContent = view.findViewById(R.id.rv_richContent);
//
//        kProgressHUD = getKProgressHUD(activity);
//        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//        ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
//        view.findViewById(R.id.back).setOnClickListener(view1 -> activity.onBackPressed());
//        sendTo = view.findViewById(R.id.sendTo);
//        getAllAddress = view.findViewById(R.id.getAllAddress);
//        myCartRecyclerView = view.findViewById(R.id.my_cart_recycler);
//        layoutManager = new GridLayoutManager(activity, 1);
//        myCartRecyclerView.setLayoutManager(layoutManager);
//        address = view.findViewById(R.id.addressvvvv);
//        PromationCode = view.findViewById(R.id.PromationCode);
//        totalPrice.setText(Rails(activity,String.valueOf((int) total_price)));
//
//        Calligrapher calligrapher = new Calligrapher(activity);
//        calligrapher.setFont(activity, "Ubuntu-M.ttf", true);
//
//        tvChange.setOnClickListener(view1 -> ChangeAddressDialog());
//        sendTo.setOnClickListener(view1 -> {
//            sendTo.setClickable(false);
//            PostOder();
//        });
//        add_address.setOnClickListener(view1 -> addAddressDialog());
//        payment_text.setText(PaymentMethod);
//        getCart();
//        getUserAddress();
//
//        PaymentMethod();
//        rv_richContent.setLayoutManager(new LinearLayoutManager(activity));
//        rv_payment.setLayoutManager(new LinearLayoutManager(activity));
//
//
//        return view;
//
//    }
//
//    private void getCart() {
//        Call<ShoppingCarts> addEVEnt_call = RetrofitClient.getInstance(activity)
//                .getCart(id);
//        addEVEnt_call.enqueue(new Callback<ShoppingCarts>() {
//
//            @Override
//            public void onResponse(Call<ShoppingCarts> call, Response<ShoppingCarts> response) {
//
//                if (response.isSuccessful()) {
//                    Log.e("TAG", "isSuccessful");
//                    myOrdersAdapter = new MyCartConfirmationsAdapter(activity, response.body());
//                    myCartRecyclerView.setAdapter(myOrdersAdapter);
//                    assert response.body() != null;
//                    for (int i = 0; i < response.body().getItems().size(); i++) {
//                        price += response.body().getItems().get(i).getTotalprice();
//                    }
//                } else {
//                    Log.e("TAG", "notSuccessful");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ShoppingCarts> call, Throwable t) {
//                Log.e("TAG ", "onFailure");
//                Toast.makeText(activity, "Check internet connection!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void PostOder() {
//        Log.i(TAG, "PostOder: " + id + "        " + price + "\n" + ShppingId + "\n" + BillingId + " \n" +
//                PaymentMethod + " \n" + PromationCode.getText().toString() + "\n " + "\n" + ip + "\n" + price);
//
//        Call<customer_update_model> addEVEnt_call = RetrofitClient.getInstance(activity)
//                .api_order_add(id, Integer.valueOf(ShppingId), Integer.valueOf(BillingId),
//                        PaymentMethod, PromationCode.getText().toString(), ip, total_price);
//        addEVEnt_call.enqueue(new Callback<customer_update_model>() {
//
//            @Override
//            public void onResponse(Call<customer_update_model> call, Response<customer_update_model> response) {
//
//                if (response.isSuccessful()) {
//                    Log.e("TAG", "isSuccessful");
//
//                    Toast.makeText(activity, "" + response.body().getMessage()
//                            , Toast.LENGTH_SHORT).show();
//                    dataSaver.edit()
//                            .putInt("abdo", 0)
//                            .apply();
//
//                    Intent intent = new Intent(activity, OrderDetailsActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.putExtra("order_id", response.body().getNewId());
//                    intent.putExtra("order", 1);
//                    startActivity(intent);
//                    activity.finish();
//                    activity.finishAffinity();
//
//                } else {
//                    Log.e("TAG", "notSuccessful");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<customer_update_model> call, Throwable t) {
//                Log.e("TAG ", "onFailure" + t.getMessage() + new Gson().toJson(t.getLocalizedMessage()));
//
//                Toast.makeText(activity, "Check internet connection!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void getUserAddress() {
//        kProgressHUD.show();
//
//        Call<List<User_address_model>> provincesCall = RetrofitClient.getInstance(activity).User_address_model(id);
//
//        provincesCall.enqueue(new Callback<List<User_address_model>>() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onResponse(Call<List<User_address_model>> call, Response<List<User_address_model>> response) {
//
//                kProgressHUD.dismiss();
//                if (response.isSuccessful()) {
//                    if (response.body().size() != 0) {
//
//                        add_address.setVisibility(View.GONE);
//                        tvChange.setVisibility(View.VISIBLE);
//
//                        address.setText(response.body().get(0).getFirstname() + "  " + response.body().get(0).getLastname()
//                                + "\n" + response.body().get(0).getEmail() + "\n" + response.body().get(0).getPhonenumber() + "\n" + response.body().get(0).getAddress1()
//                                + "," + response.body().get(0).getCity());
//                    } else {
//
//                        add_address.setVisibility(View.VISIBLE);
//
//                        tvChange.setVisibility(View.GONE);
//
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<User_address_model>> call, Throwable t) {
//                Toast.makeText(activity, R.string.error, Toast.LENGTH_SHORT).show();
//                kProgressHUD.dismiss();
//            }
//        });
//    }
//
//    private void PaymentMethod() {
//        kProgressHUD.show();
//
//        Call<OrderView> provincesCall = RetrofitClient.getInstance(activity).ORDER_VIEW_CALL();
//
//        provincesCall.enqueue(new Callback<OrderView>() {
//            @Override
//            public void onResponse(Call<OrderView> call, Response<OrderView> response) {
//
//                kProgressHUD.dismiss();
//                if (response.isSuccessful()) {
//                    assert response.body() != null;
//                    Log.i(TAG, "onResponse: " + new Gson().toJson(response.body()));
//                    rv_payment.setAdapter(new PersonAdapter(activity, response.body().getPaymentMethods(), ShppingId, BillingId));
//                    rv_richContent.setAdapter(new richContentAdapter(response.body().getRichContent(), activity));
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<OrderView> call, Throwable t) {
//                Toast.makeText(activity, R.string.error, Toast.LENGTH_SHORT).show();
//                kProgressHUD.dismiss();
//
//            }
//        });
//    }
//
//    private void addAddressDialog() {
//
//        builder = new AlertDialog.Builder(activity).create();
//        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M) {
//            setMargins(builder, 75, 150, 75, 120);
//        }
//        Calligrapher calligrapher = new Calligrapher(activity);
//        calligrapher.setFont(activity, "Ubuntu-M.ttf", true);
//        final View v = LayoutInflater.from(activity).inflate(R.layout.add_adress_fragment_confrimation, null);
////        setMargins(builder, 75, 150, 75, 120);
//
//        Button add_address = v.findViewById(R.id.add_address);
//        dataSaver = getDefaultSharedPreferences(activity);
//        CUSTOMER_ID = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);
//        Log.i(TAG, "addAddressDialog: " + CUSTOMER_ID);
//
//        MainCatSp = v.findViewById(R.id.CountrySp);
//        SubCatSp = v.findViewById(R.id.CitySp);
//        first_name = v.findViewById(R.id.first_name);
//        last_name = v.findViewById(R.id.last_name);
//        EmailAddress = v.findViewById(R.id.EmailAddress);
//        Button Shipping = v.findViewById(R.id.Shipping);
//        Button Billing = v.findViewById(R.id.Billing);
////        Shipping.setOnClickListener(view ->{} );
////        Billing.setOnClickListener(view -> {});
//        SubMobileNumberCatSp = v.findViewById(R.id.MobileNumber);
//        address = v.findViewById(R.id.address);
//        add_address.setOnClickListener(view -> {
//
//            builder.dismiss();
//        });
//        // provinces Spinner Event
//        MainCatSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String provName = MainCatSp.getSelectedItem().toString();
//                if (!MainCats_IDs.isEmpty()) {
//                    s = MainCats_IDs.get(i);
//                    Log.i(TAG, "onItemSelected: " + s);
//                    if (i == 0) {
//                        SubCatSp.setEnabled(false);
//                        SubCats_name.clear();
//                        SubCats_IDs.clear();
//                        SubCats_IDs.add("");
//                        SubCats_name.add(" select state");
//
//
//                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(activity, R.layout.spinner_view_stamp, SubCats_name);
//
//                        SubCatSp.setAdapter(spinnerArrayAdapter);
//
//                    } else if (i != 0) {
//                        SubCatSp.setEnabled(true);
//
//                        kProgressHUD.show();
//                        Log.i(TAG, "onItemSelected: " + s);
//                        GetSubCate(s);
//                    }
//                } else {
//                    Log.i("No Cities", "لا يوجد مناطق");
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//
//        //Cities Spinner Event
//        SubCatSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String s = SubCatSp.getSelectedItem().toString();
//
//                Log.i("AreaName", s);
//                if (SubCats_IDs.size() != 0) {
//                    subCatID = SubCats_IDs.get(i);
////                    Servicesub_id = subCatID;
//                    Log.i("AreaID2", subCatID);
//                    Cites = s;
//
//
////
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        SubCats_IDs.add("");
//        SubCats_name.add("select state ");
//
//
//        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(activity, R.layout.spinner_view_stamp, SubCats_name);
//
//        SubCatSp.setAdapter(spinnerArrayAdapter);
//        GetMainCat();
//        add_address.setOnClickListener(view2 -> submitData());
//        builder.setView(v);
//        // dialog.setCancelable(false);
//        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        builder.show();
//
//
//    }
//
//    private void ChangeAddressDialog() {
//
//        builder = new AlertDialog.Builder(activity).create();
//        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M) {
//            setMargins(builder, 75, 150, 75, 120);
//        }
//        Calligrapher calligrapher = new Calligrapher(activity);
//        calligrapher.setFont(activity, "Ubuntu-M.ttf", true);
//        v = LayoutInflater.from(activity).inflate(R.layout.change_adress_fragment_confrimation, null);
//        my_address_recycler = v.findViewById(R.id.my_address_recycler);
//        layoutManager = new GridLayoutManager(activity, 1);
//        my_address_recycler.setLayoutManager(layoutManager);
//        getUserAddressUSe();
//        builder.setView(v);
//       v.findViewById(R.id.Shipping).setOnClickListener(view -> {
//           v.findViewById(R.id.add_address).setOnClickListener(view1 -> {
//               String addressIdSapping = addressAdapter.getAddressIdSapping();
//               Toast.makeText(activity, ""+addressIdSapping, Toast.LENGTH_SHORT).show();
//               builder.cancel();
//           });
//       });
//
//       v.findViewById(R.id.Billing).setOnClickListener(view -> {
//
//           v.findViewById(R.id.add_address).setOnClickListener(view11 -> {
//               String addressIdSapping = addressAdapter.getAddressIdSapping();
//               Toast.makeText(activity, "" + addressIdSapping, Toast.LENGTH_SHORT).show();
//               builder.cancel();
//           });
//       });
//
//        v.findViewById(R.id.add_addressNew).setOnClickListener(view ->
//                createAddressDialog()
//
//        );
//        // dialog.setCancelable(false);
//        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        builder.show();
//
//
//    }
//
//    private void GetSubCate(String id) {
//        Log.i(TAG, "GetSubCate: " + ".............." + id + ".............." + Integer.valueOf(id));
//
//
//        Call<List<City_model>> areasCall = RetrofitClient.getInstance(activity).LIST_CALLCity_model(Integer.valueOf(id));
//
//        areasCall.enqueue(new Callback<List<City_model>>() {
//
//            @Override
//            public void onResponse(Call<List<City_model>> call, Response<List<City_model>> response) {
//                kProgressHUD.dismiss();
//
//                SubCats_IDs = new ArrayList<>();
//                SubCats_name = new ArrayList<>();
//                SubCats_name.clear();
//                SubCats_IDs.clear();
//                SubCats_name.add("select state");
//                SubCats_IDs.add("");
//                if (response.isSuccessful()) {
//                    assert response.body() != null;
//                    if (response.body().size() != 0) {
//
//                        for (int i = 0; i < response.body().size(); i++) {
//
//                            SubCats_name.add(response.body().get(i).getName());
//                            SubCats_IDs.add(String.valueOf(response.body().get(i).getId()));
//                        }
//
//                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(activity, R.layout.spinner_view_stamp, SubCats_name); //selected item will look like a spinner set from XML
//                        SubCatSp.setAdapter(spinnerArrayAdapter);
//                        Log.i("City", SubCats_name.toString());
//                        kProgressHUD.dismiss();
//                    } else {
//                        SubCats_name.clear();
//                        SubCats_name.add("no city");
//                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(activity, R.layout.spinner_view_stamp, SubCats_name);
//
//                        SubCatSp.setAdapter(spinnerArrayAdapter);
//                    }
//                } else {
////                    Toast.makeText(RegisterActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<City_model>> call, Throwable t) {
//                Toast.makeText(activity, R.string.error, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//
//    private void GetMainCat() {
//        kProgressHUD.show();
//
//        Call<List<Country_Model>> provincesCall = RetrofitClient.getInstance(activity).COUNTRY_MODEL_CALL();
//
//        provincesCall.enqueue(new Callback<List<Country_Model>>() {
//            @Override
//            public void onResponse(Call<List<Country_Model>> call, Response<List<Country_Model>> response) {
//                kProgressHUD.dismiss();
//
//                if (response.isSuccessful()) {
//                    assert response.body() != null;
//                    if (response.body().size() != 0) {
//                        String cityName = response.body().get(0).getName();
//                        Log.i("ProvincesName", cityName);
//
//                        MainCats_name = new ArrayList<>();
//                        MainCats_IDs = new ArrayList<String>();
//                        MainCats_name.clear();
//                        MainCats_IDs.clear();
//                        MainCats_IDs.add("");
//                        MainCats_name.add("select country");
//                        for (int i = 0; i < response.body().size(); i++) {
//                            MainCats_name.add(response.body().get(i).getName());
//                            MainCats_IDs.add(String.valueOf(response.body().get(i).getId()));
//                        }
//
//                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(activity, R.layout.spinner_view_stamp, MainCats_name); //selected item will look like a spinner set from XML
//                        MainCatSp.setAdapter(spinnerArrayAdapter);
//                        Log.i("Governments", MainCatSp.toString());
//                    } else {
//                        MainCats_name.clear();
//                        MainCats_name.add("no country");
//                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(activity, R.layout.spinner_view_stamp, MainCats_name);
//                        MainCatSp.setAdapter(spinnerArrayAdapter);
//                    }
//
//                } else {
////                    Toast.makeText(activity, R.string.server_error, Toast.LENGTH_SHORT).show();
////                    progressDialog.dismiss();
//
//                }
////
//            }
//
//            @Override
//            public void onFailure(Call<List<Country_Model>> call, Throwable t) {
//                Toast.makeText(activity, R.string.error, Toast.LENGTH_SHORT).show();
//                kProgressHUD.dismiss();
//
//
//            }
//        });
//
//
//    }
//
//    private void submitData() {
//
//
//        String firstName = first_name.getText().toString();
//        String last_Name = last_name.getText().toString();
//        String emailAddress = EmailAddress.getText().toString();
//        String subMobileNumberCatSp = SubMobileNumberCatSp.getText().toString();
//        Address = address.getText().toString().trim();
//
//
//        if (firstName.equals("") || last_Name.equals("") || emailAddress.equals("") || Address.equals("")) {
//            Toast.makeText(activity, "Fill all fields", Toast.LENGTH_SHORT).show();
//
//        } else if (SubCatSp.getSelectedItemPosition() == 0 || MainCatSp.getSelectedItemPosition() == 0) {
//            Toast.makeText(activity, "you must select the country && city", Toast.LENGTH_SHORT).show();
//
//        } else {
//
//            kProgressHUD.show();
//
//            Call<address_add_model> addEVEnt_call = RetrofitClient.getInstance(activity)
//
//
//                    .ADDRESS_ADD_MODEL_CALL(CUSTOMER_ID, firstName, last_Name,
//                            emailAddress, Integer.valueOf(s), Cites,
//                            Address, subMobileNumberCatSp);
//            addEVEnt_call.enqueue(new Callback<address_add_model>() {
//
//                @Override
//                public void onResponse(Call<address_add_model> call, Response<address_add_model> response) {
//                    kProgressHUD.dismiss();
//
//                    if (response.isSuccessful()) {
//
//                        assert response.body() != null;
//                        if (response.body().getResult()) {
//                            builder.dismiss();
////                            if (pressToExit) {
////                                (new Handler()).postDelayed((() ->
//
//                            City_id = String.valueOf(response.body().getNewId());
////                            if (i == 1) {
////                                getUserAddress1();
////                                Type = i;
////
////                            } else {
////                                Type = i;
////
////                                getUserAddress();
////                                getUserAddress1();
////
////
////                            }
//
//
////                                        pressToExit = false), 1000L);
////                            }
////                            getUserAddress();
//
//
//                            Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//
//                        } else {
//                            kProgressHUD.dismiss();
//                            Log.e("TAG", "notSuccessful" + new Gson().toJson(response.body()));
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<address_add_model> call, Throwable t) {
//                    Log.e("TAG ", "onFailure " + t.getMessage());
//                    kProgressHUD.dismiss();
//                    Toast.makeText(activity, "Check internet connection!", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }
//
//    public void getUserAddressUSe() {
//        Call<List<User_address_model>> addEVEnt_call = RetrofitClient.getInstance(activity)
//                .User_address_model(id);
//        addEVEnt_call.enqueue(new Callback<List<User_address_model>>() {
//
//            @Override
//            public void onResponse(Call<List<User_address_model>> call, Response<List<User_address_model>> response) {
//
//                if (response.isSuccessful()) {
//                    Log.e("TAG", "isSuccessful");
//
//                    addressAdapter = new AddressConfirmationAdapter(activity, response.body());
//                    my_address_recycler.setAdapter(addressAdapter);
//
//                } else {
//                    Log.e("TAG", "notSuccessful");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<User_address_model>> call, Throwable t) {
//                Log.e("TAG ", "onFailure");
//                Toast.makeText(ConfirmationCheckoutFragment.this.activity, "Check internet connection!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//    private void createAddressDialog() {
//
//        builder = new AlertDialog.Builder(activity).create();
//        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M) {
//            setMargins(builder, 75, 150, 75, 120);
//        }
//        final View v = LayoutInflater.from(activity).inflate(R.layout.add_adress_fragment, null);
//        Button add_address = v.findViewById(R.id.add_address);
//        dataSaver = getDefaultSharedPreferences(activity);
//        CUSTOMER_ID = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);
//
//        MainCatSp = v.findViewById(R.id.CountrySp);
//        SubCatSp = v.findViewById(R.id.CitySp);
//        first_name = v.findViewById(R.id.first_name);
//        last_name = v.findViewById(R.id.last_name);
//        EmailAddress = v.findViewById(R.id.EmailAddress);
//        SubMobileNumberCatSp = v.findViewById(R.id.MobileNumber);
//        address = v.findViewById(R.id.address);
//        MainCatSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String provName = MainCatSp.getSelectedItem().toString();
//                if (!MainCats_IDs.isEmpty()) {
//                    s = MainCats_IDs.get(i);
//                    Log.i(TAG, "onItemSelected: " + s);
//                    if (i == 0) {
//
//                        SubCatSp.setEnabled(false);
//                        SubCats_name.clear();
//                        SubCats_IDs.clear();
//                        SubCats_IDs.add("");
//                        SubCats_name.add(" select state");
//                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(activity, R.layout.spinner_view_stamp, SubCats_name);
//                        SubCatSp.setAdapter(spinnerArrayAdapter);
//
//                    } else if (i != 0) {
//                        SubCatSp.setEnabled(true);
//
//                        kProgressHUD.show();
//                        Log.i(TAG, "onItemSelected: " + s);
//                        GetSubCate(s);
//                    }
//                } else {
//                    Log.i("No Cities", "لا يوجد مناطق");
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });
//
//        SubCatSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String s = SubCatSp.getSelectedItem().toString();
//                Log.i("AreaName", s);
//                if (SubCats_IDs.size() != 0) {
//                    subCatID = SubCats_IDs.get(i);
//                    Cites = SubCats_name.get(i);
//                    Log.i("AreaID2", Cites);
//
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });
//
//        SubCats_IDs.add("");
//        SubCats_name.add("select state ");
//        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(activity, R.layout.spinner_view_stamp, SubCats_name);
//        SubCatSp.setAdapter(spinnerArrayAdapter);
//        GetMainCat();
//        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        add_address.setOnClickListener(view2 -> submitData());
//        builder.setView(v);
//        builder.show();
//    }
//}