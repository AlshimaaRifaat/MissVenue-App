package com.ibsvalleyn.missvenue.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;


import com.google.firebase.auth.FirebaseUser;
import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.fragments.Sign_IN_Fragment;

public class SigningActivity extends AppCompatActivity {

    private int positionProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signing);
        Intent intent = getIntent();
        positionProfile = intent.getIntExtra("positionProfile", 0);
        StackFragment(new Sign_IN_Fragment());
    }

    public void StackFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_container, fragment)
                .commit();

    }

    public boolean StackFragmentputParcelable(Fragment fragment, int id) {

        if (fragment != null) {
            Bundle bundle = new Bundle();
            bundle.putInt("itemId", id );

            fragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout_container, fragment)
                    .addToBackStack(null).commit();

            return true;
        }
        return false;
    }
    public boolean StackFragmentputParcelableGoole(Fragment fragment, FirebaseUser user) {

        if (fragment != null) {
            Bundle bundle = new Bundle();
            bundle.putString("getEmail",user.getEmail());
            bundle.putString("getDisplayName",user.getDisplayName());
            bundle.putString("getPhoneNumber",user.getPhoneNumber());
            bundle.putString("Type","gmail");

            fragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout_container, fragment)
                    .addToBackStack(null).commit();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {


    if (positionProfile==5){
        startActivity(new Intent(SigningActivity.this,MainActivity.class));
        finish();
    }else {
        super.onBackPressed();
    }
    }
}
