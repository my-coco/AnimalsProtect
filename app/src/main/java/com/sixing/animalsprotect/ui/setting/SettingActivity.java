package com.sixing.animalsprotect.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.constant.Constants;
import com.sixing.animalsprotect.ui.login.LoginActivity;
import com.sixing.animalsprotect.ui.main.MainActivity;
import com.sixing.animalsprotect.util.SharadUtil;


public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView back_btn;
    private TextView logout_btn,name_tx,person_word_tx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
        initView();
    }

    private void init(){

    }

    private void initView(){
        back_btn=findViewById(R.id.back_btn);
        logout_btn=findViewById(R.id.logout_btn);
        name_tx=findViewById(R.id.name_tx);
        person_word_tx=findViewById(R.id.person_word_tx);
        name_tx.setText(SharadUtil.getString(Constants.USERNAME,""));
        logout_btn.setOnClickListener(this);
        back_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.logout_btn:
                Intent intent1=new Intent(this, LoginActivity.class);
                startActivity(intent1);
                SharadUtil.remove(Constants.USERNAME);
                SharadUtil.remove(Constants.USERPASSWORD);
                SharadUtil.remove(Constants.USERPHONE);
                if(MainActivity.mainActivity!=null){
                    MainActivity.mainActivity.finish();
                }
                finish();
                break;
        }
    }
}