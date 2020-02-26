package com.ibsvalleyn.missvenue.fragments.profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.activities.MainActivity;
import com.ibsvalleyn.missvenue.activities.SigningActivity;
import com.ibsvalleyn.missvenue.adapters.AddressAdapter;
import com.ibsvalleyn.missvenue.api.RetrofitClient;
import com.ibsvalleyn.missvenue.helper.Static_variables;
import com.ibsvalleyn.missvenue.models.City_model;
import com.ibsvalleyn.missvenue.models.Country_Model;
import com.ibsvalleyn.missvenue.models.CustomerProfile;
import com.ibsvalleyn.missvenue.models.User_address_model;
import com.ibsvalleyn.missvenue.models.address_add_model;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.ibsvalleyn.missvenue.activities.MainActivity.postion;
import static com.ibsvalleyn.missvenue.helper.AppFunctions.getKProgressHUD;
import static com.ibsvalleyn.missvenue.helper.AppFunctions.setMargins;

public class AddressFragment extends Fragment {
    private RecyclerView my_address_recycler;
    private GridLayoutManager layoutManager;
    private AddressAdapter addressAdapter;
    SharedPreferences dataSaver;
    int id;
    private Button add_address;
    private KProgressHUD kProgressHUD;
    private Spinner MainCatSp, SubCatSp;

    private static String TAG = "dsajidhksajhdkjsahdkj";
    private List<String> MainCats_name = new ArrayList<>();
    private List<String> MainCats_IDs = new ArrayList<>();
    private List<String> SubCats_IDs = new ArrayList<>();
    private List<String> SubCats_name = new ArrayList<>();
    private String subCatID;
    private EditText first_name;
    private EditText last_name;
    private TextView EmailAddress;
    private EditText SubMobileNumberCatSp;
    private EditText address;
    private EditText postalCode;
    private int CUSTOMER_ID;
    private String s;
    private CustomerProfile profile = new CustomerProfile();
    private boolean pressToExit = false;
    private AlertDialog builder;
    private String Cites;
    private MainActivity activity;
    private EditText city;
    private String zip;
    private EditText zip1;
    private String device_id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
        dataSaver = getDefaultSharedPreferences(activity);
        device_id = dataSaver.getString(Static_variables.DEVICE_ID, "");
        assert device_id != null;

        if (!device_id.equals("") && postion != 0) {
            Intent intent = new Intent(activity, SigningActivity.class);
            intent.putExtra("positionProfile", 5);
            startActivity(intent);
            activity.finishAffinity();

        } else {
            postion = 1;
        }
        Log.i("onCreate:", "onCreate: ");

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.my_address_fragment, container, false);

        dataSaver.edit()
                .putInt("item", 0)
                .apply();

        id = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);
        kProgressHUD = getKProgressHUD(activity);
        add_address = view.findViewById(R.id.add_address);

        my_address_recycler = view.findViewById(R.id.my_address_recycler);
        layoutManager = new GridLayoutManager(activity, 1);
        my_address_recycler.setLayoutManager(layoutManager);

        getUserAddress();
        add_address.setOnClickListener(view1 ->
                addAddressDialog()
        );
//        Calligrapher calligrapher = new Calligrapher(activity);
//        calligrapher.setFont(activity, "ubunto_r.ttf", true);
        return view;
    }

    private void addAddressDialog() {

        builder = new AlertDialog.Builder(activity).create();
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M) {
            setMargins(builder, 75, 150, 75, 120);
        }
//        Calligrapher calligrapher = new Calligrapher(activity);
//        calligrapher.setFont(activity, "ubunto_r.ttf", true);
        final View v = LayoutInflater.from(activity).inflate(R.layout.add_adress_fragment, null);
        Button add_address = v.findViewById(R.id.add_address);
        dataSaver = getDefaultSharedPreferences(activity);
        CUSTOMER_ID = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);
        String email_address = dataSaver.getString(Static_variables.Email, "");
//        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M) {
//            setMargins(builder, 75, 150, 75, 120);
//        }
        city = v.findViewById(R.id.city);

        MainCatSp = v.findViewById(R.id.CountrySp);
        SubCatSp = v.findViewById(R.id.CitySp);
        first_name = v.findViewById(R.id.first_name);
        last_name = v.findViewById(R.id.last_name);
        EmailAddress = v.findViewById(R.id.EmailAddress);
        EmailAddress.setText(email_address);
        SubMobileNumberCatSp = v.findViewById(R.id.MobileNumber);
        zip1 = v.findViewById(R.id.zip);
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
                        SubCats_name.add(activity.getResources().getString(R.string.select_state));
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(activity, R.layout.spinner_view_stamp, SubCats_name);
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
        SubCats_name.add(activity.getResources().getString(R.string.select_state));
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(activity, R.layout.spinner_view_stamp, SubCats_name);
        SubCatSp.setAdapter(spinnerArrayAdapter);
        GetMainCat();
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        add_address.setOnClickListener(view2 -> submitData());
        builder.setView(v);
        builder.show();
    }

    public void getUserAddress() {
        Call<List<User_address_model>> addEVEnt_call = RetrofitClient.getInstance(activity)
                .User_address_model(id);
        addEVEnt_call.enqueue(new Callback<List<User_address_model>>() {

            @Override
            public void onResponse(Call<List<User_address_model>> call, Response<List<User_address_model>> response) {

                if (response.isSuccessful()) {
                    Log.e("TAG", "isSuccessful");

                    addressAdapter = new AddressAdapter(activity, response.body());
                    my_address_recycler.setAdapter(addressAdapter);

                } else {
                    Log.e("TAG", "notSuccessful");
                }
            }

            @Override
            public void onFailure(Call<List<User_address_model>> call, Throwable t) {
                Log.e("TAG ", "onFailure");
                Toast.makeText(activity, "Check internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public List<String> GetMainCat() {
        kProgressHUD.show();

        Call<List<Country_Model>> provincesCall = RetrofitClient.getInstance(activity).COUNTRY_MODEL_CALL();

        provincesCall.enqueue(new Callback<List<Country_Model>>() {
            @Override
            public void onResponse(Call<List<Country_Model>> call, Response<List<Country_Model>> response) {
                kProgressHUD.dismiss();

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().size() != 0) {
                        String cityName = response.body().get(0).getName();
                        Log.i("ProvincesName", cityName);

                        MainCats_name = new ArrayList<String>();
                        MainCats_IDs = new ArrayList<String>();
                        MainCats_name.clear();
                        MainCats_IDs.clear();
                        MainCats_IDs.add("");
                        MainCats_name.add(activity.getResources().getString(R.string.select_country));

                        for (int i = 0; i < response.body().size(); i++) {
                            MainCats_name.add(response.body().get(i).getName());
                            MainCats_IDs.add(String.valueOf(response.body().get(i).getId()));
                        }

                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(activity, R.layout.spinner_view_stamp, MainCats_name); //selected item will look like a spinner set from XML
                        MainCatSp.setAdapter(spinnerArrayAdapter);
                        Log.i("Governments", MainCatSp.toString());

                    } else {
                        MainCats_name.clear();
                        MainCats_name.add(activity.getResources().getString(R.string.no_country));
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(activity, R.layout.spinner_view_stamp, MainCats_name);
                        MainCatSp.setAdapter(spinnerArrayAdapter);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Country_Model>> call, Throwable t) {
                Toast.makeText(activity, R.string.error, Toast.LENGTH_SHORT).show();
                kProgressHUD.dismiss();
            }
        });


        return MainCats_name;
    }

    public List<String> GetSubCate(String id) {
        Log.i(TAG, "GetSubCate: " + ".............." + id + ".............." + Integer.valueOf(id));

        Call<List<City_model>> areasCall = RetrofitClient.getInstance(activity).LIST_CALLCity_model(Integer.valueOf(id));

        areasCall.enqueue(new Callback<List<City_model>>() {

            @Override
            public void onResponse(Call<List<City_model>> call, Response<List<City_model>> response) {
                kProgressHUD.dismiss();

                SubCats_IDs = new ArrayList<>();
                SubCats_name = new ArrayList<>();
                SubCats_name.clear();
                SubCats_IDs.clear();
                SubCats_name.add(activity.getResources().getString(R.string.select_state));
                SubCats_IDs.add("");
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().size() != 0) {

                        for (int i = 0; i < response.body().size(); i++) {

                            SubCats_name.add(response.body().get(i).getName());
                            SubCats_IDs.add(String.valueOf(response.body().get(i).getId()));
                        }

                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(activity, R.layout.spinner_view_stamp, SubCats_name); //selected item will look like a spinner set from XML
                        SubCatSp.setAdapter(spinnerArrayAdapter);
                        Log.i("City", SubCats_name.toString());
                        kProgressHUD.dismiss();

                    } else {
                        SubCats_name.clear();
                        SubCats_name.add(activity.getResources().getString(R.string.no_state));
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(activity, R.layout.spinner_view_stamp, SubCats_name);
                        SubCatSp.setAdapter(spinnerArrayAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<City_model>> call, Throwable t) {
                Toast.makeText(activity, R.string.error, Toast.LENGTH_SHORT).show();
            }
        });

        return SubCats_name;
    }


    public void submitData() {

        String firstName = first_name.getText().toString();
        String last_Name = last_name.getText().toString();
        String emailAddress = EmailAddress.getText().toString();
        String subMobileNumberCatSp = SubMobileNumberCatSp.getText().toString();
        String Address = address.getText().toString();
        String cityName = city.getText().toString();
        zip = zip1.getText().toString();


        if (zip.equals("") || firstName.equals("") || cityName.equals("") || last_Name.equals("") || emailAddress.equals("") || Address.equals("")) {
            Toast.makeText(activity, activity.getResources().getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show();

        } else if (!Patterns.EMAIL_ADDRESS.matcher(EmailAddress.getText().toString()).matches()) {
            Toast.makeText(activity, activity.getResources().getString(R.string.please_enter_valid_email), Toast.LENGTH_SHORT).show();

        } else if (SubCatSp.getSelectedItemPosition() == 0 || MainCatSp.getSelectedItemPosition() == 0) {
            Toast.makeText(activity, activity.getResources().getString(R.string.you_must_select_the_country_state), Toast.LENGTH_SHORT).show();

        } else {

            kProgressHUD.show();


            Call<address_add_model> addEVEnt_call = RetrofitClient.getInstance(activity)


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
                            pressToExit = true;
                            if (pressToExit) {
                                getUserAddress();
                                (new Handler()).postDelayed((() ->
                                        pressToExit = false), 1000L);
                            }
                            Toast.makeText(getActivity(), response.body().getUserMessage(), Toast.LENGTH_SHORT).show();

                        } else {
                            kProgressHUD.dismiss();
                            Toast.makeText(getActivity(), response.body().getUserMessage(), Toast.LENGTH_SHORT).show();

                            Log.e("TAG", "notSuccessful" + new Gson().toJson(response.body()));
                        }
                    }
                }

                @Override
                public void onFailure(Call<address_add_model> call, Throwable t) {
                    Log.e("TAG ", "onFailure " + t.getMessage());
                    kProgressHUD.dismiss();
                    Toast.makeText(getActivity(), "Check internet connection!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        postion = 0;
    }
}