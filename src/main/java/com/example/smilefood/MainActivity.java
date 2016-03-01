package com.example.smilefood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.Adapter.FragmentAdapter;
import com.Fragment.HomePageFragment;
import com.Fragment.PersonFragment;
import com.Fragment.RecommendFragment;
import com.Fragment.ShoppingFragment;
import com.View.SmileDialog;
import com.umeng.analytics.MobclickAgent;


public class MainActivity extends FragmentActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener{
    private SharedPreferences preferences;
    public static DrawerLayout mDrawerLayout;
    private ListView listview;
    private Button button;
    private String[] menu=new String[]{"菜单1","注册新用户","菜单3","菜单4"};
    //底部四个按钮（tab）
    private LinearLayout mTabHomePage;
    private LinearLayout mTabRecommend;
    private LinearLayout mTabShopping;
    private LinearLayout mTabPerson;
    private long exitTime = 0;
    int i=0;
    private TextView user_tv;
    public static ViewPager mViewPager;//用static修饰 为了方便其他页面调用


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //沉浸式状态栏
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                   );
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

        }

        setContentView(R.layout.activity_main);
        //读取保存的用户名
        preferences=getSharedPreferences("user", Context.MODE_PRIVATE);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //listview=(ListView) findViewById(R.id.left_drawer);
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);

        user_tv= (TextView) findViewById(R.id.user_tv);
        user_tv.setText("当前用户："+preferences.getString("user",""));
        mTabHomePage= (LinearLayout) findViewById(R.id.id_homepage_tab);
        mTabRecommend= (LinearLayout) findViewById(R.id.id_recommend_tab);
        mTabShopping= (LinearLayout) findViewById(R.id.id_shopping_tab);
        mTabPerson= (LinearLayout) findViewById(R.id.id_person_tab);

        mTabHomePage.setOnClickListener(this);
        mTabPerson.setOnClickListener(this);
        mTabRecommend.setOnClickListener(this);
        mTabShopping.setOnClickListener(this);
        mViewPager= (ViewPager) findViewById(R.id.id_viewpager);


        initFragment();
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        HomePageFragment homePageFragment=new HomePageFragment();
        RecommendFragment recommendFragment=new RecommendFragment();
        ShoppingFragment shoppingFragment=new ShoppingFragment();
        PersonFragment personFragment=new PersonFragment();
        Fragment[] fragmentArray=new Fragment[]{homePageFragment,recommendFragment,shoppingFragment,personFragment};
        FragmentAdapter adapter=new FragmentAdapter(getSupportFragmentManager(),fragmentArray);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(position==1&&i==0){
                    //当滑到第二个Fragment时 出现对话框，并且只有第一次滑的时候出现
                    SmileDialog smileDialog=new SmileDialog(MainActivity.this,R.style.Transparent);
                    smileDialog.show();
                    i++;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_homepage_tab:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.id_recommend_tab:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.id_shopping_tab:
               ;mViewPager.setCurrentItem(2);
                break;
            case R.id.id_person_tab:
                mViewPager.setCurrentItem(3);
                break;
            default:
                break;
        }
    }

    /**
     * 监听点击两次返回键
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 程序退出
     */
    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            //退出动画
            overridePendingTransition(R.anim.hold, R.anim.fade);
            System.exit(0);
        }
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

    /**
     * 左侧菜单栏事件
     * @param menuItem
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.rejist_menu) {
            Intent intent=new Intent(MainActivity.this,RejistActivity.class);
            startActivity(intent);

        } else if (id == R.id.camera_menu) {
            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//隐式启动
            startActivity(intent);


        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}
