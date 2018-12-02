package com.example.bounekai.bounekai;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class LotteryActivity extends AppCompatActivity {

    private ArrayList<RosterDto> rosterDtoList = new ArrayList<>();
    private DatabaseOpenHelper openHelper = new DatabaseOpenHelper(this);
    private int lottery_times;
    private int hageFlg;
    private int hitFlg;
    private String award = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);

        //画面を最大にする
        View fullView = getWindow().getDecorView();
        fullView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );

        Intent intent = getIntent();
        lottery_times = intent.getIntExtra("LOTTERY_TIMES", 0);
        hageFlg = intent.getIntExtra("HAGE", 0);

        award = intent.getStringExtra("AWARD");
        final Button awardButton = findViewById(R.id.award);
        awardButton.setText(award);
        awardButton.setVisibility(View.INVISIBLE);

        // 背景設定
        setImage();

        // 使わないTextViewを非表示
        for (int i = lottery_times; i < 10; i++) {
            String lottery_result = "winNum" + (i + 1);
            int viewId = getResources().getIdentifier(lottery_result, "id", getPackageName());
            TextView lotteryResult = findViewById(viewId);
            lotteryResult.setVisibility(View.INVISIBLE);
        }

        final ImageButton imgbt= findViewById(R.id.preAward);
        imgbt.bringToFront();
        imgbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgbt.setVisibility(View.INVISIBLE);
                awardButton.setVisibility(View.VISIBLE);
            }
        });


        awardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lottery();
            }
        });

        Button backButton = findViewById(R.id.back_from_lottery);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                //finish();
            }
        });

    }

    private void setImage(){
        if("5等-3".equals(award)) {
            ((ImageView) findViewById(R.id.back_ground)).setImageResource(R.drawable.bk5_3);
            ((ImageView) findViewById(R.id.preAward)).setImageResource(R.drawable.bk5_3);
            hitFlg = 53;
        }
        else if("5等-2".equals(award)) {
            ((ImageView) findViewById(R.id.back_ground)).setImageResource(R.drawable.bk5_2);
            ((ImageView) findViewById(R.id.preAward)).setImageResource(R.drawable.bk5_2);
            hitFlg = 52;
        }
        else if("5等-1".equals(award)) {
            ((ImageView) findViewById(R.id.back_ground)).setImageResource(R.drawable.bk5_1);
            ((ImageView) findViewById(R.id.preAward)).setImageResource(R.drawable.bk5_1);
            hitFlg = 51;
        }
        else if("4等-4".equals(award)) {
            ((ImageView) findViewById(R.id.back_ground)).setImageResource(R.drawable.bk4_4);
            ((ImageView) findViewById(R.id.preAward)).setImageResource(R.drawable.bk4_4);
            hitFlg = 44;
        }
        else if("4等-3".equals(award)) {
            ((ImageView) findViewById(R.id.back_ground)).setImageResource(R.drawable.bk4_3);
            ((ImageView) findViewById(R.id.preAward)).setImageResource(R.drawable.bk4_3);
            hitFlg = 43;
        }
        else if("4等-2".equals(award)) {
            ((ImageView) findViewById(R.id.back_ground)).setImageResource(R.drawable.bk4_2);
            ((ImageView) findViewById(R.id.preAward)).setImageResource(R.drawable.bk4_2);
            hitFlg = 42;
        }
        else if("4等-1".equals(award)) {
            ((ImageView) findViewById(R.id.back_ground)).setImageResource(R.drawable.bk4_1);
            ((ImageView) findViewById(R.id.preAward)).setImageResource(R.drawable.bk4_1);
            hitFlg = 41;
        }
        else if("3等".equals(award)) {
            ((ImageView) findViewById(R.id.back_ground)).setImageResource(R.drawable.bk3_1);
            ((ImageView) findViewById(R.id.preAward)).setImageResource(R.drawable.bk3_1);
            hitFlg = 31;
        }
        else if("2等-4".equals(award)) {
            ((ImageView) findViewById(R.id.back_ground)).setImageResource(R.drawable.bk2_4);
            ((ImageView) findViewById(R.id.preAward)).setImageResource(R.drawable.bk2_4);
            hitFlg = 24;
        }
        else if("2等-3".equals(award)) {
            ((ImageView) findViewById(R.id.back_ground)).setImageResource(R.drawable.bk2_3);
            ((ImageView) findViewById(R.id.preAward)).setImageResource(R.drawable.bk2_3);
            hitFlg = 23;
        }
        else if("2等-2".equals(award)) {
            ((ImageView) findViewById(R.id.back_ground)).setImageResource(R.drawable.bk2_2);
            ((ImageView) findViewById(R.id.preAward)).setImageResource(R.drawable.bk2_2);
            hitFlg = 22;
        }
        else if("2等-1".equals(award)) {
            ((ImageView) findViewById(R.id.back_ground)).setImageResource(R.drawable.bk2_1);
            ((ImageView) findViewById(R.id.preAward)).setImageResource(R.drawable.bk2_1);
            hitFlg = 21;
        }
        else if("1等-2".equals(award)) {
            ((ImageView) findViewById(R.id.back_ground)).setImageResource(R.drawable.bk1_2);
            ((ImageView) findViewById(R.id.preAward)).setImageResource(R.drawable.bk1_2);
            hitFlg = 12;
        }
        else if("1等-1".equals(award)) {
            ((ImageView) findViewById(R.id.back_ground)).setImageResource(R.drawable.bk1_1);
            ((ImageView) findViewById(R.id.preAward)).setImageResource(R.drawable.bk1_1);
            hitFlg = 11;
        }
    }

    private void lottery(){
        String[] hit_num_array = new String[lottery_times];

        // TODO 開発用
        updateAllSankaFlg();

        // 抽選対象取得
        if (hageFlg == 1) {
            // はげフラグonのときのselect文
        } else {
            rosterDtoList = selectLotteryTarget();
        }

        // シャッフル
        Collections.shuffle(rosterDtoList);

        try {
            for (int i = 0; i < lottery_times; i++) {
                String lottery_result = "winNum" + (i + 1);
                int viewId = getResources().getIdentifier(lottery_result, "id", getPackageName());
                TextView lotteryResult = findViewById(viewId);
                lotteryResult.setText(rosterDtoList.get(i).getLotNum());
                hit_num_array[i] = rosterDtoList.get(i).getLotNum();
                lotteryResult.postInvalidate();
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        // 当選フラグ更新
        updateLotteryTarget(hit_num_array);
    }

    private ArrayList<RosterDto> selectLotteryTarget() {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        try {
            Cursor cursor;
            cursor = db.query(
                    WordContract.Words.TABLE_NAME,
                    null,
                    "sanka_flg = ? AND hit_flg = ? AND hage_flg = ?",
                    new String[]{"1","0","0"},
                    null,
                    null,
                    null
            );
            int numRows = cursor.getCount();

            cursor.moveToFirst();
            for (int i = 0; i < numRows; i++, cursor.moveToNext()) {
                RosterDto rosterDto = new RosterDto();
                rosterDto.setLotNum(cursor.getString(4));
                rosterDtoList.add(rosterDto);
            }
        } finally {
            db.close();
        }
        return rosterDtoList;
    }

    private void updateLotteryTarget(String[] hit_num_array) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put("hit_flg", 1);
            for (int i = 0; i < hit_num_array.length; i++) {
                db.update(
                        WordContract.Words.TABLE_NAME,
                        cv,
                        "lot_num = ?",
                        new String[]{hit_num_array[i]}
                );
            }

        } finally {
            db.close();
        }
    }

    // 開発用 全参加フラグON
    private void updateAllSankaFlg() {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put("sanka_flg", 1);
            for (int i = 0; i < 126; i++) {
                cv.put("lot_num", i + 1);
                db.update(
                        WordContract.Words.TABLE_NAME,
                        cv,
                        "num = ?",
                        new String[]{String.valueOf(i + 1)}
                );
            }
        } finally {
            db.close();
        }
    }
}
