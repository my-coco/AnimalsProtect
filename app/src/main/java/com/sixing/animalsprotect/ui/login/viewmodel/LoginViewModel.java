package com.sixing.animalsprotect.ui.login.viewmodel;

import androidx.lifecycle.ViewModel;

import com.sixing.animalsprotect.bean.HttpResponse;
import com.sixing.animalsprotect.bean.UserInformation;
import com.sixing.animalsprotect.http.HttpRepository;

import retrofit2.Call;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private HttpRepository httpRepository;
    public LoginViewModel(){httpRepository=new HttpRepository();}

    public UserInformation getUserInfo(String phone,String password){
        UserInformation userInformation=null;
        try {
            Call<HttpResponse<UserInformation>> call=httpRepository.getUserInformation(phone,password);
            Response<HttpResponse<UserInformation>> response=call.execute();
            if (response.isSuccessful()){
                if (response.body().getCode().equals("200")){
                    userInformation=response.body().getData();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return userInformation;
    }
}
