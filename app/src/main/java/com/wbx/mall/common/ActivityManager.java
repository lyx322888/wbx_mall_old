package com.wbx.mall.common;

import android.app.Activity;
import android.os.Process;

import java.util.Stack;

/**
 * Activity管理工具
 *
 * @author Zero
 * @date 2017/2/10
 */
public class ActivityManager {
    private static Stack<Activity> lstActivity = new Stack<>();

    public static void addActivity(Activity activity) {
        lstActivity.add(activity);
    }

    public static void removeActivity(Activity activity) {
        lstActivity.remove(activity);
    }

    public static Activity getTopActivity() {
        if (lstActivity.size() > 0) {
            return lstActivity.peek();
        }
        return null;
    }

    public static Stack<Activity> getAllActivity() {
        return lstActivity;
    }

    public static void finishAllActivity() {
        for (Activity activity : lstActivity) {
            activity.finish();
        }
        lstActivity.clear();
    }

    /**
     * 结束所有除了指定外的activity
     *
     * @param cls
     */
    public static void finishAllExpectSpecifiedActivity(Class<?> cls) {
        for (Activity activity : lstActivity) {
            if (!activity.getClass().equals(cls)) {
                activity.finish();
            }
        }
    }

    public static void exit() {
        if (lstActivity.size() > 0) {
            for (Activity activity : lstActivity) {
                activity.finish();
            }
        }
        Process.killProcess(Process.myPid());
    }
}
