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
        //id textView1をt1に当てはめている
        TextView kanaName = (TextView)findViewById(R.id.kanaText);
        //id textView1をt2に当てはめている
        TextView name = (TextView)findViewById(R.id.nameText);
        //id textView1をt3に当てはめている
        TextView syaban = (TextView)findViewById(R.id.syabanText);
        TextView lotNum = (TextView)findViewById(R.id.lotNumText);

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
                info.setMoney(1);
                greenButton(moneyButton);

                lotNumCheck();
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

    private void lotNumCheck() {

        if (info.getSankaFlg()==1 & info.getMoneyFlg()==1 & info.getLotNum()==null) {
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

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = this.getLayoutInflater().inflate(R.layout.msg_layout, null);
            TextView bangou = view.findViewById(R.id.random_num);
            bangou.setText(ranNum);
            builder.setView(view)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        // ボタンをクリックしたときの動作
                            TextView lotNum = (TextView)findViewById(R.id.lotNumText);
                            lotNum.setText(ranNum);

                        }
                    });
            builder.show();

        }
    }

    public void greenButton(Button btn) {
        //リソースから作成したDrawableのリソースを取得
        Drawable btn_color = ResourcesCompat.getDrawable(getResources(), R.drawable.green_button, null);
        //ボタンにDrawableを適用する
        btn.setBackground(btn_color);
    }
}
