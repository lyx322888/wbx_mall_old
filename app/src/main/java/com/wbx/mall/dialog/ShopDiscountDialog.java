package com.wbx.mall.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.utils.DisplayUtil;

/**
 * 店铺满减弹窗
 */
public class ShopDiscountDialog extends Dialog {
    private Context mContext;
    private View layout;
    private String mDiscount;

    public ShopDiscountDialog(Context context, String str) {
        super(context, R.style.DialogTheme);
        this.mDiscount = str;
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = LayoutInflater.from(mContext).inflate(R.layout.dialog_shop_discount, null);
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
        TextView tvDisCount = layout.findViewById(R.id.tv_discount);
        tvDisCount.setText(mDiscount);
    }

    private void init() {
        getWindow().setGravity(Gravity.BOTTOM);//设置显示在底部
        WindowManager windowManager = getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = display.getWidth();//设置Dialog的宽度为屏幕宽度
        layoutParams.height = DisplayUtil.dip2px(200);//设置Dialog的高度
        getWindow().setAttributes(layoutParams);
        getWindow().setWindowAnimations(R.style.main_menu_animStyle);
    }
}