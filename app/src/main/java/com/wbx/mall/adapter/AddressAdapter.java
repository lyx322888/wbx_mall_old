package com.wbx.mall.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;

import com.wbx.mall.R;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.bean.AddressInfo;

import java.util.List;

/**
 * Created by wushenghui on 2017/7/18.
 */

public class AddressAdapter extends BaseAdapter<AddressInfo, Context> {

    public AddressAdapter(List<AddressInfo> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_address;
    }

    @Override
    public void convert(BaseViewHolder holder, AddressInfo addressInfo, int position) {
        CheckBox isDefauleCb = holder.getView(R.id.item_address_default_cb);
        isDefauleCb.setChecked(addressInfo.getDefaultX() == AppConfig.addressIsDefault.isDefault);
        holder.setText(R.id.address_name_tv, addressInfo.getXm());
        holder.setText(R.id.address_tel_tv, addressInfo.getTel());
        holder.setText(R.id.address_address_info_tv, addressInfo.getArea_str() + addressInfo.getInfo());
        if (TextUtils.isEmpty(addressInfo.getTag())) {
            holder.getView(R.id.tv_label).setVisibility(View.GONE);
        } else {
            holder.getView(R.id.tv_label).setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_label, addressInfo.getTag());
        }
    }
}
