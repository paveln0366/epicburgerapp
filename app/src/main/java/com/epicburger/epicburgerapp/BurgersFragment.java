package com.epicburger.epicburgerapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epicburger.epicburgerapp.burgers.FoodFragment;
import com.epicburger.epicburgerapp.burgers.ChickenBurgersFragment;
import com.google.android.material.tabs.TabLayout;

public class BurgersFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_burgers, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Add view pager
        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        ViewPager pager = (ViewPager) getView().findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        // Bind view pager with tab layout
        TabLayout tabLayout = (TabLayout) getView().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FoodFragment("CHEESEBURGERS");
                case 1:
                    return new FoodFragment("CHICKENBURGERS");
//                    return new ChickenBurgersFragment();
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getText(R.string.title_cheeseburgers);
                case 1:
                    return getResources().getText(R.string.title_chickenburgers);
            }
            return null;
        }
    }
}