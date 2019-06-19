package com.wbx.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.ShopInfo2;
import com.wbx.mall.utils.GlideUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPreview;

public class BusinessCertificationActivity extends BaseActivity {
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_number)
    TextView tvNumber;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.iv_audit)
    ImageView ivAudit;
    @Bind(R.id.iv_goods)
    ImageView ivGoods;
    private ShopInfo2 shopInfo;
    private ArrayList<String> lstCertification = new ArrayList<>();

    public static void actionStart(Context context, ShopInfo2 shopInfo) {
        Intent intent = new Intent(context, BusinessCertificationActivity.class);
        intent.putExtra("shopInfo", shopInfo);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_business_certification;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void fillData() {
        shopInfo = (ShopInfo2) getIntent().getSerializableExtra("shopInfo");
        tvName.setText("单位名称：" + shopInfo.getAudit_name());
        tvAddress.setText("经营地址：" + shopInfo.getAudit_addr());
        tvNumber.setText("许可证号：" + shopInfo.getZhucehao());
        tvDate.setText("有效期：" + shopInfo.getAudit_end_date());
        if (!TextUtils.isEmpty(shopInfo.getAudit_photo())) {
            lstCertification.add(shopInfo.getAudit_photo());
            GlideUtils.showMediumPic(this, ivAudit, shopInfo.getAudit_photo());
        }
        if (!TextUtils.isEmpty(shopInfo.getHygiene_photo())) {
            lstCertification.add(shopInfo.getHygiene_photo());
            GlideUtils.showMediumPic(this, ivGoods, shopInfo.getHygiene_photo());
        }
    }

    @Override
    public void setListener() {

    }

    @OnClick({R.id.iv_audit, R.id.iv_goods})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_audit:
                if (lstCertification.size() > 0) {
                    PhotoPreview.builder().setPhotos(lstCertification).setCurrentItem(0).setShowDeleteButton(false).start(this);
                }
                break;
            case R.id.iv_goods:
                if (lstCertification.size() > 1) {
                    PhotoPreview.builder().setPhotos(lstCertification).setCurrentItem(1).setShowDeleteButton(false).start(this);
                }
                break;
        }
    }
}
