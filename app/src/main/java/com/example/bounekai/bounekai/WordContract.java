package com.example.bounekai.bounekai;

import android.provider.BaseColumns;

public final class WordContract {
    public WordContract(){}
    public static abstract class Words implements BaseColumns {
        public static final String TABLE_NAME = "Member";
        public static final String COL_WORD = "num";
        public static final String COL_DESCR = "syain_num";
    }
}
