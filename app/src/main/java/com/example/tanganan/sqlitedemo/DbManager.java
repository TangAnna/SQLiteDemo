package com.example.tanganan.sqlitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

/**
 * Created by TangAnna on 2018/6/14.
 */

public class DbManager {
    private static MySQLiteOpenHelper sqLiteOpenHelper;

    /**
     * DbManager提供的SQLiteOpenHelper的对象
     *
     * @param context
     * @return SQLiteOpenHelper的对象
     */
    public static MySQLiteOpenHelper getSqLiteOpenHelper(Context context) {
        if (sqLiteOpenHelper == null) {
            sqLiteOpenHelper = new MySQLiteOpenHelper(context);
        }
        return sqLiteOpenHelper;
    }

    /**
     * 执行sql语句
     *
     * @param db  数据库对象
     * @param sql sql语句
     */
    public static void execSql(SQLiteDatabase db, String sql) {
        if (db != null && !TextUtils.isEmpty(sql)) {
            db.execSQL(sql);
        }

    }
}
