package com.wbx.mall.module.mine.ui;

import android.widget.ImageView;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.utils.GlideUtils;

import butterknife.Bind;

/**
 * 开票资料
 */

public class TicketInfoActivity extends BaseActivity {
    @Bind(R.id.iv_bill)
    ImageView mIvBill;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ticket_info;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        GlideUtils.showMediumPic(mContext, mIvBill, "http://www.wbx365.com/static/default/wap/image/xiaochengxu/sdfsaaa51894546783x.png");
    }

    @Override
    public void fillData() {

    }

    @Override
    public void setListener() {

    }
}
