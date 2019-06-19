package com.wbx.mall.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.bean.TypeInfo;
import com.wbx.mall.utils.GlideUtils;

import java.util.List;

/**
 * Created by wushenghui on 2017/7/11.
 */

public class TypeAdapter extends BaseAdapter<TypeInfo, Context> {

    public TypeAdapter(List<TypeInfo> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_type;
    }

    @Override
    public void convert(BaseViewHolder holder, TypeInfo typeInfo, int position) {
        if (typeInfo.getCate_id() != 0) {
            //说明是从线上获取的
            holder.setText(R.id.type_name_tv, typeInfo.getCate_name());
            ImageView photoIm = holder.getView(R.id.type_src_im);
            GlideUtils.showSmallPic(mContext, photoIm, typeInfo.getPhoto());
        } else {
            holder.setText(R.id.type_name_tv, typeInfo.getName());
            holder.setImageResource(R.id.type_src_im, typeInfo.getSrcScore());
        }
    }
}
