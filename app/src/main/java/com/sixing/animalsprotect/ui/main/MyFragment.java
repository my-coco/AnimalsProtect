package com.sixing.animalsprotect.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.adapter.AnimalAdapter;
import com.sixing.animalsprotect.bean.SearchResult;
import com.sixing.animalsprotect.bean.UserInformation;
import com.sixing.animalsprotect.constant.Constants;
import com.sixing.animalsprotect.ui.main.viewModel.MyViewModel;
import com.sixing.animalsprotect.ui.main.viewModel.RankViewModel;
import com.sixing.animalsprotect.ui.photo.PhotoActivity;
import com.sixing.animalsprotect.ui.setting.SettingActivity;
import com.sixing.animalsprotect.util.SharadUtil;
import com.sixing.animalsprotect.widget.MyRecycleView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MyFragment extends Fragment implements View.OnClickListener{
    private SharedPreferences sharedPreferences;
    private MyRecycleView recyclerView;
    private List<SearchResult> resultList;
    private MyViewModel myViewModel;
    private ViewModelProvider viewModelProvider;
    private AnimalAdapter adapter;
    private TextView user_name,user_phone_tx;
    private ImageView setting_ic,circleView,user_bg;
    private View view;
    private String TAG="MyFragment";
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_my,container,false);
        init();
        initPhoto();
        initList();
        return view;
    }

    private void init(){
        viewModelProvider=new ViewModelProvider(this);
        myViewModel=viewModelProvider.get(MyViewModel.class);
        sharedPreferences= getActivity().getSharedPreferences(Constants.USERINFO, Context.MODE_PRIVATE);
        user_name=view.findViewById(R.id.user_name);
        user_name.setText(sharedPreferences.getString(Constants.USERNAME,"默认昵称"));
        user_phone_tx=view.findViewById(R.id.user_phone_tx);
        user_phone_tx.setText(SharadUtil.getString(Constants.USERPHONE,""));
        setting_ic=view.findViewById(R.id.setting_ic);
        recyclerView=view.findViewById(R.id.recyclerView);
        setting_ic.setOnClickListener(this);
        circleView=view.findViewById(R.id.circleView);
        user_bg=view.findViewById(R.id.user_bg);
        circleView.setOnClickListener(this);
        user_bg.setOnClickListener(this);

    }

    public void initPhoto(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserInformation userInformation=myViewModel.getUserInfo(SharadUtil.getString(Constants.USERPHONE,""),SharadUtil.getString(Constants.USERPASSWORD,""));
                if(userInformation!=null){
                    byte[] photo= Base64.decode(userInformation.getPhoto().getBytes(),Base64.DEFAULT);
                    Bitmap photo_bit=BitmapFactory.decodeByteArray(photo,0,photo.length);
                    byte[] bgPhoto=Base64.decode(userInformation.getBg_photo().getBytes(),Base64.DEFAULT);
                    Bitmap bgPhone=BitmapFactory.decodeByteArray(bgPhoto,0,bgPhoto.length);
                    circleView.post(new Runnable() {
                        @Override
                        public void run() {
                            circleView.setImageBitmap(photo_bit);
                            user_bg.setImageBitmap(bgPhone);
                        }
                    });

                }
            }
        }).start();
    }

    private void initList(){
        resultList=new ArrayList<>();
        for(int i=0;i<10;i++){
            resultList.add(new SearchResult("0","小白",1));
        }
        adapter=new AnimalAdapter(resultList,getContext());
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_ic:
                Intent intent=new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.circleView:
                Intent intent1=new Intent();
                intent1.setClass(getContext(),PhotoActivity.class);
                intent1.putExtra("forWhat",1);
                startActivity(intent1);
                break;
            case R.id.user_bg:
                Intent intent2=new Intent();
                intent2.setClass(getContext(),PhotoActivity.class);
                intent2.putExtra("forWhat",2);
                startActivity(intent2);
                break;
        }
    }
}
