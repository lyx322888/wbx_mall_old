package com.wbx.mall.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.hedgehog.ratingbar.RatingBar;
import com.wbx.mall.R;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.bean.CommentInfo;
import com.wbx.mall.utils.DateUtil;
import com.wbx.mall.utils.GlideUtils;

import java.util.List;

/**
 * Created by wushenghui on 2017/7/12.
 */

public class CommentAdapter extends BaseAdapter<CommentInfo, Context> {

    public CommentAdapter(List<CommentInfo> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_comment;
    }

    @Override
    public void convert(BaseViewHolder holder, CommentInfo commentInfo, int position) {
        holder.setText(R.id.comment_user_name_tv, commentInfo.getNickname()).setText(R.id.comment_content_tv, commentInfo.getMessage()).setRating(R.id.rb_score, commentInfo.getGrade());
        holder.setText(R.id.tv_time, DateUtil.formatDate2(commentInfo.getCreate_time()));
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
