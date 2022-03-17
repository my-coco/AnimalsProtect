package com.sixing.animalsprotect.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.bean.UserInformation;
import com.sixing.animalsprotect.constant.Constants;
import com.sixing.animalsprotect.ui.main.MainActivity;
import com.sixing.animalsprotect.util.SharadUtil;
import com.sixing.animalsprotect.ui.login.viewmodel.LoginViewModel;

public class LoginPasswordFragment extends Fragment implements View.OnClickListener{
    private View view;
    private LoginActivity loginActivity;
    private EditText phone_ed,password_ed;
    private TextView submit_btn;
    private ImageView back_ic;
    private LoginPasswordFragment loginPasswordFragment;
    private LoginViewModel viewModel;
    private ViewModelProvider viewModelProvider;
    private Handler handler;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_load_password,container,false);
        init();
        return view;
    }
    private void init(){
        loginPasswordFragment=this;
        handler=new Handler(callback);
        loginActivity= (LoginActivity) getActivity();
        viewModelProvider=new ViewModelProvider(loginActivity);
        viewModel=viewModelProvider.get(LoginViewModel.class);

        submit_btn=view.findViewById(R.id.submit_btn);
        back_ic=view.findViewById(R.id.back_ic);
        phone_ed=view.findViewById(R.id.phone_ed);
        password_ed=view.findViewById(R.id.password_ed);

        back_ic.setOnClickListener(this);
        submit_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_btn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        UserInformation userInformation=viewModel.getUserInfo(phone_ed.getText().toString(),password_ed.getText().toString());
                        if(userInformation!=null){
                            SharadUtil.put(Constants.USERPHONE,userInformation.getUser_phone());
                            SharadUtil.put(Constants.USERNAME,userInformation.getUser_name());
                            SharadUtil.put(Constants.USERPASSWORD,userInformation.getUser_password());
                            handler.sendEmptyMessage(1);
                        }else{
                            handler.sendEmptyMessage(0);
                        }
                    }
                }).start();
                break;
            case R.id.back_ic:
                loginActivity.fragmentTransaction=loginActivity.fragmentManager.beginTransaction();
                loginActivity.fragmentTransaction.hide(this);
                loginActivity.fragmentTransaction.commit();
                loginActivity.frameLayout.setVisibility(View.GONE);
                break;
        }
    }

    private Handler.Callback callback=new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    Toast.makeText(getContext(),"登录失败，密码或账号错误",Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    loginActivity.fragmentTransaction=loginActivity.fragmentManager.beginTransaction();
                    loginActivity.fragmentTransaction.hide(loginPasswordFragment);
                    loginActivity.fragmentTransaction.commit();
                    loginActivity.frameLayout.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"登录成功",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                    loginActivity.finish();
                    break;
            }
            return false;
        }
    };
}
