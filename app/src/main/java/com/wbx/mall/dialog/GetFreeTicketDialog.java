package com.wbx.mall.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wbx.mall.R;

public class GetFreeTicketDialog extends DialogFragment {
    private Context mContext;
    private OnResultListener listener;

    public static GetFreeTicketDialog newInstance(Context context) {
        GetFreeTicketDialog freeTicketDialog = new GetFreeTicketDialog();
        freeTicketDialog.mContext = context;
        return freeTicketDialog;
    }

    public void setResultListener(OnResultListener resultList) {
        this.listener = resultList;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.dialog_get_free_ticket, container);
        TextView tvUse = view.findViewById(R.id.tv_get_free_ticket_use);
        ImageView ivCancel = view.findViewById(R.id.iv_get_free_ticket_cancel);
        RelativeLayout rlBg = view.findViewById(R.id.rl_get_free_ticket_bg);
        tvUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != listener) {
                    listener.onSuccess();
                }
            }
        });
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        rlBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
    }

    public interface OnResultListener {
        void onSuccess();
    }
}
