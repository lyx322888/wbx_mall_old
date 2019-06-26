package com.wbx.mall.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wbx.mall.R;
import com.wbx.mall.activity.FreeActivityDetailActivity;
import com.wbx.mall.activity.StoreDetailActivity;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.bean.NewFreeInfoBean;
import com.wbx.mall.bean.ShopInfo2;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.utils.GlideUtils;

import java.util.List;

/**
 * Created by wushenghui on 2017/7/18.
 */

public class ShopGoodsAdapter extends BaseAdapter<ShopInfo2, Context> {
    private int pre_shop_is_exceed = 0;
    private NewFreeInfoBean newFreeInfo;
    private boolean isShowHead = true;

    public ShopGoodsAdapter(List<ShopInfo2> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_shop_goods;
    }


    @Override
    public void convert(BaseViewHolder holder, final ShopInfo2 shopInfo, int position) {
        if (pre_shop_is_exceed == 0 && shopInfo.getIs_exceed() == 1) {
            holder.getView(R.id.iv_is_exceed).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.iv_is_exceed).setVisibility(View.GONE);
        }
        pre_shop_is_exceed = shopInfo.getIs_exceed();
        ImageView shopTag = holder.getView(R.id.iv_shop_tag);
        int sold_num = shopInfo.getSold_num();
        if (sold_num < 100) {
            shopTag.setImageResource(R.drawable.shop_tag_new);
        } else if (sold_num < 300) {
            shopTag.setImageResource(R.drawable.shop_tag_hot);
        } else if (sold_num < 600) {
            shopTag.setImageResource(R.drawable.shop_tag_star);
        } else {
            shopTag.setImageResource(R.drawable.shop_tag_fire);
        }
        ImageView storePicIm = holder.getView(R.id.item_promotion_store_pic_im);
        TextView tvShopState = holder.getView(R.id.tv_shop_state);
        TextView tvpopularity = holder.getView(R.id.popularity_tv);
        tvpopularity.setText("人气 " + shopInfo.getView());
        switch (shopInfo.getShop_status()) {
            case 0:
                //休息中
                tvShopState.setText("休息中");
                tvShopState.setTextColor(Color.parseColor("#888888"));
                tvShopState.setBackgroundResource(R.drawable.rect_stroke_888888_3dp);
                break;
            case 1:
                //营业中
                tvShopState.setText("营业中");
                tvShopState.setTextColor(mContext.getResources().getColor(R.color.app_color));
                tvShopState.setBackgroundResource(R.drawable.rect_stroke_app_color_3dp);
                break;
            case 2:
                //筹备开业
                tvShopState.setText("筹备开业");
                tvShopState.setTextColor(Color.parseColor("#F39800"));
                tvShopState.setBackgroundResource(R.drawable.rect_stroke_f39800_3dp);
                break;
            case 3:
                //新店开业
                tvShopState.setText("新店开业");
                tvShopState.setTextColor(Color.parseColor("#F15353"));
                tvShopState.setBackgroundResource(R.drawable.rect_stroke_f15353_3dp);
                break;
        }
        GlideUtils.showRoundMediumPic(mContext, storePicIm, shopInfo.getPhoto());
        holder.setText(R.id.promotion_name_tv, shopInfo.getShop_name())
                .setRating(R.id.ratingBar, shopInfo.getScore())
                .setText(R.id.item_promotion_info_tv, String.format("起送¥%.2f", shopInfo.getSince_money() / 100.00) + ((LoginUtil.isLogin() && LoginUtil.getUserInfo().getIs_salesman() == 1) ? " 销量:" + sold_num : ""))
                .setText(R.id.promotion_address_tv, "地址:" + shopInfo.getAddr())
                .setText(R.id.item_promotion_distance_tv, shopInfo.getD())
                .setText(R.id.item_promotion_attestation_tv, shopInfo.getIs_renzheng() == 0 ? "未认证" : "已认证");
        if (TextUtils.isEmpty(shopInfo.getPeisong_fanwei())) {
            holder.getView(R.id.item_promotion_send_range_tv).setVisibility(View.GONE);
        } else {
            holder.getView(R.id.item_promotion_send_range_tv).setVisibility(View.VISIBLE);
            holder.setText(R.id.item_promotion_send_range_tv, "配送范围：" + shopInfo.getPeisong_fanwei());
        }
        LinearLayout hasFullLayout = holder.getView(R.id.has_full_layout);
        LinearLayout hasCouponLayout = holder.getView(R.id.has_coupon_layout);
        if (null != shopInfo.getFull_money_reduce() && shopInfo.getFull_money_reduce().size() > 0) {
            hasFullLayout.setVisibility(View.VISIBLE);
            holder.setText(R.id.shop_activity_tv, String.format("满%.2f减%.2f", shopInfo.getFull_money_reduce().get(0).getFull_money() / 100.00, shopInfo.getFull_money_reduce().get(0).getReduce_money() / 100.00));

        } else {
            hasFullLayout.setVisibility(View.GONE);
        }
        if (null != shopInfo.getCoupon() && shopInfo.getCoupon().size() > 0) {
            hasCouponLayout.setVisibility(View.VISIBLE);
            holder.setText(R.id.shop_coupon_tv, String.format("满%.2f减%.2f", shopInfo.getCoupon().get(0).getCondition_money() / 100.00, shopInfo.getCoupon().get(0).getMoney() / 100.00));
        } else {
            hasCouponLayout.setVisibility(View.GONE);
        }


        if (shopInfo.getGrade_id() == AppConfig.StoreGrade.MARKET) {
            holder.setText(R.id.item_store_grade_tv, "菜市场");
        } else if (shopInfo.getGrade_id() == 20) {
            holder.setText(R.id.item_store_grade_tv, "美食街");
        } else {
            holder.setText(R.id.item_store_grade_tv, "实体店");
        }

        RecyclerView goodsRecycler = holder.getView(R.id.promotion_goods_rv);
        ItemShopGoodsAdapter02 goodsAdapter = new ItemShopGoodsAdapter02(shopInfo.getGoods());
        LinearLayoutManager lm = new LinearLayoutManager(mContext);
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        goodsRecycler.setLayoutManager(lm);
        goodsRecycler.setAdapter(goodsAdapter);
        goodsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (!LoginUtil.isLogin()) {
                    LoginUtil.login();
                    return;
                }
                if (shopInfo.getGoods().get(position).getIs_share_free() == 1 || shopInfo.getGoods().get(position).getIs_consume_free() == 1) {
                    FreeActivityDetailActivity.actionStart(mContext, shopInfo.getShop_id() + "", shopInfo.getGoods().get(position).getGoods_id() + "", shopInfo.getGrade_id());
                } else {
                    StoreDetailActivity.actionStart(mContext, shopInfo.getGrade_id(), shopInfo.getShop_id() + "", shopInfo.getGoods().get(position).getGoods_id());
                }
            }
        });
    }


    public void setFreeInfo(NewFreeInfoBean newFreeInfoBean) {
        this.newFreeInfo = newFreeInfoBean;
    }

    public boolean isFirstsFreeInfo() {
        return this.newFreeInfo == null;
    }

    public void hideHeadFreeInfo() {
        isShowHead = false;
    }
}
