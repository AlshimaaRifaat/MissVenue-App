//package com.ibsvalleyn.missvenue.fragments.CheckOut;
//
//import android.app.AlertDialog;
//import android.content.SharedPreferences;
//import android.os.Bundle;
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
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.gson.Gson;
//import com.ibsvalleyn.missvenue.R;
//import com.ibsvalleyn.missvenue.activities.CheckOutActivity;
//import com.ibsvalleyn.missvenue.adapters.MyOrdersAdapter;
//import com.ibsvalleyn.missvenue.api.RetrofitClient;
//import com.ibsvalleyn.missvenue.helper.Static_variables;
//import com.ibsvalleyn.missvenue.models.City_model;
//import com.ibsvalleyn.missvenue.models.Country_Model;
//import com.ibsvalleyn.missvenue.models.User_address_model;
//import com.ibsvalleyn.missvenue.models.address_add_model;
//import com.kaopiz.kprogresshud.KProgressHUD;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//import static android.preference.PreferenceManager.getDefaultSharedPreferences;
//import static com.ibsvalleyn.missvenue.helper.AppFunctions.getKProgressHUD;
//
//public class BillingCheckoutFragment extends Fragment {
//    RecyclerView myOrdersRecyclerView;
//    GridLayoutManager layoutManager;
//    MyOrdersAdapter myOrdersAdapter;
//    SharedPreferences dataSaver;
//    int id;
//    private KProgressHUD kProgressHUD;
//    private Spinner CitySp1;
//
//    List<String> Governments1 = new ArrayList<>();
//    List<String> Govern_IDs1 = new ArrayList<>();
//    private String City_id1;
//    private TextView add_address1;
//    private AlertDialog builder1;
//    private int CUSTOMER_ID;
//    private static final String TAG = "dsdsadsad";
//
//    private List<String> MainCats_name1 = new ArrayList<>();
//    private List<String> MainCats_IDs1 = new ArrayList<>();
//    private List<String> SubCats_IDs1 = new ArrayList<>();
//    private List<String> SubCats_name1 = new ArrayList<>();
//    private String subCatID1;
//    private EditText first_name1;
//    private EditText last_name1;
//    private EditText EmailAddress1;
//    private EditText SubMobileNumberCatSp1;
//    private EditText address1;
//    private EditText postalCode;
//    private String s1;
//    private Spinner MainCatSp1, SubCatSp1;
//    private String Cites1;
//    private Button add_to_cart;
//    CheckOutActivity activity;
//    private Spinner getAllAddress1;
//    private String itemId1;
//    private String provName1;
//    private TextView addressName;
//    private boolean pressToExit = false;
//    private Button sendTo;
//    private String ShppingId;
//    private boolean Status = false;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        activity = (CheckOutActivity) getActivity();
//        kProgressHUD = getKProgressHUD(activity);
//
//
//    }
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.checkout_billing_fragment, container, false);
//
//        assert getArguments() != null;
//        ShppingId = getArguments().getString("itemId1");
//        itemId1 = getArguments().getString("id");
//        Log.i(TAG, "onCreateView: " + itemId1 + ShppingId);
//
//
//
//
//
//        dataSaver = getDefaultSharedPreferences(activity);
//        id = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);
//        view.findViewById(R.id.back).setOnClickListener(view1 -> activity.onBackPressed());
//
//
//        add_address1 = view.findViewById(R.id.add_address1);
//        add_to_cart = view.findViewById(R.id.add_to_cart);
//        sendTo = view.findViewById(R.id.sendTo);
//        getAllAddress1 = view.findViewById(R.id.getAllAddress1);
//        addressName = view.findViewById(R.id.addressName);
//
//        addressName.setText("you select shaping is :" + itemId1);
//
//        add_address1.setOnClickListener(view1 ->
//                addAddressDialog()
//        );
//
//        getUserAddress();
//
//        getAllAddress1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                provName1 = getAllAddress1.getSelectedItem().toString();
//                if (!Govern_IDs1.isEmpty()) {
//
//
//                    String s = Govern_IDs1.get(i);
//
//
//                    if (i != 0) {
//                        Log.i("asdaskldj", "onItemSelected: " + s);
//                        City_id1 = s;
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
//        sendTo.setOnClickListener(view1 -> {
//            if (getAllAddress1.getSelectedItemPosition() == 0) {
//                Toast.makeText(activity, "you must select the address", Toast.LENGTH_SHORT).show();
//            } else if (Status) {
//                activity.loadFragment(new PaymentCheckoutFragment(), City_id1, ShppingId);
//
//            } else {
//                activity.loadFragment(new PaymentCheckoutFragment(), City_id1, ShppingId);
//            }
//
//        });
//
////
//        return view;
//    }
//
//    public List<String> getUserAddress() {
//        kProgressHUD.show();
//
//        Call<List<User_address_model>> provincesCall = RetrofitClient.getInstance(activity).User_address_model(id);
//
//        provincesCall.enqueue(new Callback<List<User_address_model>>() {
//            @Override
//            public void onResponse(Call<List<User_address_model>> call, Response<List<User_address_model>> response) {
//
//                kProgressHUD.dismiss();
//
//                if (response.isSuccessful()) {
//                    assert response.body() != null;
//                    if (response.body().size() != 0) {
//                        String cityName = response.body().get(0).getAddress1();
//                        Log.i("ProvincesName", cityName);
//
//                        Governments1 = new ArrayList<>();
//                        Govern_IDs1 = new ArrayList<>();
//                        Govern_IDs1.add("");
//                        Governments1.add("select address");
//                        for (int i = 0; i < response.body().size(); i++) {
//                            Governments1.add(response.body().get(i).getAddress1());
//                            Govern_IDs1.add(String.valueOf(response.body().get(i).getAddressId()));
//                        }
//
//                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(activity, R.layout.spinner_view_stamp, Governments1); //selected item will look like a spinner set from XML
//                        getAllAddress1.setAdapter(spinnerArrayAdapter);
//                        Log.i("Governments1", Governments1.toString());
//                    } else {
//                        Governments1.clear();
//                        Governments1.add("no country");
//                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(activity, R.layout.spinner_view_stamp, Governments1);
//                        getAllAddress1.setAdapter(spinnerArrayAdapter);
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
//            public void onFailure(Call<List<User_address_model>> call, Throwable t) {
//                Toast.makeText(activity, R.string.error, Toast.LENGTH_SHORT).show();
//                kProgressHUD.dismiss();
//
//
//            }
//        });
//        pressToExit = true;
//
//
//        return Governments1;
//    }
//
//
//    public void addAddressDialog() {
//
//        builder1 = new AlertDialog.Builder(activity).create();
//
//        final View v = LayoutInflater.from(activity).inflate(R.layout.add_adress_fragment, null);
//
//        Button add_address1 = v.findViewById(R.id.add_address1);
//        dataSaver = getDefaultSharedPreferences(activity);
//        CUSTOMER_ID = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);
//        Log.i(TAG, "addAddressDialog: " + CUSTOMER_ID);
//
//        MainCatSp1 = v.findViewById(R.id.CountrySp);
//        SubCatSp1 = v.findViewById(R.id.CitySp);
//        first_name1 = v.findViewById(R.id.first_name);
//        last_name1 = v.findViewById(R.id.last_name);
//        EmailAddress1 = v.findViewById(R.id.EmailAddress);
//        SubMobileNumberCatSp1 = v.findViewById(R.id.MobileNumber);
//        address1 = v.findViewById(R.id.address);
//
//        // provinces Spinner Event
//        MainCatSp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String provName1 = MainCatSp1.getSelectedItem().toString();
//                if (!MainCats_IDs1.isEmpty()) {
//                    s1 = MainCats_IDs1.get(i);
//                    Log.i(TAG, "onItemSelected: " + s1);
//                    if (i == 0) {
//                        SubCatSp1.setEnabled(false);
//                        SubCats_name1.clear();
//                        SubCats_IDs1.clear();
//                        SubCats_IDs1.add("");
//                        SubCats_name1.add(" select country");
//
//
//                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(activity, R.layout.spinner_view_stamp, SubCats_name1);
//
//                        SubCatSp1.setAdapter(spinnerArrayAdapter);
//
//                    } else if (i != 0) {
//                        SubCatSp1.setEnabled(true);
//
//                        kProgressHUD.show();
//                        Log.i(TAG, "onItemSelected: " + s1);
//                        GetSubCate(s1);
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
//        SubCatSp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String s = SubCatSp1.getSelectedItem().toString();
//
//                Log.i("AreaName", s);
//                if (SubCats_IDs1.size() != 0) {
//                    subCatID1 = SubCats_IDs1.get(i);
////                    Servicesub_id = subCatID1;
//                    Log.i("AreaID2", subCatID1);
//                    Cites1 = s;
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
//        SubCats_IDs1.add("");
//        SubCats_name1.add("select state ");
//
//
//        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(activity, R.layout.spinner_view_stamp, SubCats_name1);
//
//        SubCatSp1.setAdapter(spinnerArrayAdapter);
//        GetMainCat();
//        add_address1.setOnClickListener(view2 -> submitData());
//        builder1.setView(v);
//        // dialog.setCancelable(false);
//        builder1.show();
//
//
//    }
//
//    public List<String> GetSubCate(String id) {
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
//                SubCats_IDs1 = new ArrayList<>();
//                SubCats_name1 = new ArrayList<>();
//                SubCats_name1.clear();
//                SubCats_IDs1.clear();
//                SubCats_name1.add("select state");
//                SubCats_IDs1.add("");
//                if (response.isSuccessful()) {
//                    assert response.body() != null;
//                    if (response.body().size() != 0) {
//
//                        for (int i = 0; i < response.body().size(); i++) {
//
//                            SubCats_name1.add(response.body().get(i).getName());
//                            SubCats_IDs1.add(String.valueOf(response.body().get(i).getId()));
//                        }
//
//                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(activity, R.layout.spinner_view_stamp, SubCats_name1); //selected item will look like a spinner set from XML
//                        SubCatSp1.setAdapter(spinnerArrayAdapter);
//                        Log.i("City", SubCats_name1.toString());
//                        kProgressHUD.dismiss();
//                    } else {
//                        SubCats_name1.clear();
//                        SubCats_name1.add("no city");
//                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(activity, R.layout.spinner_view_stamp, SubCats_name1);
//
//                        SubCatSp1.setAdapter(spinnerArrayAdapter);
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
//        return SubCats_name1;
//    }
//
//
//    public List<String> GetMainCat() {
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
//                        MainCats_name1 = new ArrayList<>();
//                        MainCats_IDs1 = new ArrayList<>();
//                        MainCats_name1.clear();
//                        MainCats_IDs1.clear();
//                        MainCats_IDs1.add("");
//                        MainCats_name1.add("select state");
//                        for (int i = 0; i < response.body().size(); i++) {
//                            MainCats_name1.add(response.body().get(i).getName());
//                            MainCats_IDs1.add(String.valueOf(response.body().get(i).getId()));
//                        }
//
//                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(activity, R.layout.spinner_view_stamp, MainCats_name1); //selected item will look like a spinner set from XML
//                        MainCatSp1.setAdapter(spinnerArrayAdapter);
//                        Log.i("Governments1", MainCatSp1.toString());
//                    } else {
//                        MainCats_name1.clear();
//                        MainCats_name1.add("no country");
//                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(activity, R.layout.spinner_view_stamp, MainCats_name1);
//                        MainCatSp1.setAdapter(spinnerArrayAdapter);
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
//        return MainCats_name1;
//    }
//
//    public void submitData() {
//
//
//        String firstName = first_name1.getText().toString();
//        String last_Name = last_name1.getText().toString();
//        String emailAddress = EmailAddress1.getText().toString();
//        String SubMobileNumberCatSp = SubMobileNumberCatSp1.getText().toString();
//        String Address = address1.getText().toString();
//
//
//        if (firstName.equals("") || last_Name.equals("") || emailAddress.equals("") || Address.equals("")) {
//            Toast.makeText(activity, "Fill all fields", Toast.LENGTH_SHORT).show();
//
//        } else if (SubCatSp1.getSelectedItemPosition() == 0 || MainCatSp1.getSelectedItemPosition() == 0) {
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
//                            emailAddress, Integer.valueOf(s1), Cites1,
//                            Address, SubMobileNumberCatSp);
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
//                            builder1.dismiss();
////                            if (pressToExit) {
////                                (new Handler()).postDelayed((() ->
////                                        pressToExit = false), 1000L);
////                            }
////                            getUserAddress();
//
//
//                            Status = true;
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
//    public static int getIndex(Spinner spinner, String myString) {
//
//        int index = 0;
//
//        for (int i = 0; i < spinner.getCount(); i++) {
//            if (spinner.getItemAtPosition(i).equals(myString)) {
//                index = i;
//            }
//        }
//        return index;
//    }
//
//}
