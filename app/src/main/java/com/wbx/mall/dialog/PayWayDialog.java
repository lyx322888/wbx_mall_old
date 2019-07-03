package com.wbx.mall.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.wbx.mall.R;
import com.wbx.mall.adapter.PayWayAdapter;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.bean.PaymentInfo;

import java.util.List;

public class PayWayDialog extends Dialog {
    private Context mContext;
    private View layout;
    private List<PaymentInfo> mPayList;
    private PayWayAdapter adapter;
    private DialogListener listener;

    public PayWayDialog(Context context, List<PaymentInfo> list, DialogListener listener) {
        super(context, R.style.DialogTheme);
        this.mPayList = list;
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = LayoutInflater.from(mContext).inflate(R.layout.dialog_pay_choose, null);
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
        adapter = new PayWayAdapter(mPayList, getContext());
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(R.id.root_view, new BaseAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                for (PaymentInfo paymentBean : mPayList) {
                    paymentBean.setChecked(false);
                }
                PaymentInfo info = mPayList.get(position);
                info.setChecked(true);
                adapter.notifyDataSetChanged();
                listener.ListClick(view, info);
                dismiss();
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


    public interface DialogListener {
        void ListClick(View v, PaymentInfo info);
    }
}
