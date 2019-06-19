package com.wbx.mall.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbx.mall.R;
import com.wbx.mall.bean.NewFreeInfoBean;
import com.wbx.mall.utils.GlideUtils;

import java.util.List;

public class IndexUserShareAdapter  extends BaseQuickAdapter<NewFreeInfoBean.EstimateBean.ActivityEstimateBean, BaseViewHolder> {
    public IndexUserShareAdapter(@Nullable List<NewFreeInfoBean.EstimateBean.ActivityEstimateBean> data) {
        super(R.layout.item_index_user_share, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final NewFreeInfoBean.EstimateBean.ActivityEstimateBean item) {
        int num = 0;
        if (item.getPics() != null) {
            num = item.getPics().size();
        }
        helper.setText(R.id.tv_num, num + "张")
                .setText(R.id.tv_comment, item.getMessage())
                .setText(R.id.tv_user_name, item.getNickname());
        if (item.getPics() != null && item.getPics().size() > 0) {
            GlideUtils.showMediumPic(mContext, (ImageView) helper.getView(R.id.iv_comment), item.getPics().get(0));
        } else {
            GlideUtils.showMediumPic(mContext, (ImageView) helper.getView(R.id.iv_comment), "");
        }
        GlideUtils.showSmallPic(mContext, (ImageView) helper.getView(R.id.iv_user), item.getFace());
        helper.addOnClickListener(R.id.rl_picture);
    }
}
