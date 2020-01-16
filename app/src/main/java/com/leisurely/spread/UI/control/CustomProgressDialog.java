package com.leisurely.spread.UI.control;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.leisurely.spread.R;

public class CustomProgressDialog extends Dialog {
    private final int CHANGE_TITLE_WHAT = 1;
    private final int CHNAGE_TITLE_DELAYMILLIS = 300;
    private final int MAX_SUFFIX_NUMBER = 3;
    private final char SUFFIX = '.';
    private TextView tv_point;
    private GifView img_gif;
    private View mViewDialog;

    private Handler handler = new Handler() {
        private int num = 0;
        public void handleMessage(android.os.Message msg) {
            if (msg.what == CHANGE_TITLE_WHAT) {
                StringBuilder builder = new StringBuilder();
                if (num >= MAX_SUFFIX_NUMBER) {
                    num = 0;
                }
                num++;
                for (int i = 0; i < num; i++) {
                    builder.append(SUFFIX);
                }
                tv_point.setText(builder.toString());
                if (isShowing()) {
                    handler.sendEmptyMessageDelayed(CHANGE_TITLE_WHAT, CHNAGE_TITLE_DELAYMILLIS);
                } else {
                    num = 0;
                }
            }
        }
    };

    private void init() {
        mViewDialog = View.inflate(getContext(), R.layout.customprogressdialog, null);
        setContentView(mViewDialog);
        img_gif = (GifView) findViewById(R.id.img_gif);
        img_gif.setGifResource(R.drawable.loadinggif);
        tv_point = (TextView) findViewById(R.id.tv_point);
        getWindow().setWindowAnimations(R.style.CustomProgressDialog);
    }

    //创建对象
    public CustomProgressDialog(Context context) {
        super(context, R.style.Dialog_bocop);
        init();
    }


    /**
     * 提示内容
     * @param strMessage
     */
    public void setMessage(String strMessage) {
        TextView tvMsg = (TextView) mViewDialog.findViewById(R.id.id_tv_loadingmsg);
        if (tvMsg != null) {
            tvMsg.setText(strMessage);
        }
        if (null == strMessage) {
            tvMsg.setVisibility(View.GONE);
            tv_point.setVisibility(View.GONE);
        } else {
            tvMsg.setVisibility(View.VISIBLE);
            tv_point.setVisibility(View.VISIBLE);
            handler.sendEmptyMessage(CHANGE_TITLE_WHAT);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public void dismissAndDestory(){
        dismiss();
        handler.removeCallbacksAndMessages(null);
        mViewDialog = null;
        handler = null;
    }
}
