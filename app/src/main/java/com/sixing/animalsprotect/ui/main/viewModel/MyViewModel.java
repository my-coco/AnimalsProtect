package com.sixing.animalsprotect.ui.main.viewModel;

import androidx.lifecycle.ViewModel;

import com.sixing.animalsprotect.bean.HttpResponse;
import com.sixing.animalsprotect.bean.UserInformation;
import com.sixing.animalsprotect.http.HttpRepository;

import retrofit2.Call;
import retrofit2.Response;

public class MyViewModel extends ViewModel {

    private HttpRepository httpRepository;
    public MyViewModel(){httpRepository=new HttpRepository();}

    public UserInformation getUserInfo(String phone, String password){
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
