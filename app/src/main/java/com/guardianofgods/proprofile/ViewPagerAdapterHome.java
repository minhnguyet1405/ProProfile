package com.guardianofgods.proprofile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapterHome extends FragmentStateAdapter {


    public ViewPagerAdapterHome(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new FavoriteFragment();

            case 1:
                return new WalletFragment();

            default:
                return new FavoriteFragment();

        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
