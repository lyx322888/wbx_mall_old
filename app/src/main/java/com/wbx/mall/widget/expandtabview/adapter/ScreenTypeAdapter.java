package com.wbx.mall.widget.expandtabview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.bean.TypeInfo;

import java.util.List;

/**
 * Created by wushenghui on 2017/6/7.
 * 筛选选择类别的适配器
 */

public class ScreenTypeAdapter extends BaseAdapter{
    private Context mContext;
    private List<TypeInfo> mDataList;
    private OnItemClickCListener  mListener;
    public ScreenTypeAdapter(Context context, List<TypeInfo> dataList) {
        this.mDataList = dataList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if(null!=mDataList){
            return mDataList.size();
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
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_screen_type_view, null);
        ImageView typeIm = inflate.findViewById(R.id.type_icon_im);
        typeIm.setImageResource(mDataList.get(position).getSrcScore());
        TextView typeNameTv = inflate.findViewById(R.id.type_name_tv);
        typeNameTv.setText(mDataList.get(position).getName());
        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(null!=mListener){
                  mListener.ItemClickListener(position);
              }
            }
        });
        return inflate;
    }
    public void setOnItemClickListener(OnItemClickCListener listener){
          this.mListener = listener;
    }

    public interface OnItemClickCListener{
        void ItemClickListener(int position);
    }
}
