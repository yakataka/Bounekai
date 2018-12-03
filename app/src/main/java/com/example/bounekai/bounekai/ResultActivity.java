package com.example.bounekai.bounekai;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String[]> list = new ArrayList<>();
    ResultAdapter resultAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        View fullView = getWindow().getDecorView();
        fullView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );

        listView = (ListView) findViewById(R.id.ResultList);

        list.add(new String[]{"0", "1等ｰ1"});
        awardAdd("hit_flg = '1'");

        list.add(new String[]{"0", "1等ｰ2"});
        awardAdd("hit_flg = '12'");

        list.add(new String[]{"0", "2等ｰ1"});
        awardAdd("hit_flg = '21'");

        list.add(new String[]{"0", "2等ｰ2"});
        awardAdd("hit_flg = '22'");

        list.add(new String[]{"0", "2等ｰ3"});
        awardAdd("hit_flg = '23'");

        list.add(new String[]{"0", "2等ｰ4"});
        awardAdd("hit_flg = '24'");

        list.add(new String[]{"0", "3等ｰ1"});
        awardAdd("hit_flg = '31'");

        list.add(new String[]{"0", "4等ｰ1"});
        awardAdd("hit_flg = '41'");

        list.add(new String[]{"0", "4等ｰ2"});
        awardAdd("hit_flg = '42'");

        list.add(new String[]{"0", "4等ｰ3"});
        awardAdd("hit_flg = '43'");

        list.add(new String[]{"0", "4等ｰ4"});
        awardAdd("hit_flg = '44'");

        list.add(new String[]{"0", "5等ｰ1"});
        awardAdd("hit_flg = '51'");

        list.add(new String[]{"0", "5等ｰ2"});
        awardAdd("hit_flg = '52'");

        list.add(new String[]{"0", "5等ｰ3"});
        awardAdd("hit_flg = '53'");

        resultAdapter = new ResultAdapter(this);
        resultAdapter.setMemberList(list);
        listView.setAdapter(resultAdapter);

    }

    private void awardAdd(String where) {
        DatabaseOpenHelper openHelper = new DatabaseOpenHelper(this);
        SQLiteDatabase database = openHelper.getWritableDatabase();
        //処理
        Cursor cursor;

        cursor = database.query(
                WordContract.Words.TABLE_NAME,
                null,
                where,
                null,
                null,
                null,
                null
        );

        int numRows = cursor.getCount();

        int	i;
        cursor.moveToFirst();
        for( i = 0; i < numRows; i++ ) {

            String[] result = {cursor.getString(5),cursor.getString(4)};
            list.add(result);

            cursor.moveToNext();
        }
        cursor.close();
        database.close();
    }
}
