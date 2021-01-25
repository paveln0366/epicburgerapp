package com.epicburger.epicburgerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.epicburger.epicburgerapp.burgers.ChickenBurgersFragment;
import com.epicburger.epicburgerapp.burgers.FoodFragment;
import com.google.android.material.tabs.TabLayout;

public class BurgersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burgers);

        // Make toolbar support action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Add up button in toolbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Add view pager
        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        // Bind view pager with tab layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
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
                    //return new ChickenBurgersFragment();
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