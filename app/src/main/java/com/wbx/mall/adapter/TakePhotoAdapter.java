package com.wbx.mall.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.wbx.mall.R;
import com.wbx.mall.activity.CommentActivity;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.utils.GlideUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by wushenghui on 2017/7/14.
 */

public class TakePhotoAdapter extends BaseAdapter<String, Context> {
    private ArrayList<String> picList;
    private CommentActivity activity;

    public TakePhotoAdapter(ArrayList<String> dataList, CommentActivity context) {
        super(dataList, context);
        this.picList = dataList;
        this.activity = context;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_take_photo;
    }

    @Override
    public void convert(BaseViewHolder holder, String s, final int position) {
        ImageView photoPic = holder.getView(R.id.photo_im);
        ImageView ivDel = holder.getView(R.id.iv_del);
        if (position == 0) {
            photoPic.setImageResource(R.drawable.p_add);
            ivDel.setVisibility(View.GONE);
        } else {
            GlideUtils.displayUri(mContext, photoPic, Uri.fromFile(new File(s)));
            ivDel.setVisibility(View.VISIBLE);
        }
        ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picList.remove(position);
                activity.updateCount();
                notifyDataSetChanged();
            }
        });
    }
}
