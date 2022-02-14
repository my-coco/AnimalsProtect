package com.sixing.animalsprotect.ui.report;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.sixing.animalsprotect.R;

public class ReportActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView back_ic,add_ic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        init();
        initListener();
    }

    private void init(){
        back_ic=findViewById(R.id.back_ic);
        add_ic=findViewById(R.id.add_ic);
    }

    private void initListener(){
        back_ic.setOnClickListener(this);
        add_ic.setOnClickListener(this);
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