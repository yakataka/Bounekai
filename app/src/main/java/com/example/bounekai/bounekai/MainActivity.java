package com.example.bounekai.bounekai;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    LotteryService lotteryService = new LotteryService();

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
                TextView lotteryResultTextView = findViewById(R.id.lottery_result);
                int lotteryTimes = (Integer)lotteryNumSpinner.getSelectedItem();
                String[] hitNum = lotteryService.lotteryExec(lotteryTimes);
                String hitNumText = "";
                for(int i = 0; i < hitNum.length; i++) {
                    hitNumText = hitNumText + hitNum[i] + " ";
                }
                lotteryResultTextView.setText(hitNumText);

            }
        });

    }
}
