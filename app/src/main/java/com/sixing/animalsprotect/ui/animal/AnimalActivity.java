package com.sixing.animalsprotect.ui.animal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.adapter.BroadcastAdapter;
import com.sixing.animalsprotect.bean.AnimalInformation;
import com.sixing.animalsprotect.bean.Broadcast;
import com.sixing.animalsprotect.bean.BroadcastCommit;
import com.sixing.animalsprotect.bean.Notice;
import com.sixing.animalsprotect.constant.Constants;
import com.sixing.animalsprotect.ui.animal.viewmodel.AnimalModel;
import com.sixing.animalsprotect.ui.shelter.ShelterActivity;
import com.sixing.animalsprotect.util.SharadUtil;
import com.sixing.animalsprotect.widget.MyRecycleView;
import com.tencentcloudapi.iotvideo.v20191126.models.LogData;

import java.util.ArrayList;
import java.util.List;

public class AnimalActivity extends AppCompatActivity implements View.OnClickListener {
    private MyRecycleView broadcast_list;
    private List<Broadcast> broadcasts;
    private BroadcastAdapter broadcastAdapter;
    private ImageView animal_card,close_ic,back_ic,nothing_ic;
    private ConstraintLayout card;
    private TextView org_entry,animal_name,animal_words,card_animal_name,card_animal_old,card_animal_kind,card_animal_sex,card_animal_introduce,nothing_tx;
    private String TAG="AnimalActivity";
    private AnimalModel animalModel;
    private ViewModelProvider viewModelProvider;
    private String animalId,shelterId;
    private AnimalInformation animalInformation;
    private View.OnClickListener onClickListener=this;
    private Handler handler;
    private Context context;
    private View editView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);
        init();
        try {
            animalId=getIntent().getBundleExtra(Constants.ANIMALIDBUNDLE).getString(Constants.ANIMALID,null);
            initAnimalInformation(animalId);
        }catch (Exception e){
            e.printStackTrace();

        }

        initListener();
    }
    private void init(){
        handler=new Handler(callback);
        context=this;
        broadcasts=new ArrayList<>();
        broadcast_list=findViewById(R.id.broadcast_list);
        broadcast_list.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        card=findViewById(R.id.card);
        close_ic=findViewById(R.id.close_ic);
        animal_card=findViewById(R.id.animal_card);
        org_entry=findViewById(R.id.org_entry);
        back_ic=findViewById(R.id.back_ic);
        animal_name=findViewById(R.id.animal_name);
        animal_words=findViewById(R.id.animal_words);
        card_animal_name=findViewById(R.id.card_animal_name);
        card_animal_old=findViewById(R.id.card_animal_old);
        card_animal_kind=findViewById(R.id.card_animal_kind);
        card_animal_sex=findViewById(R.id.card_animal_sex);
        card_animal_introduce=findViewById(R.id.card_animal_introduce);
        nothing_ic=findViewById(R.id.nothing_ic);
        nothing_tx=findViewById(R.id.nothing_tx);

        viewModelProvider=new ViewModelProvider(this);
        animalModel=viewModelProvider.get(AnimalModel.class);
    }

    private void initList(){
        Drawable drawable=getDrawable(R.drawable.xiaomao1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Notice> notices=animalModel.getNoticeList(animalId,1);
                if(notices.size()>0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            nothing_ic.setVisibility(View.GONE);
                            nothing_tx.setVisibility(View.GONE);
                        }
                    });
                }
                for (Notice notice:notices){
                    Broadcast broadcast=new Broadcast(notice.getId(),drawable,animalInformation.getName(),notice.getText(),notice.getDate(),notice.getLike(),notice.getWords());
                    broadcasts.add(broadcast);
                }
                broadcastAdapter=new BroadcastAdapter(broadcasts,getApplicationContext(),handler);
                broadcast_list.post(new Runnable() {
                    @Override
                    public void run() {
                        broadcast_list.setAdapter(broadcastAdapter);
                    }
                });
            }
        }).start();


        //用户点赞
//        List<String> prase_names=new ArrayList<>();
//        prase_names.add("用户甲");
//        prase_names.add("用户乙");
//        prase_names.add("用户丙");
        //用户评论
//        List<BroadcastCommit> broadcastCommits=new ArrayList<>();
//        String people_name="用户甲：";
//        String people_words="小猫猫真可爱";
//        for(int i=0;i<2;i++){
//            BroadcastCommit broadcastCommit=new BroadcastCommit(people_name,people_words);
//            broadcastCommits.add(broadcastCommit);
//        }
//        for(int i=0;i<5;i++){
//            Broadcast broadcast=new Broadcast(drawable,name,words,time,prase_names,broadcastCommits);
//            broadcasts.add(broadcast);
//        }
    }

    private void initListener(){
        animal_card.setOnClickListener(this);
        close_ic.setOnClickListener(this);
        org_entry.setOnClickListener(this);
        back_ic.setOnClickListener(this);
    }

    private void initAnimalInformation(String id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                animalInformation=animalModel.getAnimalInformation(id);
                AnimalActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        animal_name.setText(animalInformation.getName());
                        animal_words.setText(animalInformation.getOther());
                        card_animal_name.setText(animalInformation.getName());
                        card_animal_kind.setText(animalInformation.getKind());
                        card_animal_sex.setText(animalInformation.getSex());
                        card_animal_introduce.setText(animalInformation.getOther());
                        if (animalInformation.getOld()>12){
                            card_animal_old.setText(String.valueOf((int) animalInformation.getOld()/12)+"岁"+String.valueOf((int) animalInformation.getOld()%12)+"月");
                        }else if (animalInformation.getOld()==12){
                            card_animal_old.setText("1岁");
                        }else{
                            card_animal_old.setText(String.valueOf( animalInformation.getOld())+"月");
                        }
                        shelterId=animalInformation.getShelterID();
                        org_entry.setText(animalInformation.getShelterName());
                        initList();
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.animal_card:
                card.setVisibility(View.VISIBLE);
                break;
            case R.id.close_ic:
                card.setVisibility(View.GONE);
                break;
            case R.id.org_entry:
                Intent intent=new Intent();
                intent.setClass(this, ShelterActivity.class);
                intent.putExtra(Constants.SHELTERID,shelterId);
                startActivity(intent);
                break;  
            case R.id.back_ic:
                finish();
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
                                        if(animalModel.commitNotice(bundle.getString("Id"), SharadUtil.getString(Constants.USERPHONE,""),editText.getText().toString())){
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
                    BroadcastAdapter broadcastAdapter=(BroadcastAdapter)broadcast_list.getAdapter();
                    Bundle bundle1=msg.getData();
                    int p=bundle1.getInt("position");
                    String text=bundle1.getString("text");
                    broadcastAdapter.getBroadcasts().get(p).getBroadcastCommits().add(new BroadcastCommit(SharadUtil.getString(Constants.USERNAME,""),SharadUtil.getString(Constants.USERPHONE,""),bundle1.getString("text")));
                    ((BroadcastAdapter)broadcast_list.getAdapter()).notifyItemChanged(p);
                    break;
                case 3:
                    Toast.makeText(context,"评论不能为空",Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    };
}