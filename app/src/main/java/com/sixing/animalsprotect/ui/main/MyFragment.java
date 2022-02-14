package com.sixing.animalsprotect.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.sixing.animalsprotect.R;

public class MyFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    private TextView user_name;
    private View view;
    private String TAG="MyFragment";
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_my,container,false);
        init();
        return view;
    }

    private void init(){
        sharedPreferences= getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        Log.d(TAG, "init: "+sharedPreferences.getString("user_name","默认昵称")+"  "+sharedPreferences.getString("user_phone","空"));
        user_name=view.findViewById(R.id.user_name);
        user_name.setText(sharedPreferences.getString("user_name","默认昵称"));
    }
}
