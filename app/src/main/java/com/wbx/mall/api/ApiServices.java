package com.wbx.mall.api;

import com.wbx.mall.bean.NewFreeInfoBean2;
import com.wbx.mall.bean.SalesDetailBean;
import com.wbx.mall.bean.SoftwareInfo;
import com.wbx.mall.bean.VisitShopBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface ApiServices {

    /**
     * 21.10.	获取天天免单及晒单数据
     */
    @POST("/api/freegoods/get_success_activity_and_estimate")
    Observable<NewFreeInfoBean2> getAllFreeInfo();

    //新商品
    @FormUrlEncoded
    @POST("/api/salesman/list_my_software")
    Observable<SoftwareInfo> getSoftware(@Field("login_token") String login_token);

    //销售明细
    @FormUrlEncoded
    @POST("/api/salesman/get_my_software_sell")
    Observable<SalesDetailBean> getSales(@Field("login_token") String login_token);

    //首页浏览过的店铺
    @FormUrlEncoded
    @POST("/api/index/list_user_visit_shop")
    Observable<VisitShopBean> getVisit(@Field("login_token") String login_token, @Field("page") int page, @Field("num") int num, @Field("lat") String lat, @Field("lng") String lng);
}
