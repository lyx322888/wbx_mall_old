package com.wbx.mall.base;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDexApplication;

import com.bugtags.library.Bugtags;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.UserDao;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.wbx.mall.BuildConfig;
import com.wbx.mall.R;
import com.wbx.mall.activity.ChatActivity;
import com.wbx.mall.api.ApiConstants;
import com.wbx.mall.bean.UserInfo;
import com.wbx.mall.chat.BaseManager;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.List;

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
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
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
//        LeakCanary.install(this);
        initJPush();
        initHxChat();
        initLog();
        //        Bugtags.start("af6d006285b8c34fe7d70792f7e7c27f", this, Bugtags.BTGInvocationEventBubble);
        Bugtags.start("af6d006285b8c34fe7d70792f7e7c27f", this, ApiConstants.DEBUG ? Bugtags.BTGInvocationEventBubble : Bugtags.BTGInvocationEventNone);
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

    private void initHxChat() {
        EaseUI instance = EaseUI.getInstance();

        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        options.setAutoLogin(true);
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
        if (processAppName == null || !processAppName.equalsIgnoreCase(this.getPackageName())) {
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        //初始化
        instance.init(this, options);
        instance.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                EaseUser easeUser = new EaseUser(username);

//
//                if(null!=userInfo){
//                    easeUser.setAvatar(userInfo.getFace());
//                    easeUser.setNickname(userInfo.getNickname());
//                }
                return getUserInfo(username);
            }
        });
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(false);

        EaseUI.getInstance().getNotifier().setNotificationInfoProvider(new EaseNotifier.EaseNotificationInfoProvider() {
            @Override
            public String getDisplayedText(EMMessage message) {
                String ticker = EaseCommonUtils.getMessageDigest(message, BaseApplication.instance);
                if (message.getType() == EMMessage.Type.TXT) {
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                }
                EaseUser user = getUserInfo(message.getFrom());
                if (user != null) {
                    return getUserInfo(message.getFrom()).getNick() + ": " + ticker;
                } else {
                    return message.getFrom() + ": " + ticker;
                }
            }

            @Override
            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
                return String.format("用户%s发来%d条消息，请注意查收！", getUserInfo(message.getFrom()), messageNum);
            }

            @Override
            public String getTitle(EMMessage message) {
                return null;
            }

            @Override
            public int getSmallIcon(EMMessage message) {
                return R.mipmap.app_logo;
            }

            @Override
            public Intent getLaunchIntent(EMMessage message) {
                Intent intent = new Intent(BaseApplication.instance, ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, message.getFrom());
                return intent;
            }
        });


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

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    private EaseUser getUserInfo(String username) {
        EaseUser easeUser = new EaseUser(username);
        UserInfo userInfo = (UserInfo) readObject(AppConfig.USER_DATA);
        if (userInfo != null && username.equals(userInfo.getHx_username())) {
            easeUser.setNickname(userInfo.getNickname());
            easeUser.setAvatar(userInfo.getFace());
        } else {
            UserDao userDao = new UserDao(getApplicationContext());
            userDao.openDataBase();
            easeUser = userDao.queryData(username);
            userDao.closeDataBase();
        }
        return easeUser;
    }
}
