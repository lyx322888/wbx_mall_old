package com.wbx.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.IntegralGoodsDetailBean;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.utils.GlideUtils;
import com.wbx.mall.utils.UilImageGetter;
import com.wbx.mall.widget.LoadingDialog;
import com.wbx.mall.widget.MyScrollview;

import butterknife.Bind;
import butterknife.OnClick;

public class IntegralGoodsDetailActivity extends BaseActivity {
    @Bind(R.id.iv_goods)
    ImageView ivGoods;
    @Bind(R.id.detail_goods_name_tv)
    TextView goodsName;
    @Bind(R.id.tv_integral)
    TextView tvIntegral;
    @Bind(R.id.tv_exchange_num)
    TextView tvExchangeNum;
    @Bind(R.id.goods_introduce_tv)
    TextView goodsIntroduceTv;
    @Bind(R.id.product_detail_sv)
    MyScrollview mScrollView;
    @Bind(R.id.pro_detail_title_view)
    LinearLayout mTitleView;
    @Bind(R.id.tv_total_integral)
    TextView tvTotalIntegral;
    private IntegralGoodsDetailBean data;
    private int exchangeNum;
    private int currentIntegral;

    public static void actionStart(Context context, int goodsId, int currentIntegral) {
        Intent intent = new Intent(context, IntegralGoodsDetailActivity.class);
        intent.putExtra("goodsId", goodsId);
        intent.putExtra("currentIntegral", currentIntegral);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_integral_goods_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void fillData() {
        int goodsId = getIntent().getIntExtra("goodsId", 0);
        currentIntegral = getIntent().getIntExtra("currentIntegral", 0);
        LoadingDialog.showDialogForLoading(mActivity, "加载中...", true);
        new MyHttp().doPost(Api.getDefault().getIntegralGoodsDetail(LoginUtil.getLoginToken(), goodsId), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                data = result.getObject("data", IntegralGoodsDetailBean.class);
                updateUI();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void updateUI() {
        GlideUtils.showBigPic(this, ivGoods, data.getFace_pic());
        goodsName.setText(data.getTitle());
        tvIntegral.setText(data.getIntegral() + "积分");
        goodsIntroduceTv.setText(Html.fromHtml(data.getDetails(), new UilImageGetter(mActivity, goodsIntroduceTv), null));
    }

    @Override
    public void setListener() {
        mScrollView.setScrollViewListener(new MyScrollview.ScrollViewListener() {
            @Override
            public void onScrollChanged(MyScrollview scrollView, int x, int scrollY, int oldx, int oldy) {
                if (scrollY <= mTitleView.getMeasuredHeight()) {
                    float persent = scrollY * 1.5f / (mTitleView.getTop() + mTitleView.getMeasuredHeight());
                    int alpha = (int) (255 * persent);
                    int color = Color.argb(alpha, 6, 193, 174);
                    if (alpha <= 255) {
                        mTitleView.setBackgroundColor(color);
                    }
                } else {
                    int color = Color.argb(255, 6, 193, 174);
                    mTitleView.setBackgroundColor(color);
                }
            }
        });
    }

    @OnClick({R.id.add_product_im, R.id.sub_product_im, R.id.now_buy_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_product_im:
                addExchangeNum();
                break;
            case R.id.sub_product_im:
                reduceExchangeNum();
                break;
            case R.id.now_buy_btn:
                exchangeNow();
                break;
        }

    }

    private void reduceExchangeNum() {
        if (exchangeNum == 0) {
            return;
        }
        exchangeNum--;
        tvTotalIntegral.setText("合计：" + exchangeNum * data.getIntegral());
        tvExchangeNum.setText(String.valueOf(exchangeNum));
    }

    private void addExchangeNum() {
        exchangeNum++;
        tvTotalIntegral.setText("合计：" + exchangeNum * data.getIntegral());
        tvExchangeNum.setText(String.valueOf(exchangeNum));
    }

    private void exchangeNow() {
        if (exchangeNum * data.getIntegral() > currentIntegral) {
            showShortToast("抱歉，您当前的积分不够兑换此商品哦。");
            return;
        }
        if (exchangeNum == 0) {
            showShortToast("请选择兑换数量");
            return;
        }
        SubmitIntegralExchangeActivity.actionStart(this, data, exchangeNum);
    }
}
