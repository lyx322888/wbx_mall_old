package com.wbx.mall.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.common.ActivityManager;
import com.wbx.mall.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPreview;

/**
 * Created by wushenghui on 2017/7/12.
 */

public class CommentPicAdapter extends BaseAdapter<String, Context> {
    private ArrayList<String> lstPic = new ArrayList<>();

    public CommentPicAdapter(List<String> dataList, Context context) {
        super(dataList, context);
        lstPic.addAll(dataList);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_comment_pic;
    }

    @Override
    public void convert(BaseViewHolder holder, String s, final int position) {
        ImageView pic = holder.getView(R.id.iv_photo);
        GlideUtils.showMediumPic(mContext, pic, s);
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPreview.builder().setShowDeleteButton(false).setCurrentItem(position).setPhotos(lstPic).start(ActivityManager.getTopActivity());
            }
        });
    }
}
