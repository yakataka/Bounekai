package com.example.bounekai.bounekai;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class LotteryActivity extends AppCompatActivity {

    private ArrayList<RosterDto> rosterDtoList = new ArrayList<>();
    private DatabaseOpenHelper openHelper = new DatabaseOpenHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);
        String result = "";
        Intent intent = getIntent();
        int lottery_times = intent.getIntExtra("LOTTERY_TIMES", 0);
        String[] hit_num_array = new String[lottery_times];
        int hageFlg = intent.getIntExtra("HAGE", 0);

        String award = intent.getStringExtra("AWARD");
        TextView awardTextView = findViewById(R.id.award);
        awardTextView.setText(award);

        // TODO 開発用
        updateAllSankaFlg();

        // 抽選対象取得
        if (hageFlg == 1) {
            // はげフラグonのときのselect文
        } else {
            rosterDtoList = selectLotteryTarget();
        }
//        for(int i = 0; i < rosterDtoList.size(); i++) {
//            Log.v("num", rosterDtoList.get(i).getSyainNum());
//        }
        // シャッフル
        Collections.shuffle(rosterDtoList);

        try {
            for (int i = 0; i < lottery_times; i++) {
                String lottery_result = "winNum" + (i + 1);
                int viewId = getResources().getIdentifier(lottery_result, "id", getPackageName());
                TextView lotteryResult = findViewById(viewId);
                lotteryResult.setText(rosterDtoList.get(i).getLotNum());
                hit_num_array[i] = rosterDtoList.get(i).getLotNum();
            }
        } catch(IndexOutOfBoundsException e) {
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
