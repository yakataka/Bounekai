package com.example.bounekai.bounekai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        setTitle("");

        Intent intent = getIntent();
        //MainActivityから値を受け取る
        MemberDto info = (MemberDto)getIntent().getSerializableExtra("MemberDto" );

        //id textView1をt1に当てはめている
        TextView kanaName = (TextView)findViewById(R.id.kanaText);
        //id textView1をt2に当てはめている
        TextView name = (TextView)findViewById(R.id.nameText);
        //id textView1をt3に当てはめている
        TextView syaban = (TextView)findViewById(R.id.syabanText);

        //受け取った値を表示
        kanaName.setText(info.getKanaName());
        name.setText(info.getName());
        syaban.setText(info.getSyaban());
    }
}
