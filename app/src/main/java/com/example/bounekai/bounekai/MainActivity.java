package com.example.bounekai.bounekai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }
}
