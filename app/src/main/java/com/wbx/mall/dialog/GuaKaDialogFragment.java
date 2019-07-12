package com.wbx.mall.dialog;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.module.mine.ui.WinRecordActivity;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public class GuaKaDialogFragment extends DialogFragment {
    private View.OnClickListener onCloseListener;
    private TextView tvMoney;
    private String money;

    public static GuaKaDialogFragment newInstance(String money) {
        GuaKaDialogFragment guaKaDialogFragment = new GuaKaDialogFragment();
        guaKaDialogFragment.setCancelable(false);
        guaKaDialogFragment.money = money;
        return guaKaDialogFragment;
    }

    public void setCloseListener(View.OnClickListener onClickListener) {
        this.onCloseListener = onClickListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.dialog_guaka, container);
        tvMoney = view.findViewById(R.id.tv_money);
        tvMoney.setText(money);
        view.findViewById(R.id.iv_close_dialog).setOnClickListener(onCloseListener == null ? new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        } : onCloseListener);
        view.findViewById(R.id.tv_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WinRecordActivity.actionStart(getActivity());
            }
        });
        return view;
    }
}
