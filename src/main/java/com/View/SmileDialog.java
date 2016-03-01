package com.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smilefood.R;

/**自定义Dialog
 * Created by Smile on 2015/11/20.
 */
public class SmileDialog extends Dialog {
    private String name;
    private TextView info_tv;
    private Button known_btn;
    private OnCustomDialogListener customDialogListener;

    public SmileDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    //定义回调事件，用于dialog的点击事件
    public interface OnCustomDialogListener{
        public void back(String name);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        info_tv= (TextView) findViewById(R.id.id_dialog_tv);
        info_tv.setText("亲：单击了解详情，\n长按加入购物车哦！");
        known_btn= (Button) findViewById(R.id.id_know_btn);
        known_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmileDialog.this.dismiss();
            }
        });
    }
}
