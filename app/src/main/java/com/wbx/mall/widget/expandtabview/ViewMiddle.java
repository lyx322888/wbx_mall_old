package com.wbx.mall.widget.expandtabview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.wbx.mall.R;
import com.wbx.mall.bean.BusinessInfo;
import com.wbx.mall.widget.expandtabview.adapter.ScreenAreaAdapter;
import com.wbx.mall.widget.expandtabview.adapter.ScreenBusinessAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class ViewMiddle extends LinearLayout implements ViewBaseAction {
	
	private ListView regionListView;
	private ListView plateListView;
	private ArrayList<String> groups = new ArrayList<String>();
	private LinkedList<String> childrenItem = new LinkedList<String>();
	private SparseArray<LinkedList<String>> children = new SparseArray<LinkedList<String>>();
	private TextAdapter plateListViewAdapter;
	private TextAdapter earaListViewAdapter;
	private OnSelectListener mOnSelectListener;
	private int tEaraPosition = 0;
	private int tBlockPosition = 0;
	private String showString = "不限";
	private Context mContext;
	private ScreenAreaAdapter screenAreaAdapter;
	private ScreenBusinessAdapter screenBusinessAdapter;
	private int selectAreaId;
	private List<BusinessInfo.BusinessBean> selectBusiness;


	public ViewMiddle(Context context) {
		super(context);
		init(context);
	}

	public ViewMiddle(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public void setData(final List<BusinessInfo> data){
		screenAreaAdapter = new ScreenAreaAdapter(mContext,data);
		screenBusinessAdapter = new ScreenBusinessAdapter(mContext);
		plateListView.setAdapter(screenBusinessAdapter);
		selectBusiness = data.get(0).getBusiness();
		screenBusinessAdapter.setData(selectBusiness);
		regionListView.setAdapter(screenAreaAdapter);
		regionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				selectBusiness = data.get(i).getBusiness();
				selectAreaId = data.get(i).getArea_id();
				screenAreaAdapter.setSelectItem(i);
				screenBusinessAdapter.setData(data.get(i).getBusiness());
			}
		});
		plateListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				BusinessInfo.BusinessBean businessBean = selectBusiness.get(i);
				if(null!=mOnSelectListener){
					mOnSelectListener.getValue(businessBean.getBusiness_name(),businessBean.getBusiness_id(),selectAreaId);
				}

			}
		});
	}

	public void updateShowText(String showArea, String showBlock) {
		if (showArea == null || showBlock == null) {
			return;
		}
		for (int i = 0; i < groups.size(); i++) {
			if (groups.get(i).equals(showArea)) {
				earaListViewAdapter.setSelectedPosition(i);
				childrenItem.clear();
				if (i < children.size()) {
					childrenItem.addAll(children.get(i));
				}
				tEaraPosition = i;
				break;
			}
		}
		for (int j = 0; j < childrenItem.size(); j++) {
			if (childrenItem.get(j).replace("不限", "").equals(showBlock.trim())) {
				plateListViewAdapter.setSelectedPosition(j);
				tBlockPosition = j;
				break;
			}
		}
		setDefaultSelect();
	}

	private void init(Context context) {
		this.mContext = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_region, this, true);
        regionListView = findViewById(R.id.listView);
        plateListView = findViewById(R.id.listView2);
		setBackgroundDrawable(getResources().getDrawable(
				R.drawable.choosearea_bg_mid));


	}

	public void setDefaultSelect() {
		regionListView.setSelection(tEaraPosition);
		plateListView.setSelection(tBlockPosition);
	}

	public String getShowText() {
		return showString;
	}

	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
        void getValue(String showText, int businessId, int areaId);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
}
