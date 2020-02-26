package com.ibsvalleyn.missvenue.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;


public class DynamicFragmentAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    SharedPreferences dataSaver;
    Context context;

    DynamicFragmentAdapter(FragmentManager fm, int NumOfTabs, Context context) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        dataSaver = getDefaultSharedPreferences(context);

        Bundle b = new Bundle();
        b.putInt("position", position);
        Fragment frag = DynamicFragment.newInstance();
        frag.setArguments(b);

        return frag;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
