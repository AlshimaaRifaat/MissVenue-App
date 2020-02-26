package com.ibsvalleyn.missvenue.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Transformers.AccordionTransformer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.adapters.ParentOvalAdapter;
import com.ibsvalleyn.missvenue.adapters.ProductAdapter;
import com.ibsvalleyn.missvenue.adapters.RectangleAdapter;
import com.ibsvalleyn.missvenue.adapters.RectangleCategoriesAdapter;
import com.ibsvalleyn.missvenue.api.RetrofitClient;
import com.bumptech.glide.Glide;
import com.ibsvalleyn.missvenue.data_room.AppDatabase;
import com.ibsvalleyn.missvenue.helper.Selection_Brands;
import com.ibsvalleyn.missvenue.helper.Selection_Categories;
import com.ibsvalleyn.missvenue.helper.Selection_Multi_Selected;
import com.ibsvalleyn.missvenue.helper.Static_variables;
import com.ibsvalleyn.missvenue.models.Product_Categories;
import com.ibsvalleyn.missvenue.models.Products;
import com.ibsvalleyn.missvenue.models.search.Brands;
import com.ibsvalleyn.missvenue.models.search.FilterModel;
import com.ibsvalleyn.missvenue.models.search.Filter_elements;
import com.ibsvalleyn.missvenue.viewModels.ProductCategoriesViewModel;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.ibsvalleyn.missvenue.helper.AppFunctions.Rails;
import static com.ibsvalleyn.missvenue.helper.AppFunctions.getKProgressHUD;

public class ProductOfCategories extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView mBottomNavigation;
    private int NAveBottomPortions;

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    RelativeLayout capture5_relative, capture7_relative;
    LinearLayout capture6_relative;
    TextView text1, text2, text3, notification_counter;
    ImageView capture5, capture6, capture7;
    private Toolbar toolbar;
    ImageView my_filter, banner;
    RecyclerView productRecyclerView, rv_color, product_recycler_filter;
    GridLayoutManager layoutManager;
    ProductAdapter productAdapter;
    int id, sector_id, customer_id;
    SharedPreferences dataSaver;
    List<Products> productList = new ArrayList<>();
    private GridLayoutManager gridManager;
    RectangleAdapter rectangleAdapter;
    private RecyclerView rv_rectangle, rv_parent_oval, rv_categories;
    List<Integer> idList = new ArrayList<>();
    private List<Brands> brands = new ArrayList<>();
    private KProgressHUD kProgressHUD;
    private List<Filter_elements> filterElements = new ArrayList<>();
    private TextView brandsTv;
    private TextView priceTv;
    private TextView sizeTv;
    private LinearLayout price_range;
    private TextView colorTv, sector_name, categories;
    String sectors_name;
    private TextView ApplyFilter;
    private String MinPrice;
    private String MaxPrice;
    int quantaty;
    String query;
    private int category_id;
    private static final String TAG = "AAAA";
    View line_brands, line_categories;
    private ArrayList<String> idsCategories = new ArrayList<>();
    private ArrayList<String> idsBrands = new ArrayList<>();
    private ArrayList<String> selection_multi_selected = new ArrayList<>();
    private CrystalRangeSeekbar rangeSeekbar;
    private double max_price;
    private double min_price;
    private SliderLayout slider;
    private int pageNumber = 1;
    boolean isLoading = true;
    int pastVisibleItem = 0, visibleItemCount = 0, totalItemCount = 0, previousTotal = 0;
    int viewThreshold = 10;
    private ProductCategoriesViewModel model;
    int flag = 0;
    private AppDatabase db;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_of_categories);
        rangeSeekbar = findViewById(R.id.range_seek_bar);
        db = AppDatabase.getInstance(this);

        productRecyclerView = findViewById(R.id.product_recycler);
        product_recycler_filter = findViewById(R.id.product_recycler_filter);
        kProgressHUD = getKProgressHUD(this);
        dataSaver = getDefaultSharedPreferences(this);
        customer_id = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);
        category_id = dataSaver.getInt(Static_variables.Category_id, 0);

        sector_id = dataSaver.getInt(Static_variables.Sector_id, 0);
        layoutManager = new GridLayoutManager(this, 2);
        drawer = findViewById(R.id.drawerLayout);
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        toolbar = findViewById(R.id.toolbar);
        my_filter = findViewById(R.id.my_filter);
        rv_color = findViewById(R.id.rv_color);
        banner = findViewById(R.id.banner);
        rv_rectangle = findViewById(R.id.rv_rectangle);
        rv_parent_oval = findViewById(R.id.rv_parent_oval);
        brandsTv = findViewById(R.id.brands);
        priceTv = findViewById(R.id.price);
        line_brands = findViewById(R.id.line_brands);
        line_categories = findViewById(R.id.line_categories);
        sizeTv = findViewById(R.id.size);
        colorTv = findViewById(R.id.color);
        categories = findViewById(R.id.categories);
        rv_categories = findViewById(R.id.rv_categories);
        notification_counter = findViewById(R.id.notification_counter);
        quantaty = dataSaver.getInt("abdo", 0);
        notification_counter.setText(String.valueOf(quantaty));
        constraintLayout.setOnClickListener(view -> {

            finish();
            Intent intent = new Intent(ProductOfCategories.this, MainActivity.class);
            intent.putExtra("cart", 2);
            startActivity(intent);

        });

        slider = findViewById(R.id.slider);
        slider.setBackground(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        slider.setDuration(4000);
        slider.setPagerTransformer(false, new AccordionTransformer());
        slider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
        price_range = findViewById(R.id.price_range);


        SharedPreferences pref = getSharedPreferences("lang", MODE_PRIVATE);
        String language = pref.getString("language", "en");
        assert language != null;
        if (language.equals("ar")) {
            rangeSeekbar.setScaleX(-1);
        }
        findViewById(R.id.close).setOnClickListener(view -> drawer.closeDrawer(GravityCompat.END));
        sector_name = findViewById(R.id.sector_name);
        capture5 = findViewById(R.id.capture5);
        capture6 = findViewById(R.id.capture6);
        capture7 = findViewById(R.id.capture7);

        capture5_relative = findViewById(R.id.capture5_relative);
        capture6_relative = findViewById(R.id.capture6_relative);
        capture7_relative = findViewById(R.id.capture7_relative);

        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        capture5.setOnClickListener(this);
        capture6.setOnClickListener(this);
        capture7.setOnClickListener(this);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.syncState();
        drawer.addDrawerListener(toggle);

        toggle.setDrawerIndicatorEnabled(false);
        my_filter.setOnClickListener(view -> drawer.openDrawer(GravityCompat.END));

        productRecyclerView.setLayoutManager(layoutManager);
        productRecyclerView.setAdapter(productAdapter);

        findViewById(R.id.back).setOnClickListener(view -> {
//            flag = dataSaver.getInt("flag", 0);
//
//            if (flag == 1) {
//                finish();
//                Intent intent = new Intent(ProductOfCategories.this, MainActivity.class);
//                startActivity(intent);
//                dataSaver.edit()
//                        .putInt("flag", 0)
//                        .apply();
//            } else {
                finish();
            //}

        });
        Intent intent = getIntent();
        Log.i(TAG, "onCreate: " + id);
        sectors_name = dataSaver.getString("sectors_name", "");
        sector_name.setText(sectors_name);

        Log.e("id", "id " + id);

        gridManager = new GridLayoutManager(this, 2);
        rv_rectangle.setLayoutManager(gridManager);
        gridManager = new GridLayoutManager(this, 2);
        rv_color.setLayoutManager(gridManager);
        gridManager = new GridLayoutManager(this, 2);
        gridManager = new GridLayoutManager(this, 2);
        rv_rectangle.setLayoutManager(gridManager);
        rv_rectangle.setAdapter(new RectangleAdapter(brands, ProductOfCategories.this));
        gridManager = new GridLayoutManager(this, 2);
        rv_categories.setLayoutManager(gridManager);
        gridManager = new GridLayoutManager(this, 2);
        rv_parent_oval.setLayoutManager(new LinearLayoutManager(this));
//        getBanners();
        showHideNaveElements();
        // getProduct();
        getFilter();
        ApplyFilterFun();
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Ubuntu-R.ttf", true);


        findViewById(R.id.clearData).setOnClickListener(view -> {


            idsCategories.clear();
            idsBrands.clear();
            selection_multi_selected.clear();
            Log.i(TAG, "onCreate: " + idsCategories + selection_multi_selected + selection_multi_selected);
            rangeSeekbar.setMinStartValue((float) min_price).setMaxStartValue((float) max_price).apply();
            getFilter();


//
//            drawer.closeDrawer(GravityCompat.END);

        });

       // SetupNAveBottom();
        model = ViewModelProviders.of(this).get(ProductCategoriesViewModel.class);
        model.setContext(ProductOfCategories.this);

        model.getProducts(category_id, pageNumber, 16).observe(ProductOfCategories.this, heroList -> {


            for (int i = 0; i < heroList.getSupCategories().size(); i++) {
                int id = heroList.getSupCategories().get(i).getId();
                idList.add(id);
            }

            if (heroList.getSupCategories().size() < 4) {
                slider.setVisibility(View.GONE);

            } else {
                slider.setVisibility(View.VISIBLE);

                for (int i = 3; i < heroList.getSupCategories().size(); i++) {

                    DefaultSliderView view2 = new DefaultSliderView(ProductOfCategories.this);

                    int categoryId = heroList.getSupCategories().get(i).getId();
                    String name = heroList.getSupCategories().get(i).getName();
                    view2.description(heroList.getSupCategories().get(i).getName()).image(heroList.getSupCategories().get(i).getImage_url())
                            .setOnSliderClickListener(slider1 -> {
                                Intent intent1 = new Intent(ProductOfCategories.this, ProductOfCategories.class);

                                dataSaver.edit()
                                        .putInt(Static_variables.Category_id, categoryId)
                                        .apply();

                                dataSaver.edit()
                                        .putString("sectors_name", name)
                                        .apply();
                                startActivity(intent1);

                            })
                            .setScaleType(BaseSliderView.ScaleType.Fit);

                    slider.addSlider(view2);

                }
            }

            if (heroList.getSupCategories().size() != 0) {
                if (heroList.getSupCategories().get(0) != null) {
                    capture5_relative.setVisibility(View.VISIBLE);

                    Glide.with(ProductOfCategories.this).load(heroList.getSupCategories().get(0).getImage_url()).into(capture5);

                    text1.setText(heroList.getSupCategories().get(0).getName());
                } else {
                    capture5_relative.setVisibility(View.GONE);
                }
                if (heroList.getSupCategories().size() >= 2) {
                    if (heroList.getSupCategories().get(1) != null) {
                        capture6_relative.setVisibility(View.VISIBLE);
                        text2.setText(heroList.getSupCategories().get(1).getName());
                        Glide.with(ProductOfCategories.this).load(heroList.getSupCategories().get(1).getImage_url()).into(capture6);
                    }
                } else {
                    capture6_relative.setVisibility(View.GONE);
                }
                if (heroList.getSupCategories().size() >= 3) {
                    if (heroList.getSupCategories().get(2) != null) {
                        text3.setText(heroList.getSupCategories().get(2).getName());
                        capture7_relative.setVisibility(View.VISIBLE);
                        Glide.with(ProductOfCategories.this).load(heroList.getSupCategories().get(2).getImage_url()).into(capture7);
                    }
                } else {
                    capture7_relative.setVisibility(View.GONE);
                }
            }
            //   productList.addAll(heroList);
            productRecyclerView.setVisibility(View.VISIBLE);
            product_recycler_filter.setVisibility(View.GONE);
            productAdapter = new ProductAdapter(ProductOfCategories.this, heroList.getProducts(), notification_counter, id,db);
            productRecyclerView.setAdapter(productAdapter);
            Log.i("Flag ", "Flag " + flag);
            productRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItem = layoutManager.findFirstVisibleItemPosition();
                    Log.e("TAG", "dy " + dy);
                    // dy = dy+1;
                    if (dy > 0) {
                        if (isLoading) {
                            if (totalItemCount > previousTotal) {
                                isLoading = false;
                                previousTotal = totalItemCount;
                            }
                        }
                        if (!isLoading && (totalItemCount - visibleItemCount) <= (pastVisibleItem + viewThreshold)) {
                            Log.e("TAG", "performPagination()");

                            pageNumber++;
                            model.getProducts(category_id, pageNumber, 16).observe(ProductOfCategories.this, heroList -> {
                                productAdapter.addInRecycle(heroList.getProducts());

                            });
                            isLoading = true;

                            Log.e("TAG", "pageNumber " + pageNumber);

                        }
                    }
                }
            });

        });

    }


    private void ApplyFilterFun() {
        ApplyFilter = findViewById(R.id.ApplyFilter);

        ApplyFilter.setOnClickListener(view ->
        {

            idsCategories = Selection_Categories.selection_categories().getIds();
            idsBrands = Selection_Brands.selection_brands().getIds();
            selection_multi_selected = Selection_Multi_Selected.selection_multi_selected().getIds();

            Log.i("idsCategories ", "idsCategories " + idsCategories);
            Log.i("idsBrands ", "idsBrands " + idsBrands);
            Log.i("selection_multi ", "selection_multi_selected " + selection_multi_selected);

            Log.i("productList", "productList " + new Gson().toJson(productList));
            kProgressHUD.show();
            Call<List<Products>> addEVEnt_call = RetrofitClient.getInstance(ProductOfCategories.this)
                    .PRODUCTS_CALL_Filter(category_id,
                            Arrays.toString(idsCategories.toArray()),
                            Arrays.toString(idsBrands.toArray()), Double.valueOf(MinPrice), Double.valueOf(MaxPrice),
                            Arrays.toString(selection_multi_selected.toArray()));

            addEVEnt_call.enqueue(new Callback<List<Products>>() {
                @Override
                public void onResponse(@NotNull Call<List<Products>> call, @NotNull Response<List<Products>> response) {
                    kProgressHUD.dismiss();
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        if (response.body().size() != 0) {
                            productList.clear();
                            productList.addAll(response.body());
                            productAdapter = new ProductAdapter(ProductOfCategories.this, productList, notification_counter, id,db);
                            productRecyclerView.setVisibility(View.GONE);
                            product_recycler_filter.setVisibility(View.VISIBLE);
                            product_recycler_filter.setAdapter(productAdapter);
                            drawer.closeDrawer(GravityCompat.END);

                        } else {
                            Toast.makeText(ProductOfCategories.this, getString(R.string.Noitemsmatchyourfilter), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ProductOfCategories.this, "not sccessfull", Toast.LENGTH_SHORT).show();
                        Log.i("dsafsaf", "onResponse: " + response.raw().toString());
                    }
                }

                @Override
                public void onFailure(Call<List<Products>> call, Throwable t) {
                    Log.e("TAG ", "onFailure " + t.getMessage());
                    Toast.makeText(ProductOfCategories.this, "Check internet connection!", Toast.LENGTH_SHORT).show();
                    kProgressHUD.dismiss();
                }

            });
            Log.i("asddsadsa", "ApplyFilterFun: " + MinPrice + MaxPrice);
            Log.i("new_list", String.valueOf(Selection_Categories.selection_categories().getIds()));
            Log.i("new_list", new Gson().toJson(Selection_Categories.selection_categories().getIds()));
            Log.i("new_list", new Gson().toJson(Selection_Brands.selection_brands().getIds()));
            Log.i("dsadsadsad", new Gson().toJson(Selection_Multi_Selected.selection_multi_selected().getIds()));
            Log.i("saSAsa", "ApplyFilterFun: " + selection_multi_selected);

        });


    }

    private void showHideNaveElements() {

        Drawable img_down = getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp);
        Drawable img = getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp);

        brandsTv.setOnClickListener(view ->
                {
                    if (rv_rectangle.getVisibility() == View.VISIBLE) {
                        rv_rectangle.setVisibility(View.GONE);
                        brandsTv.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);

                    } else {
                        rv_rectangle.setVisibility(View.VISIBLE);
                        brandsTv.setCompoundDrawablesWithIntrinsicBounds(img_down, null, null, null);

                    }
                }
        );
        categories.setOnClickListener(view ->
                {
                    if (rv_categories.getVisibility() == View.VISIBLE) {
                        rv_categories.setVisibility(View.GONE);
                        categories.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);

                    } else {
                        rv_categories.setVisibility(View.VISIBLE);
                        categories.setCompoundDrawablesWithIntrinsicBounds(img_down, null, null, null);

                    }
                }
        );

        priceTv.setOnClickListener(view -> {
            if (price_range.getVisibility() == View.VISIBLE) {
                price_range.setVisibility(View.GONE);
                priceTv.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);

            } else {
                price_range.setVisibility(View.VISIBLE);
                priceTv.setCompoundDrawablesWithIntrinsicBounds(img_down, null, null, null);
            }

        });

        colorTv.setOnClickListener(view -> {
            if (rv_color.getVisibility() == View.VISIBLE) {
                rv_color.setVisibility(View.GONE);
                colorTv.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);

            } else {
                rv_color.setVisibility(View.VISIBLE);
                colorTv.setCompoundDrawablesWithIntrinsicBounds(img_down, null, null, null);

            }

        });
    }

//    public void getBanners() {
//        Log.e("TAG", "sector_id " + sector_id);
//
//        Call<List<Banners>> addEVEnt_call = RetrofitClient.getInstance(ProductOfCategories.this)
//                .getBanners(sector_id, 1);
//        addEVEnt_call.enqueue(new Callback<List<Banners>>() {
//            @Override
//            public void onResponse(Call<List<Banners>> call, Response<List<Banners>> response) {
//
//                if (response.isSuccessful()) {
//                    Log.e("TAG", "isSuccessful");
//                    Log.e("TAG", "onResponse2: " + new Gson().toJson(response.body()));
//
//
//                    if (response.body().size() != 0) {
//                        if (response.body().get(0) != null) {
//                            banner.setVisibility(View.VISIBLE);
//
//                            Glide.with(ProductOfCategories.this).load(response.body().get(0).getImage()).into(banner);
//                        }
//
//                    } else {
//                        banner.setVisibility(View.GONE);
//                    }
//
//                } else {
//                    Log.e("TAG", "notSuccessful");
//                    Log.e("TAG", "notSuccessful" + new Gson().toJson(response.body()));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Banners>> call, Throwable t) {
//                Log.e("TAG ", "onFailure " + t.getMessage());
//                // Toast.makeText(ProductOfCategories.this, "Check internet connection!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    public void getFilter() {
        kProgressHUD.show();
        filterElements.clear();
        brands.clear();
        Log.e("TAG", "isSuccessful");
        Log.e("TAG", "category_id " + category_id);
        Log.e("TAG", "isSuccessful" + id);
        Call<FilterModel> addEVEnt_call = RetrofitClient.getInstance(ProductOfCategories.this)
                .getFiletr(category_id);
        addEVEnt_call.enqueue(new Callback<FilterModel>() {

            @Override
            public void onResponse(Call<FilterModel> call, Response<FilterModel> response) {
                kProgressHUD.dismiss();

                if (response.isSuccessful()) {
                    Log.e("TAG", "isSuccessful");
                    Log.i("asdgajgdsagdj", "isSuccessful" + new Gson().toJson(response.body().getBrands()));

                    if (response.body().getBrands() != null) {
                        if (response.body().getBrands().size() != 0) {
                            brands.addAll(response.body().getBrands());
                            brandsTv.setVisibility(View.VISIBLE);
                            line_brands.setVisibility(View.VISIBLE);
                            rv_rectangle.setAdapter(new RectangleAdapter(brands, ProductOfCategories.this));
                        } else {
                            brandsTv.setVisibility(View.GONE);
                            line_brands.setVisibility(View.GONE);

                        }
                    }
                    if (response.body().getCategories() != null) {
                        if (response.body().getCategories().size() != 0) {

                            categories.setVisibility(View.VISIBLE);
                            line_categories.setVisibility(View.VISIBLE);

                            rv_categories.setAdapter(new RectangleCategoriesAdapter(response.body().getCategories(), ProductOfCategories.this));
                        } else {
                            categories.setVisibility(View.GONE);
                            line_categories.setVisibility(View.GONE);

                        }
                    }

                    filterElements.addAll(response.body().getRefineSearch().getFilter().getFilter_elements());
                    rv_parent_oval.setAdapter(new ParentOvalAdapter(filterElements, ProductOfCategories.this));
                    max_price = response.body().getRefineSearch().getMax_Price();
                    min_price = response.body().getRefineSearch().getMin_Price();
                    setRangeSeekbar(response.body().getRefineSearch().getMax_Price(), response.body().getRefineSearch().getMin_Price());
                } else {
                    Log.e("TAG", "notSuccessful");
                }
            }

            @Override
            public void onFailure(Call<FilterModel> call, Throwable t) {
                Log.e("TAG ", "onFailureFilter " + t.getMessage());
                Toast.makeText(ProductOfCategories.this, "Check internet connection!", Toast.LENGTH_SHORT).show();
                kProgressHUD.dismiss();
            }
        });
    }

    private void setRangeSeekbar(double maxPrice, double minPrice) {


        rangeSeekbar.setMaxValue((int) Math.round(maxPrice));
        rangeSeekbar.setMinValue((int) Math.round(minPrice));
        final TextView tvMin = findViewById(R.id.price_min);
        final TextView tvMax = findViewById(R.id.price_max);

        rangeSeekbar.setOnRangeSeekbarChangeListener((minValue, maxValue) -> {
            MinPrice = String.valueOf(minValue);
            MaxPrice = String.valueOf(maxValue);
            tvMin.setText(Rails(ProductOfCategories.this, Double.valueOf(MinPrice)));
            tvMax.setText(Rails(ProductOfCategories.this, Double.valueOf(MaxPrice)));

        });
    }

    public void getProduct() {
        Log.e("TAG", "isSuccessful");
        Call<Product_Categories> addEVEnt_call = RetrofitClient.getInstance(ProductOfCategories.this)
                .getCategoriesProduct(category_id, pageNumber, 20);
        addEVEnt_call.enqueue(new Callback<Product_Categories>() {

            @Override
            public void onResponse(Call<Product_Categories> call, Response<Product_Categories> response) {

                if (response.isSuccessful()) {
                    Log.e("TAG", "isSuccessful");

                    for (int i = 0; i < response.body().getSupCategories().size(); i++) {
                        int id = response.body().getSupCategories().get(i).getId();
                        idList.add(id);
                    }


                    if (response.body().getSupCategories().size() < 4) {
                        slider.setVisibility(View.GONE);
                        Log.e(TAG, "onResponse2: " + new Gson().toJson(response.body()));

                    } else {
                        slider.setVisibility(View.VISIBLE);
                        Log.e(TAG, "onResponse2: " + new Gson().toJson(response.body()));

                        for (int i = 3; i < response.body().getSupCategories().size(); i++) {

                            DefaultSliderView view2 = new DefaultSliderView(ProductOfCategories.this);

                            int categoryId = response.body().getSupCategories().get(i).getId();
                            String name = response.body().getSupCategories().get(i).getName();
                            view2.description(response.body().getSupCategories().get(i).getName()).image(response.body().getSupCategories().get(i).getImage_url())
                                    .setOnSliderClickListener(slider1 -> {
                                        Intent intent = new Intent(ProductOfCategories.this, ProductOfCategories.class);

                                        dataSaver.edit()
                                                .putInt(Static_variables.Category_id, categoryId)
                                                .apply();

                                        dataSaver.edit()
                                                .putString("sectors_name", name)
                                                .apply();
                                        startActivity(intent);

                                    })
                                    .setScaleType(BaseSliderView.ScaleType.Fit);

                            slider.addSlider(view2);


                        }
                    }

                    if (response.body().getSupCategories().size() != 0) {
                        if (response.body().getSupCategories().get(0) != null) {
                            capture5_relative.setVisibility(View.VISIBLE);

                            Glide.with(ProductOfCategories.this).load(response.body().getSupCategories().get(0).getImage_url()).into(capture5);

                            text1.setText(response.body().getSupCategories().get(0).getName());
                        } else {
                            capture5_relative.setVisibility(View.GONE);
                        }
                        if (response.body().getSupCategories().size() >= 2) {
                            if (response.body().getSupCategories().get(1) != null) {
                                capture6_relative.setVisibility(View.VISIBLE);
                                text2.setText(response.body().getSupCategories().get(1).getName());
                                Glide.with(ProductOfCategories.this).load(response.body().getSupCategories().get(1).getImage_url()).into(capture6);
                            }
                        } else {
                            capture6_relative.setVisibility(View.GONE);
                        }
                        if (response.body().getSupCategories().size() >= 3) {
                            if (response.body().getSupCategories().get(2) != null) {
                                text3.setText(response.body().getSupCategories().get(2).getName());
                                capture7_relative.setVisibility(View.VISIBLE);
                                Glide.with(ProductOfCategories.this).load(response.body().getSupCategories().get(2).getImage_url()).into(capture7);
                            }
                        } else {
                            capture7_relative.setVisibility(View.GONE);
                        }
                    }
                    productList.addAll(response.body().getProducts());
                    productAdapter = new ProductAdapter(ProductOfCategories.this, productList, notification_counter, id,db);
                    productRecyclerView.setAdapter(productAdapter);

                } else {
                    Log.e("TAG", "notSuccessful");
                }
            }

            @Override
            public void onFailure(Call<Product_Categories> call, Throwable t) {
                Log.e("TAG ", "onFailure");
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == capture5) {
            if (idList.size() >= 1) {
                Intent intent = new Intent(ProductOfCategories.this, ProductOfCategories.class);
                intent.putExtra("id", idList.get(0));
                Log.e("id", "category_id " + idList.get(0));

                dataSaver.edit()
                        .putInt(Static_variables.Category_id, idList.get(0))
                        .apply();
                startActivity(intent);
            }
        }
        if (view == capture6) {
            if (idList.size() >= 2) {
                Intent intent = new Intent(ProductOfCategories.this, ProductOfCategories.class);
                intent.putExtra("id", idList.get(1));
                Log.e("id", "category_id " + idList.get(1));
                dataSaver.edit()
                        .putInt(Static_variables.Category_id, idList.get(1))
                        .apply();
                startActivity(intent);
            }
        }
        if (view == capture7) {
            if (idList.size() >= 3) {
                Intent intent = new Intent(ProductOfCategories.this, ProductOfCategories.class);
                intent.putExtra("id", idList.get(2));
                Log.e("id", "category_id " + idList.get(2));

                dataSaver.edit()
                        .putInt(Static_variables.Category_id, idList.get(2))
                        .apply();
                startActivity(intent);
            }
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
//        flag = dataSaver.getInt("flag", 0);
//
//        if (flag == 1) {
//            finish();
//            Intent intent = new Intent(ProductOfCategories.this, MainActivity.class);
//            startActivity(intent);
//            dataSaver.edit()
//                    .putInt("flag", 0)
//                    .apply();
//        } else {
            finish();
        }



    private void SetupNAveBottom() {
        mBottomNavigation = findViewById(R.id.navigation);
        mBottomNavigation.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        mBottomNavigation.setItemIconTintList(null);
        mBottomNavigation.setOnNavigationItemSelectedListener(item -> {
            NAveBottomPortions = item.getItemId();
//            Fragment fragment = null;
            Intent intent = new Intent(ProductOfCategories.this, MainActivity.class);

            switch (item.getItemId()) {

                case R.id.navigation_home:
//                    if (x == 0) {
//                        fragment = new Content_Home_Fragment();
//                        x = 1;
//                        y = 0;
//                        z = 0;
//                        c = 0;
//                        a = 0;
//                    }
                    intent.putExtra("cart", 7);

                    startActivity(intent);
                    break;

                case R.id.navigation_offers:
//                    if (z == 0) {
//                        x = 0;
//                        y = 0;
//                        z = 1;
//                        c = 0;
//                        a = 0;
//                        fragment = new Navigation_Offers_Fragment();
//                    }


                    intent.putExtra("cart", 4);

                    startActivity(intent);
                    break;

                case R.id.navigation_my_cart:
//                    if (c == 0) {
//                        x = 0;
//                        y = 0;
//                        z = 0;
//                        c = 1;
//                        a = 0;
//                        fragment = new Navigation_My_Cart_Fragment();

//                    }


                    intent.putExtra("cart", 2);

                    startActivity(intent);


                    break;

                case R.id.navigation_wishlist:
                    intent.putExtra("cart", 3);

                    startActivity(intent);
//                    if (a == 0) {
//                        x = 0;
//                        y = 0;
//                        z = 0;
//                        c = 0;
//                        a = 1;
//                        fragment = new Navigation_Wishlist_Fragment();
//                    }
                    break;

                case R.id.navigation_profile:
                    intent.putExtra("cart", 6);

                    startActivity(intent);
//
//                    if (y == 0) {
//
//                        if (!device_id.equals("")) {
//
//                            startActivity(new Intent(MainActivity.this, SigningActivity.class));
//
//                        } else {
//
//                            fragment = new Navigation_Profile_Fragment();
//                            y = 1;
//                            x = 0;
//                            z = 0;
//                            c = 0;
//                            a = 0;
//                        }
//                    }
                    break;

            }
            return false;
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}