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

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HttpApi {
    //获取全国省市区
    @GET("/ad_area/division_de.u")
    Call<HttpResponseGhost<List<HttpResponseGhost.AddressInfor>>> getAddress(@Query("uid") int uid, @Query("appkey") String appkey, @Query("parentId") String parentId);

    //获取小动物热度排名
    @GET("/animalprotection/getRankList")
    Call<HttpResponse<List<AnimalInformation>>> getAnimalRank(@Query("province") String province, @Query("city") String city, @Query("country") String country);

    //根据小动物ID获取小动物所有信息
    @GET("/animalprotection/getAnimalInformationById")
    Call<HttpResponse<List<AnimalInformation>>> getAnimalInformation(@Query("id") String id);

    //根据ID获取动态列表
    @GET("/animalprotection/getNoticeList")
    Call<HttpResponse<List<Notice>>> getNoticeList(@Query("id") String id,@Query("isAnimal") int isAnimal);

    //根据ID获取机构信息
    @GET("/animalprotection/getShelterInformationById")
    Call<HttpResponse<List<ShelterInformation>>> getShelterInformationById(@Query("id") String id);

    //根据ID获取机构流水
    @GET("/animalprotection/getAccountList")
    Call<HttpResponse<List<Account>>> getAccountList(@Query("id") String id);

    //根据ID获取机构动物
    @GET("/animalprotection/getAnimalList")
    Call<HttpResponse<List<AnimalInformation>>> getAnimalList(@Query("id") String id);

    //搜索功能实现
    @GET("/animalprotection/getAnimalorShelter")
    Call<HttpResponse<List<SearchResult>>> getAnimalorShelter(@Query("key") String key);

    //获取所有机构
    @GET("/animalprotection/getShelters")
    Call<HttpResponse<List<SearchResult>>> getShelterInformations();

    //获取所有动物
    @GET("/animalprotection/getAnimals")
    Call<HttpResponse<List<SearchResult>>> getAnimalInformations();

    //查询用户
    @GET("/animalprotection/getUserinfo")
    Call<HttpResponse<UserInformation>> getUserInformation(@Query("user_phone") String user_phone,@Query("user_password") String user_password);

    //查询用户的收养信息
    @GET("/animalprotection/getAdoptions")
    Call<HttpResponse<List<Adoption>>> getAdoptions(@Query("user_phone") String user_phone);
}
