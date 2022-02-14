package com.sixing.animalsprotect.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;

import androidx.constraintlayout.helper.widget.Carousel;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.adapter.AnimalHomeAdapter;
import com.sixing.animalsprotect.bean.AnimalHome;
import com.sixing.animalsprotect.bean.AnimalInformation;
import com.sixing.animalsprotect.bean.SearchResult;
import com.sixing.animalsprotect.shape.GridView;
import com.sixing.animalsprotect.ui.animal.AnimalActivity;
import com.sixing.animalsprotect.ui.main.viewModel.HomeViewModel;
import com.sixing.animalsprotect.ui.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private MotionLayout motionLayout;
    private EditText search_bar;
    private AnimalHomeAdapter animalHomeAdapter;
    private GridView gridview;
    private List<AnimalHome> animalHomes;
    private ViewModelProvider viewModelProvider;
    private HomeViewModel homeViewModel;
    private Carousel carousel;
    private Context context;
    private View.OnClickListener onClickListener=this;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_home,container,false);
        init();
        initListener();
        initCarousel();
        initGridView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void init(){
        context=getContext();
        animalHomes=new ArrayList<>();
        motionLayout=view.findViewById(R.id.motion);
        carousel=view.findViewById(R.id.carousel);
        gridview=view.findViewById(R.id.gridview);
        search_bar=view.findViewById(R.id.search_bar);
        viewModelProvider=new ViewModelProvider(this);
        homeViewModel=viewModelProvider.get(HomeViewModel.class);
    }

    private void initListener(){
        search_bar.setOnClickListener(this);
        gridview.setOnItemClickListener(this);
    }

    public void initCarousel(){
        carousel.setAdapter(new Carousel.Adapter() {
            @Override
            public int count() {
                return 0;
            }

            @Override
            public void populate(View view, int index) {

            }

            @Override
            public void onNewItem(int index) {

            }
        });
    }

    private void initGridView(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<SearchResult> searchResults=homeViewModel.getSomeAnimals();
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
            case R.id.search_bar:
                Intent intent=new Intent(context, SearchActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.gridview:
                Intent intent=new Intent(context, AnimalActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("animalId",((SearchResult)parent.getAdapter().getItem(position)).getId());
                intent.putExtra("animalIdBundle",bundle);
                startActivity(intent);
                break;
        }
    }
}
