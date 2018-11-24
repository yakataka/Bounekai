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
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class MemberListActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<MemberDto> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);

        int	i;

        DatabaseOpenHelper openHelper = new DatabaseOpenHelper(this);
        SQLiteDatabase database = openHelper.getWritableDatabase();
        //処理
        Cursor cursor;
        cursor = database.query(
                WordContract.Words.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        Log.v("DB確認", "Count:" + cursor.getCount());
        while (cursor.moveToNext()){
            int name = cursor.getColumnIndex(WordContract.Words.COL_WORD);
            String desc = cursor.getString(cursor.getColumnIndex(WordContract.Words.COL_DESCR));
            Log.v("DB確認", "  Word:" + name + "  Desk:" + desc);
        }

        int numRows = cursor.getCount();

        cursor.moveToFirst();


        for( i = 0; i < numRows; i++ ) {

            MemberDto memberDto = new MemberDto();
            memberDto.setNum(cursor.getInt(0));
            memberDto.setKanaName(cursor.getString(1));
            memberDto.setName(cursor.getString(2));
            memberDto.setSyaban(cursor.getString(3));
            memberDto.setYotei(cursor.getInt(6));
            memberDto.setSanka(cursor.getInt(7));
            memberDto.setMoney(cursor.getInt(8));
            list.add(memberDto);

            cursor.moveToNext();
        }

        listView = (ListView) findViewById(R.id.MemberList);

        MyAdapter myAdapter = new MyAdapter(MemberListActivity.this);

        myAdapter.setMemberList(list);
        listView.setAdapter(myAdapter);

        cursor.close();
        database.close();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
                // 画面遷移
                Intent intent = new Intent(getApplicationContext(), MemberActivity.class);
                MemberDto info = list.get(position);
                intent.putExtra("MemberDto", info);
                startActivity(intent);
            }
        });
    }


}
