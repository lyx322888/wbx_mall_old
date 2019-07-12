package com.wbx.mall.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hedgehog.ratingbar.RatingBar;
import com.wbx.mall.R;
import com.wbx.mall.activity.LoginActivity;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.bean.ShopInfo2;
import com.wbx.mall.utils.GlideUtils;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.utils.ScaleAnimatorUtils;
import com.wbx.mall.utils.ShareUtils;
import com.wbx.mall.utils.ToastUitl;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Zero
 * @date 2018/7/30
 */
public class ShopInfoContainer extends RelativeLayout {
    @Bind(R.id.iv_shop_bg)
    ImageView ivShopBg;
    @Bind(R.id.iv_shop)
    ImageView ivShop;
    @Bind(R.id.cover)
    View cover;
    @Bind(R.id.tv_shop_name)
    public TextView tvShopName;
    @Bind(R.id.rb_score)
    RatingBar rbScore;
    @Bind(R.id.tv_sell_num)
    TextView tvSellNum;
    @Bind(R.id.tv_announce)
    TextView tvAnnounce;
    @Bind(R.id.iv_collect)
    public ImageView ivCollect;
    @Bind(R.id.tv_fare)
    TextView tvFare;//满多少减配送费
    private ShopInfo2 shopInfo;
    private Activity activity;

    public ShopInfoContainer(Context context) {
        super(context);
    }

    public ShopInfoContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.layout_shop_info, this);
        ButterKnife.bind(this);
    }

    public void updateUI(Activity activity, ShopInfo2 shopInfo) {
        this.activity = activity;
        this.shopInfo = shopInfo;
        GlideUtils.showBlurBigPic(getContext(), ivShopBg, shopInfo.getPhoto());
        GlideUtils.showRoundMediumPic(getContext(), ivShop, shopInfo.getPhoto());
        ivCollect.setImageResource(shopInfo.getIs_favorites() == 0 ? R.drawable.icon_collection_un : R.drawable.already);
        tvShopName.setText(shopInfo.getShop_name());
        rbScore.setStar(shopInfo.getScore());
        tvSellNum.setText(String.format("销量：%d", shopInfo.getSold_num()));
        if (TextUtils.isEmpty(shopInfo.getNotice())) {
            ((View) tvAnnounce.getParent()).setVisibility(View.GONE);
        } else {
            tvAnnounce.setText(shopInfo.getNotice());
            tvAnnounce.setSelected(true);
        }
        if (shopInfo.getIs_full_minus_shipping_fee() == 1) {
            tvFare.setVisibility(VISIBLE);
            tvFare.setText("满" + shopInfo.getFull_minus_shipping_fee() / 100 + "免配送费");
        }
    }

    public void setWgAlpha(float alpha) {
        ivShop.setAlpha(alpha);
        ((View) rbScore.getParent()).setAlpha(alpha);
        ((View) tvAnnounce.getParent()).setAlpha(alpha);
        cover.setAlpha(alpha);
        tvFare.setAlpha(alpha);
    }

    @OnClick({R.id.iv_back, R.id.iv_share, R.id.iv_collect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                back();
                break;
            case R.id.iv_share:
                share();
                break;
            case R.id.iv_collect:
                collect();
                break;
        }
    }

    private void back() {
        if (activity != null) {
            activity.finish();
        }
    }

    /**
     * 分享店铺
     */
    private void share() {
        if (activity == null || shopInfo == null) {
            return;
        }
        ShareUtils.getInstance().share(activity, "我在微百姓购物，方便、实惠！推荐你也一起来使用吧！", "", shopInfo.getPhoto(), shopInfo.getGrade_id() == AppConfig.StoreType.VEGETABLE_MARKET ? String.format("http://www.wbx365.com/wap/ele/shop/shop_id/%s.html", String.valueOf(shopInfo.getShop_id())) : String.format("http://www.wbx365.com/wap/shop/goods/shop_id/%s.html", String.valueOf(shopInfo.getShop_id())));
    }

    /**
     * 收藏店铺
     */
    private void collect() {
        if (activity == null || shopInfo == null) {
            return;
        }
        if (!SPUtils.getSharedBooleanData(getContext(), AppConfig.LOGIN_STATE)) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.putExtra("isMainTo", true);
            getContext().startActivity(intent);
            return;
        }
        if (shopInfo.getIs_favorites() == 1) {
            ToastUitl.showShort("已经关注了");
            return;
        }
        LoadingDialog.showDialogForLoading(activity, "收藏中...", true);
        new MyHttp().doPost(Api.getDefault().followStore(Integer.valueOf(shopInfo.getShop_id()), SPUtils.getSharedStringData(getContext(), AppConfig.LOGIN_TOKEN)), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                updateCollect();
                ToastUitl.showShort(result.getString("msg"));
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    /**
     * 更新关注状态
     */
    public void updateCollect() {
        shopInfo.setIs_favorites(1);
        ivCollect.setImageResource(R.drawable.already);
        ScaleAnimatorUtils.setScalse(ivCollect);
    }
}
