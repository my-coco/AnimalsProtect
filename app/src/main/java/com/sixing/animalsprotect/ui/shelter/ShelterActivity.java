package com.sixing.animalsprotect.ui.shelter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.adapter.AccountsAdapter;
import com.sixing.animalsprotect.adapter.AnimalAdopterAdapter;
import com.sixing.animalsprotect.adapter.BroadcastAdapter;
import com.sixing.animalsprotect.bean.Account;
import com.sixing.animalsprotect.bean.AnimalInformation;
import com.sixing.animalsprotect.bean.Broadcast;
import com.sixing.animalsprotect.bean.BroadcastCommit;
import com.sixing.animalsprotect.bean.Notice;
import com.sixing.animalsprotect.bean.ShelterInformation;
import com.sixing.animalsprotect.constant.Constants;
import com.sixing.animalsprotect.ui.accounts.AccountsActivity;
import com.sixing.animalsprotect.ui.animal.AnimalActivity;
import com.sixing.animalsprotect.ui.report.ReportActivity;
import com.sixing.animalsprotect.ui.shelter.viewmodel.ShelterViewModel;
import com.sixing.animalsprotect.util.SharadUtil;
import com.sixing.animalsprotect.widget.MyRecycleView;

import java.util.ArrayList;
import java.util.List;

public class ShelterActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView org_boardcast,org_animals,org_accounts,org_name,org_address;
    private AccountsAdapter accountsAdapter;
    private List<Account> accountsList;
    private List<Notice> notices;
    private List<Broadcast> broadcasts;
    private List<AnimalInformation> animalInformations;
    private MyRecycleView listView;
    private BroadcastAdapter broadcastAdapter;
    private ImageView back_ic,org_report;
    private AnimalAdopterAdapter animalAdopterAdapter;
    private String TAG="OrganActivity";
    private ViewModelProvider viewModelProvider;
    private ShelterViewModel shelterViewModel;
    private int list_id=0;
    private String shelterId;
    private ShelterInformation shelterInformation=null;
    private Handler handler;
    private Context context;
    private View editView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organ);
        init();
        initList();
        initListener();
    }

    private void init(){
        handler=new Handler(callback);
        context=this;
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
        listView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
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
                listView.post(new Runnable() {
                    @Override
                    public void run() {
                        listView.setAdapter(getBroadcastAdapter());
                    }
                });
                list_id=0;
            }
        }).start();
    }


    private BroadcastAdapter getBroadcastAdapter(){
        broadcasts.clear();
        Drawable drawable=getDrawable(R.drawable.iu);
        for (Notice notice:notices){
            try{
                while (shelterInformation==null){
                    Thread.sleep(50);
                }
                Broadcast broadcast=new Broadcast(notice.getId(),drawable,shelterInformation.getName(),notice.getText(),notice.getDate(),notice.getLike(),notice.getWords());
                broadcasts.add(broadcast);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        broadcastAdapter=new BroadcastAdapter(broadcasts,this, handler);
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
    private Handler.Callback callback=new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    Toast.makeText(context,"评论失败",Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    editView=View.inflate(context,R.layout.fragment_edit,null);
                    EditText editText=editView.findViewById(R.id.edit);
                    editView.findViewById(R.id.bg).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            editText.clearFocus();
                            ((ViewGroup)editView.getParent()).removeView(editView);
                        }
                    });
                    TextView send_btn=editView.findViewById(R.id.send_btn);
                    Bundle bundle=msg.getData();
                    send_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(editText.getText().toString().length()>0){
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(shelterViewModel.commitNotice(bundle.getString("Id"), SharadUtil.getString(Constants.USERPHONE,""),editText.getText().toString())){
                                            Message message=Message.obtain();
                                            Bundle bundle1=new Bundle();
                                            message.what=2;
                                            bundle1.putString("text",editText.getText().toString());
                                            bundle1.putInt("position",bundle.getInt("position"));
                                            message.setData(bundle1);
                                            handler.sendMessage(message);
                                        }else{
                                            handler.sendEmptyMessage(0);
                                        }
                                    }
                                }).start();
                            }else{
                                handler.sendEmptyMessage(3);
                            }
                        }
                    });
                    addContentView(editView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    break;
                case 2:
                    Toast.makeText(context,"评论成功",Toast.LENGTH_SHORT).show();
                    ((ViewGroup)editView.getParent()).removeView(editView);
                    BroadcastAdapter broadcastAdapter=(BroadcastAdapter)listView.getAdapter();
                    Bundle bundle1=msg.getData();
                    int p=bundle1.getInt("position");
                    String text=bundle1.getString("text");
                    broadcastAdapter.getBroadcasts().get(p).getBroadcastCommits().add(new BroadcastCommit(SharadUtil.getString(Constants.USERNAME,""),SharadUtil.getString(Constants.USERPHONE,""),bundle1.getString("text")));
                    ((BroadcastAdapter)listView.getAdapter()).notifyItemChanged(p);
                    break;
                case 3:
                    Toast.makeText(context,"评论不能为空",Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    };
}