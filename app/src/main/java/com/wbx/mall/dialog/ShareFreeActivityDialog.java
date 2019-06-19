package com.wbx.mall.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wbx.mall.R;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.utils.ShareUtils;

public class ShareFreeActivityDialog extends DialogFragment {
    private String activityId;
    private String goodsId;
    private int shopId;
    private int gradeId;
    private String goodsImageUrl;

    public static ShareFreeActivityDialog newInstance(String activityId, String goodsId, int shopId, int gradeId, String goodsImageUrl) {
        ShareFreeActivityDialog fragment = new ShareFreeActivityDialog();
        Bundle bundle = new Bundle();
        bundle.putString("activityId", activityId);
        bundle.putString("goodsId", goodsId);
        bundle.putInt("shopId", shopId);
        bundle.putInt("gradeId", gradeId);
        bundle.putString("goodsImageUrl", goodsImageUrl);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        Bundle arguments = getArguments();
        activityId = arguments.getString("activityId");
        goodsId = arguments.getString("goodsId");
        shopId = arguments.getInt("shopId");
        gradeId = arguments.getInt("gradeId");
        goodsImageUrl = arguments.getString("goodsImageUrl");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.dialog_share_free_activity, container);
        view.findViewById(R.id.rl_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        view.findViewById(R.id.btn_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = "pages/index/freesheet/freesheet?activity_id=" + activityId + "&sponsor_user_id=" + LoginUtil.getUserInfo().getUser_id() + "&goods_id=" + goodsId + "&grade_id=" + gradeId + "&shop_id=" + shopId + "&activitytype=consume";
                ShareUtils.getInstance().shareMiniProgram(getContext(), "您的好友已经在附近实体店免单中，快去瞧瞧吧！！！", "", goodsImageUrl, path, "www.wbx365.com");
                dismiss();
            }
        });
        return view;
    }
}
