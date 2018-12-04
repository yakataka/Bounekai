package com.example.bounekai.bounekai;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class LotteryActivity extends AppCompatActivity {

    private ArrayList<RosterDto> rosterDtoList = new ArrayList<>();
    private DatabaseOpenHelper openHelper = new DatabaseOpenHelper(this);
    private int lottery_times;
    private int hageFlg;
    private int hitFlg;
    private String award = "";

    private boolean ranNum1 = true;
    private boolean ranNum2 = true;
    private boolean ranNum3 = true;
    private boolean ranNum4 = true;
    private boolean ranNum5 = true;
    private boolean ranNum6 = true;
    private boolean ranNum7 = true;
    private boolean ranNum8 = true;
    private boolean ranNum9 = true;
    private boolean ranNum10 = true;


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

        // gifスタート
        rouletteStart();

        // 使わないTextViewとgifを非表示
        for (int i = lottery_times; i < 10; i++) {
            String lottery_result = "winNum" + (i + 1);
            int viewId = getResources().getIdentifier(lottery_result, "id", getPackageName());
            TextView lotteryResult = findViewById(viewId);
            lotteryResult.setVisibility(View.INVISIBLE);
        }

        // 使わないgifを非表示
        for (int i = lottery_times; i < 10; i++) {
            String lottery_result = "roulette" + (i + 1);
            int viewId = getResources().getIdentifier(lottery_result, "id", getPackageName());
            Button lotteryResult = findViewById(viewId);
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

        // 抽選処理
        lottery();

        Button backButton = findViewById(R.id.back_from_lottery);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        for (int i = 1; i <= lottery_times; i++) {
            if (i==1)loop1();
            if (i==2)loop2();
            if (i==3)loop3();
            if (i==4)loop4();
            if (i==5)loop5();
            if (i==6)loop6();
            if (i==7)loop7();
            if (i==8)loop8();
            if (i==9)loop9();
            if (i==10)loop10();
        }
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
        else if("特別賞-4".equals(award)) {
            ((ImageView) findViewById(R.id.back_ground)).setImageResource(R.drawable.bkex_4);
            ((ImageView) findViewById(R.id.preAward)).setImageResource(R.drawable.bkex_4);
            hitFlg = 104;
        }
        else if("特別賞-3".equals(award)) {
            ((ImageView) findViewById(R.id.back_ground)).setImageResource(R.drawable.bkex_3);
            ((ImageView) findViewById(R.id.preAward)).setImageResource(R.drawable.bkex_3);
            hitFlg = 103;
        }
        else if("特別賞-2".equals(award)) {
            ((ImageView) findViewById(R.id.back_ground)).setImageResource(R.drawable.bkex_3);
            ((ImageView) findViewById(R.id.preAward)).setImageResource(R.drawable.bkex_3);
            hitFlg = 102;
        }
        else if("特別賞-1".equals(award)) {
            ((ImageView) findViewById(R.id.back_ground)).setImageResource(R.drawable.bkex_3);
            ((ImageView) findViewById(R.id.preAward)).setImageResource(R.drawable.bkex_3);
            hitFlg = 101;
        }
    }

    private void lottery(){
        String[] hit_num_array = new String[lottery_times];

//        // TODO 開発用
//        updateAllSankaFlg();

        // 抽選対象取得
        if ("特別賞-4".equals(award)) {
            // はげフラグonのときのselect文
            rosterDtoList = selectHageLotteryTarget();
        } else {
            rosterDtoList = selectLotteryTarget();
        }

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
        ArrayList<RosterDto> DtoList = new ArrayList<>();
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
                DtoList.add(rosterDto);
            }
            // シャッフル
            Collections.shuffle(DtoList);

            if(DtoList.size() < lottery_times - 1) {
                while(DtoList.size() < lottery_times) {
                    RosterDto rosterDto = new RosterDto();
                    rosterDto.setLotNum("");
                    DtoList.add(rosterDto);
                }
            }
        } finally {
            db.close();
        }
        return DtoList;
    }

    private ArrayList<RosterDto> selectHageLotteryTarget() {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        ArrayList<RosterDto> hageDtoList = new ArrayList<>();
        try {
            Cursor cursor;
            cursor = db.query(
                    WordContract.Words.TABLE_NAME,
                    null,
                    "sanka_flg = ? AND hit_flg = ? AND hage_flg = ?",
                    new String[]{"1","0","1"},
                    null,
                    null,
                    null
            );
            int numRows = cursor.getCount();

            cursor.moveToFirst();
            for (int i = 0; i < numRows; i++, cursor.moveToNext()) {
                RosterDto rosterDto = new RosterDto();
                rosterDto.setLotNum(cursor.getString(4));
                hageDtoList.add(rosterDto);
            }

            if(hageDtoList.size() > 1) {
                // 長山さんと藤田さんの順番入れ替え
                RosterDto tempDto = hageDtoList.get(1);
                hageDtoList.remove(1);
                hageDtoList.add(tempDto);
            }

            // 後ろに普通に抽選した人を加える
            ArrayList<RosterDto> tempDtoList = selectLotteryTarget();
            hageDtoList.addAll(tempDtoList);

        } finally {
            db.close();
        }
        return hageDtoList;
    }

    private void updateLotteryTarget(String[] hit_num_array) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put("hit_flg", hitFlg);
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

    private void rouletteStart() {
        // 1
        final Button roulette1 = findViewById(R.id.roulette1);
        roulette1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ranNum1=false;
                roulette1.setVisibility(View.INVISIBLE);
            }
        });

        // 2
        final Button roulette2 = findViewById(R.id.roulette2);
        roulette2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ranNum2=false;
                roulette2.setVisibility(View.INVISIBLE);
            }
        });

        // 3
        final Button roulette3 = findViewById(R.id.roulette3);
        roulette3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ranNum3=false;
                roulette3.setVisibility(View.INVISIBLE);
            }
        });

        // 4
        final Button roulette4 = findViewById(R.id.roulette4);
        roulette4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ranNum4=false;
                roulette4.setVisibility(View.INVISIBLE);
            }
        });

        // 5
        final Button roulette5 = findViewById(R.id.roulette5);
        roulette5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ranNum5=false;
                roulette5.setVisibility(View.INVISIBLE);
            }
        });

        // 6
        final Button roulette6 = findViewById(R.id.roulette6);
        roulette6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ranNum6=false;
                roulette6.setVisibility(View.INVISIBLE);
            }
        });

        // 7
        final Button roulette7 = findViewById(R.id.roulette7);
        roulette7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ranNum7=false;
                roulette7.setVisibility(View.INVISIBLE);
            }
        });

        // 8
        final Button roulette8 = findViewById(R.id.roulette8);
        roulette8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ranNum8=false;
                roulette8.setVisibility(View.INVISIBLE);
            }
        });

        // 9
        final Button roulette9 = findViewById(R.id.roulette9);
        roulette9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ranNum9=false;
                roulette9.setVisibility(View.INVISIBLE);
            }
        });

        // 10
        final Button roulette10 = findViewById(R.id.roulette10);
        roulette10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ranNum10=false;
                roulette10.setVisibility(View.INVISIBLE);
            }
        });
    }}

    private void loop1() {
        final Handler handler = new Handler();
        final Button roulette1 = findViewById(R.id.roulette1);
        // 別スレッドを実行
        new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    // Handlerを使用してメイン(UI)スレッドに処理を依頼する
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String ranNum;
                            ranNum = Integer.toString(ThreadLocalRandom.current().nextInt(100, 1000));
                            roulette1.setText(ranNum);
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (ranNum1);
            }
        }).start();
    }
    private void loop2() {
        final Handler handler = new Handler();
        final Button roulette1 = findViewById(R.id.roulette2);
        // 別スレッドを実行
        new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    // Handlerを使用してメイン(UI)スレッドに処理を依頼する
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String ranNum;
                            ranNum = Integer.toString(ThreadLocalRandom.current().nextInt(100, 1000));
                            roulette1.setText(ranNum);
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (ranNum2);
            }
        }).start();
    }
    private void loop3() {
        final Handler handler = new Handler();
        final Button roulette1 = findViewById(R.id.roulette3);
        // 別スレッドを実行
        new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    // Handlerを使用してメイン(UI)スレッドに処理を依頼する
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String ranNum;
                            ranNum = Integer.toString(ThreadLocalRandom.current().nextInt(100, 1000));
                            roulette1.setText(ranNum);
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (ranNum3);
            }
        }).start();
    }
    private void loop4() {
        final Handler handler = new Handler();
        final Button roulette1 = findViewById(R.id.roulette4);
        // 別スレッドを実行
        new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    // Handlerを使用してメイン(UI)スレッドに処理を依頼する
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String ranNum;
                            ranNum = Integer.toString(ThreadLocalRandom.current().nextInt(100, 1000));
                            roulette1.setText(ranNum);
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (ranNum4);
            }
        }).start();
    }
    private void loop5() {
        final Handler handler = new Handler();
        final Button roulette1 = findViewById(R.id.roulette5);
        // 別スレッドを実行
        new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    // Handlerを使用してメイン(UI)スレッドに処理を依頼する
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String ranNum;
                            ranNum = Integer.toString(ThreadLocalRandom.current().nextInt(100, 1000));
                            roulette1.setText(ranNum);
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (ranNum5);
            }
        }).start();
    }
    private void loop6() {
        final Handler handler = new Handler();
        final Button roulette1 = findViewById(R.id.roulette6);
        // 別スレッドを実行
        new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    // Handlerを使用してメイン(UI)スレッドに処理を依頼する
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String ranNum;
                            ranNum = Integer.toString(ThreadLocalRandom.current().nextInt(100, 1000));
                            roulette1.setText(ranNum);
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (ranNum6);
            }
        }).start();
    }
    private void loop7() {
        final Handler handler = new Handler();
        final Button roulette1 = findViewById(R.id.roulette7);
        // 別スレッドを実行
        new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    // Handlerを使用してメイン(UI)スレッドに処理を依頼する
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String ranNum;
                            ranNum = Integer.toString(ThreadLocalRandom.current().nextInt(100, 1000));
                            roulette1.setText(ranNum);
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (ranNum7);
            }
        }).start();
    }
    private void loop8() {
        final Handler handler = new Handler();
        final Button roulette1 = findViewById(R.id.roulette8);
        // 別スレッドを実行
        new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    // Handlerを使用してメイン(UI)スレッドに処理を依頼する
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String ranNum;
                            ranNum = Integer.toString(ThreadLocalRandom.current().nextInt(100, 1000));
                            roulette1.setText(ranNum);
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (ranNum8);
            }
        }).start();
    }
    private void loop9() {
        final Handler handler = new Handler();
        final Button roulette1 = findViewById(R.id.roulette9);
        // 別スレッドを実行
        new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    // Handlerを使用してメイン(UI)スレッドに処理を依頼する
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String ranNum;
                            ranNum = Integer.toString(ThreadLocalRandom.current().nextInt(100, 1000));
                            roulette1.setText(ranNum);
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (ranNum9);
            }
        }).start();
    }
    private void loop10() {
        final Handler handler = new Handler();
        final Button roulette1 = findViewById(R.id.roulette10);
        // 別スレッドを実行
        new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    // Handlerを使用してメイン(UI)スレッドに処理を依頼する
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String ranNum;
                            ranNum = Integer.toString(ThreadLocalRandom.current().nextInt(100, 1000));
                            roulette1.setText(ranNum);
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (ranNum10);
            }
        }).start();
    }

    // 開発用 全参加フラグON
//    private void updateAllSankaFlg() {
//        SQLiteDatabase db = openHelper.getWritableDatabase();
//        try {
//            ContentValues cv = new ContentValues();
//            cv.put("sanka_flg", 1);
//            for (int i = 0; i < 126; i++) {
//                cv.put("lot_num", i + 1);
//                db.update(
//                        WordContract.Words.TABLE_NAME,
//                        cv,
//                        "num = ?",
//                        new String[]{String.valueOf(i + 1)}
//                );}
//            }
//        } finally {
//            db.close();
//        }
//    }
