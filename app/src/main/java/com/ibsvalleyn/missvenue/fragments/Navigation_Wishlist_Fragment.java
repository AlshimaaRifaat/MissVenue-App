package com.ibsvalleyn.missvenue.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.activities.MainActivity;
import com.ibsvalleyn.missvenue.adapters.MywishlistAdapter;
import com.ibsvalleyn.missvenue.models.FavoriteModel;
import com.ibsvalleyn.missvenue.models.Wishlists;
import com.ibsvalleyn.missvenue.viewModels.WishlistViewModel;

import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.ibsvalleyn.missvenue.helper.Static_variables.CUSTOMER_ID;

public class Navigation_Wishlist_Fragment extends Fragment {
    private static final String TAG = "RegisterAdasdasdctivity";
    private ImageButton backBtn;
    SharedPreferences dataSaver;
    int id;
    private MainActivity activity;
    private String itemId;
    private RecyclerView rv_products;
    private TextView ProductsTV;
    private LinearLayout noItems;
    private TextView go_home;
    private WishlistViewModel model;
    private int size;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.navigation_wishlist_fragment, container, false);
        activity = (MainActivity) getActivity();
        dataSaver = getDefaultSharedPreferences(activity);
        id = dataSaver.getInt(CUSTOMER_ID, 0);
        rv_products = view.findViewById(R.id.rv_my_products);
        noItems = view.findViewById(R.id.no_items);
        rv_products.setLayoutManager(new GridLayoutManager(activity, 2));
        ProductsTV = view.findViewById(R.id.ProductsTV);
        go_home = view.findViewById(R.id.go_home);

        // getWishlist();
        go_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, MainActivity.class));
                activity.finish();
            }
        });
        Calligrapher calligrapher = new Calligrapher(activity);

        calligrapher.setFont(activity, "Ubuntu-R.ttf", true);
        List<FavoriteModel> favoriteModels = activity.getDb().favoriteDao().getAll();

        rv_products.setAdapter(new MywishlistAdapter(favoriteModels, activity, activity.getDb(),ProductsTV));
        // ProductsTV.setText(activity.getDb().favoriteDao().getAll().size()+  " " + activity.getResources().getString(R.string.products));
        ProductsTV.setText(favoriteModels.size() + " " + activity.getResources().getString(R.string.products));

//        model = ViewModelProviders.of(this).get(WishlistViewModel.class);
//
//        model.getWishlist(id, activity).observe(activity, new Observer<Wishlists>() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onChanged(@Nullable Wishlists heroList) {
//                if (heroList.getItems().size() != 0 && heroList.getItems() != null) {
//                    noItems.setVisibility(View.GONE);
//                    rv_products.setAdapter(new MywishlistAdapter(heroList, activity));
//                    size = heroList.getItems().size();
//                    ProductsTV.setText(size + " "+ activity.getResources().getString(R.string.products));
//
//                    if (activity.getNotificationsBadge() != null) {
//                        activity.getNotificationsBadge().setText("" + heroList.getItems().size());
//                    }
//
//                    dataSaver.edit()
//                            .putInt("abdo1", heroList.getItems().size())
//                            .apply();
//
//                } else {
//                    dataSaver.edit()
//                            .putInt("abdo1", 0)
//                            .apply();
//
//                    noItems.setVisibility(View.VISIBLE);
//                    if (activity.getNotificationsBadge() != null) {
//                        activity.getNotificationsBadge().setText("" + 0);
//                    }
//                }
//            }
//        });

        return view;
    }
}