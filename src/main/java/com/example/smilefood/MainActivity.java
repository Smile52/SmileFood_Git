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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.Adapter.FragmentAdapter;
import com.Fragment.HomePageFragment;
import com.Fragment.PersonFragment;
import com.Fragment.RecommendFragment;
import com.Fragment.ShoppingFragment;
import com.ImageUtils.MyBitmapUtils;
import com.View.SmileDialog;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

/**
 * 主activity
 */
public class MainActivity extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener{
    private SharedPreferences preferences;
    public static DrawerLayout mDrawerLayout;
    private long exitTime = 0;
    int i=0;
    private TextView user_tv;
    public static ViewPager mViewPager;//用static修饰 为了方便其他页面调用
    // 首先在您的Activity中添加如下成员变量
    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
    private RadioGroup rg_group;
    private int[] RadioButtons = new int[]{R.id.rb_home,R.id.rb_tuijian,R.id.rb_shopping,R.id.rb_my};

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

        init();
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);

        user_tv= (TextView) findViewById(R.id.user_tv);

        rg_group = (RadioGroup) findViewById(R.id.rg_Group);
        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_home:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_tuijian:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.rb_shopping:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.rb_my:
                        mViewPager.setCurrentItem(3);
                }
            }
        });
        user_tv.setText("当前用户：" + preferences.getString("user", ""));

        mViewPager= (ViewPager) findViewById(R.id.id_viewpager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position==1&&i==0){
                    //当滑到第二个Fragment时 出现对话框，并且只有第一次滑的时候出现
                    SmileDialog smileDialog=new SmileDialog(MainActivity.this,R.style.Transparent);
                    smileDialog.show();
                    i++;
                }
            }
            @Override
            public void onPageSelected(int position) {
                //当viewpager这页被选中时，设置下面的radiobutton也为选中
                rg_group.check(RadioButtons[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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


        } else if (id == R.id.changeuser) {
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            //分享
            mController.getConfig().removePlatform(SHARE_MEDIA.RENREN,
                    SHARE_MEDIA.DOUBAN);
            //默认分享方式
            mController.openShare(MainActivity.this, false);

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    /**
     * 初始化友盟社会化分享
     */
    public void init() {

        // 设置分享内容
        mController
                .setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能，http://www.umeng.com/social");
        // 设置分享图片, 参数2为图片的url地址
        mController.setShareMedia(new UMImage(this, R.drawable.abc));
//--------------------------------------------------------------------------------------------------------
        // 参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.（自己申请）
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "801556848",
                "5bc9c2b47e38eb5ab50107193e8dce1a");
        qqSsoHandler.addToSocialSDK();
        QQShareContent qqShareContent = new QQShareContent();
        // 设置分享文字
        qqShareContent.setShareContent("SmileFood真是太棒了 --SmileFood");
        // 设置分享title
        qqShareContent.setTitle("hello, title");
        // 设置分享图片
        qqShareContent.setShareImage(new UMImage(this, "http://a.hiphotos.baidu.com/image/pic/item/0bd162d9f2d3572cb1732cc28813632762d0c31f.jpg"));
        // 设置点击分享内容的跳转链接
        qqShareContent.setTargetUrl("http://www.baidu.com");
        mController.setShareMedia(qqShareContent);
//--------------------------------------------------------------------------------------------------------
        // 参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.（自己申请）
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this,
                "801556848", "5bc9c2b47e38eb5ab50107193e8dce1a");
        qZoneSsoHandler.addToSocialSDK();
        QZoneShareContent qzone = new QZoneShareContent();
        // 设置分享文字
        qzone.setShareContent("哇哇，一款好方便的APP，众多美食等你来吃！");
        // 设置点击消息的跳转URL
        qzone.setTargetUrl("http://user.qzone.qq.com/272708698?ADUIN=1094365915&ADSESSION=1448209971&ADTAG=CLIENT.QQ.5449_FriendTip.0&ADPUBNO=26525&ptlang=2052");
        // 设置分享内容的标题
        qzone.setTitle("好用就来下载吧！");
        // 设置分享图片
        qzone.setShareImage(new UMImage(this,R.drawable.abc));
        mController.setShareMedia(qzone);
//--------------------------------------------------------------------------------------------------------
        // 设置腾讯微博SSO handler
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
//--------------------------------------------------------------------------------------------------------
        // 设置新浪SSO handler
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
//--------------------------------------------------------------------------------------------------------
        // 添加微信的appID appSecret要自己申请
        String appID = "wx967daebe835fbeac";
        String appSecret = "5fa9e68ca3970e87a1f83e563c8dcbce";
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(this, appID, appSecret);
        wxHandler.addToSocialSDK();
        // 设置微信好友分享内容
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        // 设置分享文字
        weixinContent.setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能，微信");
        // 设置title
        weixinContent.setTitle("友盟社会化分享组件-微信");
        // 设置分享内容跳转URL
        weixinContent.setTargetUrl("http://www.baidu.com");
        // 设置分享图片
        weixinContent.setShareImage(new UMImage(getApplicationContext(),
                R.mipmap.ic_icon));
        mController.setShareMedia(weixinContent);
//----/*----------------------------------------------------------------------------------------------------
        // 添加微信朋友圈(自会显示title，不会显示内容，官网这样说的)
        UMWXHandler wxCircleHandler = new UMWXHandler(this, appID, appSecret);
        // 设置微信朋友圈分享内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia.setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能，朋友圈");
        // 设置朋友圈title
        circleMedia.setTitle("友盟社会化分享组件-朋友圈");
        circleMedia.setShareImage(new UMImage(getApplicationContext(),
                R.mipmap.ic_icon));
        circleMedia.setTargetUrl("http://www.baidu.com");
        mController.setShareMedia(circleMedia);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
//--------------------------------------------------------------------------------------------------------
        // 添加短信
        SmsHandler smsHandler = new SmsHandler();
        smsHandler.addToSocialSDK();
    }
}
