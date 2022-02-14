package com.sixing.animalsprotect.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.constant.Constants;
import com.sixing.animalsprotect.shara.SharadUtil;
import com.sixing.animalsprotect.ui.login.LoginActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView home_ic,adoption_ic,rank_ic,my_ic;
    private TextView home_tx,adoption_tx,rank_tx,my_tx;
    private Fragment adoptionFragment,homeFragment,myFragment,rankFragment;
    private Fragment[] fragments=new Fragment[4];
    private FragmentManager fragmentManager;
    private int fragmentIndex;
    private String TAG="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharadUtil.getInstance(this);
        login();
        init();
        initView();
        initListener();
        addFragment();
        showFragment(null,homeFragment);
        changeIc(-1,0);
        fragmentIndex=0;
    }

    private void init(){
        fragmentManager=getSupportFragmentManager();
        adoptionFragment=new AdoptionFragment();
        homeFragment=new HomeFragment();
        myFragment=new MyFragment();
        rankFragment=new RankFragment();
        fragments[0]=homeFragment;
        fragments[1]=adoptionFragment;
        fragments[2]=rankFragment;
        fragments[3]=myFragment;
    }

    private void initView(){
        home_ic=findViewById(R.id.home_ic);
        adoption_ic=findViewById(R.id.adoption_ic);
        rank_ic=findViewById(R.id.rank_ic);
        my_ic=findViewById(R.id.my_ic);

        home_tx=findViewById(R.id.home_tx);
        adoption_tx=findViewById(R.id.adoption_tx);
        rank_tx=findViewById(R.id.rank_tx);
        my_tx=findViewById(R.id.my_tx);

    }

    private void initListener(){
        home_ic.setOnClickListener(this);
        adoption_ic.setOnClickListener(this);
        rank_ic.setOnClickListener(this);
        my_ic.setOnClickListener(this);
        home_tx.setOnClickListener(this);
        adoption_tx.setOnClickListener(this);
        rank_tx.setOnClickListener(this);
        my_tx.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_ic:
            case R.id.home_tx:
                if(fragmentIndex!=0){
                    showFragment(fragments[fragmentIndex],homeFragment);
                    changeIc(fragmentIndex,0);
                    fragmentIndex=0;
                }
                break;
            case R.id.adoption_ic:
            case R.id.adoption_tx:
                if(fragmentIndex!=1){
                    showFragment(fragments[fragmentIndex],adoptionFragment);
                    changeIc(fragmentIndex,1);
                    fragmentIndex=1;
                }
                break;
            case R.id.rank_ic:
            case R.id.rank_tx:
                if(fragmentIndex!=2){
                    showFragment(fragments[fragmentIndex],rankFragment);
                    changeIc(fragmentIndex,2);
                    fragmentIndex=2;
                }
                break;
            case R.id.my_ic:
            case R.id.my_tx:
                if(fragmentIndex!=3){
                    showFragment(fragments[fragmentIndex],myFragment);
                    changeIc(fragmentIndex,3);
                    fragmentIndex=3;
                }
                break;
        }
    }

    private void addFragment(){
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment,adoptionFragment);
        fragmentTransaction.add(R.id.fragment,homeFragment);
        fragmentTransaction.add(R.id.fragment,myFragment);
        fragmentTransaction.add(R.id.fragment,rankFragment);
        fragmentTransaction.hide(adoptionFragment);
        fragmentTransaction.hide(myFragment);
        fragmentTransaction.hide(rankFragment);
        fragmentTransaction.hide(homeFragment);
        fragmentTransaction.commit();
    }

    private void showFragment(Fragment oldFragment,Fragment newFragment){
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        if(oldFragment!=null){
            fragmentTransaction.hide(oldFragment);
            Log.d(TAG, "showFragment: 1");
        }
        fragmentTransaction.show(newFragment);
        fragmentTransaction.commit();
    }

    private void changeIc(int oldIndex,int newIndex){
        switch (newIndex){
            case 0:
                home_ic.setImageDrawable(getDrawable(R.drawable.home));
                home_tx.setTextColor(getColor(R.color.black));
                break;
            case 1:
                adoption_ic.setImageDrawable(getDrawable(R.drawable.bring));
                adoption_tx.setTextColor(getColor(R.color.black));
                break;
            case 2:
                rank_ic.setImageDrawable(getDrawable(R.drawable.charts));
                rank_tx.setTextColor(getColor(R.color.black));
                break;
            case 3:
                my_ic.setImageDrawable(getDrawable(R.drawable.me));
                my_tx.setTextColor(getColor(R.color.black));
                break;
        }
        switch (oldIndex){
            case 0:
                home_ic.setImageDrawable(getDrawable(R.drawable.home_normal));
                home_tx.setTextColor(getColor(R.color.navigation_font_color));
                break;
            case 1:
                adoption_ic.setImageDrawable(getDrawable(R.drawable.bring_normal));
                adoption_tx.setTextColor(getColor(R.color.navigation_font_color));
                break;
            case 2:
                rank_ic.setImageDrawable(getDrawable(R.drawable.charts_normal));
                rank_tx.setTextColor(getColor(R.color.navigation_font_color));
                break;
            case 3:
                my_ic.setImageDrawable(getDrawable(R.drawable.me_normal));
                my_tx.setTextColor(getColor(R.color.navigation_font_color));
                break;
        }
    }


    private void login(){
        Log.d(TAG, "login: "+SharadUtil.getInstance(this).getString(Constants.USERPHONE,null));
        if(SharadUtil.getInstance(this).getString(Constants.USERPHONE,null)==null){
            Intent intent=new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}