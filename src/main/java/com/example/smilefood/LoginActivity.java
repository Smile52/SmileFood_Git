package com.example.smilefood;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SyncStatusObserver;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.Smile.Intent.webService;
import com.umeng.analytics.MobclickAgent;

import java.sql.SQLOutput;

/**用户登录
 * Created by qq272 on 2015/11/5.
 */
public class LoginActivity extends Activity {
    private EditText user_Num_et;
    private EditText user_Pwd_et;
    private Button login_btn;
    private TextView rejist_tv;
    private ProgressDialog dialog;
    // 返回的数据
    private String info;
    // 返回主线程更新数据
    private  Handler handler = new Handler();
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.login_activity);

        initViews();


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new ProgressDialog(LoginActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("正在登陆，请稍后...");
                dialog.setCancelable(false);
                dialog.show();
                // 创建子线程，分别进行Get传输
                new Thread(new MyThread()).start();

            }
        });
        rejist_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RejistActivity.class);

                startActivity(intent);
            }
        });
    }
    public class  MyThread implements Runnable {

        @Override
        public void run() {
            try {
                info= webService.executeHttpGet(user_Num_et.getText().toString(),user_Pwd_et.getText().toString());
                System.out.println("返回数据"+info);
            }catch (Exception e){
                e.printStackTrace();
            }


            handler.post(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    if(info==null){
                        Toast.makeText(LoginActivity.this,"请求错误",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(info.equals("Success")){
                        SavaUserinfo(user_Num_et.getText().toString(), user_Pwd_et.getText().toString());
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        //进入动画
                        overridePendingTransition(R.anim.fade, R.anim.hold);
                        finish();
                    }else
                    Toast.makeText(LoginActivity.this,"密码或者账号错误",Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    /**
     * 初始化
     */
    private void initViews() {
        user_Num_et= (EditText) findViewById(R.id.user_num_et);
        user_Pwd_et= (EditText) findViewById(R.id.user_pwd_et);
        login_btn= (Button) findViewById(R.id.login_btn);
        rejist_tv= (TextView) findViewById(R.id.rejist_tv);

        preferences=getSharedPreferences("user",Context.MODE_PRIVATE);
        user_Num_et.setText(preferences.getString("user",""));
        user_Pwd_et.setText(preferences.getString("userpwd",""));
    }

    /**
     * 保存用户账户密码
     */
    private void SavaUserinfo(String usernum,String userpwd){
        SharedPreferences preference=getSharedPreferences("user",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preference.edit();
        edit.putString("user",usernum);
        edit.putString("userpwd",userpwd);
        edit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //友盟统计
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {

        super.onPause();
        //友盟统计
        MobclickAgent.onPause(this);
    }
}
