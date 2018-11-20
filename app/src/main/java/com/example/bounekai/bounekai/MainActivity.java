package com.example.bounekai.bounekai;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    LotteryMain lotteryMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button lottery=findViewById(R.id.ChanceLottery);
        lottery.setOnClickListener(new View.OnClickListener(){
            /** ボタンをクリックした時に呼ばれる */
            @Override
            public void onClick(View v) {
//                String a="fd";
//                TextView winNum1=findViewById(R.id.winNum1);
//                winNum1.setText(a);
//                TextView winNum2=findViewById(R.id.winNum2);
//                winNum2.setText(a);
//                TextView winNum3=findViewById(R.id.winNum3);
//                winNum3.setText(a);

                int[] nums = lotteryMain.lottery(3);
                TextView winNum1=findViewById(R.id.winNum1);
                winNum1.setText(String.valueOf(nums[0]));
                TextView winNum2=findViewById(R.id.winNum2);
                winNum2.setText(String.valueOf(nums[1]));
                TextView winNum3=findViewById(R.id.winNum3);
                winNum3.setText(String.valueOf(nums[2]));

            }
        });
    }
        }

