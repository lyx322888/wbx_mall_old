package com.wbx.mall.api;

import android.content.Intent;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.activity.LoginActivity;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseApplication;
import com.wbx.mall.common.ActivityManager;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.utils.ToastUitl;
import com.wbx.mall.widget.LoadingDialog;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wushenghui on 2017/6/20.
 */

public class MyHttp {
    private int opNum;

    public MyHttp() {
    }

    public void doPost(Observable<JSONObject> observable, final HttpListener listener) {

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<JSONObject>() {
            @Override
            public void onCompleted() {
                LoadingDialog.cancelDialogForLoading();

            }

            @Override
            public void onError(Throwable e) {
                LoadingDialog.cancelDialogForLoading();
//                if (!NetWorkUtils.isNetConnected(BaseApplication.getInstance())) {
//                    //网络
//                    listener.onError(AppConfig.ERROR_STATE.NO_NETWORK);
//                } else {
//                    //服务器
//                    listener.onError(AppConfig.ERROR_STATE.SERVICE_ERROR);
//                    ToastUitl.showShort("网络错误，请重试！");
//                }
            }

            @Override
            public void onNext(JSONObject result) {

                if (result.getInteger("state") == 1) {
                    listener.onSuccess(result);
                } else {
                    if (result.getString("msg").equals("暂无数据")) {
                        //无数据
                        listener.onError(AppConfig.ERROR_STATE.NULLDATA);
                    } else if (result.getString("msg").equals("请先登陆")) {
                        opNum++;
                        if (opNum == 1) {
                            ToastUitl.showShort("请先登录");
                            SPUtils.setSharedBooleanData(BaseApplication.getInstance(), AppConfig.LOGIN_STATE, false);
                            SPUtils.setSharedStringData(BaseApplication.getInstance(), AppConfig.LOGIN_TOKEN, "");
                            ActivityManager.getTopActivity().startActivity(new Intent(ActivityManager.getTopActivity(), LoginActivity.class));
                            ActivityManager.finishAllExpectSpecifiedActivity(LoginActivity.class);
                        }
                    } else if (result.getString("msg").equals("未设置支付密码")) {
                        listener.onError(AppConfig.ERROR_STATE.NULL_PAY_PSW);
                    } else if (result.getString("msg").equals("请先绑定手机")) {
                        listener.onError(AppConfig.ERROR_STATE.NO_BIND_PHONE);
                    } else if (result.getString("msg").equals("商品金额不足30元")) {
                        listener.onError(AppConfig.ERROR_STATE.NO_30);
                    } else if (result.getString("msg").equals("奖励补贴已关闭")) {
                        listener.onError(AppConfig.ERROR_STATE.CLOSE_REWARD);
                    } else if (result.getString("msg").equals("失败")) {//购物车增加
                        listener.onError(AppConfig.ERROR_STATE.CLOSE_REWARD);
                    } else if (result.getString("msg").equals("该手机号不是星合伙人")) {
                        listener.onError(AppConfig.ERROR_STATE.NO_STAR_MAN);
                    } else {
                        ToastUitl.showShort(result.getString("msg"));
                        listener.onError(result.getInteger("state"));
                    }
                }
            }
        });
    }
}
