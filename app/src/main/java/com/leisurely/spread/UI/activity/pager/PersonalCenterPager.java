package com.leisurely.spread.UI.activity.pager;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.home.MainActivity;
import com.leisurely.spread.UI.activity.home.ModifyPasswordActivity;
import com.leisurely.spread.UI.activity.home.ModifyPayPasswordActivity;
import com.leisurely.spread.UI.activity.home.PersonDetailActivity;
import com.leisurely.spread.UI.activity.money.QuantificationDetailActivity;
import com.leisurely.spread.UI.activity.setting.CommunityActivity;
import com.leisurely.spread.UI.activity.setting.FeedbackActivity;
import com.leisurely.spread.UI.activity.setting.LoginActivity;
import com.leisurely.spread.UI.activity.setting.ServiceActivity;
import com.leisurely.spread.UI.view.ObservableScrollView;
import com.leisurely.spread.config.SysConstants;
import com.leisurely.spread.framework.tabManager.BasePager;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.User;
import com.leisurely.spread.util.SharedPreferencesUtil;

public class PersonalCenterPager extends BasePager implements View.OnClickListener {

    private XclModel xclModel;
    private TextView data;
    private TextView phone;
    private LinearLayout shequ;
    private LinearLayout mingxi;
    private LinearLayout edit_pwd;
    private LinearLayout edit_paypwd;
    private LinearLayout fankui;
    private TextView login_out;
    private TextView kefu;

    private RelativeLayout title_re;
    private ObservableScrollView person_scroll;
    private User user;


    public PersonalCenterPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {

        rootView = mInflater.inflate(R.layout.activity_personal_center, null);
        data = rootView.findViewById(R.id.data);
        phone = rootView.findViewById(R.id.phone);
        shequ = rootView.findViewById(R.id.shequ);
        mingxi = rootView.findViewById(R.id.mingxi);
        edit_pwd = rootView.findViewById(R.id.edit_pwd);
        edit_paypwd = rootView.findViewById(R.id.edit_paypwd);
        fankui = rootView.findViewById(R.id.fankui);
        login_out = rootView.findViewById(R.id.login_out);
        title_re = rootView.findViewById(R.id.title_re);
        person_scroll = rootView.findViewById(R.id.person_scroll);
        kefu = rootView.findViewById(R.id.kefu);
        return rootView;
    }

    @Override
    public void initData() {
        if (xclModel == null) {
            xclModel = new XclModel((MainActivity) mContext, this);
        }
        phone.setText(SharedPreferencesUtil.readString(SysConstants.TELLPHONE, ""));
        xclModel.getUserDetail();
        initListener();
    }

    private void initListener() {
        data.setOnClickListener(this);
        shequ.setOnClickListener(this);
        mingxi.setOnClickListener(this);
        edit_pwd.setOnClickListener(this);
        edit_paypwd.setOnClickListener(this);
        fankui.setOnClickListener(this);
        login_out.setOnClickListener(this);
        kefu.setOnClickListener(this);

        person_scroll.setOnScollChangedListener(new ObservableScrollView.OnScollChangedListener() {
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                int scrollY = person_scroll.getScrollY();
                int height = title_re.getHeight();
                if (scrollY >= height) {
                    title_re.setVisibility(View.VISIBLE);
                } else {
                    title_re.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = ((MainActivity)mContext).getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(mContext.getResources().getColor(R.color.white));
//            //底部导航栏
//            //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
//        }
        phone.setText(SharedPreferencesUtil.readString(SysConstants.TELLPHONE, ""));
        if (xclModel == null) {
            xclModel = new XclModel((MainActivity) mContext, this);
        }
        xclModel.getUserDetail();
    }

    public void getUserInfoSuccess(User user) {
        this.user = user;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_out:
                mContext.startActivity(new Intent(mContext, LoginActivity.class));
                SharedPreferencesUtil.saveBoolean(SysConstants.ISLOGIN, false);
                break;
            case R.id.data:
                if (user != null) {
                    mContext.startActivity(new Intent(mContext, PersonDetailActivity.class)
                            .putExtra("trueid", user.getTrueid())
                            .putExtra("name", user.getTruename()));
                }
                break;
            case R.id.shequ:
                mContext.startActivity(new Intent(mContext, CommunityActivity.class));
                break;
            case R.id.mingxi:
                mContext.startActivity(new Intent(mContext, QuantificationDetailActivity.class));
                break;
            case R.id.edit_pwd:
                mContext.startActivity(new Intent(mContext, ModifyPasswordActivity.class));
                break;
            case R.id.edit_paypwd:
                mContext.startActivity(new Intent(mContext, ModifyPayPasswordActivity.class));
                break;
            case R.id.fankui:
                mContext.startActivity(new Intent(mContext, FeedbackActivity.class));
                break;
            case R.id.kefu:
                mContext.startActivity(new Intent(mContext, ServiceActivity.class)
                        .putExtra("qq", user.getServiceqq())
                        .putExtra("wechat", user.getServicewx()));
                break;
            default:
                break;
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            default:
                break;
        }
    }

}
