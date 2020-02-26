package com.ibsvalleyn.missvenue.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.adapters.AdsCommentsAdapter;
import com.ibsvalleyn.missvenue.adapters.DescriptionAdapter;
import com.ibsvalleyn.missvenue.adapters.RelatedProductAdapter;
import com.ibsvalleyn.missvenue.adapters.ViewPagerAdapter;
import com.ibsvalleyn.missvenue.api.RetrofitClient;
import com.ibsvalleyn.missvenue.data_room.AppDatabase;
import com.ibsvalleyn.missvenue.helper.Static_variables;
import com.ibsvalleyn.missvenue.models.FavoriteModel;
import com.ibsvalleyn.missvenue.models.ProductDetails;
import com.ibsvalleyn.missvenue.models.Add_to_cart;
import com.ibsvalleyn.missvenue.models.RelatedProducts;
import com.ibsvalleyn.missvenue.models.ReviewModel;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.ibsvalleyn.missvenue.helper.AppFunctions.Rails;
import static com.ibsvalleyn.missvenue.helper.AppFunctions.getKProgressHUD;
import static com.ibsvalleyn.missvenue.helper.AppFunctions.setMargins;

public class Product_details extends AppCompatActivity implements View.OnClickListener {
    private TextView price_before, productName, price_after, write_review1, write_review2,
            product_description, description, notification_counter, write_review, T_description;
    ImageView menu;
    int productId, category_id, related, sector, customer_id;
    RecyclerView related_recycler;
    SharedPreferences dataSaver;
    List<String> images;
    ViewPager mPager;
    SimpleRatingBar simpleRatingBar;
    EditText ET_comment;
    TextView review, value;
    ViewPagerAdapter pagerAdapter;
    private static int currentPage = 0;
    GridLayoutManager layoutManager;
    RelatedProductAdapter relatedProductAdapter;
    Button add_to_cart, submit_rating;
    int offers;
    int wishlist_;
    int quantaty;
    private KProgressHUD kProgressHUD;
    private Toast toast;
    private TextView ProductName;
    private String product_name;
    private ConstraintLayout constraintLayout;
    private ConstraintLayout bage;
    private int flageformProOF;
    private int idOfProductOf;
    private AppCompatImageView TvShare;
    private RecyclerView rv_ads_comments;
    private RatingBar rating;
    private SimpleRatingBar infow_window_rating;
    int Enable_Review;
    int isSector;
    String reviewComment = "";
    float reviewRate = 0;
    private int search;
    private String SearchWord;
    int flag = 0;
    private TextView related_text;
    private FavoriteModel favoriteModel;
    private AppDatabase db;
    private boolean b11;
    private boolean b12;
    private ImageView favBtnOff, favBtnOn;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        db = AppDatabase.getInstance(this);

        price_before = findViewById(R.id.price_before);
        related_text = findViewById(R.id.related_text);
        price_after = findViewById(R.id.price_after);
        infow_window_rating = findViewById(R.id.infow_window_rating);
        rv_ads_comments = findViewById(R.id.rv_ads_comments);
        TvShare = findViewById(R.id.TvShare);
        bage = findViewById(R.id.bage);
        productName = findViewById(R.id.product_name);
        value = findViewById(R.id.value);
        dataSaver = getDefaultSharedPreferences(this);
        isSector = dataSaver.getInt("isSector", 0);

        toast = new Toast(this);
        notification_counter = findViewById(R.id.notification_counter);
        customer_id = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);
        mPager = findViewById(R.id.pager);
        menu = findViewById(R.id.menu);
        write_review = findViewById(R.id.write_review);
        write_review1 = findViewById(R.id.write_review1);
        write_review2 = findViewById(R.id.write_review2);
        write_review.setOnClickListener(this);
        rating = findViewById(R.id.rating);
        submit_rating = findViewById(R.id.submit_rating);
        submit_rating.setOnClickListener(this);
        kProgressHUD = getKProgressHUD(this);
        add_to_cart = findViewById(R.id.add_to_cart);
        review = findViewById(R.id.review);
        review.setOnClickListener(this);
        related_recycler = findViewById(R.id.related_recycler);
        layoutManager = new GridLayoutManager(this, 2);
        related_recycler.setLayoutManager(layoutManager);
        product_description = findViewById(R.id.product_description);
        description = findViewById(R.id.description);
        T_description = findViewById(R.id.T_description);
        description.setOnClickListener(this);
        add_to_cart.setOnClickListener(this);
        quantaty = dataSaver.getInt("abdo", 0);
        favBtnOff = findViewById(R.id.favBtnOff);
        favBtnOn = findViewById(R.id.favBtnOn);
        rv_ads_comments.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        category_id = dataSaver.getInt(Static_variables.Category_id, 0);
        productId = intent.getIntExtra("product_id", 0);
        offers = intent.getIntExtra("offers", 0);
        flageformProOF = intent.getIntExtra("flageformProOF", 0);
        idOfProductOf = intent.getIntExtra("idOfProductOf", 0);
        search = intent.getIntExtra("search", 0);
        SearchWord = intent.getStringExtra("SearchWord");

        Log.i("asdsadsad", "onCreate: " + search);

        menu.setOnClickListener(this);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Ubuntu-R.ttf", true);
        findViewById(R.id.back).setOnClickListener(view -> {
            finish();

//            if (flag != 0) {
//
//                if (search == 60) {
//                    Intent intent2 = new Intent(Product_details.this, SearchResultActivity.class);
//                    intent.putExtra("SearchWord", SearchWord);
//                    startActivity(intent2);
//                    finishAffinity();
//
//                } else if (isSector == 0) {
//                    Intent intent1 = new Intent(Product_details.this, ProductOfCategories.class);
//                    intent1.putExtra("idOfProductOf", idOfProductOf);
//                    startActivity(intent1);
//                    finishAffinity();
//
//                } else if (isSector == 55) {
//                    Intent intent2 = new Intent(Product_details.this, MainActivity.class);
//                    intent2.putExtra("cart", 2);
//                    startActivity(intent2);
//                    finishAffinity();
//
//                } else if (isSector == 66) {
//                    Intent intent2 = new Intent(Product_details.this, MainActivity.class);
//                    intent2.putExtra("cart", 3);
//                    startActivity(intent2);
//                    finishAffinity();
//
//                } else if (isSector == 77) {
//                    Intent intent2 = new Intent(Product_details.this, MainActivity.class);
//                    intent2.putExtra("cart", 4);
//                    startActivity(intent2);
//                    finishAffinity();
//
//                } else if (isSector == 88) {
//                    Intent intent2 = new Intent(Product_details.this, MainActivity.class);
//                    intent2.putExtra("cart", 5);
//                    startActivity(intent2);
//                    finishAffinity();
//
//                } else {
//                    Intent intent2 = new Intent(Product_details.this, MainActivity.class);
//                    startActivity(intent2);
//                    finishAffinity();
//                }
//            }

        });
        rating.setOnRatingBarChangeListener((ratingBar, v, b) -> {
            Call<Add_to_cart> addEVEnt_call = RetrofitClient.getInstance(Product_details.this)
                    .addRate(customer_id, productId, (int) rating.getRating());
            Log.e("customer_id", "customer_id " + customer_id);
            Log.e("productId", "productId " + productId);

            addEVEnt_call.enqueue(new Callback<Add_to_cart>() {

                @Override
                public void onResponse(Call<Add_to_cart> call, Response<Add_to_cart> response) {

                    if (response.isSuccessful()) {

                        if (response.body().isResult()) {
                            kProgressHUD.dismiss();

                            Log.e("TAG", "isSuccessful");
                            if (toast != null) {
                                toast.cancel();
                                Toast.makeText(Product_details.this, response.body().getUser_message(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (toast != null) {
                                toast.cancel();
                                Toast.makeText(Product_details.this, response.body().getUser_message(), Toast.LENGTH_SHORT).show();
                            }
                            kProgressHUD.dismiss();

                        }
                    } else {
                        Log.e("TAG", "notSuccessful");
                        // Toast.makeText(Product_details.this, "error", Toast.LENGTH_SHORT).show();
                        kProgressHUD.dismiss();

                    }
                }

                @Override
                public void onFailure(Call<Add_to_cart> call, Throwable t) {
                    Log.e("TAG ", "onFailure " + t.getMessage());
                    kProgressHUD.dismiss();
                    if (toast != null) {
                        toast.cancel();
//                        Toast.makeText(Product_details.this, "Check internet connection!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
        bage.setOnClickListener(view -> {
            finish();
            Intent intent1 = new Intent(Product_details.this, MainActivity.class);
            intent1.putExtra("cart", 2);
            startActivity(intent1);
        });

        price_before.setPaintFlags(price_before.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        ProductName = findViewById(R.id.ProductName);

        product_name = intent.getStringExtra("product_name");
        if (product_name.length() < 20) {
            ProductName.setText(product_name);

        } else {
            ProductName.setText(product_name.substring(0, 20) + "... ");
        }

        Log.e("TAG", "product_id " + productId);
        Log.e("TAG", "related " + related);
        Log.e("TAG", "category_id " + category_id);
        Log.e("TAG", "offers " + offers);

        getProductDetails();
        getRelatedProduct();
        getDescriptions();
        getProductReview();

        Log.e("TAG", "Enable_Review " + Enable_Review);
        notification_counter.setText(String.valueOf(quantaty));

        try {
            favBtnOff.setVisibility(View.GONE);
            favBtnOn.setVisibility(View.VISIBLE);
            b11 = ifExist(productId);

        } catch (Exception e) {
            favBtnOff.setVisibility(View.VISIBLE);
            favBtnOn.setVisibility(View.GONE);
        }

        Log.e("TAG", "b11 " + b11);

        if (b11) {
            favBtnOff.setVisibility(View.GONE);
            favBtnOn.setVisibility(View.VISIBLE);
        } else {
            favBtnOff.setVisibility(View.VISIBLE);
            favBtnOn.setVisibility(View.GONE);
        }


        favBtnOff.setOnClickListener(v -> {

            // To Add To Fav
            favBtnOff.setVisibility(View.GONE);
            favBtnOn.setVisibility(View.VISIBLE);
//            Integer integer = Integer.valueOf(favCount.getText().toString());
//            favCount.setText(String.valueOf(integer + 1));

//            if (favoriteModel != null) {
////                if (database.CheckItemExist(favoriteModel.getId())) {
////
////                    Toast.makeText(Product_details.this, "Already Exist", Toast.LENGTH_SHORT).show();
////
////                } else {
////
////                    database.addToFav(favoriteModel);
////                }
//                db.favoriteDao().insert(favoriteModel);
//            }
            on();

        });

        favBtnOn.setOnClickListener(v -> {

            favBtnOn.setVisibility(View.GONE);
            favBtnOff.setVisibility(View.VISIBLE);
//            Integer integer = Integer.valueOf(favCount.getText().toString());

//            if (integer != 0) {
//                favCount.setText(String.valueOf(integer - 1));
//            }


            off();
        });

    }


    private void toRatingDialog() {
        final AlertDialog builder = new AlertDialog.Builder(this).create();

        final View v2 = LayoutInflater.from(this).inflate(R.layout.rating_dialog, null);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Ubuntu-R.ttf", true);
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M) {
            setMargins(builder, 75, 150, 75, 120);
        }
        simpleRatingBar = v2.findViewById(R.id.simpleRatingBar);
        ET_comment = v2.findViewById(R.id.ET_comment);
        Button submit_rate = v2.findViewById(R.id.submit_rate);

        submit_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewComment = ET_comment.getText().toString();
                reviewRate = simpleRatingBar.getRating();
                Log.e("reviewComment", "reviewComment " + reviewComment);
                Log.e("reviewRate", "reviewRate " + (int) reviewRate);
                submitRate();
                builder.dismiss();
            }
        });
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder.setView(v2);
        builder.setCancelable(true);
        builder.show();
    }

    public void getRelatedProduct() {
        Call<List<RelatedProducts>> addEVEnt_call = RetrofitClient.getInstance(Product_details.this)
                .getRelated(productId, customer_id);
        addEVEnt_call.enqueue(new Callback<List<RelatedProducts>>() {

            @Override
            public void onResponse(Call<List<RelatedProducts>> call, Response<List<RelatedProducts>> response) {

                if (response.isSuccessful()) {
                    if (response.body().size() > 0) {
                        Log.e("isSuccessful?", "isSuccessful");
                        Log.e("onResponse", "onResponse: " + new Gson().toJson(response.body()));
                        relatedProductAdapter = new RelatedProductAdapter(Product_details.this, response.body(), notification_counter,db);
                        related_recycler.setAdapter(relatedProductAdapter);
                        related_text.setVisibility(View.VISIBLE);

                    } else {
                        related_text.setVisibility(View.GONE);
                    }
                } else {
                    Log.e("TAG", "notSuccessful");
                }
            }

            @Override
            public void onFailure(Call<List<RelatedProducts>> call, Throwable t) {
                Log.e("TAG ", "onFailure");
                //   Toast.makeText(Product_details.this, "Check internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getProductReview() {
        Call<ReviewModel> addEVEnt_call = RetrofitClient.getInstance(Product_details.this)
                .LIST_ReviewModel_model(productId);
        addEVEnt_call.enqueue(new Callback<ReviewModel>() {

            @Override
            public void onResponse(Call<ReviewModel> call, Response<ReviewModel> response) {

                if (response.isSuccessful()) {
                    if (response.body().getReview_Lists().size() == 0) {
                        write_review1.setVisibility(View.VISIBLE);
                        write_review2.setVisibility(View.VISIBLE);
                    } else {
                        write_review1.setVisibility(View.VISIBLE);
                        write_review2.setVisibility(View.GONE);

                    }
                    rv_ads_comments.setAdapter(new AdsCommentsAdapter(response.body().getReview_Lists(), Product_details.this));
                    infow_window_rating.setRating((float) response.body().getTotal_Rate());

                } else {
                    Log.e("TAG", "notSuccessful");
                }
            }

            @Override
            public void onFailure(Call<ReviewModel> call, Throwable t) {
                Log.e("TAG ", "onFailure");
                //    Toast.makeText(Product_details.this, "Check internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void submitRate() {
        Call<Add_to_cart> addEVEnt_call = RetrofitClient.getInstance(Product_details.this)
                .addReview(customer_id, productId, reviewComment, (int) reviewRate);
        Log.e("customer_id", "customer_id " + customer_id);
        Log.e("productId", "productId " + productId);
        Log.e("reviewComment", "reviewComment " + reviewComment);
        Log.e("reviewRate", "reviewRate " + (int) reviewRate);

        addEVEnt_call.enqueue(new Callback<Add_to_cart>() {

            @Override
            public void onResponse(Call<Add_to_cart> call, Response<Add_to_cart> response) {

                if (response.isSuccessful()) {

                    assert response.body() != null;
                    if (response.body().isResult()) {
                        Intent intent = new Intent(Product_details.this, Product_details.class);
                        intent.putExtra("product_id", productId);
                        intent.putExtra("product_name", product_name);
                        startActivity(intent);
                        finish();
                        Log.e("TAG", "isSuccessful");

                        if (toast != null) {
                            toast.cancel();
                            Toast.makeText(Product_details.this, response.body().getUser_message(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        if (toast != null) {
                            toast.cancel();
                            Toast.makeText(Product_details.this, response.body().getUser_message(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Log.e("TAG", "notSuccessful");
                    Toast.makeText(Product_details.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Add_to_cart> call, Throwable t) {
                Log.e("TAG ", "onFailure " + t.getMessage());
                if (toast != null) {
                    toast.cancel();
                    //     Toast.makeText(Product_details.this, "Check internet connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getProductDetails() {
        Call<List<ProductDetails>> addEVEnt_call = RetrofitClient.getInstance(Product_details.this)
                .getOfferDetails(productId);
        addEVEnt_call.enqueue(new Callback<List<ProductDetails>>() {

            @Override
            public void onResponse(Call<List<ProductDetails>> call, Response<List<ProductDetails>> response) {

                if (response.isSuccessful()) {
                    Log.e("isSuccessful", "isSuccessful");
                    Log.e("onResponse", "onResponse: " + new Gson().toJson(response.body()));
//                    if (response.body().get(0).getIswishlist() != null) {
//                        wishlist_ = response.body().get(0).getIswishlist();
//
//                    } else {
//                        wishlist_ = 0;
//                    }
//                    value.setText(response.body().get(0).getsKU());
//                    if (wishlist_ == 1) {
//                        wishlist.setImageResource(R.drawable.heart);
//                    } else {
//                        wishlist.setImageResource(R.drawable.heart_add);
//                    }
                    productName.setText(response.body().get(0).getName());
                    product_description.setText(response.body().get(0).getDescription());
                    if (response.body().get(0).getPrice() > response.body().get(0).getSellingPrice()) {
                        price_before.setVisibility(View.VISIBLE);
                        price_before.setTextSize(14);
                        price_after.setTextSize(14);
                    }
                    price_before.setText(Rails(Product_details.this, response.body().get(0).getPrice()));
                    price_after.setText(Rails(Product_details.this, response.body().get(0).getSellingPrice()));
                    images = response.body().get(0).getImages();
                    pagerAdapter = new ViewPagerAdapter(Product_details.this, images);
                    mPager.setAdapter(pagerAdapter);
                    Enable_Review = response.body().get(0).getEnable_Review();

                    if (Enable_Review == 1) {
                        write_review1.setVisibility(View.GONE);
                        write_review.setVisibility(View.VISIBLE);
                        review.setVisibility(View.VISIBLE);
                    } else {
                        write_review1.setVisibility(View.VISIBLE);
                        write_review.setVisibility(View.GONE);
                        review.setVisibility(View.GONE);
                    }

                    init();
                    TvShare.setOnClickListener(view -> shareIntent(response.body().get(0).getProductUrl()));
                    favoriteModel = new FavoriteModel();

                    favoriteModel.setName(response.body().get(0).getName());
                    favoriteModel.setId(response.body().get(0).getId());
                    favoriteModel.setDescription(response.body().get(0).getDescription());
                    favoriteModel.setPrice(response.body().get(0).getPrice());
                    favoriteModel.setSellingPrice(response.body().get(0).getSellingPrice());
                    favoriteModel.setImage_url(response.body().get(0).getImage_url());

                } else {
                    Log.e("TAG", "notSuccessful");
                }
            }

            @Override
            public void onFailure(Call<List<ProductDetails>> call, Throwable t) {
                Log.e("TAG ", "onFailure");

                //    Toast.makeText(Product_details.this, "Check internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void btToggleClick(View view) {
        if (view instanceof Button) {
            Button b = (Button) view;
            if (b.isSelected()) {
                b.setTextColor(Color.parseColor("#000000"));
            } else {
                b.setTextColor(Color.WHITE);
            }
            b.setSelected(!b.isSelected());
        }
    }

    private void init() {
        CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (currentPage == images.size()) {
                currentPage = 0;
            }
            mPager.setCurrentItem(currentPage++, true);
        };
        if (images.size() == 1) {
            indicator.setVisibility(View.GONE);
        }
//        Timer swipeTimer = new Timer();
//        swipeTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 2000, 2000);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void popupMenu() {
        PopupMenu popup = new PopupMenu(Product_details.this, menu);

        popup.getMenuInflater().inflate(R.menu.miss_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    Intent intent1 = new Intent(Product_details.this, MainActivity.class);
                    startActivity(intent1);
                    return true;
                } else if (item.getItemId() == R.id.wishlist) {
                    Intent intent1 = new Intent(Product_details.this, MainActivity.class);
                    intent1.putExtra("cart", 3);
                    startActivity(intent1);
                    return true;
                } else if (item.getItemId() == R.id.profile) {
                    Intent intent1 = new Intent(Product_details.this, MainActivity.class);
                    intent1.putExtra("cart", 5);
                    startActivity(intent1);
                    return true;
                }
                return false;
            }
        });
        popup.show();
    }

    public void getDescriptions() {
        RecyclerView recycler_attribute = findViewById(R.id.recycler_attribute);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recycler_attribute.setLayoutManager(layoutManager);

        Call<List<ProductDetails>> addEVEnt_call = RetrofitClient.getInstance(Product_details.this)
                .getOfferDetails(productId);
        addEVEnt_call.enqueue(new Callback<List<ProductDetails>>() {

            @Override
            public void onResponse(Call<List<ProductDetails>> call, Response<List<ProductDetails>> response) {

                if (response.isSuccessful()) {
                    Log.e("isSuccessful", "isSuccessful");
                    Log.e("onResponse", "onResponse: " + new Gson().toJson(response.body()));
                    DescriptionAdapter descriptionAdapter = new DescriptionAdapter(Product_details.this, response.body());
                    recycler_attribute.setAdapter(descriptionAdapter);

                } else {
                    Log.e("TAG", "notSuccessful");
                }
            }

            @Override
            public void onFailure(Call<List<ProductDetails>> call, Throwable t) {
                Log.e("TAG ", "onFailure");
                //  Toast.makeText(Product_details.this, "Check internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == menu) {
            popupMenu();
        }
        if (view == submit_rating) {
            String review_ = review.getText().toString();
            if (review_.equals("")) {
                Toast.makeText(Product_details.this, "empty field", Toast.LENGTH_SHORT).show();

            } else {
                Call<Add_to_cart> addEVEnt_call = RetrofitClient.getInstance(Product_details.this)
                        .addReview(customer_id, productId, review_, 1);
                Log.e("customer_id", "customer_id " + customer_id);
                Log.e("productId", "productId " + productId);

                addEVEnt_call.enqueue(new Callback<Add_to_cart>() {

                    @Override
                    public void onResponse(Call<Add_to_cart> call, Response<Add_to_cart> response) {

                        if (response.isSuccessful()) {

                            assert response.body() != null;
                            if (response.body().isResult()) {
                                kProgressHUD.dismiss();

                                Log.e("TAG", "isSuccessful");
                                if (toast != null) {
                                    toast.cancel();

                                    Toast.makeText(Product_details.this, response.body().getUser_message(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if (toast != null) {
                                    toast.cancel();
                                    Toast.makeText(Product_details.this, response.body().getUser_message(), Toast.LENGTH_SHORT).show();

                                }
                                kProgressHUD.dismiss();

                            }
                        } else {
                            Log.e("TAG", "notSuccessful");
                            Toast.makeText(Product_details.this, "error", Toast.LENGTH_SHORT).show();
                            kProgressHUD.dismiss();

                        }
                    }

                    @Override
                    public void onFailure(Call<Add_to_cart> call, Throwable t) {
                        Log.e("TAG ", "onFailure " + t.getMessage());
                        kProgressHUD.dismiss();
                        if (toast != null) {
                            toast.cancel();
                            //            Toast.makeText(Product_details.this, "Check internet connection!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

        if (view == review) {
            toRatingDialog();
        }
        if (view == add_to_cart) {
            kProgressHUD.show();
            quantaty = quantaty + 1;

            Call<Add_to_cart> addEVEnt_call = RetrofitClient.getInstance(Product_details.this)
                    .addToCart(1, customer_id, productId, 1);
            Log.e("customer_id", "customer_id " + customer_id);
            Log.e("productId", "productId " + productId);

            addEVEnt_call.enqueue(new Callback<Add_to_cart>() {

                @Override
                public void onResponse(Call<Add_to_cart> call, Response<Add_to_cart> response) {

                    if (response.isSuccessful()) {

                        if (response.body().isResult() == true) {
                            kProgressHUD.dismiss();

                            Log.e("TAG", "isSuccessful");
                            if (toast != null) {
                                toast.cancel();

                                dataSaver.edit()
                                        .putInt("abdo", response.body().getTotal_cart_amount())
                                        .apply();

                                notification_counter.setText(String.valueOf(response.body().getTotal_cart_amount()));
                                Toast.makeText(Product_details.this, response.body().getUser_message(), Toast.LENGTH_SHORT).show();

                            }

                        } else {
                            if (toast != null) {
                                toast.cancel();
                                Toast.makeText(Product_details.this, response.body().getUser_message(), Toast.LENGTH_SHORT).show();
                            }
                            kProgressHUD.dismiss();
                        }
                    } else {
                        Log.e("TAG", "notSuccessful");
                        Toast.makeText(Product_details.this, "error", Toast.LENGTH_SHORT).show();
                        kProgressHUD.dismiss();

                    }
                }

                @Override
                public void onFailure(Call<Add_to_cart> call, Throwable t) {
                    Log.e("TAG ", "onFailure " + t.getMessage());
                    kProgressHUD.dismiss();
                    if (toast != null) {
                        toast.cancel();


                        //   Toast.makeText(Product_details.this, "Check internet connection!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        if (flag != 0) {
//            if (search == 60) {
//                Intent intent2 = new Intent(Product_details.this, SearchResultActivity.class);
//                intent2.putExtra("SearchWord", SearchWord);
//                startActivity(intent2);
//                finishAffinity();
//            } else if (isSector == 0) {
//                Intent intent1 = new Intent(Product_details.this, ProductOfCategories.class);
//                intent1.putExtra("idOfProductOf", idOfProductOf);
//                startActivity(intent1);
//                finishAffinity();
//            } else if (isSector == 55) {
//                Intent intent2 = new Intent(Product_details.this, MainActivity.class);
//                intent2.putExtra("cart", 2);
//                startActivity(intent2);
//                finishAffinity();
//            } else if (isSector == 66) {
//                Intent intent2 = new Intent(Product_details.this, MainActivity.class);
//                intent2.putExtra("cart", 3);
//                startActivity(intent2);
//                finishAffinity();
//            } else if (isSector == 77) {
//                Intent intent2 = new Intent(Product_details.this, MainActivity.class);
//                intent2.putExtra("cart", 4);
//                startActivity(intent2);
//                finishAffinity();
//            } else if (isSector == 88) {
//                Intent intent2 = new Intent(Product_details.this, MainActivity.class);
//                intent2.putExtra("cart", 5);
//                startActivity(intent2);
//                finishAffinity();
//            } else {
//                Intent intent2 = new Intent(Product_details.this, MainActivity.class);
//                startActivity(intent2);
//                finishAffinity();
//            }
//        }

        finish();
    }

    private void shareIntent(String productUrl) {
        final String appPackageName = Product_details.this.getPackageName();
        String strAppLink = "";

        try {
            strAppLink = "https://play.google.com/store/apps/details?id=" + appPackageName;
        } catch (android.content.ActivityNotFoundException anfe) {
            strAppLink = "https://play.google.com/store/apps/details?id=" + appPackageName;
        }
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                productUrl);
        sendIntent.setType("text/plain");
        Product_details.this.startActivity(Intent.createChooser(sendIntent, ""));
    }

    private Boolean ifExist(int id) {
         /*sonyRec
        if ()*/
        favoriteModel = db.favoriteDao().fetchById(id); // Read first

        if (favoriteModel.getId() != id) {
            return false;
        }

        // db.productDao().delete(sonyRec)

        return true;
    }


    private void delete(int id) {
        FavoriteModel sonyRec = db.favoriteDao().fetchById(id); // Read first


        db.favoriteDao().delete(sonyRec);

    }


    private void off() {
        b12 = ifExist(productId);
        kProgressHUD.show();
        Log.e("wishlist_", "wishlist_ " + wishlist_);


        wishlist_ = 0;
        Call<Add_to_cart> addEVEnt_call = RetrofitClient.getInstance(Product_details.this)
                .updateCart(2, customer_id, productId, 0);
        Log.e("customer_id", "customer_id " + customer_id);
        Log.e("productId", "productId " + productId);

        addEVEnt_call.enqueue(new Callback<Add_to_cart>() {

            @Override
            public void onResponse(Call<Add_to_cart> call, Response<Add_to_cart> response) {

                if (response.isSuccessful()) {

                    if (response.body().isResult() == true) {
                        kProgressHUD.dismiss();
                        favBtnOn.setVisibility(View.GONE);
                        favBtnOff.setVisibility(View.VISIBLE);
                        Log.e("TAG", "isSuccessful");
                        if (toast != null) {
                            toast.cancel();
                            Toast.makeText(Product_details.this, response.body().getUser_message(), Toast.LENGTH_SHORT).show();
                        }
                        if (favoriteModel != null) {
                            delete(productId);
                        }
                    } else {
                        if (toast != null) {
                            toast.cancel();
                            Toast.makeText(Product_details.this, response.body().getUser_message(), Toast.LENGTH_SHORT).show();

                        }
                        kProgressHUD.dismiss();
                    }
                } else {
                    Log.e("TAG", "notSuccessful");

                    if (toast != null) {
                        toast.cancel();
                        Toast.makeText(Product_details.this, "error", Toast.LENGTH_SHORT).show();
                    }
                    kProgressHUD.dismiss();

                }
            }

            @Override
            public void onFailure(Call<Add_to_cart> call, Throwable t) {
                Log.e("TAG ", "onFailure " + t.getMessage());
                kProgressHUD.dismiss();
                if (toast != null) {
                    toast.cancel();
                    //       Toast.makeText(Product_details.this, "Check internet connection!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void on() {

        Call<Add_to_cart> addEVEnt_call = RetrofitClient.getInstance(Product_details.this)
                .addToCart(2, customer_id, productId, 1);
        Log.e("customer_id", "customer_id " + customer_id);
        Log.e("productId", "productId " + productId);

        addEVEnt_call.enqueue(new Callback<Add_to_cart>() {

            @Override
            public void onResponse(Call<Add_to_cart> call, Response<Add_to_cart> response) {

                if (response.isSuccessful()) {

                    if (response.body().isResult() == true) {
                        kProgressHUD.dismiss();
                        favBtnOn.setVisibility(View.VISIBLE);
                        favBtnOff.setVisibility(View.GONE);
                        Log.e("TAG", "isSuccessful");

                        if (toast != null) {
                            toast.cancel();
                            Toast.makeText(Product_details.this, response.body().getUser_message(), Toast.LENGTH_SHORT).show();
                        }
                        if (favoriteModel != null) {


                            db.favoriteDao().insert(favoriteModel);
                        }

                    } else {
                        if (toast != null) {
                            toast.cancel();
                            Toast.makeText(Product_details.this, response.body().getUser_message(), Toast.LENGTH_SHORT).show();
                        }
                        kProgressHUD.dismiss();
                    }

                } else {
                    Log.e("TAG", "notSuccessful");

                    if (toast != null) {
                        toast.cancel();
                        Toast.makeText(Product_details.this, "error", Toast.LENGTH_SHORT).show();
                    }
                    kProgressHUD.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Add_to_cart> call, Throwable t) {
                Log.e("TAG ", "onFailure " + t.getMessage());
                kProgressHUD.dismiss();
                //       Toast.makeText(Product_details.this, "Check internet connection!", Toast.LENGTH_SHORT).show();
            }
        });

    }


}