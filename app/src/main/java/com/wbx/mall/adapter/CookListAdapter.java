package com.wbx.mall.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.bean.CookBookDetailInfo;
import com.wbx.mall.utils.GlideUtils;

import java.util.List;

/**
 * Created by wushenghui on 2017/8/25.
 */

public class CookListAdapter extends BaseAdapter<CookBookDetailInfo, Context> {

    public CookListAdapter(List<CookBookDetailInfo> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_cook_list;
    }

    @Override
    public void convert(BaseViewHolder holder, CookBookDetailInfo cookInfo, int position) {
        ImageView view = holder.getView(R.id.pic_im);
        GlideUtils.showMediumPic(mContext, view, cookInfo.getPic());
        holder.setText(R.id.tv_title, cookInfo.getName()).setText(R.id.intro_tv, cookInfo.getContent());
    }
}
