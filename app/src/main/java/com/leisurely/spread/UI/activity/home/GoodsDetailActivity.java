package com.leisurely.spread.UI.activity.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.money.AddressActivity;
import com.leisurely.spread.UI.activity.order.PlaceOrderActivity;
import com.leisurely.spread.UI.activity.setting.MainAct;
import com.leisurely.spread.UI.adapter.GoodDetailViewPagerAda;
import com.leisurely.spread.UI.view.ContentViewPager;
import com.leisurely.spread.config.SysConstants;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.Address;
import com.leisurely.spread.model.bean.GoodsDetail;
import com.leisurely.spread.util.ObservableScrollView;
import com.leisurely.spread.util.SharedPreferencesUtil;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.ToastUtil;
import com.leisurely.spread.util.WheelViewUtils;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;


public class GoodsDetailActivity extends BaseActivity implements ObservableScrollView.ScrollViewListener {

    private String id;
    private XclModel xclModel;
    private ImageView back;
    private ContentViewPager view_page;
    private TextView price;
    private TextView total_stock;
    private TextView surplus_stock;
    private TextView name, txtTitle;
    private TextView address;
    private TextView plus;
    private TextView add;
    private EditText count;
    private TextView good_detail;
    private TextView good_parameter;
    private WebView content;
    private RelativeLayout address_re, layoutActionBar;
    private TextView commit, txtLookMore, txtCount;
    private LinearLayout home_dot, layoutGoHome;
    private TextView address_alert;
    private ImageView dian;
    private ObservableScrollView scrollview;

    private GoodsDetail goodsDetail;
    private List<Address> addresses;
    private List<String> str;

    private GoodDetailViewPagerAda viewPageAda;
    private Dialog dialog;

    private List<ImageView> mDots;//小点
    private int type;

    private String addressId;

    private int oldHeight;
    private boolean isfirst = true;
    private int imageHeight;
    TextView txtActionTitle;

    /**
     * 初始化布局
     */
    @SuppressLint("JavascriptInterface")
    protected void initView() {
//        setContentView(R.layout.good_detail_activity);
        setContentView(R.layout.good_detail_toolbar_activity);
        txtActionTitle = findViewById(R.id.txtActionTitle);
        layoutActionBar = findViewById(R.id.layoutActionBar);
        txtCount = findViewById(R.id.txtGoodNum);
        back = findViewById(R.id.back);
        view_page = findViewById(R.id.view_page);
        price = findViewById(R.id.price);
        total_stock = findViewById(R.id.total_stock);
        surplus_stock = findViewById(R.id.surplus_stock);
        txtLookMore = findViewById(R.id.txtLookMore);
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        txtTitle = findViewById(R.id.txtTitle);
        plus = findViewById(R.id.plus);
        add = findViewById(R.id.add);
        count = findViewById(R.id.count);
        good_detail = findViewById(R.id.good_detail);
        good_parameter = findViewById(R.id.good_parameter);
        content = findViewById(R.id.content);
        commit = findViewById(R.id.commit);
        address_re = findViewById(R.id.address_re);
        home_dot = findViewById(R.id.home_dot);
        address_alert = findViewById(R.id.address_alert);
        dian = findViewById(R.id.dian);
        scrollview = findViewById(R.id.scrollview);
        layoutGoHome = findViewById(R.id.layoutGoHome);


        WebSettings wSet = content.getSettings();

        content.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        content.setVerticalScrollBarEnabled(false);
        content.setVerticalScrollbarOverlay(false);
        content.setHorizontalScrollBarEnabled(false);
        content.setHorizontalScrollbarOverlay(false);
        wSet.setJavaScriptEnabled(true);
        wSet.setUseWideViewPort(true);//关键点
        wSet.setLoadWithOverviewMode(true);
        wSet.setUseWideViewPort(true);
        wSet.setBuiltInZoomControls(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            wSet.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            wSet.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }

        /**
         * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
         * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
         */
//        wSet.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);


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

            public void onPageFinished(WebView view, String url) {
                int w = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                int h = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                //重新测量
                content.measure(w, h);

//                content.loadUrl("[removed](function(){"+"document.getElementsByTagName('body')[0].style.height = window.innerHeight+'px';"+"})()");

                content.loadUrl("javascript:App.resize(document.body.getBoundingClientRect().height)");

                super.onPageFinished(view, url);

            }

        });

        content.addJavascriptInterface(this, "App");


        viewPageAda = new GoodDetailViewPagerAda(new ArrayList<String>(), this);

        view_page.setAdapter(viewPageAda);
        view_page.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                // 获取顶部图片高度后，设置滚动监听
                ViewTreeObserver vto = view_page.getViewTreeObserver();
                vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        view_page.getViewTreeObserver().removeGlobalOnLayoutListener(
                                this);
                        imageHeight = view_page.getHeight();

                        scrollview.setScrollViewListener(GoodsDetailActivity.this);
                    }
                });


                Log.e("tag", "onPageSelected===================");
                for (int i = 0; i < goodsDetail.getLbtImg().size(); i++) {
                    // 将所有的圆点设置为为选中时候的图片
                    mDots.get(i).setImageResource(R.drawable.dot_select);
                }
                // 将被选中的图片中的圆点设置为被选中的时候的图片
                mDots.get(position % goodsDetail.getLbtImg().size()).setImageResource(R.drawable.dot_normal);
                int index = position % goodsDetail.getLbtImg().size();
                txtCount.setText((index + 1) + "/" + goodsDetail.getLbtImg().size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        count.setFocusable(true);
//        count.setFocusableInTouchMode(true);
//        count.requestFocus();
//
//        count.clearFocus();//失去焦点
        count.setSelection(1);
//        count.requestFocus();//获取焦点
//        TextView tv = (TextView) findViewById(R.id.txt);


// //       tv.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动
//        tv.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以打开网页
//        tv.setText(Html.fromHtml(html, imgGetter, null));
    }


    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }


    @Override
    public void onResume() {
        super.onResume();
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
                if (!isfirst) {
                    scrollview.scrollTo(0, 1000);
                }
//                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) content.getLayoutParams();
//                layoutParams.height = (int) (height * getResources().getDisplayMetrics().density);
//                content.setLayoutParams(layoutParams);
            }

        });

    }

    @Override
    protected void initListener() {
        layoutGoHome.setOnClickListener(this);
        txtLookMore.setOnClickListener(this);
        back.setOnClickListener(this);
        plus.setOnClickListener(this);
        add.setOnClickListener(this);
        address_re.setOnClickListener(this);
        good_detail.setOnClickListener(this);
        good_parameter.setOnClickListener(this);
        commit.setOnClickListener(this);

        count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                String countStr = count.getText().toString();
//                int s;
//                if (StringUtil.isNullOrEmpty(countStr)) {
//                    s = 1;
//                } else {
//                    s = Integer.parseInt(countStr);
//                }
//
//                if (s > Integer.parseInt(goodsDetail.getSurplusStock()) && !"0".equals(goodsDetail.getSurplusStock())) {
//                    count.setText(goodsDetail.getSurplusStock());
//                    count.setSelection(count.getText().toString().length());
//                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        id = getIntent().getStringExtra("id");
        xclModel.queryGoodDetailedById(id);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutGoHome:
                startActivity(new Intent(this, MainAct.class));
                finish();
                break;
            case R.id.txtLookMore:
                finish();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.plus:
                changeCount(-1);
                break;
            case R.id.add:
                changeCount(1);
                break;
            case R.id.count:

                break;
            case R.id.address_re:
                if (type == 0) {
                    startActivityForResult(new Intent(this, RegisterOrLoginActivity.class), 1111);
                } else if (type == 1) {
                    startActivityForResult(new Intent(this, AddressActivity.class), 2222);
                } else if (type == 2) {
                    int index = 0;
                    int position = 0;
//                    str = new ArrayList<>();
                    if (addresses != null && addresses.size() > 0) {
                        for (Address a : addresses) {
//                        str.add(a.getAddress());
                            if (address.getText().toString().contains(a.getAddress())) {
                                position = index;
                            }
                            index++;
                        }
                    }
                    WheelViewUtils.alertBottomWheelOption(this, str, position, new WheelViewUtils.OnWheelViewClick() {
                        @Override
                        public void onClick(View view, final int index) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    address.setText(str.get(index));
                                }
                            });
                        }
                    });
                }
                break;
            case R.id.good_detail:
                good_detail.setTextColor(getResources().getColor(R.color.color_EC3537));
                good_parameter.setTextColor(getResources().getColor(R.color.color_8D8D8D));
                isfirst = false;
                initCount(goodsDetail.getGoodDetail());
                break;
            case R.id.good_parameter:
                good_parameter.setTextColor(getResources().getColor(R.color.color_EC3537));
                good_detail.setTextColor(getResources().getColor(R.color.color_8D8D8D));
                isfirst = false;
                initCount(goodsDetail.getGoodParameter());
                break;
            case R.id.commit:
                if (SharedPreferencesUtil.isLogin()) {
                    if (goodsDetail != null) {
                        startActivityForResult(new Intent(this, PlaceOrderActivity.class).putExtra(PlaceOrderActivity.GOODS_DETAIL, goodsDetail), 234);
                    }
//                    String s = count.getText().toString();
//                    if (StringUtil.isNullOrEmpty(count.getText().toString())) {
//                        ToastUtil.showToast("请输入购买数量");
//                    } else {
//                        int c = Integer.parseInt(s);
//                        if (c < 1) {
//                            ToastUtil.showToast("至少购买一个");
//                        } else {
//                            addressId = "";
//                            if (addresses != null && addresses.size() > 0) {
//                                for (Address a : addresses) {
//                                    if (a.getAddress().equals(address.getText().toString())) {
//                                        addressId = a.getId();
//                                        break;
//                                    }
//
//                                }
//                            }
//                            if (StringUtil.isNullOrEmpty(addressId)) {
//                                ToastUtil.showToast("请选择地址");
//                                break;
//                            }
//
//                            showAlert();
//
//                        }
//                    }
                } else {
                    startActivityForResult(new Intent(this, RegisterOrLoginActivity.class), 1111);
                }
                break;
            default:
                break;
        }
    }

    public void showAlert() {
        View layout = LayoutInflater.from(this).inflate(R.layout.good_detail_alert, null);
        dialog = new Dialog(this, R.style.officeDialog);
        dialog.setCancelable(true);
        dialog.setContentView(layout);

        final EditText edit = layout.findViewById(R.id.edit);

        TextView forget_pwd = layout.findViewById(R.id.forget_pwd);
        forget_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GoodsDetailActivity.this, ForgetPayPwdActivity.class)
                        .putExtra("type", 1).putExtra("title", "忘记资金密码"));
            }
        });

        TextView text1 = layout.findViewById(R.id.awardpage_button1);
        text1.setText("取消");
        text1.setTextColor(this.getResources().getColor(R.color.light));
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView text2 = layout.findViewById(R.id.awardpage_button2);
        text2.setText("购买");
        text2.setTextColor(this.getResources().getColor(R.color.white));
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtil.isNullOrEmpty(edit.getText().toString())) {
                    ToastUtil.showToast("请输入资金密码");
                } else {
                    xclModel.goodPurchase(id, addressId, count.getText().toString(), goodsDetail.getPrice(), edit.getText().toString());
                    dialog.dismiss();
                }


            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface arg0) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public void initCount(String s) {
//        String url = "<html><head><title></title></head><body>%s</body></html>";

//        content.setLayoutParams(new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, 0));
//        content.loadDataWithBaseURL(null, String.format(url,s), "text/html", "UTF-8", null);
//        content.removeAllViews();
        content.getLayoutParams();
//        String html1 = s.replace("<img",
//                "<img style=\"width:90%;display:block;margin:10dp;max-height:%fpx;vertical-align:middle;\"");
//        html1 = "<html><body  width=100% style=\"font-size:25px;margin:0dp; line-height:10px;line-width:100%;text-indent:0em;word-wrap:break-word; font-family:Arial\">"
//                + html1 + "</body></html>";
        if (StringUtil.isNullOrEmpty(s)) {
            s = "";
        }
        content.loadDataWithBaseURL(null, getHtmlData(s), "text/html", "UTF-8", null);

//        content.loadData(getHtmlData(s), "text/html", "UTF-8");
    }

    public void changeCount(int i) {
        int result = StringUtil.isNotNull(count.getText().toString()) ? Integer.parseInt(count.getText().toString()) : 0;
        result += i;
        if (result < 1) {
            result = 1;
        }
        count.setText(String.valueOf(result));
        count.setSelection(count.getText().toString().length());
    }

    public void queryGoodDetailedByIdSuccess(JSONObject json) {

        if (json.getBoolean("success")) {
            goodsDetail = JSONObject.parseObject(JSONObject.toJSONString(json.getJSONObject("data").getJSONObject("goodDto")), GoodsDetail.class);
            addresses = JSONArray.parseArray(JSONArray.toJSONString(json.getJSONObject("data").getJSONArray("addresses")), Address.class);
            init();
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }

    public void init() {
        txtActionTitle.setText(goodsDetail.getName());
        name.setText(goodsDetail.getName());
        price.setText(goodsDetail.getPrice());
//        total_stock.setText(goodsDetail.getTotalStock());
        surplus_stock.setText(goodsDetail.getSurplusStock());
        txtTitle.setText(goodsDetail.getName());
//        content.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以打开网页
        initCount(goodsDetail.getGoodDetail());
//        initCount(goodsDetail.getGoodParameter());
//        initCount(goodsDetail.getGoodDetail());
        str = new ArrayList<>();
        if (!SharedPreferencesUtil.readBoolean(SysConstants.ISLOGIN, false)) {
            address.setVisibility(View.GONE);
            dian.setVisibility(View.GONE);
            address_alert.setVisibility(View.VISIBLE);
            address_alert.setText("未登录，请去登录");
            type = 0;
        } else {
            if (addresses != null && addresses.size() > 0) {
                address.setVisibility(View.VISIBLE);
                dian.setVisibility(View.VISIBLE);
                address_alert.setVisibility(View.GONE);
                address.setText(addresses.get(0).getAddress());
                for (Address a : addresses) {
                    str.add(a.getAddress());
                }
                type = 2;
            } else {
                type = 1;
                address.setVisibility(View.GONE);
                dian.setVisibility(View.GONE);
                address_alert.setVisibility(View.VISIBLE);
                address_alert.setText("暂无地址，请去新增");
            }
        }
        initDots();

    }

    private void initDots() {
        home_dot.removeAllViews();
        mDots = new ArrayList<ImageView>();// 底部圆点集合的初始化
        if (goodsDetail.getLbtImg() != null && goodsDetail.getLbtImg().size() > 0) {
            if (goodsDetail.getLbtImg().size() > 1) {
                for (int i = 0; i < goodsDetail.getLbtImg().size(); i++) {// 根据界面数量动态添加圆点
                    ImageView imageView = new ImageView(this);
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(40, 40));// 设置ImageView的宽度和高度
                    imageView.setPadding(12, 12, 12, 12);// 设置圆点的Padding，与周围的距离
                    imageView.setImageResource(R.drawable.dot_select);// 设置图片
                    mDots.add(imageView);// 将该图片添加到圆点集合中
                    home_dot.addView(imageView);// 将图片添加到LinearLayout中
                }
                mDots.get(0).setImageResource(R.drawable.dot_normal);
                viewPageAda.setUrllist(goodsDetail.getLbtImg());
                viewPageAda.notifyDataSetChanged();
                view_page.setCurrentItem(goodsDetail.getLbtImg().size() * 100);
            }
            viewPageAda.setUrllist(goodsDetail.getLbtImg());
            viewPageAda.notifyDataSetChanged();

        }

    }

    public void goodPurchaseSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            showSuccess();
            int k = Integer.parseInt(goodsDetail.getSurplusStock()) - Integer.parseInt(count.getText().toString());
            goodsDetail.setSurplusStock(String.valueOf(k));
            count.setText("1");
            surplus_stock.setText(goodsDetail.getSurplusStock() + " /");
            count.setSelection(1);
//            Intent it = new Intent();
//            setResult(RESULT_OK, it);
//            finish();
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }

    public void showSuccess() {
        View layout = LayoutInflater.from(this).inflate(R.layout.view_alert, null);
        final Dialog dialog1 = new Dialog(this, R.style.officeDialog);
        dialog1.setCancelable(true);
        dialog1.setContentView(layout);

        TextView alert_title = layout.findViewById(R.id.alert_title);
        alert_title.setText("购买成功");
        TextView alert_content = layout.findViewById(R.id.alert_content);
        alert_content.setText("确认已收到货物!");
        alert_content.setVisibility(View.GONE);
        TextView text1 = layout.findViewById(R.id.awardpage_button1);
        text1.setText("取消");
        text1.setVisibility(View.GONE);
        text1.setTextColor(this.getResources().getColor(R.color.light));
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        TextView text2 = layout.findViewById(R.id.awardpage_button2);
        text2.setText("确认");
        text2.setTextColor(this.getResources().getColor(R.color.color_1490D8));
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        dialog1.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface arg0) {
                dialog1.dismiss();
            }
        });
        dialog1.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1111) {//登录
                xclModel.queryGoodDetailedById(id);
            } else if (requestCode == 2222) {//地址
                xclModel.queryGoodDetailedById(id);
            } else if (requestCode == 234) {
//                购买成功
                if (data != null) {
                    int subPage = data.getIntExtra(PlaceOrderActivity.GOODS_BUY_AFTER, 0);
                    if (subPage == 1) {
                        //跳订单页
                        startActivity(new Intent(this, OrderActivity.class));
                    } else {
                        startActivity(new Intent(this, MainAct.class));
                        finish();
                    }
                }
                finish();
            }
        }
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        // Log.i("TAG", "y--->" + y + "    height-->" + height);
        if (y <= 0) {
            layoutActionBar.setBackgroundColor(Color.argb((int) 0, 235, 235, 235));//AGB由相关工具获得，或者美工提供
            txtActionTitle.setTextColor(Color.argb((int) 0, 45, 45, 45));
        } else if (y > 0 && y <= imageHeight) {
            float scale = (float) y / imageHeight;
            float alpha = (255 * scale);
            // 只是layout背景透明(仿知乎滑动效果)
            layoutActionBar.setBackgroundColor(Color.argb((int) alpha, 235, 235, 235));
            txtActionTitle.setTextColor(Color.argb((int) alpha, 45, 45, 45));
        } else {
            layoutActionBar.setBackgroundColor(Color.argb((int) 255, 235, 235, 235));
            txtActionTitle.setTextColor(Color.argb((int) 255, 45, 45, 45));
        }

    }
}
