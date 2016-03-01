package com.DB;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.Bean.Food;

import java.util.ArrayList;
import java.util.List;

/**将数据库层封装类
 * Created by Smile on 2015/11/28.
 */
public class DbOpenHelperDao {
    private DbOpenHelper openHelper;
    private List<Food> datalist;

    public  DbOpenHelperDao(DbOpenHelper openHelper){
        this.openHelper=openHelper;
    }

    /**
     * 查询全部菜的数据
     * @return
     */
    public List<Food> queryDatas(){
        SQLiteDatabase db=openHelper.getReadableDatabase();
        datalist=new ArrayList<Food>();
        if (db.isOpen()){
            Cursor cursor=db.query("tb_foodinfo",new String[]{Food.COLUMN_FOODID,Food.COLUMN_FOODNAME,Food.COLUMN_FOODPRICE,
            Food.COLUMN_FOODDETAIL,Food.COLUMN_FOODURL,Food.COLUMN_FOODCOUNT,Food.COLUMN_FOODTYPE},null,null,null,null,null);

        }
        return datalist;
    }

    /**
     * 查询数据库里面的菜
     * @return  菜集合
     */
    public List<Food> query(){
        SQLiteDatabase db=openHelper.getReadableDatabase();
        datalist=new ArrayList<Food>();
        Cursor c=db.rawQuery("SELECT * FROM tb_foodinfo", null);
        while (c.moveToNext()){
            Food food=new Food();
            food.setFoodId(c.getInt(c.getColumnIndex(Food.COLUMN_FOODID)));;
            food.setFoodName(c.getString(c.getColumnIndex(Food.COLUMN_FOODNAME)));
            food.setFoodPrice(c.getDouble(c.getColumnIndex(Food.COLUMN_FOODPRICE)));
            food.setFoodUrl(c.getString(c.getColumnIndex(Food.COLUMN_FOODURL)));
            food.setFoodDetail(c.getString(c.getColumnIndex(Food.COLUMN_FOODDETAIL)));
            food.setFoodCount(c.getInt(c.getColumnIndex(Food.COLUMN_FOODCOUNT)));
            food.setFoodType(c.getInt(c.getColumnIndex(Food.COLUMN_FOODTYPE)));
            datalist.add(food);
        }
        return datalist;

    }

    /**
     * 插入数据
     * @param foods
     * @return
     */
    public void insert(List<Food> foods){
        SQLiteDatabase db=openHelper.getWritableDatabase();
        db.beginTransaction();//开启事务
        try {
            for(Food food:foods){
                db.execSQL("INSERT INTO tb_foodinfo VALUES(null,?,?,?,?,?,?)",
                        new Object[]{food.getFoodName(),food.getFoodPrice(),
                                food.getFoodDetail(),food.getFoodUrl(),food.getFoodCount(),food.getFoodType()});

            }
            db.setTransactionSuccessful();//设置事务成功完成
        }finally {
            db.endTransaction();//结束事务
        }
        
    }
    
    public void delete(){
        SQLiteDatabase db=openHelper.getWritableDatabase();
        db.execSQL("DELETE FROM tb_foodinfo");
    }
}
