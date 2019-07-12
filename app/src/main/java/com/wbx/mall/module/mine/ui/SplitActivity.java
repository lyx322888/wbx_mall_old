package com.wbx.mall.module.mine.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.utils.GlideUtils;
import com.wbx.mall.utils.ToastUitl;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我要分包
 */
public class SplitActivity extends BaseActivity {
    @Bind(R.id.iv_head)
    ImageView ivHead;
    @Bind(R.id.tv_user_name)
    TextView tvUserName;
    @Bind(R.id.tv_user_type)
    TextView tvUserType;
    @Bind(R.id.tv_num_480)
    TextView tvNum480;
    @Bind(R.id.tv_num_2580)
    TextView tvNum2580;
    @Bind(R.id.edit_tip_1)
    EditText etTip1;
    @Bind(R.id.edit_tip_3)
    EditText etTip3;
    @Bind(R.id.tv_type_480)
    TextView tvType480;
    @Bind(R.id.tv_type_2580)
    TextView tvType2580;
    private String mType = "1";

    @Override
    public int getLayoutId() {
        return R.layout.activity_split;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        tvType480.setSelected(true);
    }

    @Override
    public void fillData() {
        new MyHttp().doPost(Api.getDefault().getSoftSubPackage(LoginUtil.getLoginToken()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                JSONObject data = result.getJSONObject("data");
                tvUserName.setText(data.getString("nickname"));
                GlideUtils.showMediumPic(SplitActivity.this, ivHead, data.getString("face"));
                tvNum480.setText(data.getString("480_software_num") + "套");
                tvNum2580.setText(data.getString("2080_software_num") + "套");
                switch (data.getInteger("rank")) {
                    case 5:
                        tvUserType.setText("创业领袖");
                        break;
                    case 6:
                        tvUserType.setText("战略合作伙伴");
                        break;
                    case 7:
                        tvUserType.setText("城市代理");
                        break;
                    default:
                        tvUserType.setText("创业合伙人");
                }
            }

            @Override
            public void onError(int code) {
            }
        });
    }

    @Override
    public void setListener() {
    }

    @OnClick({R.id.btn_submit, R.id.tv_type_480, R.id.tv_type_2580, R.id.tv_record})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                submit();
                break;
            case R.id.tv_type_480:
                mType = "1";
                tvType480.setSelected(true);
                tvType2580.setSelected(false);
                break;
            case R.id.tv_type_2580:
                mType = "2";
                tvType480.setSelected(false);
                tvType2580.setSelected(true);
                break;
            case R.id.tv_record:
                ToastUitl.showShort("分包记录");
                break;
        }
    }

    private void upSoftSubPackage(String strTip1, String strType, String strTip3) {
        new MyHttp().doPost(Api.getDefault().upSoftSubPackage(LoginUtil.getLoginToken(), strTip1, strType, strTip3), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                String msg = result.getString("msg");
                if ("成功".equals(msg)) {
                    finish();
                }
                ToastUitl.showShort("成功");
            }

            @Override
            public void onError(int code) {
            }
        });
    }

    private void submit() {
        String strTip1 = etTip1.getText().toString().trim();
        String strTip3 = etTip3.getText().toString().trim();
        if (TextUtils.isEmpty(strTip1)) {
            ToastUitl.showShort("请输入分包帐号");
            return;
        }
        if (TextUtils.isEmpty(strTip3)) {
            ToastUitl.showShort("请输入分包数量");
            return;
        }
        upSoftSubPackage(strTip1, mType, strTip3);
    }
}
