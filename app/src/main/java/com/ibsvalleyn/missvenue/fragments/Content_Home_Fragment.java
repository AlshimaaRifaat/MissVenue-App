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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.duolingo.open.rtlviewpager.RtlViewPager;
import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.activities.MainActivity;
import com.ibsvalleyn.missvenue.activities.SearchResultActivity;
import com.ibsvalleyn.missvenue.adapters.ProductSectorsAdapter;
import com.ibsvalleyn.missvenue.api.RetrofitClient;
import com.ibsvalleyn.missvenue.helper.Static_variables;
import com.ibsvalleyn.missvenue.models.Products;
import com.ibsvalleyn.missvenue.models.Sectors;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.ibsvalleyn.missvenue.helper.AppFunctions;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Color.*;
import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class Content_Home_Fragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "RegisterAdasdasdctivity";
    List<Integer> subcats_id = new ArrayList<>();
    List<String> sectors_name = new ArrayList<>();
    String sector_name;
    private FrameLayout mFrame;
    TextView noData, category_name, category_name1,
            category_name2, category_name3, category_name4, category_nam;
    ImageView main_image, capture3, capture2, capture5, capture6, capture7, banner1, banner2;
    private TabLayout category_tabs;
    LinearLayout container_linear;
    List<Integer> idList = new ArrayList<>();
    SharedPreferences dataSaver;
    RelativeLayout capture5_relative, capture6_relative, capture7_relative;
    int idSubCat, customer_id;
    RecyclerView productRecyclerView;
    GridLayoutManager layoutManager;
    ProductSectorsAdapter productAdapter;
    MainActivity activity;
    private ImageView search;
    private List<Products> products = new ArrayList<>();
    private KProgressHUD kProgressHUD;
    private RtlViewPager viewPager;
    private TabLayout mTabLayout;
    private List<Sectors> body;
    int p = 0;
    private int sectorPosition;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
        kProgressHUD = AppFunctions.getKProgressHUD(activity);
        body = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_home_fragment, container, false);
        dataSaver = getDefaultSharedPreferences(activity);
        p = dataSaver.getInt("position", 0);
        customer_id = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);
        initViews(view);
        search = view.findViewById(R.id.search);
        search.setOnClickListener(view1 -> ShowPopUp(search));
        return view;
    }

    private void initViews(View view) {
        Call<List<Sectors>> addEVEnt_call = RetrofitClient.getInstance(activity)
                .getSectors(1000);

        addEVEnt_call.enqueue(new Callback<List<Sectors>>() {
            @Override
            public void onResponse(Call<List<Sectors>> call, Response<List<Sectors>> response) {
                if (response.isSuccessful()) {
                    Log.e("TAG", "isSuccessful");
                    assert response.body() != null;
                    body.addAll(response.body());
                    setDynamicFragmentToTabLayout();

                } else {
                    Log.e("TAG", "notSuccessful" + new Gson().toJson(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<Sectors>> call, Throwable t) {
                Log.e("TAG ", "onFailure " + t.getMessage());
            }
        });

        viewPager = view.findViewById(R.id.viewpager);
        mTabLayout = view.findViewById(R.id.tabs);
    //    mTabLayout.setRotation(180);
//        viewPager.setOffscreenPageLimit(5);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                sectorPosition = tab.getPosition();
                dataSaver.edit()
                        .putInt("position", tab.getPosition())
                        .apply();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setDynamicFragmentToTabLayout() {
        Log.e("positionSector", "positionSector " + p);
        for (int i = 0; i < body.size(); i++) {

            mTabLayout.addTab(mTabLayout.newTab().setText(body.get(i).getName()));
        }
        DynamicFragmentAdapter mDynamicFragmentAdapter = new DynamicFragmentAdapter(activity.getSupportFragmentManager(), mTabLayout.getTabCount(), activity);
        viewPager.setAdapter(mDynamicFragmentAdapter);
        viewPager.setCurrentItem(p);
    }
//
//    public void getCategories(int i) {
//        Log.i(TAG, "getCategories: " + i);
//
//        Call<List<Categories>> addEVEnt_call = RetrofitClient.getInstance()
//                .getCategories(8, i);
//        addEVEnt_call.enqueue(new Callback<List<Categories>>() {
//            @Override
//            public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
//
//                if (response.isSuccessful()) {
//                    Log.e("TAG", "isSuccessful");
//                    Log.e(TAG, "onResponse2: " + new Gson().toJson(response.body()));
//
//                    idList.clear();
//                    for (int i = 0; i < response.body().size(); i++) {
//                        int id = response.body().get(i).getCategoryId();
//                        idList.add(id);
//                    }
//                    if (response.body().size() != 0) {
//                        container_linear.setVisibility(View.VISIBLE);
//                        noData.setVisibility(View.GONE);
//                        productRecyclerView.setVisibility(View.VISIBLE);
//
//                        if (response.body().get(0) != null) {
//                            category_nam.setText(response.body().get(0).getName());
//
//                            Glide.with(activity).load(response.body().get(0).getPictureurl())
//                                    .into(main_image);
//                        }
//
//                        if (response.body().size() >= 2) {
//                            if (response.body().get(1) != null) {
//                                category_name.setText(response.body().get(1).getName());
//
//                                Glide.with(activity).load(response.body().get(1).getPictureurl()).into(capture2);
//                            }
//                        }
//
//                        if (response.body().size() >= 3) {
//                            if (response.body().get(2) != null) {
//                                category_name1.setText(response.body().get(2).getName());
//                                Glide.with(activity).load(response.body().get(2).getPictureurl()).into(capture3);
//                            }
//                        }
//
//                        if (response.body().size() >= 5) {
//                            if (response.body().get(3) != null) {
//                                category_name2.setText(response.body().get(3).getName());
//                                capture5_relative.setVisibility(View.VISIBLE);
//                                Glide.with(activity).load(response.body().get(3).getPictureurl()).into(capture5);
//                            }
//                        } else {
//                            capture5_relative.setVisibility(View.GONE);
//                        }
//
//                        if (response.body().size() >= 6) {
//                            if (response.body().get(4) != null) {
//                                category_name3.setText(response.body().get(4).getName());
//                                capture6_relative.setVisibility(View.VISIBLE);
//
//                                Glide.with(activity).load(response.body().get(5).getPictureurl()).into(capture6);
//                            }
//                        } else {
//                            capture6_relative.setVisibility(View.GONE);
//                        }
//
//                        if (response.body().size() >= 7) {
//                            if (response.body().get(5) != null) {
//                                category_name4.setText(response.body().get(5).getName());
//                                capture7_relative.setVisibility(View.VISIBLE);
//                                Glide.with(activity).load(response.body().get(5).getPictureurl()).into(capture7);
//                            }
//                        } else {
//                            capture7_relative.setVisibility(View.GONE);
//                        }
//                    } else {
//                        noData.setVisibility(View.VISIBLE);
//                        productRecyclerView.setVisibility(View.GONE);
//                        container_linear.setVisibility(View.GONE);
//                    }
//
//                } else {
//                    Log.e("TAG", "notSuccessful");
//                    Log.e("TAG", "notSuccessful" + new Gson().toJson(response.body()));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Categories>> call, Throwable t) {
//                Log.e("TAG ", "onFailure " + t.getMessage());
//                Toast.makeText(activity, "Check internet connection!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    public void getBanners(int i) {
//        Call<List<Banners>> addEVEnt_call = RetrofitClient.getInstance()
//                .getBanners(i, 1);
//        addEVEnt_call.enqueue(new Callback<List<Banners>>() {
//            @Override
//            public void onResponse(Call<List<Banners>> call, Response<List<Banners>> response) {
//
//                if (response.isSuccessful()) {
//                    Log.e("TAG", "isSuccessful");
//                    Log.e(TAG, "onResponse2: " + new Gson().toJson(response.body()));
//
//                    if (response.body().size() != 0) {
//                        if (response.body().get(0) != null) {
//                            banner1.setVisibility(View.VISIBLE);
//
//                            Glide.with(activity).load(response.body().get(0).getImage()).into(banner1);
//                        }
//
//                    } else {
//                        banner1.setVisibility(View.GONE);
//                    }
//                    if (response.body().size() >= 2) {
//                        if (response.body().get(1) != null) {
//                            banner2.setVisibility(View.VISIBLE);
//
//                            Glide.with(activity).load(response.body().get(1).getImage()).into(banner2);
//                        }
//                    } else {
//                        banner2.setVisibility(View.GONE);
//                    }
//                } else {
//                    Log.e("TAG", "notSuccessful");
//                    Log.e("TAG", "notSuccessful" + new Gson().toJson(response.body()));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Banners>> call, Throwable t) {
//                Log.e("TAG ", "onFailure " + t.getMessage());
//                Toast.makeText(activity, "Check internet connection!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    public void getProduct(int i) {
//        products.clear();
//        kProgressHUD.show();
//
//        productRecyclerView.setVisibility(View.GONE);
//        Log.e("TAG", "isSuccessful");
//        Call<Product_Categories> addEVEnt_call = RetrofitClient.getInstance()
//                .getCategoriesProduct(i, customer_id);
//        addEVEnt_call.enqueue(new Callback<Product_Categories>() {
//
//            @Override
//            public void onResponse(Call<Product_Categories> call, Response<Product_Categories> response) {
//
//                if (response.isSuccessful()) {
//                    Log.e("TAG", "isSuccessful");
//                    products.addAll(response.body().getProducts());
//                    productRecyclerView.setVisibility(View.VISIBLE);
//
//                    productAdapter = new ProductSectorsAdapter(activity, products, Counter);
//                    productRecyclerView.setAdapter(productAdapter);
//                    kProgressHUD.dismiss();
//
//                } else {
//                    Log.e("TAG", "notSuccessful");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Product_Categories> call, Throwable t) {
//                Log.e("TAG ", "onFailure");
//                Toast.makeText(activity, "Check internet connection!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    public void onClick(View view) {

    }

    private void ShowPopUp(View v) {

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        final EditText searchEt;
        final PopupWindow popupWindow;

        LayoutInflater layoutInflater = getLayoutInflater();
        final View popupView = layoutInflater.inflate(R.layout.popup_layout, null);
        popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(
                TRANSPARENT));
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        searchEt = popupView.findViewById(R.id.searchEt);
        searchEt.requestFocus();
       // searchEt.setSelectAllOnFocus(true);
        ImageView search_btn1 = popupView.findViewById(R.id.search_btn1);
        search_btn1.setOnClickListener(view -> {
            String SearchWord = searchEt.getText().toString();
            if (SearchWord.length() >= 3) {
                Intent intent = new Intent(activity, SearchResultActivity.class);
                intent.putExtra("SearchWord", SearchWord);
                intent.putExtra("idSubCat", String.valueOf(idSubCat));
                popupWindow.dismiss();

                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                activity.startActivity(intent);

            } else {
                Toast.makeText(activity, activity.getResources().getString(R.string.Pleasecharacters), Toast.LENGTH_SHORT).show();
            }
        });
        searchEt.setOnEditorActionListener((v1, actionId, event) -> {

            String SearchWord = searchEt.getText().toString();
            if (SearchWord.length() >= 3) {
                Intent intent = new Intent(activity, SearchResultActivity.class);
                intent.putExtra("SearchWord", SearchWord);
                intent.putExtra("idSubCat", String.valueOf(idSubCat));
                popupWindow.dismiss();

                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                activity.startActivity(intent);

            } else {
                Toast.makeText(activity, activity.getResources().getString(R.string.Pleasecharacters), Toast.LENGTH_SHORT).show();
            }
            Log.i("sadasd", "ShowPopUp: " + idSubCat);

            return true;
        });

        popupWindow.showAsDropDown(v);
    }
}