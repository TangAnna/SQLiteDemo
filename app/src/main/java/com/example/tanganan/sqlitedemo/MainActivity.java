package com.example.tanganan.sqlitedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SQLiteOpenHelper mHelper;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.tv_sql_show);
        findViewById(R.id.tv_sql_create).setOnClickListener(this);
        findViewById(R.id.tv_sql_query).setOnClickListener(this);
        findViewById(R.id.tv_sql_insert).setOnClickListener(this);
        findViewById(R.id.tv_sql_delete).setOnClickListener(this);
        findViewById(R.id.tv_sql_deleteTable).setOnClickListener(this);
        findViewById(R.id.tv_sql_update).setOnClickListener(this);
        mHelper = DbManager.getSqLiteOpenHelper(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sql_create:
                mHelper.getWritableDatabase();
                break;
            case R.id.tv_sql_insert:
                //为避免资源占用问题每次最好成对的写打开|创建数据库  和关闭数据库语句
                SQLiteDatabase db = mHelper.getWritableDatabase();
                //使用sql的方式插入数据
                String sql = "insert into " + Constant.TABLE_STUDENT_NAME + "(" + Constant.STUDENT_NAME + ","
                        + Constant.STUDENT_SEX +
                        "," + Constant.STUDENT_AGE + ")" +
                        " values('魏大勋','男',30)";
                DbManager.execSql(db, sql);
                //使用API的方式插入数据
                ContentValues values = new ContentValues();
                values.put(Constant.STUDENT_NAME, "魏大勋API");
                values.put(Constant.STUDENT_AGE, "30");
                values.put(Constant.STUDENT_SEX, "男");
                db.insert("student", null, values);
                query();
                db.close();
                break;
            case R.id.tv_sql_query:
                SQLiteDatabase dbQuery = mHelper.getWritableDatabase();
                query();
                dbQuery.close();
                break;
            case R.id.tv_sql_delete:
                SQLiteDatabase dbDelete = mHelper.getWritableDatabase();
                String sqlDelete = "delete from " + Constant.TABLE_STUDENT_NAME + " where " + Constant.STUDENT_ID + "=1";
                DbManager.execSql(dbDelete, sqlDelete);

                dbDelete.delete(Constant.TABLE_STUDENT_NAME, Constant.STUDENT_ID + "=?", new String[]{"5"});
                query();
                dbDelete.close();
                break;
            case R.id.tv_sql_update:
                SQLiteDatabase dbUpdate = mHelper.getWritableDatabase();
                String sqlUpdate = "update " + Constant.TABLE_STUDENT_NAME + " set " + Constant.STUDENT_NAME + "='范冰冰',"
                        + Constant.STUDENT_SEX + "='女' where " + Constant.STUDENT_ID + "=10";
                DbManager.execSql(dbUpdate, sqlUpdate);
                ContentValues values1 = new ContentValues();
                values1.put(Constant.STUDENT_NAME, "白敬亭");
                dbUpdate.update(Constant.TABLE_STUDENT_NAME, values1, Constant.STUDENT_NAME + "=?", new String[]{"魏大勋"});
                query();
                dbUpdate.close();

                break;
            case R.id.tv_sql_deleteTable:
                SQLiteDatabase dbDeleteTable = mHelper.getWritableDatabase();
                String sqlDeleteTable = "drop table " + Constant.TABLE_STUDENT_NAME + "";
                DbManager.execSql(dbDeleteTable, sqlDeleteTable);
                dbDeleteTable.close();
                break;
        }
    }

    /**
     * 查询   查询的方法不能使用SQL语句的方式实现
     */
    public void query() {
        Cursor cursor = mHelper.getWritableDatabase().query("student", new String[]{"_id", "name", "sex"}, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String strResult = "";
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                String sex = cursor.getString(cursor.getColumnIndex("sex"));
                strResult = strResult + id + " " + name + " " + sex + "\n";
            } while (cursor.moveToNext());
            mTextView.setText(strResult);
        } else {
            mTextView.setText("数据库没有数据");
        }
    }
}
