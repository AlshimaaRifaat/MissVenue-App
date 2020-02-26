package com.ibsvalleyn.missvenue.fragments.splash;


import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.activities.SplashScreen;
import com.ibsvalleyn.missvenue.adapters.MywishlistAdapter;
import com.ibsvalleyn.missvenue.models.Wishlists;
import com.ibsvalleyn.missvenue.viewModels.WishlistViewModel;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.ibsvalleyn.missvenue.helper.AppFunctions.StackFragment;
import static com.ibsvalleyn.missvenue.helper.Static_variables.CUSTOMER_ID;

public class SplashLogoFragment extends Fragment {
    private SplashScreen activity;
    private WishlistViewModel model;
    private int id;
    SharedPreferences dataSaver;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (SplashScreen) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.splash_logo_fragment, container, false);
        dataSaver = getDefaultSharedPreferences(activity);

        id = dataSaver.getInt(CUSTOMER_ID, 0);

        Animator.AnimatorListener listener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                if(token.equals("")){
//                    startActivity(new Intent(getApplicationContext(), SigningActivity.class));
//                    finish();
//                }
//                else {
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                finish();
//                }



                model = ViewModelProviders.of(activity).get(WishlistViewModel.class);

        model.getWishlist(id, activity).observe(activity, new Observer<Wishlists>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(@Nullable Wishlists heroList) {
                if (heroList.getItems().size() != 0 && heroList.getItems() != null) {

//                    noItems.setVisibility(View.GONE);
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
                }
            }
        });

                StackFragment(new SplashImageFragment(),activity,R.id.frameLayout_container);

            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        };


        YoYo.with(Techniques.RotateInDownRight)
                .duration(2000)
                .withListener(listener)
                .repeat(0).playOn(view.findViewById(R.id.logo_white));
        return view;
    }


}