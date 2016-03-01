package com.example.smilefood;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SendAdviceActivity extends AppCompatActivity {
    private TextView aboutTitle_tv;
    private EditText et_content;
    private FloatingActionButton ab_send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_advice);
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
        aboutTitle_tv= (TextView) findViewById(R.id.id_title_text);
        aboutTitle_tv.setText("给我建议");
        et_content= (EditText) findViewById(R.id.id_et_content);
        ab_send= (FloatingActionButton) findViewById(R.id.id_ab_send);
        ab_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String context = et_content.getText().toString();
                SmsManager manager = SmsManager.getDefault();
                ArrayList<String> list = manager.divideMessage(context);  //因为一条短信有字数限制，因此要将长短信拆分
                for(String text:list){
                    manager.sendTextMessage("15575818134", null, text, null, null);
                }
                Toast.makeText(getApplicationContext(), "发送完毕", Toast.LENGTH_SHORT).show();
            }

        });
    }

}
