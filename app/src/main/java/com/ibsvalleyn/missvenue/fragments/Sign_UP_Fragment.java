package com.ibsvalleyn.missvenue.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.iid.FirebaseInstanceId;
import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.activities.AllActivity;
import com.ibsvalleyn.missvenue.activities.MainActivity;
import com.ibsvalleyn.missvenue.activities.SigningActivity;
import com.ibsvalleyn.missvenue.api.RetrofitClient;
import com.ibsvalleyn.missvenue.helper.AppFunctions;
import com.ibsvalleyn.missvenue.helper.GPSTracker;
import com.ibsvalleyn.missvenue.helper.Static_variables;
import com.ibsvalleyn.missvenue.models.Guest_session;
import com.ibsvalleyn.missvenue.models.RegisterResponse;
import com.jaychang.sa.AuthCallback;
import com.jaychang.sa.SocialUser;
import com.jaychang.sa.facebook.SimpleAuth;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.facebook.GraphRequest.TAG;

public class Sign_UP_Fragment extends Fragment {
    Button register;
    EditText firstName, lastName, password, confirmationPassword, email, phone;
    private KProgressHUD kProgressHUD;
    private SigningActivity activity;
    String first_name, last_name, pass_word, user_phone, password_con, user_mail;
    SharedPreferences dataSaver;
    private String Gmail;
    private String getEmail;
    private String getDisplayName;
    private String getPhoneNumber;
    private String emailSocial;
    private String fullName;
    private int itemId;
    private int id;

    private String DeviceId;
    private final int REQUEST_CODE_ASK_PERMISSIONS = 555;
    private GPSTracker gpsTracker;
    private String city;
    private String country;
    LinearLayout terms_us;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (SigningActivity) getActivity();
        gpsTracker = new GPSTracker(activity);


        assert activity != null;
        @SuppressLint
                ("HardwareIds") final String android_id = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.i("DeviceId", android_id);
        DeviceId = android_id;

//
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
//                    PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(new String[]{
//                                android.Manifest.permission.ACCESS_FINE_LOCATION},
//                        REQUEST_CODE_ASK_PERMISSIONS);
//                return;
//            }
//
//        }


//        if (gpsTracker.canGetLocation()) {
//            double latitude = gpsTracker.getLatitude();
//            double longitude = gpsTracker.getLongitude();
//            sendNotificationData(latitude,longitude);
//
//
//            Log.i(TAG, "onCreate: "+ latitude+longitude);
//        }else {
//            gpsTracker.showSettingsAlert();
//        }


        getGentSession();

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_fragment, container, false);
        Bundle bundle = getArguments();

        assert bundle != null;
        kProgressHUD = AppFunctions.getKProgressHUD(activity);

        if (getArguments().getInt("itemId") != 0) {
            itemId = getArguments().getInt("itemId");
            Log.i("ITEM_ID", " " + itemId);

            switch (itemId) {
                case 1:
                    connectFacebook();
                    break;
                case 2:
                    connectGoogle();
                    break;
                case 3:
                    break;
            }
        }
        dataSaver = getDefaultSharedPreferences(activity);
        register = view.findViewById(R.id.register);
        firstName = view.findViewById(R.id.first_name);
        lastName = view.findViewById(R.id.last_name);
        password = view.findViewById(R.id.password);
        confirmationPassword = view.findViewById(R.id.confirm_password);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        terms_us = view.findViewById(R.id.terms_us);
        terms_us.setOnClickListener(view1 -> {
            Intent intent = new Intent(activity, AllActivity.class);
            intent.putExtra("statics", "TERMS_OF_USE");
            startActivity(intent);
        });

        register.setOnClickListener(view2 -> userRegister());
        Calligrapher calligrapher = new Calligrapher(activity);
        calligrapher.setFont(activity, "Ubuntu-R.ttf", true);

        return view;
    }

    private void sendNotificationData(double latitude, double longitude) {


        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(activity, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

            sendData();

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("adddd", e.toString());


        }
    }


    private void userRegister() {
        id = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);

        first_name = firstName.getText().toString();
        pass_word = password.getText().toString();
        user_mail = email.getText().toString();
        password_con = confirmationPassword.getText().toString();
        last_name = lastName.getText().toString();
        user_phone = phone.getText().toString();

        if (first_name.equals("") || last_name.equals("") || user_mail.equals("") || user_phone.equals("") || pass_word.equals("") || password_con.equals("")) {
            Toast.makeText(activity, activity.getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show();

        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            Toast.makeText(activity, activity.getString(R.string.please_enter_valid_email), Toast.LENGTH_SHORT).show();

        } else if (!pass_word.equals(password_con)) {
            Toast.makeText(activity, activity.getString(R.string.password_and_confirmation), Toast.LENGTH_SHORT).show();

        } else {
            kProgressHUD.show();
            Call<RegisterResponse> addEVEnt_call = RetrofitClient.getInstance(activity)
                    .userRegister(id
                            , first_name,
                            last_name, user_mail, user_phone, pass_word);
            addEVEnt_call.enqueue(new Callback<RegisterResponse>() {

                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                    if (response.isSuccessful()) {
                        kProgressHUD.dismiss();

                        assert response.body() != null;
                        if (response.body().getRegisterResult().isResult()) {
                            Log.e("TAG", "isSuccessful");

                            Toast.makeText(activity, response.body().getRegisterResult().getResult_message(), Toast.LENGTH_SHORT).show();
                            Log.e("TAG", "Customer_id " + response.body().getRegisterResult().getCustomer_id());

                            dataSaver.edit()
                                    .putString(Static_variables.ACCESS_TOKEN, response.body().getRegisterResult().getAccess_token())
                                    .apply();

                            dataSaver.edit()
                                    .putInt(Static_variables.CUSTOMER_ID, response.body().getCustomerInfo().getCustomer_Id())
                                    .apply();

                            dataSaver.edit()
                                    .putString(Static_variables.Email, response.body().getCustomerInfo().getEmail()).apply();

                            dataSaver.edit()
                                    .putString(Static_variables.Phone, response.body().getCustomerInfo().getPhone()).apply();

                            dataSaver.edit()
                                    .putString(Static_variables.Fname, response.body().getCustomerInfo().getfName()).apply();

                            dataSaver.edit()
                                    .putString(Static_variables.Lname, response.body().getCustomerInfo().getlName()).apply();
                            dataSaver.edit()
                                    .putString(Static_variables.DEVICE_ID, "").apply();


//                            if (gpsTracker.canGetLocation()) {
//                                double latitude = gpsTracker.getLatitude();
//                                double longitude = gpsTracker.getLongitude();
//                                sendNotificationData(latitude, longitude);
//
//
//                                Log.i(TAG, "onCreate: " + latitude + longitude);
//                            } else {
//                                gpsTracker.showSettingsAlert();
//                            }
                            Intent intent = new Intent(activity, MainActivity.class);
                            startActivity(intent);
                            activity.finishAffinity();
                        } else {
                            Toast.makeText(activity, response.body().getRegisterResult().getResult_message(), Toast.LENGTH_SHORT).show();

                        }

                    } else {
                        kProgressHUD.dismiss();
                        Log.e("TAG", "notSuccessful");
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    Log.e("TAG ", "onFailure " + t.getMessage());
                    kProgressHUD.dismiss();
                    Toast.makeText(activity, "Check internet connection!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void getGentSession() {
        Call<Guest_session> addEVEnt_call = RetrofitClient.getInstance(activity)
                .GUEST_SESSION_CALL(DeviceId);
        addEVEnt_call.enqueue(new Callback<Guest_session>() {

            @Override
            public void onResponse(@NotNull Call<Guest_session> call, @NotNull Response<Guest_session> response) {

                if (response.isSuccessful()) {
                    Log.e("TAG", "isSuccessful");
                    assert response.body() != null;
                    if (response.body().getResult()) {

                        Log.i(TAG, "onResponse: " + response.body().getNewId());
                        int customer_id = response.body().getNewId();
                        dataSaver.edit()
                                .putInt(Static_variables.CUSTOMER_ID, customer_id).apply();
                        dataSaver.edit()
                                .putString(Static_variables.DEVICE_ID, DeviceId).apply();
//                        Toast.makeText(activity, response.body().getNewId()+"", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(activity, MainActivity.class));
//                        activity.finish();
                    }

                } else {

                }
            }

            @Override
            public void onFailure(@NotNull Call<Guest_session> call, Throwable t) {
                Log.e("TAG ", "onFailure");
                Toast.makeText(activity, "Check internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendData() {
        String fcmToken = FirebaseInstanceId.getInstance().getToken();

        Call<Guest_session> addEVEnt_call = RetrofitClient.getInstance(activity)
                .sendAddressFcm(id, "android", fcmToken, country + "_" + city);
        addEVEnt_call.enqueue(new Callback<Guest_session>() {

            @Override
            public void onResponse(@NotNull Call<Guest_session> call, @NotNull Response<Guest_session> response) {

                if (response.isSuccessful()) {
                    Log.e("TAG", "isSuccessful");
                    assert response.body() != null;
                    if (response.body().getResult()) {
                        Log.e("TAG", "true");
//                        Intent intent = new Intent(activity, MainActivity.class);
//                        startActivity(intent);
//                        activity.finishAffinity();
                    }


                } else {

                }
            }

            @Override
            public void onFailure(@NotNull Call<Guest_session> call, Throwable t) {
                Log.e("TAG ", "onFailure");
                Toast.makeText(activity, "Check internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void connectFacebook() {
        List<String> scopes = Arrays.asList("user_birthday");

        SimpleAuth.connectFacebook(scopes, new AuthCallback() {
            @Override
            public void onSuccess(SocialUser socialUser) {
                Log.d(TAG, "userId:" + socialUser.userId);
                Log.d(TAG, "email:" + socialUser.email);
                Log.d(TAG, "accessToken:" + socialUser.accessToken);
                Log.d(TAG, "profilePictureUrl:" + socialUser.profilePictureUrl);
                Log.d(TAG, "username:" + socialUser.username);
                Log.d(TAG, "fullName:" + socialUser.fullName);
                Log.d(TAG, "pageLink:" + socialUser.pageLink);
                String fullName = socialUser.fullName;
                Log.i("fsdfuidsyfiu", "onSuccess: " + fullName.substring(fullName.indexOf(" ")));
                Log.i("fsdfuidsyfiu", "onSuccess: " + fullName.substring(0, fullName.indexOf(" ")));

                activity.runOnUiThread(() -> {
                    firstName.setText(fullName.substring(0, fullName.indexOf(" ")));
                    lastName.setText(fullName.substring(fullName.indexOf(" ")));
                    email.setText(socialUser.email);
                });
            }

            @Override
            public void onError(Throwable error) {
                Log.d(TAG, error.getMessage());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "Canceled");
            }
        });
    }

    private void connectGoogle() {

        List<String> scopes = Arrays.asList("https://www.googleapis.com/auth/analytics.readonly");
        com.jaychang.sa.google.SimpleAuth.connectGoogle(scopes, new AuthCallback() {
            @Override
            public void onSuccess(SocialUser socialUser) {
                Log.d(TAG, "userId:" + socialUser.userId);
                Log.d(TAG, "email:" + socialUser.email);
                Log.d(TAG, "accessToken:" + socialUser.accessToken);
                Log.d(TAG, "profilePictureUrl:" + socialUser.profilePictureUrl);
                Log.d(TAG, "username:" + socialUser.username);
                Log.d(TAG, "fullName:" + socialUser.fullName);
                String fullName = socialUser.fullName;
                Log.i("fsdfuidsyfiu", "onSuccess: " + fullName.substring(fullName.indexOf(" ")));
                Log.i("fsdfuidsyfiu", "onSuccess: " + fullName.substring(0, fullName.indexOf(" ")));

                activity.runOnUiThread(() -> {
                    firstName.setText(fullName.substring(0, fullName.indexOf(" ")));
                    lastName.setText(fullName.substring(fullName.indexOf(" ")));
                    email.setText(socialUser.email);
                });

            }

            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onCancel() {

            }
        });

    }

}
