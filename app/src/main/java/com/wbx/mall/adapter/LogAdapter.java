package com.wbx.mall.adapter;

import android.content.Context;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.bean.LogInfo;
import com.wbx.mall.utils.DateUtil;

import java.util.List;

/**
 * Created by wushenghui on 2017/7/18.
 */

public class LogAdapter extends BaseAdapter<LogInfo, Context> {

    public LogAdapter(List<LogInfo> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_log;
    }

    @Override
    public void convert(BaseViewHolder holder, LogInfo logInfo, int position) {
        holder.setText(R.id.money_tv, String.format("微米:%.2f", logInfo.getMoney() / 100.00))
                .setText(R.id.create_time_tv, DateUtil.formatDateAndTime2(logInfo.getCreate_time()))
                .setText(R.id.intro_tv, logInfo.getIntro().replaceAll("\r|\n|\t",""));
    }
}
