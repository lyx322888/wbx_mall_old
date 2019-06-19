package com.wbx.mall.adapter;

import android.content.Context;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.bean.MemberInfo;

import java.util.List;

/**
 * Created by wushenghui on 2017/9/6.
 */

public class MemberTypeAdapter extends BaseAdapter<MemberInfo,Context> {
    private int selectItem = 0;
    public MemberTypeAdapter(List<MemberInfo> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_member_type;
    }

    @Override
    public void convert(BaseViewHolder holder, MemberInfo memberInfo, int position) {

    }
    public  void setSelectItem(int position){
        this.selectItem = position;
        notifyDataSetChanged();
    }
}
