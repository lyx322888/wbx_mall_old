package com.wbx.mall.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDexApplication;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;
import com.wbx.mall.BuildConfig;
import com.wbx.mall.chat.BaseManager;
import com.wbx.mall.utils.APPUtil;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by wushenghui on 2017/6/20.
 */

public class BaseApplication extends MultiDexApplication {
    private static BaseApplication instance;
    private static long appInitTime = System.currentTimeMillis();

    //单例模式中获取唯一的MyApplication实例
    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
    }

    private void init() {
        ThreadPoolManager.execute(new Runnable() {
            @Override
            public void run() {
                BaseManager.initOpenHelper(instance);
                strictMode();
            }
        });
        initInUiThread();
    }

    private void initInUiThread() {
        initJPush();
        initLog();
        initBugly();
    }

    private void initBugly() {
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = APPUtil.getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        strategy.setAppReportDelay(20000);
        // 初始化Bugly
        CrashReport.initCrashReport(context, "77a74b0ab9", true, strategy);
    }

    public long getAppInitTime() {
        return appInitTime;
    }

    private void initLog() {
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.IS_DEBUG;
            }
        });
    }

    private void strictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }
    }

    //初始化极光
    private void initJPush() {
        JPushInterface.setDebugMode(BuildConfig.IS_DEBUG);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
    }

    public String getVersion() {
        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info.versionName;
    }

    /**
     * 读取保存的登陆用户
     *
     * @return
     */
    public Object readObject(String key) {
        SharedPreferences preferences = getSharedPreferences("base64", MODE_PRIVATE);
        String productBase64 = preferences.getString(key, "");
        if (productBase64 == "") {
            return null;
        }

        // 读取字节
        byte[] base64 = Base64.decodeBase64(productBase64.getBytes());

        // 封装到字节流
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        try {
            // 再次封装
            ObjectInputStream bis = new ObjectInputStream(bais);
            // 读取对象
            return bis.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存的登陆用户
     *
     * @return
     */
    public void saveObject(Object object, String key) {
        SharedPreferences preferences = getSharedPreferences("base64", MODE_PRIVATE);

        // 创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 创建对象输出流，并封装字节流
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            // 将对象写入字节流
            oos.writeObject(object);

            // 将字节流编码成base64的字符窜
            String productBase64 = new String(Base64.encodeBase64(baos.toByteArray()));

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, productBase64);

            editor.commit();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
