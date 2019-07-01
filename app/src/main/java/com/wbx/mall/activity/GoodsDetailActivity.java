package com.wbx.mall.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.wbx.mall.R;
import com.wbx.mall.adapter.GoodsDetallAdapter;
import com.wbx.mall.adapter.NatureAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.GoodsInfo;
import com.wbx.mall.bean.GoodsInfo2;
import com.wbx.mall.bean.OrderBean;
import com.wbx.mall.bean.SpecInfo;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.presenter.GoodsPresenterImp;
import com.wbx.mall.utils.GlideImageLoader;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.utils.ShareUtils;
import com.wbx.mall.utils.UilImageGetter;
import com.wbx.mall.view.GoodsView;
import com.wbx.mall.widget.LoadingDialog;
import com.wbx.mall.widget.MyScrollview;
import com.wbx.mall.widget.flowLayout.BaseTagAdapter;
import com.wbx.mall.widget.flowLayout.TagFlowLayout;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wushenghui on 2017/7/14.
 * 商品详情
 */

public class GoodsDetailActivity extends BaseActivity implements GoodsView {
    @Bind(R.id.banner)
    Banner mBanner;
    @Bind(R.id.product_detail_sv)
    MyScrollview mScrollView;
    @Bind(R.id.pro_detail_title_view)
    LinearLayout mTitleView;
    @Bind(R.id.detail_goods_name_tv)
    TextView goodsName;
    @Bind(R.id.tv_member_price)
    TextView tvMemberPrice;
    @Bind(R.id.goods_price_tv)
    TextView goodsPriceTv;
    @Bind(R.id.goods_introduce_tv)
    TextView goodsIntroduceTv;
    @Bind(R.id.product_detail_collect_tv)
    TextView collectTv;
    @Bind(R.id.product_detail_sales_num)
    TextView salesNum;
    @Bind(R.id.detail_store_info_layout)
    LinearLayout storeInfoLayout;
    @Bind(R.id.show_num_buy_tv)
    TextView tvBuyNum;
    @Bind(R.id.product_intro_tv)
    TextView introTv;
    @Bind(R.id.iv_member)
    ImageView ivMember;
    @Bind(R.id.add_product_im)
    ImageView addProductIm;
    @Bind(R.id.all_buy)
    RecyclerView allRecycler;
    private String goodsId;
    private GoodsInfo mGoodsInfo;
    private List<String> images = new ArrayList<>();
    private int shopId;
    private HashMap<String, Object> mSpecParams = new HashMap<>();
    private SpecInfo selectSpec;//选中的规格Id
    private GoodsInfo2 goodsInfo;
    private boolean isShopMemberUser;
    private LinkedHashMap<String, GoodsInfo2.Nature_attr> hmSelectNature;//选中的规格属性

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {

    }

    @Override
    public void fillData() {
        goodsId = getIntent().getStringExtra("goodsId");
        shopId = getIntent().getIntExtra("shopId", 0);
        isShopMemberUser = getIntent().getBooleanExtra("isShopMemberUser", false);

        GoodsPresenterImp goodsPresenterImp = new GoodsPresenterImp(this);
        goodsPresenterImp.getGoods(shopId + "", LoginUtil.getLoginToken(), AppConfig.pageNum, AppConfig.pageSize, 0);
        allRecycler.setLayoutManager(new LinearLayoutManager(this));

        mSpecParams.put("type", "shop");
        mSpecParams.put("goods_id", goodsId);
        goodsInfo = (GoodsInfo2) getIntent().getSerializableExtra("goodsInfo");
        int count = 0;
        for (String s : goodsInfo.getHmBuyNum().keySet()) {
            count += goodsInfo.getHmBuyNum().get(s);
        }
        tvBuyNum.setText(String.valueOf(count));
        LoadingDialog.showDialogForLoading(mActivity, "加载中...", true);
        new MyHttp().doPost(Api.getDefault().getGoodsDetail(goodsId), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                mGoodsInfo = result.getObject("data", GoodsInfo.class);
                mGoodsInfo.setProduct_id(mGoodsInfo.getGoods_id());
                mGoodsInfo.setProduct_name(mGoodsInfo.getTitle());
                pullData();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void pullData() {
        collectTv.setText(String.format("收藏%d人", mGoodsInfo.getFavorites()));
        if (LoginUtil.isLogin() && LoginUtil.getUserInfo().getIs_salesman() == 1) {
            salesNum.setVisibility(View.VISIBLE);
            salesNum.setText(String.format("月销量%d笔", mGoodsInfo.getSold_num()));
        } else {
            salesNum.setVisibility(View.GONE);
        }
        addShowState();
        initProductPic();
        putGoodsData();
    }

    @Override
    public void setListener() {
        mScrollView.setScrollViewListener(new MyScrollview.ScrollViewListener() {
            @Override
            public void onScrollChanged(MyScrollview scrollView, int x, int scrollY, int oldx, int oldy) {
                if (scrollY <= mTitleView.getMeasuredHeight()) {
                    float persent = scrollY * 1.5f / (mTitleView.getTop() + mTitleView.getMeasuredHeight());
                    int alpha = (int) (255 * persent);
                    int color = Color.argb(alpha, 6, 193, 174);
                    if (alpha <= 255) {
                        mTitleView.setBackgroundColor(color);
                    }
                } else {
                    int color = Color.argb(255, 6, 193, 174);
                    mTitleView.setBackgroundColor(color);
                }
            }
        });
        addProductIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBuyNum();
            }
        });
    }

    private void putGoodsData() {
        goodsName.setText(mGoodsInfo.getTitle());
        if (mGoodsInfo.getIs_shop_member() == 1) {
            tvMemberPrice.setText(String.format("%.2f", mGoodsInfo.getShop_member_price() / 100.00));
            goodsPriceTv.setText(String.format("商城价¥%.2f", (mGoodsInfo.getSales_promotion_is() == 1 ? mGoodsInfo.getSales_promotion_price() : mGoodsInfo.getMall_price()) / 100.00));
            if (isShopMemberUser && mGoodsInfo.getIs_shop_member() == 1) {
                goodsPriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
        } else {
            ivMember.setVisibility(View.GONE);
            tvMemberPrice.setText(String.format("%.2f", (mGoodsInfo.getSales_promotion_is() == 1 ? mGoodsInfo.getSales_promotion_price() : mGoodsInfo.getMall_price()) / 100.00));
            goodsPriceTv.setVisibility(View.INVISIBLE);
        }
        goodsIntroduceTv.setText(Html.fromHtml(mGoodsInfo.getDetails(), new UilImageGetter(mActivity, goodsIntroduceTv), null));
        introTv.setText(mGoodsInfo.getIntro());
    }

    private void initProductPic() {
        if (mGoodsInfo.getGoods_photo() != null && mGoodsInfo.getGoods_photo().size() != 0) {
            images.addAll(mGoodsInfo.getGoods_photo());
        } else {
            images.add(mGoodsInfo.getPhoto());
        }
        mBanner.setImages(images).setDelayTime(3000).setImageLoader(new GlideImageLoader()).start();
    }

    //添加店铺已有标识
    private void addShowState() {
        //认证商品
        if (mGoodsInfo.getIs_vs1() == 1) {
            addView("认证商品  ");
        }
        if (mGoodsInfo.getIs_vs2() == 1) {
            //正品保障
            addView("正品保障  ");
        }
        if (mGoodsInfo.getIs_vs3() == 1) {
            //假一赔十
            addView("假一赔十  ");
        }
        if (mGoodsInfo.getIs_vs4() == 1) {
            //当日到达
            addView("当日到达  ");
        }
        if (storeInfoLayout.getChildCount() < 5) {
            //只显示4个
            if (mGoodsInfo.getIs_vs5() == 1) {
                //当日到达
                addView("免运费  ");
            }
            if (mGoodsInfo.getIs_vs6() == 1) {
                //当日到达
                addView("货到付款  ");
            }
        }
    }

    private void addView(String stateStr) {
        TextView textview = new TextView(mActivity);
        textview.setTextSize(12);
        textview.setText(stateStr);
        Drawable leftDrawable = getResources().getDrawable(R.drawable.product_certification);
        leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
        textview.setCompoundDrawables(leftDrawable, null, null, null);
        textview.setCompoundDrawablePadding(5);
        storeInfoLayout.addView(textview);
    }

    @Override
    public void getGoods(GoodsInfo2 goodsInfo2) {
        GoodsDetallAdapter goodsDetallAdapter = new GoodsDetallAdapter(mContext, (List<GoodsInfo2>) goodsInfo);
        allRecycler.setAdapter(goodsDetallAdapter);
    }


    @OnClick({R.id.detail_store_tv, R.id.sub_product_im, R.id.add_car_btn, R.id.now_buy_btn, R.id.collect_product_tv, R.id.forwarding})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_store_tv:
                finish();
                break;
//            case R.id.share_wx_circle_tv:
//                shareWx(SendMessageToWX.Req.WXSceneTimeline);
//                break;
//            case R.id.share_wx_friends_tv:
//                shareWx(SendMessageToWX.Req.WXSceneSession);
//                break;
//            case R.id.share_wx_collect_tv:
//                shareWx(SendMessageToWX.Req.WXSceneFavorite);
//                break;
            case R.id.forwarding:
                showBottomDialog();
                break;
            case R.id.sub_product_im:
                reduceBuyNum();
                break;
            case R.id.add_car_btn:
                addCar();
                break;
            case R.id.now_buy_btn:
                buyNow();
                break;
            case R.id.collect_product_tv:
                collect();
                break;
        }

    }

    @SuppressLint("NewApi")
    private void reduceBuyNum() {
        if (!SPUtils.getSharedBooleanData(mActivity, AppConfig.LOGIN_STATE)) {
            Intent intent = new Intent(mActivity, LoginActivity.class);
            intent.putExtra("isMainTo", true);
            startActivity(intent);
            return;
        }
        if (goodsInfo.getIs_attr() == 1 || (goodsInfo.getNature() != null && goodsInfo.getNature().size() > 0)) {
            //多规格
            getGoodsSpec();
        } else {
            //不是多规格
            int count = goodsInfo.getHmBuyNum().get(goodsInfo.getGoods_id() + ",,");
            if (count == 0) {
                showShortToast("购买数量不能小于0");
                return;
            }
            count--;
            goodsInfo.getHmBuyNum().put(goodsInfo.getGoods_id() + ",,", count);
            addGoodsToServer();
            tvBuyNum.setText(String.valueOf(count));
            if (count == 0) {
                goodsInfo.getHmBuyNum().remove(goodsInfo.getGoods_id() + ",,", count);
            }
        }
    }

    private void addBuyNum() {
        if (!SPUtils.getSharedBooleanData(mActivity, AppConfig.LOGIN_STATE)) {
            Intent intent = new Intent(mActivity, LoginActivity.class);
            intent.putExtra("isMainTo", true);
            startActivity(intent);
            return;
        }
        if (goodsInfo.getIs_attr() == 1 || (goodsInfo.getNature() != null && goodsInfo.getNature().size() > 0)) {
            //多规格
            getGoodsSpec();
        } else {
            //不是多规格
            int count = 0;
            if (goodsInfo.getHmBuyNum().containsKey(goodsInfo.getGoods_id() + ",,")) {
                count = goodsInfo.getHmBuyNum().get(goodsInfo.getGoods_id() + ",,");
            }
            if (goodsInfo.getIs_seckill() == 1 && goodsInfo.getLimitations_num() != 0) {
                //秒杀商品
                if (count + 1 > goodsInfo.getLimitations_num()) {
                    Toast.makeText(mContext, "不能超过限购数量", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            if (goodsInfo.getIs_use_num() == 1) {
                //开启库存
                if (count + 1 > goodsInfo.getInventory_num()) {
                    Toast.makeText(mContext, "库存不足", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            count++;
            goodsInfo.getHmBuyNum().put(goodsInfo.getGoods_id() + ",,", count);
            addGoodsToServer();
            tvBuyNum.setText(String.valueOf(count));
        }
    }

    //获得商品规格
    private void getGoodsSpec() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View inflate = getLayoutInflater().inflate(R.layout.dialog_product_spec, null);
        builder.setView(inflate);
        TextView tvName = inflate.findViewById(R.id.product_name_tv);
        RecyclerView rvNature = inflate.findViewById(R.id.recycler_view);
        tvName.setText(goodsInfo.getTitle());
        rvNature.setLayoutManager(new LinearLayoutManager(mContext));
        NatureAdapter natureAdapter = new NatureAdapter(R.layout.item_nature, goodsInfo.getNature());
        natureAdapter.setOnNatureChangeListener(new NatureAdapter.OnNatureChangeListener() {
            @Override
            public void onNatureChange(LinkedHashMap<String, GoodsInfo2.Nature_attr> selectNature) {
                hmSelectNature = selectNature;
                changeSpec(inflate);
            }
        });
        rvNature.setAdapter(natureAdapter);
        hmSelectNature = new LinkedHashMap<>();
        if (goodsInfo.getIs_attr() == 1) {
            View specHead = getLayoutInflater().inflate(R.layout.item_nature, null);
            TagFlowLayout tagFlowLayout = specHead.findViewById(R.id.laybelLayout);
            natureAdapter.addHeaderView(specHead);
            BaseTagAdapter<SpecInfo> baseTagAdapter = new BaseTagAdapter<SpecInfo>(mContext, goodsInfo.getGoods_attr()) {
                @Override
                public void setText(TextView textView, int position, SpecInfo specInfo) {
                    if (specInfo.getIs_seckill() == 1) {
                        textView.setText(Html.fromHtml("<font color=#ff0000>(秒)</font>" + specInfo.getAttr_name()));
                    } else {
                        textView.setText(specInfo.getAttr_name());
                    }
                }
            };
            tagFlowLayout.setAdapter(baseTagAdapter);
            tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                @Override
                public void onSelected(Set<Integer> selectPosSet) {
                    if (selectPosSet.size() == 0) {
                        return;
                    }
                    SpecInfo specInfo = goodsInfo.getGoods_attr().get(selectPosSet.iterator().next());
                    selectSpec = specInfo;
                    changeSpec(inflate);
                }
            });
        }
        //默认选中第一个
        if (goodsInfo.getIs_attr() == 1) {
            selectSpec = goodsInfo.getGoods_attr().get(0);
        }
        if (goodsInfo.getNature() != null && goodsInfo.getNature().size() > 0) {
            for (GoodsInfo2.Nature nature : goodsInfo.getNature()) {
                hmSelectNature.put(nature.getItem_id(), nature.getNature_arr().get(0));
            }
        }
        changeSpec(inflate);
        final AlertDialog specDialog = builder.create();
        if (!specDialog.isShowing()) {
            specDialog.show();
        }
        inflate.findViewById(R.id.dialog_fragment_goods_dismiss_im).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                specDialog.dismiss();
            }
        });
        inflate.findViewById(R.id.spec_add_im).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer currentSpecBuyNum = 0;
                if (goodsInfo.getHmBuyNum().containsKey(getGoodsCurrentSelectedSpecKey())) {
                    currentSpecBuyNum = goodsInfo.getHmBuyNum().get(getGoodsCurrentSelectedSpecKey());
                }
                if (goodsInfo.getIs_attr() == 1) {
                    if (selectSpec.getIs_seckill() == 1 && selectSpec.getLimitations_num() != 0) {
                        //秒杀商品
                        if (currentSpecBuyNum + 1 > selectSpec.getLimitations_num()) {
                            showShortToast("不能超过限购数量");
                            return;
                        }
                    }
                    if (goodsInfo.getIs_use_num() == 1) {
                        //开启库存
                        if (currentSpecBuyNum + 1 > selectSpec.getInventory_num()) {
                            showShortToast("库存不足");
                            return;
                        }
                    }
                } else {
                    if (goodsInfo.getIs_seckill() == 1 && goodsInfo.getLimitations_num() != 0) {
                        //秒杀商品
                        if (currentSpecBuyNum + 1 > goodsInfo.getLimitations_num()) {
                            showShortToast("不能超过限购数量");
                            return;
                        }
                    }
                    if (goodsInfo.getIs_use_num() == 1) {
                        //开启库存
                        if (currentSpecBuyNum + 1 > goodsInfo.getInventory_num()) {
                            showShortToast("库存不足");
                            return;
                        }
                    }
                }
                HashMap<String, Integer> hashMap = new HashMap<>();
                hashMap.put(getGoodsCurrentSelectedSpecKey(), currentSpecBuyNum + 1);
                goodsInfo.getHmBuyNum().putAll(hashMap);
                setTotalNum();
                addGoodsToServer();
                changeSpec(inflate);
            }
        });
        inflate.findViewById(R.id.spec_sub_im).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer currentSpecBuyNum = 0;
                if (goodsInfo.getHmBuyNum().containsKey(getGoodsCurrentSelectedSpecKey())) {
                    currentSpecBuyNum = goodsInfo.getHmBuyNum().get(getGoodsCurrentSelectedSpecKey());
                }
                if (currentSpecBuyNum == 0) {
                    showShortToast("购买数量不能小于0");
                    return;
                }
                HashMap<String, Integer> hashMap = new HashMap<>();
                hashMap.put(getGoodsCurrentSelectedSpecKey(), currentSpecBuyNum - 1);
                goodsInfo.getHmBuyNum().putAll(hashMap);
                setTotalNum();
                addGoodsToServer();
                changeSpec(inflate);
                if (currentSpecBuyNum - 1 == 0) {
                    goodsInfo.getHmBuyNum().remove(getGoodsCurrentSelectedSpecKey());
                }
            }
        });
        specDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                selectSpec = null;
                hmSelectNature = null;
            }
        });
    }

    private void setTotalNum() {
        int num = 0;
        for (String key : goodsInfo.getHmBuyNum().keySet()) {
            num += goodsInfo.getHmBuyNum().get(key);
        }
        tvBuyNum.setText(String.valueOf(num));
    }

    private void changeSpec(View inflate) {
        TextView tvMemberPrice = inflate.findViewById(R.id.tv_member_price);
        TextView tvPrice = inflate.findViewById(R.id.price_tv);
        TextView tvSpec = inflate.findViewById(R.id.tv_spec);
        TextView tvBuyNum = inflate.findViewById(R.id.spec_buy_num_tv);
        if (goodsInfo.getIs_attr() == 1) {
            double money = 0;
            if (selectSpec.getIs_seckill() == 1) {
                money = selectSpec.getSeckill_price() / 100.00;
            } else if (selectSpec.getSales_promotion_is() == 1) {
                money = selectSpec.getSales_promotion_price() / 100.00;
            } else if (selectSpec.getMall_price() != 0) {
                money = selectSpec.getMall_price() / 100.00;
            }
            if (selectSpec.getIs_shop_member() == 1) {
                tvMemberPrice.setVisibility(View.VISIBLE);
                tvMemberPrice.setText(String.format("会员价¥ %.2f", selectSpec.getShop_member_price() / 100.00));
                if (isShopMemberUser) {
                    tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    tvPrice.getPaint().setFlags(0);
                }
            } else {
                tvMemberPrice.setVisibility(View.GONE);
                tvPrice.getPaint().setFlags(0);
            }
            tvPrice.setText(String.format("¥ %.2f", money));
        } else {
            if (goodsInfo.getIs_shop_member() == 1) {
                tvMemberPrice.setVisibility(View.VISIBLE);
                tvMemberPrice.setText(String.format("会员价¥ %.2f", goodsInfo.getShop_member_price() / 100.00));
                if (isShopMemberUser) {
                    tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    tvPrice.getPaint().setFlags(0);
                }
            } else {
                tvMemberPrice.setVisibility(View.GONE);
                tvPrice.getPaint().setFlags(0);
            }
            tvPrice.setText(String.format("¥ %.2f", goodsInfo.getPrice() / 100.00));
        }
        StringBuilder sbSpec = new StringBuilder();
        sbSpec.append("(");
        if (selectSpec != null) {
            sbSpec.append(selectSpec.getAttr_name());
            sbSpec.append("+");
        }
        if (hmSelectNature != null && hmSelectNature.size() > 0) {
            for (String key : hmSelectNature.keySet()) {
                sbSpec.append(hmSelectNature.get(key).getNature_name());
                sbSpec.append("+");
            }
        }
        sbSpec.deleteCharAt(sbSpec.length() - 1);
        sbSpec.append(")");
        tvSpec.setText(sbSpec.toString());
        if (goodsInfo.getHmBuyNum().containsKey(getGoodsCurrentSelectedSpecKey())) {
            tvBuyNum.setText("" + goodsInfo.getHmBuyNum().get(getGoodsCurrentSelectedSpecKey()));
        } else {
            tvBuyNum.setText("0");
        }
    }

    public String getGoodsCurrentSelectedSpecKey() {
        StringBuilder natureId = new StringBuilder();
        if (hmSelectNature != null && hmSelectNature.size() > 0) {
            for (String key : hmSelectNature.keySet()) {
                natureId.append(hmSelectNature.get(key).getNature_id());
                natureId.append("+");
            }
            natureId.deleteCharAt(natureId.length() - 1);
        }
        return goodsInfo.getGoods_id() + "," + (selectSpec != null ? selectSpec.getAttr_id() : "") + "," + natureId.toString();
    }


    private void collect() {
        if (!SPUtils.getSharedBooleanData(mActivity, AppConfig.LOGIN_STATE)) {
            Intent intent = new Intent(mActivity, LoginActivity.class);
            intent.putExtra("isMainTo", true);
            startActivity(intent);
            return;
        }
        LoadingDialog.showDialogForLoading(mActivity, "", true);
        new MyHttp().doPost(Api.getDefault().collectProduct(SPUtils.getSharedStringData(mActivity, AppConfig.LOGIN_TOKEN), goodsId), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                showShortToast(result.getString("msg"));
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void shareWx(int flag) {
        String imageUrl = null;
        if (null != mGoodsInfo.getPics() && mGoodsInfo.getPics().size() > 0) {
            imageUrl = mGoodsInfo.getPics().get(0).getPhoto();
        }
        ShareUtils.getInstance().doShare(this, "我在微百姓购物，方便、实惠！推荐你也一起来使用吧！", "", imageUrl, "http://www.wbx365.com/wap/mall/detail/goods_id/" + goodsId + ".html", flag);
    }

    private void addCar() {
        addProductIm.performClick();
    }

    //立即购买
    private void buyNow() {
        if (!SPUtils.getSharedBooleanData(mActivity, AppConfig.LOGIN_STATE)) {
            Intent intent = new Intent(mActivity, LoginActivity.class);
            intent.putExtra("isMainTo", true);
            startActivity(intent);
            return;
        }
        LoadingDialog.showDialogForLoading(mActivity, "提交中...", true);

        HashMap<String, List<OrderBean>> hashMap = new HashMap<>();
        ArrayList<OrderBean> lstGoods = new ArrayList<>();
        for (String key : goodsInfo.getHmBuyNum().keySet()) {
            String[] split2 = key.split(",");
            OrderBean orderBean = new OrderBean();
            if (split2.length >= 2) {
                String attrId = split2[1];
                orderBean.setAttr_id(attrId);
            }
            orderBean.setGoods_id(goodsInfo.getGoods_id());
            orderBean.setNum(goodsInfo.getHmBuyNum().get(key));
            if (split2.length >= 3) {
                String[] natureId = split2[2].split("\\+");
                ArrayList<String> lstNature = new ArrayList<>();
                if (natureId.length > 0) {
                    for (String s : natureId) {
                        lstNature.add(s);
                    }
                }
                orderBean.setNature(lstNature);
            }
            lstGoods.add(orderBean);
        }
        hashMap.put(String.valueOf(shopId), lstGoods);
        String orderGoodsJson = new Gson().toJson(hashMap);
        new MyHttp().doPost(Api.getDefault().createOrder(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), orderGoodsJson, getIntent().getStringExtra("bookId")), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                String orderId = result.getJSONObject("data").getString("order_id");
                Intent intent = new Intent(mActivity, SubmitOrderActivity.class);
                intent.putExtra("isPhysical", true);
                intent.putExtra("orderId", orderId);
                intent.putExtra("isBook", !TextUtils.isEmpty(getIntent().getStringExtra("bookId")));
                startActivity(intent);
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    public void addGoodsToServer() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("login_token", SPUtils.getSharedStringData(this, AppConfig.LOGIN_TOKEN));
        params.put("goods_id", goodsInfo.getGoods_id());
        if (goodsInfo.getIs_attr() == 1) {
            params.put("attr_id", selectSpec.getAttr_id());
        }
        params.put("type", 2);//非菜市场
        params.put("shop_id", shopId);
        params.put("num", goodsInfo.getHmBuyNum().get(getGoodsCurrentSelectedSpecKey()));
        ArrayList<String> lstNature = new ArrayList<>();
        if (hmSelectNature != null) {
            //通过多规格弹窗的方式更改数量
            for (String s : hmSelectNature.keySet()) {
                lstNature.add(hmSelectNature.get(s).getNature_id());
            }
        } else if (goodsInfo.getSelected_nature_ids() != null && goodsInfo.getSelected_nature_ids().size() > 0) {
            //在购物车中直接更改数量
            for (String key : goodsInfo.getHmBuyNum().keySet()) {//key的格式：goodsId,attrId,natureId+natureId+natureId
                String[] split = key.split(",");
                if (split.length >= 3) {
                    String natureId = split[2];
                    String[] split1 = natureId.split("\\+");
                    for (String s : split1) {
                        lstNature.add(s);
                    }
                }
            }
        }
        if (lstNature.size() > 0) {
            params.put("nature", new Gson().toJson(lstNature));
        }
        new MyHttp().doPost(Api.getDefault().addCart(params), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
            }

            @Override
            public void onError(int code) {
            }
        });
    }

    private void showBottomDialog() {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this, R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this, R.layout.dialog_custom_layout, null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        dialog.findViewById(R.id.share_wx_friends_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareWx(SendMessageToWX.Req.WXSceneSession);
            }
        });

        dialog.findViewById(R.id.share_wx_circle_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareWx(SendMessageToWX.Req.WXSceneTimeline);
            }
        });
        dialog.findViewById(R.id.iv_guan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("goodsInfo", goodsInfo);
        setResult(RESULT_OK, intent);
        super.finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBanner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();
    }
}