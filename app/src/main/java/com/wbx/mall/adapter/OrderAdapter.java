package com.wbx.mall.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.OptionsPickerView;
import com.wbx.mall.R;
import com.wbx.mall.activity.CommentActivity;
import com.wbx.mall.activity.OrderDetailActivity;
import com.wbx.mall.activity.RefundProgressActivity;
import com.wbx.mall.activity.StoreDetailActivity;
import com.wbx.mall.activity.SubmitOrderActivity;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.bean.OrderListBean;
import com.wbx.mall.bean.RefundInfo;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.fragment.OrderFragment;
import com.wbx.mall.utils.GlideUtils;
import com.wbx.mall.utils.ToastUitl;
import com.wbx.mall.widget.iosdialog.AlertDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;

/**
 * Created by zero on 2017/1/17.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    private Context mContext;
    private List<OrderListBean> lstData = new ArrayList<>();
    private HashMap<String, Object> mOrderParams = new HashMap<>();
    private OptionsPickerView catePickerView;
    private List<RefundInfo> refundInfoList = new ArrayList<>();
    private OrderFragment.OnNeedRefreshListener onNeedRefreshListener;

    public OrderAdapter(Context context, OrderFragment.OnNeedRefreshListener onNeedRefreshListener) {
        mContext = context;
        mOrderParams.put("login_token", LoginUtil.getLoginToken());
        refundInfoList.add(new RefundInfo("操作有误（商品、地址等选错）"));
        refundInfoList.add(new RefundInfo("重复下单/误下单"));
        refundInfoList.add(new RefundInfo("其他渠道价格更低"));
        refundInfoList.add(new RefundInfo("不想买了"));
        refundInfoList.add(new RefundInfo("其他原因"));
        this.onNeedRefreshListener = onNeedRefreshListener;
    }

    public void update(List<OrderListBean> data) {
        lstData.clear();
        lstData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        MyViewHolder holder = new MyViewHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final OrderListBean data = lstData.get(position);
        GlideUtils.showSmallPic(mContext, holder.ivShop, data.getShop_logo());
        holder.tvShopName.setText(data.getShop_name());
        holder.tvShopName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreDetailActivity.actionStart(mContext, data.getOrder_type() == 1, String.valueOf(data.getShop_id()));
            }
        });
        holder.tvCancelOrder.setVisibility(View.GONE);
        holder.tvPayNow.setVisibility(View.GONE);
        holder.tvApplyRefund.setVisibility(View.GONE);
        holder.tvCallMerchant.setVisibility(View.GONE);
        holder.tvContactDriver.setVisibility(View.GONE);
        holder.tvRefundDetail.setVisibility(View.GONE);
        holder.tvApplyService.setVisibility(View.GONE);
        holder.tvComment.setVisibility(View.GONE);
        holder.tvCancelRefund.setVisibility(View.GONE);
        holder.tvConfirmReceive.setVisibility(View.GONE);
        holder.tvDeleteOrder.setVisibility(View.GONE);
        switch (data.getStatus()) {
            case 0:
                //待付款
                holder.tvCancelOrder.setVisibility(View.VISIBLE);
                holder.tvPayNow.setVisibility(View.VISIBLE);
                if (data.getOver_order_time() > 0) {
                    holder.tvState.setText("等待支付");
                } else {
                    holder.tvState.setText("支付超时");
                }
                break;
            case 1:
                //待发货
                holder.tvState.setText("商家已接单");
                holder.tvApplyRefund.setVisibility(View.VISIBLE);
                holder.tvCallMerchant.setVisibility(View.VISIBLE);
                break;
            case 2:
                //待收货
                holder.tvApplyRefund.setVisibility(View.VISIBLE);
                holder.tvConfirmReceive.setVisibility(View.VISIBLE);
                if (data.getIs_fengniao() == 1) {//蜂鸟配送
                    holder.tvContactDriver.setVisibility(View.VISIBLE);
                    int fengniao_status = data.getFengniao_status();
                    switch (fengniao_status) {
                        case 1:
                            holder.tvState.setText("蜂鸟已接单");
                            break;
                        case 20:
                            holder.tvState.setText("已分配骑手");
                            break;
                        case 80:
                            holder.tvState.setText("骑手已到店");
                            break;
                        case 2:
                            holder.tvState.setText("蜂鸟配送中");
                            break;
                        case 3:
                            holder.tvState.setText("蜂鸟已送达");
                            break;
                        case 5:
                            holder.tvState.setText("蜂鸟拒单");
                            break;
                    }
                } else if (data.getIs_dada() == 1) {//达达配送
                    holder.tvContactDriver.setVisibility(View.VISIBLE);
                    int dada_status = data.getDada_status();
                    switch (dada_status) {
                        case 1:
                            holder.tvState.setText("达达待接单");
                            break;
                        case 2:
                            holder.tvState.setText("达达待取货");
                            break;
                        case 3:
                            holder.tvState.setText("达达配送中");
                            break;
                        case 4:
                            holder.tvState.setText("配送完成");
                            break;
                        case 5:
                            holder.tvState.setText("达达配送已取消");
                            break;
                        case 7:
                            holder.tvState.setText("达达配送已过期");
                            break;
                        case 8:
                            holder.tvState.setText("达达指派单");
                            break;
                        case 9:
                        case 10:
                            holder.tvState.setText("达达派送异常");
                            break;
                        case 1000:
                            holder.tvState.setText("创建达达运单失败");
                            break;
                    }
                } else {
                    holder.tvCallMerchant.setVisibility(View.VISIBLE);
                    holder.tvState.setText("商家正在配送");
                }
                break;
            case 3:
                //菜市场待退款
                holder.tvState.setText("订单待退款");
                holder.tvCancelRefund.setVisibility(View.VISIBLE);
                holder.tvRefundDetail.setVisibility(View.VISIBLE);
                break;
            case 4:
                if (data.getOrder_type() == 2) {//实体店待退款
                    holder.tvState.setText("订单待退款");
                    holder.tvCancelRefund.setVisibility(View.VISIBLE);
                    holder.tvRefundDetail.setVisibility(View.VISIBLE);
                } else {
                    //菜市场已退款
                    holder.tvState.setText("退款完成");
                    holder.tvRefundDetail.setVisibility(View.VISIBLE);
                }
                break;
            case 5:
                //实体店已退款
                holder.tvState.setText("退款完成");
                holder.tvRefundDetail.setVisibility(View.VISIBLE);
                break;
            case 8:
                //已完成
                holder.tvState.setText("订单已完成");
                holder.tvApplyService.setVisibility(View.VISIBLE);
                holder.tvComment.setVisibility(View.VISIBLE);
                holder.tvDeleteOrder.setVisibility(View.VISIBLE);
                break;
        }
        holder.llRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderDetailActivity.actionStart(mContext, data.getOrder_id(), data.getOrder_type() == 1);
            }
        });
        holder.llContainer.removeAllViews();
        for (int i = 0; i < (data.getGoods().size() > 3 ? 3 : data.getGoods().size()); i++) {
            OrderListBean.GoodsBean goodsBean = data.getGoods().get(i);
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_order_goods, null);
            StringBuilder sbNature = new StringBuilder();
            if (!TextUtils.isEmpty(goodsBean.getAttr_name()) || !TextUtils.isEmpty(goodsBean.getNature_name())) {
                sbNature.append("(");
                if (!TextUtils.isEmpty(goodsBean.getAttr_name())) {
                    sbNature.append(goodsBean.getAttr_name());
                }
                if (!TextUtils.isEmpty(goodsBean.getAttr_name()) && !TextUtils.isEmpty(goodsBean.getNature_name())) {
                    sbNature.append("+");
                }
                if (!TextUtils.isEmpty(goodsBean.getNature_name())) {
                    sbNature.append(goodsBean.getNature_name());
                }
                sbNature.append(")");
            }
            ((TextView) inflate.findViewById(R.id.tv_goods_name)).setText(goodsBean.getTitle() + sbNature.toString() + "   x" + goodsBean.getNum());
            ((TextView) inflate.findViewById(R.id.tv_goods_price)).setText(String.format("¥%.2f", goodsBean.getTotal_price() / 100.00));
            holder.llContainer.addView(inflate);
        }
        if (data.getGoods().size() > 3) {
            holder.more.setVisibility(View.VISIBLE);
        } else {
            holder.more.setVisibility(View.GONE);
        }
        holder.tvGoodsNum.setText("共" + data.getNum() + "件商品，实付");
        holder.tvMoney.setText(String.format("¥%.2f", data.getNeed_pay() / 100.00));
        holder.tvCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelOrder(data);
            }
        });
        holder.tvPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay(data);
            }
        });
        holder.tvApplyRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyRefund(data);
            }
        });
        holder.tvCallMerchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone(data.getTel());
            }
        });
        holder.tvContactDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone(data.getCarrier_driver_phone());
            }
        });
        holder.tvRefundDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefundProgressActivity.actionStart(mContext, data.getOrder_id(), data.getOrder_type());
            }
        });
        holder.tvApplyService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyService(data);
            }
        });
        holder.tvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment(data);
            }
        });
        holder.tvCancelRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelRefund(data);
            }
        });
        holder.tvConfirmReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmReceive(data);
            }
        });
        holder.tvDeleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOrder(data);
            }
        });
    }

    private void callPhone(String tel) {
        if (TextUtils.isEmpty(tel)) {
            ToastUitl.showShort("抱歉，暂未获取到联系方式");
            return;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    private void deleteOrder(OrderListBean data) {
        orderOperation(Api.getDefault().deleteOrder(LoginUtil.getLoginToken(), data.getOrder_id(), data.getOrder_type()), data, "确认删除订单?");
    }

    private void confirmReceive(OrderListBean data) {
        orderOperation(Api.getDefault().confirmReceive(LoginUtil.getLoginToken(), data.getOrder_id(), data.getOrder_type()), data, "确认收货?");
    }

    private void cancelRefund(OrderListBean data) {
        orderOperation(Api.getDefault().cancelRefund(LoginUtil.getLoginToken(), data.getOrder_id(), data.getOrder_type()), data, "确认取消退款?");
    }


    private void comment(OrderListBean data) {
        if (data.getStatus() == 4) {//已退款
            Toast.makeText(mContext, "已退款不能评价", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(mContext, CommentActivity.class);
        intent.putExtra("orderId", data.getOrder_id());
        intent.putExtra("physical", data.getOrder_type() == 2);
        mContext.startActivity(intent);
    }

    private void applyService(final OrderListBean data) {
        if (data.getStatus() == 4) {//已退款
            Toast.makeText(mContext, "已退款不能申请售后", Toast.LENGTH_SHORT).show();
            return;
        }
        mOrderParams.put("order_id", data.getOrder_id());
        catePickerView = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                mOrderParams.put("message", refundInfoList.get(options1).getPickerViewText());
                mOrderParams.put("type", data.getOrder_type());
                new MyHttp().doPost(Api.getDefault().applyService(mOrderParams), new HttpListener() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        Toast.makeText(mContext, result.getString("msg"), Toast.LENGTH_SHORT).show();
                        mOrderParams.remove("message");
                        mOrderParams.remove("type");
                    }

                    @Override
                    public void onError(int code) {

                    }
                });
            }
        }).build();
        catePickerView.setPicker(refundInfoList);//添加数据
        catePickerView.show();
    }

    private void applyRefund(OrderListBean data) {
        if (data.getIs_daofu() == 1) {
            Toast.makeText(mContext, "货到付款不能申请退款", Toast.LENGTH_SHORT).show();
            return;
        }
        orderOperation(Api.getDefault().applyRefund(LoginUtil.getLoginToken(), data.getOrder_id(), data.getOrder_type()), data, "确认申请退款?");
    }

    private void pay(OrderListBean data) {
        Intent intent = new Intent(mContext, SubmitOrderActivity.class);
        intent.putExtra("orderId", data.getOrder_id());
        intent.putExtra("isPhysical", data.getOrder_type() == 2);
        mContext.startActivity(intent);
    }

    private void cancelOrder(OrderListBean data) {
        orderOperation(Api.getDefault().cancelOrder(LoginUtil.getLoginToken(), data.getOrder_id(), data.getOrder_type()), data, "确认取消订单?");
    }

    /**
     * 订单操作统一处理
     *
     * @param orderInfo
     */
    private void orderOperation(final Observable<JSONObject> observable, final OrderListBean orderInfo, String hint) {
        new AlertDialog(mContext).builder()
                .setTitle("提示")
                .setMsg(hint)
                .setNegativeButton("再想想", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new MyHttp().doPost(observable, new HttpListener() {
                            @Override
                            public void onSuccess(JSONObject result) {
                                Toast.makeText(mContext, result.getString("msg"), Toast.LENGTH_SHORT).show();
                                if (onNeedRefreshListener != null) {
                                    onNeedRefreshListener.onNeedRefresh();
                                }
                            }

                            @Override
                            public void onError(int code) {

                            }
                        });
                    }
                }).show();
    }

    @Override
    public int getItemCount() {
        return lstData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ll_root)
        LinearLayout llRoot;
        @Bind(R.id.iv_shop)
        ImageView ivShop;
        @Bind(R.id.tv_shop_name)
        TextView tvShopName;
        @Bind(R.id.tv_state)
        TextView tvState;
        @Bind(R.id.ll_container)
        LinearLayout llContainer;
        @Bind(R.id.more)
        TextView more;
        @Bind(R.id.tv_goods_num)
        TextView tvGoodsNum;
        @Bind(R.id.tv_money)
        TextView tvMoney;
        @Bind(R.id.tv_cancel_order)
        TextView tvCancelOrder;
        @Bind(R.id.tv_call_merchant)
        TextView tvCallMerchant;
        @Bind(R.id.tv_contact_driver)
        TextView tvContactDriver;
        @Bind(R.id.tv_cancel_refund)
        TextView tvCancelRefund;
        @Bind(R.id.tv_apply_service)
        TextView tvApplyService;
        @Bind(R.id.tv_pay_now)
        TextView tvPayNow;
        @Bind(R.id.tv_comment)
        TextView tvComment;
        @Bind(R.id.tv_refund_detail)
        TextView tvRefundDetail;
        @Bind(R.id.tv_apply_refund)
        TextView tvApplyRefund;
        @Bind(R.id.tv_confirm_receive)
        TextView tvConfirmReceive;
        @Bind(R.id.tv_delete_order)
        TextView tvDeleteOrder;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
