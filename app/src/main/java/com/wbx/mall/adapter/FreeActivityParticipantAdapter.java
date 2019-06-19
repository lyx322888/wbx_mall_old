package com.wbx.mall.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbx.mall.R;
import com.wbx.mall.bean.ConsumeFreeGoodsDetailBean;
import com.wbx.mall.utils.GlideUtils;

import java.util.List;

public class FreeActivityParticipantAdapter extends BaseQuickAdapter<ConsumeFreeGoodsDetailBean.ActivityUsers, BaseViewHolder> {

    public FreeActivityParticipantAdapter(@Nullable List<ConsumeFreeGoodsDetailBean.ActivityUsers> data) {
        super(R.layout.item_free_activity_participant, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ConsumeFreeGoodsDetailBean.ActivityUsers item) {
        TextView tvUserName = helper.getView(R.id.tv_user_name);
        if (TextUtils.isEmpty(item.getFace()) && TextUtils.isEmpty(item.getNickname())) {
            helper.getView(R.id.rl_has_user).setVisibility(View.GONE);
            tvUserName.setText("待下单");
            tvUserName.setTextColor(Color.parseColor("#908F8F"));
        } else {
            helper.getView(R.id.rl_has_user).setVisibility(View.VISIBLE);
            GlideUtils.showMediumPic(mContext, (ImageView) helper.getView(R.id.iv_user), item.getFace());
            tvUserName.setText(item.getNickname());
            tvUserName.setTextColor(Color.BLACK);
        }
    }
}
