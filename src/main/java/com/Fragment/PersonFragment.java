package com.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smilefood.R;

/**个人信息Fragment
 * Created by qq272 on 2015/11/10.
 */
public class PersonFragment extends android.support.v4.app.Fragment {
    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View person_view=inflater.inflate(R.layout.person_fragment,container,false);
        TextView title_text= (TextView) person_view.findViewById(R.id.id_title_text);
        webView= (WebView) person_view.findViewById(R.id.id_web);
        title_text.setText("小贴心");

        webView.loadUrl("http://www.haodou.com/");
        WebSettings webSettings = webView.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        //加载需要显示的网页

        //设置Web视图
        webView.setWebViewClient(new webViewClient ());
        return  person_view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    //Web视图
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
