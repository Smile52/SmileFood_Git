package com.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.Adapter.MyButtonAdapter;
import com.Bean.MyButton;
import com.example.smilefood.AboutStoreActivity;
import com.example.smilefood.MainActivity;
import com.example.smilefood.R;
import com.example.smilefood.SendActivity;

import java.util.ArrayList;
import java.util.List;

/**首页Fragment
 * Created by qq272 on 2015/11/10.
 */
public class HomePageFragment extends android.support.v4.app.Fragment {
    private GridView mGridView;
    private List<MyButton> myButtonList=new ArrayList<MyButton>();
    private String[] texts={"我要吃饭","给我们建议","设置","关于店铺","退出"};
    private MyButtonAdapter myButtonAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View homepage_view=inflater.inflate(R.layout.homepage_fragment,container,false);
        TextView title_text= (TextView) homepage_view.findViewById(R.id.id_title_text);
        title_text.setText("首页");
        return homepage_view ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mGridView= (GridView) view.findViewById(R.id.id_gridview);
        initData();
        initGridView();

        super.onViewCreated(view, savedInstanceState);
    }

    private void initData() {
        MyButton order=new MyButton(R.drawable.ic_order,"我要吃饭");
        MyButton advice=new MyButton(R.drawable.ic_advice,"给我们建议");
        MyButton setting=new MyButton(R.drawable.ic_setting,"设置");
        MyButton about=new MyButton(R.drawable.ic_about,"关于店铺");
        MyButton exit=new MyButton(R.drawable.ic_exit_bg,"退出");
        //将Button添加到集合里面去
        myButtonList.add(order);
        myButtonList.add(advice);
        myButtonList.add(setting);
        myButtonList.add(about);
        myButtonList.add(exit);
    }

    /**
     * 初始化GridView
     */
    private void initGridView(){
        myButtonAdapter=new MyButtonAdapter(getActivity(),R.layout.homepage_button,myButtonList);
        mGridView.setAdapter(myButtonAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0 :

                        MainActivity.mViewPager.setCurrentItem(1);//跳转到今日推荐Fragment
                        break;
                    case 1:
                        Intent intent=new Intent(getContext(),SendActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        MainActivity.mDrawerLayout.openDrawer(Gravity.LEFT);
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(), AboutStoreActivity.class));
                        break;
                    case 4:
                        System.exit(0);
                        break;
                }

            }
        });
    }
}
