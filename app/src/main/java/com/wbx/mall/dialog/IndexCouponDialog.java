package com.wbx.mall.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.wbx.mall.R;
import com.wbx.mall.activity.StoreDetailActivity;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseApplication;
import com.wbx.mall.bean.HomeCouponBean;
import com.wbx.mall.bean.LocationInfo;
import com.wbx.mall.widget.LoadingDialog;
import com.wbx.mall.widget.decoration.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页免单推荐弹窗
 * DispatchingTimeDialog
 */
public class IndexCouponDialog extends Dialog {
    private Context mContext;
    private View layout;
    private List<HomeCouponBean.DataBean> mCouponList = new ArrayList<>();
    private IndexCouponAdapter adapter;

    public IndexCouponDialog(Context context) {
        super(context, R.style.DialogTheme);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = LayoutInflater.from(mContext).inflate(R.layout.dialog_index_coupon, null);
        setContentView(layout);
        init();
        initView();
        getIndexCoupon();
    }

    private void initView() {
        View ivClose = layout.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        RecyclerView recyclerView = layout.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10));
        adapter = new IndexCouponAdapter(mCouponList);
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.user_button) {
                    HomeCouponBean.DataBean data = mCouponList.get(position);
                    StoreDetailActivity.actionStart(mContext, data.getGrade_id() == AppConfig.StoreType.VEGETABLE_MARKET, data.getShop_id());
                }
            }
        });
    }

    private void init() {
        getWindow().setGravity(Gravity.CENTER);//设置显示在底部
        WindowManager windowManager = getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = (int) (display.getWidth() * 0.8);//设置Dialog的宽度为屏幕宽度
//        layoutParams.height = (int) (display.getHeight() / 0.8);//设置Dialog的高度
        getWindow().setAttributes(layoutParams);
//        getWindow().setWindowAnimations(R.style.main_menu_animStyle);
        setCanceledOnTouchOutside(true);
    }

    /**
     * 获取首页优惠券内容
     */
    private void getIndexCoupon() {
        LocationInfo locationInfo = (LocationInfo) BaseApplication.getInstance().readObject(AppConfig.LOCATION_DATA);
        String mCityName = String.valueOf(locationInfo.getCity_id());
        LoadingDialog.showDialogForLoading((Activity) mContext);
        new MyHttp().doPost(Api.getDefault().getIndexCoupon(mCityName), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                HomeCouponBean bean = new Gson().fromJson(result.toString(), HomeCouponBean.class);
                mCouponList.addAll(bean.getData());
                if (mCouponList.size() != 0) {
                    adapter.notifyDataSetChanged();
                } else {
                    dismiss();
                }
            }

            @Override
            public void onError(int code) {
                dismiss();
            }
        });
    }

    private class IndexCouponAdapter extends BaseQuickAdapter<HomeCouponBean.DataBean, BaseViewHolder> {
        IndexCouponAdapter(@Nullable List<HomeCouponBean.DataBean> data) {
            super(R.layout.item_index_coupon, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, HomeCouponBean.DataBean couponInfo) {
            holder.setText(R.id.shop_name_tv, couponInfo.getShop_name());
            TextView tv = holder.getView(R.id.coupon_monet_tv);
            Spannable span = new SpannableString("￥" + couponInfo.getMoney() / 100);
            span.setSpan(new RelativeSizeSpan(1f), 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            tv.setText(span);

            holder.setText(R.id.full_reduction_tv, "满" + couponInfo.getCondition_money() / 100 + "元可用");
            holder.addOnClickListener(R.id.user_button);
        }
    }
}
