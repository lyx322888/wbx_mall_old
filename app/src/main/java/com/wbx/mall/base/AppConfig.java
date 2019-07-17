package com.wbx.mall.base;


/**
 * ************************************************************************
 * **                              _oo0oo_                               **
 * **                             o8888888o                              **
 * **                             88" . "88                              **
 * **                             (| -_- |)                              **
 * **                             0\  =  /0                              **
 * **                           ___/'---'\___                            **
 * **                        .' \\\|     |// '.                          **
 * **                       / \\\|||  :  |||// \\                        **
 * **                      / _ ||||| -:- |||||- \\                       **
 * **                      | |  \\\\  -  /// |   |                       **
 * **                      | \_|  ''\---/''  |_/ |                       **
 * **                      \  .-\__  '-'  __/-.  /                       **
 * **                    ___'. .'  /--.--\  '. .'___                     **
 * **                 ."" '<  '.___\_<|>_/___.' >'  "".                  **
 * **                | | : '-  \'.;'\ _ /';.'/ - ' : | |                 **
 * **                \  \ '_.   \_ __\ /__ _/   .-' /  /                 **
 * **            ====='-.____'.___ \_____/___.-'____.-'=====             **
 * **                              '=---='                               **
 * ************************************************************************
 * **                        佛祖保佑      镇类之宝                         **
 * ************************************************************************
 */
public class AppConfig {
    /**
     * apk 文件存储路径
     */
    public static final String SP_DOWNLOAD_PATH = "download.path";
    public static final String WX_APP_ID = "wx446e462418664fa0";//  wx07867153bc1d88fa
    public static final String WX_APP_SECRET = "d4e18fd38227f81749ee509efa8ac13f";
    public static final String IMAGES = "http://imgs.wbx365.com/";
    public static final String LOGIN_STATE = "loginState";//登录状态
    public static final String LOCATION_DATA = "location";//保存定位信息
    public static final String CITY_LIST = "city_list";//城市列表
    public static final String USER_DATA = "user";//保存用户信息
    public static final String LOGIN_TOKEN = "token";//token
    public static final int TAKE_PHOTO_CODE = 10066;
    public static final int MAX_COUNT = 6;
    public static final int REQUEST_CONTACT = 10003;
    public static final int PERMISSIONS_CODE = 10004;
    public static final int pageNum = 1;
    public static final int pageSize = 10;

    public interface CollectionType {
        int GOODS = 0;
        int STORE = 1;
    }

    public interface StoreGrade {
        int STORE = 16;//注册店铺
        int MARKET = 15;//菜市场
        int EVINE = 32;//实体店
    }

    public interface StoreType {
        int VEGETABLE_MARKET = 15;//菜市场
        int PHYSICAL_STORE = 19;//实体店
        int FOOD_STREET = 20;//美食街
    }

    public interface addressIsDefault {
        int isDefault = 1;
        int unDefault = 0;
    }

    //搜索type
    public interface SearchType {
        int GOODS = 1;
        int STORE = 2;
    }

    /**
     * 付款类型
     */
    public interface PayType {
        String money = "money";//余额支付
        String wxpay = "weixinapp";//微信支付
        String alipay = "alipayapp";//支付宝
    }

    /**
     * 提现类型
     */
    public interface WidthdrawType {
        String wxpay = "weixinpay";//微信支付
        String alipay = "alipay";//支付宝
        String bank = "bank";//银行卡
    }

    /**
     * 提现种类
     */
    public enum CashType {
        money,
        deposit,
        operation_money,
        recommend_bonus
    }

    public static int getCateId(int position) {
        int stateInt = 164;
        switch (position) {
            case 0:
                stateInt = 164;
            case 1:
                stateInt = 166;
            case 2:
                stateInt = 167;
            case 3:
                stateInt = 168;
            case 4:
                stateInt = 169;
            case 5:
                stateInt = 170;
            case 6:
                stateInt = 171;
            case 7:
                stateInt = 172;
            case 8:
                stateInt = 173;
        }
        return stateInt;
    }

    public interface ERROR_STATE {
        int NULLDATA = 1001;//无数据
        int NO_NETWORK = 1002;//网络错误
        int SERVICE_ERROR = 1003;//加载错误
        int NULL_PAY_PSW = 1005;//未设置支付密码
        int NO_BIND_PHONE = 1006;//未绑定手机
        int NO_30 = 1007;//不足30元不能刮奖
        int CLOSE_REWARD = 1008;//奖励补贴已关闭
        /**
         * 该手机号不是星合伙人
         */
        int NO_STAR_MAN = 1009;
        int ACTIVITY_FINISH = 1010;//免单活动已结束
        int ALREADY_FOLLOW = 1011;//店铺已关注
    }

    public interface BOOK_SEAT_STYLE {
        int NOW = 2;//立即点菜
        int TO = 1;//到店点菜
    }
}
