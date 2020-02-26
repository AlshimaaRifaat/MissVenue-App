package com.ibsvalleyn.missvenue.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.gson.Gson;
import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.adapters.ParentOvalAdapter;
import com.ibsvalleyn.missvenue.adapters.ProductFromSearchAdapter;
import com.ibsvalleyn.missvenue.adapters.RectangleAdapter;
import com.ibsvalleyn.missvenue.adapters.RectangleCategoriesAdapter;
import com.ibsvalleyn.missvenue.api.RetrofitClient;
import com.ibsvalleyn.missvenue.data_room.AppDatabase;
import com.ibsvalleyn.missvenue.helper.Selection_Brands;
import com.ibsvalleyn.missvenue.helper.Selection_Categories;
import com.ibsvalleyn.missvenue.helper.Selection_Multi_Selected;
import com.ibsvalleyn.missvenue.helper.Static_variables;
import com.ibsvalleyn.missvenue.models.SearchProductCategory;
import com.ibsvalleyn.missvenue.models.search.Brands;
import com.ibsvalleyn.missvenue.models.search.Categories;
import com.ibsvalleyn.missvenue.models.search.FilterModel;
import com.ibsvalleyn.missvenue.models.search.Filter_elements;
import com.ibsvalleyn.missvenue.viewModels.ProductSearchViewModel;
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

public class SearchResultActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private String SearchWord = "";
    private RecyclerView rv_products;
    private GridLayoutManager layoutManager;
    private KProgressHUD kProgressHUD;
    private String idSubCat;
    ImageView search_btn, search_btn1;
    private LinearLayout tvNodata;
    EditText search_txt1;
    RelativeLayout search_relative;
    private SharedPreferences dataSaver;
    private int id;
    ImageView menu;
    List<Categories> categoriesList = new ArrayList<>();
    private Toolbar toolbar;
    private ImageView my_filter;
    private TextView applyFilter;
    private LinearLayout price_range;
    private GridLayoutManager gridManager;
    private List<Brands> brands = new ArrayList<>();
    private RecyclerView rv_rectangle, rv_parent_oval, rv_categories, rv_color, product_recycler_filter;
    List<SearchProductCategory> productList = new ArrayList<>();
    ProductFromSearchAdapter productAdapter;
    RelativeLayout capture5_relative, capture7_relative;
    LinearLayout capture6_relative;
    TextView text1, text2, text3, notification_counter;
    ImageView capture5, capture6, capture7;
    int sector_id;
    RectangleAdapter rectangleAdapter;
    List<Integer> idList = new ArrayList<>();
    private List<Filter_elements> filterElements = new ArrayList<>();
    private TextView brandsTv;
    private TextView priceTv;
    private TextView sizeTv;
    private TextView colorTv, sector_name, categories;
    String sectors_name;
    private TextView ApplyFilter;
    private String MinPrice;
    private String MaxPrice;
    int quantaty;
    private ConstraintLayout constraintLayout;
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
    private ProductSearchViewModel model;
    private int pageNumber = 1, pageNumber2 = 1, pageNumber3 = 1;
    boolean isLoading = true;
    int pastVisibleItem = 0, visibleItemCount = 0, totalItemCount = 0, previousTotal = 0;
    int viewThreshold = 10;
    private ProductFromSearchAdapter productFromSearchAdapter;
    private int flag_search;
    private AppDatabase db;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        rangeSeekbar = findViewById(R.id.range_seek_bar);
        db = AppDatabase.getInstance(this);

        SharedPreferences pref = getSharedPreferences("lang", MODE_PRIVATE);
        String language = pref.getString("language", "en");
        assert language != null;
        if (language.equals("ar")) {
            rangeSeekbar.setScaleX(-1);
        }
        kProgressHUD = getKProgressHUD(this);
        dataSaver = getDefaultSharedPreferences(this);
        id = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);
        drawer = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        my_filter = findViewById(R.id.my_filter);
        price_range = findViewById(R.id.price_range);
        product_recycler_filter = findViewById(R.id.product_recycler_filter);


        rv_color = findViewById(R.id.rv_color);
        rv_categories = findViewById(R.id.rv_categories);

        rv_rectangle = findViewById(R.id.rv_rectangle);
        rv_parent_oval = findViewById(R.id.rv_parent_oval);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        toggle.syncState();
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        my_filter.setOnClickListener(view -> drawer.openDrawer(GravityCompat.END));
        menu = findViewById(R.id.menu);
        Intent intent = getIntent();
        SearchWord = intent.getStringExtra("SearchWord");
        idSubCat = intent.getStringExtra("idSubCat");
        rv_products = findViewById(R.id.rv_products);
        //search_txt = findViewById(R.id.search_txt);
        search_txt1 = findViewById(R.id.search_txt1);
        search_btn = findViewById(R.id.search_btn);
        brandsTv = findViewById(R.id.brands);
        priceTv = findViewById(R.id.price);
        line_brands = findViewById(R.id.line_brands);
        line_categories = findViewById(R.id.line_categories);
        sizeTv = findViewById(R.id.size);
        colorTv = findViewById(R.id.color);
        categories = findViewById(R.id.categories);
        rv_categories = findViewById(R.id.rv_categories);

        search_btn1 = findViewById(R.id.search_btn1);
        search_relative = findViewById(R.id.search_relative);
//        search_btn.setOnClickListener(this);
        search_btn1.setOnClickListener(this);
        tvNodata = findViewById(R.id.tvNodata);
        menu.setOnClickListener(this);

        layoutManager = new GridLayoutManager(this, 1);
        Log.i("saSAsa", "onCreate: " + SearchWord + idSubCat);
        rv_products.setLayoutManager(layoutManager);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Ubuntu-R.ttf", true);
        findViewById(R.id.back).setOnClickListener(view -> {
//            flag_search = dataSaver.getInt("flag_search", 0);
//            if (flag_search == 1) {
//                finishAffinity();
//                startActivity(new Intent(SearchResultActivity.this, MainActivity.class));
//                dataSaver.edit().putInt("flag_search", 0).apply();
//
//            }

            finish();
        });

        gridManager = new GridLayoutManager(this, 2);
        rv_rectangle.setLayoutManager(gridManager);
        gridManager = new GridLayoutManager(this, 2);
        rv_color.setLayoutManager(gridManager);
        gridManager = new GridLayoutManager(this, 2);
        gridManager = new GridLayoutManager(this, 2);
        rv_rectangle.setLayoutManager(gridManager);

        rv_rectangle.setAdapter(new RectangleAdapter(brands, SearchResultActivity.this));
        gridManager = new GridLayoutManager(this, 2);
        rv_categories.setLayoutManager(gridManager);
        gridManager = new GridLayoutManager(this, 2);
        rv_parent_oval.setLayoutManager(new LinearLayoutManager(this));
        showHideNaveElements();
        findViewById(R.id.close).setOnClickListener(view -> drawer.closeDrawer(GravityCompat.END));
//        getFilter();
        findViewById(R.id.clearData).setOnClickListener(view -> {

            idsCategories.clear();
            idsBrands.clear();
            selection_multi_selected.clear();
            Log.i(TAG, "onCreate: " + idsCategories + selection_multi_selected + selection_multi_selected);
            rangeSeekbar.setMinStartValue((float) min_price).setMaxStartValue((float) max_price).apply();
            getFilter();

        });
        ApplyFilterFun();

        model = ViewModelProviders.of(this).get(ProductSearchViewModel.class);
        model.setContext(SearchResultActivity.this);
        //search_txt.setText(SearchWord);
        search_txt1.setText(SearchWord);
        model.getProductSearch(SearchWord, pageNumber, 20).observe(this, heroList -> {
            kProgressHUD.dismiss();
            getFilter();

            if (heroList.size() != 0) {

                rv_products.setVisibility(View.VISIBLE);
                tvNodata.setVisibility(View.GONE);
                search_relative.setVisibility(View.VISIBLE);
                productFromSearchAdapter = new ProductFromSearchAdapter(SearchResultActivity.this, heroList, idSubCat, SearchWord,db);
                rv_products.setAdapter(productFromSearchAdapter);
                rv_products.setVisibility(View.VISIBLE);
                product_recycler_filter.setVisibility(View.GONE);

            } else {
                search_relative.setVisibility(View.VISIBLE);
                rv_products.setVisibility(View.GONE);
                tvNodata.setVisibility(View.VISIBLE);
            }
            rv_products.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItem = layoutManager.findFirstVisibleItemPosition();
                    Log.e("TAG", "dy " + dy);
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
                            model.getProductSearch( SearchWord, pageNumber, 20).observe(SearchResultActivity.this, heroList -> {
                                productFromSearchAdapter.addInRecycle(heroList);

                            });
                            isLoading = true;

                            Log.e("TAG", "pageNumber " + pageNumber);
                        }
                    }
                }
            });
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void SearchProductCategoryCall2() {
        rv_rectangle.setVisibility(View.GONE);
        pastVisibleItem = 0;
        visibleItemCount = 0;
        totalItemCount = 0;
        previousTotal = 0;
        viewThreshold = 10;
        Log.e("Tag", "SearchWord1 " + SearchWord);
        model.getProductSearch( SearchWord, pageNumber2, 20).observe(this, new Observer<List<SearchProductCategory>>() {
            @Override
            public void onChanged(@Nullable List<SearchProductCategory> heroList) {

                kProgressHUD.dismiss();
                getFilter();

                Log.e("Tag", "SearchWord1 " + SearchWord);
                if (heroList.size() != 0) {
                    rv_products.setVisibility(View.VISIBLE);
                    tvNodata.setVisibility(View.GONE);
                    search_relative.setVisibility(View.VISIBLE);
                    productFromSearchAdapter = new ProductFromSearchAdapter(SearchResultActivity.this, heroList, idSubCat, SearchWord,db);
                    rv_products.setAdapter(productFromSearchAdapter);
                    rv_products.setVisibility(View.VISIBLE);
                    product_recycler_filter.setVisibility(View.GONE);
                } else {
                    search_relative.setVisibility(View.VISIBLE);
                    rv_products.setVisibility(View.GONE);
                    tvNodata.setVisibility(View.VISIBLE);
                    product_recycler_filter.setVisibility(View.GONE);

                }

                rv_products.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        visibleItemCount = layoutManager.getChildCount();
                        totalItemCount = layoutManager.getItemCount();
                        pastVisibleItem = layoutManager.findFirstVisibleItemPosition();
                        Log.e("TAG", "dy " + dy);
                        if (dy > 0) {
                            if (isLoading) {
                                if (totalItemCount > previousTotal) {
                                    isLoading = false;
                                    previousTotal = totalItemCount;
                                }
                            }
                            if (!isLoading && (totalItemCount - visibleItemCount) <= (pastVisibleItem + viewThreshold)) {
                                Log.e("TAG", "performPagination()");

                                pageNumber2++;
                                model.getProductSearch(SearchWord, pageNumber2, 20).observe(SearchResultActivity.this, heroList -> {
                                    productFromSearchAdapter.addInRecycle(heroList);

                                });
                                isLoading = true;

                                Log.e("TAG", "pageNumber " + pageNumber);

                            }
                        }
                    }

                });
            }
        });
    }

    public void popupMenu() {
        PopupMenu popup = new PopupMenu(SearchResultActivity.this, menu);
        popup.getMenuInflater().inflate(R.menu.miss_menu2, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    Intent intent1 = new Intent(SearchResultActivity.this, MainActivity.class);
                    startActivity(intent1);
                    return true;
                } else if (item.getItemId() == R.id.wishlist) {
                    Intent intent1 = new Intent(SearchResultActivity.this, MainActivity.class);
                    intent1.putExtra("cart", 3);
                    startActivity(intent1);
                    return true;
                } else if (item.getItemId() == R.id.cart) {
                    Intent intent1 = new Intent(SearchResultActivity.this, MainActivity.class);
                    intent1.putExtra("cart", 2);
                    startActivity(intent1);
                    return true;
                }
                return false;
            }
        });
        popup.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
//        if (view == search_btn) {
//            SearchProductCategoryCall1();
//            Log.e("tag", "search_btn " + "search_btn");
//
//        }
        if (view == search_btn1) {

            SearchWord = search_txt1.getText().toString();

            if (SearchWord.length() >= 3) {
                SearchProductCategoryCall2();


            } else {
                Toast.makeText(SearchResultActivity.this, getResources().getString(R.string.Pleasecharacters), Toast.LENGTH_SHORT).show();
            }

            Log.e("tag", "search_btn1 " + "search_btn1");
        }
        if (view == menu) {
            popupMenu();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        flag_search = dataSaver.getInt("flag_search", 0);
//        if (flag_search == 1) {
//            finishAffinity();
//            startActivity(new Intent(SearchResultActivity.this, MainActivity.class));
//            dataSaver.edit().putInt("flag_search", 0).apply();
//        }
        finish();
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

    private void ApplyFilterFun() {
        ApplyFilter = findViewById(R.id.ApplyFilter);

        ApplyFilter.setOnClickListener(view -> {


            idsCategories = Selection_Categories.selection_categories().getIds();
            idsBrands = Selection_Brands.selection_brands().getIds();
            selection_multi_selected = Selection_Multi_Selected.selection_multi_selected().getIds();

            Log.i("idsCategories ", "idsCategories " + idsCategories);
            Log.i("idsBrands ", "idsBrands " + idsBrands);
            Log.i("selection_multi ", "selection_multi_selected " + selection_multi_selected);
            Log.i("productList", "productList " + new Gson().toJson(productList));
            kProgressHUD.show();
            Call<List<SearchProductCategory>> addEVEnt_call = RetrofitClient.getInstance(SearchResultActivity.this)
                    .PRODUCTS_CALL_FilterSearchfilteResult(SearchWord,
                            Arrays.toString(idsCategories.toArray()),
                            Arrays.toString(idsBrands.toArray()), Double.valueOf(MinPrice), Double.valueOf(MaxPrice),
                            Arrays.toString(selection_multi_selected.toArray()));

            addEVEnt_call.enqueue(new Callback<List<SearchProductCategory>>() {
                @Override
                public void onResponse(@NotNull Call<List<SearchProductCategory>> call, @NotNull Response<List<SearchProductCategory>> response) {
                    kProgressHUD.dismiss();
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        if (response.body().size() != 0) {
                            productList.clear();
                            productList.addAll(response.body());
//                            rv_products.setAdapter(new ProductFromSearchAdapter(SearchResultActivity.this, response.body(), idSubCat));

                            product_recycler_filter.setVisibility(View.VISIBLE);
                            rv_products.setVisibility(View.GONE);
                            productAdapter = new ProductFromSearchAdapter(SearchResultActivity.this, productList, idSubCat, SearchWord,db);
                            product_recycler_filter.setAdapter(productAdapter);
                            drawer.closeDrawer(GravityCompat.END);

                        } else {
                            product_recycler_filter.setVisibility(View.GONE);

                            Toast.makeText(SearchResultActivity.this, getString(R.string.Noitemsmatchyourfilter), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SearchResultActivity.this, "not sccessfull", Toast.LENGTH_SHORT).show();
                        Log.i("dsafsaf", "onResponse: " + response.raw().toString());
                    }
                }

                @Override
                public void onFailure(Call<List<SearchProductCategory>> call, Throwable t) {
                    Log.e("TAG ", "onFailure " + t.getMessage());
                    Toast.makeText(SearchResultActivity.this, "Check internet connection!", Toast.LENGTH_SHORT).show();

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

    public void getFilter() {
        // kProgressHUD.show();
        filterElements.clear();
        brands.clear();
        categoriesList.clear();
        Log.e("TAG", "isSuccessful");
        Log.e("TAG", "category_id " + category_id);
        Log.e("TAG", "isSuccessful" + id);
        kProgressHUD.show();
        Call<FilterModel> addEVEnt_call = RetrofitClient.getInstance(SearchResultActivity.this)
                .getFiletrRefineSearch(SearchWord);
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
                            rv_rectangle.setAdapter(new RectangleAdapter(brands, SearchResultActivity.this));
                        } else {
                            brandsTv.setVisibility(View.GONE);
                            line_brands.setVisibility(View.GONE);
                            rv_rectangle.setVisibility(View.GONE);

                        }
                    }
                    if (response.body().getCategories() != null) {
                        if (response.body().getCategories().size() != 0) {

                            categories.setVisibility(View.VISIBLE);
                            line_categories.setVisibility(View.VISIBLE);
                            categoriesList.addAll(response.body().getCategories());
                            rv_categories.setAdapter(new RectangleCategoriesAdapter(categoriesList, SearchResultActivity.this));
                        } else {
                            categories.setVisibility(View.GONE);
                            line_categories.setVisibility(View.GONE);

                        }
                    }
                if (response.body().getRefineSearch().getFilter().getFilter_elements()!=null&&
                        response.body().getRefineSearch().getFilter().getFilter_elements().size()!=0){

                    filterElements.addAll(response.body().getRefineSearch().getFilter().getFilter_elements());
                    rv_parent_oval.setAdapter(new ParentOvalAdapter(filterElements, SearchResultActivity.this));
                    rv_parent_oval.setVisibility(View.VISIBLE);
                }else {
                    rv_parent_oval.setVisibility(View.GONE);
                }

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
                Toast.makeText(SearchResultActivity.this, "Check internet connection!", Toast.LENGTH_SHORT).show();
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
            Log.e("MaxPrice ", "MaxPrice " + MaxPrice);
            Log.e("MinPrice ", "MinPrice " + MinPrice);
            tvMin.setText(Rails(SearchResultActivity.this, Double.valueOf(MinPrice)));
            tvMax.setText(Rails(SearchResultActivity.this, Double.valueOf(MaxPrice)));

        });
    }
}