package com.leisurely.spread.UI.activity.setting;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.ToastUtil;

/**
 * Created by jbuy on 2018/9/3.
 */

public class FeedbackActivity extends BaseActivity {

    private XclModel xclModel;

    private TextView commit;
    private EditText content;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_feetback);
        setTitleName("意见反馈");
        commit = findViewById(R.id.commit);
        content = findViewById(R.id.content);
    }

    @Override
    protected void initListener() {
        commit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        xclModel = new XclModel(this);
    }

    public void  feedbackSuccess(){
        ToastUtil.showToast("提交成功");
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.commit:
                String result = validate();
                if (StringUtil.isNullOrEmpty(result)) {
                    xclModel.feedback(content.getText().toString());
                } else {
                    ToastUtil.showToast(result);
                }
                break;
                default:
                    break;
        }
    }

    private String validate() {
        if (StringUtil.isNullOrEmpty(content.getText().toString())) {
            return "提交内容不可为空";
        }
        return "";
    }


}
