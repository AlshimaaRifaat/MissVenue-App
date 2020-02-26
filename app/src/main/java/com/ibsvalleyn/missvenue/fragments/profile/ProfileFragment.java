package com.ibsvalleyn.missvenue.fragments.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.activities.MainActivity;
import com.ibsvalleyn.missvenue.activities.SigningActivity;
import com.ibsvalleyn.missvenue.api.RetrofitClient;
import com.ibsvalleyn.missvenue.helper.Static_variables;
import com.ibsvalleyn.missvenue.models.Add_to_cart;
import com.ibsvalleyn.missvenue.models.CustomerProfile;
import com.ibsvalleyn.missvenue.models.customer_update_model;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.jetbrains.annotations.NotNull;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.ibsvalleyn.missvenue.activities.MainActivity.postion;
import static com.ibsvalleyn.missvenue.helper.AppFunctions.getKProgressHUD;
import static com.ibsvalleyn.missvenue.helper.Static_variables.CUSTOMER_ID;

public class ProfileFragment extends Fragment {
    LinearLayout changePassword, logout;
    SharedPreferences dataSaver;
    int id;
    EditText firstName, lastName, phone;
    private TextView email;
    private Button updateProfile;
    private KProgressHUD kProgressHUD;
    String email_, phone_, fname_, lname_;
    MainActivity activity;
    int x;
    private String device_id;
    private LinearLayout profileInput;
    ImageView e, ed, edi;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("onCreate:", "onCreate: ");

        activity = (MainActivity) getActivity();
        dataSaver = getDefaultSharedPreferences(activity);
        device_id = dataSaver.getString(Static_variables.DEVICE_ID, "");
//        Postion = dataSaver.getInt("positionProfile", 0);
        assert device_id != null;
        if (!device_id.equals("") && postion != 0) {
            Intent intent = new Intent(activity, SigningActivity.class);
            intent.putExtra("positionProfile", 5);

            startActivity(intent);
            activity.finishAffinity();
        } else {
            postion = 1;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.profile_fragment_layout, container, false);

        email_ = dataSaver.getString(Static_variables.Email, "");
        phone_ = dataSaver.getString(Static_variables.Phone, "");
        fname_ = dataSaver.getString(Static_variables.Fname, "");
        lname_ = dataSaver.getString(Static_variables.Lname, "");

        kProgressHUD = getKProgressHUD(activity);
        id = dataSaver.getInt(CUSTOMER_ID, 0);
        firstName = view.findViewById(R.id.first_name);
        profileInput = view.findViewById(R.id.profileInput);
        lastName = view.findViewById(R.id.last_name);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.mobile);
        e = view.findViewById(R.id.e);
        ed = view.findViewById(R.id.ed);
        edi = view.findViewById(R.id.edi);
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // firstName.setSelectAllOnFocus(true);
                firstName.requestFocus();
                firstName.setSelectAllOnFocus(true);
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            }
        });
        ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastName.requestFocus();
                lastName.setSelectAllOnFocus(true);
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            }
        });
        edi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone.requestFocus();
                phone.setSelectAllOnFocus(true);
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            }
        });
        email.setText(email_);
        phone.setText(phone_);
        firstName.setText(fname_);
        lastName.setText(lname_);

        logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(view12 -> {
            com.jaychang.sa.google.SimpleAuth.disconnectGoogle();
            com.jaychang.sa.google.SimpleAuth.revokeGoogle();

            com.jaychang.sa.facebook.SimpleAuth.disconnectFacebook();
            com.jaychang.sa.facebook.SimpleAuth.revokeFacebook(null);

            dataSaver.edit().clear().apply();
            activity.finishAffinity();
            Intent intent = new Intent(activity, SigningActivity.class);
            startActivity(intent);

        });
        changePassword = view.findViewById(R.id.change_password_linear);
        changePassword.setOnClickListener(view1 -> changePassword());

        updateProfile = view.findViewById(R.id.updateProfile);

        profileInput.setVisibility(View.VISIBLE);

        updateProfile.setOnClickListener(view1 ->
                UpdateProfile()
        );

        Calligrapher calligrapher = new Calligrapher(activity);
        calligrapher.setFont(activity, "Ubuntu-R.ttf", true);
        return view;
    }

    public void disconnectFromFacebook() {
        if (AccessToken.getCurrentAccessToken() == null) {
            Log.e("TAG", "already logged out" + "disconnectFromFacebook");

            return; // already logged out
        }
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, graphResponse -> {
            //  mAuth = FirebaseAuth.getInstance();
            //  mAuth.signOut();
            LoginManager.getInstance().logOut();
        }).executeAsync();
    }

    private void UpdateProfile() {
        String firstN = firstName.getText().toString();
        String LastN = lastName.getText().toString();
        String emaill = email.getText().toString();
        String phonee = phone.getText().toString();

        if (firstN.equals("") || emaill.equals("") || phonee.equals("") || LastN.equals("")) {
            Toast.makeText(activity, activity.getResources().getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show();

        } else {

            kProgressHUD.show();
            Call<customer_update_model> addEVEnt_call = RetrofitClient.getInstance(activity)
                    .CUSTOMER_UPDATE_MODEL_CALL(id, phonee, emaill, firstN, LastN);
            addEVEnt_call.enqueue(new Callback<customer_update_model>() {

                @Override
                public void onResponse(@NotNull Call<customer_update_model> call, Response<customer_update_model> response) {

                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        if (response.body().getResult()) {
                            getUserProfile();
                            Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        } else {
                            kProgressHUD.dismiss();
                            Log.e("TAG", "notSuccessful" + new Gson().toJson(response.body()));
                        }
                    } else {
                        kProgressHUD.dismiss();
                        Toast.makeText(activity, "حاول مره اخرى", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<customer_update_model> call, Throwable t) {
                    Log.e("TAG ", "onFailure " + t.getMessage());
                    kProgressHUD.dismiss();
                    Toast.makeText(activity, "Check internet connection!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void getUserProfile() {
        Call<CustomerProfile> addEVEnt_call = RetrofitClient.getInstance(activity)
                .userProfile(id);
        addEVEnt_call.enqueue(new Callback<CustomerProfile>() {

            @Override
            public void onResponse(Call<CustomerProfile> call, Response<CustomerProfile> response) {

                if (response.isSuccessful()) {
                    Log.e("TAG", "isSuccessful");
                    email.setText(response.body().getEmail());
                    phone.setText(response.body().getMobile());
                    firstName.setText(response.body().getFirstname());
                    lastName.setText(response.body().getLastname());
                    dataSaver.edit()
                            .putString(Static_variables.Email, response.body().getEmail()).apply();

                    dataSaver.edit()
                            .putString(Static_variables.Phone, response.body().getMobile()).apply();

                    dataSaver.edit()
                            .putString(Static_variables.Fname, response.body().getFirstname()).apply();

                    dataSaver.edit()
                            .putString(Static_variables.Lname, response.body().getLastname()).apply();

                    kProgressHUD.dismiss();

                } else {
                    Log.e("TAG", "notSuccessful");
                }
            }

            @Override
            public void onFailure(Call<CustomerProfile> call, Throwable t) {
                Log.e("TAG ", "onFailure");
                Toast.makeText(getContext(), "Check internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void changePassword() {
        final AlertDialog builder = new AlertDialog.Builder(activity).create();


        final View v = LayoutInflater.from(activity).inflate(R.layout.change_password_dialog, null);

        Button changePasswordBtn = v.findViewById(R.id.change_password_btn);
        EditText old_password = v.findViewById(R.id.old_password);
        EditText confirm_password = v.findViewById(R.id.confirm_password);
        EditText new_password = v.findViewById(R.id.new_password);

        changePasswordBtn.setOnClickListener(view -> {
            String oldPass = old_password.getText().toString();
            String confirmPass = confirm_password.getText().toString();
            String newPass = new_password.getText().toString();
            if (old_password.equals("") || confirmPass.equals("") || newPass.equals("")) {
                Toast.makeText(activity, "Fill all fields", Toast.LENGTH_SHORT).show();

            } else if (!newPass.equals(confirmPass)) {

                Toast.makeText(activity, "Password and Confirmation password doesn't match", Toast.LENGTH_SHORT).show();

            } else {
                Call<Add_to_cart> addEVEnt_call = RetrofitClient.getInstance(activity)
                        .changePassword(id, oldPass, newPass);
                addEVEnt_call.enqueue(new Callback<Add_to_cart>() {

                    @Override
                    public void onResponse(Call<Add_to_cart> call, Response<Add_to_cart> response) {

                        if (response.isSuccessful()) {
                            Log.e("TAG", "isSuccessful");
                            Toast.makeText(activity, response.body().getUser_message(), Toast.LENGTH_SHORT).show();
                            builder.dismiss();
                        } else {
                            Log.e("TAG", "notSuccessful");
                            Toast.makeText(activity, response.body().getUser_message(), Toast.LENGTH_SHORT).show();
                            builder.dismiss();

                        }
                    }

                    @Override
                    public void onFailure(Call<Add_to_cart> call, Throwable t) {
                        Log.e("TAG ", "onFailure");
                        builder.dismiss();
                        Toast.makeText(activity, "Check internet connection!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        builder.setView(v);
        // dialog.setCancelable(false);
        builder.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        postion = 0;
    }
}