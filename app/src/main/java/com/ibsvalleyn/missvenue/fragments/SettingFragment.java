package com.ibsvalleyn.missvenue.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.activities.AllActivity;
import com.ibsvalleyn.missvenue.activities.MainActivity;
import com.ibsvalleyn.missvenue.helper.LanSettings;

import static android.content.Context.MODE_PRIVATE;

public class SettingFragment extends Fragment {

    private MainActivity activity;
    RelativeLayout change_language;
    TextView lang;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("onCreate:", "onCreate: ");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.setting_fragment, container, false);
        activity = (MainActivity) getActivity();
        change_language = view.findViewById(R.id.change_language);
        lang = view.findViewById(R.id.lang);
        SharedPreferences pref = activity.getSharedPreferences("lang", MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = activity.getSharedPreferences("lang", MODE_PRIVATE).edit();
        String language = pref.getString("language", "not_Set");

        if (language.equals("en")) {

            lang.setText("Arabic");


        } else if (language.equals("ar")) {
            lang.setText("اللغة الانجليزية");

        }

        change_language.setOnClickListener(view12 -> {

            SharedPreferences pref1 = activity.getSharedPreferences("lang", MODE_PRIVATE);
            SharedPreferences.Editor prefEdit1 = activity.getSharedPreferences("lang", MODE_PRIVATE).edit();
            String language1 = pref1.getString("language", "not_Set");

            if (language1.equals("en")) {
                LanSettings.changeLang("ar", activity);
                prefEdit1.putString("language", "ar");
                prefEdit1.apply();
                lang.setText("Arabic");
                Intent intent = activity.getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                activity.finishAffinity();

            } else if (language1.equals("ar")) {
                lang.setText("اللغة الانجليزية");
                LanSettings.changeLang("en", activity);
                prefEdit1.putString("language", "en");
                prefEdit1.apply();
                Intent intent = activity.getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                activity.finishAffinity();
            }
        });

        view.findViewById(R.id.about_us).setOnClickListener(view1 -> {

                    Intent intent = new Intent(activity, AllActivity.class);
                    intent.putExtra("statics", "AboutUs");
                    startActivity(intent);

                }
        );
        view.findViewById(R.id.terms).setOnClickListener(view1 -> {
                    Intent intent = new Intent(activity, AllActivity.class);
                    intent.putExtra("statics", "TERMS_OF_USE");
                    startActivity(intent);
                }
        );
        view.findViewById(R.id.Privacy).setOnClickListener(view1 -> {
                    Intent intent = new Intent(activity, AllActivity.class);
                    intent.putExtra("statics", "Privacy_Policy");
                    startActivity(intent);
                }
        );

        view.findViewById(R.id.Shipping).setOnClickListener(view1 -> {
                    Intent intent = new Intent(activity, AllActivity.class);
                    intent.putExtra("statics", "Shipping_returns");
                    startActivity(intent);
                }
        );

        return view;
    }


}