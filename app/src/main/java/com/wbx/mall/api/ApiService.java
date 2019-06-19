package com.wbx.mall.api;

import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.bean.NewFreeInfoBean;

import java.util.Map;

import retrofit2.adapter.rxjava.Result;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * des:ApiService
 * Created by wushenghui on 2017/4/23.
 */
public interface ApiService {
    //获取版本号
    @POST("/api/index/get_version_info")
    Observable<JSONObject> getVersion();

//    //2.5.轮循获取首页数据
//    @POST("/api/index/index_count_data")
//    Observable<JSONObject> getIndexCountData();

    //注册
    @FormUrlEncoded
    @POST("/api/user/register")
    Observable<JSONObject> register(@FieldMap Map<String, Object> params);

    //登录
    @FormUrlEncoded
    @POST("/api/user/login")
    Observable<JSONObject> login(@FieldMap Map<String, Object> params);

    //短信登录
    @FormUrlEncoded
    @POST("/api/user/sms_code_login")
    Observable<JSONObject> loginByCode(@Field("account") String account, @Field("code") String code, @Field("app_type") String type, @Field("version") String version, @Field("registration_id") String registration_id, @Field("phone_type") String phone_type);

    //退出
    @FormUrlEncoded
    @POST("/api/user/logout")
    Observable<JSONObject> logout(@Field("login_token") String loginToken, @Field("type") String type, @Field("registration_id") String registration_id);

    //发送验证码
    @FormUrlEncoded
    @POST("/api/user/sendsms")
    Observable<JSONObject> sendSMSCode(@Field("mobile") String mobile);

    //忘记密码
    @FormUrlEncoded
    @POST("/api/user/forget_password")
    Observable<JSONObject> forgetPassword(@Field("mobile") String mobile, @Field("code") String code, @Field("new_password") String password);

    //获取用户信息
    @FormUrlEncoded
    @POST("/api/user/get_user_info")
    Observable<JSONObject> getUserInfo(@Field("login_token") String loginToken);

    //城市列表
    @POST("/api/index/list_city")
    Observable<JSONObject> getCityList();

    //城市列表
    @POST("/api/index/list_all_city")
    Observable<JSONObject> getAllCityList();

    //评论列表
    @FormUrlEncoded
    @POST("/api/userorder/list_assess")
    Observable<JSONObject> getCommentList(@FieldMap Map<String, Object> params);

    //获取附近店铺列表
    @FormUrlEncoded
    @POST("/api/shop/list_shop")
    Observable<JSONObject> getNearByShopList(@FieldMap Map<String, Object> params);

    //获取菜市场店铺列表
    @FormUrlEncoded
    @POST("/api/buygreens/list_shop")
    Observable<JSONObject> getBuyShopList(@FieldMap Map<String, Object> params);

    //获取首页店铺信息
    @FormUrlEncoded
    @POST("/api/index/list_shop")
    Observable<JSONObject> getIndexShopInfo(@FieldMap Map<String, Object> params);

    //获取首页新店铺信息
    @FormUrlEncoded
    @POST("/api/index/list_shop_v2")
    Observable<JSONObject> getIndexShopInfo2(@FieldMap Map<String, Object> params);

    //店铺详情（菜市场）
    @FormUrlEncoded
    @POST("/api/buygreens/get_goods")
    Observable<JSONObject> getVegetableShopInfo(@Field("shop_id") String shop_id, @Field("login_token") String login_token, @Field("goods_id") String goods_id);

    //店铺详情（实体店）
    @FormUrlEncoded
    @POST("/api/shop/get_goods")
    Observable<JSONObject> getShopInfo(@Field("shop_id") String shop_id, @Field("login_token") String login_token, @Field("goods_id") String goods_id);

    //店铺详情（实体店）
    @FormUrlEncoded
    @POST("/api/unifyshop/list_goods")
    Observable<JSONObject> getShopInfo2(@Field("shop_id") String shop_id, @Field("login_token") String login_token, @Field("page") String page, @Field("num") String num);

    /**
     * 订单操作接口
     **/

    //订单详情
    @FormUrlEncoded
    @POST("/api/userorder/get_order_detail")
    Observable<JSONObject> getOrderDetail(@Field("login_token") String login_token, @Field("order_id") String order_id, @Field("order_type") int order_type);

    //退款详情
    @FormUrlEncoded
    @POST("/api/userorder/list_refund_track")
    Observable<JSONObject> getRefundDetail(@Field("login_token") String login_token, @Field("order_id") String order_id, @Field("order_type") int order_type);

    //取消订单
    @FormUrlEncoded
    @POST("/api/userorder/cancel_order")
    Observable<JSONObject> cancelOrder(@Field("login_token") String login_token, @Field("order_id") String order_id, @Field("order_type") int order_type);

    //申请退款
    @FormUrlEncoded
    @POST("/api/userorder/refund_order")
    Observable<JSONObject> applyRefund(@Field("login_token") String login_token, @Field("order_id") String order_id, @Field("order_type") int order_type);

    //取消退款
    @FormUrlEncoded
    @POST("/api/userorder/cancel_order_refund")
    Observable<JSONObject> cancelRefund(@Field("login_token") String login_token, @Field("order_id") String order_id, @Field("order_type") int order_type);

    //确认收货
    @FormUrlEncoded
    @POST("/api/userorder/confirm_order")
    Observable<JSONObject> confirmReceive(@Field("login_token") String login_token, @Field("order_id") String order_id, @Field("order_type") int order_type);

    //删除订单
    @FormUrlEncoded
    @POST("/api/userorder/delete_order")
    Observable<JSONObject> deleteOrder(@Field("login_token") String login_token, @Field("order_id") String order_id, @Field("order_type") int order_type);

    //订单轨迹
    @FormUrlEncoded
    @POST("/api/userorder/list_order_track_two")
    Observable<JSONObject> getOrderTrack(@Field("login_token") String login_token, @Field("order_id") String order_id, @Field("order_type") int order_type);

    /**
     * 订单操作接口 END
     **/

    //申请售后
    @FormUrlEncoded
    @POST("/api/userorder/add_customer_service")
    Observable<JSONObject> applyService(@FieldMap Map<String, Object> params);

    //发表评论
    @FormUrlEncoded
    @POST("/api/userorder/add_assess")
    Observable<JSONObject> publishComment(@FieldMap Map<String, Object> params);

    //获取商品详情
    @FormUrlEncoded
    @POST("/api/shop/get_goods_detail")
    Observable<JSONObject> getGoodsDetail(@Field("goods_id") String goodsId);

    //下单 提交购物车 菜市场
    @FormUrlEncoded
    @POST("/api/buygreens/order")
    Observable<JSONObject> createVegetableOrder(@Field("login_token") String loginToken, @Field("cart_goods") String cartGoods);

    //下单 提交购物车 实体店
    @FormUrlEncoded
    @POST("/api/mall/order")
    Observable<JSONObject> createOrder(@Field("login_token") String loginToken, @Field("cart_goods") Object cartGoods, @Field("reserve_table_id") String bookId);

    //获取菜市场支付信息
    @FormUrlEncoded
    @POST("/api/buygreens/get_pay_info")
    Observable<JSONObject> getPayInfo(@Field("login_token") String loginToken, @Field("order_id") String orderId);

    //获取实体店支付信息
    @FormUrlEncoded
    @POST("/api/mall/get_pay_info")
    Observable<JSONObject> getShopPayInfo(@Field("login_token") String loginToken, @Field("order_id") String orderId);

    //更新菜市场订单收货地址
    @FormUrlEncoded
    @POST("/api/buygreens/update_order_address")
    Observable<JSONObject> updateVegetableOrderAddress(@Field("login_token") String loginToken, @Field("order_id") String orderId, @Field("addr_id") int addr_id);

    //更新实体店订单收货地址
    @FormUrlEncoded
    @POST("/api/mall/update_order_address")
    Observable<JSONObject> updateOrderAddress(@Field("login_token") String loginToken, @Field("order_id") String orderId, @Field("addr_id") int addr_id);

    //收货地址列表
    @FormUrlEncoded
    @POST("/api/user/list_address")
    Observable<JSONObject> getAddressList(@Field("login_token") String loginToken);

    //设置默认地址
    @FormUrlEncoded
    @POST("/api/user/set_default_address")
    Observable<JSONObject> setDefaultAddress(@Field("login_token") String loginToken, @Field("id") int addressId);

    //删除收货地址
    @FormUrlEncoded
    @POST("/api/user/delete_address")
    Observable<JSONObject> deleteAddress(@Field("login_token") String loginToken, @Field("id") int addressId);

    //编辑收货地址
    @FormUrlEncoded
    @POST("/api/user/update_address")
    Observable<JSONObject> editAddress(@FieldMap Map<String, Object> params);

    //新增收货地址
    @FormUrlEncoded
    @POST("/api/user/add_address")
    Observable<JSONObject> addAddress(@FieldMap Map<String, Object> params);

    //首页搜索
    @FormUrlEncoded
    @POST("/api/index/search")
    Observable<JSONObject> indexSearch(@FieldMap Map<String, Object> params);

    //订单备注信息 菜市场
    @FormUrlEncoded
    @POST("/api/buygreens/message")
    Observable<JSONObject> addRemark(@Field("login_token") String loginToken, @Field("order_id") String orderId, @Field("message") String message);

    //菜市场免单商品下单
    @FormUrlEncoded
    @POST("/api/buygreens/order")
    Observable<JSONObject> createVegetableFreeAcitivityOrder(@Field("login_token") String loginToken, @Field("cart_goods") String cartGoods, @Field("activity_id") String activity_id);

    /**
     * 获取我的免单活动
     */
    @FormUrlEncoded
    @POST("/api/freegoods/list_free_goods")
    Observable<JSONObject> getMyFreeActivity(@Field("login_token") String loginToken, @Field("type") int type, @Field("page") int page, @Field("num") int num);

    /**
     * 获取我的积分免单商品
     */
    @FormUrlEncoded
    @POST("/api/accumulate/buy_goods_list")
    Observable<JSONObject> getMyAccumulateGoodsList(@Field("login_token") String loginToken, @Field("page") int page, @Field("num") int num);

    //订单备注信息 实体店
    @FormUrlEncoded
    @POST("/api/mall/message")
    Observable<JSONObject> addMallRemark(@Field("login_token") String loginToken, @Field("order_id") String orderId, @Field("message") String message);

    //支付菜市场
    @FormUrlEncoded
    @POST("/api/buygreens/pay")
    Observable<JSONObject> marketPayOrder(@FieldMap Map<String, Object> params);

    //支付实体店
    @FormUrlEncoded
    @POST("/api/mall/pay")
    Observable<JSONObject> shopPayOrder(@FieldMap Map<String, Object> params);

    //修改昵称
    @FormUrlEncoded
    @POST("/api/user/update_nickname")
    Observable<JSONObject> upDateNickName(@Field("login_token") String loginToken, @Field("nickname") String nickName);

    //修改支付密码
    @FormUrlEncoded
    @POST("/api/user/update_pay_password")
    Observable<JSONObject> resetPayPassword(@Field("login_token") String loginToken, @Field("code") String SMS, @Field("mobile") String mobile, @Field("new_pay_password") String newPsw);

    //修改密码
    @FormUrlEncoded
    @POST("/api/user/update_password")
    Observable<JSONObject> resetPassword(@Field("login_token") String loginToken, @Field("old_password") String oldPsw, @Field("new_password") String newPsw);

    @FormUrlEncoded
    @POST("/api/user/recharge")
    Observable<JSONObject> recharge(@Field("login_token") String loginToken, @Field("money") String money, @Field("code") String code);

    //代金券充值
    @FormUrlEncoded
    @POST("/api/user/card_recharge")
    Observable<JSONObject> cardRecharge(@Field("login_token") String loginToken, @Field("card_key") String cardKey);

    // 获取代金券日志
    @FormUrlEncoded
    @POST("/api/user/list_recharge_card")
    Observable<JSONObject> getVoucherLogs(@FieldMap Map<String, Object> params);

    //申请提现
    @FormUrlEncoded
    @POST("/api/user/add_cash_apply_new")
    Observable<JSONObject> applyCash(@FieldMap Map<String, Object> params);

    //上传头像
    @FormUrlEncoded
    @POST("/api/user/update_face")
    Observable<JSONObject> updateFace(@Field("login_token") String loginToken, @Field("face") String faceUrl);

    //收藏商品
    @FormUrlEncoded
    @POST("/api/mall/favorites")
    Observable<JSONObject> collectProduct(@Field("login_token") String loginToken, @Field("goods_id") String productId);

    //获得地区及商圈
    @FormUrlEncoded
    @POST("/api/buygreens/get_area")
    Observable<JSONObject> getScreenArea(@Field("city_name") String cityName);

    //我的客户
    @FormUrlEncoded
    @POST("/api/user/list_my_client")
    Observable<JSONObject> myClient(@FieldMap Map<String, Object> params);

    //收藏的店铺
    @FormUrlEncoded
    @POST("/api/user/list_shop_favorites")
    Observable<JSONObject> getLikeStores(@FieldMap Map<String, Object> params);

    //收藏的商品
    @FormUrlEncoded
    @POST("/api/user/list_goods_favorites")
    Observable<JSONObject> getLikeGoods(@FieldMap Map<String, Object> params);

    //删除收藏商品
    @FormUrlEncoded
    @POST("/api/user/delete_goods_favorites")
    Observable<JSONObject> cancelCollectProduct(@Field("login_token") String loginToken, @Field("favorites_id") int favoritesId);

    //取消收藏的店铺
    @FormUrlEncoded
    @POST("/api/user/delete_shop_favorites")
    Observable<JSONObject> cancelLikeStores(@Field("login_token") String loginToken, @Field("shop_id") int shopId);

    //关注商家
    @FormUrlEncoded
    @POST("/api/index/follow_shop")
    Observable<JSONObject> followStore(@Field("shop_id") int shopId, @Field("login_token") String loginToken);

    //申请入驻
    @FormUrlEncoded
    @POST("/api/applyenter/add_apply_enter")
    Observable<JSONObject> applyJoin(@FieldMap Map<String, Object> params);

    //积分列表
    @FormUrlEncoded
    @POST("/api/user/list_integral_logs")
    Observable<JSONObject> getIntegralLogs(@Field("login_token") String loginToken, @Field("page") int page, @Field("num") int num);

    //积分商品详情
    @FormUrlEncoded
    @POST("/api/user/get_integral_goods_details")
    Observable<JSONObject> getIntegralGoodsDetail(@Field("login_token") String loginToken, @Field("goods_id") int goods_id);

    //获取默认地址
    @FormUrlEncoded
    @POST("/api/Integral/get_address")
    Observable<JSONObject> getDefaultAddress(@Field("login_token") String loginToken);

    //积分兑换
    @FormUrlEncoded
    @POST("/api/integral/exchange_goods")
    Observable<JSONObject> integralExchangeGoods(@Field("login_token") String loginToken, @Field("goods_id") int goods_id, @Field("num") int num, @Field("addr_id") int addr_id);

    //签到
    @FormUrlEncoded
    @POST("/api/user/sign_in")
    Observable<JSONObject> signIn(@Field("login_token") String loginToken);

    //积分商城 列表数据
    @FormUrlEncoded
    @POST("/api/integral/list_integral_goods")
    Observable<JSONObject> getIntegralGoods(@Field("login_token") String loginToken, @Field("page") int page, @Field("num") int num);

    //积分兑换列表及详情
    @FormUrlEncoded
    @POST("/api/integral/list_my_integral_goods")
    Observable<JSONObject> getIntegralGoodsList(@FieldMap Map<String, Object> params);

    //获取菜谱分类
    @POST("/api/cook/list_cook_class")
    Observable<JSONObject> getCookInfo();

    //菜谱搜索
    @FormUrlEncoded
    @POST("/api/cook/search_cook")
    Observable<JSONObject> searchCook(@FieldMap Map<String, Object> params);

    //根据id获取菜谱分类列表
    @FormUrlEncoded
    @POST("/api/cook/list_class_cook")
    Observable<JSONObject> searchCookForId(@FieldMap Map<String, Object> params);

    //星伙计划 获得支付信息
    @FormUrlEncoded
    @POST("/api/salesman/pay")
    Observable<JSONObject> getAgentPayInfo(@FieldMap Map<String, Object> params);

    //获取业务员信息
    @FormUrlEncoded
    @POST("/api/salesman/get_salesman_info")
    Observable<JSONObject> getAgentInfo(@FieldMap Map<String, Object> params);

    //获取业务员证书信息
    @FormUrlEncoded
    @POST("/api/salesman/get_certificate")
    Observable<JSONObject> getCertificateInfo(@Field("login_token") String loginToken);

    //领取优惠券
    @FormUrlEncoded
    @POST("/api/coupon/receive_coupon")
    Observable<JSONObject> rceiveCoupon(@Field("login_token") String loginToken, @Field("coupon_id") int couponId);

    //首页优惠券
    @FormUrlEncoded
    @POST("/api/index/list_index_coupon")
    Observable<JSONObject> getIndexCoupon(@Field("city_id") int cityId);

    //已绑客户
    @FormUrlEncoded
    @POST("/api/salesman/list_my_client")
    Observable<JSONObject> myCustomer(@Field("login_token") String loginToken);

    //添加支付宝信息
    @FormUrlEncoded
    @POST("/api/withdraw/add_alipay")
    Observable<JSONObject> addAliPay(@FieldMap Map<String, Object> params);

    //添加微信信息
    @FormUrlEncoded
    @POST("/api/withdraw/add_weixinpay")
    Observable<JSONObject> addWXPay(@FieldMap Map<String, Object> params);

    //获取支付宝账号信息
    @FormUrlEncoded
    @POST("/api/withdraw/get_alipay_info")
    Observable<JSONObject> getAliPayInfo(@Field("login_token") String loginToken);

    //获取微信账号信息
    @FormUrlEncoded
    @POST("/api/withdraw/get_weixinpay_info")
    Observable<JSONObject> getWXPayInfo(@Field("login_token") String loginToken);

    //获取提现申请时需要的信息
    @FormUrlEncoded
    @POST("api/user/get_cash_info")
    Observable<JSONObject> getBindPayInfo(@Field("login_token") String loginToken);

    //获取提现申请时需要的信息
    @FormUrlEncoded
    @POST("/api/withdraw/add_bank")
    Observable<JSONObject> addBank(@FieldMap Map<String, Object> params);

    //获取银行卡帐户信息
    @FormUrlEncoded
    @POST("/api/withdraw/get_bank_info")
    Observable<JSONObject> getBankInfo(@Field("login_token") String loginToken);

    // 获取余额日志
    @FormUrlEncoded
    @POST("/api/user/list_yue_logs")
    Observable<JSONObject> getBalanceLog(@FieldMap Map<String, Object> params);

    //星伙提现记录
    @FormUrlEncoded
    @POST("/api/user/list_cash_log")
    Observable<JSONObject> getCashLogList(@Field("login_token") String login_token, @Field("page") int page, @Field("num") int num);

    //预定座位
    @FormUrlEncoded
    @POST("/api/subscribe/append_seat")
    Observable<JSONObject> bookSeat(@FieldMap Map<String, Object> params);

    //获取预定座位信息包含支付信息
    @FormUrlEncoded
    @POST("/api/subscribe/get_pay_info")
    Observable<JSONObject> getBookInfoAndPayInfo(@Field("login_token") String loginToken, @Field("reserve_table_id") String bookId);

    @FormUrlEncoded
    @POST("/api/subscribe/pay")
    Observable<JSONObject> bookSeatPay(@FieldMap Map<String, Object> params);

    //预定座位列表
    @FormUrlEncoded
    @POST("/api/subscribe/list_subscribe")
    Observable<JSONObject> getBookSeatList(@FieldMap Map<String, Object> params);

    //取消预定
    @FormUrlEncoded
    @POST("/api/subscribe/cancel_subscribe")
    Observable<JSONObject> cancelBook(@Field("login_token") String loginToken, @Field("reserve_table_id") int bookId);

    //删除预定
    @FormUrlEncoded
    @POST("/api/subscribe/delete_subscribe")
    Observable<JSONObject> deleteBook(@Field("login_token") String loginToken, @Field("reserve_table_id") int bookId);

    //预定订单退款
    @FormUrlEncoded
    @POST("/api/subscribe/apply_refund")
    Observable<JSONObject> refundBook(@Field("login_token") String loginToken, @Field("reserve_table_id") int bookId);

    //根据关键词显示对应问题标题
    @FormUrlEncoded
    @POST("/api/airobot/list_question_title")
    Observable<JSONObject> getQuestion(@Field("keyword") String keyword);

    //常见问题
    @POST("/api/airobot/list_often_question")
    Observable<JSONObject> getOftenQuestion();

    //获取购物车数据
    @FormUrlEncoded
    @POST("/api/goodscart/get_cart")
    Observable<JSONObject> indexTrolleyInfo(@Field("login_token") String loginToken);

    //获取购物车数据
    @FormUrlEncoded
    @POST("/api/goodscart/get_cart")
    Observable<JSONObject> getCartInfo(@Field("login_token") String loginToken, @Field("shop_id") Object shopId);

    //添加购物车
    @FormUrlEncoded
    @POST("/api/goodscart/add_cart")
    Observable<JSONObject> addCart(@FieldMap Map<String, Object> params);

    //购物车增加商品数量
    @FormUrlEncoded
    @POST("/api/goodscart/add_goods_num")
    Observable<JSONObject> addCartNum(@Field("login_token") String loginToken, @Field("cart_id") Object cart_id);

    //购物车减少数量
    @FormUrlEncoded
    @POST("/api/goodscart/reduce_goods_num")
    Observable<JSONObject> subCartNum(@Field("login_token") String loginToken, @Field("cart_id") Object cart_id);

    //实体店免单商品下单
    @FormUrlEncoded
    @POST("/api/mall/order")
    Observable<JSONObject> createFreeActivityOrder(@Field("login_token") String loginToken, @Field("cart_goods") Object cartGoods, @Field("activity_id") String activity_id);

    //选择购物车商品
    @FormUrlEncoded
    @POST("/api/goodscart/secected_cart")
    Observable<JSONObject> selectCart(@Field("login_token") String loginToken, @Field("cart_id") Object cart_id, @Field("selected") Object selected);

    //全选 全不选购物车
    @FormUrlEncoded
    @POST("/api/goodscart/secected_cart_all")
    Observable<JSONObject> selectCartAll(@Field("login_token") String loginToken, @Field("shop_id") Object shopId, @Field("selected") Object selected);

    //删除购物车数据
    @FormUrlEncoded
    @POST("/api/goodscart/delete_cart")
    Observable<JSONObject> deleteCartInfo(@Field("login_token") String loginToken, @Field("cart_id") Object cartId);

    //删除店铺购物车数据
    @FormUrlEncoded
    @POST("/api/goodscart/delete_shop_cart")
    Observable<JSONObject> deleteShopCartInfo(@Field("login_token") String loginToken, @Field("shop_id") Object shopId);

    //获取随机补贴金额
    @FormUrlEncoded
    @POST("/api/subsidyincentive/get_subsidy_money")
    Observable<JSONObject> getRedPacket(@Field("login_token") String loginToken, @Field("order_id") String order_id, @Field("order_type") int order_type);

    //设置补贴金额
    @FormUrlEncoded
    @POST("/api/subsidyincentive/set_subsidy_money")
    Observable<JSONObject> setRedPacket(@Field("login_token") String loginToken, @Field("order_id") String order_id, @Field("order_type") int order_type);

    //获取补贴金额列表
    @FormUrlEncoded
    @POST("/api/subsidyincentive/list_subsidy_incentive")
    Observable<JSONObject> getRedPacketList(@Field("login_token") String loginToken);

    //微信登录
    @FormUrlEncoded
    @POST("api/user/wx_login")
    Observable<JSONObject> loginByWeChat(@Field("open_id") String open_id, @Field("union_id") String union_id, @Field("nickname") String nickname, @Field("face") String face,
                                         @Field("app_type") String app_type, @Field("version") String version, @Field("registration_id") String registration_id, @Field("phone_type") String phone_type);

    //绑定手机
    @FormUrlEncoded
    @POST("/api/user/wx_bind_mobile")
    Observable<JSONObject> bindPhone(@Field("open_id") String open_id, @Field("union_id") String union_id, @Field("nickname") String nickname, @Field("face") String face,
                                     @Field("app_type") String app_type, @Field("version") String version, @Field("registration_id") String registration_id,
                                     @Field("mobile") String mobile, @Field("code") String code, @Field("password") String password, @Field("phone_type") String phone_type);

    //智能支付
    @FormUrlEncoded
    @POST("/api/noopsychepay/pay")
    Observable<JSONObject> intelligentPay(@Field("login_token") String login_token, @Field("shop_id") String shop_id, @Field("money") String money, @Field("code") String code, @Field("pay_password") String pay_password);

    //智能支付列表
    @FormUrlEncoded
    @POST("api/noopsychepay/list_noopstchepay")
    Observable<JSONObject> getIntelligentPayList(@Field("login_token") String login_token, @Field("page") int page, @Field("num") int num);

    //星伙数据统计
    @FormUrlEncoded
    @POST("/api/salesman/count_salesman")
    Observable<JSONObject> getSalesManData(@Field("login_token") String login_token);

    /**
     * 判断是否是星合伙人
     */
    @FormUrlEncoded
    @POST("/api/salesman/get_rank")
    Observable<JSONObject> isStarMan(@Field("login_token") String login_token, @Field("phone") String phone);

    /**
     * 成为合伙人
     */
    @FormUrlEncoded
    @POST("/api/salesman/apply_salesman")
    Observable<JSONObject> tobeAgent(@Field("rank") int rank, @Field("name") String name, @Field("phone") String phone, @Field("identity_card_no") String identity_card_no
            , @Field("referee_account") String referee_account, @Field("city_id") String city_id, @Field("identity_card_front") String identity_card_front, @Field("identity_card_con") String identity_card_con, @Field("login_token") String login_token);

    /**
     * 电子签名图片提交
     */
    @FormUrlEncoded
    @POST("/api/salesman/add_sign_photo")
    Observable<JSONObject> addSignPhoto(@Field("login_token") String loginToken, @Field("sign_photo") String sign_photo);

    /**
     * 星伙审核
     */
    @FormUrlEncoded
    @POST("/api/salesman/check_salesman")
    Observable<JSONObject> checkSalesMan(@Field("login_token") String loginToken, @Field("rank") int rank);

    /**
     * 扫码订单列表
     */
    @FormUrlEncoded
    @POST("/api/scanorder/scan_order_list")
    Observable<JSONObject> getScanOrderList(@Field("login_token") String loginToken, @Field("page") int page, @Field("num") int num);

    /**
     * 获取系统消息
     */
    @FormUrlEncoded
    @POST("/api/user/list_system_message")
    Observable<JSONObject> getSystemMessageList(@Field("login_token") String loginToken);

    /**
     * 获取百度客服聊天链接
     */
    @FormUrlEncoded
    @POST("/api/airobot/get_baidutim_url")
    Observable<JSONObject> getBaiduTimUrl(@Field("type") int type);

    /**
     * 获取系统未读消数
     */
    @FormUrlEncoded
    @POST("/api/user/get_no_read_system_message")
    Observable<JSONObject> getUnreadSystemMessageNum(@Field("login_token") String loginToken);

    /**
     * 获取商家发现内容
     */
    @FormUrlEncoded
    @POST("/api/discovery/list_discover")
    Observable<JSONObject> getDiscoveryList(@Field("city_name") String cityName, @Field("lat") double lat, @Field("lng") double lng, @Field("login_token") String loginToken);

    /**
     * 获取随机红包
     */
    @FormUrlEncoded
    @POST("/api/redpacket/get_red_packet")
    Observable<JSONObject> getRandomRedPacket(@Field("login_token") String loginToken, @Field("shop_id") int shop_id);

    /**
     * 领取红包
     */
    @FormUrlEncoded
    @POST("/api/redpacket/receive_red_packet")
    Observable<JSONObject> receiveRedPacket(@Field("login_token") String loginToken, @Field("user_red_packet_id") String user_red_packet_id, @Field("shop_id") int shop_id);

    /**
     * 获取所有订单列表
     */
    @FormUrlEncoded
    @POST("/api/userorder/list_all_order")
    Observable<JSONObject> getAllOrder(@Field("login_token") String loginToken, @Field("status") int status, @Field("page") int page, @Field("num") int num);

    /**
     * 获取购物车数量
     */
    @FormUrlEncoded
    @POST("/api/goodscart/get_goods_cart_num")
    Observable<JSONObject> getShoppingCartGoodsNum(@Field("login_token") String loginToken);

    /**
     * 获取免单商品详情
     */
    @FormUrlEncoded
    @POST("/api/freegoods/get_free_goods_details")
    Observable<JSONObject> getFreeGoodsDetail(@Field("goods_id") String goods_id, @Field("grade_id") int grade_id, @Field("lat") double lat,
                                              @Field("lng") double lng, @Field("login_token") String loginToken);

    /**
     * 获取消费免单商品详情
     */
    @FormUrlEncoded
    @POST("/api/freegoods/get_share_free_activity_info")
    Observable<JSONObject> getShareFreeGoodsDetail(@Field("activityId") String activityId, @Field("goods_id") String goods_id, @Field("grade_id") int grade_id, @Field("lat") double lat,
                                                   @Field("lng") double lng, @Field("login_token") String loginToken);

    /**
     * 获取分享免单加一券
     */
    @FormUrlEncoded
    @POST("/api/freegoods/get_share_free_ticket")
    Observable<JSONObject> getShareFreeTicket(@Field("login_token") String loginToken);

    /**
     * 使用分享免单券
     */
    @FormUrlEncoded
    @POST("/api/freegoods/use_share_free_ticket")
    Observable<JSONObject> useShareFreeTicket(@Field("login_token") String loginToken, @Field("share_free_ticket_id") String id, @Field("activity_id") String activityId);

    /**
     * 获取免单活动消息
     */
    @POST("/api/freegoods/get_success_activity_and_estimate")
    Observable<Result<NewFreeInfoBean>> getAllFreeInfo(@Body ApiRequestBody apiRequestBody);

    /**
     * 获取消费免单商品详情
     */
    @FormUrlEncoded
    @POST("/api/freegoods/get_consume_free_activity_info")
    Observable<JSONObject> getConsumeFreeGoodsDetail(@Field("activityId") String activityId, @Field("goods_id") String goods_id, @Field("grade_id") int grade_id, @Field("lat") double lat,
                                                     @Field("lng") double lng, @Field("login_token") String loginToken);

    /**
     * 获取软件包
     */
    @FormUrlEncoded
    @POST("/api/salesman/list_my_software")
    Observable<JSONObject> getMySoftware(@Field("login_token") String login_token);

    @FormUrlEncoded
    @POST("/api/unifyshop/list_cate")
    Observable<JSONObject> getListCate(@Field("login_token") String login_token, @Field("shop_id") String shop_id);

    @FormUrlEncoded
    @POST("/api/unifyshop/list_goods")
    Observable<JSONObject> getListGoods(@Field("login_token") String login_token, @Field("cate_id") int cate_id, @Field("shop_id") String shop_id, @Field("page") int page, @Field("num") int num);
}