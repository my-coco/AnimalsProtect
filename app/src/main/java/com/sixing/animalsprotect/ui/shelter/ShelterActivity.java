package com.sixing.animalsprotect.ui.shelter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.adapter.AccountsAdapter;
import com.sixing.animalsprotect.adapter.AnimalAdopterAdapter;
import com.sixing.animalsprotect.adapter.BroadcastAdapter;
import com.sixing.animalsprotect.bean.Account;
import com.sixing.animalsprotect.bean.AnimalInformation;
import com.sixing.animalsprotect.bean.Broadcast;
import com.sixing.animalsprotect.bean.Notice;
import com.sixing.animalsprotect.bean.ShelterInformation;
import com.sixing.animalsprotect.constant.Constants;
import com.sixing.animalsprotect.ui.accounts.AccountsActivity;
import com.sixing.animalsprotect.ui.animal.AnimalActivity;
import com.sixing.animalsprotect.ui.report.ReportActivity;
import com.sixing.animalsprotect.ui.shelter.viewmodel.ShelterViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShelterActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private TextView org_boardcast,org_animals,org_accounts,org_name,org_address;
    private AccountsAdapter accountsAdapter;
    private List<Account> accountsList;
    private List<Notice> notices;
    private List<Broadcast> broadcasts;
    private List<AnimalInformation> animalInformations;
    private ListView listView;
    private BroadcastAdapter broadcastAdapter;
    private ImageView back_ic,org_report;
    private AnimalAdopterAdapter animalAdopterAdapter;
    private String TAG="OrganActivity";
    private ViewModelProvider viewModelProvider;
    private ShelterViewModel shelterViewModel;
    private int list_id=0;
    private String shelterId;
    private ShelterInformation shelterInformation=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organ);
        init();
        initList();
        initListener();
    }

    private void init(){
        notices=new ArrayList<>();
        animalInformations=new ArrayList<>();
        broadcasts=new ArrayList<>();
        accountsList=new ArrayList<>();
        org_report=findViewById(R.id.org_report);
        org_boardcast=findViewById(R.id.org_boardcast);
        org_animals=findViewById(R.id.org_animals);
        org_accounts=findViewById(R.id.org_accounts);
        listView=findViewById(R.id.list);
        back_ic=findViewById(R.id.back_ic);
        org_name=findViewById(R.id.org_name);
        org_address=findViewById(R.id.org_address);

        viewModelProvider=new ViewModelProvider(this);
        shelterViewModel=viewModelProvider.get(ShelterViewModel.class);

        Intent intent=getIntent();
        try {
            shelterId=intent.getStringExtra("shelterId");
            initAccountList(shelterId);
            initShelterInformation(shelterId);
            initAnimalList(shelterId);
            initBroadcastList(shelterId,0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initList(){
        org_boardcast.setTextColor(Color.BLACK);
        listView.setAdapter(getBroadcastAdapter());
    }

    private void initListener(){
        org_boardcast.setOnClickListener(this);
        org_animals.setOnClickListener(this);
        org_accounts.setOnClickListener(this);
        back_ic.setOnClickListener(this);
        org_report.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    private void initShelterInformation(String id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                shelterInformation=shelterViewModel.getShelterInformationById(id);
                ShelterActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        org_name.setText(shelterInformation.getName());
                        org_address.setText(shelterInformation.getProvince()+shelterInformation.getCity()+shelterInformation.getCountry()+shelterInformation.getDetailedAddress());
                    }
                });
            }
        }).start();

    }

    private void initAccountList(String id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                accountsList=shelterViewModel.getAccountList(id);
            }
        }).start();
    }

    private void initAnimalList(String id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                animalInformations=shelterViewModel.getAnimalList(id);
            }
        }).start();
    }

    private void initBroadcastList(String id,int isAnimal){
        new Thread(new Runnable() {
            @Override
            public void run() {
                notices=shelterViewModel.getNoticeList(id,isAnimal);
            }
        }).start();
    }


    private BroadcastAdapter getBroadcastAdapter(){
        broadcasts.clear();
        Drawable drawable=getDrawable(R.drawable.iu);
        for (Notice notice:notices){
            Broadcast broadcast=new Broadcast(drawable,shelterInformation.getName(),notice.getText(),notice.getDate(),null,null);
            broadcasts.add(broadcast);
        }
        broadcastAdapter=new BroadcastAdapter(broadcasts,this, this);
        return broadcastAdapter;
    }

    private AnimalAdopterAdapter getanimalAdopterAdapter(){
        animalAdopterAdapter=new AnimalAdopterAdapter(this,animalInformations,this);
        return animalAdopterAdapter;
    }

    private AccountsAdapter getAccountsAdapter(){
        accountsAdapter=new AccountsAdapter(accountsList,this);
        return accountsAdapter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.org_boardcast:
                if(list_id!=0){
                    org_boardcast.setTextColor(Color.BLACK);
                    org_animals.setTextColor(getColor(R.color.org_grayness));
                    org_accounts.setTextColor(getColor(R.color.org_grayness));
                    listView.setAdapter(getBroadcastAdapter());
                    list_id=0;
                }
                break;
            case R.id.org_animals:
                if(list_id!=1){
                    org_animals.setTextColor(Color.BLACK);
                    org_boardcast.setTextColor(getColor(R.color.org_grayness));
                    org_accounts.setTextColor(getColor(R.color.org_grayness));
                    listView.setAdapter(getanimalAdopterAdapter());
                    list_id=1;
                }
                break;
            case R.id.org_accounts:
                if(list_id!=2){
                    org_accounts.setTextColor(Color.BLACK);
                    org_animals.setTextColor(getColor(R.color.org_grayness));
                    org_boardcast.setTextColor(getColor(R.color.org_grayness));
                    listView.setAdapter(getAccountsAdapter());
                    list_id=2;
                }
                break;
            case R.id.back_ic:
                finish();
                break;
            case R.id.org_report:
                Intent intent=new Intent(this, ReportActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.list:
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                switch (list_id){
                    //animal
                    case 1:
                        intent.setClass(this, AnimalActivity.class);
                        AnimalInformation animalInformation=animalInformations.get(position);
                        bundle.putString(Constants.ANIMALID,animalInformation.getId());
                        intent.putExtra(Constants.ANIMALIDBUNDLE,bundle);
                        startActivity(intent);
                        break;
                    //account
                    case 2:
                        intent.setClass(this,AccountsActivity.class);
                        Account account=accountsList.get(position);
                        bundle.putString("title",account.getTitle());
                        bundle.putString("reason",account.getReason());
                        bundle.putString("date",account.getDate());
                        intent.putExtra(Constants.ACCOUNTBUNDLE,bundle);
                        startActivity(intent);
                        break;
                    //notice
                    case 3:break;
                }
                break;
        }
    }
}