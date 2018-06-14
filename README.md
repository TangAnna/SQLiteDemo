# SQLite的使用
_SQLite数据库_ 
数据库的种类有很多，在Android中我们使用SQLite这种嵌入式的数据库<br />
特点：小型的、可嵌入、效率高、开源的、无数据类型、关系型数据库、程序驱动、支持事物操作

Android中的数据存储有五种方式：<br />
1.SharedPreferences<br />
2.文件存储<br />
3.ContentProvider<br />
4.数据库存储<br />
5.网络存储<br />
其中数据库存储的方式经常会用到，这里讲解一下使用方式，主要就是增、删、改、查的操作<br />
在Android中有一个数据库辅助类SQLiteOpenHelper，使用要创建他的子类并重写其中了两个方法和一个构造方法<br />
onCreate()方法
    
    /**
         * 数据库第1次创建时 则会调用，即 第1次调用 getWritableDatabase（） / getReadableDatabase（）时调用
         * 作用：创建数据库 创建表
         *
         * @param db
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
          //TODO 一般是创建需要用到的表        
        }
onUpgrade()方法
    
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
            String sql1 = "alter table student add age integer";
            db.execSQL(sql1);
        }
构造方法

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
    
SQLiteOpenHelper中有两个方法是获取SQLiteDatabase对象的，一个是getReadableDatabase()另一个是getWritableDatabase()
这两个方法从方法名称上看一个是获取可读的数据库，一个是获取可写入的数据库，但其实这两个方法发的真正含义是创建或者打开数据库，
这个数据库正常情况是可读可写的
只有在特殊情况下才分只可读 | 写（磁盘不足时 | 数据库限制了权限）
    
    
    
操作数据库时有两种方式：<br /> 
1.直接写sql语句，使用SQLiteDatabase的execSQl(String sql)方法执行（推荐使用）；<br /> 
2.SQLiteDatabase给我们封装好了常用的API我们可以直接调用；<br /> 

总结一下sql语句的使用<br /> 
1.创建表  <br /> 
    
     create table 表名(字段名 数据类型 是否是主键 是否自增，字段名 数据类型，.....) 
     create table student (_id integer primary key,name varchar(32),age integer)

2.删除表
    
    drop table 表名
    drop table student

3.插入数据
    
    insert into 表名 (字段名，字段名..) values(value1,value2...)
    insert into student (_id,name) values(1,"小明")
    insert into student values(1,"小明",18)//这省略掉前面字段名的方式后面的value值必须与表中的顺序相对应，并且是全部字段值

4.修改数据
    
    update 表名 set 字段=新值 where 修改条件
    update student set name="张三" where name="小明"

5.删除数据
    
    delete from 表名 where 删除条件
    delete from student where _id=1

6.查询数据
    
    select 字段名(可多个中间用逗号隔开) from 表名 where 查询条件 group by 分组的字段 having 筛选条件 order by 排序字段
    select * from 表名   //表示查询所有数据
    select _id,name from student where age>18
    select * from student
    select * from student where _id<>2  //不等于
    select * from student where _id=2 and age>20
    select * from student where name like "%娜%"   //名字中包含娜的数据
    select * from student where name is null  //姓名是空的数据
    select * from student where age between 18 and 20  //年龄在18到20之间的数据
    select * from student where age >18 order by age    //年龄大于18并且按照年龄排好序

API的使用方式：<br />
1.插入
    
    /**
         * @param table 表名
         * @param nullColumnHack 常使用null
         * @param values Key-value的map
         * @return the row ID of the newly inserted row, or -1 if an error occurred
         */
    public long insert(String table, String nullColumnHack, ContentValues values) {
            
        }
       
    ContentValues values = new ContentValues();
                    values.put(Constant.STUDENT_NAME, "魏大勋API");
                    values.put(Constant.STUDENT_AGE, "30");
                    values.put(Constant.STUDENT_SEX, "男");
                    db.insert("student", null, values);

2.删除<br/>
    
    /**
         * @param table 表名
         * @param whereClause 条件
         * @param whereArgs 占位
         * @return 影响的行数
         */
    public int delete(String table, String whereClause, String[] whereArgs) {
            
    }
    db.delete(Constant.TABLE_STUDENT_NAME, Constant.STUDENT_ID + "=?", new String[]{"5"});
    db.delete(Constant.TABLE_STUDENT_NAME, Constant.STUDENT_ID + "=5", null);

3.修改<br/>
    
    /**
         * @param 表名
         * @param values Key-value的map
         * @param whereClause 修改条件
         * @param whereArgs 占位
         * @return 
         */
        public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {

        }
        ContentValues values1 = new ContentValues();
                        values1.put(Constant.STUDENT_NAME, "白敬亭");
                        db.update(Constant.TABLE_STUDENT_NAME, values1, Constant.STUDENT_NAME + "=?", new String[]{"魏大勋"});

4.查询<br/>
    
    /**
         * @param table 表名
         * @param columns 要查询的字段的数组
         * @param selection 查询条件
         * @param selectionArgs 占位
         * @param groupBy   分组字段
         * @param having    筛选条件
         * @param orderBy 排序字段
         * @return Cursor
         */
        public Cursor query(String table, String[] columns, String selection,
                String[] selectionArgs, String groupBy, String having,
                String orderBy) {
    
        }
        Cursor cursor = db.query("student", new String[]{"_id", "name", "sex"}, null, null, null, null, null);
                
Cursor的使用：<br/>
cursor在Android中是查询数据后得到的一个管理数据集合的类，cursor位于android.database包下面，
可见他的设计就是为数据库使用的。使用SQLiteDatabase.query()方法时就会得到Cursor对象，再根据需求对cursor处理
去除自己需要的数据；<br/>
使用Cursor时如果数据量不大不会造成内存问题，但是如果数据量大的时候会有问题，不要想着要GC处理而是要开发者手动调用cursor.close()；
方法关闭游标释放资源；<br/>
Cursor的常用方法：
    
    cursor.moveToFirst();   //移动到第一行
    cursor.moveToLast();    //移动到最后一行
    cursor.moveToNext();    //移动到下一行
    cursor.move(int offset);    //一当前位置为基准移动到指定位置
    cursor.moveToPosition(int position);    //移动到指定行
    Cursor.moveToPrevious();    //移动到前一行
    Cursor.isFirst();   //是否是指向第一行
    Cursor.isLast();    //是否是指向最后一行
    Cursor.isBeforeFirst();     //是否指向第一行之前
    Cursor.isAfterLast();   //是否指向最后一行之后
    Cursor.isNull(int columnIndex);     //指定列是否为空
    Cursor.isClosed();  //游标是否关闭
    Cursor.getCount();  //获取总数
    Cursor.getPosition();   //当前游标的行标
    Cursor.getColumnIndex(String columnName);   //获取列名对应的索引值
    Cursor.getString(int columnIndex);  //获取当前行指定列的值
    Cursor.close(); //关闭游标，释放资源
    Cursor.getColumnIndexOrThow(String columnName); //从零开始返回列名对应的索引值不存在的话抛出IllegalArgumentException异常

Cursor的遍历方式：<br/>
    
    if(cursor!=null&&cursor.moveToFirst()){
          do{
          }while(cursor.moveToNext);
    }
    
或者
    
    if(cursor!=null&&cursor.moveToFirst()){
           while (!cursor.isAfterLast()) {
    
          }
    }