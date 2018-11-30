package com.example.bounekai.bounekai;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ThreadLocalRandom;

public class MemberActivity extends AppCompatActivity {

    MemberDto info;
    int min = 100;
    int max = 1000;
    String where;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        setTitle("");

        Intent intent = getIntent();
        //MainActivityから値を受け取る
        info = (MemberDto)getIntent().getSerializableExtra("MemberDto" );

        //IDを入れる
        final int num = info.getNum();
        where = "num = " + num;
        //id 各textViewに当てはめている
        TextView kanaName = (TextView)findViewById(R.id.kanaText);
        TextView name = (TextView)findViewById(R.id.nameText);
        TextView syaban = (TextView)findViewById(R.id.syabanText);
        TextView lotNum = (TextView)findViewById(R.id.random_num);

        //受け取った値を表示
        kanaName.setText(info.getKanaName());
        name.setText(info.getName());
        syaban.setText(info.getSyaban());
        lotNum.setText(info.getLotNum());

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
                info.setSanka(1);
                greenButton(sankaButton);

                lotNumCheck();

            }
        });

        if (info.getSankaFlg() == 1) {
            //リソースからボタンのリソースを取得
            greenButton(sankaButton);
        }
    }

    private void lotNumCheck() {

        if (info.getSankaFlg()==1 & info.getLotNum()==null) {
            DatabaseOpenHelper openHelper = new DatabaseOpenHelper(getApplication());
            SQLiteDatabase database = openHelper.getWritableDatabase();

            int randomNum = ThreadLocalRandom.current().nextInt(min, max);

            Cursor cursor;
            cursor = database.query(
                    WordContract.Words.TABLE_NAME,
                    null,
                    "lot_num=?",
                    new String[]{Integer.toString(randomNum)},
                    null,
                    null,
                    null
            );
            int num = cursor.getCount();
            do  {
                randomNum = ThreadLocalRandom.current().nextInt(min, max);
                cursor = database.query(
                        WordContract.Words.TABLE_NAME,
                        null,
                        "lot_num=?",
                        new String[]{Integer.toString(randomNum)},
                        null,
                        null,
                        null
                );

                num = cursor.getCount();
            }while (num < 0);

            final String ranNum = Integer.toString(randomNum);

            ContentValues cv = new ContentValues();
            cv.put("lot_num", ranNum);

            database.update(
                    "Member",
                    cv,
                    where,
                    null
            );
            info.setLotNum(Integer.toString(randomNum));
        }
    }

    public void greenButton(Button btn) {
        //リソースから作成したDrawableのリソースを取得
        Drawable btn_color = ResourcesCompat.getDrawable(getResources(), R.drawable.green_button, null);
        //ボタンにDrawableを適用する
        btn.setBackground(btn_color);
    }
}
