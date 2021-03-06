package com.sixing.animalsprotect.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.adapter.AnimalAdopterAdapter;
import com.sixing.animalsprotect.bean.Adoption;
import com.sixing.animalsprotect.bean.AnimalInformation;
import com.sixing.animalsprotect.constant.Constants;
import com.sixing.animalsprotect.ui.animal.AnimalActivity;
import com.sixing.animalsprotect.ui.main.viewModel.AdoptionViewModel;

import java.util.ArrayList;
import java.util.List;

public class AdoptionFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ListView listView;
    private View view;
    private AnimalAdopterAdapter animalAdopterAdapter;
    private ViewModelProvider viewModelProvider;
    private AdoptionViewModel adoptionViewModel;
    private Context context;
    private View.OnClickListener onClickListener=this;
    private SharedPreferences sharedPreferences;
    private List<Adoption> adoptions;
    private String TAG="AdoptionFragment";
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_adoption,container,false);
        Log.d("TAG", "onCreateView: "+TAG);
        init();
        initListener();
        initList();
        return view;
    }
    private void init(){
        context=getContext();
        adoptions=new ArrayList<>();
        viewModelProvider=new ViewModelProvider(this);
        adoptionViewModel=viewModelProvider.get(AdoptionViewModel.class);
        sharedPreferences=getActivity().getSharedPreferences(Constants.USERINFO,Context.MODE_PRIVATE);
        listView=view.findViewById(R.id.animals_list);
    }

    private void initList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                adoptions=adoptionViewModel.getAdoptions(sharedPreferences.getString(Constants.USERPHONE," "));
                List<AnimalInformation> animalInformations=new ArrayList<>();
                for (Adoption adoption:adoptions){
                    AnimalInformation animalInformation=new AnimalInformation();
                    animalInformation.setName(adoption.getAnimal_name());
                    animalInformation.setId(adoption.getAnimal_id());
                    animalInformation.setSurplusFood(adoption.getSurplusFood());
                    animalInformations.add(animalInformation);
                }
                animalAdopterAdapter=new AnimalAdopterAdapter(context,animalInformations,onClickListener);
                listView.post(new Runnable() {
                    @Override
                    public void run() {
                        listView.setAdapter(animalAdopterAdapter);
                    }
                });
            }
        }).start();

    }

    private void initListener(){
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=null;
        Bundle bundle=new Bundle();
        switch (parent.getId()){
            case R.id.animals_list:
                intent=new Intent(context, AnimalActivity.class);
                bundle.putString(Constants.ANIMALID,((AnimalInformation)parent.getAdapter().getItem(position)).getId());
                intent.putExtra(Constants.ANIMALIDBUNDLE,bundle);
                startActivity(intent);
                break;
        }
    }
}
