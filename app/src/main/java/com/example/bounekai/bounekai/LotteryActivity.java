package com.example.bounekai.bounekai;

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

        // 抽選対象取得
        rosterDtoList = selectLotteryTarget();
        for(int i = 0; i < rosterDtoList.size(); i++) {
            Log.v("num", rosterDtoList.get(i).getSyainNum());
        }
        // シャッフル
        Collections.shuffle(rosterDtoList);

        Intent intent = getIntent();
        int lottery_times = intent.getIntExtra("LOTTERY_TIMES", 0);
        for (int i = 0; i < lottery_times; i++) {
            result = result + rosterDtoList.get(i).getSyainNum() + "  ";
        }

        TextView lotteryResult = findViewById(R.id.lottery_result);
        lotteryResult.setText(result);
    }

    private ArrayList<RosterDto> selectLotteryTarget() {

        SQLiteDatabase db = openHelper.getWritableDatabase();

        try {
            Cursor cursor;
            cursor = db.query(
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
            for (int i = 0; i < numRows; i++) {
                RosterDto rosterDto = new RosterDto();
                rosterDto.setSyainNum(cursor.getString(3));
                rosterDto.setLotNum(cursor.getString(4));
                rosterDtoList.add(rosterDto);
            }
        } finally {
            db.close();
        }

        return rosterDtoList;
    }
}
