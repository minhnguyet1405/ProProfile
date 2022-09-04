package com.guardianofgods.proprofile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new HomeFragment();

            case 1:
                return new NotificationFragment();

            case 2:
                return new CreateFragment();

            case 3:
                return new AccountFragment();

            case 4:
                return new SettingFragment();

            default:
                return new HomeFragment();
        }


    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
