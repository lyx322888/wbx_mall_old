package com.wbx.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.wbx.mall.R;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.WinRecordBean;
import com.wbx.mall.dialog.ActivityRuleDialogFragment;
import com.wbx.mall.utils.GlideUtils;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.CircleImageView;

import butterknife.Bind;
import butterknife.OnClick;

public class WinRecordActivity extends BaseActivity {
    @Bind(R.id.iv_user)
    CircleImageView ivUser;
    @Bind(R.id.tv_user_name)
    TextView tvUserName;
    @Bind(R.id.ll_container)
    LinearLayout llContainer;
    private WinRecordBean winRecordBean;
    Gson gson = new Gson();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, WinRecordActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_win_record;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        tvUserName.setText(userInfo.getNickname());
        GlideUtils.showMediumPic(this, ivUser, userInfo.getFace());
    }

    @Override
    public void fillData() {
        new MyHttp().doPost(Api.getDefault().getRedPacketList(
                SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN)), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                winRecordBean = gson.fromJson(result.getString("data"), WinRecordBean.class);
                llContainer.removeAllViews();
                for (int i = 0; i < winRecordBean.getList().size(); i++) {
                    WinRecordBean.ListBean data = winRecordBean.getList().get(i);
                    View item = LayoutInflater.from(WinRecordActivity.this).inflate(R.layout.layout_item_win_record, null);
                    TextView tvContent = item.findViewById(R.id.tv_content);
                    TextView tvMoney = item.findViewById(R.id.tv_money);
                    String state = "";
                    if (data.getIs_use() == 1) {
                        state = "（已使用）";
                    } else if (data.getCan_use() == 0) {
                        state = "（待确认）";
                    }
                    tvContent.setText("下单获得红包奖励" + state);
                    tvMoney.setText("¥" + String.format("%.2f", (float) data.getMoney() / 100));
                    llContainer.addView(item);
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

    @OnClick(R.id.tv_rule)
    public void onViewClicked() {
        if (winRecordBean != null) {
            ActivityRuleDialogFragment.newInstance(winRecordBean.getRule()).show(getSupportFragmentManager(), "");
        }
    }
}
