package com.example.smilefood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class WecomeActivity extends AppCompatActivity {
    private Handler handler=new Handler();
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            //window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_wecome);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
             checkUser();
            }
        },2000);

    }

    /**
     * 判断是否有用户登录过
     */
    private void checkUser(){
      preferences=getSharedPreferences("user", Context.MODE_PRIVATE);
        if(preferences.getString("user","")==""){
            Intent intent=new Intent(WecomeActivity.this,LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade, R.anim.hold);
            finish();
        }
        else {
            Intent intent=new Intent(WecomeActivity.this,MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade, R.anim.hold);
            finish();
        }
    }
}
