package com.sixing.animalsprotect.http;

import com.sixing.animalsprotect.bean.Account;
import com.sixing.animalsprotect.bean.Adoption;
import com.sixing.animalsprotect.bean.AnimalInformation;
import com.sixing.animalsprotect.bean.HttpResponse;
import com.sixing.animalsprotect.bean.HttpResponseGhost;
import com.sixing.animalsprotect.bean.Notice;
import com.sixing.animalsprotect.bean.SearchResult;
import com.sixing.animalsprotect.bean.ShelterInformation;
import com.sixing.animalsprotect.bean.UserInformation;

import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRepository implements HttpApi{
    private String baseUrl="http://192.168.162.234:8080";

    @Override
    public Call<HttpResponseGhost<List<HttpResponseGhost.AddressInfor>>> getAddress(int uid,String appkey,String parentId) {
        String url="http://area.ylapi.cn";
        Call<HttpResponseGhost<List<HttpResponseGhost.AddressInfor>>> repo=null;
        try{
            repo=getHttpApi(url).getAddress(uid, appkey, parentId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return repo;
    }

    @Override
    public Call<HttpResponse<List<AnimalInformation>>> getAnimalRank(String province, String city, String country) {
        Call<HttpResponse<List<AnimalInformation>>> repo=null;
        try {
            repo=getHttpApi(baseUrl).getAnimalRank(province,city,country);
        }catch (Exception e){
            e.printStackTrace();
        }
        return repo;
    }

    @Override
    public Call<HttpResponse<List<AnimalInformation>>> getAnimalInformation(String id) {
        Call<HttpResponse<List<AnimalInformation>>> res=null;
        try{
            res=getHttpApi(baseUrl).getAnimalInformation(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Call<HttpResponse<List<Notice>>> getNoticeList(String id, int isAnimal) {
        Call<HttpResponse<List<Notice>>> res=null;
        try{
            res=getHttpApi(baseUrl).getNoticeList(id,isAnimal);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Call<HttpResponse<List<ShelterInformation>>> getShelterInformationById(String id) {
        Call<HttpResponse<List<ShelterInformation>>> res=null;
        try{
            res=getHttpApi(baseUrl).getShelterInformationById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Call<HttpResponse<List<Account>>> getAccountList(String id) {
        Call<HttpResponse<List<Account>>> res=null;
        try{
            res=getHttpApi(baseUrl).getAccountList(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Call<HttpResponse<List<AnimalInformation>>> getAnimalList(String id) {
        Call<HttpResponse<List<AnimalInformation>>> res=null;
        try{
            res=getHttpApi(baseUrl).getAnimalList(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Call<HttpResponse<List<SearchResult>>> getAnimalorShelter(String key) {
        Call<HttpResponse<List<SearchResult>>> res=null;
        try{
            res=getHttpApi(baseUrl).getAnimalorShelter(key);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Call<HttpResponse<List<SearchResult>>> getShelterInformations() {
        Call<HttpResponse<List<SearchResult>>> res=null;
        try{
            res=getHttpApi(baseUrl).getShelterInformations();
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Call<HttpResponse<List<SearchResult>>> getAnimalInformations() {
        Call<HttpResponse<List<SearchResult>>> res=null;
        try{
            res=getHttpApi(baseUrl).getAnimalInformations();
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Call<HttpResponse<UserInformation>> getUserInformation(String user_phone, String user_password) {
        Call<HttpResponse<UserInformation>> res=null;
        try{
            res=getHttpApi(baseUrl).getUserInformation(user_phone,user_password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Call<HttpResponse<List<Adoption>>> getAdoptions(String user_phone) {
        Call<HttpResponse<List<Adoption>>> call=null;
        try{
            call=getHttpApi(baseUrl).getAdoptions(user_phone);
        }catch (Exception e){
            e.printStackTrace();
        }
        return call;
    }

    public RequestBody getRequestBody(JSONObject jsonObject){
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json-path+json"),String.valueOf(jsonObject));
        return requestBody;
    }

    private HttpApi getHttpApi(String url){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpApi httpApi=retrofit.create(HttpApi.class);
        return httpApi;
    }
}
