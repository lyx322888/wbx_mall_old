package com.wbx.mall.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.adapter.CityAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseApplication;
import com.wbx.mall.bean.LocationInfo;
import com.wbx.mall.utils.SectionDecoration;
import com.wbx.mall.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;

/**
 * Created by wushenghui on 2017/5/9.
 */

public class ChooseCityActivity extends BaseActivity {
    @Bind(R.id.city_recycle_view)
    RecyclerView mRecycleView;
    @Bind(R.id.choose_city_location_city_tv)
    TextView cityTv;
    @Bind(R.id.screen_keyword_edit)
    EditText screenKeyWordEdit;
    private LinearLayoutManager linearLayoutManager;
    private List<LocationInfo> cityInfoList;
    private CityAdapter mAdapter;
    private List<String> titleName;
    private LocationInfo locationCityInfo;
    private boolean isAllCity;
    private List<LocationInfo> screenCityInfoList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_choose_city;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        linearLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void fillData() {
        isAllCity = getIntent().getBooleanExtra("isAllCity", false);
        locationCityInfo = (LocationInfo) BaseApplication.getInstance().readObject(AppConfig.LOCATION_DATA);
        cityTv.setText(String.format("当前城市 ：%s", locationCityInfo.getName()));
        getServiceData();

    }

    private void getServiceData() {
        LoadingDialog.showDialogForLoading(mActivity, "加载中...", true);
        new MyHttp().doPost(isAllCity ? Api.getDefault().getAllCityList() : Api.getDefault().getCityList(), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                cityInfoList = JSONArray.parseArray(result.getString("data"), LocationInfo.class);
                putData();
            }

            @Override
            public void onError(int code) {

            }
        });

    }

    private void putData() {
        Collections.sort(cityInfoList, new Comparator<LocationInfo>() {
            @Override
            public int compare(LocationInfo cityInfo, LocationInfo t1) {
                //根据首字母排序
                return cityInfo.getFirst_letter().compareTo(t1.getFirst_letter());
            }
        });
        titleName = new ArrayList<>();
        for (int i = 0; i < cityInfoList.size(); i++) {
            titleName.add(cityInfoList.get(i).getFirst_letter());
        }
        mRecycleView.addItemDecoration(new SectionDecoration(titleName, mContext, new SectionDecoration.DecorationCallback() {
            //返回标记id (即每一项对应的标志性的字符串)
            @Override
            public String getGroupId(int position) {
                if (titleName.get(position) != null) {
                    return titleName.get(position);
                }
                return "-1";
            }

            //获取同组中的第一个内容
            @Override
            public String getGroupFirstLine(int position) {
                if (titleName.get(position) != null) {
                    return titleName.get(position);
                }
                return "";
            }
        }));


        mAdapter = new CityAdapter(cityInfoList, mActivity);

        mRecycleView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(R.id.root_view, new BaseAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                LocationInfo item = mAdapter.getItem(position);
                if (isAllCity) {
                    Intent intent = new Intent();
                    intent.putExtra("selectCity", item);
                    setResult(1004, intent);
                } else {
//                locationCityInfo.setCity_id(locationInfo.getCity_id());
//                locationCityInfo.setName(locationInfo.getName());
//                locationCityInfo.setName(locationInfo.getName());
//                locationCityInfo.setCity_id(locationInfo.getCity_id());
                    locationCityInfo.setSelectCityName(item.getName());
                    locationCityInfo.setSelectCityId(item.getCity_id());
                    locationCityInfo.setSelect(true);
                    BaseApplication.getInstance().saveObject(locationCityInfo, AppConfig.LOCATION_DATA);
                    Intent intent = new Intent();
                    intent.setAction("refreshUi");
                    sendOrderedBroadcast(intent, null);
                }
                finish();
            }
        });
    }


    @Override
    public void setListener() {
        screenKeyWordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                screenCityInfoList.clear();
                if (TextUtils.isEmpty(charSequence)) {
                    mAdapter.setData(cityInfoList);
                } else {
                    for (LocationInfo locationInfo : cityInfoList) {
                        if (locationInfo.getName().contains(charSequence.toString()) || locationInfo.getFirst_letter().equals(charSequence.toString())) {
                            screenCityInfoList.add(locationInfo);
                        }
                    }
                    mAdapter.setData(screenCityInfoList);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        cityTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationCityInfo.setSelectCityId(locationCityInfo.getCity_id());
                locationCityInfo.setSelectCityName(locationCityInfo.getName());
                locationCityInfo.setCity_id(locationCityInfo.getCity_id());
                locationCityInfo.setSelect(false);
                if (isAllCity) {
                    Intent intent = new Intent();
                    intent.putExtra("selectCity", locationCityInfo);
                    setResult(1004, intent);
                } else {
                    BaseApplication.getInstance().saveObject(locationCityInfo, AppConfig.LOCATION_DATA);
                    Intent intent = new Intent();
                    intent.setAction("refreshUi");
                    sendOrderedBroadcast(intent, null);
                }
                finish();
            }
        });
    }

}
