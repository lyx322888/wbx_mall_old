package com.wbx.mall.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbx.mall.R;
import com.wbx.mall.bean.NewFreeInfoBean;
import com.wbx.mall.utils.GlideUtils;

import java.util.List;

public class IndexFreeRecordAdapter extends BaseQuickAdapter<NewFreeInfoBean.SuccessActivityBeanX.SuccessActivityBean, BaseViewHolder> {
    public IndexFreeRecordAdapter(@Nullable List<NewFreeInfoBean.SuccessActivityBeanX.SuccessActivityBean> data) {
        super(R.layout.item_index_free_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final NewFreeInfoBean.SuccessActivityBeanX.SuccessActivityBean item) {
        GlideUtils.showMediumPic(mContext, (ImageView) helper.getView(R.id.iv_free_shop_photo), item.getPhoto());
        GlideUtils.showSmallPic(mContext, (ImageView) helper.getView(R.id.iv_free_people_photo), item.getFace());
        helper.setText(R.id.tv_free_people_name, item.getNickname())
                .setText(R.id.tv_free_goods_title, item.getTitle());
    }
}
