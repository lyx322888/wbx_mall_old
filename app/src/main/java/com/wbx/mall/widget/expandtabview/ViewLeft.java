package com.wbx.mall.widget.expandtabview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.wbx.mall.R;
import com.wbx.mall.bean.TypeInfo;
import com.wbx.mall.widget.expandtabview.adapter.ScreenTypeAdapter;

import java.util.List;


public class ViewLeft extends RelativeLayout implements ViewBaseAction{

	private ListView mListView;
	private final String[] items = new String[] { "item1", "item2", "item3", "item4", "item5", "item6" };//显示字段
	private final String[] itemsVaule = new String[] { "1", "2", "3", "4", "5", "6" };//隐藏id
	private OnSelectListener mOnSelectListener;
	private ScreenTypeAdapter adapter;
	private String mDistance;
	private String showText = "item1";
	private Context mContext;


	public void setData(final List<TypeInfo> dataList){
		adapter = new ScreenTypeAdapter(mContext,dataList);
		mListView.setAdapter(adapter);
		adapter.setOnItemClickListener(new ScreenTypeAdapter.OnItemClickCListener() {
			@Override
			public void ItemClickListener(int position) {
				if (mOnSelectListener != null) {
					showText = dataList.get(position).getName();
					mOnSelectListener.getValue(position, dataList.get(position).getName());
				}
			}
		});
	}

	public String getShowText() {
		return showText;
	}

	public ViewLeft(Context context) {
		super(context);
		init(context);
	}

	public ViewLeft(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public ViewLeft(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_distance, this, true);
		setBackgroundDrawable(getResources().getDrawable(R.drawable.choosearea_bg_left));
        mListView = findViewById(R.id.listView);

	}

	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
        void getValue(int distance, String showText);
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void show() {
		
	}

}
