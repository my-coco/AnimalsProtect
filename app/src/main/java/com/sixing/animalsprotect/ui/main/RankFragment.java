package com.sixing.animalsprotect.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.adapter.AddressAdapter;
import com.sixing.animalsprotect.adapter.AnimalRankAdapter;
import com.sixing.animalsprotect.bean.AnimalInformation;
import com.sixing.animalsprotect.bean.AnimalRank;
import com.sixing.animalsprotect.bean.HttpResponseGhost;
import com.sixing.animalsprotect.constant.Constants;
import com.sixing.animalsprotect.ui.animal.AnimalActivity;
import com.sixing.animalsprotect.ui.main.viewModel.RankViewModel;

import java.util.ArrayList;
import java.util.List;

public class RankFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
    private List<AnimalRank> animalRanks;
    private String TAG="RankFragment";
    private List<HttpResponseGhost.AddressInfor> addressInfors=new ArrayList<>();
    private List<HttpResponseGhost.AddressInfor> addressInforsP=new ArrayList<>();
    private List<HttpResponseGhost.AddressInfor> addressInforsCity=new ArrayList<>();
    private List<HttpResponseGhost.AddressInfor> addressInforsCounty=new ArrayList<>();
    private View.OnClickListener onClickListener;
    private Context context;
    private ListView listView;
    private View view;
    private AnimalRankAdapter animalRankAdapter;
    private AddressAdapter addressAdapter;
    private Spinner province,city,county;
    private RankViewModel rankViewModel;
    private ViewModelProvider viewModelProvider;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_rank,container,false);
        Log.d("TAG", "onCreateView: "+TAG);
        init();
        initSpinner();
        initListener();
        alterSpinner("0",province);
        return view;
    }

    private void initListener() {
        listView.setOnItemClickListener(this);
        province.setOnItemSelectedListener(this);
        city.setOnItemSelectedListener(this);
        county.setOnItemSelectedListener(this);
    }

    public void init(){
        context=getContext();
        onClickListener=this;
        viewModelProvider=new ViewModelProvider(getActivity());
        rankViewModel=viewModelProvider.get(RankViewModel.class);

        animalRanks=new ArrayList<>();

        listView=view.findViewById(R.id.animal_rank_list);
        province=view.findViewById(R.id.province);
        city=view.findViewById(R.id.city);
        county=view.findViewById(R.id.county);
    }

    public void initList(String province, String city, String country){
        List<AnimalRank> animalRankList=new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<AnimalInformation> animalInformations=rankViewModel.getAnimalRank(province,city,country);
                for (AnimalInformation animalInformation: animalInformations){
                    AnimalRank animalRank_temp=new AnimalRank(animalInformation.getId(),context.getDrawable(R.drawable.xiaomao1),animalInformation.getName(),String.valueOf(animalInformation.getHot()),String.valueOf(animalRankList.size()+1));
                    animalRankList.add(animalRank_temp);
                }
                animalRankAdapter=new AnimalRankAdapter(context,animalRankList,onClickListener);
                listView.post(new Runnable() {
                    @Override
                    public void run() {
                        listView.setAdapter(animalRankAdapter);
                    }
                });
            }
        }).start();


    }

    public void initSpinner(){
        List<String> defaultaddress=new ArrayList<>();
        defaultaddress.add("请选择");
        addressAdapter=new AddressAdapter(context,defaultaddress);
        addressAdapter=new AddressAdapter(context,defaultaddress);
        addressAdapter=new AddressAdapter(context,defaultaddress);
        province.setAdapter(addressAdapter);
        province.setDropDownVerticalOffset((int) context.getResources().getDimension(R.dimen.size_27dp));
        city.setAdapter(addressAdapter);
        city.setDropDownVerticalOffset((int) context.getResources().getDimension(R.dimen.size_27dp));
        county.setAdapter(addressAdapter);
        county.setDropDownVerticalOffset((int) context.getResources().getDimension(R.dimen.size_27dp));

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.animal_rank_list:
                try {
                    Intent intent=new Intent();
                    intent.setClass(context, AnimalActivity.class);
                    Bundle bundle=new Bundle();
                    AnimalRank animalRank=(AnimalRank) parent.getAdapter().getItem(position);
                    bundle.putString(Constants.ANIMALID,animalRank.getAnimal_id());
                    intent.putExtra(Constants.ANIMALIDBUNDLE,bundle);
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    private void alterSpinner(String id,Spinner spinner){
        List<String> address=new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: "+id);
                addressInfors=rankViewModel.getAddress(id);
                for (HttpResponseGhost.AddressInfor addressInfor:addressInfors){
                    address.add(addressInfor.areaName);
                }
                switch (spinner.getId()){
                    case R.id.province:
                        addressInforsP.clear();
                        addressInforsP.addAll(addressInfors);
                        break;
                    case R.id.city:
                        addressInforsCity.clear();
                        addressInforsCity.addAll(addressInfors);
                        break;
                    case R.id.county:
                        addressInforsCounty.clear();
                        addressInforsCounty.addAll(addressInfors);
                        break;
                }
                AddressAdapter addressAdapter=new AddressAdapter(context,address);
                spinner.post(new Runnable() {
                    @Override
                    public void run() {
                        spinner.setAdapter(addressAdapter);
                    }
                });
            }
        }).start();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            switch (parent.getId()){
                case R.id.province:
                    Log.d(TAG, "onItemSelected: "+addressInforsP.get(position).areaName);
                    alterSpinner(addressInforsP.get(position).id,city);
                    break;
                case R.id.city:
                    Log.d(TAG, "onItemSelected: "+addressInforsCity.get(position).areaName);
                    alterSpinner(addressInforsCity.get(position).id,county);
                    break;
                case R.id.county:
                    initList(province.getSelectedItem().toString(),city.getSelectedItem().toString(),county.getSelectedItem().toString());
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){

            }
        }
    };
}
