package com.sixing.animalsprotect.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.adapter.AnimalAdapter;
import com.sixing.animalsprotect.adapter.OgAdapter;
import com.sixing.animalsprotect.bean.AnimalHome;
import com.sixing.animalsprotect.bean.SearchResult;
import com.sixing.animalsprotect.constant.Constants;
import com.sixing.animalsprotect.ui.animal.AnimalActivity;
import com.sixing.animalsprotect.ui.search.viewmodel.SearchResultViewModel;
import com.sixing.animalsprotect.ui.shelter.ShelterActivity;
import com.sixing.animalsprotect.widget.MyRecycleView;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView back_ic;
    private MyRecycleView recyclerView;
    private AnimalAdapter animalAdapter;
    private OgAdapter ogAdapter;
    private Intent intent;
    private String key_words;
    private ViewModelProvider viewModelProvider;
    private SearchResultViewModel resultViewModel;
    private View.OnClickListener onClickListener=this;
    private Context context=this;
    private EditText search_bar;
    private TextView sure_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        init();
        initListener();
        initGridView(key_words);
    }

    private void init(){
        intent=getIntent();
        key_words=intent.getStringExtra("key");
        viewModelProvider=new ViewModelProvider(this);
        resultViewModel=viewModelProvider.get(SearchResultViewModel.class);
        back_ic=findViewById(R.id.back_ic);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        search_bar=findViewById(R.id.search_bar);
        if (!(key_words.equals("animal_all")||key_words.equals("shelter_all"))){
            search_bar.setText(key_words);
        }
        sure_btn=findViewById(R.id.sure_btn);
    }

    private void initListener(){
        back_ic.setOnClickListener(this);
        sure_btn.setOnClickListener(this);
    }

    private void initGridView(String key){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<SearchResult> searchResults=resultViewModel.getAnimalorShelter(key);
                if(key.equals("shelter_all")){
                    ogAdapter=new OgAdapter(searchResults,context);
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(ogAdapter);
                        }
                    });
                }else{
                    animalAdapter=new AnimalAdapter(searchResults,context);
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(animalAdapter);
                        }
                    });
                }

            }
        }).start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_ic:
                finish();
                break;
            case R.id.sure_btn:
                initGridView(search_bar.getText().toString());
                break;
        }
    }

}