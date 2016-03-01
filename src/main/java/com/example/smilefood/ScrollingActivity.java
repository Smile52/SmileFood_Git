package com.example.smilefood;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.Bean.Food;
import com.Cache.BitmapCache;
import com.ImageUtils.MyBitmapUtils;
import com.android.volley.toolbox.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
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
 * 单个菜的详情activity
 */
public class ScrollingActivity extends AppCompatActivity {
    private Food food;
    private ImageView food_img;
    private TextView foodDetail_tv;
    private  Toolbar toolbar;
    private Bitmap bitmap;
    // 首先在您的Activity中添加如下成员变量
    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        setContentView(R.layout.activity_scrolling);
        initViews();
        initDatas();
        PushAgent mPushAgent = PushAgent.getInstance(ScrollingActivity.this);
        mPushAgent.enable();
        init();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //分享
                mController.getConfig().removePlatform(SHARE_MEDIA.RENREN,
                        SHARE_MEDIA.DOUBAN);
                //默认分享方式
                mController.openShare(ScrollingActivity.this, false);

            }
        });
    }
    private void initDatas() {
        Intent intent=getIntent();
        food= (Food) intent.getSerializableExtra("food");
        //System.out.println("菜名" + food.getFoodName());
        toolbar.setTitle(food.getFoodName());
        setSupportActionBar(toolbar);
        new MyBitmapUtils().display(food_img, food.getFoodUrl());

        ImageLoader loader=new ImageLoader(MyApplication.getQueue(),new BitmapCache());
        ImageLoader.ImageListener listener=ImageLoader.getImageListener
                (food_img,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
        loader.get(food.getFoodUrl(), listener);

        foodDetail_tv.setText(food.getFoodDetail());
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        food_img= (ImageView) findViewById(R.id.img_food);
        foodDetail_tv= (TextView) findViewById(R.id.fooddetail_tv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void init() {

        // 设置分享内容
        mController
                .setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能，http://www.umeng.com/social");
        // 设置分享图片, 参数2为图片的url地址
        mController.setShareMedia(new UMImage(this, food.getFoodUrl()));
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
        qzone.setShareContent(food.getFoodDetail());
        // 设置点击消息的跳转URL
        qzone.setTargetUrl("http://user.qzone.qq.com/272708698?ADUIN=1094365915&ADSESSION=1448209971&ADTAG=CLIENT.QQ.5449_FriendTip.0&ADPUBNO=26525&ptlang=2052");
        // 设置分享内容的标题
        qzone.setTitle(food.getFoodName());
        // 设置分享图片

        qzone.setShareImage(new UMImage(this,new MyBitmapUtils().display(food.getFoodUrl())));
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

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
