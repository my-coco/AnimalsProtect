package com.sixing.animalsprotect.ui.photo.ViewModel;

import android.util.Base64;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sixing.animalsprotect.bean.HttpResponse;
import com.sixing.animalsprotect.http.HttpRepository;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import retrofit2.Call;
import retrofit2.Response;

public class PhotoViewModel extends ViewModel {
    public MutableLiveData<Integer> sum=new MutableLiveData<>();
    public MutableLiveData<Integer> max=new MutableLiveData<>();
    private HttpRepository httpRepository;
    public PhotoViewModel() {
        httpRepository=new HttpRepository();
    }

    public Boolean setUserPhoto(String user_phone,String path){
        Boolean result=false;
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("user_phone",user_phone);
            InputStream inputStream=new FileInputStream(path);
            byte[] bytes=new byte[inputStream.available()];
            inputStream.read(bytes);
            String base=Base64.encodeToString(bytes,Base64.DEFAULT);
            jsonObject.put("inputStream",base);
            Call<HttpResponse<Boolean>> call=httpRepository.setUserPhoto(httpRepository.getRequestBody(jsonObject));
            Response<HttpResponse<Boolean>> response=call.execute();
            if(response.isSuccessful()){
                if(response.body().getData()){
                    result=true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Boolean setUserBgPhoto(String user_phone,String path){
        Boolean result=false;
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("user_phone",user_phone);
            InputStream inputStream=new FileInputStream(path);
            byte[] bytes=new byte[inputStream.available()];
            inputStream.read(bytes);
            String base=Base64.encodeToString(bytes,Base64.DEFAULT);
            jsonObject.put("inputStream",base);
            Call<HttpResponse<Boolean>> call=httpRepository.setUserBgPhoto(httpRepository.getRequestBody(jsonObject));
            Response<HttpResponse<Boolean>> response=call.execute();
            if(response.isSuccessful()){
                if(response.body().getData()){
                    result=true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
