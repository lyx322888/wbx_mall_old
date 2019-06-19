package com.wbx.mall.widget.expandtabview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.bean.BusinessInfo;

import java.util.List;

/**
 * Created by wushenghui on 2017/6/7.
 */

public class ScreenBusinessAdapter extends BaseAdapter {
    private Context mContext;
    private List<BusinessInfo.BusinessBean> mList;
    public ScreenBusinessAdapter(Context context) {
          this.mContext = context;
    }
    public void setData(List<BusinessInfo.BusinessBean> business) {
        this.mList = business;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        if(null!=mList){
            return  mList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_screen_area_view, null);
        TextView textView = inflate.findViewById(R.id.area_id_tv);
        textView.setText(mList.get(i).getBusiness_name());
        return inflate;
    }


}
