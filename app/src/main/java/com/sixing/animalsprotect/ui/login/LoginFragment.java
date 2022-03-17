package com.sixing.animalsprotect.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sixing.animalsprotect.R;

public class LoginFragment extends Fragment implements View.OnClickListener{
    private View view;
    private LoginActivity loginActivity;
    private TextView submit_btn;
    private ImageView back_ic;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_login,container,false);
        init();
        return view;
    }
    private void init(){
        loginActivity= (LoginActivity) getActivity();

        submit_btn=view.findViewById(R.id.submit_btn);
        back_ic=view.findViewById(R.id.back_ic);

        back_ic.setOnClickListener(this);
        submit_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_btn:
                break;
            case R.id.back_ic:
                loginActivity.fragmentTransaction=loginActivity.fragmentManager.beginTransaction();
                loginActivity.fragmentTransaction.hide(this);
                loginActivity.fragmentTransaction.commit();
                loginActivity.frameLayout.setVisibility(View.GONE);
                break;
        }
    }
}
