package com.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**菜实体
 * Created by 九龙 on 2015/11/15.
 */
public class Food implements Serializable {
    private int foodId;
    private String foodName;
    private double foodPrice;
    private String foodDetail;
    private String foodUrl;
    private int foodCount;
    private int foodType;

    //数据库字段名
    public static final String TABLE_NAME="tb_foodinfo";
    public static final String COLUMN_FOODID="_foodId";
    public static final String COLUMN_FOODNAME="foodName";
    public static final String COLUMN_FOODPRICE="foodPrice";
    public static final String COLUMN_FOODDETAIL="foodDetail";
    public static final String COLUMN_FOODURL="foodUrl";
    public static final String COLUMN_FOODCOUNT="foodCount";
    public static final String COLUMN_FOODTYPE="foodType";

    public Food() {

    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodDetail() {
        return foodDetail;
    }

    public void setFoodDetail(String foodDetail) {
        this.foodDetail = foodDetail;
    }

    public String getFoodUrl() {
        return foodUrl;
    }

    public void setFoodUrl(String foodUrl) {
        this.foodUrl = foodUrl;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }

    public int getFoodType() {
        return foodType;
    }

    public void setFoodType(int foodType) {
        this.foodType = foodType;
    }


}
