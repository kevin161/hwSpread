package com.leisurely.spread.UI.activity.money;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.home.RealNameActivity;
import com.leisurely.spread.UI.adapter.BindCardAdapter;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.Bank;
import com.leisurely.spread.util.SharedPreferencesUtil;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/12/11.
 */

public class BindCardActivity extends BaseActivity {

    private XclModel xclModel;


    private List<Bank> list;

    private ListView listView;
    private ImageView add;
    private LinearLayout realname_li;
    private BindCardAdapter adapter;
    private Dialog backDialog;
    private int deleteId;
    private LinearLayout back;
    private Dialog deleteDialog;


    @Override
    protected void initView() {
        setContentView(R.layout.bindcard_activity);

        listView = findViewById(R.id.lv_listview);
        add = findViewById(R.id.add);
        realname_li = findViewById(R.id.realname_li);
        back = findViewById(R.id.back);

        list = new ArrayList<>();
        adapter = new BindCardAdapter(this, list);
        listView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        xclModel = new XclModel(this);
        xclModel.queryCards();

    }


    @Override
    protected void initListener() {
        add.setOnClickListener(this);
        realname_li.setOnClickListener(this);
        back.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                if ("0".equals(SharedPreferencesUtil.readString("isRiskAuth", "0"))) {
                    startActivityForResult(new Intent(this, RealNameActivity.class), 1111);
                } else {
                    showDialog();
                }
                break;
            case R.id.realname_li:
                startActivityForResult(new Intent(this, RealNameActivity.class), 1111);
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    public void deleteBank(String id, int position, Dialog deleteDialog) {
        this.deleteDialog = deleteDialog;
        deleteId = position;
        xclModel.deleteBankCard(id);
    }

    private void showDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.add_bankcard_dialog, null);
        backDialog = new Dialog(this, R.style.officeDialog);
        backDialog.setCancelable(true);
        backDialog.setContentView(view);
        ImageView close = view.findViewById(R.id.close);
        final TextView name = view.findViewById(R.id.name);
        final EditText bank_name = view.findViewById(R.id.bank_name);
        final EditText bank_card = view.findViewById(R.id.bank_card);
        final EditText sub_bank_name = view.findViewById(R.id.sub_bank_name);
        setEditTextInhibitInputSpeChat(bank_name);
        setEditTextInhibitInputSpeChat(sub_bank_name);
        bank_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){

                }else {
                    bank_name.setSelection(0);
                }
            }
        });
        sub_bank_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){

                }else {
                    sub_bank_name.setSelection(0);
                }
            }
        });
        name.setText(SharedPreferencesUtil.readString("realname", ""));
        TextView commit = view.findViewById(R.id.commit);
//        backDialog.setCancelable(false);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backDialog.cancel();
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result4 = validate(bank_name, bank_card, sub_bank_name);
                if (StringUtil.isNullOrEmpty(result4)) {
                    xclModel.bindCard(name.getText().toString(), bank_name.getText().toString(),
                            bank_card.getText().toString(), sub_bank_name.getText().toString());

                } else {
                    ToastUtil.showToast(result4);
                }
            }
        });

        backDialog.show();
    }

    public void showAlert(final Bank bank, final int position) {
        View layout = LayoutInflater.from(this).inflate(R.layout.view_alert, null);
        deleteDialog = new Dialog(this, R.style.officeDialog);
        deleteDialog.setCancelable(true);
        deleteDialog.setContentView(layout);

        TextView alert_title = layout.findViewById(R.id.alert_title);
        alert_title.setText("确认删除");
        TextView alert_content = layout.findViewById(R.id.alert_content);
        alert_content.setText("是否确认删除此银行卡?");

        TextView text1 = layout.findViewById(R.id.awardpage_button1);
        text1.setText("取消");
        text1.setTextColor(this.getResources().getColor(R.color.light));
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
            }
        });
        TextView text2 = layout.findViewById(R.id.awardpage_button2);
        text2.setText("确认删除");
        text2.setTextColor(this.getResources().getColor(R.color.color_1490D8));
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBank(bank.getId(), position, deleteDialog);
                deleteDialog.dismiss();
            }
        });
        deleteDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface arg0) {
                deleteDialog.dismiss();
            }
        });
        deleteDialog.show();
    }

    public void bindCardSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            ToastUtil.showToast("提交成功");
            xclModel.queryCards();
            backDialog.cancel();
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }

    public void deleteBankSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            deleteDialog.cancel();
            ToastUtil.showToast("删除成功");
            list.remove(deleteId);
            adapter.changeList(list);

        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }

    public void queryCardsSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            list = JSONArray.parseArray(JSONArray.toJSONString(json.getJSONArray("data")), Bank.class);
            adapter.changeList(list);
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }

    @NonNull
    private String validate(EditText bank_name, EditText bank_card, EditText sub_bank_name) {

        if (StringUtil.isNullOrEmpty(bank_card.getText().toString())) {
            return "请输入银行卡号";
        }
        if (StringUtil.isNullOrEmpty(bank_name.getText().toString())) {
            return "请输入银行名称";
        }
        if (StringUtil.isNullOrEmpty(sub_bank_name.getText().toString())) {
            return "请输入开户支行名称";
        }

        return "";
    }

    /**
     * 禁止EditText表情
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpeChat(EditText editText) {

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[a-zA-Z0-9!@#%*()_+=`~\\u4E00-\\u9FA5]+";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (!matcher.find()) return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    @Override
    public void onResume() {
        super.onResume();
        if ("1".equals(SharedPreferencesUtil.readString("isRiskAuth", "0"))) {
            realname_li.setVisibility(View.GONE);
        }
    }
}
