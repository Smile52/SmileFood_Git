package com.example.smilefood;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.Config.Config;
import com.Smile.Intent.webService;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;


/**用户注册
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
                volleyRejist();
            }
        });
    }

    /**
     * 初始化
     */
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

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getQueue().cancelAll("volleypost");
    }

    /**
     * 注册网络请求 post
     */
    private void volleyRejist(){
        Log.i("dandy","wocao");
        String  url="http://"+ Config.IP + "/SmileFoodServer/servlet/RejistUserServlet";

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("dandy", "你妹啊" + s);
                Toast.makeText(RejistActivity.this,"注册成功",2000).show();
                startActivity(new Intent(RejistActivity.this,LoginActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("dandy", "请求错误" + volleyError.toString());
                Toast.makeText(RejistActivity.this,"注册失败",2000).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();
                map.put("newname",newUserName_et.getText().toString());
                map.put("newnum",newUserNum_et.getText().toString());
                map.put("newpwd",newUserPwd_et.getText().toString());
                map.put("newphone",newUserPhone_et.getText().toString());
                return map;
            }

        };
        request.setTag("volleypost");
        MyApplication.getQueue().add(request);
    }

}
