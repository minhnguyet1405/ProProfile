package com.guardianofgods.proprofile;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.List;


public class HomeFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapterHome viewPagerAdapterHome;
    ImageView imageView, copy_phone,copy_email;
    TextView tvPhone,tvEmail;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        tabLayout=view.findViewById(R.id.tablayout);
        viewPager2=view.findViewById(R.id.view_pager_home);
        imageView=view.findViewById(R.id.Image_avatar);
        copy_phone=view.findViewById(R.id.copy_phone);
        copy_email=view.findViewById(R.id.copy_email);
        tvPhone=view.findViewById(R.id.text_phone);
        tvEmail=view.findViewById(R.id.text_email);

        copy_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager= (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData=ClipData.newPlainText("Text",tvPhone.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                clipData.getDescription();
                Toast.makeText(getActivity(),"Copied",Toast.LENGTH_SHORT).show();
            }
        });
        copy_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager= (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData=ClipData.newPlainText("Text",tvEmail.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                clipData.getDescription();
                Toast.makeText(getActivity(),"Copied",Toast.LENGTH_SHORT).show();
            }
        });








        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissions();
            }
        });



        viewPagerAdapterHome=new ViewPagerAdapterHome(this);
        viewPager2.setAdapter(viewPagerAdapterHome);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:

                        tab.setIcon(R.drawable.ic_heart);
                        //đổi màu background tablayout
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

    private void requestPermissions() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openImagePicker();

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getActivity(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    private void openImagePicker() {

    }



}