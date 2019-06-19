package com.wbx.mall.adapter;

import android.content.Context;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.bean.ClientInfo;
import com.wbx.mall.utils.FormatUtil;

import java.util.List;

/**
 * Created by wushenghui on 2017/6/10.
 */

public class MyClientAdapter extends BaseAdapter<ClientInfo,Context> {

    public MyClientAdapter(List<ClientInfo> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_client_layout;
    }

    @Override
    public void convert(BaseViewHolder holder, ClientInfo clientInfo, int position) {
          holder.setText(R.id.account_tv,"账号:"+clientInfo.getAccount()).setText(R.id.nick_name_tv,"昵称:"+clientInfo.getNickname())
                  .setText(R.id.store_tv,"店铺:"+clientInfo.getShop_name()).setText(R.id.money_tv,String.format("提成:%.2f",clientInfo.getMoney()/100.00)).setText(R.id.time_tv, String.format("到期时间 : %s", FormatUtil.stampToDate(clientInfo.getReg_time()+"")));
    }
}
