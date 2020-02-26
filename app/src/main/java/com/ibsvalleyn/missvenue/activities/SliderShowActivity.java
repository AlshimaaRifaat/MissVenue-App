package com.ibsvalleyn.missvenue.activities;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Transformers.AccordionTransformer;

import com.github.chrisbanes.photoview.PhotoView;
import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.adapters.ViewPagerAdapter;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class SliderShowActivity extends AppCompatActivity {
    private static final String TAG = "AAQQDDSFFSF";

    private KProgressHUD kProgressHUD;
    private ImageButton backBtn;
    private ViewPager slider;
    private ArrayList<String> temp;
    private ViewPagerAdapter pagerAdapter;
    private static int currentPage = 0;
    ArrayList<String> myList;

    public SliderShowActivity() {
        // Required empty public constructor
    }

    private PhotoView sliderIamge;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.slider_show_activity);
        Intent intent = getIntent();
        myList = (ArrayList<String>) intent.getSerializableExtra("mylist");

        sliderIamge = findViewById(R.id.sliderIamge);
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> finish());

        slider = findViewById(R.id.pager);
        if (myList.size() == 1) {
            slider.setVisibility(View.GONE);
            sliderIamge.setVisibility(View.VISIBLE);
            //String aUrl = myList.get(0).replace("http", "https");

            Picasso.with(this).load(myList.get(0)).into(sliderIamge);
        } else {
            slider.setVisibility(View.VISIBLE);
            sliderIamge.setVisibility(View.GONE);
            pagerAdapter = new ViewPagerAdapter(SliderShowActivity.this, myList);
            slider.setAdapter(pagerAdapter);
            init();
//            slider.setBackground(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            slider.setDuration(3000);
//            slider.setPagerTransformer(false, new AccordionTransformer());
//
//            slider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);

//            for (int i = 0; i < myList.size(); i++) {
////
////
//
//            //    String aUrl = myList.get(i).replace("http", "https");
//
//                DefaultSliderView view2 = new DefaultSliderView(this);
//                view2.image(myList.get(i))
//                        .setScaleType(BaseSliderView.ScaleType.Fit);
//
//                slider.addSlider(view2);
//
//
//            }
        }


    }

    private void init() {
        CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setVisibility(View.VISIBLE);
        indicator.setViewPager(slider);
        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (currentPage == myList.size()) {
                currentPage = 0;
            }
            slider.setCurrentItem(currentPage++, true);
        };
        if (myList.size() == 1) {
            indicator.setVisibility(View.GONE);
        }
    }
}