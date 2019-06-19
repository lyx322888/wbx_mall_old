package com.wbx.mall.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.adapter.BookSeatOrderDetailAdapter;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.BookSeatInfo;
import com.wbx.mall.utils.FormatUtil;
import com.wbx.mall.utils.GlideUtils;
import com.wbx.mall.widget.CircleImageView;

import butterknife.Bind;

/**
 * Created by wushenghui on 2017/12/22.
 * 预定订单详情
 */

public class BookSeatOrderDetailActivity extends BaseActivity {
    @Bind(R.id.scroll_view)
    NestedScrollView mScrollView;
    @Bind(R.id.shop_photo_im)
    CircleImageView shopPhotoIm;
    @Bind(R.id.shop_name_tv)
    TextView shopNameTv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.phone_tv)
    TextView phoneTv;
    @Bind(R.id.time_tv)
    TextView timeTv;
    @Bind(R.id.use_people_num_tv)
    TextView usePeopleNumTv;
    @Bind(R.id.subscription_tv)
    TextView subscriptionTv;//订金
    @Bind(R.id.order_num_tv)
    TextView orderNumTv;
    @Bind(R.id.remark_tv)
    TextView remarkTv;
    @Bind(R.id.goods_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.full_tv)
    TextView fullTv;
    @Bind(R.id.coupon_tv)
    TextView couponTv;
    @Bind(R.id.total_price_tv)
    TextView totalPriceTv;
    private BookSeatInfo bookInfo;
    private BookSeatOrderDetailAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_seat_order_detail;
    }

    @Override
    public void initPresenter() {
        mScrollView.smoothScrollTo(0, 20);
    }

    @Override
    public void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    public void fillData() {
        bookInfo = (BookSeatInfo) getIntent().getSerializableExtra("bookInfo");
        GlideUtils.showMediumPic(mContext, shopPhotoIm, bookInfo.getPhoto());
        shopNameTv.setText(bookInfo.getShop_name());
        nameTv.setText(bookInfo.getName());
        phoneTv.setText(bookInfo.getMobile());
        timeTv.setText(FormatUtil.stampToDate2(bookInfo.getReserve_time() + "", "yyyy-MM-dd HH:mm"));
        usePeopleNumTv.setText(Html.fromHtml("<font color=#06c1ae>" + bookInfo.getNumber() + "</font>人"));
        subscriptionTv.setText(Html.fromHtml("<font color=#06c1ae>" + (bookInfo.getSubscribe_money() / 100.00) + "</font>元"));
        orderNumTv.setText(bookInfo.getOrder_id() == 0 ? bookInfo.getReserve_table_id() + "" : bookInfo.getOrder_id() + "");
        remarkTv.setText(bookInfo.getNote());
        fullTv.setText(String.format("-%.2f", bookInfo.getFull_money_reduce() / 100.00));
        couponTv.setText(String.format("-%.2f", bookInfo.getCoupon_money() / 100.00));
        totalPriceTv.setText(String.format("总计:¥%.2f", bookInfo.getSubscribe_money() / 100.00));
        if (bookInfo.getOrder_goods() == null || bookInfo.getOrder_goods().size() == 0) {
            ((View) mRecyclerView.getParent()).setVisibility(View.GONE);
        } else {
            mAdapter = new BookSeatOrderDetailAdapter(bookInfo.getOrder_goods(), mContext);
            mRecyclerView.setAdapter(mAdapter);
        }

    }

    @Override
    public void setListener() {
        findViewById(R.id.call_business_im).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(bookInfo.getTel())) {
                    showShortToast("抱歉，该商家还没有留下电话喔~");
                } else {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + bookInfo.getTel()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }
}
