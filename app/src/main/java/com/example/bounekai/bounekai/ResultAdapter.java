package com.example.bounekai.bounekai;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<String[]> resultList;

    public ResultAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setMemberList(ArrayList<String[]> resultList) {
        this.resultList = resultList;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public String[] getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.result_row,parent,false);

        if (resultList.get(position)[0].equals("0")) {
            ((TextView)convertView.findViewById(R.id.result_title)).setText(resultList.get(position)[1]);
        } else {
            ((TextView)convertView.findViewById(R.id.result_lottery_num)).setText(resultList.get(position)[1]);
        }

        return convertView;
    }
}
