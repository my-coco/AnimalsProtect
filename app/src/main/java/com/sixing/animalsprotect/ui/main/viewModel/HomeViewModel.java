package com.sixing.animalsprotect.ui.main.viewModel;

import androidx.lifecycle.ViewModel;

import com.sixing.animalsprotect.bean.HttpResponse;
import com.sixing.animalsprotect.bean.SearchResult;
import com.sixing.animalsprotect.http.HttpRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private HttpRepository httpRepository;
    public HomeViewModel(){httpRepository=new HttpRepository();}

    public List<SearchResult> getSomeAnimals(){
        List<SearchResult> searchResults=null;
        Call<HttpResponse<List<SearchResult>>> call=null;
        try{
            call=httpRepository.getAnimalInformations();
            Response<HttpResponse<List<SearchResult>>> repo=call.execute();
            if(repo.isSuccessful()){
                if (repo.body().getCode().equals("200")){
                    searchResults=repo.body().getData();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return searchResults;
    }
}
