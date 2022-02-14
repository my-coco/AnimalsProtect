package com.sixing.animalsprotect.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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
import com.sixing.animalsprotect.adapter.AnimalHomeAdapter;
import com.sixing.animalsprotect.bean.AnimalHome;
import com.sixing.animalsprotect.bean.SearchResult;
import com.sixing.animalsprotect.constant.Constants;
import com.sixing.animalsprotect.ui.animal.AnimalActivity;
import com.sixing.animalsprotect.ui.search.viewmodel.SearchResultViewModel;
import com.sixing.animalsprotect.ui.shelter.ShelterActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ImageView back_ic;
    private GridView gridview;
    private List<AnimalHome> animalHomes;
    private AnimalHomeAdapter animalHomeAdapter;
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
        animalHomes=new ArrayList<>();
        back_ic=findViewById(R.id.back_ic);
        gridview=findViewById(R.id.gridview);
        search_bar=findViewById(R.id.search_bar);
        if (!(key_words.equals("animal_all")||key_words.equals("shelter_all"))){
            search_bar.setText(key_words);
        }
        sure_btn=findViewById(R.id.sure_btn);
    }

    private void initListener(){
        back_ic.setOnClickListener(this);
        gridview.setOnItemClickListener(this);
        sure_btn.setOnClickListener(this);
    }

    private void initGridView(String key){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<SearchResult> searchResults=resultViewModel.getAnimalorShelter(key);
                animalHomeAdapter=new AnimalHomeAdapter(context,searchResults,onClickListener);
                gridview.post(new Runnable() {
                    @Override
                    public void run() {
                        gridview.setAdapter(animalHomeAdapter);
                    }
                });
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.gridview:
                SearchResult searchResult= (SearchResult) parent.getAdapter().getItem(position);
                if (searchResult.getIsAnimal()==1){
                    Intent intent=new Intent(this, AnimalActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString(Constants.ANIMALID,searchResult.getId());
                    intent.putExtra(Constants.ANIMALIDBUNDLE,bundle);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(this, ShelterActivity.class);
                    intent.putExtra(Constants.SHELTERID,searchResult.getId());
                    startActivity(intent);
                }
                break;
        }
    }
}