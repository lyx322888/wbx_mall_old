package com.wbx.mall.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbx.mall.R;
import com.wbx.mall.bean.GoodsInfo2;
import com.wbx.mall.utils.GlideUtils;

import java.util.List;

/**
 * Created by wushenghui on 2017/7/18.
 */

public class ItemShopGoodsAdapter extends BaseQuickAdapter<GoodsInfo2, BaseViewHolder> {

    ItemShopGoodsAdapter(@Nullable List<GoodsInfo2> data) {
        super(R.layout.item_shop_goods_layout, data);
    }

    @Override
    public void convert(BaseViewHolder holder, GoodsInfo2 goodsInfo) {
        ImageView view = holder.getView(R.id.goods_pic_im);
        ImageView faceView = holder.getView(R.id.iv_free_activity);
        GlideUtils.showMediumPic(mContext, view, goodsInfo.getPhoto());
        TextView user_num = holder.getView(R.id.tv_activity_user_num);
        TextView tvTitle = holder.getView(R.id.goods_name_tv);
        if (!TextUtils.isEmpty(goodsInfo.getTitle())) {
            tvTitle.setText(goodsInfo.getTitle());
        } else {
            tvTitle.setText(goodsInfo.getProduct_name());
        }
        holder.setText(R.id.goods_price_tv, String.format("店内价:" + "¥%.2f", goodsInfo.getSales_promotion_price() == 0 ? goodsInfo.getPrice() / 100.00 : goodsInfo.getSales_promotion_price() / 100.00));
        if (goodsInfo.getFree_goods_type().equals("goods")) {
            faceView.setVisibility(View.VISIBLE);
            faceView.setBackgroundResource(R.drawable.icon_index_special_offer);
        } else if (goodsInfo.getFree_goods_type().equals("share_free")) {
            faceView.setVisibility(View.VISIBLE);
            faceView.setBackgroundResource(R.drawable.icon_index_free_action);
        } else {
            faceView.setVisibility(View.GONE);
        }
        if (goodsInfo.getFree_goods_peoples() <= 0) {
            user_num.setVisibility(View.GONE);
        }
        LinearLayout linearLayout = holder.getView(R.id.ll_activity_user);
        if (goodsInfo.getFree_goods_peoples() == 0 || (goodsInfo.getIs_share_free() == 0 && goodsInfo.getIs_consume_free() == 0)) {
            linearLayout.setVisibility(View.GONE);
        } else {
            linearLayout.setVisibility(View.VISIBLE);
            ImageView face1 = holder.getView(R.id.iv_acitivity_user_1);
            ImageView face2 = holder.getView(R.id.iv_acitivity_user_2);
            ImageView face3 = holder.getView(R.id.iv_acitivity_user_3);
            face1.setVisibility(View.GONE);
            face2.setVisibility(View.GONE);
            face3.setVisibility(View.GONE);
            if (goodsInfo.getFree_goods_peoples_face() != null && goodsInfo.getFree_goods_peoples_face().size() > 0) {
                if (goodsInfo.getFree_goods_peoples_face().size() > 0) {
                    face1.setVisibility(View.VISIBLE);
                    GlideUtils.showSmallPic(mContext, (ImageView) holder.getView(R.id.iv_acitivity_user_1), goodsInfo.getFree_goods_peoples_face().get(0));
                }
                if (goodsInfo.getFree_goods_peoples_face().size() > 1) {
                    face2.setVisibility(View.VISIBLE);
                    GlideUtils.showSmallPic(mContext, (ImageView) holder.getView(R.id.iv_acitivity_user_2), goodsInfo.getFree_goods_peoples_face().get(1));
                }
                if (goodsInfo.getFree_goods_peoples_face().size() > 2) {
                    face3.setVisibility(View.VISIBLE);
                    GlideUtils.showSmallPic(mContext, (ImageView) holder.getView(R.id.iv_acitivity_user_3), goodsInfo.getFree_goods_peoples_face().get(2));
                }
            }
            user_num.setText(goodsInfo.getFree_goods_peoples() + "人免单成功");

        }
        holder.addOnClickListener(R.id.root_view);
    }
}
