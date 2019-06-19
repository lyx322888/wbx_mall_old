package com.wbx.mall.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.wbx.mall.R;
import com.wbx.mall.adapter.IndexCouponAdapter;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseApplication;
import com.wbx.mall.base.BaseFragment;
import com.wbx.mall.bean.HomeCouponBean;
import com.wbx.mall.bean.LocationInfo;
import com.wbx.mall.bean.UserInfo;
import com.wbx.mall.presenter.HomeCouponPresenterImp;
import com.wbx.mall.view.HomeCouponView;

import butterknife.Bind;
import butterknife.OnClick;

public class CouponFragment extends BaseFragment implements HomeCouponView {

    @Bind(R.id.coupon_recycler)
    RecyclerView mRecyclerView;

    @Bind(R.id.star_main)
    FrameLayout mFrameLayout;

    @Bind(R.id.iv_close)
    ImageView iv_close;

    private LocationInfo mLocationInfo;
    private IndexCouponAdapter indexCouponAdapter;
    private UserInfo userInfo;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_coupon;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        mFrameLayout.getBackground().setAlpha(100);
        mFrameLayout.setOnTouchListener(null);
        mFrameLayout.setOnClickListener(null);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HomeCouponPresenterImp presenterImp = new HomeCouponPresenterImp(this);
        mLocationInfo = (LocationInfo) BaseApplication.getInstance().readObject(AppConfig.LOCATION_DATA);
        presenterImp.getHomeCoupon(19);
    }

    @Override
    protected void fillData() {

    }

    @Override
    protected void bindEvent() {

    }

    @OnClick({R.id.iv_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                mFrameLayout.setVisibility(View.GONE);
//                Intent intent=new Intent(getContext(),TestActivity.class);
//                startActivity(intent);
                break;
        }
    }

    @Override
    public void getHomeCoupon(HomeCouponBean homeCouponBean) {
        indexCouponAdapter = new IndexCouponAdapter(getActivity(), homeCouponBean.getData());
        mRecyclerView.setAdapter(indexCouponAdapter);
        indexCouponAdapter.setRecyclerViewItemClieck(new IndexCouponAdapter.RecyclerViewItemClieck() {
            @Override
            public void recyclerViewItemClieck(int position, View view, RecyclerView.ViewHolder viewHolder) {

            }
        });


    }
}
