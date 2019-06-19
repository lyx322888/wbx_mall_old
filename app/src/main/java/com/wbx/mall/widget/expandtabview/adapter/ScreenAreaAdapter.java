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
 * 商圈
 */

public class ScreenAreaAdapter extends BaseAdapter{
    private Context mContext;
    private List<BusinessInfo> mDataList;
    private int selectPosition = 0;
    public ScreenAreaAdapter(Context context, List<BusinessInfo> data) {
          this.mContext = context;
        this.mDataList = data;
    }
    public void setSelectItem(int item){
        this.selectPosition = item;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(mDataList!=null){
            return  mDataList.size();
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
        TextView areaNameTv = inflate.findViewById(R.id.area_id_tv);
        if(selectPosition==i){
            inflate.setBackgroundColor(mContext.getResources().getColor(R.color.color_gray_line));
            areaNameTv.setTextColor(mContext.getResources().getColor(R.color.app_color));
        }
        areaNameTv.setText(mDataList.get(i).getArea_name());
        return inflate;
    }

}
