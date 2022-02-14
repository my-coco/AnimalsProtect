package com.sixing.animalsprotect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sixing.animalsprotect.R;
import com.sixing.animalsprotect.bean.Account;

import java.util.List;

public class AccountsAdapter extends BaseAdapter {
    private List<Account> accountsList;
    private Context context;

    public AccountsAdapter(List<Account> accounts,Context context){
        accountsList=accounts;
        this.context=context;
    }
    @Override
    public int getCount() {
        return accountsList.size();
    }

    @Override
    public Object getItem(int position) {
        return accountsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Hodler hodler=null;
        if(convertView==null){
            hodler=new Hodler();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_accounts,parent,false);
            hodler.kind=convertView.findViewById(R.id.accounts_kind);
            hodler.nums=convertView.findViewById(R.id.accounts_nums);
            convertView.setTag(hodler);
        }else{
            hodler=(Hodler) convertView.getTag();
        }
        Account account=accountsList.get(position);
        if (account.getIncome()==1){
            hodler.kind.setText("收入");
        }else{
            hodler.kind.setText("支出");
        }
        hodler.nums.setText(String.valueOf(account.getData()));
        return convertView;
    }

    static class Hodler{
        TextView kind;
        TextView nums;
    }
}
