package com.sixing.animalsprotect.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.sixing.animalsprotect.util.SharadUtil;
import com.sixing.animalsprotect.widget.HexagonView;

import java.util.ArrayList;
import java.util.List;

public class NewRankFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener{
    private View view;
    private TextView province,city,county,sum_1,sum_2,sum_3;
    private HexagonView hexagonView_1,hexagonView_2,hexagonView_3;
    private ListView list,province_list,city_list,county_list;
    private LinearLayout locatLinearLayout;
    private List<HttpResponseGhost.AddressInfor> addressInforsP;
    private List<HttpResponseGhost.AddressInfor> addressInforsCity;
    private List<HttpResponseGhost.AddressInfor> addressInforsCounty;
    private RankViewModel rankViewModel;
    private ViewModelProvider viewModelProvider;
    private String province_id,city_id,county_id;
    private int province_index,city_index,county_index;
    private ConstraintLayout locatBackground,close_logcat;
    private AnimalRankAdapter animalRankAdapter;
    public Handler handler;
    private List<AnimalRank> animalRankList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_rank_new,container,false);
        init();
        return view;
    }

    private void init(){
        viewModelProvider=new ViewModelProvider(this);
        rankViewModel=viewModelProvider.get(RankViewModel.class);
        handler=new Handler(callback);
        animalRankList=new ArrayList<>();
        province=view.findViewById(R.id.province);
        city=view.findViewById(R.id.city);
        county=view.findViewById(R.id.county);
        sum_1=view.findViewById(R.id.sum_1);
        sum_2=view.findViewById(R.id.sum_2);
        sum_3=view.findViewById(R.id.sum_3);
        hexagonView_1=view.findViewById(R.id.hexagonView_1);
        hexagonView_2=view.findViewById(R.id.hexagonView_2);
        hexagonView_3=view.findViewById(R.id.hexagonView_3);
        list=view.findViewById(R.id.list);
        province_list=view.findViewById(R.id.province_list);
        city_list=view.findViewById(R.id.city_list);
        county_list=view.findViewById(R.id.county_list);
        locatLinearLayout=view.findViewById(R.id.locatLinearLayout);
        locatBackground=view.findViewById(R.id.locatBackground);
        close_logcat=view.findViewById(R.id.close_logcat);

        hexagonView_1.setOnClickListener(this);
        hexagonView_2.setOnClickListener(this);
        hexagonView_3.setOnClickListener(this);
        close_logcat.setOnClickListener(this);
        province.setOnClickListener(this);
        city.setOnClickListener(this);
        county.setOnClickListener(this);
        province_list.setOnItemClickListener(this);
        city_list.setOnItemClickListener(this);
        county_list.setOnItemClickListener(this);
        list.setOnItemClickListener(this);
        addressInforsP=new ArrayList<>();
        addressInforsCity=new ArrayList<>();
        addressInforsCounty=new ArrayList<>();
    }

    private void alterList(String id,ListView listView){
        List<String> address=new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<HttpResponseGhost.AddressInfor> addressInfors=rankViewModel.getAddress(id);
                for (HttpResponseGhost.AddressInfor addressInfor:addressInfors){
                    address.add(addressInfor.areaName);
                }
                switch (listView.getId()){
                    case R.id.province_list:
                        addressInforsP.clear();
                        addressInforsP.addAll(addressInfors);
                        break;
                    case R.id.city_list:
                        addressInforsCity.clear();
                        addressInforsCity.addAll(addressInfors);
                        break;
                    case R.id.county_list:
                        addressInforsCounty.clear();
                        addressInforsCounty.addAll(addressInfors);
                        break;
                }
                AddressAdapter addressAdapter=new AddressAdapter(getContext(),address);
                listView.post(new Runnable() {
                    @Override
                    public void run() {
                        listView.setAdapter(addressAdapter);
                    }
                });
            }
        }).start();


    }


    public void initList(String province, String city, String country){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<AnimalInformation> animalInformations=rankViewModel.getAnimalRank(province,city,country);
                int sum=0;
                List<AnimalRank> animalRanks=new ArrayList<>();
                if(animalInformations.size()==0){
                    handler.sendEmptyMessage(1);
                }
                for (AnimalInformation animalInformation: animalInformations){
                    AnimalRank animalRank_temp=new AnimalRank(animalInformation.getId(),getResources().getDrawable(R.drawable.xiaomao1),animalInformation.getName(),String.valueOf(animalInformation.getHot()),String.valueOf(animalRankList.size()+1));
                    animalRankList.add(animalRank_temp);
                    if(sum>=3){
                        animalRanks.add(animalRank_temp);
                    }else{
                        int sum_ui=sum;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                switch (sum_ui){
                                    case 0:
                                        sum_1.setText(animalRank_temp.getAnimal_votes());
                                        hexagonView_1.setImageDrawable(animalRank_temp.getAnimal_pic());
                                        break;
                                    case 1:
                                        sum_2.setText(animalRank_temp.getAnimal_votes());
                                        hexagonView_2.setImageDrawable(animalRank_temp.getAnimal_pic());
                                        break;
                                    case 2:
                                        sum_3.setText(animalRank_temp.getAnimal_votes());
                                        hexagonView_3.setImageDrawable(animalRank_temp.getAnimal_pic());
                                        break;
                                }
                            }
                        });
                        sum++;
                    }
                }
                if(animalRanks.size()>0){
                    animalRankAdapter=new AnimalRankAdapter(getContext(),animalRanks);
                    list.post(new Runnable() {
                        @Override
                        public void run() {
                            list.setAdapter(animalRankAdapter);
                        }
                    });
                }
            }
        }).start();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.province:
                locatBackground.setVisibility(View.VISIBLE);
                province_list.setVisibility(View.VISIBLE);
                alterList("0",province_list);
                break;
            case R.id.city:
                locatBackground.setVisibility(View.VISIBLE);
                city_list.setVisibility(View.VISIBLE);
                alterList(province_id,city_list);
                break;
            case R.id.county:
                locatBackground.setVisibility(View.VISIBLE);
                county_list.setVisibility(View.VISIBLE);
                alterList(city_id,county_list);
                break;
            case R.id.close_logcat:
                province_list.setVisibility(View.GONE);
                city_list.setVisibility(View.GONE);
                county_list.setVisibility(View.GONE);
                locatBackground.setVisibility(View.GONE);
                break;
            case R.id.hexagonView_1:
                try {
                    Intent intent=new Intent();
                    intent.setClass(getContext(), AnimalActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString(Constants.ANIMALID,animalRankList.get(0).getAnimal_id());
                    intent.putExtra(Constants.ANIMALIDBUNDLE,bundle);
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.hexagonView_2:
                try {
                    Intent intent=new Intent();
                    intent.setClass(getContext(), AnimalActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString(Constants.ANIMALID,animalRankList.get(1).getAnimal_id());
                    intent.putExtra(Constants.ANIMALIDBUNDLE,bundle);
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.hexagonView_3:
                try {
                    Intent intent=new Intent();
                    intent.setClass(getContext(), AnimalActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString(Constants.ANIMALID,animalRankList.get(2).getAnimal_id());
                    intent.putExtra(Constants.ANIMALIDBUNDLE,bundle);
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            switch (parent.getId()){
                case R.id.list:
                    try {
                        Intent intent=new Intent();
                        intent.setClass(getContext(), AnimalActivity.class);
                        Bundle bundle=new Bundle();
                        AnimalRank animalRank=(AnimalRank) parent.getAdapter().getItem(position);
                        bundle.putString(Constants.ANIMALID,animalRank.getAnimal_id());
                        intent.putExtra(Constants.ANIMALIDBUNDLE,bundle);
                        startActivity(intent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case R.id.province_list:
                    city_list.setVisibility(View.VISIBLE);
                    province_id=addressInforsP.get(position).id;
                    province_index=position;
                    alterList(addressInforsP.get(position).id,city_list);
                    break;
                case R.id.city_list:
                    county_list.setVisibility(View.VISIBLE);
                    city_id=addressInforsCity.get(position).id;
                    city_index=position;
                    alterList(addressInforsCity.get(position).id,county_list);
                    break;
                case R.id.county_list:
                    county_id=addressInforsCounty.get(position).id;
                    county_index=position;
                    province.setText(addressInforsP.get(province_index).areaName);
                    city.setText(addressInforsCity.get(city_index).areaName);
                    county.setText(addressInforsCounty.get(county_index).areaName);
                    province_list.setVisibility(View.GONE);
                    city_list.setVisibility(View.GONE);
                    county_list.setVisibility(View.GONE);
                    locatBackground.setVisibility(View.GONE);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            clearRank();
                        }
                    });
                    initList(addressInforsP.get(province_index).areaName,addressInforsCity.get(city_index).areaName,addressInforsCounty.get(county_index).areaName);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Handler.Callback callback=new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    initAddress();
                    break;
                case 1:
                    Toast.makeText(getContext(),"该地区暂时没有排名",Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    };

    private void initAddress(){
        province.setText(SharadUtil.getString(Constants.LOCATIONPROVINCE,""));
        city.setText(SharadUtil.getString(Constants.LOCATIONCITY,""));
        county.setText(SharadUtil.getString(Constants.LOCATIONCOUNTRY,""));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<HttpResponseGhost.AddressInfor> addressInfors=rankViewModel.getAddress("0");
                String p=SharadUtil.getString(Constants.LOCATIONPROVINCE,"z");
                addressInforsP.clear();
                addressInforsP.addAll(addressInfors);
                province_index=0;
                for (HttpResponseGhost.AddressInfor addressInfor:addressInfors){
                    if(addressInfor.areaName.equals(p)||addressInfor.areaName.equals(p.substring(0,p.length()-1))){
                        province_id=addressInfor.id;
                        break;
                    }
                    province_index++;
                }
                String c=SharadUtil.getString(Constants.LOCATIONCITY,"z");
                List<HttpResponseGhost.AddressInfor> addressInfors2=rankViewModel.getAddress(province_id);
                addressInforsCity.clear();
                addressInforsCity.addAll(addressInfors2);
                city_index=0;
                for (HttpResponseGhost.AddressInfor addressInfor:addressInfors2){
                    if(addressInfor.areaName.equals(c)||addressInfor.areaName.equals(c.substring(0,c.length()-1))){
                        city_id=addressInfor.id;
                        break;
                    }
                    city_index++;
                }
                initList(p,c,SharadUtil.getString(Constants.LOCATIONCOUNTRY,"z"));
            }
        }).start();
    }

    private void clearRank(){
        sum_1.setText("");
        sum_2.setText("");
        sum_3.setText("");
        animalRankList.clear();
        list.setAdapter(null);
    }
}
