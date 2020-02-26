package com.ibsvalleyn.missvenue.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.duolingo.open.rtlviewpager.RtlViewPager;
import com.google.android.material.tabs.TabLayout;
import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.fragments.profile.AddressFragment;
import com.ibsvalleyn.missvenue.fragments.profile.MyOrdersFragment;
import com.ibsvalleyn.missvenue.fragments.profile.ProfileFragment;
import com.ibsvalleyn.missvenue.helper.Static_variables;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import me.anwarshahriar.calligrapher.Calligrapher;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class Navigation_Profile_Fragment extends Fragment {
    private int itemId;
    SharedPreferences dataSaver;
    private String device_id;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_navigation__profile__fragment, container, false);
        dataSaver = getDefaultSharedPreferences(getActivity());
        device_id = dataSaver.getString(Static_variables.DEVICE_ID, "");

        itemId = dataSaver.getInt("item", 0);
        Log.e("item", "item " + itemId);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        RtlViewPager viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        if (itemId == 2) {
            viewPager.setCurrentItem(2);

        } else if (itemId == 1) {
            viewPager.setCurrentItem(3);

        }

        Calligrapher calligrapher = new Calligrapher(getActivity());
        calligrapher.setFont(getActivity(), "Ubuntu-R.ttf", true);
        return view;
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.i("sdsadsad", "getItem: " + position);
            switch (position) {

                case 0:
                    return new SettingFragment();

                case 1:
                    return new MyOrdersFragment();

                case 2:
                    return new AddressFragment();
                case 3:
                    return new ProfileFragment();

            }

            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}