package com.wbx.mall.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbx.mall.R;
import com.wbx.mall.bean.NewFreeInfoBean2;
import com.wbx.mall.common.ActivityManager;
import com.wbx.mall.widget.ScrollRecycleView;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPreview;

public class UpFreeInfoAdapter extends BaseQuickAdapter<NewFreeInfoBean2.DataBean, BaseViewHolder> {


    public UpFreeInfoAdapter(@Nullable List<NewFreeInfoBean2.DataBean> data) {
        super(R.layout.index_free_record, data);
    }




    @Override
    protected void convert(BaseViewHolder helper, final NewFreeInfoBean2.DataBean item) {
        if (helper.getView(R.id.ll_free).getVisibility() == View.GONE) {
            helper.getView(R.id.ll_free).setVisibility(View.VISIBLE);
        }
        TextView freePeopleNum = (TextView) helper.getView(R.id.tv_record_free_people_num);
        freePeopleNum.setText(item.getSuccess_activity().getCount_success_activity_user() + "");
        ScrollRecycleView rvFreeRecord = helper.getView(R.id.rv_free_record);
        rvFreeRecord.setLayoutManager(new LinearLayoutManager(mContext));
        IndexFreeRecordAdapter2 freeRecordAdapter = new IndexFreeRecordAdapter2(item.getSuccess_activity().getSuccess_activity());
        rvFreeRecord.setAdapter(freeRecordAdapter);
        //解决滑动冲突
        rvFreeRecord.setNestedScrollingEnabled(false);

        TextView tvSunDryNum = (TextView) helper.getView(R.id.tv_free_user_sun_drying_num);
        tvSunDryNum.setText(String.format("用户晒单(%1$d)", item.getEstimate().getCount_activity_estimate_users()));
        RecyclerView rvUserShare = helper.getView(R.id.rv_user_share);
        rvUserShare.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        IndexUserShareAdapter2 userShareAdapter = new IndexUserShareAdapter2(item.getEstimate().getActivity_estimate());
        rvUserShare.setAdapter(userShareAdapter);
        userShareAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.rl_picture:
                        if (item.getEstimate().getActivity_estimate() != null && item.getEstimate().getActivity_estimate().size() >= position) {
                            ArrayList<String> lstPhoto = new ArrayList<>();
                            lstPhoto.addAll(item.getEstimate().getActivity_estimate().get(position).getPics());
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

