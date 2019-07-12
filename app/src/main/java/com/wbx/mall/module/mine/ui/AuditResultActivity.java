package com.wbx.mall.module.mine.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 审核中
 */
public class AuditResultActivity extends BaseActivity {
    @Bind(R.id.title_name_tv)
    TextView titleNameTv;
    @Bind(R.id.ll_auditing)
    LinearLayout llAuditing;
    @Bind(R.id.ll_audit_fail)
    LinearLayout llAuditFail;

    public static void   actionStart(Context context, boolean isAuditing) {
        Intent intent = new Intent(context, AuditResultActivity.class);
        intent.putExtra("isAuditing", isAuditing);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_audit_result;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        boolean isAuditing = getIntent().getBooleanExtra("isAuditing", true);
        if (isAuditing) {
            llAuditing.setVisibility(View.VISIBLE);
            titleNameTv.setText("审核中");
        } else {
            llAuditFail.setVisibility(View.VISIBLE);
            titleNameTv.setText("审核失败");
        }
    }

    @Override
    public void fillData() {

    }

    @Override
    public void setListener() {

    }

    @OnClick(R.id.tv_modify)
    public void onViewClicked() {
        ToBeAgentActivity.actionStart(this);
        finish();
    }
}
