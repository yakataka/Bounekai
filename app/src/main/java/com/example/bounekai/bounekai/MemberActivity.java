package com.example.bounekai.bounekai;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        //IDを入れる
        final int num = info.getNum();
        final String where = "num = " + num;
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

        Button backButton = findViewById(R.id.back_button_member);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MemberListActivity.class);
                startActivity(intent);
            }
        });

        final Button sankaButton = findViewById(R.id.sanka_button);
        sankaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues cv = new ContentValues();
                cv.put("sanka_flg", 1);

                DatabaseOpenHelper openHelper = new DatabaseOpenHelper(getApplication());
                SQLiteDatabase database = openHelper.getWritableDatabase();
                database.update(
                        "Member",
                        cv,
                        where,
                        null
                );
                greenButton(sankaButton);
            }
        });

        final Button moneyButton = findViewById(R.id.money_button);
        moneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put("money_flg", 1);

                DatabaseOpenHelper openHelper = new DatabaseOpenHelper(getApplication());
                SQLiteDatabase database = openHelper.getWritableDatabase();
                database.update(
                        "Member",
                        cv,
                        where,
                        null
                );
                greenButton(moneyButton);
            }
        });

        if (info.getSankaFlg() == 1) {
            //リソースからボタンのリソースを取得
            greenButton(sankaButton);
        }
        if (info.getMoneyFlg() == 1) {
            //リソースからボタンのリソースを取得
            greenButton(moneyButton);
        }
    }
    public void greenButton(Button btn) {
        //リソースから作成したDrawableのリソースを取得
        Drawable btn_color = ResourcesCompat.getDrawable(getResources(), R.drawable.green_button, null);
        //ボタンにDrawableを適用する
        btn.setBackground(btn_color);
    }
}
