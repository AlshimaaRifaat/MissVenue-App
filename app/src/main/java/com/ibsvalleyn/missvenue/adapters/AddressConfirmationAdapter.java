package com.ibsvalleyn.missvenue.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.util.Patterns;
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
import com.ibsvalleyn.missvenue.activities.CheckOutActivity;
import com.ibsvalleyn.missvenue.api.RetrofitClient;
import com.ibsvalleyn.missvenue.helper.Static_variables;
import com.ibsvalleyn.missvenue.models.City_model;
import com.ibsvalleyn.missvenue.models.Country_Model;
import com.ibsvalleyn.missvenue.models.User_address_model;
import com.ibsvalleyn.missvenue.models.address_add_model;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.ibsvalleyn.missvenue.helper.AppFunctions.setMargins;

public class AddressConfirmationAdapter extends RadioConFixationAdapter<User_address_model> {
    List<User_address_model> profile;

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
    private boolean pressToExit = false;
    private AlertDialog builder;
    private String Cites;
    private CheckOutActivity context;
    private String country = "??";
    String addressIdSapping;
    private EditText city;
    private int addressId;
    private EditText zip1;
    private String zip;

    public String getAddressIdSapping() {
        return addressIdSapping;
    }

    public AddressConfirmationAdapter(CheckOutActivity context, List<User_address_model> profile) {
        super(context, profile);

        this.context = context;

        this.profile = profile;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NotNull RadioConFixationAdapter.ViewHolder viewHolder, int position) {
        super.onBindViewHolder(viewHolder, position);
        viewHolder.fullName.setText(profile.get(position).getFirstname() + " " + profile.get(position).getLastname());
        viewHolder.address.setText(profile.get(position).getAddress1() + "\n" + profile.get(position).getCountry() + "," + profile.get(position).getCity());
        viewHolder.editAddress.setOnClickListener(view -> addAddressDialog(position, viewHolder));
        // viewHolder.editAddress.setOnClickListener(view -> addAddressDialog(position, viewHolder));
//        viewHolder.mRadio.setOnClickListener(view -> {
//        });


        viewHolder.mRadio.setOnCheckedChangeListener((check, isChecked) -> {
            if (check.isChecked()) {
///                context.loadFragment(new ConfirmationCheckoutFragment(), shppingId, billingId, mItems.get(i).getTitle());

                dataSaver = getDefaultSharedPreferences(context);

                //     Toast.makeText(context, String.valueOf(profile.get(position).getAddressId()), Toast.LENGTH_SHORT).show();
                dataSaver.edit()
                        .putString("addressIdSapping", String.valueOf(profile.get(position).getAddressId()))
                        .apply();


//                id = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);

            }
        });

    }


    //}
//    @NonNull
//    @Override
//    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.address_layout_confrimation, viewGroup, false);
//        return new AddressViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
//        kProgressHUD = getKProgressHUD(context);
//
//
//
//
//        if (profile.get(position).getPhonenumber() != null) {
//            holder.phone.setText(profile.get(position).getPhonenumber());
//        }
//
//
//    }


//
//    @Override
//    public void onBindViewHolder(RadioAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
//        super.onBindViewHolder(viewHolder, i);
//        viewHolder.mText.setText(mItems.get(i).getTitle());
////        viewHolder.mRadio.setBackgroundColor(BLACK);
////        viewHolder.radioGroup1.setOnClickListener(view ->
////
////
//////                context.loadFragment(new ConfirmationCheckoutFragment(), shppingId, billingId, mItems.get(i).getTitle())
////
////
////        );
////        viewHolder.mRadio.setOnCheckedChangeListener((check, isChecked) -> {
////            if (check.isChecked()) {
////                context.loadFragment(new ConfirmationCheckoutFragment(), shppingId, billingId, mItems.get(i).getTitle());
////
////            }
////        });
//
//    }
//

    @Override
    public int getItemCount() {
        return profile.size();
    }


    public void addAddressDialog(int position, ViewHolder holder) {

        builder = new AlertDialog.Builder(context).create();

        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M) {
            setMargins(builder, 75, 150, 75, 120);
        }
        final View v = LayoutInflater.from(context).inflate(R.layout.add_adress_fragment, null);

        Button add_address = v.findViewById(R.id.add_address);
        city = v.findViewById(R.id.city);
        add_address.setText(context.getResources().getString(R.string.update));
        dataSaver = getDefaultSharedPreferences(context);
        CUSTOMER_ID = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);
        Log.i(TAG, "addAddressDialog: " + CUSTOMER_ID);

        MainCatSp = v.findViewById(R.id.CountrySp);
        SubCatSp = v.findViewById(R.id.CitySp);
        first_name = v.findViewById(R.id.first_name);
        last_name = v.findViewById(R.id.last_name);
        EmailAddress = v.findViewById(R.id.EmailAddress);
        SubMobileNumberCatSp = v.findViewById(R.id.MobileNumber);
        address = v.findViewById(R.id.address);
        zip1 = v.findViewById(R.id.zip);
        first_name.setText(profile.get(position).getFirstname());
        last_name.setText(profile.get(position).getLastname());
        EmailAddress.setText(profile.get(position).getEmail());
        SubMobileNumberCatSp.setText(profile.get(position).getPhonenumber());
        address.setText(profile.get(position).getAddress1());
        city.setText(profile.get(position).getCity());
        zip1.setText(profile.get(position).getZippostalcode());
        addressId = profile.get(position).getAddressId();


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
                        SubCats_name.add(context.getResources().getString(R.string.select_state));


                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(context, R.layout.spinner_view_stamp, SubCats_name);

                        SubCatSp.setAdapter(spinnerArrayAdapter);

                    } else if (i != 0) {
                        SubCatSp.setEnabled(true);


                        Log.i(TAG, "onItemSelected: " + s);
                        GetSubCate(s, position);
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
                    Cites = SubCats_name.get(i);
//                    Servicesub_id = subCatID;
                    Log.i("AreaID2", Cites);


//
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        SubCats_IDs.add("");
        SubCats_name.add(context.getResources().getString(R.string.select_state));


        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(context, R.layout.spinner_view_stamp, SubCats_name);

        SubCatSp.setAdapter(spinnerArrayAdapter);
        GetMainCat(position);
        add_address.setOnClickListener(view2 -> submitData(position, holder));
        builder.setView(v);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // dialog.setCancelable(false);
        builder.show();


    }


    public void GetMainCat(int position) {
//        

        Call<List<Country_Model>> provincesCall = RetrofitClient.getInstance(context).COUNTRY_MODEL_CALL();

        provincesCall.enqueue(new Callback<List<Country_Model>>() {
            @Override
            public void onResponse(Call<List<Country_Model>> call, Response<List<Country_Model>> response) {
//                

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
                        MainCats_name.add(context.getResources().getString(R.string.select_country));
                        for (int i = 0; i < response.body().size(); i++) {
                            MainCats_name.add(response.body().get(i).getName());
                            MainCats_IDs.add(String.valueOf(response.body().get(i).getId()));
                        }

                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner_view_stamp, MainCats_name); //selected item will look like a spinner set from XML
                        MainCatSp.setAdapter(spinnerArrayAdapter);
                        Log.i("Governments", MainCatSp.toString());

                        if (!country.equals("??")) {
                            MainCatSp.setSelection(getIndex(MainCatSp, country));

                        } else {
                            MainCatSp.setSelection(getIndex(MainCatSp, profile.get(position).getCountry()));
                        }
                    } else {
                        MainCats_name.clear();
                        MainCats_name.add(context.getResources().getString(R.string.no_state));
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner_view_stamp, MainCats_name);
                        MainCatSp.setAdapter(spinnerArrayAdapter);
                    }

                } else {
//                    Toast.makeText(activity, R.string.server_error, Toast.LENGTH_SHORT).show();
//                    progressDialog.dismiss();

                }
//
            }

            @Override
            public void onFailure(Call<List<Country_Model>> call, Throwable t) {
                Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
//                


            }
        });


    }

    // Get Cities

    public void GetSubCate(String id, int position) {
        Log.i(TAG, "GetSubCate: " + ".............." + id + ".............." + Integer.valueOf(id));


        Call<List<City_model>> areasCall = RetrofitClient.getInstance(context).LIST_CALLCity_model(Integer.valueOf(id));

        areasCall.enqueue(new Callback<List<City_model>>() {

            @Override
            public void onResponse(Call<List<City_model>> call, Response<List<City_model>> response) {


                SubCats_IDs = new ArrayList<>();
                SubCats_name = new ArrayList<>();
                SubCats_name.clear();
                SubCats_IDs.clear();
                SubCats_name.add(context.getResources().getString(R.string.select_state));
                SubCats_IDs.add("");
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().size() != 0) {

                        for (int i = 0; i < response.body().size(); i++) {

                            SubCats_name.add(response.body().get(i).getName());
                            SubCats_IDs.add(String.valueOf(response.body().get(i).getId()));
                        }

                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(context, R.layout.spinner_view_stamp, SubCats_name); //selected item will look like a spinner set from XML
                        SubCatSp.setAdapter(spinnerArrayAdapter);
                        Log.i("City", SubCats_name.toString());

                        SubCatSp.setSelection(getIndex(SubCatSp, profile.get(position).getState()));

                    } else {
                        SubCats_name.clear();
                        SubCats_name.add(context.getResources().getString(R.string.no_state));
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(context, R.layout.spinner_view_stamp, SubCats_name);

                        SubCatSp.setAdapter(spinnerArrayAdapter);
                    }
                } else {
//                    Toast.makeText(RegisterActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<City_model>> call, Throwable t) {
                Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void submitData(int position, ViewHolder holder) {

        String firstName = first_name.getText().toString();
        String last_Name = last_name.getText().toString();
        String cityName = city.getText().toString();
        String emailAddress = EmailAddress.getText().toString();
        String subMobileNumberCatSp = SubMobileNumberCatSp.getText().toString();
        String Address = address.getText().toString();
        zip = zip1.getText().toString();

        if (firstName.equals("") || zip.equals("") || last_Name.equals("") || emailAddress.equals("") || Address.equals("")) {
            Toast.makeText(context, context.getResources().getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show();

        } else if (!Patterns.EMAIL_ADDRESS.matcher(EmailAddress.getText().toString()).matches()) {
            Toast.makeText(context, context.getResources().getString(R.string.please_enter_valid_email), Toast.LENGTH_SHORT).show();

        } else if (SubCatSp.getSelectedItemPosition() == 0 || MainCatSp.getSelectedItemPosition() == 0) {
            Toast.makeText(context, context.getResources().getString(R.string.you_must_select_the_country_state), Toast.LENGTH_SHORT).show();

        } else {

            Call<address_add_model> addEVEnt_call = RetrofitClient.getInstance(context)


                    .Update_ADD_MODEL_CALL(CUSTOMER_ID, addressId, firstName, last_Name,
                            emailAddress, Integer.valueOf(s), cityName, Integer.valueOf(subCatID),
                            Address, zip, subMobileNumberCatSp);
            addEVEnt_call.enqueue(new Callback<address_add_model>() {

                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<address_add_model> call, Response<address_add_model> response) {


                    if (response.isSuccessful()) {

                        assert response.body() != null;
                        if (response.body().getResult()) {
                            builder.dismiss();

                            context.getUserAddressUSe();
//
//                            profile.get(position).setFirstname(firstName);
//                            profile.get(position).setLastname(last_Name);
//                            profile.get(position).setAddress1(Address);
//                            profile.get(position).setCountry(s);
//                            profile.get(position).setCity(Cites);
//                            profile.get(position).setPhonenumber(subMobileNumberCatSp);
//                            holder.fullName.setText(profile.get(position).getFirstname() + " " + profile.get(position).getLastname());
//                            context.loadFragment(new Navigation_Profile_Fragment());
//                            dataSaver.edit()
//                                    .putInt("item", 2)
//                                    .apply();

                            holder.address.setText(profile.get(position).getAddress1() +
                                    "\n" + profile.get(position).getCountry() + "," + profile.get(position).getCity());

                            holder.phone.setText(profile.get(position).getPhonenumber());

                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            country = s;
//                            MainCatSp.setSelection(getIndex(MainCatSp, s));

                        } else {

                            Log.e("TAG", "notSuccessful" + new Gson().toJson(response.body()));
                        }
                    }
                }

                @Override
                public void onFailure(Call<address_add_model> call, Throwable t) {
                    Log.e("TAG ", "onFailure " + t.getMessage());

                    Toast.makeText(context, "Check internet connection!", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public static int getIndex(Spinner spinner, String myString) {

        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(myString)) {
                index = i;
            }
        }
        return index;
    }

}