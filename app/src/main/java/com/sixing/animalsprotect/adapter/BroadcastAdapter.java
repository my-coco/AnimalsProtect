package com.sixing.animalsprotect.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.bean.Broadcast;
import com.sixing.animalsprotect.bean.BroadcastLike;
import com.sixing.animalsprotect.bean.HttpResponse;
import com.sixing.animalsprotect.constant.Constants;
import com.sixing.animalsprotect.http.HttpRepository;
import com.sixing.animalsprotect.util.SharadUtil;
import com.sixing.animalsprotect.widget.MyListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class BroadcastAdapter extends RecyclerView.Adapter<BroadcastAdapter.Hodler> {
    private List<Broadcast> broadcasts;
    private Context context;
    private View.OnClickListener onClickListener;
    private Handler handler;
    private HttpRepository httpRepository;

    public BroadcastAdapter(List<Broadcast> broadcasts,Context context,Handler handler){
        this.broadcasts=broadcasts;
        this.context=context;
        this.onClickListener=onClickListener;
        httpRepository=new HttpRepository();
        this.handler=handler;
    }


    @NonNull
    @Override
    public BroadcastAdapter.Hodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=View.inflate(context,R.layout.item_broadcast,null);
        Hodler hodler=new Hodler(view);
        return hodler;
    }

    @Override
    public void onBindViewHolder(@NonNull BroadcastAdapter.Hodler holder, int position) {
        Broadcast broadcast=broadcasts.get(position);
        holder.pic.setImageDrawable(broadcast.getPic());
        holder.name.setText(broadcast.getName());
        holder.words.setText(broadcast.getWords());
        holder.time.setText(broadcast.getTime());
        //图片
        //hodler.gridView
        if (broadcast.getPrase_names().size()>0){
            String names="";
            for(BroadcastLike i:broadcast.getPrase_names()){
                names+=i.getUserName()+"、";
                if(i.getUserId().equals(SharadUtil.getString(Constants.USERPHONE,""))){
                    broadcast.setLike(true);
                }
            }
            holder.prase_names.setText(names.substring(0,names.length()-1));
        }else{
            holder.praise_bg.setVisibility(View.GONE);
        }
        if(broadcast.getLike()){
            holder.like.setImageDrawable(context.getDrawable(R.drawable.like_y));
        }else{
            holder.like.setImageDrawable(context.getDrawable(R.drawable.like_n));
        }
        //评论
        if(broadcast.getBroadcastCommits()!=null){
            BroadcastCommitAdapter broadcastCommitAdapter=new BroadcastCommitAdapter(broadcast.getBroadcastCommits(),context);
            holder.commits.setAdapter(broadcastCommitAdapter);
        }
        int p=position;
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(broadcast.getLike()){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if(likeNotice(broadcast.getId(),SharadUtil.getString(Constants.USERPHONE,""),!broadcast.getLike())){
                                holder.like.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        broadcast.setLike(false);
                                        for(BroadcastLike broadcastLike:broadcast.getPrase_names()){
                                            if(broadcastLike.getUserId().equals(SharadUtil.getString(Constants.USERPHONE,""))){
                                                broadcast.getPrase_names().remove(broadcastLike);
                                            }
                                        }
                                        notifyItemChanged(p);
                                    }
                                });
                                broadcast.setLike(false);
                            }
                        }
                    }).start();
                }else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if(likeNotice(broadcast.getId(),SharadUtil.getString(Constants.USERPHONE,""),!broadcast.getLike())){
                                holder.like.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        broadcast.setLike(true);
                                        broadcast.getPrase_names().add(new BroadcastLike(SharadUtil.getString(Constants.USERNAME,""),SharadUtil.getString(Constants.USERPHONE,"")));
                                        notifyItemChanged(p);
                                    }
                                });
                                broadcast.setLike(true);
                            }
                        }
                    }).start();
                }
            }
        });
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message=Message.obtain();
                message.what=1;
                Bundle bundle=new Bundle();
                bundle.putString("Id",broadcast.getId());
                bundle.putInt("position",p);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
    }

    private Boolean likeNotice(String noticeId, String userId, Boolean like){
        try{
            Call<HttpResponse<Boolean>> call=httpRepository.likeNotice(noticeId,userId,like);
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

    public List<Broadcast> getBroadcasts(){
        return broadcasts;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return broadcasts.size();
    }

    static class Hodler extends RecyclerView.ViewHolder{
        ImageView pic;
        TextView name;
        TextView words;
        GridView gridView;
        TextView time;
        ImageView more;
        ImageView like;
        TextView prase_names;
        MyListView commits;
        ConstraintLayout praise_bg;
        public Hodler(@NonNull View itemView) {
            super(itemView);
            pic=itemView.findViewById(R.id.pic);
            name=itemView.findViewById(R.id.name);
            words=itemView.findViewById(R.id.words);
            gridView=itemView.findViewById(R.id.pic_grid);
            time=itemView.findViewById(R.id.time);
            prase_names=itemView.findViewById(R.id.people_names);
            commits=itemView.findViewById(R.id.people_words);
            praise_bg=itemView.findViewById(R.id.praise_bg);
            more=itemView.findViewById(R.id.more);
            like=itemView.findViewById(R.id.like);
        }
    }
}
