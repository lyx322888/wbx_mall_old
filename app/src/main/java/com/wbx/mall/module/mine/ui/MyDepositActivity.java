package com.wbx.mall.module.mine.ui;

import android.view.View;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.module.mine.ui.FinanceOperationActivity;

import butterknife.Bind;

/**
 * 我的押金
 */
public class MyDepositActivity extends BaseActivity {
    @Bind(R.id.deposit_tv)
    TextView depositTv;
    private int deposit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_deposit;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void fillData() {
        deposit = getIntent().getIntExtra("deposit", 0);
        depositTv.setText(String.format("¥ %.2f", deposit / 100.00));

    }

    @Override
    public void setListener() {
        findViewById(R.id.cash_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FinanceOperationActivity.withdrawStart(mContext, deposit, AppConfig.CashType.deposit);
            }
        });
    }
}
