package com.ibsvalleyn.missvenue.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.helper.AppFunctions;
import com.ibsvalleyn.missvenue.models.StaticsModel;
import com.ibsvalleyn.missvenue.viewModels.StaticsViewModel;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class AllActivity extends AppCompatActivity {

    private TextView notification_counter;
    private SharedPreferences dataSaver;
    int quantaty;
    private ConstraintLayout bage;
    private WebView content;
    String statics = "AboutUs";
    StaticsViewModel model;
    ImageView static_image;
    private TextView ProductName;
    String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref = getSharedPreferences("lang", MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = getSharedPreferences("lang", MODE_PRIVATE).edit();
        String language = pref.getString("language", "en");
        if (language.equals("en")) {
            lang = "ltr";
        } else {
            lang = "rtl";

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);

        static_image = findViewById(R.id.static_image);
        ProductName = findViewById(R.id.ProductName);

        Intent intent = getIntent();
        statics = intent.getStringExtra("statics");
        findViewById(R.id.back).setOnClickListener(view -> finish());

        dataSaver = getDefaultSharedPreferences(this);

        notification_counter = findViewById(R.id.notification_counter);
        content = findViewById(R.id.content);
        bage = findViewById(R.id.bage);
        quantaty = dataSaver.getInt("abdo", 0);

        notification_counter.setText(String.valueOf(quantaty));
        bage.setOnClickListener(view -> {

            Intent intent1 = new Intent(this, MainActivity.class);
            intent1.putExtra("cart", 2);
            startActivity(intent1);

        });
        //content.setMovementMethod(new ScrollingMovementMethod());
        if (statics.equals("Privacy_Policy")) {
            ProductName.setText(getString(R.string.privacy_policies));

        } else if (statics.equals("Shipping_returns")) {

            ProductName.setText(getString(R.string.shipping_amp_returns));
        } else if (statics.equals("TERMS_OF_USE")) {
            ProductName.setText(getString(R.string.terms_and_conditions));
        } else if (statics.equals("AboutUs")) {
            ProductName.setText(getString(R.string.about_us));
        }
        model = ViewModelProviders.of(this).get(StaticsViewModel.class);
        model.setContext(this);
        model.getStatics(statics).observe(this, new Observer<StaticsModel>() {
            @Override
            public void onChanged(@Nullable StaticsModel heroList) {
                Glide.with(AllActivity.this).load(heroList.getImage_url()).into(static_image);
                //  content.setText(heroList.getBody());
                AppFunctions.stringToWebView(content, heroList.getBody(), AllActivity.this, lang);
            }

        });

    }
}