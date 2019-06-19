package com.wbx.mall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.activity.BookSeatOrderDetailActivity;
import com.wbx.mall.activity.BookSeatPayActivity;
import com.wbx.mall.activity.SubmitOrderActivity;
import com.wbx.mall.adapter.BookSeatAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseFragment;
import com.wbx.mall.bean.BookSeatInfo;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.iosdialog.AlertDialog;
import com.wbx.mall.widget.refresh.BaseRefreshListener;
import com.wbx.mall.widget.refresh.PullToRefreshLayout;
import com.wbx.mall.widget.refresh.ViewStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import rx.Observable;

/**
 * Created by wushenghui on 2017/12/22.
 */

public class BookSeatOrderFragment extends BaseFragment implements BaseRefreshListener {
    @Bind(R.id.order_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.refresh_layout)
    PullToRefreshLayout mRefreshLayout;
    private HashMap<String, Object> mParams = new HashMap<>();
    public static final String POSITION = "position";
    private BookSeatAdapter mAdapter;
    private List<BookSeatInfo> bookSeatInfoList = new ArrayList<>();
    private int mStatus;
    private boolean canLoadMore = true;//控制下次是否加载更多
    private int pageNum = AppConfig.pageNum;
    private int pageSize = AppConfig.pageSize;
    private BookSeatInfo selectOrder;

    public static BookSeatOrderFragment newInstance(int position) {
        BookSeatOrderFragment tabFragment = new BookSeatOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_book_seat_order;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        mRefreshLayout.showView(ViewStatus.LOADING_STATUS);
        int status = getArguments().getInt(POSITION, 0);
        mParams.put("login_token", SPUtils.getSharedStringData(getActivity(), AppConfig.LOGIN_TOKEN));
        if (status == 3) {
            mStatus = 4;
        } else if (status == 4 || status == 5) {
            mStatus = 8;
        } else {
            mStatus = status;
        }
        mParams.put("status", mStatus);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new BookSeatAdapter(bookSeatInfoList, getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void fillData() {
        mParams.put("page", pageNum);
        mParams.put("num", pageSize);
        new MyHttp().doPost(Api.getDefault().getBookSeatList(mParams), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();

                mRefreshLayout.showView(ViewStatus.CONTENT_STATUS);
                List<BookSeatInfo> dataList = JSONArray.parseArray(result.getString("data"), BookSeatInfo.class);
                if (null == dataList) {
                    if (pageNum == AppConfig.pageNum) {
                        mRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
                        mRefreshLayout.buttonClickNullData(BookSeatOrderFragment.this, "fillData");
                    }
                    return;
                }
                if (pageNum == AppConfig.pageNum) {
                    bookSeatInfoList.clear();
                }
                if (dataList.size() < pageSize) {
                    //说明下次已经没有数据了
                    canLoadMore = false;
                }
                bookSeatInfoList.addAll(dataList);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onError(int code) {
                if (pageNum == AppConfig.pageNum) {
                    if (code == AppConfig.ERROR_STATE.NULLDATA) {
                        //无数据情况下
                        mRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
                        mRefreshLayout.buttonClickNullData(BookSeatOrderFragment.this, "fillData");
                    } else if (code == AppConfig.ERROR_STATE.NO_NETWORK || code == AppConfig.ERROR_STATE.SERVICE_ERROR) {
                        mRefreshLayout.showView(ViewStatus.ERROR_STATUS);
                        mRefreshLayout.buttonClickError(BookSeatOrderFragment.this, "fillData");
                    } else {

                    }
                } else {
                    if (code == AppConfig.ERROR_STATE.NULLDATA) {
                        canLoadMore = false;
                    }
                }
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
            }
        });
    }

    @Override
    protected void bindEvent() {
        mAdapter.setOnItemClickListener(R.id.pay_btn, new BaseAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                BookSeatInfo item = mAdapter.getItem(position);
                if (item.getType() == 2) {//立即点菜
                    Intent orderIntent = new Intent(getActivity(), SubmitOrderActivity.class);
                    orderIntent.putExtra("isBook", true);
                    orderIntent.putExtra("isPhysical", true);
                    orderIntent.putExtra("orderId", item.getOrder_id() + "");
                    startActivity(orderIntent);
                } else {//到店点菜
                    Intent intent = new Intent(getActivity(), BookSeatPayActivity.class);
                    intent.putExtra("bookId", item.getReserve_table_id() + "");
                    startActivity(intent);
                }
            }
        });
        mAdapter.setOnItemClickListener(R.id.refund_btn, new BaseAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                selectOrder = mAdapter.getItem(position);
                new AlertDialog(getActivity()).builder()
                        .setTitle("提示")
                        .setMsg("确认退款？")
                        .setNegativeButton("再想想", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        })
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                orderOperation(Api.getDefault().refundBook(SPUtils.getSharedStringData(getActivity(), AppConfig.LOGIN_TOKEN), selectOrder.getReserve_table_id()));
                            }
                        }).show();
            }
        });
        mRefreshLayout.setRefreshListener(this);
        mAdapter.setOnItemClickListener(R.id.detail_btn, new BaseAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(getActivity(), BookSeatOrderDetailActivity.class);
                intent.putExtra("bookInfo", mAdapter.getItem(position));
                startActivity(intent);
            }
        });
        mAdapter.setOnItemClickListener(R.id.cancel_btn, new BaseAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                selectOrder = mAdapter.getItem(position);
                new AlertDialog(getActivity()).builder()
                        .setTitle("提示")
                        .setMsg("删除此订单？")
                        .setNegativeButton("再想想", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        })
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (selectOrder.getStatus() == 1) {
                                    //取消订单  已付钱
                                    orderOperation(Api.getDefault().cancelBook(SPUtils.getSharedStringData(getActivity(), AppConfig.LOGIN_TOKEN), selectOrder.getReserve_table_id()));
                                } else {
                                    //删除订单
                                    orderOperation(Api.getDefault().deleteBook(SPUtils.getSharedStringData(getActivity(), AppConfig.LOGIN_TOKEN), selectOrder.getReserve_table_id()));
                                }
                            }
                        }).show();
            }
        });
    }

    /**
     * 取消/删除订单操作
     *
     * @param observable
     */
    private void orderOperation(Observable<JSONObject> observable) {
        new MyHttp().doPost(observable, new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                showShortToast(result.getString("msg"));
                bookSeatInfoList.remove(selectOrder);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    @Override
    public void refresh() {
        canLoadMore = true;
        pageNum = AppConfig.pageNum;
        fillData();
    }

    @Override
    public void loadMore() {
        pageNum++;
        if (!canLoadMore) {
            mRefreshLayout.finishLoadMore();
            showShortToast("没有更多数据了");
            return;
        }
        fillData();
    }
}
