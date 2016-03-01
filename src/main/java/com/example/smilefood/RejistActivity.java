package com.example.smilefood;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.Smile.Intent.webService;
import com.umeng.analytics.MobclickAgent;


/**
 * Created by qq272 on 2015/11/8.
 */
public class RejistActivity extends Activity {
    private EditText newUserNum_et;
    private EditText newUserPwd_et;
    private EditText newUserName_et;
    private EditText newUserPhone_et;
    private EditText againPwd_et;
    private ImageView back_img;
    private Button rejist_btn;
    private String info;//请求服务器返回的信息
    private String userinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //沉浸式状态栏
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
        setContentView(R.layout.rejist);
        super.onCreate(savedInstanceState);
        initviews();
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rejist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new Thread(new RejistThread()).start();
            }
        });

    }

    private void initviews() {
        newUserNum_et= (EditText) findViewById(R.id.id_newusernum_et);
        newUserName_et= (EditText) findViewById(R.id.id_newusername_et);
        newUserPwd_et= (EditText) findViewById(R.id.id_newuserpwd_et);
        newUserPhone_et= (EditText) findViewById(R.id.id_newuserphone_et);
        againPwd_et= (EditText) findViewById(R.id.id_againpwd_et);
        rejist_btn= (Button) findViewById(R.id.id_rejist_btn);
        back_img= (ImageView) findViewById(R.id.id_back_img);

    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {

        super.onPause();
        MobclickAgent.onPause(this);
    }

    public class  RejistThread implements Runnable{

        @Override
        public void run() {
            userinfo="newnum="+newUserNum_et.getText().toString()+"&newpwd="+newUserPwd_et.getText().toString()+"" +
                    "&newphone="+newUserPhone_et.getText().toString()+"&newname="+newUserName_et.getText().toString();

            webService.rejistUser(userinfo);
        }
    }
}
