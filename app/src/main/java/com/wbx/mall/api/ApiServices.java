package com.wbx.mall.api;

import com.wbx.mall.bean.BuygreensGoodsBean;
import com.wbx.mall.bean.CateInfo2;
import com.wbx.mall.bean.GoodsInfo2;
import com.wbx.mall.bean.HomeCouponBean;
import com.wbx.mall.bean.ListShopDataBean;
import com.wbx.mall.bean.NewFreeInfoBean2;
import com.wbx.mall.bean.SalesDetailBean;
import com.wbx.mall.bean.ShopGoodsBean;
import com.wbx.mall.bean.SoftwareInfo;
import com.wbx.mall.bean.VisitShopBean;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface ApiServices {
    //首页优惠券
    @FormUrlEncoded
    @POST("/api/index/list_index_coupon")
    Observable<HomeCouponBean> getIndexCoupon(@FieldMap Map<String, String> params);


    //店铺详情（菜市场）
    @FormUrlEncoded
    @POST("/api/buygreens/get_goods")
    Observable<BuygreensGoodsBean> getVegetable(@Field("shop_id") String shop_id, @Field("cate_id") String cate_id, @Field("login_token") String login_token);

    //店铺详情（实体店）
    @FormUrlEncoded
    @POST("/api/shop/get_goods")
    Observable<ShopGoodsBean> getShop(@Field("shop_id") String shop_id, @Field("login_token") String login_token);

    //店铺详情（实体店）
    @FormUrlEncoded
    @POST("/api/salesman/list_shop_data")
    Observable<ListShopDataBean> getMerchant(@Field("login_token") String login_token);

    /**
     * 获取免单活动消息
     */
    @POST("/api/freegoods/get_success_activity_and_estimate")
    Observable<NewFreeInfoBean2> getAllFreeInfo();

    //新菜单
    @FormUrlEncoded
    @POST("/api/unifyshop/list_cate")
    Observable<CateInfo2> getCate(@Field("login_token") String login_token, @Field("shop_id") String shop_id);

    //新商品
    @FormUrlEncoded
    @POST("/api/unifyshop/list_goods")
    Observable<GoodsInfo2> getGoods(@Field("shop_id") String shop_id, @Field("login_token") String login_token, @Field("page") int page, @Field("num") int num, @Field("cate_id") int cate_id);

    //新商品
    @FormUrlEncoded
    @POST("/api/salesman/list_my_software")
    Observable<SoftwareInfo> getSoftware(@Field("login_token") String login_token);

    //销售明细
    @FormUrlEncoded
    @POST("/api/salesman/get_my_software_sell")
    Observable<SalesDetailBean> getSales(@Field("login_token") String login_token);

    //销售明细
    @FormUrlEncoded
    @POST("/api/index/list_user_visit_shop")
    Observable<VisitShopBean> getVisit(@Field("login_token") String login_token,@Field("page") int page,@Field("num") int num,@Field("lat") String lat,@Field("lng") String lng);
}
