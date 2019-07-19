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
import com.google.gson.Gson;
import com.wbx.mall.R;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.bean.DispatchTimeBean;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.utils.TimeUtil;
import com.wbx.mall.utils.ToastUitl;
import com.wbx.mall.widget.LoadingDialog;
import com.wbx.mall.widget.decoration.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 选择送达时间弹窗
 * DispatchingTimeDialog
 */
public class DispatchingTimeDialog extends Dialog {
    private Context mContext;
    private boolean isPhysical;//是否菜市场
    private String orderId;//订单id
    private View layout;
    private List<DispatchTimeBean.TimeBean> mDate = new ArrayList<>();//日期list
    private List<String> mTime = new ArrayList<>();//时间list
    private int mIndexDate;//日期选中索引
    private int mIndexTime;//时间选中索引
    private DispatchDateAdapter mDateAdapter;//日期adapter
    private DispatchTimeAdapter mTimeAdapter;//时间adapter

    public DispatchingTimeDialog(Context context, boolean physical, String orderId, OnSubmitListener listener) {
        super(context, R.style.DialogTheme);
        this.mContext = context;
        this.onSubmitListener = listener;
        this.isPhysical = physical;
        this.orderId = orderId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = LayoutInflater.from(mContext).inflate(R.layout.dialog_dispatch_time, null);
        setContentView(layout);
        init();
        initView();
        getDispatchTime();
    }

    private void initView() {
        View ivClose = layout.findViewById(R.id.tv_submit);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(mDate.get(mIndexDate).getTime().get(mIndexTime));
            }
        });
        RecyclerView mRDate = layout.findViewById(R.id.recycler_date);
        mRDate.setLayoutManager(new LinearLayoutManager(getContext()));
        mRDate.setHasFixedSize(true);
        mDateAdapter = new DispatchDateAdapter(mDate);
        mDateAdapter.bindToRecyclerView(mRDate);
        mDateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position != mIndexDate) {
                    mDateAdapter.setItemCheck(position);
                    mIndexDate = position;
                    mIndexTime = 0;
                    mTime.clear();
                    mTime.addAll(mDate.get(position).getTime());
                    mTimeAdapter.setItemCheck(mIndexTime);
                    mTimeAdapter.notifyDataSetChanged();
                }
            }
        });
        RecyclerView mRTime = layout.findViewById(R.id.recycler_time);
        mRTime.setLayoutManager(new LinearLayoutManager(getContext()));
        mRTime.setHasFixedSize(true);
        mRTime.addItemDecoration(new SpacesItemDecoration(1));
        mTimeAdapter = new DispatchTimeAdapter(mTime);
        mTimeAdapter.bindToRecyclerView(mRTime);
        mTimeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position != mIndexTime) {
                    mTimeAdapter.setItemCheck(position);
                    mTimeAdapter.notifyDataSetChanged();
                    mIndexTime = position;
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

    /**
     * 获取可预订时间
     */
    private void getDispatchTime() {
        LoadingDialog.showDialogForLoading((Activity) mContext);
        new MyHttp().doPost(Api.getDefault().getDispatchTime("721"), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                DispatchTimeBean bean = new Gson().fromJson(result.toString(), DispatchTimeBean.class);
                mDate.addAll(bean.getData());
                mDateAdapter.notifyDataSetChanged();
                if (mDate.size() != 0) {
                    mTime.addAll(mDate.get(0).getTime());
                    mTimeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(int code) {
                dismiss();
            }
        });
    }

    public class DispatchDateAdapter extends BaseQuickAdapter<DispatchTimeBean.TimeBean, BaseViewHolder> {
        private int checkItem = 0;

        DispatchDateAdapter(@Nullable List<DispatchTimeBean.TimeBean> data) {
            super(R.layout.item_dispatch_date, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, DispatchTimeBean.TimeBean timeBean) {
            helper.setText(R.id.tv_day, timeBean.getDay() + "(" + timeBean.getWeek() + ")");
            helper.setText(R.id.tv_date, timeBean.getDate());
            View view = helper.getView(R.id.root_view);
            if (helper.getAdapterPosition() == checkItem) {
                view.setBackgroundResource(R.color.white);
            } else {
                view.setBackgroundResource(R.color.color_gray_line);
            }
        }

        void setItemCheck(int i) {
            this.checkItem = i;
            notifyDataSetChanged();
        }
    }

    public class DispatchTimeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        private int checkItem = 0;

        DispatchTimeAdapter(@Nullable List<String> data) {
            super(R.layout.item_dispatch_time, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String timeBean) {
            String str;
            String time = TimeUtil.formatData(TimeUtil.dateFormatHM, Long.valueOf(timeBean));
            if (mIndexDate == 0 && helper.getAdapterPosition() == 0) {
                str = "尽快送达 | " + time;
            } else {
                str = time + "";
            }
            helper.setText(R.id.tv_time, str);
            ImageView view = helper.getView(R.id.iv_check);
            if (helper.getAdapterPosition() == checkItem) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
        }

        void setItemCheck(int i) {
            this.checkItem = i;
        }
    }

    /**
     * 接口更新预订时间
     *
     * @param time
     */
    private void update(final String time) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("login_token", SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN));
        params.put("order_id", orderId);
        params.put("dispatching_time", time);
        new MyHttp().doPost((isPhysical ? Api.getDefault().updateMallTime(params) : Api.getDefault().updateBuyGreensTime(params)), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                String msg = result.getString("msg");
                if ("添加成功".equals(msg)) {
                    ToastUitl.showShort(msg);
                    String str;
                    String format = TimeUtil.formatData(TimeUtil.dateFormatHM, Long.valueOf(mDate.get(mIndexDate).getTime().get(mIndexTime)));
                    if (mIndexDate == 0 && mIndexTime == 0) {
                        str = "尽快送达 | " + format;
                    } else {
                        str = mDate.get(mIndexDate).getDate() + "-" + format;
                    }
                    onSubmitListener.onSubmit(time, str);
                } else {
                    ToastUitl.showShort(msg);
                }
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private OnSubmitListener onSubmitListener;

    public interface OnSubmitListener {
        void onSubmit(String time, String formatTime);//time 时间戳, formatTime 时间格式化
    }
}