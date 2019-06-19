package com.wbx.mall.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wbx.mall.R;
import com.wbx.mall.adapter.IndexFreeRecordAdapter;
import com.wbx.mall.adapter.IndexUserShareAdapter;
import com.wbx.mall.bean.NewFreeInfoBean;
import com.wbx.mall.common.ActivityManager;
import com.wbx.mall.widget.ScrollRecycleView;

import java.util.ArrayList;

import me.iwf.photopicker.PhotoPreview;

public class UpFreeInfoUtils {
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    public static void upUI(final NewFreeInfoBean newFreeInfo, View item, Context mContext) {
        if (item.getVisibility() == View.GONE) {
            item.setVisibility(View.VISIBLE);
        }
        TextView freePeopleNum = (TextView) item.findViewById(R.id.tv_record_free_people_num);
        freePeopleNum.setText(newFreeInfo.getSuccess_activity().getCount_success_activity_user() + "");

        ScrollRecycleView rvFreeRecord = item.findViewById(R.id.rv_free_record);
        rvFreeRecord.setLayoutManager(new LinearLayoutManager(mContext));
        IndexFreeRecordAdapter freeRecordAdapter = new IndexFreeRecordAdapter(newFreeInfo.getSuccess_activity().getSuccess_activity());
        rvFreeRecord.setAdapter(freeRecordAdapter);
        //解决滑动冲突
        rvFreeRecord.setNestedScrollingEnabled(false);

        TextView tvSunDryNum = (TextView) item.findViewById(R.id.tv_free_user_sun_drying_num);
        tvSunDryNum.setText(String.format("用户晒单(%1$d)", newFreeInfo.getEstimate().getCount_activity_estimate_users()));
        RecyclerView rvUserShare = item.findViewById(R.id.rv_user_share);
        rvUserShare.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        IndexUserShareAdapter userShareAdapter = new IndexUserShareAdapter(newFreeInfo.getEstimate().getActivity_estimate());
        rvUserShare.setAdapter(userShareAdapter);
        userShareAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.rl_picture:
                        if (newFreeInfo.getEstimate().getActivity_estimate() != null && newFreeInfo.getEstimate().getActivity_estimate().size() >= position) {
                            ArrayList<String> lstPhoto = new ArrayList<>();
                            lstPhoto.addAll(newFreeInfo.getEstimate().getActivity_estimate().get(position).getPics());
                            PhotoPreview.builder().setPhotos(lstPhoto).setShowDeleteButton(false).start(ActivityManager.getTopActivity());
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
