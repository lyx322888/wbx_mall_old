package com.wbx.mall.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by wushenghui on 2017/11/16.
 * 选择提现/充值方式
 */

public class ChooseFinanceWayActivity extends BaseActivity {
    public static final int REQUEST_CHOOSE_PLATFORM = 1000;
    private boolean isWithdraw;
    private String data;
    @Bind(R.id.ali_layout)
    LinearLayout aliLayout;
    @Bind(R.id.weixin_layout)
    LinearLayout weChatLayout;
    @Bind(R.id.bank_layout)
    LinearLayout bankLayout;
    @Bind(R.id.ali_pay_hint_tv)
    TextView aliPayHintTv;
    @Bind(R.id.scroll_view)
    NestedScrollView scrollView;
    @Bind(R.id.wx_pay_hint_tv)
    TextView wxPayHintTv;
    @Bind(R.id.bank_pay_hint_tv)
    TextView bankPayHintTv;
    @Bind(R.id.ali_check_im)
    ImageView aliCheckIm;
    @Bind(R.id.wx_check_im)
    ImageView wxCheckIm;
    @Bind(R.id.bank_check_im)
    ImageView bankCheckIm;
    @Bind(R.id.bank_name_tv)
    TextView bankNameTv;
    private String platform;

    public static void rechargeStart(Activity context, String defaultPlatform) {
        Intent intent = new Intent(context, ChooseFinanceWayActivity.class);
        intent.putExtra("platform", defaultPlatform);
        context.startActivityForResult(intent, REQUEST_CHOOSE_PLATFORM);
    }

    public static void withdrawStart(Activity context, String platformInfo, String defaultPlatform) {
        Intent intent = new Intent(context, ChooseFinanceWayActivity.class);
        intent.putExtra("isWithdraw", true);
        intent.putExtra("data", platformInfo);
        intent.putExtra("platform", defaultPlatform);
        context.startActivityForResult(intent, REQUEST_CHOOSE_PLATFORM);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_choose_finance_way;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {

    }

    @Override
    public void fillData() {
        isWithdraw = getIntent().getBooleanExtra("isWithdraw", false);
        platform = getIntent().getStringExtra("platform");
        if (isWithdraw) {
            data = getIntent().getStringExtra("data");
            JSONObject jsonObject = JSONObject.parseObject(data);
            JSONObject userAliPay = jsonObject.getJSONObject("user_alipay");
            JSONObject userWeiXinPay = jsonObject.getJSONObject("user_weixinpay");
            JSONObject userBank = jsonObject.getJSONObject("user_bank");
            if (userAliPay != null) {
                aliLayout.setVisibility(View.VISIBLE);
                aliPayHintTv.setText(userAliPay.getString("depict"));
            }
            if (userWeiXinPay != null) {
                weChatLayout.setVisibility(View.VISIBLE);
                wxPayHintTv.setText(userWeiXinPay.getString("depict"));
            }
            if (userBank != null) {
                bankLayout.setVisibility(View.VISIBLE);
                bankPayHintTv.setText(userBank.getString("depict"));
                bankNameTv.setText(userBank.getString("bank_name"));
            }
            switch (platform) {
                case AppConfig.WidthdrawType.alipay:
                    aliCheckIm.setImageResource(R.drawable.selected);
                    wxCheckIm.setImageResource(R.drawable.uncheck);
                    bankCheckIm.setImageResource(R.drawable.uncheck);
                    break;
                case AppConfig.WidthdrawType.wxpay:
                    aliCheckIm.setImageResource(R.drawable.uncheck);
                    wxCheckIm.setImageResource(R.drawable.selected);
                    bankCheckIm.setImageResource(R.drawable.uncheck);
                    break;
                case AppConfig.WidthdrawType.bank:
                    aliCheckIm.setImageResource(R.drawable.uncheck);
                    wxCheckIm.setImageResource(R.drawable.uncheck);
                    bankCheckIm.setImageResource(R.drawable.selected);
                    break;
            }
        } else {
            aliLayout.setVisibility(View.VISIBLE);
            weChatLayout.setVisibility(View.VISIBLE);
            switch (platform) {
                case AppConfig.PayType.alipay:
                    aliCheckIm.setImageResource(R.drawable.selected);
                    wxCheckIm.setImageResource(R.drawable.uncheck);
                    bankCheckIm.setImageResource(R.drawable.uncheck);
                    break;
                case AppConfig.PayType.wxpay:
                    aliCheckIm.setImageResource(R.drawable.uncheck);
                    wxCheckIm.setImageResource(R.drawable.selected);
                    bankCheckIm.setImageResource(R.drawable.uncheck);
                    break;
            }
        }
    }

    @Override
    public void setListener() {
        aliLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isWithdraw) {
                    platform = AppConfig.WidthdrawType.alipay;
                } else {
                    platform = AppConfig.PayType.alipay;
                }
                Intent intent = new Intent();
                intent.putExtra("platform", platform);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        weChatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isWithdraw) {
                    platform = AppConfig.WidthdrawType.wxpay;
                } else {
                    platform = AppConfig.PayType.wxpay;
                }
                Intent intent = new Intent();
                intent.putExtra("platform", platform);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        bankLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                platform = AppConfig.WidthdrawType.bank;
                Intent intent = new Intent();
                intent.putExtra("platform", platform);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
