package com.sixing.animalsprotect.ui.shelter.viewmodel;

import androidx.lifecycle.ViewModel;

import com.sixing.animalsprotect.bean.Account;
import com.sixing.animalsprotect.bean.AnimalInformation;
import com.sixing.animalsprotect.bean.HttpResponse;
import com.sixing.animalsprotect.bean.Notice;
import com.sixing.animalsprotect.bean.ShelterInformation;
import com.sixing.animalsprotect.http.HttpRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ShelterViewModel extends ViewModel {
    private HttpRepository httpRepository;
    public ShelterViewModel(){ httpRepository=new HttpRepository();}

    public ShelterInformation getShelterInformationById(String id){
        Call<HttpResponse<List<ShelterInformation>>> call=null;
        ShelterInformation shelterInformation=null;
        try{
            call=httpRepository.getShelterInformationById(id);
            Response<HttpResponse<List<ShelterInformation>>> response=call.execute();
            if (response.isSuccessful()){
                if (response.body().getCode().equals("200")){
                    shelterInformation=response.body().getData().get(0);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return shelterInformation;
    }

    public List<Account> getAccountList(String id){
        Call<HttpResponse<List<Account>>> call=null;
        List<Account> accounts=null;
        try{
            call= httpRepository.getAccountList(id);
            Response<HttpResponse<List<Account>>> repo=call.execute();
            if(repo.isSuccessful()){
                if(repo.body().getCode().equals("200")){
                    accounts=repo.body().getData();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return accounts;
    }

    public List<AnimalInformation> getAnimalList(String id){
        Call<HttpResponse<List<AnimalInformation>>> call=null;
        List<AnimalInformation> animalInformations=null;
        try{
            call= httpRepository.getAnimalList(id);
            Response<HttpResponse<List<AnimalInformation>>> response=call.execute();
            if(response.isSuccessful()){
                if(response.body().getCode().equals("200")){
                    animalInformations=response.body().getData();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return animalInformations;
    }

    public List<Notice> getNoticeList(String id, int isAnimal){
        List<Notice> notices=null;
        Call<HttpResponse<List<Notice>>> call=httpRepository.getNoticeList(id,isAnimal);
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
            Call<HttpResponse<Boolean>> call=httpRepository.commentNotice(noticeId,userId,text);
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
