package com.wbx.mall.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bugtags.library.Bugtags;
import com.wbx.mall.activity.SplashActivity;
import com.wbx.mall.bean.LocationInfo;
import com.wbx.mall.bean.UserInfo;
import com.wbx.mall.common.ActivityManager;
import com.wbx.mall.utils.ToastUitl;
import com.wbx.mall.widget.LoadingDialog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by wushenghui on 2017/4/23.
 * <p>
 * <p>
 * mvp
 * <p>
 * public class MainActivity extends BaseActivity<TestPresenter,TestModel> implements TestContract.View{
 *
 * @Override public int getLayoutId() {
 * return R.layout.activity_main;
 * }
 * @Override public void initPresenter() {
 * mPresenter.setVM(this,mModel);
 * }
 * @Override public void initView() {
 * findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
 * @Override public void onClick(View view) {
 * mPresenter.getAppIdRequest("1dc2f0ac6f94c439560d0fe6246ca4ad");
 * }
 * });
 * <p>
 * <p>
 * }
 * @Override public void dataSuccess(DeviceInfo result) {
 * showShortToast("appId::::::"+result.getAppId());
 * }
 */

public abstract class BaseActivity extends AppCompatActivity {
    public Context mContext;
    protected Activity mActivity;
    protected UserInfo userInfo;
    protected Bundle mSavedInstanceState;
    protected LocationInfo mLocationInfo;
    private HashMap<String, CompositeSubscription> mSubscriptionMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSavedInstanceState = savedInstanceState;
        ActivityManager.addActivity(this);
        userInfo = (UserInfo) BaseApplication.getInstance().readObject(AppConfig.USER_DATA);
        initLocation();
        mActivity = this;
        doBeforeSetcontentView();
        setContentView(getLayoutId());
        if (this instanceof SplashActivity && avoidRenewLaunchActivity()) {
            return;
        }
        ButterKnife.bind(this);
        mContext = this;
        this.initPresenter();
        this.initView();
        this.fillData();
        this.setListener();
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
            mLocationInfo.setLng(118.197294);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        userInfo = (UserInfo) BaseApplication.getInstance().readObject(AppConfig.USER_DATA);
        Bugtags.onResume(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        Bugtags.onDispatchTouchEvent(this, ev);
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Bugtags.onPause(this);
    }

    /**
     * 设置layout前配置
     */
    private void doBeforeSetcontentView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 默认着色状态栏
        SetStatusBarColor();
    }


    /*********************子类实现*****************************/
    //获取布局文件
    public abstract int getLayoutId();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    //初始化view
    public abstract void initView();

    //加载网络数据
    public abstract void fillData();

    //事件
    public abstract void setListener();

    /**
     * 状态栏字亮色
     */
    protected void SetStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (MIUISetStatusBarLightMode(getWindow(), true)) {
            } else if (FlymeSetStatusBarLightMode(getWindow(), true)) {
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 短暂显示Toast提示(来自String)
     **/
    public void showShortToast(String text) {
        ToastUitl.showShort(text);
    }

    public void back(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoadingDialog.cancelDialogForLoading();
        ActivityManager.removeActivity(this);

    }

    /**
     * 避免首次安装点击打开启动程序后，按home键返回桌面然后点击桌面图标会重新实例化入口类的activity，原因参考http://www.cnblogs.com/net168/p/5722752.html
     */
    private boolean avoidRenewLaunchActivity() {
        if (!this.isTaskRoot()) {
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                    finish();
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 保存订阅后的subscription
     *
     * @param o
     * @param subscription
     */
    public void addSubscription(Object o, Subscription subscription) {
        if (mSubscriptionMap == null) {
            mSubscriptionMap = new HashMap<>();
        }
        String key = o.getClass().getName();
        if (mSubscriptionMap.get(key) != null) {
            mSubscriptionMap.get(key).add(subscription);
        } else {
            CompositeSubscription compositeSubscription = new CompositeSubscription();
            compositeSubscription.add(subscription);
            mSubscriptionMap.put(key, compositeSubscription);
        }
    }
    /**
     * 取消订阅
     *
     * @param o
     */
    public void unSubscribe(Object o) {
        if (mSubscriptionMap == null) {
            return;
        }

        String key = o.getClass().getName();
        if (!mSubscriptionMap.containsKey(key)) {
            return;
        }
        if (mSubscriptionMap.get(key) != null) {
            mSubscriptionMap.get(key).unsubscribe();
        }

        mSubscriptionMap.remove(key);
    }
}
