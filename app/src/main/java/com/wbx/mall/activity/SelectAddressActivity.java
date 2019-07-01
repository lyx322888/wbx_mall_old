package com.wbx.mall.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;
import com.wbx.mall.R;
import com.wbx.mall.adapter.CityAdapter;
import com.wbx.mall.adapter.NearByAddressAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseApplication;
import com.wbx.mall.bean.LocationInfo;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.utils.ScreenUtil;
import com.wbx.mall.widget.LoadingDialog;
import com.wbx.mall.widget.decoration.DividerItemDecoration;
import com.wbx.mall.widget.iosdialog.AlertDialog;
import com.zyyoona7.lib.EasyPopup;
import com.zyyoona7.lib.HorizontalGravity;
import com.zyyoona7.lib.VerticalGravity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wushenghui on 2018/1/15.
 */

public class SelectAddressActivity extends BaseActivity implements PoiSearch.OnPoiSearchListener, AMapLocationListener {
    @Bind(R.id.nearby_address_rv)
    RecyclerView nearbyAddressRv;
    private NearByAddressAdapter nearByAddressAdapter;
    List<PoiItem> poiItems = new ArrayList<>();
    @Bind(R.id.search_address_edit)
    EditText searchEdit;
    @Bind(R.id.city_name_tv)
    TextView cityNameTv;
    private EasyPopup easyPopup;
    @Bind(R.id.hand_view_ll)
    LinearLayout handViewLl;
    @Bind(R.id.tv_address_hint)
    TextView tvAddressHint;
    private List<LocationInfo> cityInfoList = new ArrayList<>();
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明AMapLocationClient类对象
    public AMapLocationClient mlocationClient = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_address;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        nearbyAddressRv.setLayoutManager(new LinearLayoutManager(mContext));
        nearByAddressAdapter = new NearByAddressAdapter(poiItems, mContext);
        nearbyAddressRv.setAdapter(nearByAddressAdapter);
    }

    @Override
    public void fillData() {
        if (SPUtils.getString("city_name_select", mLocationInfo.getName()) == null) {
            cityNameTv.setText(mLocationInfo.getName());
        } else {
            cityNameTv.setText(SPUtils.getString("city_name_select", mLocationInfo.getName()));
        }
        search("", mLocationInfo.getName(), new LatLng(mLocationInfo.getLat(), mLocationInfo.getLng()));
    }

    private void search(String keyword, String cityName, LatLng latLng) {
        PoiSearch.Query query;
        if (TextUtils.isEmpty(keyword)) {
            query = new PoiSearch.Query("", "商务住宅", cityName);
        } else {
            query = new PoiSearch.Query(keyword, "", cityName);
        }
        query.setPageSize(10);
        query.setPageNum(1);
        PoiSearch poiSearch = new PoiSearch(this, query);
        if (latLng != null) {
            tvAddressHint.setText("附近地址");
            poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(mLocationInfo.getLat(), mLocationInfo.getLng()), 1000));
        } else {
            tvAddressHint.setText("搜索相关地址");
        }
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void setListener() {
        findViewById(R.id.select_location_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingDialog.showDialogForLoading(SelectAddressActivity.this, "定位中...", false);
                reLocation();
            }
        });
        nearByAddressAdapter.setOnItemClickListener(R.id.root_view, new BaseAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                PoiItem poiItem = nearByAddressAdapter.getItem(position);
                LocationInfo locationInfo = new LocationInfo();
                locationInfo.setName(poiItem.getCityName().replace("市", ""));
                locationInfo.setLat(poiItem.getLatLonPoint().getLatitude());
                locationInfo.setLng(poiItem.getLatLonPoint().getLongitude());
                locationInfo.setAddressName(poiItem.getTitle());
                BaseApplication.getInstance().saveObject(locationInfo, AppConfig.LOCATION_DATA);
                Intent intent = new Intent();
                intent.setAction("refreshHasLocation");
                sendBroadcast(intent);
                finish();
            }
        });

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    search("", mLocationInfo.getName(), new LatLng(mLocationInfo.getLat(), mLocationInfo.getLng()));
                } else {
                    search(charSequence.toString(), cityNameTv.getText().toString(), null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void reLocation() {
        mlocationClient = new AMapLocationClient(getApplicationContext());
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
        //如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会。
        mLocationOption.setOnceLocationLatest(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        poiItems.clear();
        poiItems.addAll(poiResult.getPois());
        nearByAddressAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
    }

    @OnClick({R.id.city_name_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.city_name_tv:
                LoadingDialog.showDialogForLoading(mActivity, "加载中...", true);
                showAllCityPop();
                break;
        }
    }

    private void getAllCityData() {
        new MyHttp().doPost(Api.getDefault().getCityList(), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                cityInfoList = JSONArray.parseArray(result.getString("data"), LocationInfo.class);
                initAllView();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    //显示所有的城市
    private void showAllCityPop() {
        if (null == easyPopup) {
            easyPopup = new EasyPopup(this)
                    .setContentView(R.layout.pop_all_city_view)
                    .setAnimationStyle(R.style.PopupWindowAnimation)
                    .setWidth(ScreenUtil.getScreenWidth(mContext))
                    .setHeight((int) (ScreenUtil.getScreenHeight(mContext) - handViewLl.getY() - handViewLl.getHeight()))
                    .setFocusAndOutsideEnable(true)
                    .createPopup();
            getAllCityData();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    LoadingDialog.cancelDialogForLoading();
                }
            }, 500);
            easyPopup.showAtAnchorView(handViewLl, VerticalGravity.BELOW, HorizontalGravity.LEFT, 0, 0);
        }
    }


    //初始化 所有城市的View
    private void initAllView() {
//        mIndexBar.getDataHelper().sortSourceDatas(cityInfoList);//排序
        final TextView tvLocationCityName = easyPopup.getView(R.id.tv_city_name);
        if (mLocationInfo != null) {
            tvLocationCityName.setText(mLocationInfo.getName());
        }
        easyPopup.getView(R.id.ll_location_city).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityNameTv.setText(tvLocationCityName.getText().toString());
                easyPopup.dismiss();
                search("", tvLocationCityName.getText().toString(), null);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        RecyclerView cityRecyclerView = easyPopup.getView(R.id.city_recycle_view);
        cityRecyclerView.setLayoutManager(linearLayoutManager);
        cityRecyclerView.addItemDecoration(new SuspensionDecoration(this, cityInfoList)
                .setmTitleHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, getResources().getDisplayMetrics()))
                .setColorTitleBg(0xffefefef)
                .setTitleFontSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()))
                .setColorTitleFont(mContext.getResources().getColor(android.R.color.black)));
        cityRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        CityAdapter cityAdapter = new CityAdapter(cityInfoList, mActivity);
        cityRecyclerView.setAdapter(cityAdapter);
        cityAdapter.setOnItemClickListener(R.id.root_view, new BaseAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                cityNameTv.setText(cityInfoList.get(position).getName());
                SPUtils.put("city_name_select", cityInfoList.get(position).getName(), mContext);
                easyPopup.dismiss();
                search("", cityNameTv.getText().toString(), null);
            }
        });
        IndexBar mIndexBar = easyPopup.getView(R.id.indexBar);
        TextView mSideBarHintTv = easyPopup.getView(R.id.tvSideBarHint);
        mIndexBar.setmPressedShowTextView(mSideBarHintTv)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(linearLayoutManager);//设置RecyclerView的LayoutManager
        mIndexBar.setmSourceDatas(cityInfoList)//设置数据
                .invalidate();
        easyPopup.showAtAnchorView(handViewLl, VerticalGravity.BELOW, HorizontalGravity.LEFT, 0, 0);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        LoadingDialog.cancelDialogForLoading();
        if (aMapLocation.getErrorCode() != AMapLocation.LOCATION_SUCCESS) {
            new AlertDialog(getApplicationContext()).builder()
                    .setTitle("提示")
                    .setMsg("抱歉,定位失败,使用默认地址。\n开启定位权限--->授权管理--->应用权限管理--->定位权限开启")
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LocationInfo locationInfo = new LocationInfo();
                            locationInfo.setName("厦门");
                            locationInfo.setCity_id(19);
                            locationInfo.setSelectCityId(19);
                            locationInfo.setSelectCityName("厦门");
                            locationInfo.setLat(24.485271);
                            locationInfo.setLng(118.197294);
                            BaseApplication.getInstance().saveObject(locationInfo, AppConfig.LOCATION_DATA);

                        }
                    }).show();
        } else {
            LocationInfo locationInfo = new LocationInfo();
            locationInfo.setName(aMapLocation.getCity().replace("市", ""));
            locationInfo.setAddressName(aMapLocation.getPoiName());
            locationInfo.setLat(aMapLocation.getLatitude());
            locationInfo.setLng(aMapLocation.getLongitude());
            BaseApplication.getInstance().saveObject(locationInfo, AppConfig.LOCATION_DATA);
            Intent intent = new Intent();
            intent.setAction("refreshHasLocation");
            sendBroadcast(intent);
        }
        finish();
    }
}
