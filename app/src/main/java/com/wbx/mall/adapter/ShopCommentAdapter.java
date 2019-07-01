package com.wbx.mall.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hedgehog.ratingbar.RatingBar;
import com.wbx.mall.R;
import com.wbx.mall.bean.CommentInfo;
import com.wbx.mall.utils.DateUtil;
import com.wbx.mall.utils.GlideUtils;

import java.util.List;

/**
 * Created by wushenghui on 2017/7/12.
 */

public class ShopCommentAdapter extends BaseQuickAdapter<CommentInfo, BaseViewHolder> {

    public ShopCommentAdapter(@Nullable List<CommentInfo> data) {
        super(R.layout.item_comment, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, CommentInfo commentInfo) {
        holder.setText(R.id.comment_user_name_tv, commentInfo.getNickname());
        holder.setText(R.id.tv_time, DateUtil.formatDate2(commentInfo.getCreate_time()));
        holder.setText(R.id.comment_content_tv, commentInfo.getMessage());
        RatingBar rb = holder.getView(R.id.rb_score);
        rb.setStar(commentInfo.getGrade());

        ImageView faceIm = holder.getView(R.id.comment_face_im);
        GlideUtils.showRoundSmallPic(mContext, faceIm, commentInfo.getFace());
        RecyclerView commentPicRv = holder.getView(R.id.comment_pic_rv);
        commentPicRv.setLayoutManager(new GridLayoutManager(mContext, 3));
        if (null != commentInfo.getPics()) {
            CommentPicAdapter adapter = new CommentPicAdapter(commentInfo.getPics(), mContext);
            commentPicRv.setAdapter(adapter);
        }
    }
}
