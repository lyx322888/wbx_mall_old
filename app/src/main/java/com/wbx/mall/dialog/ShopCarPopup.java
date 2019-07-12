package com.wbx.mall.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.adapter.ShopCarAdapter;
import com.wbx.mall.bean.GoodsInfo2;

import java.util.ArrayList;
import java.util.List;

public class ShopCarPopup extends PopupWindow {
    private Context mContext;
    private final View layout;
    private TextView tvClear;
    private RecyclerView recyclerView;
    private List<GoodsInfo2> mSelecteList = new ArrayList<>();
    private ShopCarAdapter carAdapter;


    public ShopCarPopup(Context context) {
        super(context);
        this.mContext = context;
        layout = LayoutInflater.from(context).inflate(R.layout.dialog_shopcar_detail, null);
        setContentView(layout);
        init();
        intiView();
    }

    private void init() {
        ColorDrawable dw = new ColorDrawable(mContext.getResources().getColor(R.color.white));
        setBackgroundDrawable(dw);
        setAnimationStyle(R.style.PopupWindowAnimation);
    }

    private void intiView() {
        tvClear = layout.findViewById(R.id.tv_clear);
        recyclerView = layout.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setHasFixedSize(true);
        carAdapter = new ShopCarAdapter(mSelecteList);
        carAdapter.bindToRecyclerView(recyclerView);
    }

    public void setList(List<GoodsInfo2> list) {
        this.mSelecteList = list;
        carAdapter.notifyDataSetChanged();
    }
}
