package com.wbx.mall.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wbx.mall.R;
import com.wbx.mall.base.AppConfig;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "com.wbx.mall.wxapi.WXPayEntryActivity";
    private IWXAPI api;
    @Bind(R.id.error_state_tv)
    TextView errorStateTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, AppConfig.WX_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {
                //成功
                EventBus.getDefault().post("PaySuccess");
            } else {

            }
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle("支付结果");
//			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
//			builder.show();
        }
        finish();
    }

    public void close(View view) {
        finish();
    }
}