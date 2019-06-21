package com.wbx.mall.fragment;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.wbx.mall.R;
import com.wbx.mall.activity.BusinessCertificationActivity;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseFragment;
import com.wbx.mall.bean.ShopInfo2;
import com.wbx.mall.utils.DisplayUtil;
import com.wbx.mall.utils.GlideUtils;
import com.wbx.mall.utils.ScannerUtils;
import com.wbx.mall.utils.ShareUtils;
import com.wbx.mall.utils.ToastUitl;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPreview;

public class MerchantInfoFragment extends BaseFragment {
    private static final String SHOP_INFO = "SHOP_INFO";
    @Bind(R.id.ll_container_shop_pic)
    LinearLayout llContainerShopPic;
    @Bind(R.id.ll_shop_pic)
    LinearLayout llShopPic;
    @Bind(R.id.tv_shop_name)
    TextView tvShopName;
    @Bind(R.id.tv_goods_num)
    TextView tvGoodsNum;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_business_time)
    TextView tvBusinessTime;
    private ShopInfo2 shopInfo;
    private AlertDialog codeDialog;
    private ArrayList<String> lstPhotos;

    public MerchantInfoFragment() {
    }

    public static MerchantInfoFragment newInstance(ShopInfo2 shopInfo) {
        MerchantInfoFragment fragment = new MerchantInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(SHOP_INFO, shopInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            shopInfo = (ShopInfo2) getArguments().getSerializable(SHOP_INFO);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_merchant_info;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void fillData() {
        lstPhotos = new ArrayList<>();
        lstPhotos.add(shopInfo.getPhoto());
        if (shopInfo.getPhotos() != null && shopInfo.getPhotos().size() > 0) {
            lstPhotos.addAll(shopInfo.getPhotos());
        }
        if (lstPhotos.size() == 0) {
            llShopPic.setVisibility(View.GONE);
        } else {
            addPic();
        }
        tvShopName.setText(shopInfo.getShop_name());
        tvGoodsNum.setText(String.valueOf(shopInfo.getGoods_num() + "件"));
        tvAddress.setText(shopInfo.getAddr());
        tvBusinessTime.setText(shopInfo.getBusiness_time());
    }

    private void addPic() {
        llContainerShopPic.removeAllViews();
        for (int i = 0; i < lstPhotos.size(); i++) {
            String path = lstPhotos.get(i);
            ImageView imageView = new ImageView(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DisplayUtil.dip2px(76), DisplayUtil.dip2px(76));
            if (i == 0) {
                layoutParams.leftMargin = DisplayUtil.dip2px(16);
            } else {
                layoutParams.leftMargin = DisplayUtil.dip2px(8);
            }
            if (i == lstPhotos.size() - 1) {
                layoutParams.rightMargin = DisplayUtil.dip2px(16);
            }
            GlideUtils.showMediumPic(getContext(), imageView, path);
            final int finalI = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PhotoPreview.builder()
                            .setPhotos(lstPhotos)
                            .setCurrentItem(finalI)
                            .setShowDeleteButton(false)
                            .start(getContext(), MerchantInfoFragment.this);
                }
            });
            llContainerShopPic.addView(imageView, layoutParams);
        }
    }

    @Override
    protected void bindEvent() {

    }

    @OnClick({R.id.ll_address, R.id.ll_see_qrcode, R.id.ll_call_phone, R.id.ll_see_qualification})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_address:
                openAddress();
                break;
            case R.id.ll_see_qrcode:
                seeQrCode();
                break;
            case R.id.ll_call_phone:
                callPhone();
                break;
            case R.id.ll_see_qualification:
                BusinessCertificationActivity.actionStart(getContext(), shopInfo);
                break;
        }
    }

    private void callPhone() {
        if (shopInfo.getIs_buy() == 1) {
            if (!TextUtils.isEmpty(shopInfo.getTel())) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + shopInfo.getTel()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                ToastUitl.showShort("该商家暂未提供联系方式");
            }
        } else {
            ToastUitl.showShort("温馨提示：请下单后再试试吧！");
        }
    }

    private void seeQrCode() {
        if (shopInfo == null || TextUtils.isEmpty(shopInfo.getQr_url())) {
            ToastUitl.showShort("抱歉，该商家暂无提供二维码");
            return;
        }
        final View inflate = getLayoutInflater().inflate(R.layout.max_card_layout, null);
        if (null == codeDialog) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(inflate);
            codeDialog = builder.create();
            ImageView qrCodeIm = inflate.findViewById(R.id.qr_code_im);
            TextView qrCodeShopNameTv = inflate.findViewById(R.id.qr_code_shop_name_tv);
            Button saveCodeBtn = inflate.findViewById(R.id.save_code_btn);
            saveCodeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    inflate.setDrawingCacheEnabled(true);
                    Bitmap mBitmap = inflate.getDrawingCache();
                    ScannerUtils.saveImageToGallery(getContext(), mBitmap, ScannerUtils.ScannerType.RECEIVER);
                }
            });
            qrCodeShopNameTv.setText(shopInfo.getShop_name());
            GlideUtils.showBigPic(getContext(), qrCodeIm, shopInfo.getSmall_routine_photo());
            inflate.findViewById(R.id.share_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    share();
                    codeDialog.dismiss();
                }
            });
            inflate.findViewById(R.id.dialog_fragment_goods_dismiss_im).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    codeDialog.dismiss();
                }
            });
        }
        codeDialog.show();
    }

    private void share() {
        ShareUtils.getInstance().shareImage(getContext(), "我在微百姓购物，方便、实惠！推荐你也一起来使用吧！", shopInfo.getSmall_routine_photo());
    }

    private void openAddress() {
        if (isInstallMap("com.autonavi.minimap")) {
            //有安装高德地图
            GeocodeSearch geocodeSearch = new GeocodeSearch(getContext());
            geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
                @Override
                public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

                }

                @Override
                public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                    LatLonPoint latLonPoint = geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint();
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("amapuri://route/plan/?dlat=" + latLonPoint.getLatitude() + "&dlon=" + latLonPoint.getLongitude() + "&dev=0&t=0"));
                    intent.setPackage("com.autonavi.minimap");
                    startActivity(intent);
                }
            });
            GeocodeQuery query = new GeocodeQuery(shopInfo.getAddr(), mLocationInfo.getName());
            geocodeSearch.getFromLocationNameAsyn(query);
        } else {
            //没有l安装高德地图
            GeocodeSearch geocodeSearch = new GeocodeSearch(getContext());
            geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
                @Override
                public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

                }

                @Override
                public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                    LatLonPoint latLonPoint = geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint();
//                            String url = "http://uri.amap.com/navigation?from=116.478346,39.997361,&to=116.3246,39.966577,&mode=car&policy=1&src=mypage&coordinate=gaode&callnative=0";
                    String url = String.format("http://uri.amap.com/navigation?from=%f,%f,&to=%f,%f&mode=car&policy=1&src=mypage&coordinate=gaode&callnative=0"
                            , mLocationInfo.getLng()
                            , mLocationInfo.getLat()
                            , latLonPoint.getLongitude()
                            , latLonPoint.getLatitude()
                    );   //指定网址
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);           //指定Action
                    intent.setData(uri);                            //设置Uri
                    startActivity(intent);        //启动Activity
                }
            });
            GeocodeQuery query = new GeocodeQuery(shopInfo.getAddr().replace(" ", ""), mLocationInfo.getName());
            geocodeSearch.getFromLocationNameAsyn(query);
        }
    }

    //是否安装高德地图
    private boolean isInstallMap(String packageName) {
        PackageInfo packageInfo;
        try {
            packageInfo = getContext().getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        return null != packageInfo;
    }
}