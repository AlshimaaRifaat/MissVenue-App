package com.ibsvalleyn.missvenue.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.activities.MainActivity;
import com.ibsvalleyn.missvenue.adapters.OffersAdapter;
import com.ibsvalleyn.missvenue.helper.Static_variables;
import com.ibsvalleyn.missvenue.viewModels.OffersViewMetaModel;
import com.ibsvalleyn.missvenue.viewModels.OffersViewModel;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class Navigation_Offers_Fragment extends Fragment {
    RecyclerView offersRecyclerView;
    GridLayoutManager layoutManager;
    OffersAdapter offersAdapter;
    MainActivity activity;
    private SharedPreferences dataSaver;
    private int CUSTOMER_id;
    int total_quantity;
    private OffersViewModel model;
    private OffersViewMetaModel metaModel;
    private String offers;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.offers_fragment_layout, container, false);
        activity = (MainActivity) getActivity();
        dataSaver = getDefaultSharedPreferences(activity);
        total_quantity = dataSaver.getInt("abdo", 0);
        offers = dataSaver.getString("offers", "");
        CUSTOMER_id = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);
        offersRecyclerView = view.findViewById(R.id.offers_recycler);
        layoutManager = new GridLayoutManager(activity, 2);
        offersRecyclerView.setLayoutManager(layoutManager);
        Log.e("offers","offersmeta "+offers);
        if (!offers.equals("")) {

            metaModel = ViewModelProviders.of(this).get(OffersViewMetaModel.class);
            metaModel.setContext(activity);
            metaModel.getOffers(offers).observe(activity, heroList -> {
                offersAdapter = new OffersAdapter(activity, heroList,activity.getDb());
                offersRecyclerView.setAdapter(offersAdapter);
                dataSaver.edit()
                        .putString("offers", "")
                        .apply();
                if (activity.getNotificationsBadge1() != null) {
                    activity.getNotificationsBadge1().setText("" + total_quantity);
                }
            });

        } else {

            model = ViewModelProviders.of(this).get(OffersViewModel.class);
            model.setContext(activity);
            model.getOffers().observe(activity, heroList -> {
                offersAdapter = new OffersAdapter(activity, heroList,activity.getDb());
                offersRecyclerView.setAdapter(offersAdapter);

                if (activity.getNotificationsBadge1() != null) {
                    activity.getNotificationsBadge1().setText("" + total_quantity);
                }

            });
        }

        return view;
    }
}