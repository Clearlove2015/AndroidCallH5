package com.zc.androidcallh5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JavaAndJsCallActivity extends AppCompatActivity {

    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.btn_login)
    Button btnLogin;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_and_js_call);
        ButterKnife.bind(this);
        initWebView();//初始化webview
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        String name = etName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
            Toast.makeText(JavaAndJsCallActivity.this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            if(name.equals("zc_china")&&password.equals("123456")){
                Toast.makeText(JavaAndJsCallActivity.this, "登录", Toast.LENGTH_SHORT).show();
                login(name);
            }
        }
    }

    //登录
    private void login(String name) {
        //Java调js
        webView.loadUrl("javascript:javaCallJs(" + "'" + name + "'" + ")");
        setContentView(webView);
    }

    //调用webview
    private void initWebView() {
        webView = new WebView(this);
        WebSettings webSettings = webView.getSettings();

        //设置支持js
        webSettings.setJavaScriptEnabled(true);

        //不调用系统浏览器
        webView.setWebViewClient(new WebViewClient());

        //添加JavascriptInterface
        //以后js通过Android字段调用AndroidAndJsInterface类中的任意方法
        webView.addJavascriptInterface(new AndroidAndJsInterface(),"Android");

        //加载网页
        //webView.loadUrl("http://www.baidu.com");
        //加载本地html页面
        webView.loadUrl("file:///android_asset/JavaAndJavaScriptCall.html");

    }

    //js调java
    class AndroidAndJsInterface{
        /**
         * 将会被js调用
         */
        @JavascriptInterface
        public void showToast(){
            Toast.makeText(JavaAndJsCallActivity.this,"我是Android代码，我被js调用了",Toast.LENGTH_SHORT).show();
        }
    }

}
