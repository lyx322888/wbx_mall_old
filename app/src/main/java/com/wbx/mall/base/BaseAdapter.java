package com.wbx.mall.base;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wushenghui on 2017/4/27.
 */

public abstract class BaseAdapter<T, I> extends RecyclerView.Adapter<BaseViewHolder> {
    private final String TAG = "BaseAdapter";
    //存储监听回调
    private SparseArray<ItemClickListener> onClickListeners;

    private List<T> dataList;
    protected I mContext;

    public interface ItemClickListener {
        void onItemClicked(View view, int position);
    }

    public BaseAdapter(List<T> dataList, I context) {
        this.dataList = dataList;
        this.mContext = context;
        onClickListeners = new SparseArray<>();
    }

    /**
     * 存储viewId对应的回调监听实例listener
     *
     * @param viewId
     * @param listener
     */
    public void setOnItemClickListener(int viewId, ItemClickListener listener) {
        ItemClickListener listener_ = onClickListeners.get(viewId);
        if (listener_ == null) {
            onClickListeners.put(viewId, listener);
        }
    }

        /**
     * 获取列表控件的视图id(由子类负责完成)
     *
     * @param viewType
     * @return
     */
    public abstract int getLayoutId(int viewType);

    //更新itemView视图(由子类负责完成)
    public abstract void convert(BaseViewHolder holder, T t, int position);

    public T getItem(final int position) {
        if (dataList == null) {
            return null;
        }
        return dataList.get(position);
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = getLayoutId(viewType);
        BaseViewHolder viewHolder = BaseViewHolder.getViewHolder(parent, layoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        T itemModel = dataList.get(position);
        convert(holder, itemModel, position);//更新itemView视图
        //设置点击监听
        for (int i = 0; i < onClickListeners.size(); ++i) {
            int id = onClickListeners.keyAt(i);
            View view = holder.getView(id);
            if (view == null) {
                continue;
            }
            final ItemClickListener listener = onClickListeners.get(id);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClicked(v, position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (dataList == null) {
            return 0;
        }
        return dataList.size();
    }

    //清空
    public void destroyAdapter() {
        if (onClickListeners != null) {
            onClickListeners.clear();
        }
        onClickListeners = null;

        if (dataList != null) {
            dataList.clear();
        }
        dataList = null;
    }

    public void setData(List<T> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public void addData(List<T> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void deleteData(int position) {
        this.dataList.remove(position);
        notifyDataSetChanged();
    }


}
