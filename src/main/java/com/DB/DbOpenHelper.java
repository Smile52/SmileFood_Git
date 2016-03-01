package com.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.Bean.Food;

/**数据库类
 * Created by Smile on 2015/11/27.
 */
public class DbOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="food.db";
    private static final int DB_VERSION=1;
    //单例模式
    private DbOpenHelper(Context context){
        super(context.getApplicationContext(),DB_NAME,null,DB_VERSION);
    }
    private static DbOpenHelper dbOpenHelper;
    public static DbOpenHelper getInstance(Context context){
        if(dbOpenHelper==null){
            synchronized (DbOpenHelper.class){
                if (dbOpenHelper==null){
                    dbOpenHelper=new DbOpenHelper(context);
                }
            }
        }
        return dbOpenHelper;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table "+ Food.TABLE_NAME+" (" +
                Food.COLUMN_FOODID + " integer ,"+
                Food.COLUMN_FOODNAME + " integer ,"+
                Food.COLUMN_FOODPRICE + " double ,"+
                Food.COLUMN_FOODDETAIL + " text ,"+
                Food.COLUMN_FOODURL + " text ,"+
                Food.COLUMN_FOODCOUNT + " integer ,"+
                Food.COLUMN_FOODTYPE + " integer "
                +")";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
