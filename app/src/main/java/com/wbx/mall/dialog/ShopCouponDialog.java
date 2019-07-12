package com.wbx.mall.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbx.mall.R;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.bean.CouponInfo;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.utils.DateUtil;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.utils.ToastUitl;
import com.wbx.mall.widget.LoadingDialog;

import java.util.List;

/**
 * 店铺领取优惠券弹窗
 */
public class ShopCouponDialog extends Dialog {
    private Context mContext;
    private View layout;
    private List<CouponInfo> mCouponList;
    private ShopCouponAdapter adapter;
    private DialogListener listener;

    public ShopCouponDialog(Context context, List<CouponInfo> list, DialogListener listener) {
        super(context, R.style.DialogTheme);
        this.mCouponList = list;
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = LayoutInflater.from(mContext).inflate(R.layout.dialog_shop_coupon, null);
        setContentView(layout);
        init();
        initView();
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
        adapter = new ShopCouponAdapter(mCouponList);
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_receive) {
                    CouponInfo data = mCouponList.get(position);
                    if (data.getIs_receive() != 1) {
                        if (LoginUtil.isLogin()) {
                            receiveCoupon(data);
                        } else {
                            LoginUtil.login();
                        }
                    } else {
                        ToastUitl.showShort("不能重复领取!");
                    }
                }
            }
        });
    }

    private void init() {
        getWindow().setGravity(Gravity.BOTTOM);//设置显示在底部
        WindowManager windowManager = getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = display.getWidth();//设置Dialog的宽度为屏幕宽度
//        layoutParams.height = DensityUtil.dip2px(mContext, 250);//设置Dialog的高度
        getWindow().setAttributes(layoutParams);
        getWindow().setWindowAnimations(R.style.main_menu_animStyle);
    }

    private void receiveCoupon(final CouponInfo data) {
        LoadingDialog.showDialogForLoading((Activity) mContext);
        new MyHttp().doPost(Api.getDefault().rceiveCoupon(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), data.getCoupon_id()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                ToastUitl.showShort("领取成功!");
                data.setIs_receive(1);
                adapter.notifyDataSetChanged();
                listener.ListClick(mCouponList);
            }

            @Override
            public void onError(int code) {
                ToastUitl.showShort("领取失败!");
                LoadingDialog.cancelDialogForLoading();
            }
        });
    }

    public interface DialogListener {
        void ListClick(List<CouponInfo> couponInfoList);
    }

    private class ShopCouponAdapter extends BaseQuickAdapter<CouponInfo, BaseViewHolder> {

        ShopCouponAdapter(@Nullable List<CouponInfo> data) {
            super(R.layout.item_shop_coupon_detail, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, CouponInfo couponInfo) {
            holder.setText(R.id.tv_money, String.format("%.2f", couponInfo.getMoney() / 100.00)).setText(R.id.tv_condition, String.format("满%d元使用", couponInfo.getCondition_money() / 100)).setText(R.id.tv_end_time, String.format("限本店使用，%s到期", DateUtil.formatDate3(couponInfo.getEnd_time())));
            ImageView ivReceive = holder.getView(R.id.iv_receive);
            if (couponInfo.getIs_receive() == 1) {
                ivReceive.setImageResource(R.drawable.icon_received_coupon);
            } else {
                ivReceive.setImageResource(R.drawable.icon_receive_coupon);
            }
            holder.addOnClickListener(R.id.iv_receive);
        }
    }

}
