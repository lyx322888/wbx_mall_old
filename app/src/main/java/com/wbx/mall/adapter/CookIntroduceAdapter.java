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

public class CookIntroduceAdapter extends BaseAdapter<CookBookDetailInfo.ProcessBean, Context> {

    public CookIntroduceAdapter(List<CookBookDetailInfo.ProcessBean> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_cook_intro;
    }

    @Override
    public void convert(BaseViewHolder holder, CookBookDetailInfo.ProcessBean processList, int position) {
        ImageView view = holder.getView(R.id.pic_im);
        GlideUtils.showMediumPic(mContext, view, processList.getPic());
        holder.setText(R.id.content_tv, processList.getPcontent());
    }
}
