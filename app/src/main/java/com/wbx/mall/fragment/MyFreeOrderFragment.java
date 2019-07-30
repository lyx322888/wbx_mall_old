package com.wbx.mall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.wbx.mall.R;
import com.wbx.mall.activity.CommentActivity;
import com.wbx.mall.activity.ConsumeFreeActivity;
import com.wbx.mall.activity.ShareFreeActivity;
import com.wbx.mall.activity.SubmitOrderActivity;
import com.wbx.mall.adapter.MyFreeOrderAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseFragment;
import com.wbx.mall.bean.MyFreeOrderBean;
import com.wbx.mall.bean.OrderBean;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.utils.ShareUtils;
import com.wbx.mall.utils.ToastUitl;
import com.wbx.mall.widget.LoadingDialog;
import com.wbx.mall.widget.refresh.BaseRefreshListener;
import com.wbx.mall.widget.refresh.PullToRefreshLayout;
import com.wbx.mall.widget.refresh.ViewStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

public class MyFreeOrderFragment extends BaseFragment implements BaseRefreshListener {
    private static final String POSITION = "POSITION";
    @Bind(R.id.srl)
    PullToRefreshLayout mRefreshLayout;
    @Bind(R.id.rv_free_activity)
    RecyclerView rvFreeActivity;
    private List<MyFreeOrderBean> lstData = new ArrayList<>();
    private MyFreeOrderAdapter adapter;
    private int type;
    private int pageNum = AppConfig.pageNum;
    private int pageSize = AppConfig.pageSize;
    private boolean canLoadMore = true;

    public MyFreeOrderFragment() {
    }

    public static MyFreeOrderFragment newInstance(int position) {
        MyFreeOrderFragment fragment = new MyFreeOrderFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int position = getArguments().getInt(POSITION);
            switch (position) {
                case 0:
                    //全部
                    type = 88;
                    break;
                case 1:
                    //进行中
                    type = 0;
                    break;
                case 2:
                    //成功
                    type = 1;
                    break;
                case 3:
                    //失败
                    type = 2;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my_free_order;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    protected void initView() {
        rvFreeActivity.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyFreeOrderAdapter(lstData);
        rvFreeActivity.setAdapter(adapter);
    }

    @Override
    protected void fillData() {
        loadData();
    }

    @Override
    protected void bindEvent() {
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MyFreeOrderBean data = lstData.get(position);
                switch (view.getId()) {
                    case R.id.rl_goods:
                        if (data.getActivity_type() == 1) {
                            ConsumeFreeActivity.actionStart(getContext(), data.getActivity_id(), data.getGoods_id(), data.getGrade_id());
                        } else {
                            ShareFreeActivity.actionStart(getContext(), data.getActivity_id(), data.getGoods_id(), data.getGrade_id());
                        }
                        break;
                    case R.id.tv_free_gain:
                        freeGain(data);
                        break;
                    case R.id.tv_free_again:
                        if (data.getActivity_type() == 1) {
                            ConsumeFreeActivity.actionStart(getContext(), data.getGoods_id(), data.getGrade_id());
                        } else {
                            ShareFreeActivity.actionStart(getContext(), data.getGoods_id(), data.getGrade_id());
                        }
                        break;
                    case R.id.tv_invite:
                        inviteFriend(data);
                        break;
                    case R.id.tv_comment:
                        Intent intent = new Intent(getContext(), CommentActivity.class);
                        intent.putExtra("activityId", data.getActivity_id());
                        intent.putExtra("physical", data.getGrade_id() == AppConfig.StoreType.VEGETABLE_MARKET);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void inviteFriend(MyFreeOrderBean data) {
        String path = "pages/index/freesheet/freesheet?activity_id=" + data.getActivity_id() + "&sponsor_user_id=" + LoginUtil.getUserInfo().getUser_id() + "&goods_id=" + data.getGoods_id() + "&grade_id=" + data.getGrade_id() + "&shop_id=" + data.getShop_id() + "&activitytype=" + (data.getActivity_type() == 1 ? "consume" : "share");
        ShareUtils.getInstance().shareMiniProgram(getContext(), "您的好友已经在附近实体店免单中，快去瞧瞧吧！！！", "", data.getPhoto(), path, "www.wbx365.com");
    }

    private void freeGain(final MyFreeOrderBean data) {
        if (data.getIs_gain() == 1) {
            ToastUitl.showLong("您已领取过了");
            return;
        }
        LoadingDialog.showDialogForLoading(getActivity(), "领取中...", false);
        new MyHttp().doPost(data.getGrade_id() == AppConfig.StoreType.VEGETABLE_MARKET ? Api.getDefault().createVegetableOrder(SPUtils.getSharedStringData(getContext(), AppConfig.LOGIN_TOKEN), createGoodsJson(data)) : Api.getDefault().createOrder(SPUtils.getSharedStringData(getContext(), AppConfig.LOGIN_TOKEN), createGoodsJson(data), null), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                String orderId = result.getJSONObject("data").getString("order_id");
                Intent intent = new Intent(getContext(), SubmitOrderActivity.class);
                intent.putExtra("isPhysical", data.getGrade_id() == AppConfig.StoreType.VEGETABLE_MARKET);
                intent.putExtra("orderId", orderId);
                startActivity(intent);
            }

            @Override
            public void onError(int code) {
            }
        });
    }

    private String createGoodsJson(MyFreeOrderBean data) {
        HashMap<String, List<OrderBean>> hashMap = new HashMap<>();
        ArrayList<OrderBean> lstGoods = new ArrayList<>();
        OrderBean orderBean = new OrderBean();
        if (data.getGrade_id() == AppConfig.StoreType.VEGETABLE_MARKET) {
            orderBean.setProduct_id(data.getGoods_id());
        } else {
            orderBean.setGoods_id(data.getGoods_id());
        }
        orderBean.setActivity_type(data.getActivity_type());
        orderBean.setNum(1);
        lstGoods.add(orderBean);
        hashMap.put(data.getShop_id(), lstGoods);
        return new Gson().toJson(hashMap);
    }

    private void loadData() {
        new MyHttp().doPost(Api.getDefault().getMyFreeActivity(LoginUtil.getLoginToken(), type, pageNum, pageSize), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                List<MyFreeOrderBean> data = JSONArray.parseArray(result.getString("data"), MyFreeOrderBean.class);
                if (data != null) {
                    mRefreshLayout.showView(ViewStatus.CONTENT_STATUS);
                    if (pageNum == AppConfig.pageNum) {
                        lstData.clear();
                    }
                    if (data.size() < pageSize) {
                        canLoadMore = false;
                    }
                    lstData.addAll(data);
                    adapter.notifyDataSetChanged();
                } else if (lstData.size() == 0) {
                    mRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
                    mRefreshLayout.buttonClickNullData(MyFreeOrderFragment.this, "loadData");
                }
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
            }

            @Override
            public void onError(int code) {
                if (pageNum == AppConfig.pageNum) {
                    if (code == AppConfig.ERROR_STATE.NULLDATA) {
                        mRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
                        mRefreshLayout.buttonClickNullData(MyFreeOrderFragment.this, "loadData");
                    } else if (code == AppConfig.ERROR_STATE.NO_NETWORK || code == AppConfig.ERROR_STATE.SERVICE_ERROR) {
                        mRefreshLayout.showView(ViewStatus.ERROR_STATUS);
                        mRefreshLayout.buttonClickError(MyFreeOrderFragment.this, "loadData");
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
    public void onDestroy() {
        super.onDestroy();
        adapter.cancelAllTimer();
    }

    @Override
    public void refresh() {
        canLoadMore = true;
        pageNum = AppConfig.pageNum;
        loadData();
    }

    @Override
    public void loadMore() {
        pageNum++;
        if (!canLoadMore) {
            mRefreshLayout.finishLoadMore();
            showShortToast("没有更多数据了");
            return;
        }
        loadData();
    }
}
