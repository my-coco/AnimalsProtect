package com.sixing.animalsprotect.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.bean.UserInformation;
import com.sixing.animalsprotect.ui.login.viewmodel.LoginViewModel;
import com.tencentcloudapi.iotvideo.v20191126.models.LogData;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private ViewModelProvider viewModelProvider;
    private LoginViewModel loginViewModel;
    private EditText user_phone,user_password;
    private TextView login_btn;
    private Context context;
    private SharedPreferences sharadPreferences;

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
        sharadPreferences=getSharedPreferences("user_info",Context.MODE_PRIVATE);

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
                    SharedPreferences.Editor editor=sharadPreferences.edit();
                    editor.putString("user_phone",userInformation.getUser_phone());
                    editor.putString("user_password",userInformation.getUser_password());
                    editor.putString("user_name",userInformation.getUser_name());
                    editor.apply();
                    editor.commit();
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