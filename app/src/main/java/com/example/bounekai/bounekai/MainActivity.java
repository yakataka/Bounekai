package com.example.bounekai.bounekai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View fullView = getWindow().getDecorView();
        fullView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
        // Spinner変数にIDを紐づけ
        Spinner awardSpinner = (Spinner) findViewById(R.id.award);
        Spinner lotSpinner = (Spinner) findViewById(R.id.lottery_num);

        // Initializing a String Array
        String[] award = new String[]{
                "5等-3",  // めぐリズム
                "5等-2",  //珪藻土マット
                "5等-1",  // コーディアル
                "4等-4",  // LUSHセット
                "4等-3",  // 本
                "4等-2",  // 茅乃舎のセット
                "4等-1",  // ラーメンセット
                "3等",    // お酒とおつまみ
                "2等-3",  // アロマディフューザー
                "2等-2",  // AIスピーカー
                "2等-1",  // 蟹カタログ
                "1等-2",  // コーヒーメーカー
                "1等-1",  // お肉カタログ
                "特別賞-4",  // 育毛剤
                "特別賞-3",  // フライパン
                "特別賞-2",  // TDRチケット
                "特別賞-1",  // フジQチケット
                "-",
                "-",
                "-",
                "-",
                "-",
                "-",
                "-",
                "-",
                "-",
                "-",
                "当日賞"
        };

        String[] lotUnit = new String[]{
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10"
        };

        // awardSpinnerに配列とスタイルを設定
        ArrayAdapter<String> awardSpinnerAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item,award);
        awardSpinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
        awardSpinner.setAdapter(awardSpinnerAdapter);
        // lotSpinnerに配列とスタイルを設定
        ArrayAdapter<String> lotSpinnerAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item,lotUnit);
        lotSpinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
        lotSpinner.setAdapter(lotSpinnerAdapter);

        Button sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MemberListActivity.class);
                startActivity(intent);
            }
        });

        Button lotteryButton = findViewById(R.id.lottery_button);
        lotteryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner lotteryNumSpinner = findViewById(R.id.lottery_num);
                int lotteryTimes = Integer.parseInt((String)lotteryNumSpinner.getSelectedItem());
                Intent intent = new Intent(getApplication(), LotteryActivity.class);
                intent.putExtra("LOTTERY_TIMES", lotteryTimes);
                Spinner AwardSpinner = findViewById(R.id.award);
                String award = (String)AwardSpinner.getSelectedItem();
                intent.putExtra("AWARD", award);
                startActivity(intent);
            }
        });

        Button resultButton = findViewById(R.id.result_button);
        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                startActivity(intent);
                //finish();
            }
        });
    }
}
