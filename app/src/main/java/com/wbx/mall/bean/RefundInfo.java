package com.wbx.mall.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by wushenghui on 2017/7/13.
 */

public class RefundInfo implements IPickerViewData {
    private String refund;

    public RefundInfo(String s) {
        refund = s;
    }

    @Override
    public String getPickerViewText() {
        return refund;
    }
}
