package com.guardianofgods.proprofile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore firestore;

    BottomNavigationView navigationView;
    ViewPager2 viewPager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bottom navigation
        navigationView=findViewById(R.id.bottom_navigation);
        viewPager2=findViewById(R.id.viewPager);
        setupViewPager();
        
//        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.action_home:
//                        viewPager.setCurrentItem(0);
//                        break;
//
//                    case R.id.action_notification:
//                        viewPager.setCurrentItem(1);
//                        break;
//
//                    case R.id.action_create:
//                        viewPager.setCurrentItem(2);
//                        break;
//
//                    case R.id.action_account:
//                        viewPager.setCurrentItem(3);
//                        break;
//
//                    case R.id.action_setting:
//                        viewPager.setCurrentItem(4);
//                        break;
//                }
//                return true;
//            }
//        });
            navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id=item.getItemId();
                    if(id==R.id.action_home){
                        viewPager2.setCurrentItem(0);
                    }else if(id==R.id.action_notification){
                        viewPager2.setCurrentItem(1);
                    }else if(id==R.id.action_create){
                        viewPager2.setCurrentItem(2);
                    }else if(id==R.id.action_account){
                        viewPager2.setCurrentItem(3);
                    }else if(id==R.id.action_setting){
                        viewPager2.setCurrentItem(4);
                    }
                    return true;
                }
            });


    }
    private void setupViewPager(){
        ViewPagerAdapter viewPagerAdapter= new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);
        //vuot ngang dung ViewPager2
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        navigationView.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;

                    case 1:
                        navigationView.getMenu().findItem(R.id.action_notification).setChecked(true);
                        break;

                    case 2:
                        navigationView.getMenu().findItem(R.id.action_create).setChecked(true);
                        break;

                    case 3:
                        navigationView.getMenu().findItem(R.id.action_account).setChecked(true);
                        break;

                    case 4:
                        navigationView.getMenu().findItem(R.id.action_setting).setChecked(true);
                        break;
                }
            }
        });


        //chuyeenr page vuot ngang dung ViewPager
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                switch (position){
//                    case 0:
//                        navigationView.getMenu().findItem(R.id.action_home).setChecked(true);
//                        break;
//
//                    case 1:
//                        navigationView.getMenu().findItem(R.id.action_notification).setChecked(true);
//                        break;
//
//                    case 2:
//                        navigationView.getMenu().findItem(R.id.action_create).setChecked(true);
//                        break;
//
//                    case 3:
//                        navigationView.getMenu().findItem(R.id.action_account).setChecked(true);
//                        break;
//
//                    case 4:
//                        navigationView.getMenu().findItem(R.id.action_setting).setChecked(true);
//                        break;
//                }
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

    }
}