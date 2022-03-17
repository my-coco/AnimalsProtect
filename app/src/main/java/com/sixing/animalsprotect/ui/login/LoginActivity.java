package com.sixing.animalsprotect.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sixing.animalsprotect.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    public FrameLayout frameLayout;
    public FragmentManager fragmentManager;
    public FragmentTransaction fragmentTransaction;
    public Fragment loadPhoneFragment,loadPasswordFragment,loginFragment;
    private TextView load_phone_btn,load_password_btn,login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initFragment();

    }

    private void initFragment(){
        loadPhoneFragment=new LoadPhoneFragment();
        loadPasswordFragment=new LoginPasswordFragment();
        loginFragment=new LoginFragment();
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(frameLayout.getId(),loadPhoneFragment);
        fragmentTransaction.add(frameLayout.getId(),loadPasswordFragment);
        fragmentTransaction.add(frameLayout.getId(),loginFragment);
        fragmentTransaction.hide(loadPasswordFragment);
        fragmentTransaction.hide(loadPhoneFragment);
        fragmentTransaction.hide(loginFragment);
        fragmentTransaction.commit();
    }

    private void initView() {
        load_phone_btn=findViewById(R.id.load_phone);
        load_password_btn=findViewById(R.id.load_password);
        login_btn=findViewById(R.id.login);
        frameLayout=findViewById(R.id.fragment);

        load_phone_btn.setOnClickListener(this);
        load_password_btn.setOnClickListener(this);
        login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.load_password:
                frameLayout.setVisibility(View.VISIBLE);
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.show(loadPasswordFragment);
                fragmentTransaction.commit();
                break;
            case R.id.load_phone:
                frameLayout.setVisibility(View.VISIBLE);
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.show(loadPhoneFragment);
                fragmentTransaction.commit();
                break;
            case R.id.login:
                frameLayout.setVisibility(View.VISIBLE);
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.show(loginFragment);
                fragmentTransaction.commit();
                break;
        }
    }
}