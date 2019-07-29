package com.wbx.mall.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.utils.ToastUitl;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 订单备注
 */
public class OrderRemarkActivity extends BaseActivity {
    @Bind(R.id.et_remark)
    EditText etRemark;
    @Bind(R.id.tv_count)
    TextView tvCount;

    public static void actionStart(Activity context, boolean isPhysical, String orderId, String remark) {
        Intent intent = new Intent(context, OrderRemarkActivity.class);
        intent.putExtra("isPhysical", isPhysical);
        intent.putExtra("orderId", orderId);
        intent.putExtra("remark", remark);
        context.startActivityForResult(intent, SubmitOrderActivity.REQUEST_ADD_REMARK);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_remark;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        etRemark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvCount.setText(s.length() + "/50个字");
            }
        });
    }

    @Override
    public void fillData() {
        etRemark.setText(getIntent().getStringExtra("remark"));
        etRemark.setSelection(getIntent().getStringExtra("remark").length());
    }

    @Override
    public void setListener() {

    }

    @OnClick(R.id.tv_sure)
    public void onViewClicked() {
        final String remark = etRemark.getText().toString();
        if (TextUtils.isEmpty(remark)) {
            ToastUitl.showShort("请输入备注信息");
            return;
        }
        new MyHttp().doPost(getIntent().getBooleanExtra("isPhysical", false) ? Api.getDefault().addRemark(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), getIntent().getStringExtra("orderId"), remark) : Api.getDefault().addMallRemark(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), getIntent().getStringExtra("orderId"), remark), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                Intent intent = new Intent();
                intent.putExtra("remark", remark);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onError(int code) {

            }
        });
    }
}
