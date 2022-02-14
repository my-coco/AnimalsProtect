package com.sixing.animalsprotect.ui.search.viewmodel;

import androidx.lifecycle.ViewModel;

import com.sixing.animalsprotect.bean.HttpResponse;
import com.sixing.animalsprotect.bean.SearchResult;
import com.sixing.animalsprotect.http.HttpRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class SearchResultViewModel extends ViewModel {
    private HttpRepository httpRepository;
    public SearchResultViewModel(){httpRepository=new HttpRepository();}

    public List<SearchResult> getAnimalorShelter(String key){
        Call<HttpResponse<List<SearchResult>>> call=null;
        List<SearchResult> results=null;
        try{
            switch (key){
                case "animal_all":
                    call= httpRepository.getAnimalInformations();
                    Response<HttpResponse<List<SearchResult>>> repo=call.execute();
                    if (repo.isSuccessful()){
                        if (repo.body().getCode().equals("200")){
                            results=repo.body().getData();
                        }
                    }
                    break;
                case "shelter_all":
                    call= httpRepository.getShelterInformations();
                    Response<HttpResponse<List<SearchResult>>> repo2=call.execute();
                    if (repo2.isSuccessful()){
                        if (repo2.body().getCode().equals("200")){
                            results=repo2.body().getData();
                        }
                    }
                    break;
                default:
                    call= httpRepository.getAnimalorShelter(key);
                    Response<HttpResponse<List<SearchResult>>> repo3=call.execute();
                    if (repo3.isSuccessful()){
                        if (repo3.body().getCode().equals("200")){
                            results=repo3.body().getData();
                        }
                    }
                    break;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }

}
