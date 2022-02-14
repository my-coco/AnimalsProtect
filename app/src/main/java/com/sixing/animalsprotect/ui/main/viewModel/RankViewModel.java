package com.sixing.animalsprotect.ui.main.viewModel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.sixing.animalsprotect.bean.AnimalInformation;
import com.sixing.animalsprotect.bean.HttpResponse;
import com.sixing.animalsprotect.bean.HttpResponseGhost;
import com.sixing.animalsprotect.http.HttpRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class RankViewModel extends ViewModel {
    private HttpRepository repository;
    private List<HttpResponseGhost.AddressInfor> address;
    public RankViewModel(){
        repository=new HttpRepository();
    }
    public String TAG="RankViewModel";

    public List<HttpResponseGhost.AddressInfor> getAddress(String id){
        if(address==null){
            address=new ArrayList<>();
        }else{
            address.clear();
        }
        Call<HttpResponseGhost<List<HttpResponseGhost.AddressInfor>>> responseCall=repository.getAddress(11994,"90a0a511cd62ed3da944f541169f4311",id);
        try {
            Response<HttpResponseGhost<List<HttpResponseGhost.AddressInfor>>> responseGhost=responseCall.execute();
            if (responseGhost.isSuccessful()){
                address=responseGhost.body().getDatas();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    public List<AnimalInformation> getAnimalRank(String province, String city, String country){
        List<AnimalInformation> animalRanks=new ArrayList<>();
        Call<HttpResponse<List<AnimalInformation>>> call=repository.getAnimalRank(province, city, country);
        try {
            Response<HttpResponse<List<AnimalInformation>>> response=call.execute();
            if(response.isSuccessful()){
                if (response.body().getCode().equals("200")){
                    animalRanks=response.body().getData();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return animalRanks;
    }

}
