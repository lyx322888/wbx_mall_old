package com.wbx.mall.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseApplication;
import com.wbx.mall.bean.LocationInfo;
import com.wbx.mall.common.ActivityManager;
import com.wbx.mall.widget.iosdialog.AlertDialog;

import java.util.List;

/**
 * Created by wushenghui on 2018/1/16.
 * 定位服务
 */

public class LocationService extends Service implements AMapLocationListener {
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明AMapLocationClient类对象
    public AMapLocationClient mlocationClient = null;
    private LocationInfo mLocationInfo;
    private List<LocationInfo> lstCity;
    private String cityList;
    private LocationBinder locationBinder;

    @Override
    public void onCreate() {
        super.onCreate();
       /* cityList = (String) BaseApplication.getInstance().readObject(AppConfig.CITY_LIST);
        if (!TextUtils.isEmpty(cityList)) {
            lstCity = JSONArray.parseArray(cityList, LocationInfo.class);
            startLocation();
        } else {
            getAllCity();
        }*/
    }


    private void getAllCity() {
        new MyHttp().doPost(Api.getDefault().getAllCityList(), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                BaseApplication.getInstance().saveObject(result.getString("data"), AppConfig.CITY_LIST);
                lstCity = JSONArray.parseArray(result.getString("data"), LocationInfo.class);
                startLocation();
            }

            @Override
            public void onError(int code) {

            }
        });

    }


    @Override
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
        return super.bindService(service, conn, flags);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        cityList = (String) BaseApplication.getInstance().readObject(AppConfig.CITY_LIST);
        if (!TextUtils.isEmpty(cityList)) {
            lstCity = JSONArray.parseArray(cityList, LocationInfo.class);
            startLocation();
        } else {
            getAllCity();
        }
        return super.onStartCommand(intent, flags, startId);

    }

    private void startLocation() {
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        if (locationBinder==null)
        {
            locationBinder = new LocationBinder();
        }
        return locationBinder;
    }

    @Override
    public void onLocationChanged(final AMapLocation aMapLocation) {
        if (aMapLocation.getErrorCode() != AMapLocation.LOCATION_SUCCESS) {
            new AlertDialog(ActivityManager.getTopActivity()).builder()
                    .setTitle("提示")
                    .setMsg("抱歉,定位失败,使用默认地址。\n开启定位权限--->授权管理--->应用权限管理--->定位权限开启")
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LocationInfo locationInfo = new LocationInfo();
                            locationInfo.setName("厦门");
                            locationInfo.setAddressName("厦门市");
                            locationInfo.setCity_id(19);
                            locationInfo.setSelectCityId(19);
                            locationInfo.setSelectCityName("厦门");
                            locationInfo.setLat(24.485271);
                            locationInfo.setLng(118.197294);
                            BaseApplication.getInstance().saveObject(locationInfo, AppConfig.LOCATION_DATA);
                            sendBroadcast();
                        }
                    }).show();
        } else {
            final LocationInfo locationInfo = new LocationInfo();
            locationInfo.setName(aMapLocation.getCity().replace("市", ""));
            locationInfo.setAddressName(aMapLocation.getPoiName());
            locationInfo.setLat(aMapLocation.getLatitude());
            locationInfo.setLng(aMapLocation.getLongitude());
            BaseApplication.getInstance().saveObject(locationInfo, AppConfig.LOCATION_DATA);
            for (LocationInfo info : lstCity) {
                if (locationInfo.getName().equals(info.getName())) {
                    locationInfo.setCity_id(info.getCity_id());
                    break;
                }
            }
            LocationInfo lastLocationInfo = (LocationInfo) BaseApplication.getInstance().readObject(AppConfig.LOCATION_DATA);
            if (lastLocationInfo != null && !lastLocationInfo.getName().equals(locationInfo.getName())) {
                new AlertDialog(ActivityManager.getTopActivity()).builder().setTitle("温馨提示").setMsg(String.format("你当前城市是%s,是否切换到当前位置", locationInfo.getName()))
                        .setNegativeButton("不切换", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendBroadcast();
                            }
                        }).setPositiveButton("切换", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BaseApplication.getInstance().saveObject(locationInfo, AppConfig.LOCATION_DATA);
                        sendBroadcast();
                    }
                }).setCancelable(false).show();
            } else {
                BaseApplication.getInstance().saveObject(locationInfo, AppConfig.LOCATION_DATA);
                sendBroadcast();
            }
        }
    }

    private void sendBroadcast() {
        Intent intent = new Intent();
        intent.setAction("refreshHasLocation");
        sendBroadcast(intent);
    }


    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }


    private boolean isContains(List<LocationInfo> locationInfoList, String city) {
        for (LocationInfo l : locationInfoList) {
            if (city.contains(l.getName())) {
                return true;
            }
        }
        return false;
    }


    public class LocationBinder extends Binder {

        public LocationService getService() {
            cityList = (String) BaseApplication.getInstance().readObject(AppConfig.CITY_LIST);
            if (!TextUtils.isEmpty(cityList)) {
                lstCity = JSONArray.parseArray(cityList, LocationInfo.class);
                startLocation();
            } else {
                getAllCity();
            }
            return LocationService.this;

        }
    }


}
