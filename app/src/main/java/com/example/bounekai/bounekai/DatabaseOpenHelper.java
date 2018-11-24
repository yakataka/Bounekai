package com.example.bounekai.bounekai;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "open.db";  //データベース名
    private static final int DB_VERSION = 1;  //データベースのバージョン
    private final Context mContext;  //コンテキスト
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS Member ( " +
                    "num INTEGER PRIMARY KEY NOT NULL ," + //通し番号
                    "kana_name TEXT ," + //ｶﾅ氏名
                    "name TEXT ," + //氏名
                    "syain_num TEXT ," + //社員番号
                    "lot_num TEXT ," + //抽選番号
                    "hit_flg INTEGER ," + //当選フラグ
                    "sanka_yotei_flg INTEGER ," + //参加予定フラグ
                    "sanka_flg INTEGER ," + //参加フラグ
                    "money_flg INTEGER ," + //徴収フラグ
                    "hage_flg INTEGER )"; //はげフラグ


    //コンストラクタ
    public DatabaseOpenHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
    }
    //データベースが作成された時に呼ばれる
    //assets/sql内に定義されているsqlを実行します
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
            execSql(db,"sql");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //引数に指定したassetsフォルダ内のsqlを実行する
    private void execSql(SQLiteDatabase db, String assetsDir) throws IOException {
        AssetManager as = mContext.getResources().getAssets();
        try{
            String files[] = as.list(assetsDir);
            for (int i = 0; i < files.length; i++){
                String str = readFile(as.open(assetsDir + "/" + files[i]));
                for (String sql: str.split("/")){
                    db.execSQL(sql);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //ファイルから文字列を読み込む
    private String readFile(InputStream is) throws IOException{
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = br.readLine()) != null){
                sb.append(str + "\n");
            }
            return  sb.toString();
        }finally {
            if (br != null)br.close();
        }
    }
}
