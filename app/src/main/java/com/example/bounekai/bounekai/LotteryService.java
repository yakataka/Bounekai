package com.example.bounekai.bounekai;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class LotteryService extends AppCompatActivity {

    public String[] lotteryExec(int lottery_times) {

        String[] hitNum = new String[lottery_times];

        // 抽選対象取得
        ArrayList<RosterDto> rosterDtoList = selectLotteryTarget();
        // シャッフル
        Collections.shuffle(rosterDtoList);

        // 当選番号格納
        for(int i = 0; i < lottery_times; i++) {
            hitNum[i] = rosterDtoList.get(i).getLotNum();
        }

        // 当選フラグ更新

        return hitNum;
    }


    private ArrayList<RosterDto> selectLotteryTarget() {
        ArrayList<RosterDto> rosterDtoList = new ArrayList<>();
        DatabaseOpenHelper openHelper = new DatabaseOpenHelper(this);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        try {
            Cursor cursor;
            cursor = db.query(
                    WordContract.Words.TABLE_NAME,
                    new String[]{"lot_num"},
                    "hit_flg = ?, sanka_flg = ?, hage_flg = ?",
                    new String[]{"0", "1", "0"}, null, null, null
            );
            int numRows = cursor.getCount();

            for (int i = 0; i < numRows; i++) {
                RosterDto rosterDto = new RosterDto();
                rosterDto.setLotNum(cursor.getString(4));
                rosterDtoList.add(rosterDto);
            }
        } finally {
            db.close();
        }

        return rosterDtoList;
    }
}
