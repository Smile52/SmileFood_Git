package com.Smile.Intent;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;

import com.Bean.Demo;
import com.Bean.Food;
import com.DB.DbOpenHelper;
import com.DB.DbOpenHelperDao;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.smilefood.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**获取服务器菜数据（json）
 * Created by 九龙 on 2015/11/15.   //谁调用的这个方法
 */
public class GetJson {

    private static String jsonResult;
    private static RequestQueue mRequestQueue;
    private RequestQueue mQueue;
    public static List<Food> mList=new ArrayList<Food>();

    public static void getJsonArray(String url, final Handler mHandler, final Context context){
        //volley框架获取JSON数据方法
        final RequestQueue requestQueue = Volley.newRequestQueue(MyApplication.getContextObject());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {  //只有在请求成功的情况下才会执行这里的方法
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response
                                        .getJSONObject(i);
                                Food food=new Food();
                                food.setFoodId(jsonObject.getInt("Foodid"));
                                food.setFoodName(jsonObject.getString("FoodName"));
                                food.setFoodPrice(jsonObject.getDouble("FoodPrice"));
                                food.setFoodDetail(jsonObject.getString("FoodDetial"));
                                food.setFoodUrl(jsonObject.getString("FoodUrl"));
                                food.setFoodCount(jsonObject.getInt("FoodCount"));
                                food.setFoodType(jsonObject.getInt("FoodType"));
                                mList.add(food);

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        Message msg=new Message();
                        mHandler.sendMessage(msg);
                        //切记，不要将这个mHandler.sendMessage(msg);放在for循环里面，不然会执行N次

                    }
                }, new Response.ErrorListener() {
            //请求服务器失败后将会查询本地数据库
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("请求失败");

                DbOpenHelper dbOpenHelper=DbOpenHelper.getInstance(context);
                DbOpenHelperDao dao=new DbOpenHelperDao(dbOpenHelper);
                mList=dao.query();
                Message msg=new Message();
                mHandler.sendMessage(msg);

            }
        });
        jsonArrayRequest.setTag("GetFoodJson");
        requestQueue.add(jsonArrayRequest);


        //这里返回的肯定是空  因为上面是接口回调的方法；
    }

}
