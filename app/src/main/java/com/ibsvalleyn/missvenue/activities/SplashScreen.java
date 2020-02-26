package com.ibsvalleyn.missvenue.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.fragments.splash.SplashImageFragment;
import com.ibsvalleyn.missvenue.helper.LanSettings;

import static com.ibsvalleyn.missvenue.helper.AppFunctions.NoStackFragment;


public class SplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        SharedPreferences pref = getSharedPreferences("lang", MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = getSharedPreferences("lang", MODE_PRIVATE).edit();
        String language = pref.getString("language", "en");

        if (language.equals("en")) {
            prefEdit.putString("language", "en");
            prefEdit.apply();
        } else {
            prefEdit.putString("language", "ar");
            prefEdit.apply();
        }
        LanSettings.changeLang(language, this);

        NoStackFragment(new SplashImageFragment(),this,R.id.frameLayout_container);
    }


}
