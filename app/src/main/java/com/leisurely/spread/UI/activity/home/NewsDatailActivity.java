package com.leisurely.spread.UI.activity.home;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.News;
import com.leisurely.spread.util.DateUtil;
import com.leisurely.spread.util.ToastUtil;


public class NewsDatailActivity extends BaseActivity {

    private XclModel xclModel;

    private LinearLayout back;

    private TextView title;
    private TextView time, txtTitle;
    private WebView content;
    private ProgressBar progressBar;

    private String id;

    /**
     * 初始化布局
     */
    @SuppressLint("JavascriptInterface")
    protected void initView() {
        setContentView(R.layout.newsdetail_activity);

        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        txtTitle = findViewById(R.id.txtTitle);
        time = findViewById(R.id.time);
        content = findViewById(R.id.content);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setMax(100);

        WebSettings wSet = content.getSettings();
        wSet.setDefaultTextEncodingName("UTF-8");
        content.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        content.setVerticalScrollBarEnabled(false);
        content.setVerticalScrollbarOverlay(false);
        content.setHorizontalScrollBarEnabled(false);
        content.setHorizontalScrollbarOverlay(false);
        wSet.setJavaScriptEnabled(true);
        wSet.setUseWideViewPort(true);//关键点
        wSet.setLoadWithOverviewMode(true);
        wSet.setBuiltInZoomControls(true);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            wSet.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
//        } else {
//            wSet.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
//        }

        /**
         * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
         * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
         */
        wSet.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);


        int screenDensity = getResources().getDisplayMetrics().densityDpi;

        WebSettings.ZoomDensity zoomDensity = WebSettings.ZoomDensity.MEDIUM;

        switch (screenDensity) {

            case DisplayMetrics.DENSITY_LOW:

                zoomDensity = WebSettings.ZoomDensity.CLOSE;

                break;

            case DisplayMetrics.DENSITY_MEDIUM:

                zoomDensity = WebSettings.ZoomDensity.MEDIUM;

                break;

            case DisplayMetrics.DENSITY_HIGH:

                zoomDensity = WebSettings.ZoomDensity.FAR;

                break;

        }

        wSet.setDefaultZoom(zoomDensity);
        content.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(ProgressBar.VISIBLE);
                progressBar.setProgress(0);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                int w = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                int h = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                //重新测量
                content.measure(w, h);

//                content.loadUrl("[removed](function(){"+"document.getElementsByTagName('body')[0].style.height = window.innerHeight+'px';"+"})()");

                content.loadUrl("javascript:App.resize(document.body.getBoundingClientRect().height)");
                progressBar.setProgress(100);
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                super.onPageFinished(view, url);

            }

        });

        content.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                } else {
                    Log.e("MyTag", "加载进度" + newProgress);
                }
                progressBar.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }
        });

        content.addJavascriptInterface(this, "App");
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    @JavascriptInterface
    public void resize(final float height) {

        runOnUiThread(new Runnable() {

            @Override

            public void run() {

                //Toast.makeText(getActivity(), height + "", Toast.LENGTH_LONG).show();
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)content.getLayoutParams();
//                layoutParams.width = getResources().getDisplayMetrics().widthPixels;
//                layoutParams.height = (int) (height * getResources().getDisplayMetrics().density);
//                content.setLayoutParams(layoutParams);
                content.setLayoutParams(new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, (int) (height * getResources().getDisplayMetrics().density)));

//                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) content.getLayoutParams();
//                layoutParams.height = (int) (height * getResources().getDisplayMetrics().density);
//                content.setLayoutParams(layoutParams);
            }

        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);

    }

    String topic;

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        id = getIntent().getStringExtra("id");
        topic = getIntent().getStringExtra("topic");
        if ("PRODUCT".equals(topic)) {
//            修改为产品介绍页的样式
            time.setVisibility(View.GONE);
            txtTitle.setText("商品介绍详情");
        }
        xclModel.queryNewsById(id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }


    public void queryNewsByIdSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            News news = JSONObject.parseObject(JSONObject.toJSONString(json.getJSONObject("data")), News.class);
            if (news != null) {
                title.setText(news.getSubject());
                time.setText(DateUtil.getdata(news.getPublicationDate()));
//                content.setText(news.getContent());
                content.setLayoutParams(new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, 0));
                content.getLayoutParams();
                content.loadDataWithBaseURL(null, getHtmlData(news.getContent()), "text/html", "UTF-8", null);
            }
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
