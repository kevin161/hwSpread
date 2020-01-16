package com.leisurely.spread.UI.activity.money;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.Address;
import com.leisurely.spread.model.bean.Area;
import com.leisurely.spread.model.bean.City;
import com.leisurely.spread.model.bean.Provinces;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.TextUtil;
import com.leisurely.spread.util.ToastUtil;
import com.leisurely.spread.widget.MySpinner;

import org.angmarch.views.NiceSpinner;

import java.util.LinkedList;
import java.util.List;

/**
 * @version V3.0
 * @FileName: com.leisurely.spread.UI.activity.money.AddUpdateAddressActivity.java
 * @author: GYZ
 * asia161@qq.com
 * @date: 2020-01-09 19:59
 */
public class AddUpdateAddressActivity extends BaseActivity {

    private XclModel xclModel;
    private Address address;

    private LinearLayout back;
    private TextView activityTitle, txtSave, txtProvinceHint, txtCityHint, txtAreaHint;
    private EditText txtName, txtPhone, edtDetailAddress;
    //    private NiceSpinner spinnerArea;
    private MySpinner spinnerProvince, spinnerCity, spinnerArea;
    private Address updateAddress;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_add_address);
        updateAddress = (Address) getIntent().getSerializableExtra("address");
        activityTitle = findViewById(R.id.activityTitle);
        txtSave = findViewById(R.id.txtSave);
        txtProvinceHint = findViewById(R.id.txtProvinceHint);
        txtCityHint = findViewById(R.id.txtCityHint);
        txtAreaHint = findViewById(R.id.txtAreaHint);
        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        back = findViewById(R.id.back);
        edtDetailAddress = findViewById(R.id.edtDetailAddress);
        spinnerProvince = findViewById(R.id.spinnerProvince);
        spinnerCity = findViewById(R.id.spinnerCity);
        spinnerArea = findViewById(R.id.spinnerArea);

    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        xclModel.getProvinces();

        if (updateAddress != null) {
            initUpdateView();
        }

    }

    private void initUpdateView() {
        txtName.setText(updateAddress.getUserName());
        txtPhone.setText(updateAddress.getPhone());
        edtDetailAddress.setText(updateAddress.getAddress());
        activityTitle.setText("编辑收货地址");
    }

    @Override
    protected void initListener() {
        txtSave.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtSave:
                String result = validate();
                if (StringUtil.isNullOrEmpty(result)) {
                    xclModel.saveOrUpdateAddress(address.getId(), address.getUserName(), address.getPhone(), address.getProvince()
                            , address.getCity(), address.getArea(), address.getAddress());

                } else {
                    ToastUtil.showToast(result);
                }
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private String validate() {
        if (StringUtil.isNullOrEmpty(txtName.getText().toString())) {
            return "请输入联系人";
        }
        if (StringUtil.isNullOrEmpty(txtPhone.getText().toString())) {
            return "请输入联系方式";
        }
        if (TextUtil.isMobileNumber(txtPhone.getText().toString())) {
            return "请输入正确的手机号";
        }
        if (selectedProvince == null) {
            return "请选择所在省";
        }
        String proStr = selectedProvince.getProvince();
        if (StringUtil.isNullOrEmpty(proStr) || "省".equals(proStr)) {
            return "请选择所在省";
        }
        if (selectedCity == null) {
            return "请选择所在市";
        }
        String cityStr = selectedCity.getCity();
        if (StringUtil.isNullOrEmpty(cityStr) || "区".equals(cityStr)) {
            return "请选择所在市";
        }
        if (StringUtil.isNullOrEmpty(edtDetailAddress.getText().toString())) {
            return "请输入详细地址";
        }
        address = new Address();
        address.setUserName(txtName.getText().toString());
        address.setAddress(edtDetailAddress.getText().toString());
        address.setPhone(txtPhone.getText().toString());
        address.setProvince(proStr);
        address.setCity(cityStr);
        if (selectedArea != null) {
            address.setArea(selectedArea.getArea());
        }
        if (updateAddress != null) {
            address.setId(updateAddress.getId());
        }
        return "";
    }


    public void saveOrUpdateAddressSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            if (StringUtil.isNullOrEmpty(address.getId())) {
                ToastUtil.makeTextAndShow("添加成功");
            } else {
                ToastUtil.makeTextAndShow("修改成功");
            }
            address = null;
            finish();
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }


    List<Provinces> provinces;
    Provinces selectedProvince;
    List<City> citys;
    City selectedCity;
    List<Area> areas;
    Area selectedArea;


    boolean isProvinceInitial = true;
    boolean isCityInitial = true;
    boolean isAreaInitial = true;

    public void getProvincesSuccess(JSONObject json) {
        if (json.getBoolean("success")) {

            provinces = JSONArray.parseArray(JSONArray.toJSONString(json.getJSONArray("data")), Provinces.class);
            if (provinces.size() > 0) {
                String[] provinceStrs = new String[provinces.size()];
                for (int i = 0; i < provinces.size(); i++) {
                    provinceStrs[i] = provinces.get(i).getProvince();

                }
                // 建立Adapter并且绑定数据源
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, provinceStrs);
                adapter.setDropDownViewResource(R.layout.dropdown_stytle);

//绑定 Adapter到控件
                spinnerProvince.setAdapter(adapter);
                spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        isCityInitial = true;
                        isAreaInitial = true;
                        selectedCity = null;
                        selectedArea = null;
                        txtCityHint.setVisibility(View.VISIBLE);
                        txtAreaHint.setVisibility(View.VISIBLE);

                        if (isProvinceInitial) {
                            view.setVisibility(View.INVISIBLE);
                            txtProvinceHint.setVisibility(View.VISIBLE);
                            isProvinceInitial = false;
                            return;
                        } else {
                            selectedProvince = provinces.get(pos);
                            txtProvinceHint.setVisibility(View.GONE);
                            view.setVisibility(View.VISIBLE);
                        xclModel.getCities(selectedProvince.getProvinceid());
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


            }
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }


    public void getCitiesSuccess(JSONObject json) {
        if (json.getBoolean("success")) {

            citys = JSONArray.parseArray(JSONArray.toJSONString(json.getJSONArray("data")), City.class);
            if (citys.size() > 0) {
                String[] cityStrs = new String[citys.size()];
                for (int i = 0; i < citys.size(); i++) {
                    cityStrs[i] = citys.get(i).getCity();
                }
                // 建立Adapter并且绑定数据源
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, cityStrs);
                adapter.setDropDownViewResource(R.layout.dropdown_stytle);

//绑定 Adapter到控件
                spinnerCity.setAdapter(adapter);
                spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        isAreaInitial = true;
                        selectedArea = null;
                        txtAreaHint.setVisibility(View.VISIBLE);
                        if (isCityInitial) {
                            view.setVisibility(View.INVISIBLE);
                            txtCityHint.setVisibility(View.VISIBLE);
                            isCityInitial = false;
                            return;
                        } else {
                            selectedCity = citys.get(pos);
                            txtCityHint.setVisibility(View.GONE);
                            view.setVisibility(View.VISIBLE);
                        xclModel.getAreas(selectedCity.getCityid());
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

            } else {
                ToastUtil.showToast(json.getString("msg"));
            }
        }
    }

    public void getAreasSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            areas = JSONArray.parseArray(JSONArray.toJSONString(json.getJSONArray("data")), Area.class);

            if (areas.size() > 0) {
                String[] areaStrs = new String[areas.size()];
                for (int i = 0; i < areas.size(); i++) {
                    areaStrs[i] = areas.get(i).getArea();
                }
                // 建立Adapter并且绑定数据源
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, areaStrs);
                adapter.setDropDownViewResource(R.layout.dropdown_stytle);


//绑定 Adapter到控件
                spinnerArea.setAdapter(adapter);
                spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        if (isAreaInitial) {
                            view.setVisibility(View.INVISIBLE);
                            txtAreaHint.setVisibility(View.VISIBLE);
                            isAreaInitial = false;
                            return;
                        } else {
                            selectedArea = areas.get(pos);
                            txtAreaHint.setVisibility(View.GONE);
                            view.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


            }


//            final LinkedList listArray = new LinkedList<String>();
//            if (areas.size() == 0) {
//                selectedArea = null;
//                listArray.add("区");
//                spinnerArea.attachDataSource(listArray);
//                return;
//            }
//            for (Area area : areas) {
//                listArray.add(area.getArea());
//            }
//            spinnerArea.attachDataSource(listArray);
//            spinnerArea.addOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    if (position == areas.size()) {
////                    选择了我们自己添加的“市”选项
//                        spinnerArea.setSelectedIndex(listArray.size() - 1);
//                        spinnerArea.setTextColor(getResources().getColor(R.color.color_CACACA));
//                        selectedArea = null;
//                        return;
//                    }
//                    selectedArea = areas.get(position);
//                    if (!"区".equals(selectedArea.getArea())) {
//                        spinnerArea.setTextColor(getResources().getColor(R.color.black));
//                    } else {
//                        spinnerArea.setTextColor(getResources().getColor(R.color.color_CACACA));
//                    }
//
//
//                }
//            });

        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }
}
