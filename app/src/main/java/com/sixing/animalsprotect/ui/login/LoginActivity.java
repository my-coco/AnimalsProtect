package com.sixing.animalsprotect.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.bean.UserInformation;
import com.sixing.animalsprotect.constant.Constants;
import com.sixing.animalsprotect.shara.SharadUtil;
import com.sixing.animalsprotect.ui.login.viewmodel.LoginViewModel;
import com.sixing.animalsprotect.ui.main.MainActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private ViewModelProvider viewModelProvider;
    private LoginViewModel loginViewModel;
    private EditText user_phone,user_password;
    private TextView login_btn;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        initListener();
    }


    private void init(){
        viewModelProvider=new ViewModelProvider(this);
        loginViewModel=viewModelProvider.get(LoginViewModel.class);
        context=this;

        user_password=findViewById(R.id.user_password);
        user_phone=findViewById(R.id.user_phone);
        login_btn=findViewById(R.id.login_btn);
    }

    private void initListener(){
        login_btn.setOnClickListener(this);
    }

    private void login(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserInformation userInformation=loginViewModel.getUserInfo(user_phone.getText().toString(),user_password.getText().toString());
                if(userInformation.getUser_phone()==null){
                    Log.d("TAG", "run: 错误");
                }else{
                    SharadUtil.sharadUtil.put(Constants.USERPHONE,userInformation.getUser_phone());
                    SharadUtil.sharadUtil.put(Constants.USERPASSWORD,userInformation.getUser_password());
                    SharadUtil.sharadUtil.put(Constants.USERNAME,userInformation.getUser_name());
                    Intent intent=new Intent(context, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                login();
                break;
        }
    }

}