package com.wbx.mall.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.bean.BookSeatInfo;
import com.wbx.mall.utils.FormatUtil;

import java.util.List;

/**
 * Created by wushenghui on 2017/12/22.
 */

public class BookSeatAdapter extends BaseAdapter<BookSeatInfo, Context> {

    public BookSeatAdapter(List<BookSeatInfo> dataList, Context context) {
        super(dataList, context);

    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_book_seat;
    }

    @Override
    public void convert(BaseViewHolder holder, BookSeatInfo bookSeatInfo, int position) {
        holder.setText(R.id.time_tv, String.format("就餐时间:%s", FormatUtil.stampToDate2(bookSeatInfo.getReserve_time() + "", "yyyy-MM-dd HH:mm")))
                .setText(R.id.repast_people_tv, String.format("就餐人数:%d人", bookSeatInfo.getNumber()))
                .setText(R.id.seat_info_tv, "桌位信息:" + bookSeatInfo.getTable_number())
                .setText(R.id.customer_info_tv, String.format("顾客信息:%s %s", bookSeatInfo.getName(), bookSeatInfo.getMobile()))
                .setText(R.id.shop_name_tv, bookSeatInfo.getShop_name());
        TextView stateTv = holder.getView(R.id.order_state_tv);
        TextView cancelBtn = holder.getView(R.id.cancel_btn);
        TextView detailBtn = holder.getView(R.id.detail_btn);
        TextView payBtn = holder.getView(R.id.pay_btn);
        TextView refundBtn = holder.getView(R.id.refund_btn);
        switch (bookSeatInfo.getStatus()) {
            case 0:
                stateTv.setText("待付款");
                cancelBtn.setText("删除订单");
                cancelBtn.setVisibility(View.VISIBLE);
                detailBtn.setVisibility(View.VISIBLE);
                if (bookSeatInfo.getReserve_time() > System.currentTimeMillis() / 1000L) {
                    payBtn.setVisibility(View.VISIBLE);
                } else {
                    payBtn.setVisibility(View.GONE);
                }
                refundBtn.setVisibility(View.GONE);
                break;
            case 1:
                stateTv.setText("待接单");
                cancelBtn.setVisibility(View.VISIBLE);
                detailBtn.setVisibility(View.VISIBLE);
                payBtn.setVisibility(View.GONE);
                refundBtn.setVisibility(View.GONE);
                break;
            case 2:
                stateTv.setText("已接单");
                cancelBtn.setVisibility(View.GONE);
                detailBtn.setVisibility(View.VISIBLE);
                payBtn.setVisibility(View.GONE);
                refundBtn.setVisibility(View.VISIBLE);
                break;
            case 4:
                stateTv.setText("待退款");
                cancelBtn.setVisibility(View.GONE);
                detailBtn.setVisibility(View.VISIBLE);
                payBtn.setVisibility(View.GONE);
                refundBtn.setVisibility(View.GONE);
                break;
            case 5:
                stateTv.setText("已退款");
                cancelBtn.setText("删除订单");
                cancelBtn.setVisibility(View.VISIBLE);
                detailBtn.setVisibility(View.VISIBLE);
                payBtn.setVisibility(View.GONE);
                refundBtn.setVisibility(View.GONE);
                break;
            case 8:
                stateTv.setText("已完成");
                cancelBtn.setText("删除订单");
                cancelBtn.setVisibility(View.VISIBLE);
                detailBtn.setVisibility(View.VISIBLE);
                payBtn.setVisibility(View.GONE);
                refundBtn.setVisibility(View.GONE);
            case 9:
                stateTv.setText("已拒单");
                cancelBtn.setText("删除订单");
                cancelBtn.setVisibility(View.VISIBLE);
                detailBtn.setVisibility(View.VISIBLE);
                payBtn.setVisibility(View.GONE);
                refundBtn.setVisibility(View.GONE);
                break;
        }
    }
}
