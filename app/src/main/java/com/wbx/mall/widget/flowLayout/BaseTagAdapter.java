package com.wbx.mall.widget.flowLayout;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.utils.DisplayUtil;

import java.util.List;

/**
 * @author Zero
 * @date 2018/10/17
 */
public abstract class BaseTagAdapter<T> extends TagAdapter<T> {
    private Context mContext;

    public BaseTagAdapter(Context context, List<T> datas) {
        super(datas);
        this.mContext = context;
        setSelectedList(0);
    }

    @Override
    public View getView(FlowLayout parent, int position, T t) {
        TextView child = new TextView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.rightMargin = DisplayUtil.dip2px(10);
        params.bottomMargin = DisplayUtil.dip2px(5);
        child.setBackgroundResource(R.drawable.uncheck_spec);
        child.setTextColor(Color.parseColor("#737373"));
        child.setLayoutParams(params);
        child.setPadding(DisplayUtil.dip2px(10), DisplayUtil.dip2px(4), DisplayUtil.dip2px(10), DisplayUtil.dip2px(4));
        child.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        setText(child, position, t);
        return child;
    }

    public abstract void setText(TextView textView, int position, T t);

    @Override
    public void onSelected(int position, View view) {
        super.onSelected(position, view);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(mContext.getResources().getColor(R.color.app_color));
            view.setBackgroundResource(R.drawable.check_spec);
        }
    }

    @Override
    public void unSelected(int position, View view) {
        super.unSelected(position, view);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(Color.parseColor("#737373"));
            view.setBackgroundResource(R.drawable.uncheck_spec);
        }
    }
}
