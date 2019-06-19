package com.chad.library.adapter.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * @author Administrator
 */
public abstract class BaseQuickWithPayloadsAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {

    public BaseQuickWithPayloadsAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public BaseQuickWithPayloadsAdapter(List<T> data) {
        super(data);
    }

    @Override
    public void onBindViewHolder(@NonNull K holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            changeInfo(holder);
        }
    }

    /**
     * 局部刷新
     * @param holder
     */
    public abstract void changeInfo(K holder);
}
