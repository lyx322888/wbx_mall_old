package com.wbx.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.widget.AutoLocateHorizontalView;

import java.util.List;

/**
 * Created by wushenghui on 2017/12/20.
 */

public class SelectUseFoodNumAdapter extends RecyclerView.Adapter<SelectUseFoodNumAdapter.NumViewHolder> implements AutoLocateHorizontalView.IAutoLocateHorizontalView{
    //存储监听回调
    private SparseArray<ItemClickListener> onClickListeners;
    private Context context;
    private View view;
    private List<String> ages;
    public interface ItemClickListener {
        void onItemClicked(View view, int position);
    }

    public SelectUseFoodNumAdapter(Context context,List<String>ages){
        this.context = context;
        this.ages = ages;
        onClickListeners = new SparseArray<>();
    }
    @Override
    public NumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item_select_use_food_num,parent,false);

        return new NumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NumViewHolder holder, final int position) {
        //设置点击监听
        for (int i = 0; i < onClickListeners.size(); ++i){
            final ItemClickListener listener = onClickListeners.get(holder.tvNum.getId());
            holder.tvNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onItemClicked(v,position);
                    }
                }
            });
        }
        holder.tvNum.setText(ages.get(position));
    }

    @Override
    public int getItemCount() {
        return ages.size();
    }

    @Override
    public View getItemView() {
        return view;
    }

    @Override
    public void onViewSelected(boolean isSelected, int pos, RecyclerView.ViewHolder holder, int itemWidth) {
        if(isSelected) {
            ((NumViewHolder) holder).tvNum.setTextSize(20);
        }else{
            ((NumViewHolder) holder).tvNum.setTextSize(14);
        }
    }
    /**
     * 存储viewId对应的回调监听实例listener
     * @param viewId
     * @param listener
     */
    public void setOnItemClickListener(int viewId,ItemClickListener listener) {
        ItemClickListener listener_ = onClickListeners.get(viewId);
        if(listener_ == null){
            onClickListeners.put(viewId,listener);
        }
    }

    static class NumViewHolder extends RecyclerView.ViewHolder{
        TextView tvNum;
        NumViewHolder(View itemView) {
            super(itemView);
            tvNum = itemView.findViewById(R.id.num_tv);
        }

    }
}
