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

import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.bean.RandomRedPacketBean;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.utils.ToastUitl;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public class RedPacketFragment extends DialogFragment {
    private RandomRedPacketBean data;
    private int shop_id;
    private TextView tvMoney;
    private OnReceiveSuccessListener listener;

    public static RedPacketFragment newInstance(RandomRedPacketBean bean, int shop_id) {
        RedPacketFragment redPacketFragment = new RedPacketFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", bean);
        bundle.putInt("shop_id", shop_id);
        redPacketFragment.setArguments(bundle);
        return redPacketFragment;
    }

    public void setOnReceiveSuccessListener(OnReceiveSuccessListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.dialog_red_packet, container);
        view.findViewById(R.id.iv_close_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        view.findViewById(R.id.follow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRedPacket();
            }
        });
        tvMoney = view.findViewById(R.id.tv_money);
        return view;
    }

    private void getRedPacket() {
        new MyHttp().doPost(Api.getDefault().receiveRedPacket(LoginUtil.getLoginToken(), data.getUser_red_packet_id(), shop_id), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                tvMoney.setText(String.valueOf(data.getReceive_money()));
                ToastUitl.showShort(result.getString("msg"));
                if (listener != null) {
                    listener.onSuccess();
                }
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        data = (RandomRedPacketBean) getArguments().getSerializable("data");
        shop_id = getArguments().getInt("shop_id");
    }

    public interface OnReceiveSuccessListener {
        void onSuccess();
    }
}
