package com.guardianofgods.proprofile;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.guardianofgods.proprofile.databinding.ActivityHomeBinding;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class HomeFragment extends Fragment {

    ActivityHomeBinding binding;

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapterHome viewPagerAdapterHome;
    ImageView imageView, copy_phone,copy_email, i_facebook,i_youtube,i_instagram;
    TextView tvPhone,tvEmail;

    Uri imageUri;
    StorageReference storageReference;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        //binding=ActivityHomeBinding.inflate(getLayoutInflater());


        tabLayout=view.findViewById(R.id.tablayout);
        viewPager2=view.findViewById(R.id.view_pager_home);
        imageView=view.findViewById(R.id.Image_avatar);
        copy_phone=view.findViewById(R.id.copy_phone);
        copy_email=view.findViewById(R.id.copy_email);
        tvPhone=view.findViewById(R.id.text_phone);
        tvEmail=view.findViewById(R.id.text_email);
        i_facebook=view.findViewById(R.id.facebook);
        i_youtube=view.findViewById(R.id.youtube);
        i_instagram=view.findViewById(R.id.instagram);

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








//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                requestPermissions();
//            }
//        });



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
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();

            }
        });

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        i_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFacebookIntent("100014907154914");
            }
        });

        i_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInstagramIntent();
            }
        });
        i_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=kHy6ePOn44s")));
            }
        });
        return view;
    }

//    private void requestPermissions() {
//        PermissionListener permissionlistener = new PermissionListener() {
//            @Override
//            public void onPermissionGranted() {
//                openImagePicker();
//
//            }
//
//            @Override
//            public void onPermissionDenied(List<String> deniedPermissions) {
//                Toast.makeText(getActivity(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
//            }
//
//
//        };
//        TedPermission.create()
//                .setPermissionListener(permissionlistener)
//                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
//                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
//                .check();
//    }
//
//    private void openImagePicker() {
//
//    }
    private void selectImage(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Complete action using"),1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getActivity(),data.getData().toString(),Toast.LENGTH_SHORT).show();
        if(requestCode==1 && data !=null && data.getData()!=null){
            imageUri =data.getData();
            imageView.setImageURI(imageUri);
            Toast.makeText(getActivity(),imageUri.toString(),Toast.LENGTH_SHORT).show();
            imageView.getLayoutParams().height=100;
            imageView.getLayoutParams().width=100;
            uploadImage();



        }
    }
    private void uploadImage(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CHINESE);
        Date date=new Date();
        String filename= format.format(date);
        storageReference= FirebaseStorage.getInstance().getReference("image/"+filename);

        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getActivity(),"Successfully Upload",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"Failed to upload",Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void openFacebookIntent(String id){
        try {
            Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/"+id));
            startActivity(intent);
        } catch (Exception e) {
            Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" +id));
            startActivity(intent);
        }
    }

    private void openInstagramIntent(){
        Uri uri = Uri.parse("https://www.instagram.com/mnguyet.1405/");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/mnguyet.1405/")));
        }
    }
}