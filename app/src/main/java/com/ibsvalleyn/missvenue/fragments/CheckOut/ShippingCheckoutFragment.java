//package com.ibsvalleyn.missvenue.fragments.CheckOut;
//
//import android.app.AlertDialog;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
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
//import static com.ibsvalleyn.missvenue.helper.AppFunctions.setMargins;
//
//public class ShippingCheckoutFragment extends Fragment {
//    RecyclerView myOrdersRecyclerView;
//    GridLayoutManager layoutManager;
//    MyOrdersAdapter myOrdersAdapter;
//    SharedPreferences dataSaver;
//    int id;
//    private KProgressHUD kProgressHUD;
//    private Spinner CitySp;
//    private String Address;
//    List<String> Governments = new ArrayList<>();
//    List<String> Govern_IDs = new ArrayList<>();
//    private String City_id = "??";
//    private TextView add_address;
//    private AlertDialog builder;
//    private int CUSTOMER_ID;
//    private static final String TAG = "kldjasdlkjsald";
//
//    private List<String> MainCats_name = new ArrayList<>();
//    private List<String> MainCats_IDs = new ArrayList<>();
//    private List<String> SubCats_IDs = new ArrayList<>();
//    private List<String> SubCats_name = new ArrayList<>();
//    private String subCatID;
//    private EditText first_name;
//    private EditText last_name;
//    private EditText EmailAddress;
//    private EditText SubMobileNumberCatSp;
//    private EditText address;
//    private EditText postalCode;
//    private String s;
//    private Spinner MainCatSp, SubCatSp;
//    private String Cites;
//    private Button sendTo;
//    CheckOutActivity activity;
//    private Spinner getAllAddress;
//    private String provName;
//    private boolean Status = false;
//    /////////////////////////////////////////////////////
//    RecyclerView myOrdersRecyclerView1;
//    GridLayoutManager layoutManager1;
//    MyOrdersAdapter myOrdersAdapter1;
//
//
//    List<String> Governments1 = new ArrayList<>();
//    List<String> Govern_IDs1 = new ArrayList<>();
//    private String City_id1;
//    private TextView add_address1;
//    private AlertDialog builder1;
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
//    private String s1;
//    private Spinner MainCatSp1, SubCatSp1;
//    private String Cites1;
//    private Button add_to_cart;
//    private Spinner getAllAddress1;
//    private String itemId1;
//    private String provName1;
//    private TextView addressName;
//    private boolean pressToExit = false;
//    private String ShppingId;
//    private String BillingId;
//    private int Type;
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
//        View view = inflater.inflate(R.layout.checkout_shipping_fragment, container, false);
//        dataSaver = getDefaultSharedPreferences(activity);
//        id = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);
//
//        kProgressHUD = getKProgressHUD(activity);
//        add_address1 = view.findViewById(R.id.add_address1);
//        getAllAddress1 = view.findViewById(R.id.getAllAddress1);
//
//        add_address = view.findViewById(R.id.add_address);
//        view.findViewById(R.id.back).setOnClickListener(view1 -> activity.finish());
//        sendTo = view.findViewById(R.id.sendTo);
//        getAllAddress = view.findViewById(R.id.getAllAddress);
//
//
//        add_address1.setOnClickListener(view1 ->
//                addAddressDialog(1)
//        );
//        add_address.setOnClickListener(view1 ->
//                addAddressDialog(0)
//        );
//        getUserAddress();
//        getUserAddress1();
//
//        getAllAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                provName = getAllAddress.getSelectedItem().toString();
//                if (!Govern_IDs.isEmpty()) {
//
//
//                    String s = Govern_IDs.get(i);
//                    getAllAddress1.setSelection(getIndex(getAllAddress1, provName));
//
//                    if (i != 0) {
//                        Log.i("asdaskldj", "onItemSelected: " + s);
//                        ShppingId = s;
//                        Log.i(TAG, "onItemSelected: " + City_id);
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
//        getAllAddress1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                provName1 = getAllAddress1.getSelectedItem().toString();
//                if (!Govern_IDs1.isEmpty()) {
//
//                    String s = Govern_IDs1.get(i);
//
//                    if (i != 0) {
//                        Log.i("asdaskldj", "onItemSelected: " + s);
//                        BillingId = s;
//                    }
//                } else {
//                    Log.i("No Cities", "لا يوجد مناطق");
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        sendTo.setOnClickListener(view1 -> {
//            if (getAllAddress.getSelectedItemPosition() == 0 || getAllAddress1.getSelectedItemPosition() == 0) {
//                Toast.makeText(activity, "you must select the address", Toast.LENGTH_SHORT).show();
//
//            } else {
//                activity.loadFragment(new ConfirmationCheckoutFragment(), ShppingId, BillingId);
//            }
//
//        });
//        return view;
//    }
//
////
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
////                        Log.i("ProvincesName", cityName);
//                        Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
//                        Governments = new ArrayList<>();
//                        Govern_IDs = new ArrayList<>();
//                        Govern_IDs.add("");
//                        Governments.add("select address");
//
//                        Log.i("ADSSADSADSAF", "onResponse: " + new Gson().toJson(response.body()));
//                        for (int i = 0; i < response.body().size(); i++) {
//                            Governments.add(response.body().get(i).getAddress1());
//                            Govern_IDs.add(String.valueOf(response.body().get(i).getAddressId()));
//                        }
//
//                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(activity, R.layout.spinner_view_stamp, Governments); //selected item will look like a spinner set from XML
//                        getAllAddress.setAdapter(spinnerArrayAdapter);
//                        if (Type == 0) {
//                            getAllAddress.setSelection(getIndex(getAllAddress, Address));
//                            getAllAddress1.setSelection(getIndex(getAllAddress1, Address));
//                        }
//                        Log.i("Governments", Governments.toString());
//                    } else {
//                        Governments.clear();
//                        Governments.add("no address");
//                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(activity, R.layout.spinner_view_stamp, Governments);
//                        getAllAddress.setAdapter(spinnerArrayAdapter);
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
//
//
//        return Governments;
//    }
//
//    public void addAddressDialog(int i) {
//
//        builder = new AlertDialog.Builder(activity).create();
//        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M) {
//            setMargins(builder, 75, 150, 75, 120);
//        }
//        final View v = LayoutInflater.from(activity).inflate(R.layout.add_adress_fragment, null);
//        setMargins(builder, 75, 150, 75, 120);
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
//        SubMobileNumberCatSp = v.findViewById(R.id.MobileNumber);
//        address = v.findViewById(R.id.address);
//
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
//        add_address.setOnClickListener(view2 -> submitData(i));
//        builder.setView(v);
//        // dialog.setCancelable(false);
//        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        builder.show();
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
//        return SubCats_name;
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
//        return MainCats_name;
//    }
//
//    public void submitData(int i) {
//        Log.i(TAG, "submitData: " + i);
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
//
//    public List<String> getUserAddress1() {
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
//                        if (Type == 1) {
//                            getAllAddress1.setSelection(getIndex(getAllAddress1, Address));
//                        }
//                        Log.i("Governments1", Governments1.toString());
//                    } else {
//                        Governments1.clear();
//                        Governments1.add("no address");
//                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(activity, R.layout.spinner_view_stamp, Governments1);
//                        getAllAddress1.setAdapter(spinnerArrayAdapter);
//                    }
//
//                } else {
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<User_address_model>> call, Throwable t) {
//                Toast.makeText(activity, R.string.error, Toast.LENGTH_SHORT).show();
//                kProgressHUD.dismiss();
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