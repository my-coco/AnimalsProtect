package com.sixing.animalsprotect.ui.search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.adapter.HotAdapter;
import com.sixing.animalsprotect.ui.animal.AnimalActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView hot_list;
    private ImageView back_ic;
    private HotAdapter hotAdapter;
    private Context context;
    private EditText search_bar;
    private TextView sure_btn,org_btn,animals_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
        initListener();
        initList();
    }

    private void init(){
        hot_list=findViewById(R.id.list);
        back_ic=findViewById(R.id.back_ic);
        sure_btn=findViewById(R.id.sure_btn);
        animals_btn=findViewById(R.id.animals_btn);
        org_btn=findViewById(R.id.org_btn);
        search_bar=findViewById(R.id.search_bar);
        context=this;
    }

    private void initListener(){
        hot_list.setOnItemClickListener(this);
        back_ic.setOnClickListener(this);
        sure_btn.setOnClickListener(this);
        animals_btn.setOnClickListener(this);
        org_btn.setOnClickListener(this);
    }

    private void initList(){
        List<String> hot_titles=new ArrayList<>();
        hot_titles.add("橘猫阿肥昨日又抓到一只大老鼠");
        hot_titles.add("橘猫阿肥昨日又抓到二只大老鼠");
        hot_titles.add("橘猫阿肥昨日又抓到三只大老鼠");
        hot_titles.add("橘猫阿肥昨日又抓到四只大老鼠");
        hotAdapter=new HotAdapter(hot_titles,this);
        hot_list.setAdapter(hotAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.list:
//                Intent intent=new Intent(this, AnimalActivity.class);
//                startActivity(intent);
                Toast.makeText(context,"该功能敬请期待",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this,SearchResultActivity.class);
        switch (v.getId()){
            case R.id.back_ic:
                finish();
                break;
            case R.id.animals_btn:
                intent.putExtra("key","animal_all");
                startActivity(intent);
                break;
            case R.id.org_btn:
                intent.putExtra("key","shelter_all");
                startActivity(intent);
                break;
            case R.id.sure_btn:
                intent.putExtra("key",search_bar.getText().toString());
                startActivity(intent);
                break;
        }
    }
}