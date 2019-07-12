package com.wbx.mall.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.activity.ShoppingCartActivity;
import com.wbx.mall.activity.StoreDetailActivity;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.bean.GoodsInfo2;
import com.wbx.mall.bean.ShopCart;
import com.wbx.mall.utils.GlideUtils;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.refresh.ViewStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wushenghui on 2018/1/31.
 */

public class ShoppingCartAdapter extends BaseAdapter<ShopCart, Context> {

    private List<ItemTrolleyGoodsAdapter> mAdapterList = new ArrayList<>();
    private ShoppingCartActivity shoppingCartActivity;

    public ShoppingCartAdapter(List<ShopCart> dataList, ShoppingCartActivity shoppingCartActivity) {
        super(dataList, shoppingCartActivity);
        this.shoppingCartActivity = shoppingCartActivity;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_shopping_cart;
    }

    @Override
    public void convert(final BaseViewHolder holder, final ShopCart shopCart, final int position) {
        RecyclerView goodsRv = holder.getView(R.id.index_trolley_goods_rv);
        ImageView shopPic = holder.getView(R.id.shop_pic_im);
        final ImageView checkIm = holder.getView(R.id.check_all_im);
        goodsRv.setLayoutManager(new LinearLayoutManager(mContext));
        GlideUtils.showSmallPic(mContext, shopPic, shopCart.getPhoto());
        TextView nameTv = holder.getView(R.id.shop_name_tv);
        nameTv.setText(shopCart.getShop_name());
        for (GoodsInfo2 goodsInfo : shopCart.getGoods_cart()) {
            if (goodsInfo.getSelected() == 0) {
                shopCart.setCheck(false);
                checkIm.setImageResource(R.drawable.uncheck);
                break;
            } else {
                shopCart.setCheck(true);
                checkIm.setImageResource(R.drawable.selected);
            }
        }
        holder.setText(R.id.logistics_price_tv, String.format("¥%.2f", shopCart.getLogistics() / 100.00)).setText(R.id.full_reduce_tv, String.format("-¥%.2f", shopCart.getFull_reduce() / 100.00))
                .setText(R.id.reduction_price_tv, String.format("已优惠%.2f元", shopCart.getFull_reduce() / 100.00 + shopCart.getCoupon_money() / 100.00))
                .setText(R.id.coupon_money_tv, String.format("-¥%.2f", shopCart.getCoupon_money() / 100.00)).setText(R.id.aggregate_amount_tv, String.format("¥%.2f", shopCart.getAll_money() / 100.000));
        final ItemTrolleyGoodsAdapter adapter = new ItemTrolleyGoodsAdapter(shopCart.getGoods_cart(), mContext);
        mAdapterList.add(adapter);
        goodsRv.setAdapter(adapter);
        checkIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAllGoods(shopCart);
            }
        });
        holder.getView(R.id.ll_shop_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreDetailActivity.actionStart(mContext, shopCart.getGrade_id()== AppConfig.StoreType.VEGETABLE_MARKET, String.valueOf(shopCart.getShop_id()));
            }
        });
        adapter.setOnItemClickListener(R.id.select_layout, new ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                selectedGoods(shopCart, adapter.getItem(position));
            }
        });
        adapter.setOnItemClickListener(R.id.goods_sub_car_im, new ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                GoodsInfo2 goodsInfo = shopCart.getGoods_cart().get(position);
                subCartNum(shopCart, goodsInfo);
            }
        });
        adapter.setOnItemClickListener(R.id.goods_add_car_im, new ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                GoodsInfo2 goodsInfo = shopCart.getGoods_cart().get(position);
                if (goodsInfo.getIs_seckill() == 1 && goodsInfo.getLimitations_num() != 0) {
                    //秒杀商品
                    if (goodsInfo.getNum() + 1 > goodsInfo.getLimitations_num()) {
                        Toast.makeText(mContext, "不能超过限购数量", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (goodsInfo.getIs_use_num() == 1) {
                    //开启库存
                    if (goodsInfo.getNum() + 1 > goodsInfo.getInventory_num()) {
                        Toast.makeText(mContext, "库存不足", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                addCartNum(shopCart, goodsInfo);
            }
        });
    }

    /**
     * 全选/全不选购物车
     */
    private void selectAllGoods(final ShopCart shopCart) {
        new MyHttp().doPost(Api.getDefault().selectCartAll(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), shopCart.getShop_id(), shopCart.isCheck() ? 0 : 1), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                shopCart.setCheck(!shopCart.isCheck());
                getCartTotalInfo(shopCart);
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    /**
     * 选中/取消选中购物车商品
     */
    private void selectedGoods(final ShopCart shopCart, GoodsInfo2 goodsInfo) {
        new MyHttp().doPost(Api.getDefault().selectCart(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), goodsInfo.getCart_id(), goodsInfo.getSelected() == 1 ? 0 : 1), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                getCartTotalInfo(shopCart);
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    /**
     * 重新获取价格
     */
    private void getCartTotalInfo(final ShopCart indexTrolleyInfos) {
        new MyHttp().doPost(Api.getDefault().indexTrolleyInfo(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN)), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                if ("暂无数据".equals(result.getString("msg"))) {
                    shoppingCartActivity.mRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
                    shoppingCartActivity.mRefreshLayout.buttonClickNullData(shoppingCartActivity, "getServiceData");
                    return;
                }
                List<ShopCart> mDataList = JSONArray.parseArray(result.getString("data"), ShopCart.class);
                for (ShopCart shopCart : mDataList) {
                    shopCart.setCheck(indexTrolleyInfos.isCheck());
                }
                setData(mDataList);
                shoppingCartActivity.mDataList = mDataList;
                notifyDataSetChanged();
            }

            @Override
            public void onError(int code) {
            }
        });
    }


    /**
     * 购物车增加数量
     *
     * @param shopCart
     */
    private void addCartNum(final ShopCart shopCart, GoodsInfo2 goodsInfo) {
        new MyHttp().doPost(Api.getDefault().addCartNum(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), goodsInfo.getCart_id()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                getCartTotalInfo(shopCart);
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    /**
     * 购物车减少数量
     *
     * @param shopCart
     */
    private void subCartNum(final ShopCart shopCart, GoodsInfo2 goodsInfo) {
        new MyHttp().doPost(Api.getDefault().subCartNum(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), goodsInfo.getCart_id()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                getCartTotalInfo(shopCart);
            }

            @Override
            public void onError(int code) {

            }
        });
    }
}