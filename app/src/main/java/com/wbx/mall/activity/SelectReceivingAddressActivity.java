package com.wbx.mall.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;
import com.wbx.mall.R;
import com.wbx.mall.adapter.CityAdapter;
import com.wbx.mall.adapter.SelectReceivingAddressAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.bean.LocationInfo;
import com.wbx.mall.utils.KeyBordUtil;
import com.wbx.mall.utils.ScreenUtil;
import com.wbx.mall.utils.ToastUitl;
import com.wbx.mall.widget.LoadingDialog;
import com.wbx.mall.widget.decoration.DividerItemDecoration;
import com.wbx.mall.widget.refresh.BaseRefreshListener;
import com.wbx.mall.widget.refresh.PullToRefreshLayout;
import com.zyyoona7.lib.EasyPopup;
import com.zyyoona7.lib.HorizontalGravity;
import com.zyyoona7.lib.VerticalGravity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class SelectReceivingAddressActivity extends BaseActivity implements PoiSearch.OnPoiSearchListener, BaseRefreshListener, GeocodeSearch.OnGeocodeSearchListener {
    public static final int REQUEST_SELECT_ADDRESS = 1000;
    @Bind(R.id.et_search_address)
    EditText etSearchAddress;
    @Bind(R.id.map_view)
    MapView mapView;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.ptrl)
    PullToRefreshLayout ptrl;
    @Bind(R.id.tv_city)
    TextView tvCity;
    @Bind(R.id.ll_search)
    LinearLayout llSearch;
    @Bind(R.id.tv_search)
    TextView tvSearch;
    @Bind(R.id.cover)
    View cover;
    @Bind(R.id.recycler_view_search)
    RecyclerView recyclerViewSearch;
    private EasyPopup easyPopup;
    private List<PoiItem> lstAddress = new ArrayList<>();
    private List<PoiItem> lstSearchAddress = new ArrayList<>();
    private int currentPage = 1;
    private SelectReceivingAddressAdapter adapter;
    private String lastKeyWord;
    private String lastCityName;
    private LatLng lastLatLng;
    private List<LocationInfo> lstCity;
    private SelectReceivingAddressAdapter searchAdapter;
    private boolean isDragByUser = false;//防止由于定位或者地理编码引起的地图中心点移动而造成多次请求数据

    //新增
    public static void actionStart(Activity context) {
        Intent intent = new Intent(context, SelectReceivingAddressActivity.class);
        context.startActivityForResult(intent, REQUEST_SELECT_ADDRESS);
    }

    //旧数据，没有经纬度，手动输入地址
    public static void actionStart(Activity context, String address) {
        Intent intent = new Intent(context, SelectReceivingAddressActivity.class);
        intent.putExtra("address", address);
        context.startActivityForResult(intent, REQUEST_SELECT_ADDRESS);
    }

    //采用地图地位的方式，有经纬度
    public static void actionStart(Activity context, LatLng latLng) {
        Intent intent = new Intent(context, SelectReceivingAddressActivity.class);
        intent.putExtra("latLng", latLng);
        context.startActivityForResult(intent, REQUEST_SELECT_ADDRESS);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_receiving_address;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        initRecyclerView();
        initMap();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SelectReceivingAddressAdapter(R.layout.item_select_receiving_address, lstAddress);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        recyclerViewSearch.setLayoutManager(layoutManager2);
        searchAdapter = new SelectReceivingAddressAdapter(R.layout.item_select_receiving_address, lstSearchAddress);
        recyclerViewSearch.setAdapter(searchAdapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PoiItem poiItem = lstAddress.get(position);
                Intent intent = new Intent();
                intent.putExtra("address", poiItem);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        searchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PoiItem poiItem = lstSearchAddress.get(position);
                Intent intent = new Intent();
                intent.putExtra("address", poiItem);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void initMap() {
        mapView.onCreate(mSavedInstanceState);
        final AMap map = mapView.getMap();
        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);
        uiSettings.setMyLocationButtonEnabled(false);
        map.setMapType(AMap.MAP_TYPE_NORMAL);
        map.moveCamera(CameraUpdateFactory.zoomTo(17));
        LatLng latLng = new LatLng(39.906901, 116.397972);
        final Marker marker = map.addMarker(new MarkerOptions().position(latLng));
        map.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                LatLng latLng = new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude);
                marker.setPosition(latLng);
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                LatLng latLng = new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude);
                marker.setPosition(latLng);
                if (isDragByUser) {
                    search("", "", latLng);
                }
            }
        });
        map.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                isDragByUser = true;
            }
        });
        if (TextUtils.isEmpty(getIntent().getStringExtra("address"))) {
            LatLng latLng2 = getIntent().getParcelableExtra("latLng");
            if (latLng2 != null) {
                //采用地图定位的方式得到的数据
                map.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng2, 17, 0, 0)));
                marker.setPosition(latLng2);
                search("", "", latLng2);
            } else {
                //新增
                uiSettings.setMyLocationButtonEnabled(true);
                MyLocationStyle myLocationStyle = new MyLocationStyle();
                myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
                myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
                myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
                map.setMyLocationStyle(myLocationStyle);
                map.setMyLocationEnabled(true);
                map.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location location) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        marker.setPosition(latLng);
                        search("", "", latLng);
                    }
                });
            }
        } else {
            //用户手动输入的旧数据
            LoadingDialog.showDialogForLoading(this);
            GeocodeSearch geocodeSearch = new GeocodeSearch(this);
            geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
                @Override
                public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

                }

                @Override
                public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                    LoadingDialog.cancelDialogForLoading();
                    if (geocodeResult.getGeocodeAddressList().size() > 0) {
                        LatLonPoint latLonPoint = geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint();
                        LatLng latLng = new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
                        map.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 17, 0, 0)));
                        marker.setPosition(latLng);
                        search("", "", latLng);
                    }
                }
            });
            GeocodeQuery geocodeQuery = new GeocodeQuery(getIntent().getStringExtra("address"), null);
            geocodeSearch.getFromLocationNameAsyn(geocodeQuery);
        }
    }

    private void search(String keyword, String cityName, LatLng latLng) {
        LoadingDialog.showDialogForLoading(this);
        lastKeyWord = keyword;
        lastCityName = cityName;
        lastLatLng = latLng;
        PoiSearch.Query query;
        if (lastLatLng == null) {
            query = new PoiSearch.Query(keyword, "", cityName);
        } else {
            query = new PoiSearch.Query(keyword, "商务住宅", cityName);
        }
        if (recyclerViewSearch.getVisibility() == View.VISIBLE) {
            query.setPageSize(30);
        } else {
            query.setPageSize(10);
        }
        query.setPageNum(currentPage);
        PoiSearch poiSearch = new PoiSearch(this, query);
        if (lastLatLng != null) {
            poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(lastLatLng.latitude, lastLatLng.longitude), 1000));
        }
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void fillData() {

    }

    @Override
    public void setListener() {
        ptrl.setRefreshListener(this);
        etSearchAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    cover.setVisibility(View.VISIBLE);
                    tvSearch.setVisibility(View.VISIBLE);
                } else {
                    cover.setVisibility(View.GONE);
                    tvSearch.setVisibility(View.GONE);
                }
            }
        });
        etSearchAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    recyclerViewSearch.setVisibility(View.VISIBLE);
                }
            }
        });
        etSearchAddress.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    tvSearch.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        LoadingDialog.cancelDialogForLoading();
        if (ptrl != null) {
            ptrl.finishRefresh();
            ptrl.finishLoadMore();
        }
        if (recyclerViewSearch.getVisibility() == View.VISIBLE) {
            lstSearchAddress.clear();
            lstSearchAddress.addAll(poiResult.getPois());
            searchAdapter.notifyDataSetChanged();
        } else {
            if (currentPage == 1) {
                lstAddress.clear();
            }
            lstAddress.addAll(poiResult.getPois());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void refresh() {
        currentPage = 1;
        search(lastKeyWord, lastCityName, lastLatLng);
    }

    @Override
    public void loadMore() {
        currentPage++;
        search(lastKeyWord, lastCityName, lastLatLng);
    }

    //显示所有的城市
    private void showAllCityPop() {
        LoadingDialog.showDialogForLoading(mActivity, "加载中...", true);
        if (null == easyPopup) {
            easyPopup = new EasyPopup(this)
                    .setContentView(R.layout.pop_all_city_view)
                    .setAnimationStyle(R.style.PopupWindowAnimation)
                    .setWidth(ScreenUtil.getScreenWidth(mContext))
                    .setHeight((int) (ScreenUtil.getScreenHeight(mContext) - llSearch.getY() - llSearch.getHeight()))
                    .setFocusAndOutsideEnable(true)
                    .createPopup();
            getAllCityData();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    LoadingDialog.cancelDialogForLoading();
                }
            }, 1000);
            easyPopup.showAtAnchorView(llSearch, VerticalGravity.BELOW, HorizontalGravity.LEFT, 0, 0);
        }
    }

    private void getAllCityData() {
        new MyHttp().doPost(Api.getDefault().getCityList(), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                lstCity = JSONArray.parseArray(result.getString("data"), LocationInfo.class);
                initAllView();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    //初始化 所有城市的View
    private void initAllView() {
        final TextView tvLocationCityName = easyPopup.getView(R.id.tv_city_name);
        if (mLocationInfo != null) {
            tvLocationCityName.setText(mLocationInfo.getName());
        }
        easyPopup.getView(R.id.ll_location_city).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCity.setText(tvLocationCityName.getText().toString());
                easyPopup.dismiss();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        RecyclerView cityRecyclerView = easyPopup.getView(R.id.city_recycle_view);
        cityRecyclerView.setLayoutManager(linearLayoutManager);
        cityRecyclerView.addItemDecoration(new SuspensionDecoration(this, lstCity)
                .setmTitleHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, getResources().getDisplayMetrics()))
                .setColorTitleBg(0xffefefef)
                .setTitleFontSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()))
                .setColorTitleFont(mContext.getResources().getColor(android.R.color.black)));
        cityRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        CityAdapter cityAdapter = new CityAdapter(lstCity, mActivity);
        cityRecyclerView.setAdapter(cityAdapter);
        cityAdapter.setOnItemClickListener(R.id.root_view, new BaseAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                tvCity.setText(lstCity.get(position).getName());
                easyPopup.dismiss();
            }
        });
        IndexBar mIndexBar = easyPopup.getView(R.id.indexBar);
        TextView mSideBarHintTv = easyPopup.getView(R.id.tvSideBarHint);
        mIndexBar.setmPressedShowTextView(mSideBarHintTv)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(linearLayoutManager);//设置RecyclerView的LayoutManager
        mIndexBar.setmSourceDatas(lstCity)//设置数据
                .invalidate();
        easyPopup.showAtAnchorView(llSearch, VerticalGravity.BELOW, HorizontalGravity.LEFT, 0, 0);
    }

    @OnClick({R.id.tv_city, R.id.cover, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_city:
                showAllCityPop();
                break;
            case R.id.cover:
                etSearchAddress.clearFocus();
                KeyBordUtil.hideSoftKeyboard(etSearchAddress);
                break;
            case R.id.tv_search:
                if (etSearchAddress.getText().toString().length() > 0) {
                    recyclerViewSearch.setVisibility(View.VISIBLE);
                    search(etSearchAddress.getText().toString(), tvCity.getText().toString(), null);
                } else {
                    ToastUitl.showShort("请输入关键字");
                }
                break;
        }
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}
