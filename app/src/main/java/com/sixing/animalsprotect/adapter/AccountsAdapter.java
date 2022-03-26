package com.sixing.animalsprotect.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.bean.Account;
import com.sixing.animalsprotect.constant.Constants;
import com.sixing.animalsprotect.ui.accounts.AccountsActivity;

import java.util.List;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.Hodler> {
    private List<Account> accountsList;
    private Context context;

    public AccountsAdapter(List<Account> accounts,Context context){
        accountsList=accounts;
        this.context=context;
    }

    @NonNull
    @Override
    public Hodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=View.inflate(context,R.layout.item_accounts,null);
        Hodler hodler=new Hodler(view);
        return hodler;
    }

    @Override
    public void onBindViewHolder(@NonNull Hodler holder, int position) {
        Account account=accountsList.get(position);
        if (account.getIncome()==1){
            holder.kind.setText("收入");
        }else{
            holder.kind.setText("支出");
        }
        holder.nums.setText(String.valueOf(account.getData()));
        holder.bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(context, AccountsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("title",account.getTitle());
                bundle.putString("reason",account.getReason());
                bundle.putString("date",account.getDate());
                intent.putExtra(Constants.ACCOUNTBUNDLE,bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return accountsList.size();
    }

    static class Hodler extends RecyclerView.ViewHolder {
        TextView kind;
        TextView nums;
        ConstraintLayout bg;

        public Hodler(@NonNull View itemView) {
            super(itemView);
            kind=itemView.findViewById(R.id.accounts_kind);
            nums=itemView.findViewById(R.id.accounts_nums);
            bg=itemView.findViewById(R.id.bg);
        }
    }
}
