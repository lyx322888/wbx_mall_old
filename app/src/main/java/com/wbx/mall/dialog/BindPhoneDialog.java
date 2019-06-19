package com.wbx.mall.dialog;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/13 0013.
 */

public class BindPhoneDialog extends DialogFragment {
    private OnResultListener listener;
    private Context mContext;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.btn_send_code)
    Button btnSendCode;
    @Bind(R.id.et_password)
    EditText etPassword;

    public static BindPhoneDialog newInstance(Context context) {
        BindPhoneDialog bindPhoneDialog = new BindPhoneDialog();
        bindPhoneDialog.mContext = context;
        return bindPhoneDialog;
    }

    public void setResultListener(OnResultListener resultList) {
        this.listener = resultList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.dialog_bind_phone, container);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.btn_send_code, R.id.tv_cancel, R.id.tv_ensure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send_code:
                sendCode();
                break;
            case R.id.tv_cancel:
                timer.cancel();
                dismiss();
                if (listener != null) {
                    listener.onCancel();
                }
                break;
            case R.id.tv_ensure:
                ensure();
                break;
        }
    }

    private void ensure() {
        String phone = etPhone.getText().toString();
        String code = etCode.getText().toString();
        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(code) || TextUtils.isEmpty(password)) {
            Toast.makeText(mContext, "请输入完整信息", Toast.LENGTH_SHORT).show();
            return;
        }
        if (phone.length() != 11) {
            Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (code.length() != 6) {
            Toast.makeText(mContext, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(mContext, "密码不得少于6位", Toast.LENGTH_SHORT).show();
            return;
        }
        if (listener != null) {
            listener.onSuccess(phone, code, password);
        }
        timer.cancel();
        dismiss();
    }

    public interface OnResultListener {
        void onSuccess(String phone, String code, String password);

        void onCancel();
    }

    private void sendCode() {
        String phone = etPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(mContext, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (phone.length() != 11) {
            Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        new MyHttp().doPost(Api.getDefault().sendSMSCode(phone), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                timer.start();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            btnSendCode.setEnabled(false);
            btnSendCode.setText((millisUntilFinished / 1000) + "秒后可重发");
        }

        @Override
        public void onFinish() {
            btnSendCode.setEnabled(true);
            btnSendCode.setText("获取验证码");
        }
    };
}
