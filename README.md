# SQLite的使用
_SQLite数据库_ 
数据库的种类有很多，在Android中我们使用SQLite这种嵌入式的数据库
特点：小型的、可嵌入、效率高、开源的、无数据类型、关系型数据库、程序驱动、支持事物操作

Android中的数据存储有五种方式：
1.SharedPreferences
2.文件存储
3.ContentProvider
4.数据库存储
5.网络存储
其中数据库存储的方式经常会用到，这里讲解一下使用方式，主要就是增、删、改、查的操作
在Android中有一个数据库辅助类SQLiteOpenHelper，使用要创建他的子类并重写其中了两个方法和一个构造方法
    
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
    
操作数据库时有两种方式：
1.直接写sql语句，使用SQLiteDatabase的execSQl(String sql)方法执行（推荐使用）；
2.SQLiteDatabase给我们封装好了常用的API我们可以直接调用；

总结一下sql语句的使用
1.创建表  

create table 表名(字段名 数据类型 是否是主键 是否自增，字段名 数据类型，.....)  

create table student (_id integer primary key,name varchar(32),age integer)</br>

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


