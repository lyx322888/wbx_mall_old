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

/**
 * Created by Administrator on 2018/3/6 0006.
 */

public class ActivityRuleDialogFragment extends DialogFragment {
    private String rule;

    public static ActivityRuleDialogFragment newInstance(String rule) {
        ActivityRuleDialogFragment fragment = new ActivityRuleDialogFragment();
        fragment.rule = rule;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.dialog_activity_rule, container);
        TextView tvRule = view.findViewById(R.id.tv_rule);
        tvRule.setText(rule);
        view.findViewById(R.id.iv_close_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }
}
