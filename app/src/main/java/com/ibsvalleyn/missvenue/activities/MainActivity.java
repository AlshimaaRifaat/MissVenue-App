package com.ibsvalleyn.missvenue.activities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.api.RetrofitClient;
import com.ibsvalleyn.missvenue.data_room.AppDatabase;
import com.ibsvalleyn.missvenue.fragments.Content_Home_Fragment;
import com.ibsvalleyn.missvenue.fragments.Navigation_My_Cart_Fragment;
import com.ibsvalleyn.missvenue.fragments.Navigation_Offers_Fragment;
import com.ibsvalleyn.missvenue.fragments.Navigation_Profile_Fragment;
import com.ibsvalleyn.missvenue.fragments.Navigation_Wishlist_Fragment;
import com.ibsvalleyn.missvenue.helper.Static_variables;
import com.ibsvalleyn.missvenue.models.FavoriteModel;
import com.ibsvalleyn.missvenue.models.ShoppingCarts;
import com.ibsvalleyn.missvenue.models.Wishlists;
import com.ibsvalleyn.missvenue.viewModels.ProductSectorsViewModel;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView mBottomNavigation;
    private int NAveBottomPortions;
    private boolean pressToExit = false;
    private SharedPreferences dataSaver;
    private int id;
    int x, y, z, c, a;
    private TextView notificationsBadge, notificationsBadge1;
    private int badgeWhilstsize1, badgeCartSize;
    public static int Counter = 0;
    public static int Counter1 = 0;
    private int OpneCart;
    private String device_id;
    private ProductSectorsViewModel model;
    public static int postion = 0;
    private FavoriteModel favoriteModel;

    public int getBadgeWhilstsize1() {
        return badgeWhilstsize1;
    }

    public int getBadgeCartSize() {
        return badgeCartSize;
    }

    public TextView getNotificationsBadge() {
        return notificationsBadge;
    }

    public TextView getNotificationsBadge1() {
        return notificationsBadge1;
    }

    public BottomNavigationView getmBottomNavigation() {
        return mBottomNavigation;
    }

    private AppDatabase db;

    public AppDatabase getDb() {
        return db;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SharedPreferences pref = getSharedPreferences("lang", MODE_PRIVATE);
//        SharedPreferences.Editor prefEdit = getSharedPreferences("lang", MODE_PRIVATE).edit();
//        String language = pref.getString("language", "en");
//
//        if (language.equals("en")) {
//            prefEdit.putString("language", "en");
//            prefEdit.apply();
//        } else {
//            prefEdit.putString("language", "ar");
//            prefEdit.apply();
//        }
//        LanSettings.changeLang(language, this);


        setContentView(R.layout.activity_main);
        dataSaver = getDefaultSharedPreferences(this);
        db = AppDatabase.getInstance(this);
        db.favoriteDao().deleteAll();

        id = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);
        device_id = dataSaver.getString(Static_variables.DEVICE_ID, "");
        dataSaver.edit()
                .putInt("onResumeWhilst", 1)
                .apply();
        dataSaver.edit()
                .putInt("onResumeCart", 1)
                .apply();
        Log.i("TAG", "onCreate: " + id);
        getCart();
        sendFCMForPlayer();
        SetupNAveBottom();

        Intent intent = getIntent();
        OpneCart = intent.getIntExtra("cart", 0);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Ubuntu-R.ttf", true);

        switch (OpneCart) {
            case 2:
                loadFragment(new Navigation_My_Cart_Fragment());
                mBottomNavigation.setSelectedItemId(R.id.navigation_my_cart);

                break;
            case 3:

                getWishlist1();

                //  db = AppDatabase.getInstance(this);

                break;
            case 4:
                loadFragment(new Navigation_Offers_Fragment());
                mBottomNavigation.setSelectedItemId(R.id.navigation_offers);
                break;

            case 5:
                dataSaver.edit()
                        .putInt("item", 1)
                        .apply();

                loadFragment(new Navigation_Profile_Fragment());
                mBottomNavigation.setSelectedItemId(R.id.navigation_profile);
                break;
            case 6:
                loadFragment(new Navigation_Profile_Fragment());
                mBottomNavigation.setSelectedItemId(R.id.navigation_profile);
                break;

            default:
                if (x == 0) {
                    loadFragment(new Content_Home_Fragment());
                    x = 1;
                    getWishlist();

                }
        }

    }

    private void SetupNAveBottom() {
        mBottomNavigation = findViewById(R.id.navigation);
        mBottomNavigation.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        mBottomNavigation.setItemIconTintList(null);
        mBottomNavigation.setOnNavigationItemSelectedListener(item -> {
            NAveBottomPortions = item.getItemId();
            Fragment fragment = null;

            switch (item.getItemId()) {

                case R.id.navigation_home:
                    if (x == 0) {
                        dataSaver.edit()
                                .putInt("position", 0)
                                .apply();
                        fragment = new Content_Home_Fragment();
                        x = 1;
                        y = 0;
                        z = 0;
                        c = 0;
                        a = 0;
                    }
                    break;

                case R.id.navigation_offers:
                    if (z == 0) {
                        x = 0;
                        y = 0;
                        z = 1;
                        c = 0;
                        a = 0;
                        dataSaver.edit()
                                .putInt("position", 0)
                                .apply();
                        fragment = new Navigation_Offers_Fragment();
                    }
                    break;

                case R.id.navigation_my_cart:
                    if (c == 0) {
                        x = 0;
                        y = 0;
                        z = 0;
                        c = 1;
                        a = 0;
                        dataSaver.edit()
                                .putInt("position", 0)
                                .apply();
                        fragment = new Navigation_My_Cart_Fragment();
                    }
                    break;

                case R.id.navigation_wishlist:
                    if (a == 0) {
                        x = 0;
                        y = 0;
                        z = 0;
                        c = 0;
                        a = 1;
                        dataSaver.edit()
                                .putInt("position", 0)
                                .apply();
                        fragment = new Navigation_Wishlist_Fragment();
                    }
                    break;

                case R.id.navigation_profile:

                    if (y == 0) {

//                        if (!device_id.equals("")) {
//
//                            startActivity(new Intent(MainActivity.this, SigningActivity.class));
//
//                        } else {


                        dataSaver.edit()
                                .putInt("position", 0)
                                .apply();
                        fragment = new Navigation_Profile_Fragment();
                        y = 1;
                        x = 0;
                        z = 0;
                        c = 0;
                        a = 0;
//                        }
                    }
                    break;

            }
            return loadFragment(fragment);
        });

    }

    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_nav_host1, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public boolean loadFragment(Fragment fragment, int itemId) {
        //switching fragment
        if (fragment != null) {
            Bundle bundle = new Bundle();
            bundle.putInt("itemId", itemId);
            fragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_nav_host, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onBackPressed() {
        if (OpneCart == 2 || OpneCart == 3 || OpneCart == 4 || OpneCart == 6 || OpneCart == 7) {
            super.onBackPressed();

        } else {
            if (pressToExit) {
                super.onBackPressed();
//                dataSaver.edit()
//                        .putInt("position", 0)
//                        .apply();
                return;
            }
            pressToExit = true;
            Toast.makeText(MainActivity.this, getResources().getString(R.string.press_to_exit), Toast.LENGTH_SHORT).show();
            (new Handler()).postDelayed((() ->
                    MainActivity.this.pressToExit = false), 2000L);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public TextView badgeWhilst() {
        BottomNavigationMenuView bottomNavigationMenuView = (BottomNavigationMenuView) mBottomNavigation.getChildAt(0);
        View view = bottomNavigationMenuView.getChildAt(3);
        BottomNavigationItemView itemView = (BottomNavigationItemView) view;


        View badge = LayoutInflater.from(this)
                .inflate(R.layout.notification_badge_layout, itemView, true);
        notificationsBadge = badge.findViewById(R.id.notificationsBadge);

        return badge.findViewById(R.id.notificationsBadge);
    }

    public TextView badgeCart() {
        BottomNavigationMenuView bottomNavigationMenuView = (BottomNavigationMenuView) mBottomNavigation.getChildAt(0);
        View view = bottomNavigationMenuView.getChildAt(2);
        BottomNavigationItemView itemView = (BottomNavigationItemView) view;

        View badge = LayoutInflater.from(this)
                .inflate(R.layout.notification_badge_layout, itemView, true);

        notificationsBadge1 = badge.findViewById(R.id.notificationsBadge);
        return badge.findViewById(R.id.notificationsBadge);
    }

    public void getCart() {
        Call<ShoppingCarts> addEVEnt_call = RetrofitClient.getInstance(MainActivity.this)
                .getCart(id);
        addEVEnt_call.enqueue(new Callback<ShoppingCarts>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ShoppingCarts> call, Response<ShoppingCarts> response) {

                if (response.isSuccessful()) {
                    Log.e("TAG", "isSuccessful");
                    badgeCartSize = response.body().getTotal_Quantity();
                    badgeCart().setText("" + badgeCartSize);
                    dataSaver.edit()
                            .putInt("abdo", response.body().getTotal_Quantity())
                            .apply();
                    Log.e("DSADsad", "onResponse: " + response.body().getItems().size());
                    Log.e("DSADsad", "onResponse: " + response.body().getItems().size());

                } else {
                    Log.e("TAG", "notSuccessful");
                }
            }

            @Override
            public void onFailure(Call<ShoppingCarts> call, Throwable t) {
                Log.e("TAG ", "onFailure");
                Toast.makeText(getApplicationContext(), "Check internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getWishlist() {
        Call<Wishlists> addEVEnt_call = RetrofitClient.getInstance(MainActivity.this)
                .getWishlist(id);
        addEVEnt_call.enqueue(new Callback<Wishlists>() {

            @Override
            public void onResponse(Call<Wishlists> call, Response<Wishlists> response) {

                if (response.isSuccessful()) {
                    Log.e("TAG", "isSuccessful");
                    badgeWhilstsize1 = response.body().getItems().size();
                    badgeWhilst().setText("" + badgeWhilstsize1);
                    dataSaver.edit()
                            .putInt("abdo1", response.body().getItems().size())
                            .apply();
                    for (int i = 0; i < response.body().getItems().size(); i++) {
                        favoriteModel = new FavoriteModel();

                        favoriteModel.setName(response.body().getItems().get(i).getProduct().getName());
                        favoriteModel.setId(response.body().getItems().get(i).getProduct().getId());
                        favoriteModel.setDescription(response.body().getItems().get(i).getProduct().getDescription());
                        favoriteModel.setPrice(response.body().getItems().get(i).getProduct().getPrice());
                        favoriteModel.setSellingPrice(response.body().getItems().get(i).getProduct().getSellingPrice());
                        favoriteModel.setImage_url(response.body().getItems().get(i).getProduct().getImage_url());

                        db.favoriteDao().insert(favoriteModel);


                    }

                } else {
                    Log.e("TAG", "notSuccessful");
                }
            }

            @Override
            public void onFailure(Call<Wishlists> call, Throwable t) {
                Log.e("TAG ", "onFailure");
                Toast.makeText(MainActivity.this, "Check internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getWishlist1() {
        Call<Wishlists> addEVEnt_call = RetrofitClient.getInstance(MainActivity.this)
                .getWishlist(id);
        addEVEnt_call.enqueue(new Callback<Wishlists>() {

            @Override
            public void onResponse(Call<Wishlists> call, Response<Wishlists> response) {

                if (response.isSuccessful()) {
                    db = AppDatabase.getInstance(MainActivity.this);
                    Log.e("TAG", "isSuccessful");
                    badgeWhilstsize1 = response.body().getItems().size();
                    badgeWhilst().setText("" + badgeWhilstsize1);
                    dataSaver.edit()
                            .putInt("abdo1", response.body().getItems().size())
                            .apply();
                    for (int i = 0; i < response.body().getItems().size(); i++) {
                        favoriteModel = new FavoriteModel();

                        favoriteModel.setName(response.body().getItems().get(i).getProduct().getName());
                        favoriteModel.setId(response.body().getItems().get(i).getProduct().getId());
                        favoriteModel.setDescription(response.body().getItems().get(i).getProduct().getDescription());
                        favoriteModel.setPrice(response.body().getItems().get(i).getProduct().getPrice());
                        favoriteModel.setSellingPrice(response.body().getItems().get(i).getProduct().getSellingPrice());
                        favoriteModel.setImage_url(response.body().getItems().get(i).getProduct().getImage_url());

                        db.favoriteDao().insert(favoriteModel);


                    }
                    loadFragment(new Navigation_Wishlist_Fragment());
                    mBottomNavigation.setSelectedItemId(R.id.navigation_wishlist);
                } else {
                    Log.e("TAG", "notSuccessful");
                }
            }

            @Override
            public void onFailure(Call<Wishlists> call, Throwable t) {
                Log.e("TAG ", "onFailure");
                Toast.makeText(MainActivity.this, "Check internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //
    public void sendFCMForPlayer() {
//        String fcmToken = FirebaseInstanceId.getInstance().getToken();
//        Log.e("TAG", "fcmToken " + fcmToken);
//        Service.Fetcher.getInstance().sendFCMForPlayer(token, fcmToken).enqueue(new Callback<GeneralResponse>() {
//            @Override
//            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
//                if (response.isSuccessful()) {
//                    GeneralResponse generalResponse = response.body();
//                    if (generalResponse.getStatus() == 1) {
//                        //  Toast.makeText(SportsContainerActivity.this, "تم اضافة FCM", Toast.LENGTH_LONG).show();
//                    } else {
//                        //   Toast.makeText(SportsContainerActivity.this, "لم يتم اضافة FCM", Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    //  Toast.makeText(SportsContainerActivity.this, "not Successful", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GeneralResponse> call, Throwable t) {
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i("TAG", "onResume: ");
        int onResumeWihlst = dataSaver.getInt("onResumeWhilst", 0);
        int onResumeCart = dataSaver.getInt("onResumeCart", 0);
        if (onResumeWihlst == 2) {

            notificationsBadge.setText(String.valueOf(db.favoriteDao().getAll().size()));

        }
        if (onResumeCart == 2) {

            notificationsBadge1.setText(String.valueOf( dataSaver.getInt("abdo", 0)));

        }

        dataSaver.edit()
                .putInt("onResumeWhilst", 2)
                .apply();
     dataSaver.edit()
                .putInt("onResumeCart", 2)
                .apply();


    }
}