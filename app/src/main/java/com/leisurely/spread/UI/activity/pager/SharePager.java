package com.leisurely.spread.UI.activity.pager;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.home.MainActivity;
import com.leisurely.spread.UI.activity.money.ShareWinActivity;
import com.leisurely.spread.framework.tabManager.BasePager;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.util.FileUtil;
import com.leisurely.spread.util.ImageUtil;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.TextUtil;

public class SharePager extends BasePager implements View.OnClickListener {

    private XclModel xclModel;

    private ImageView qrcode;
    private TextView invitation;
    private TextView save_pic;
    private TextView copy_url;
    private TextView fenxiangjiangli;

    private String url;
    private Bitmap bitmap;

    public SharePager(Context context) {
        super(context);
    }

    @Override
    public View initView() {

        rootView = mInflater.inflate(R.layout.activity_share, null);
        qrcode = rootView.findViewById(R.id.qrcode);
        invitation = rootView.findViewById(R.id.invitation);
        save_pic = rootView.findViewById(R.id.save_pic);
        copy_url = rootView.findViewById(R.id.copy_url);
        fenxiangjiangli = rootView.findViewById(R.id.fenxiangjiangli);
        return rootView;
    }

    @Override
    public void initData() {
        if (xclModel == null) {
            xclModel = new XclModel((MainActivity) mContext, this);
        }
        xclModel.shareUrl();
        initListener();
    }

    private void initListener() {
        save_pic.setOnClickListener(this);
        copy_url.setOnClickListener(this);
        fenxiangjiangli.setOnClickListener(this);
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

        if (xclModel == null) {
            xclModel = new XclModel((MainActivity) mContext, this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.copy_url:
                if (StringUtil.isNotNull(url)) {
                    TextUtil.onClickCopy(url, (MainActivity) mContext);
                }
                break;
            case R.id.save_pic:
                if (bitmap != null) {
                    FileUtil.saveImageToGallery(mContext, bitmap);
                }
                break;
            case R.id.fenxiangjiangli:
                mContext.startActivity(new Intent(mContext, ShareWinActivity.class));
            default:
                break;
        }

    }

    public void shareUrlSuccess(final String url, String invitation) {
        this.url = url;
        ((MainActivity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bitmap = ImageUtil.createQRcodeImage(url, qrcode);
            }
        });
        this.invitation.setText("我的邀请码: " + invitation);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            default:
                break;
        }
    }

}
