package com.wbx.mall.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.LocationInfo;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wushenghui on 2017/8/10.
 * 申请入驻
 */

public class ApplyJoinActivity extends BaseActivity {
    @Bind(R.id.title_name_tv)
    TextView titleNameTv;
    @Bind(R.id.choose_city_tv)
    TextView cityTv;
    @Bind(R.id.name_edit)
    EditText nameEdit;
    @Bind(R.id.phone_edit)
    EditText phoneEdit;
    private boolean isApply;
    private HashMap<String, Object> mParams = new HashMap<>();
    private LocationInfo selectCity;

    @Override
    public int getLayoutId() {
        return R.layout.activity_apply_join;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void fillData() {
        isApply = getIntent().getBooleanExtra("isApply", false);
        if (!isApply) titleNameTv.setText("城市代理");
    }

    @Override
    public void setListener() {

    }

    private boolean canApply(String nameStr, String phoneStr) {
        if (null == selectCity) {
            showShortToast("请选择城市");
            return false;
        }
        if (TextUtils.isEmpty(nameStr)) {
            showShortToast("请填写您的姓名");
            return false;
        }
        if (TextUtils.isEmpty(nameStr)) {
            showShortToast("请填写您的联系方式");
            return false;
        }

        return true;
    }

    @OnClick({R.id.apply_layout, R.id.submit_apply_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.apply_layout:
                Intent intent = new Intent(mContext, ChooseCityActivity.class);
                intent.putExtra("isAllCity", true);
                startActivityForResult(intent, 1004);
                break;
            case R.id.submit_apply_btn:
                String cityStr = cityTv.getText().toString();
                String nameStr = nameEdit.getText().toString();
                String phoneStr = phoneEdit.getText().toString();
                if (!canApply(nameStr, phoneStr)) return;
                mParams.put("city_id", selectCity.getCity_id());
                mParams.put("apply_type", isApply ? 2 : 1);
                mParams.put("mobile", phoneStr);
                mParams.put("name", nameStr);
                new MyHttp().doPost(Api.getDefault().applyJoin(mParams), new HttpListener() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        showShortToast(result.getString("msg"));
                        finish();
                    }

                    @Override
                    public void onError(int code) {

                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null == data) return;
        selectCity = (LocationInfo) data.getSerializableExtra("selectCity");
        cityTv.setText(selectCity.getName());
    }
}
