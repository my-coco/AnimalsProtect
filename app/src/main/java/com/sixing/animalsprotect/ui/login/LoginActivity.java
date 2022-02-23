package com.sixing.animalsprotect.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.bean.UserInformation;
import com.sixing.animalsprotect.constant.Constants;
import com.sixing.animalsprotect.shara.SharadUtil;
import com.sixing.animalsprotect.ui.login.viewmodel.LoginViewModel;
import com.sixing.animalsprotect.ui.main.MainActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener ,View.OnTouchListener{
    private ViewModelProvider viewModelProvider;
    private LoginViewModel loginViewModel;
    private Context context;
    private ConstraintLayout login,load;
    private boolean isLoad=true;
    private Animation load_up,load_down,text_up,text_up1,text_down,text_down1;
    private EditText name_ed,password_ed,phone_ed,token_ed;
    private TextView name_tx,password_tx,load_btn,login_btn,phone_tx,token_tx,sure_btn;
    private ConstraintLayout load_layout,login_layout;
    private ConstraintLayout rootView;
    private float posX,posY,curPosX,curPosY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        initAnima();
        initListener();
    }


    private void init(){
        viewModelProvider=new ViewModelProvider(this);
        loginViewModel=viewModelProvider.get(LoginViewModel.class);
        context=this;

        login=findViewById(R.id.login);
        load=findViewById(R.id.load);
        name_ed=findViewById(R.id.name_ed);
        name_tx=findViewById(R.id.name_tx);
        password_ed=findViewById(R.id.password_ed);
        password_tx=findViewById(R.id.password_tx);
        load_btn=findViewById(R.id.load_btn);
        login_btn=findViewById(R.id.login_btn);
        load_layout=findViewById(R.id.load_layout);
        login_layout=findViewById(R.id.login_layout);
        phone_ed=findViewById(R.id.phone_ed);
        token_ed=findViewById(R.id.token_ed);
        phone_tx=findViewById(R.id.phone_tx);
        token_tx=findViewById(R.id.token_tx);
        sure_btn=findViewById(R.id.sure_btn);
        rootView=findViewById(R.id.rootView);
    }

    private void initAnima(){
        load_up= AnimationUtils.loadAnimation(this,R.anim.load_up);
        load_down=AnimationUtils.loadAnimation(this,R.anim.load_down);
        text_up=AnimationUtils.loadAnimation(this,R.anim.text_up);
        text_up1=AnimationUtils.loadAnimation(this,R.anim.text_up);
        text_down=AnimationUtils.loadAnimation(this,R.anim.text_down);
        text_down1=AnimationUtils.loadAnimation(this,R.anim.text_down);

        login.startAnimation(load_down);
        login.setZ(login.getZ()-1);
        login_btn.setVisibility(View.VISIBLE);
        login_layout.setVisibility(View.INVISIBLE);

    }

    private void initListener(){
        sure_btn.setOnClickListener(this);
        name_ed.setOnFocusChangeListener(this);
        password_ed.setOnFocusChangeListener(this);
        phone_ed.setOnFocusChangeListener(this);
        token_ed.setOnFocusChangeListener(this);
        rootView.setOnTouchListener(this);
    }

    private void login(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserInformation userInformation=loginViewModel.getUserInfo(name_ed.getText().toString(),password_ed.getText().toString());
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
            case R.id.sure_btn:
                if (isLoad){
                    login();
                }
                break;
        }
    }

    public void move(){
        if(isLoad){
            login.startAnimation(load_up);
            login.setZ(login.getZ()+1);
            load.startAnimation(load_down);
            load.setZ(load.getZ()-1);
            login_btn.setVisibility(View.INVISIBLE);
            load_btn.setVisibility(View.VISIBLE);
            load_layout.setVisibility(View.INVISIBLE);
            login_layout.setVisibility(View.VISIBLE);
            isLoad=false;
        }else{
            login.startAnimation(load_down);
            login.setZ(login.getZ()-1);
            load_btn.setVisibility(View.INVISIBLE);
            login_layout.setVisibility(View.INVISIBLE);
            load.startAnimation(load_up);
            load.setZ(load.getZ()+1);
            login_btn.setVisibility(View.VISIBLE);
            load_layout.setVisibility(View.VISIBLE);
            isLoad=true;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.name_ed:
                if(name_ed.getText().toString().length()==0){
                    if(hasFocus){
                        name_tx.setTextSize(14);
                        name_tx.startAnimation(text_up);
                    }else{
                        name_tx.setTextSize(18);
                        name_tx.startAnimation(text_down);
                    }
                }
                break;
            case R.id.password_ed:
                if(password_ed.getText().toString().length()==0){
                    if(hasFocus){
                        password_tx.setTextSize(14);
                        password_tx.startAnimation(text_up1);
                    }else{
                        password_tx.setTextSize(18);
                        password_tx.startAnimation(text_down1);
                    }
                }
                break;
            case R.id.phone_ed:
                if(phone_ed.getText().toString().length()==0){
                    if(hasFocus){
                        phone_tx.setTextSize(14);
                        phone_tx.startAnimation(text_up);
                    }else{
                        phone_tx.setTextSize(18);
                        phone_tx.startAnimation(text_down);
                    }
                }
                break;
            case R.id.token_ed:
                if(token_ed.getText().toString().length()==0){
                    if(hasFocus){
                        token_tx.setTextSize(14);
                        token_tx.startAnimation(text_up1);
                    }else{
                        token_tx.setTextSize(18);
                        token_tx.startAnimation(text_down1);
                    }
                }
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.rootView:
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        posX = event.getX();
                        posY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        curPosX = event.getX();
                        curPosY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if ((Math.abs(curPosY - posY) > 200) || (Math.abs(curPosX - posX) > 200)){
                            move();
                        }
                        break;

                }
                break;
        }
        return true;
    }
}