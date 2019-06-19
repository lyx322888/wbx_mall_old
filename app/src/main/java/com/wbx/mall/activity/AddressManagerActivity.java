package com.wbx.mall.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.adapter.AddressAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.bean.AddressInfo;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.LoadingDialog;
import com.wbx.mall.widget.iosdialog.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wushenghui on 2017/7/17.
 * 地址管理
 */

public class AddressManagerActivity extends BaseActivity implements BaseAdapter.ItemClickListener {
    @Bind(R.id.address_recycle_view)
    RecyclerView mRecycleView;
    private List<AddressInfo> addressInfoList = new ArrayList<>();
    private AddressAdapter mAdapter;
    private boolean orderSelect;

    @Override
    public int getLayoutId() {
        return R.layout.activity_address_manager;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        mRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new AddressAdapter(addressInfoList, mContext);
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        addressInfoList.clear();
        getServiceData();
    }

    @Override
    public void fillData() {
        orderSelect = getIntent().getBooleanExtra("orderSelect", false);
    }

    private void getServiceData() {
        LoadingDialog.showDialogForLoading(mActivity, "加载中", true);
        new MyHttp().doPost(Api.getDefault().getAddressList(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN)), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                addressInfoList.addAll(JSONArray.parseArray(result.getString("data"), AddressInfo.class));
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    @Override
    public void setListener() {
        mAdapter.setOnItemClickListener(R.id.item_address_del_tv, AddressManagerActivity.this);
        mAdapter.setOnItemClickListener(R.id.item_address_edit_tv, AddressManagerActivity.this);
        mAdapter.setOnItemClickListener(R.id.root_view, new BaseAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                if (orderSelect) {
                    Intent intent = new Intent();
                    intent.putExtra("selectAddress", addressInfoList.get(position));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });


        mAdapter.setOnItemClickListener(R.id.item_address_default_cb, new BaseAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                if (addressInfoList.get(position).getDefaultX() == 1) {
                    showShortToast("选中项已经是默认地址啦");
                    mAdapter.notifyDataSetChanged();
                    return;
                }
                setDefaultAddress(position);
            }
        });

    }

    private void setDefaultAddress(final int position) {
        new MyHttp().doPost(Api.getDefault().setDefaultAddress(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), addressInfoList.get(position).getId()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                for (AddressInfo addressInfo : addressInfoList) {
                    if (addressInfo.getId() != addressInfoList.get(position).getId()) {
                        addressInfo.setDefaultX(AppConfig.addressIsDefault.unDefault);
                    } else {
                        addressInfo.setDefaultX(AppConfig.addressIsDefault.isDefault);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    @Override
    public void onItemClicked(View view, final int position) {
        switch (view.getId()) {
            case R.id.item_address_del_tv:
                new AlertDialog(mActivity).builder()
                        .setTitle("提示")
                        .setMsg("删除收货地址？")
                        .setNegativeButton("再想想", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                delAddress(position);
                            }
                        }).show();

                break;
            case R.id.item_address_edit_tv:
                editAddress(position);
                break;
        }
    }

    private void editAddress(int position) {
        EditAddressActivity.actionStart(mActivity, addressInfoList.get(position));
    }

    private void delAddress(final int position) {
        new MyHttp().doPost(Api.getDefault().deleteAddress(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), addressInfoList.get(position).getId()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                addressInfoList.remove(position);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    @OnClick({R.id.rl_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_right:
                EditAddressActivity.actionStart(mContext);
                break;
        }
    }
}
