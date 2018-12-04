package com.example.bounekai.bounekai;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<MemberDto> memberList;

    public MyAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setMemberList(ArrayList<MemberDto> memberList) {
        this.memberList = memberList;
    }

    @Override
    public int getCount() {
        return memberList.size();
    }

    @Override
    public MemberDto getItem(int position) {
        return memberList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return memberList.get(position).getNum();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.memberrow,parent,false);

        ((TextView)convertView.findViewById(R.id.kana)).setText(memberList.get(position).getKanaName());
        ((TextView)convertView.findViewById(R.id.name)).setText(memberList.get(position).getName());
        ((TextView)convertView.findViewById(R.id.syaban)).setText(Integer.toString(memberList.get(position).getHit()));
        ((TextView)convertView.findViewById(R.id.sanka)).setText(memberList.get(position).getSanka());
        if (memberList.get(position).getLotNum() != null) {
            ((TextView)convertView.findViewById(R.id.lotNum)).setText(memberList.get(position).getLotNum());
        } else {
            ((TextView)convertView.findViewById(R.id.lotNum)).setText("未確定");
        }
        return convertView;
    }
}