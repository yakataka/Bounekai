package com.example.bounekai.bounekai;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class MemberListActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<MemberDto> list = new ArrayList<>();
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View fullView = getWindow().getDecorView();
        fullView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
        setContentView(R.layout.activity_member_list);
        listView = (ListView) findViewById(R.id.MemberList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
                // 画面遷移
                Intent intent = new Intent(getApplicationContext(), MemberActivity.class);
                MemberDto info = list.get(position);
                intent.putExtra("MemberDto", info);
                startActivity(intent);
            }
        });

        Button backButton = findViewById(R.id.back_button_main);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        Button allButton = findViewById(R.id.allbutton);
        allButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAdapter = new MyAdapter(MemberListActivity.this);
                myAdapter.setMemberList(list);
                listView.setAdapter(myAdapter);
            }
        });

        Button disButton = findViewById(R.id.disButton);
        disButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<MemberDto> memberList = new ArrayList<>();
                for (MemberDto member : list) {
                    if (member.getSankaFlg()==0 & member.getYotei()==1){
                        memberList.add(member);
                    }
                }
                myAdapter = new MyAdapter(MemberListActivity.this);
                myAdapter.setMemberList(memberList);
                listView.setAdapter(myAdapter);
            }
        });

        Button zumiButton = findViewById(R.id.zumiButton);
        zumiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<MemberDto> memberList = new ArrayList<>();
                for (MemberDto member : list) {
                    if (member.getSankaFlg()==1){
                        memberList.add(member);
                    }
                }
                myAdapter = new MyAdapter(MemberListActivity.this);
                myAdapter.setMemberList(memberList);
                listView.setAdapter(myAdapter);
            }
        });

        Button kessekiButton = findViewById(R.id.kessekiButton);
        kessekiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<MemberDto> memberList = new ArrayList<>();
                for (MemberDto member : list) {
                    if (member.getYotei()==0 & member.getSankaFlg()==0) {
                        memberList.add(member);
                    }
                }
                myAdapter = new MyAdapter(MemberListActivity.this);
                myAdapter.setMemberList(memberList);
                listView.setAdapter(myAdapter);
            }
        });

        Button sankaButton = findViewById(R.id.syussekiha);
        sankaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSanka();
            }
        });
    }

    public void getSanka() {
        DatabaseOpenHelper openHelper = new DatabaseOpenHelper(this);
        SQLiteDatabase database = openHelper.getWritableDatabase();
        //処理
        Cursor cursor;

        cursor = database.query(
                WordContract.Words.TABLE_NAME,
                null,
                "sanka_flg = '1'",
                null,
                null,
                null,
                null
        );

        int sankaPeople = cursor.getCount();

        TextView sankaTotal = (TextView)findViewById(R.id.sanka_total);
        sankaTotal.setText(Integer.toString(sankaPeople));

        cursor.close();
        database.close();
    }

    @Override
    protected void onResume() {
        super.onResume();

        int	i;

        DatabaseOpenHelper openHelper = new DatabaseOpenHelper(this);
        SQLiteDatabase database = openHelper.getWritableDatabase();
        //処理
        Cursor cursor;

        getSanka();

        cursor = database.query(
                WordContract.Words.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        int numRows = cursor.getCount();

        cursor.moveToFirst();
        for( i = 0; i < numRows; i++ ) {

            MemberDto memberDto = new MemberDto();
            memberDto.setNum(cursor.getInt(0));
            memberDto.setKanaName(cursor.getString(1));
            memberDto.setName(cursor.getString(2));
            memberDto.setSyaban(cursor.getString(3));
            memberDto.setLotNum(cursor.getString(4));
            memberDto.setHit(cursor.getInt(5));
            memberDto.setYotei(cursor.getInt(6));
            memberDto.setSanka(cursor.getInt(7));
            memberDto.setMoney(cursor.getInt(8));
            memberDto.setHage(cursor.getInt(9));
            list.add(memberDto);

            cursor.moveToNext();
        }

        myAdapter = new MyAdapter(MemberListActivity.this);
        myAdapter.setMemberList(list);
        listView.setAdapter(myAdapter);

        cursor.close();
        database.close();
    }


}
