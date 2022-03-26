package com.sixing.animalsprotect.ui.animal.viewmodel;

import androidx.lifecycle.ViewModel;

import com.sixing.animalsprotect.bean.AnimalInformation;
import com.sixing.animalsprotect.bean.HttpResponse;
import com.sixing.animalsprotect.bean.Notice;
import com.sixing.animalsprotect.http.HttpRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AnimalModel extends ViewModel {
    private HttpRepository repository;
    public AnimalModel(){
        repository=new HttpRepository();
    }
    private String TAG="AnimalModel";

    public AnimalInformation getAnimalInformation(String id){
        AnimalInformation animalInformation=null;
        Call<HttpResponse<List<AnimalInformation>>> call=repository.getAnimalInformation(id);
        try {
            Response<HttpResponse<List<AnimalInformation>>> repo=call.execute();
            if(repo.isSuccessful()){
                if (repo.body().getCode().equals("200")){
                    animalInformation=repo.body().getData().get(0);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return animalInformation;
    }

    public List<Notice> getNoticeList(String id,int isAnimal){
        List<Notice> notices=null;
        Call<HttpResponse<List<Notice>>> call=repository.getNoticeList(id,isAnimal);
        try{
            Response<HttpResponse<List<Notice>>> repo=call.execute();
            if(repo.isSuccessful()){
                if(repo.body().getCode().equals("200")){
                    notices=repo.body().getData();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return notices;
    }

    public Boolean commitNotice(String noticeId,String userId,String text){
        try{
            Call<HttpResponse<Boolean>> call=repository.commentNotice(noticeId,userId,text);
            Response<HttpResponse<Boolean>> response=call.execute();
            if(response.isSuccessful()){
                if(response.body().getData()){
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
