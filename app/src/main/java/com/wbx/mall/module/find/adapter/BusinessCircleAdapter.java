package com.wbx.mall.module.find.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.wbx.mall.R;
import com.wbx.mall.activity.LoginActivity;
import com.wbx.mall.activity.StoreDetailActivity;
import com.wbx.mall.adapter.BusinessCirclePhotoAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.bean.BusinessCircleBean;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.utils.GlideUtils;
import com.wbx.mall.utils.ShareUtils;
import com.wbx.mall.widget.CircleImageView;
import com.wbx.mall.widget.LoadingDialog;
import com.wbx.mall.widget.decoration.SpacesItemDecoration;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zero on 2017/1/17.
 */

public class BusinessCircleAdapter extends RecyclerView.Adapter<BusinessCircleAdapter.MyViewHolder> {
    private Activity mContext;
    private List<BusinessCircleBean> lstData;
    private DecimalFormat format = new DecimalFormat("0.00");
    private LatLng currentLatLng;

    public BusinessCircleAdapter(Activity context, List<BusinessCircleBean> list) {
        this.mContext = context;
        this.lstData = list;
    }

    public void setCurrentLatLng(LatLng currentLatLng) {
        this.currentLatLng = currentLatLng;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business_circle, parent, false);
        return new MyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final BusinessCircleBean bean = lstData.get(position);
        holder.tvEnterShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreDetailActivity.actionStart(mContext, bean.getGrade_id() == AppConfig.StoreType.VEGETABLE_MARKET, String.valueOf(bean.getShop_id()));
            }
        });
        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setNestedScrollingEnabled(false);
        GlideUtils.showSmallPic(mContext, holder.ivUser, bean.getPhoto());
        holder.tvUser.setText(bean.getShop_name());
        holder.tvTime.setText(bean.getCreate_time());
        if (bean.getIs_favorites() == 1) {
            holder.tvFocus.setSelected(true);
            holder.tvFocus.setText("已关注");
        } else {
            holder.tvFocus.setSelected(false);
            holder.tvFocus.setText("关注店铺");
        }
        holder.tvFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.getIs_favorites() == 1 || holder.tvFocus.isSelected()) {
                    return;
                }
                if (!LoginUtil.isLogin()) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.putExtra("isMainTo", true);
                    mContext.startActivity(intent);
                    return;
                }
                LoadingDialog.showDialogForLoading(mContext, "关注中...", true);
                new MyHttp().doPost(Api.getDefault().followStore(bean.getShop_id(), LoginUtil.getLoginToken()), new HttpListener() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        for (BusinessCircleBean lstDatum : lstData) {
                            if (lstDatum.getShop_id() == bean.getShop_id()) {
                                lstDatum.setIs_favorites(1);
                            }
                        }
                        Toast.makeText(mContext, result.getString("msg"), Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onError(int code) {

                    }
                });
            }
        });
        holder.tvContent.setText(bean.getText());
        if (bean.getPhotos() != null && bean.getPhotos().size() > 2) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
            holder.recyclerView.setLayoutManager(gridLayoutManager);
        } else {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
            holder.recyclerView.setLayoutManager(gridLayoutManager);
        }
        holder.recyclerView.addItemDecoration(new SpacesItemDecoration(2));
        BusinessCirclePhotoAdapter businessCirclePhotoAdapter = new BusinessCirclePhotoAdapter(mContext);
        holder.recyclerView.setAdapter(businessCirclePhotoAdapter);
        businessCirclePhotoAdapter.update(bean.getPhotos());
        LatLng latLng = new LatLng(bean.getLat(), bean.getLng());
        float distance = AMapUtils.calculateLineDistance(latLng, currentLatLng);
        holder.tvAddress.setText(bean.getAddr());
        holder.tvDistance.setText(distance < 1000 ? format.format(distance) + "m" : format.format(distance / 1000.00) + "km");
        holder.tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ShareUtils.getInstance().share(mContext, bean.getShop_name(), bean.getText(), bean.getPhotos() == null || bean.getPhotos().size() == 0 ? bean.getPhoto() : bean.getPhotos().get(0), bean.getShare_url());
                String path = "pages/found/found?shop_id=" + bean.getShop_id();
                ShareUtils.getInstance().shareMiniProgram(mContext, bean.getText(), "", bean.getPhotos().size() == 0 ? bean.getPhoto() : bean.getPhotos().get(0), path, "www.wbx365.com");
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_user)
        CircleImageView ivUser;
        @Bind(R.id.tv_user)
        TextView tvUser;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_enter_shop)
        TextView tvEnterShop;
        @Bind(R.id.tv_focus)
        TextView tvFocus;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.recycler_view)
        RecyclerView recyclerView;
        @Bind(R.id.tv_address)
        TextView tvAddress;
        @Bind(R.id.tv_distance)
        TextView tvDistance;
        @Bind(R.id.tv_share)
        TextView tvShare;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
