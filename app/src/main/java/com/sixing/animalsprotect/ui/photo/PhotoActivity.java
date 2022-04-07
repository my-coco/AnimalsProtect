package com.sixing.animalsprotect.ui.photo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.adapter.PhotoAdapter;
import com.sixing.animalsprotect.constant.Constants;
import com.sixing.animalsprotect.ui.main.MainActivity;
import com.sixing.animalsprotect.ui.main.MyFragment;
import com.sixing.animalsprotect.ui.photo.ViewModel.PhotoViewModel;
import com.sixing.animalsprotect.util.SharadUtil;
import com.sixing.animalsprotect.widget.MyRecycleView;

import java.io.File;
import java.util.ArrayList;

public class PhotoActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView submit_btn;
    private MyRecycleView myRecycleView;
    private ImageView back_ic;
    private ViewModelProvider viewModelProvider;
    private PhotoViewModel photoViewModel;
    private ArrayList<String> photoPath;
    private Context context;
    private Handler handler;
    private int forWhat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        SharadUtil.getInstance(this);
        init();
        initView();
        hasPermission();
    }

    private void init(){
        viewModelProvider=new ViewModelProvider(this);
        photoViewModel=viewModelProvider.get(PhotoViewModel.class);
        photoPath=new ArrayList<>();
        context=this;
        handler=new Handler(callback);
        forWhat=getIntent().getIntExtra("forWhat",0);
    }

    private void initView(){
        submit_btn=findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(this);
        back_ic=findViewById(R.id.back_ic);
        back_ic.setOnClickListener(this);
        myRecycleView=findViewById(R.id.photo_list);
        myRecycleView.setLayoutManager(new GridLayoutManager(context,3));
        photoViewModel.sum.postValue(0);
        photoViewModel.max.postValue(1);
        photoViewModel.sum.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                submit_btn.setText(integer+"/1"+"上传");
            }
        });
    }

    private void hasPermission(){
        if (Build.VERSION.SDK_INT >= 23) {// 6.0
            String[] perms = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,perms, 1);
            }else{
                choosePhoto();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case 1:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        choosePhoto();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private void choosePhoto(){
        photoPath.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver =getContentResolver();

                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "+
                                MediaStore.Images.Media.MIME_TYPE + "=? or "+
                                MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png","image/jpg"},
                        MediaStore.Images.Media.DATE_MODIFIED+" desc");//获取图片的cursor，按照时间倒序（发现没卵用)
                int sum=0;
                while (mCursor.moveToNext()&&sum<=50) {
                    String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));// 1.获取图片的路径
                    sum++;
                    photoPath.add(path);
                }
                PhotoAdapter photoAdapter=new PhotoAdapter(context,photoPath,handler);
                myRecycleView.post(new Runnable() {
                    @Override
                    public void run() {
                        myRecycleView.setAdapter(photoAdapter);
                    }
                });
                mCursor.close();
            }
        }).start();
    }

    private Handler.Callback callback=new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    photoViewModel.sum.postValue(0);
                    break;
                case 1:
                    photoViewModel.sum.postValue(1);
                    break;
                case 2:
                    Toast.makeText(context,"只能上传一张图片",Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_btn:
                if(photoViewModel.sum.getValue()>0){
                    String path=photoPath.get(((PhotoAdapter)myRecycleView.getAdapter()).index);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            switch (forWhat){
                                case 1:
                                    photoViewModel.setUserPhoto(SharadUtil.getString(Constants.USERPHONE,""),path);
                                    ((MyFragment)MainActivity.mainActivity.myFragment).initPhoto();
                                    finish();
                                    break;
                                case 2:
                                    photoViewModel.setUserBgPhoto(SharadUtil.getString(Constants.USERPHONE,""),path);
                                    ((MyFragment)MainActivity.mainActivity.myFragment).initPhoto();
                                    finish();
                                    break;
                            }

                        }
                    }).start();
                }else{
                    Toast.makeText(context,"请先选择一张照片",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.back_ic:
                finish();
                break;
        }
    }
}