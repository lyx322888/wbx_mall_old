package com.wbx.mall.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbx.mall.R;
import com.wbx.mall.bean.GoodsInfo2;
import com.wbx.mall.widget.flowLayout.BaseTagAdapter;
import com.wbx.mall.widget.flowLayout.TagFlowLayout;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * @author Zero
 * @date 2018/10/16
 */
public class NatureAdapter extends BaseQuickAdapter<GoodsInfo2.Nature, BaseViewHolder> {
    private LinkedHashMap<String, GoodsInfo2.Nature_attr> hmSelectNature = new LinkedHashMap<>();
    private OnNatureChangeListener onNatureChangeListener;

    public NatureAdapter(int layoutResId, @Nullable List<GoodsInfo2.Nature> data) {
        super(layoutResId, data);
        if (data != null) {
            for (GoodsInfo2.Nature datum : data) {
                //默认选中第一个
                hmSelectNature.put(datum.getItem_id(), datum.getNature_arr().get(0));
            }
        }
    }

    public void setOnNatureChangeListener(OnNatureChangeListener onNatureChangeListener) {
        this.onNatureChangeListener = onNatureChangeListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, final GoodsInfo2.Nature item) {
        helper.setText(R.id.tv_name, item.getItem_name());
        TagFlowLayout tagFlowLayout = helper.getView(R.id.laybelLayout);
        tagFlowLayout.setAdapter(new BaseTagAdapter<GoodsInfo2.Nature_attr>(mContext, item.getNature_arr()) {
            @Override
            public void setText(TextView textView, int position, GoodsInfo2.Nature_attr nature_attr) {
                textView.setText(nature_attr.getNature_name());
            }
        });
        tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                hmSelectNature.put(item.getItem_id(), item.getNature_arr().get(selectPosSet.iterator().next()));
                if (onNatureChangeListener != null) {
                    onNatureChangeListener.onNatureChange(hmSelectNature);
                }
            }
        });
    }

    public interface OnNatureChangeListener {
        void onNatureChange(LinkedHashMap<String, GoodsInfo2.Nature_attr> selectNature);
    }
}
