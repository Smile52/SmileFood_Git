package com.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.Bean.MyButton;
import com.example.smilefood.R;

import java.util.List;

/**
 * 首页按钮GridView适配器
 * Created by 九龙 on 2015/11/13.
 */
public class MyButtonAdapter extends ArrayAdapter<MyButton> {
    private int resourceId;
    public MyButtonAdapter(Context context, int resource, List<MyButton> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyButton myButton=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView imageView= (ImageView) view.findViewById(R.id.id_homepage_img);
        TextView textView= (TextView) view.findViewById(R.id.id_homepage_text);
        imageView.setImageResource(myButton.getImageId());
        textView.setText(myButton.getText());

        return view;
    }

}

