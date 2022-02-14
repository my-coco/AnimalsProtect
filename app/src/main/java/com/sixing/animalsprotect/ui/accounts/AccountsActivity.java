package com.sixing.animalsprotect.ui.accounts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.constant.Constants;

public class AccountsActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView back_ic;
    private Intent intent;
    private TextView title,accounts_information,accounts_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
        intent=getIntent();
        init();
        initInformation();
        initListener();
    }
    private void init(){
        back_ic=findViewById(R.id.back_ic);
        title=findViewById(R.id.title);
        accounts_information=findViewById(R.id.accounts_information);
        accounts_time=findViewById(R.id.accounts_time);
    }

    private void initListener(){
        back_ic.setOnClickListener(this);
    }

    private void initInformation(){
        Bundle bundle=intent.getBundleExtra(Constants.ACCOUNTBUNDLE);
        title.setText(bundle.getString("title"));
        accounts_information.setText(bundle.getString("reason"));
        accounts_time.setText(bundle.getString("date"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_ic:
                finish();
                break;
        }
    }
}