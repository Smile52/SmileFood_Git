package com.example.smilefood;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by qq272 on 2015/11/5.
 */
public class MyApplication extends Application {
    public static RequestQueue queue;
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        queue= Volley.newRequestQueue(getApplicationContext());//使用全局上下文
        context = getApplicationContext();
    }
    /**
     * 对外公布一个RequestQueue实例
     * @return
     */
    public static RequestQueue getQueue(){
        return queue;
    }

    public static Context getContextObject(){
        return context;
    }
}
