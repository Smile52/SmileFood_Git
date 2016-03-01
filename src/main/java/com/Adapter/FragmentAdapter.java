package com.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by qq272 on 2015/11/10.
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    Fragment[] fragmentArray;
    public FragmentAdapter(FragmentManager fm, Fragment[] fragmentArray2) {
        super(fm);
        if(fragmentArray2==null){
            this.fragmentArray=new Fragment[]{};
        }else{
            this.fragmentArray=fragmentArray2;
        }
    }

    @Override
    public Fragment getItem(int arg0) {

        return fragmentArray[arg0];
    }

    @Override
    public int getCount() {

        return fragmentArray.length;
    }
}
