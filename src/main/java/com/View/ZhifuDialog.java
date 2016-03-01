package com.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.example.smilefood.R;

/**
 * Created by qq272 on 2016/2/26.
 */
public class ZhifuDialog extends Dialog {
    public ZhifuDialog(Context context) {
        super(context);
    }

    public ZhifuDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhifudialog);
    }
}
