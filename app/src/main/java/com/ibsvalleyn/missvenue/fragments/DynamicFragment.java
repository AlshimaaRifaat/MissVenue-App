package com.ibsvalleyn.missvenue.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Transformers.AccordionTransformer;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.activities.MainActivity;
import com.ibsvalleyn.missvenue.activities.ProductOfCategories;
import com.ibsvalleyn.missvenue.adapters.CategoryAdapter;
import com.ibsvalleyn.missvenue.adapters.ProductSectorsAdapter;
import com.ibsvalleyn.missvenue.api.RetrofitClient;
import com.ibsvalleyn.missvenue.helper.AppFunctions;
import com.bumptech.glide.Glide;
import com.ibsvalleyn.missvenue.helper.Static_variables;
import com.ibsvalleyn.missvenue.models.Banners;
import com.ibsvalleyn.missvenue.models.HomeModel;
import com.ibsvalleyn.missvenue.models.Products;
import com.ibsvalleyn.missvenue.models.Sectors;
import com.ibsvalleyn.missvenue.viewModels.ProductSectorsViewModel;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.ibsvalleyn.missvenue.activities.MainActivity.Counter;

public class DynamicFragment extends Fragment implements View.OnClickListener {
    private TextSliderView textSliderView;
    private MainActivity activity;
    private KProgressHUD kProgressHUD;
    private SharedPreferences dataSaver;
    private int customer_id;
    private static final String TAG = "DynamicFragment";
    List<Integer> subcats_id = new ArrayList<>();
    List<String> sectors_name = new ArrayList<>();
    String sector_name, sector_name1, sector_name2, sector_name3, sector_name4, sector_name5;
    private FrameLayout mFrame;

    TextView noData, category_name, category_name1,
            category_name2, category_name3, category_name4, category_nam;
    ImageView main_image, capture3, capture2, capture5, capture6, capture7, banner2;
    private TabLayout category_tabs;
    LinearLayout container_linear;
    //RelativeLayout banner1;
    ImageView banner1;
    List<Integer> idList = new ArrayList<>();
    RelativeLayout capture5_relative, capture7_relative;
    int idSubCat;
    LinearLayout capture6_relative;
    RecyclerView productRecyclerView;
    GridLayoutManager layoutManager;
    ProductSectorsAdapter productAdapter;
    RelativeLayout relative1, relative2;
    private ImageView search;
    private List<Products> products = new ArrayList<>();
    private ViewPager viewPager;
    private TabLayout mTabLayout;
    private int sector_id;
    private ArrayList<Sectors> body = new ArrayList<>();
    private SliderLayout slider;
    ProductSectorsViewModel model;
    PagerIndicator custom_indicator;
    private RelativeLayout relativeLayout_slider;
    private ImageView main_imageslider;
    private TextView category_namslider;
    ProgressBar progressBar;
    private int pageNumber = 1;
    boolean isLoading = true;
    int pastVisibleItem = 0, visibleItemCount = 0, totalItemCount = 0, previousTotal = 0;
    int viewThreshold = 10;
    NestedScrollView nested_scroll_view;
    private RecyclerView rvCategory;
    private GridLayoutManager layoutManager1, layoutManager2;
    private RelativeLayout main_image_banner;
    private int count;

    public static DynamicFragment newInstance() {
        return new DynamicFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
        kProgressHUD = AppFunctions.getKProgressHUD(activity);
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dynamic_fragment_layout, container, false);
        dataSaver = getDefaultSharedPreferences(activity);
        customer_id = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);
//        activity.runOnUiThread(() -> sector_id = dataSaver.getInt(Static_variables.Sector_id, 0));
        Log.e("TAG", "Sector_id " + sector_id);
        initViews(view);
        Calligrapher calligrapher = new Calligrapher(activity);
        calligrapher.setFont(activity, "Ubuntu-R.ttf", true);
        slider = view.findViewById(R.id.slider);
        slider.setBackground(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        slider.setDuration(4000);
        slider.setPagerTransformer(false, new AccordionTransformer());
        slider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
        slider.setCustomIndicator(view.findViewById(R.id.custom_indicator));
        model = ViewModelProviders.of(this).get(ProductSectorsViewModel.class);
        model.setContext(activity);
        return view;
    }

    private void initViews(View view) {
        main_image_banner = view.findViewById(R.id.main_image_banner);
        category_name = view.findViewById(R.id.category_name);
        category_name1 = view.findViewById(R.id.category_name1);
        category_name2 = view.findViewById(R.id.category_name2);
        category_name3 = view.findViewById(R.id.category_name3);
        category_name4 = view.findViewById(R.id.category_name4);
        relativeLayout_slider = view.findViewById(R.id.relativeLayout_slider);
        main_imageslider = view.findViewById(R.id.main_imageslider);
        category_namslider = view.findViewById(R.id.category_namslider);
        relative1 = view.findViewById(R.id.relative1);
        relative2 = view.findViewById(R.id.relative2);
        category_nam = view.findViewById(R.id.category_nam);
        layoutManager = new GridLayoutManager(activity, 2);
        productRecyclerView = view.findViewById(R.id.product_recycler);
        productRecyclerView.setLayoutManager(layoutManager);
        main_image = view.findViewById(R.id.main_image);
        capture2 = view.findViewById(R.id.capture2);
        capture3 = view.findViewById(R.id.capture3);
        banner1 = view.findViewById(R.id.banner1);
        banner2 = view.findViewById(R.id.banner2);
        capture5 = view.findViewById(R.id.capture5);
        capture6 = view.findViewById(R.id.capture6);
        capture7 = view.findViewById(R.id.capture7);
        progressBar = view.findViewById(R.id.progressBar);
        nested_scroll_view = view.findViewById(R.id.nested_scroll_view);

        capture5_relative = view.findViewById(R.id.capture5_relative);
        capture6_relative = view.findViewById(R.id.capture6_relative);
        capture7_relative = view.findViewById(R.id.capture7_relative);
        container_linear = view.findViewById(R.id.container_linear);
        rvCategory = view.findViewById(R.id.rvCategory);
//        layoutManager1 = new GridLayoutManager(activity, 3);
//        rvCategory.setLayoutManager(layoutManager1);

//        main_image.setOnClickListener(this);
//        capture2.setOnClickListener(this);
//        capture3.setOnClickListener(this);
//        capture5.setOnClickListener(this);
//        capture6.setOnClickListener(this);
//        capture7.setOnClickListener(this);

//        banner1.setOnClickListener(view1 -> {
//            activity.getmBottomNavigation().setSelectedItemId(R.id.navigation_offers);
//            dataSaver.edit()
//                    .putString("offers", "")
//                    .apply();
//
//        });

        Call<List<Sectors>> addEVEnt_call = RetrofitClient.getInstance(activity)
                .getSectors(1000);
        productRecyclerView.setNestedScrollingEnabled(false);

        addEVEnt_call.enqueue(new Callback<List<Sectors>>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<List<Sectors>> call, Response<List<Sectors>> response) {
                if (response.isSuccessful()) {
                    Log.e("TAG", "isSuccessful");

                    assert response.body() != null;
                    body.clear();
                    body.addAll(response.body());
                    int position = body.get(getArguments().getInt("position")).getCategoryId();
                    Log.i("sdfdsgdsg", "" + position + "  " + customer_id + "  ");
                   // ViewCompat.setNestedScrollingEnabled(productRecyclerView, false);

                    model.getProducts(100, position, pageNumber, 6).observe(activity, heroList -> {
                        getBanners(position);
                        productRecyclerView.setVisibility(View.VISIBLE);

                        productAdapter = new ProductSectorsAdapter(activity, heroList.getProducts(), Counter, activity.getDb());
                        getCategories(heroList);
                        productRecyclerView.setAdapter(productAdapter);
                        if (heroList.getCategoriesList().size() < 6) {
                            count = 6;
                        } else {
                            count = 2;
                        }
                        Log.e("TAG", "notSuccessful" + new Gson().toJson(heroList.getCategoriesList()));
//                        productRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                            @Override
//                            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
//                                super.onScrolled(recyclerView, dx, dy);
//                                visibleItemCount = layoutManager.getChildCount();
//                                totalItemCount = layoutManager.getItemCount();
//                                pastVisibleItem = layoutManager.findFirstVisibleItemPosition();
//                                Log.e("TAG", "dy " + dy);
//                                // dy = dy+1;
//                                if (dy > 0) {
//                                    if (isLoading) {
//                                        if (totalItemCount > previousTotal) {
//                                            isLoading = false;
//                                            previousTotal = totalItemCount;
//                                        }
//                                    }
//                                    if (!isLoading && (totalItemCount - visibleItemCount) <= (pastVisibleItem + viewThreshold)) {
//                                        Log.e("TAG", "performPagination()");
//
//                                        pageNumber++;
//                                        model.getProducts(100, position, pageNumber, 6).observe(activity, heroList1 -> {
//                                            productAdapter.addInRecycle(heroList1.getProducts());
//
//                                        });
//                                        isLoading = true;
//
//
//                                        Log.e("TAG", "pageNumber " + pageNumber);
//
//                                    }
//                                }
//                            }
//                        });
//
//                    });

                        nested_scroll_view.setOnScrollChangeListener(((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                            visibleItemCount = layoutManager.getChildCount();
                            totalItemCount = layoutManager.getItemCount();
                            pastVisibleItem = layoutManager.findFirstVisibleItemPosition();
                            Log.e("TAG", "dy " + scrollY);
                            Log.e("TAG", "dy " + oldScrollY);
                            // dy = dy+1;
                            if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {

                                if (isLoading) {
                                    if (totalItemCount > previousTotal) {
                                        isLoading = false;
                                        previousTotal = totalItemCount;

                                    }
                                }
                                if (!isLoading && (totalItemCount - visibleItemCount) <= (pastVisibleItem + viewThreshold)) {
                                    Log.e("TAG", "performPagination()");
                                    heroList.getCategoriesList().clear();

                                    pageNumber++;
                                    model.getProducts(100, position, pageNumber, count).observe(activity, heroList1 -> {
                                        productAdapter.addInRecycle(heroList1.getProducts());

                                    });
                                    isLoading = true;
                                    Log.e("TAG", "pageNumber " + pageNumber);
                                }
                            }
                        }
                        ));
                    });
                } else {
                    Log.e("TAG", "notSuccessful");
                    Log.e("TAG", "notSuccessful" + new Gson().toJson(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<Sectors>> call, Throwable t) {
                Log.e("TAG ", "onFailure " + t.getMessage());
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        if (view == main_image) {
            if (idList.size() >= 1) {
                Intent intent = new Intent(activity, ProductOfCategories.class);
                intent.putExtra("id", idList.get(0));
                //intent.putExtra("sectors_name", sector_name);
                dataSaver.edit()
                        .putInt(Static_variables.Category_id, idList.get(0))
                        .apply();

                dataSaver.edit()
                        .putString("sectors_name", sector_name)
                        .apply();

                dataSaver.edit()
                        .putInt(Static_variables.Sector_id, idSubCat)
                        .apply();

                startActivity(intent);
                activity.finish();
            }
        }
        if (view == capture2) {
            if (idList.size() >= 2) {
                Intent intent = new Intent(activity, ProductOfCategories.class);
                intent.putExtra("id", idList.get(1));

                dataSaver.edit()
                        .putInt(Static_variables.Category_id, idList.get(1))
                        .apply();

                dataSaver.edit()
                        .putString("sectors_name", sector_name1)
                        .apply();

                dataSaver.edit()
                        .putInt(Static_variables.Sector_id, idSubCat)
                        .apply();

                startActivity(intent);
                activity.finish();

            }
        }
        if (view == capture3) {
            Intent intent = new Intent(activity, ProductOfCategories.class);
            if (idList.size() >= 3) {
                intent.putExtra("id", idList.get(2));

                dataSaver.edit()
                        .putInt(Static_variables.Category_id, idList.get(2))
                        .apply();

                dataSaver.edit()
                        .putString("sectors_name", sector_name2)
                        .apply();

                dataSaver.edit()
                        .putInt(Static_variables.Sector_id, idSubCat)
                        .apply();

                startActivity(intent);
                activity.finish();

            }
        }

        if (view == capture5) {
            if (idList.size() >= 4) {
                Intent intent = new Intent(activity, ProductOfCategories.class);
                intent.putExtra("id", idList.get(3));

                dataSaver.edit()
                        .putInt(Static_variables.Category_id, idList.get(3))
                        .apply();

                dataSaver.edit()
                        .putString("sectors_name", sector_name3)
                        .apply();

                dataSaver.edit()
                        .putInt(Static_variables.Sector_id, idSubCat)
                        .apply();

                startActivity(intent);
                activity.finish();

            }
        }
        if (view == capture6) {
            if (idList.size() >= 5) {
                Intent intent = new Intent(activity, ProductOfCategories.class);
                intent.putExtra("id", idList.get(4));
                intent.putExtra("id", "");

                dataSaver.edit()
                        .putInt(Static_variables.Category_id, idList.get(4))
                        .apply();

                dataSaver.edit()
                        .putString("sectors_name", sector_name4)
                        .apply();

                dataSaver.edit()
                        .putInt(Static_variables.Sector_id, idSubCat)
                        .apply();

                startActivity(intent);
                activity.finish();

            }
        }
        if (view == capture7) {
            if (idList.size() >= 6) {
                Intent intent = new Intent(activity, ProductOfCategories.class);
                intent.putExtra("id", idList.get(5));

                dataSaver.edit()
                        .putInt(Static_variables.Category_id, idList.get(5))
                        .apply();
                dataSaver.edit()
                        .putString("sectors_name", sector_name5)
                        .apply();
                dataSaver.edit()
                        .putInt(Static_variables.Sector_id, idSubCat)
                        .apply();

                startActivity(intent);
                activity.finish();

            }
        }
    }

    public void getCategories(HomeModel homeModel) {

//        idList.clear();
//        Log.e("TAG", "homeModel.getCategoriesList() " + new Gson().toJson(homeModel.getCategoriesList()));
//        if (homeModel.getCategoriesList().size() < 4) {
//            slider.setVisibility(View.GONE);
//
//
//        } else {
//            if (homeModel.getCategoriesList().size() == 4) {
//                slider.setVisibility(View.GONE);
//                relativeLayout_slider.setOnClickListener(view -> {
//                    Intent intent = new Intent(activity, ProductOfCategories.class);
//
//                    dataSaver.edit()
//                            .putInt(Static_variables.Category_id, homeModel.getCategoriesList().get(3).getCategoryId())
//                            .apply();
//
//                    dataSaver.edit()
//                            .putString("sectors_name", homeModel.getCategoriesList().get(3).getName())
//                            .apply();
//                    startActivity(intent);
//                });
//
//
//                relativeLayout_slider.setVisibility(View.VISIBLE);
//                Glide.with(getApplicationContext()).load(homeModel.getCategoriesList().get(3).getPictureurl()).into(main_imageslider);
//
//
//                category_namslider.setText(homeModel.getCategoriesList().get(3).getName());
//
//            } else {
//                slider.setVisibility(View.VISIBLE);
//                for (int i = 3; i < homeModel.getCategoriesList().size(); i++) {
//
//                    TextSliderView demoSlider = new TextSliderView(activity);
//                    int categoryId = homeModel.getCategoriesList().get(i).getCategoryId();
//                    String name = homeModel.getCategoriesList().get(i).getName();
//                    if (!homeModel.getCategoriesList().get(i).getPictureurl().equals("")) {
//                        // String aUrl = homeModel.getCategoriesList().get(i).getPictureurl().replace("http", "https");
//
//                        // demoSlider.getView().findViewById(SliderLayout.ALIGN_END);
//                        demoSlider.description(homeModel.getCategoriesList().get(i).getName())
//                                .image(homeModel.getCategoriesList().get(i).getPictureurl())
//                                .setOnSliderClickListener(slider1 -> {
//                                    Intent intent = new Intent(activity, ProductOfCategories.class);
//
//                                    dataSaver.edit()
//                                            .putInt(Static_variables.Category_id, categoryId)
//                                            .apply();
//
//                                    dataSaver.edit()
//                                            .putString("sectors_name", name)
//                                            .apply();
//                                    startActivity(intent);
//
//                                })
//                                .setScaleType(BaseSliderView.ScaleType.Fit);
//                    }
//                    slider.addSlider(demoSlider);
//                }
//            }
//        }

//        for (int i = 0; i < homeModel.getCategoriesList().size(); i++) {
//            int id = homeModel.getCategoriesList().get(i).getCategoryId();
//            idList.add(id);
//        }
//        if (homeModel.getCategoriesList().size() != 0) {
//            container_linear.setVisibility(View.VISIBLE);
//            productRecyclerView.setVisibility(View.VISIBLE);
//
//            if (homeModel.getCategoriesList().get(0) != null) {
//                category_nam.setText(homeModel.getCategoriesList().get(0).getName());
//                sector_name = homeModel.getCategoriesList().get(0).getName();
//
//                Glide.with(getApplicationContext()).load(homeModel.getCategoriesList().get(0).getPictureurl())
//                        .into(main_image);
//            }
//            List<Categories> categories = new ArrayList<>();
//            if (homeModel.getCategoriesList().size() >= 2) {
//                for (int i = 1; i < homeModel.getCategoriesList().size(); i++) {
//                    categories.add(homeModel.getCategoriesList().get(i));
//                }
//
//            }
        layoutManager1 = new GridLayoutManager(activity, 3);
        layoutManager2 = new GridLayoutManager(activity, 2);
        if (homeModel.getCategoriesList().size() == 2) {
            rvCategory.setLayoutManager(layoutManager2);

        } else {
            rvCategory.setLayoutManager(layoutManager1);

        }
        rvCategory.setAdapter(new CategoryAdapter(activity, homeModel.getCategoriesList()));


//                if (homeModel.getCategoriesList().get(1) != null) {
//                    sector_name1 = homeModel.getCategoriesList().get(1).getName();
//                    relative1.setVisibility(View.VISIBLE);
//
//                    category_name.setText(homeModel.getCategoriesList().get(1).getName());
//                    Glide.with(getApplicationContext()).load(homeModel.getCategoriesList().get(1).getPictureurl()).into(capture2);
//                }
//            } else {
//                relative1.setVisibility(View.GONE);
//            }
//
//            if (homeModel.getCategoriesList().size() >= 3) {
//                if (homeModel.getCategoriesList().get(2) != null) {
//                    sector_name2 = homeModel.getCategoriesList().get(2).getName();
//                    relative2.setVisibility(View.VISIBLE);
//
//                    category_name1.setText(homeModel.getCategoriesList().get(2).getName());
//                    Log.i(TAG, "onResponse: " + homeModel.getCategoriesList().get(2).getPictureurl());
//                    Glide.with(getApplicationContext()).load(homeModel.getCategoriesList().get(2).getPictureurl()).into(capture3);
//                }
//            } else {
//                relative2.setVisibility(View.GONE);
//            }

//                        if (response.body().size() >= 5) {
//                            if (response.body().get(3) != null) {
//                                sector_name3 = response.body().get(3).getName();
//
//                                category_name2.setText(response.body().get(3).getName());
//                                capture5_relative.setVisibility(View.VISIBLE);
//
//                                Glide.with(getApplicationContext()).load(response.body().get(3).getPictureurl()).into(capture5);
//                            }
//                        } else {
//                            capture5_relative.setVisibility(View.GONE);
//                        }
//
//                        if (response.body().size() >= 6) {
//                            if (response.body().get(4) != null) {
//                                category_name3.setText(response.body().get(4).getName());
//                                capture6_relative.setVisibility(View.VISIBLE);
//                                sector_name4 = response.body().get(4).getName();
//
//                                Glide.with(getApplicationContext()).load(response.body().get(5).getPictureurl()).into(capture6);
//                            }
//                        } else {
//                            capture6_relative.setVisibility(View.GONE);
//                        }
//
//                        if (response.body().size() >= 7) {
//                            if (response.body().get(5) != null) {
//                                category_name4.setText(response.body().get(5).getName());
//                                capture7_relative.setVisibility(View.VISIBLE);
//                                sector_name5 = response.body().get(5).getName();
//
//                                Glide.with(getApplicationContext()).load(response.body().get(5).getPictureurl()).into(capture7);
//                            }
//                        } else {
//                            capture7_relative.setVisibility(View.GONE);
//                        }

//        }
    }

    private void getBanners(int i) {

        Call<Banners> addEVEnt_call = RetrofitClient.getInstance(activity)
                .getBanners(i, 1);
        addEVEnt_call.enqueue(new Callback<Banners>() {
            @Override
            public void onResponse(Call<Banners> call, Response<Banners> response) {

                if (response.isSuccessful()) {
                    Log.e("TAG", "isSuccessful");
                    Log.e(TAG, "onResponse2KKKKKKKKKKk: " + new Gson().toJson(response.body()));

                    assert response.body() != null;
                    if (response.body().getPromoBanner() != null) {
                        banner1.setVisibility(View.VISIBLE);
                        banner1.setOnClickListener(view -> {
                            activity.getmBottomNavigation().setSelectedItemId(R.id.navigation_offers);
                            dataSaver.edit()
                                    .putString("offers", response.body().getPromoBanner().getBannerType())
                                    .apply();

                        });

                        Glide.with(getApplicationContext()).load(response.body().getPromoBanner().getPictureUrl()).into(banner1);
                    } else {
                        banner1.setVisibility(View.GONE);
                    }
                    if (response.body().getMainBanners().size() != 0) {
                        if (response.body().getMainBanners().size() == 1) {
                            main_image_banner.setVisibility(View.VISIBLE);
                            Glide.with(getApplicationContext()).load(response.body().getMainBanners().get(0).getPictureUrl()).into(main_image);
                            main_image.setOnClickListener(view -> {

                                activity.getmBottomNavigation().setSelectedItemId(R.id.navigation_offers);
                                dataSaver.edit()
                                        .putString("offers", response.body().getMainBanners().get(0).getMetaTitle())
                                        .apply();

                            });

                        } else {

                            main_image_banner.setVisibility(View.GONE);

                            slider.setVisibility(View.VISIBLE);
                            for (int i = 0; i < response.body().getMainBanners().size(); i++) {

                                TextSliderView demoSlider = new TextSliderView(activity);
                                int categoryId = response.body().getMainBanners().get(i).getBannerId();
                                // String aUrl = homeModel.getCategoriesList().get(i).getPictureurl().replace("http", "https");

                                // demoSlider.getView().findViewById(SliderLayout.ALIGN_END);
                                int finalI = i;
                                demoSlider
                                        .image(response.body().getMainBanners().get(i).getPictureUrl())
                                        .setOnSliderClickListener(slider1 -> {
                                            activity.getmBottomNavigation().setSelectedItemId(R.id.navigation_offers);
                                            dataSaver.edit()
                                                    .putString("offers", response.body().getMainBanners().get(finalI).getMetaTitle())
                                                    .apply();

                                        })
                                        .setScaleType(BaseSliderView.ScaleType.Fit);

                                slider.addSlider(demoSlider);
                            }
                        }


                    } else {
                        main_image_banner.setVisibility(View.GONE);
                        slider.setVisibility(View.GONE);
                    }

                    if (response.body().getSubBanner() != null) {
                        banner2.setVisibility(View.VISIBLE);

                        banner2.setOnClickListener(view -> {

                            activity.getmBottomNavigation().setSelectedItemId(R.id.navigation_offers);
                            dataSaver.edit()
                                    .putString("offers", response.body().getSubBanner().getMetaTitle())
                                    .apply();
                        });
                        Glide.with(getApplicationContext()).load(response.body().getSubBanner().getPictureUrl()).into(banner2);

                    } else {
                        banner2.setVisibility(View.GONE);
                    }
                } else {
                    Log.e("TAG", "notSuccessful");
                    Log.e("TAG", "notSuccessful" + new Gson().toJson(response.body()));
                }
            }

            @Override
            public void onFailure(Call<Banners> call, Throwable t) {
                Log.e("TAG ", "onFailure " + t.getMessage());
                //  Toast.makeText(activity, "Check internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}