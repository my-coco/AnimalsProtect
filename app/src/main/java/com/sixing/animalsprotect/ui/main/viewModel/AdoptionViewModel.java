package com.sixing.animalsprotect.ui.main.viewModel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.sixing.animalsprotect.bean.Adoption;
import com.sixing.animalsprotect.bean.HttpResponse;
import com.sixing.animalsprotect.http.HttpRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AdoptionViewModel extends ViewModel {
    private HttpRepository httpRepository;
    public AdoptionViewModel(){httpRepository=new HttpRepository();}

    public List<Adoption> getAdoptions(String user_phone){
        List<Adoption> adoptions=null;
        try{
            Call<HttpResponse<List<Adoption>>> call=httpRepository.getAdoptions(user_phone);
            Response<HttpResponse<List<Adoption>>> response=call.execute();
            if (response.isSuccessful()){
                if (response.body().getCode().equals("200")){
                    adoptions=response.body().getData();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return adoptions;
    }
}
