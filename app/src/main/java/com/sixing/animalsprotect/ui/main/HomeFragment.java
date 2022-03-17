package com.sixing.animalsprotect.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.adapter.AnimalAdapter;
import com.sixing.animalsprotect.bean.AnimalHome;
import com.sixing.animalsprotect.bean.SearchResult;
import com.sixing.animalsprotect.ui.main.viewModel.HomeViewModel;
import com.sixing.animalsprotect.ui.search.SearchActivity;
import com.sixing.animalsprotect.widget.MyRecycleView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener,View.OnTouchListener ,View.OnFocusChangeListener{
    private MotionLayout motionLayout;
    private EditText search_bar;
    private AnimalAdapter animalAdapter;
    private MyRecycleView recyclerView;
    private List<AnimalHome> animalHomes;
    private ViewModelProvider viewModelProvider;
    private HomeViewModel homeViewModel;
    private Carousel carousel;
    private Context context;
    private View.OnClickListener onClickListener=this;
    private ImageView load_anim;
    private View view;
    private String TAG="HomeFragment";
    private AnimationDrawable load_animdrawable;
    private Handler handler;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_home,container,false);
        Log.d("TAG", "onCreateView: "+TAG);
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
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        search_bar=view.findViewById(R.id.search_bar);
        load_anim=view.findViewById(R.id.load_anim);

        viewModelProvider=new ViewModelProvider(this);
        homeViewModel=viewModelProvider.get(HomeViewModel.class);

        load_animdrawable=(AnimationDrawable)load_anim.getBackground();

        handler=new Handler(callback);
    }

    private void initListener(){
        search_bar.setOnTouchListener(this);
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
        load_anim.setVisibility(View.VISIBLE);
        load_animdrawable.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<SearchResult> searchResults=homeViewModel.getSomeAnimals();
                animalAdapter=new  AnimalAdapter(searchResults,context);
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(animalAdapter);
                        handler.sendEmptyMessage(1);
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.search_bar:
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        Intent intent=new Intent(context, SearchActivity.class);
                        startActivity(intent);
                        break;
                }
                break;
        }
        return true;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){

        }
    }

    private Handler.Callback callback=new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    load_anim.setVisibility(View.GONE);
                    load_animdrawable.stop();
                    break;
            }
            return false;
        }
    };
}
