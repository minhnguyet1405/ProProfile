package com.guardianofgods.proprofile;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class HomeFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapterHome viewPagerAdapterHome;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        tabLayout=view.findViewById(R.id.tablayout);
        viewPager2=view.findViewById(R.id.view_pager_home);
        viewPagerAdapterHome=new ViewPagerAdapterHome(this);
        viewPager2.setAdapter(viewPagerAdapterHome);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:

                        tab.setIcon(R.drawable.ic_heart);
                        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#F5781D"));

                        break;

                    case 1:

                        tab.setIcon(R.drawable.ic_wallet);
                        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#F5781D"));
                        break;
                }
            }
        }).attach();
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });

        return view;
    }

}