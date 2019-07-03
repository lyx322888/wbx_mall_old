package com.wbx.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.GoodsInfo2;
import com.wbx.mall.widget.AddWidget;

/**
 * 新版店铺详情
 */
public class DetailActivity extends BaseActivity implements AddWidget.OnAddClick {
    private static final String IS_VEGETABLE_MARKET = "IsVegetableMarket";
    private static final String STORE_ID = "StoreId";
    private boolean isVM;//是否菜市场
    private String mStoreId;//店铺id

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail;
    }

    public static void actionStart(Context context, boolean isVegetable, String storeId) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(IS_VEGETABLE_MARKET, isVegetable);
        intent.putExtra(STORE_ID, storeId);
        context.startActivity(intent);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        isVM = getIntent().getBooleanExtra(IS_VEGETABLE_MARKET, false);
        mStoreId = getIntent().getStringExtra(STORE_ID);
    }

    @Override
    public void fillData() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void onAddClick(View view, GoodsInfo2 goodsInfo) {

    }

    @Override
    public void onSubClick(GoodsInfo2 goodsInfo) {

    }

    @Override
    public void onClickSpecs(GoodsInfo2 goodsInfo) {

    }
}
