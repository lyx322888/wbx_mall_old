package com.wbx.mall.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.wbx.mall.R;
import com.wbx.mall.activity.AccountInfoActivity;
import com.wbx.mall.activity.AddressManagerActivity;
import com.wbx.mall.activity.BookSeatOrderActivity;
import com.wbx.mall.activity.CollectionActivity;
import com.wbx.mall.activity.IndexOrderActivity;
import com.wbx.mall.activity.IntegralMallActivity;
import com.wbx.mall.activity.IntelligentPayListActivity;
import com.wbx.mall.activity.IntelligentServiceActivity;
import com.wbx.mall.activity.InviteActivity;
import com.wbx.mall.activity.LoginActivity;
import com.wbx.mall.activity.MessageCenterActivity;
import com.wbx.mall.activity.MyFreeOrderActivity;
import com.wbx.mall.activity.MyFreeOrderCouponActivity;
import com.wbx.mall.activity.ScanOrderListActivity;
import com.wbx.mall.activity.SignInActivity;
import com.wbx.mall.activity.WinRecordActivity;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseApplication;
import com.wbx.mall.base.BaseFragment;
import com.wbx.mall.bean.UserInfo;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.utils.GlideUtils;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.iosdialog.AlertDialog;
import com.wbx.mall.widget.refresh.BaseRefreshListener;
import com.wbx.mall.widget.refresh.PullToRefreshLayout;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by wushenghui on 2018/2/7.
 */

public class MineFragment extends BaseFragment implements BaseRefreshListener {
    @Bind(R.id.refresh_layout)
    PullToRefreshLayout mRefreshLayout;
    @Bind(R.id.user_name_tv)
    TextView mUserNameTv;
    @Bind(R.id.head_pic_im)
    ImageView headPicIm;
    @Bind(R.id.tv_red_packet)
    TextView tvRedPacket;
    private UserInfo userInfo;
    @Bind(R.id.balance_tv)
    TextView balanceTv;
    @Bind(R.id.tv_integral)
    TextView integralTv;
    @Bind(R.id.rank_name_tv)
    TextView rankNameTv;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onResume() {
        super.onResume();
        getServiceData();
    }

    @Override
    public void initPresenter() {
        mRefreshLayout.setCanLoadMore(false);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void fillData() {

    }

    @Override
    protected void bindEvent() {
        mRefreshLayout.setRefreshListener(this);
    }


    private void getServiceData() {
        new MyHttp().doPost(Api.getDefault().getUserInfo(SPUtils.getSharedStringData(getActivity(), AppConfig.LOGIN_TOKEN)), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                userInfo = result.getObject("data", UserInfo.class);
                pullData();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void pullData() {
        BaseApplication.getInstance().saveObject(userInfo, AppConfig.USER_DATA);
        mUserNameTv.setText(userInfo.getNickname());
        GlideUtils.showMediumPic(getActivity(), headPicIm, userInfo.getFace());
        balanceTv.setText(String.format("%.2f", userInfo.getMoney() / 100.00));
        integralTv.setText("" + userInfo.getIntegral());
        tvRedPacket.setText(String.format("¥%.2f", userInfo.getSubsidy_money() / 100.00));
        rankNameTv.setText(userInfo.getRank_name());
    }

    @Override
    public void refresh() {
        getServiceData();
    }

    @Override
    public void loadMore() {

    }

    @OnClick({R.id.ll_free_activity, R.id.ll_free_sheet, R.id.ll_my_order, R.id.iv_sign_in, R.id.ll_my_integrate, R.id.ll_red_packet, R.id.ll_red_envelope, R.id.ll_my_collection, R.id.ll_my_integral, R.id.ll_help, R.id.ll_my_book, R.id.ll_message, R.id.ll_address_manager, R.id.ll_personal, R.id.mine_out_login_btn, R.id.ll_scan_order, R.id.ll_invite, R.id.ll_payment_store})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_free_activity:
                if (LoginUtil.isLogin()) {
                    startActivity(new Intent(getActivity(), MyFreeOrderCouponActivity.class));
                } else {
                    LoginUtil.login();
                }
                break;
//            case R.id.ll_integral_activity:
//                if (LoginUtil.isLogin()) {
//                    MyFreeOrderCouponActivity.actionStart(getContext());
//                } else {
//                    LoginUtil.login();
//                }
//                break;
            case R.id.iv_sign_in:
            case R.id.ll_my_integrate:
                if (LoginUtil.isLogin()) {
                    SignInActivity.actionStart(getActivity());
                } else {
                    LoginUtil.login();
                }
                break;
            case R.id.ll_red_packet:
                WinRecordActivity.actionStart(getContext());
                break;
            case R.id.ll_red_envelope:
                WinRecordActivity.actionStart(getContext());
                break;
            case R.id.ll_my_collection:
                intent = new Intent(getActivity(), CollectionActivity.class);
                intent.putExtra("type", AppConfig.CollectionType.STORE);
                startActivity(intent);
                break;
            case R.id.ll_my_integral:
                startActivity(new Intent(getActivity(), IntegralMallActivity.class));
                break;
//            case R.id.ll_intelligent_pay:
//                IntelligentPayListActivity.actionStart(getContext());
//                break;
            case R.id.ll_help:
                startActivity(new Intent(getActivity(), IntelligentServiceActivity.class));
                break;
            case R.id.ll_my_order:
                startActivity(new Intent(getActivity(), IndexOrderActivity.class));
                break;
            case R.id.ll_my_book:
                startActivity(new Intent(getActivity(), BookSeatOrderActivity.class));
                break;
            case R.id.ll_message:
                MessageCenterActivity.actionStart(getActivity());
                break;
            case R.id.ll_free_sheet:
                if (LoginUtil.isLogin()) {
                    MyFreeOrderActivity.actionStart(getContext());
                } else {
                    LoginUtil.login();
                }
                break;
            case R.id.ll_address_manager:
                startActivity(new Intent(getActivity(), AddressManagerActivity.class));
                break;
            case R.id.ll_personal:
                startActivity(new Intent(getActivity(), AccountInfoActivity.class));
                break;
            case R.id.mine_out_login_btn:
                new AlertDialog(getActivity()).builder()
                        .setTitle("提示")
                        .setMsg("退出当前账号？")
                        .setNegativeButton("再想想", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                EMClient.getInstance().logout(true, new EMCallBack() {
                                    @Override
                                    public void onSuccess() {
                                        clearUserData();
                                    }

                                    @Override
                                    public void onError(int i, String s) {
                                        clearUserData();
                                    }

                                    @Override
                                    public void onProgress(int i, String s) {

                                    }
                                });

                            }
                        }).show();
                break;
            case R.id.ll_scan_order:
                ScanOrderListActivity.actionStart(getActivity());
                break;
            case R.id.ll_invite:
                InviteActivity.actionStart(getContext(), "https://www.wbx365.com/Wbxwaphome/purchase/index.html", false);
                break;
            case R.id.ll_payment_store:
                IntelligentPayListActivity.actionStart(getActivity());
                break;
        }
    }

    private void clearUserData() {
        new MyHttp().doPost(Api.getDefault().logout(SPUtils.getSharedStringData(getActivity(), AppConfig.LOGIN_TOKEN), "android", JPushInterface.getRegistrationID(getContext())), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {

            }

            @Override
            public void onError(int code) {

            }
        });
        SPUtils.setSharedBooleanData(getActivity(), AppConfig.LOGIN_STATE, false);
        SPUtils.setSharedStringData(getActivity(), AppConfig.LOGIN_TOKEN, "");
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.putExtra("isMainTo", false);
        startActivity(intent);
        getActivity().finish();
    }
}
