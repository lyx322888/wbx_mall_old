package com.wbx.mall.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wbx.mall.bean.LocationInfo;
import com.wbx.mall.bean.UserInfo;
import com.wbx.mall.utils.ToastUitl;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {
    protected View rootView;
    protected UserInfo loginUser;
    protected LocationInfo mLocationInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutResource(), container, false);
        }
        ButterKnife.bind(this, rootView);
        initLocation();
        loginUser = (UserInfo) BaseApplication.getInstance().readObject(AppConfig.USER_DATA);
        initPresenter();
        initView();
        fillData();
        bindEvent();
        return rootView;
    }

    private void initLocation() {
        mLocationInfo = (LocationInfo) BaseApplication.getInstance().readObject(AppConfig.LOCATION_DATA);
        if (mLocationInfo == null) {
            mLocationInfo = new LocationInfo();
            mLocationInfo.setName("厦门");
            mLocationInfo.setCity_id(19);
            mLocationInfo.setSelectCityId(19);
            mLocationInfo.setSelectCityName("厦门");
            mLocationInfo.setLat(24.485271);
            mLocationInfo.setLng(24.485271);
        }
    }
    //获取布局文件
    protected abstract int getLayoutResource();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    //初始化view
    protected abstract void initView();

    //获取数据
    protected abstract void fillData();

    //绑定事件
    protected abstract void bindEvent();

    @Override
    public void onResume() {
        super.onResume();
        mLocationInfo = (LocationInfo) BaseApplication.getInstance().readObject(AppConfig.LOCATION_DATA);
    }

    /**
     * 短暂显示Toast提示(来自String)
     **/
    public void showShortToast(String text) {
        ToastUitl.showShort(text);
    }

    /**
     * 长时间显示Toast提示(来自String)
     **/
    public void showLongToast(String text) {
        ToastUitl.showLong(text);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
