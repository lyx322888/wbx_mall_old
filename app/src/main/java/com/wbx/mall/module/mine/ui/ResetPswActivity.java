package com.wbx.mall.module.mine.ui;

import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.utils.MD5;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.LoadingDialog;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wushenghui on 2017/5/10.
 * 修改密码
 */

public class ResetPswActivity extends BaseActivity {
    @Bind(R.id.reset_psw_old_edit)
    EditText mOldPswEdit;
    @Bind(R.id.reset_psw_new_edit)
    EditText mNewPswEdit;
    @Bind(R.id.reset_psw_affirm_edit)
    EditText mAffirmEdit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_reset_psw;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void fillData() {

    }

    @Override
    public void setListener() {

    }

    @OnClick(R.id.reset_psw_submit_btn)
    public void submit() {
        String oldPsw = mOldPswEdit.getText().toString();
        String newPsw = mNewPswEdit.getText().toString();
        String newPswAgain = mAffirmEdit.getText().toString();
        if (!newPsw.equals(newPswAgain)) {
            showShortToast("两次输入的密码不同，请重新输入");
            return;
        }
        String pswMd5 = new MD5().EncoderByMd5(oldPsw);
        String newMd5 = new MD5().EncoderByMd5(newPsw);
        LoadingDialog.showDialogForLoading(mActivity, "信息提交中...", true);
        new MyHttp().doPost(Api.getDefault().resetPassword(SPUtils.getSharedStringData(mActivity, AppConfig.LOGIN_TOKEN), pswMd5, newMd5), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                showShortToast(result.getString("msg"));
                finish();
            }

            @Override
            public void onError(int code) {

            }
        });

    }
}
