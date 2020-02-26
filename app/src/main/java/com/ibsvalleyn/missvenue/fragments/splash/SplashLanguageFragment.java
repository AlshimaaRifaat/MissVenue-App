package com.ibsvalleyn.missvenue.fragments.splash;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.activities.MainActivity;
import com.ibsvalleyn.missvenue.activities.SplashScreen;
import com.ibsvalleyn.missvenue.helper.LanSettings;

import static android.content.Context.MODE_PRIVATE;

public class SplashLanguageFragment extends Fragment {
    private SplashScreen activity;
   // SharedPreferences dataSaver;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (SplashScreen) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.splash_logo_2fragment, container, false);
//        dataSaver = getDefaultSharedPreferences(activity);
//        dataSaver.edit()
//                .putString(Static_variables.Lang, "langSplash")
//                .apply();
        Intent intent = new Intent(activity, MainActivity.class);
        SharedPreferences pref1 = activity.getSharedPreferences("lang", MODE_PRIVATE);
        SharedPreferences.Editor prefEdit1 = activity.getSharedPreferences("lang", MODE_PRIVATE).edit();
        String language1 = pref1.getString("language", "not_Set");


        view.findViewById(R.id.enTv).setOnClickListener(view1 -> {
            LanSettings.changeLang("en", activity);
            prefEdit1.putString("language", "en");
            prefEdit1.apply();
            startActivity(intent);
            activity.finishAffinity();

        });
        view.findViewById(R.id.arTv).setOnClickListener(view1 -> {
            LanSettings.changeLang("ar", activity);
            prefEdit1.putString("language", "ar");
            prefEdit1.apply();
            startActivity(intent);
            activity.finishAffinity();

        });


        return view;

    }


//        change_language.setOnClickListener(view12 -> {
//
//
//        if (language1.equals("en")) {
//            LanSettings.changeLang("ar", activity);
//            prefEdit1.putString("language", "ar");
//            prefEdit1.apply();
//            lang.setText("Arabic");
//            Intent intent = activity.getIntent();
//            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//            startActivity(intent);
//            activity.finishAffinity();
//
//        } else if (language1.equals("ar")) {
//            lang.setText("اللغة الانجليزية");
//            LanSettings.changeLang("en", activity);
//            prefEdit1.putString("language", "en");
//            prefEdit1.apply();
//            Intent intent = activity.getIntent();
//            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//            startActivity(intent);
//            activity.finishAffinity();
//        }
//    });


}