package com.wbx.mall.module.mine.ui;

import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseApplication;
import com.wbx.mall.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by wushenghui on 2017/7/19.
 * 关于我们
 */

public class AboutUsActivity extends BaseActivity {
    @Bind(R.id.version_tv)
    TextView versionTv;
    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    public void initPresenter() {

    }
    @Override
    public void initView() {

    }

    @Override
    public void fillData() {
        versionTv.setText(String.format("当前版本 : %s", BaseApplication.getInstance().getVersion()));
    }

    @Override
    public void setListener() {

    }
}
