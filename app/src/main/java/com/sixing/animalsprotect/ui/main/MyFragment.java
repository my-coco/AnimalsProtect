package com.sixing.animalsprotect.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.adapter.AnimalAdapter;
import com.sixing.animalsprotect.bean.SearchResult;
import com.sixing.animalsprotect.constant.Constants;
import com.sixing.animalsprotect.ui.setting.SettingActivity;
import com.sixing.animalsprotect.widget.MyRecycleView;

import java.util.ArrayList;
import java.util.List;

public class MyFragment extends Fragment implements View.OnClickListener {
    private SharedPreferences sharedPreferences;
    private MyRecycleView recyclerView;
    private List<SearchResult> resultList;
    private AnimalAdapter adapter;
    private TextView user_name;
    private ImageView setting_ic;
    private View view;
    private String TAG="MyFragment";
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_my,container,false);
        init();
        initList();
        return view;
    }

    private void init(){
        sharedPreferences= getActivity().getSharedPreferences(Constants.USERINFO, Context.MODE_PRIVATE);
        user_name=view.findViewById(R.id.user_name);
        user_name.setText(sharedPreferences.getString(Constants.USERNAME,"默认昵称"));
        setting_ic=view.findViewById(R.id.setting_ic);
        recyclerView=view.findViewById(R.id.recyclerView);
        setting_ic.setOnClickListener(this);
    }

    private void initList(){
        resultList=new ArrayList<>();
        for(int i=0;i<10;i++){
            resultList.add(new SearchResult("0","小白",1));
        }
        adapter=new AnimalAdapter(resultList,getContext());
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_ic:
                Intent intent=new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
                break;
        }
    }
}
