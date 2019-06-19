package com.wbx.mall.common;

import android.content.Intent;

import com.wbx.mall.activity.LoginActivity;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseApplication;
import com.wbx.mall.bean.UserInfo;
import com.wbx.mall.utils.SPUtils;

/**
 * @author Zero
 * @date 2018/5/18 0018
 */
public class LoginUtil {
    public static String getLoginToken() {
        return SPUtils.getSharedStringData(ActivityManager.getTopActivity(), AppConfig.LOGIN_TOKEN);
    }

    public static UserInfo getUserInfo() {
        return (UserInfo) BaseApplication.getInstance().readObject(AppConfig.USER_DATA);
    }

    public static boolean isLogin() {
        return SPUtils.getSharedBooleanData(ActivityManager.getTopActivity(), AppConfig.LOGIN_STATE);
    }

    public static void login() {
        Intent intent = new Intent(ActivityManager.getTopActivity(), LoginActivity.class);
        ActivityManager.getTopActivity().startActivity(intent);
    }
}
