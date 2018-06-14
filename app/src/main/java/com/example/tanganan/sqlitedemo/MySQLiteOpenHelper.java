package com.example.tanganan.sqlitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by TangAnna on 2018/6/13.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {


    /**
     * 提供外界快速使用的构造器
     *
     * @param context
     */
    public MySQLiteOpenHelper(Context context) {
        this(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
    }

    /**
     * 构造方法
     *
     * @param context
     * @param name    数据库名称
     * @param factory 一个可选的游标工厂（通常是 Null）
     * @param version 当前数据库的版本，值必须是整数并且是递增的状态
     */
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 数据库第1次创建时 则会调用，即 第1次调用 getWritableDatabase（） / getReadableDatabase（）时调用
     * 作用：创建数据库 表 & 初始化数据
     * SQLite数据库创建支持的数据类型： 整型数据、字符串类型、日期类型、二进制
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //使用execSQL执行SQL语句
        //创建一张student表
        String sql = "create table " + Constant.TABLE_STUDENT_NAME + " " +
                "(" + Constant.STUDENT_ID + " integer primary key autoincrement," +
                "" + Constant.STUDENT_NAME + " varchar(64)," +
                "" + Constant.STUDENT_SEX + " varchar(10)," +
                "" + Constant.STUDENT_AGE + " integer)";
        db.execSQL(sql);
    }

    /**
     * 调用时刻：当数据库升级时则自动调用（即 数据库版本 发生变化时）
     * 作用：更新数据库表结构
     * 注：创建SQLiteOpenHelper子类对象时,必须传入一个version参数，该参数 = 当前数据库版本,
     * 若该版本高于之前版本, 就调用onUpgrade()
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 使用 SQL的ALTER语句
        String sql = "alter table person add sex varchar(8)";
        String sql1 = "alter table student add age integer";
        db.execSQL(sql1);
    }

    /**
     * 创建 or 打开 可读/写的数据库（通过 返回的SQLiteDatabase对象 进行操作）
     *
     * @return
     */
    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    /**
     * 创建 or 打开 可读/写的数据库（通过 返回的SQLiteDatabase对象 进行操作）
     *
     * @return
     */
    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

}
