package com.wbx.mall.fragment;


import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.qiniu.android.utils.StringUtils;
import com.wbx.mall.R;
import com.wbx.mall.activity.StoreDetailActivity;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseFragment;
import com.wbx.mall.bean.CateInfo;
import com.wbx.mall.bean.GoodsInfo2;
import com.wbx.mall.utils.KeyBordUtil;
import com.wbx.mall.widget.ShopGoodsContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

public class GoodsFragment extends BaseFragment {
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.list_container)
    ShopGoodsContainer shopGoodsContainer;
    private StoreDetailActivity storeDetailActivity;
    private List<GoodsInfo2> lstGoods = new ArrayList<>();
    private List<CateInfo> lstCate = new ArrayList<>();
    private HashMap<Integer, List<GoodsInfo2>> goodsHm = new HashMap<>();

    public GoodsFragment() { }

    public static GoodsFragment newInstance() {
        GoodsFragment fragment = new GoodsFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_goods;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void fillData() {
        storeDetailActivity = (StoreDetailActivity) getActivity();
        if (storeDetailActivity == null || storeDetailActivity.storeDetailBean == null) {
            return;
        }
        updateUI();
    }

    private void updateUI() {

        lstCate.addAll(storeDetailActivity.storeDetailBean.getCate());
        lstGoods.addAll(storeDetailActivity.storeDetailBean.getGoods());
        shopGoodsContainer.setData(storeDetailActivity, storeDetailActivity.storeDetailBean.getDetail());
        shopGoodsContainer.setGoodsAdapter(storeDetailActivity.storeDetailBean.getDetail().getGrade_id() == AppConfig.StoreType.FOOD_STREET, storeDetailActivity.storeDetailBean.getDetail().getIs_shop_member_user() == 1, (StoreDetailActivity) getActivity());
        shopGoodsContainer.setGoods(lstGoods);
        shopGoodsContainer.goodsAdapter.setNewData(lstGoods);
        classify();
        shopGoodsContainer.setMenu(lstCate);
        shopGoodsContainer.typeAdapter.setNewData(lstCate);
    }

    //根据类别分类商品
    private void classify() {
        List<GoodsInfo2> secKillGoodsList = new ArrayList<>();
        List<GoodsInfo2> promotionGoodsList = new ArrayList<>();
        goodsHm.put(0, lstGoods);//添加全部的
        lstCate.add(0, new CateInfo("0", "全部"));
        for (GoodsInfo2 goodsInfo : lstGoods) {
            if (goodsInfo.getIs_seckill() == 1) {
                //是否存在秒杀
                secKillGoodsList.add(goodsInfo);
            }
            if (goodsInfo.getSales_promotion_is() == 1) {
                //是否存在促销
                promotionGoodsList.add(goodsInfo);
            }
        }
        goodsHm.put(1, secKillGoodsList);//添加秒杀数据
        goodsHm.put(2, promotionGoodsList);//添加促销数据
        if (goodsHm.get(1).size() != 0) {
            //存在秒杀秒杀数据
            lstCate.add(1, new CateInfo("1", "秒杀"));
        }
        if (goodsHm.get(2).size() != 0) {
            //存在促销数据
            lstCate.add(1, new CateInfo("2", "促销"));
        }
    }

    @Override
    protected void bindEvent() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    search(textView.getText().toString());
                    KeyBordUtil.hideSoftKeyboard(etSearch);
                }
                return false;
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String inputStr = charSequence.toString();
                if (StringUtils.isNullOrEmpty(inputStr) && shopGoodsContainer.goodsAdapter != null) {
                    shopGoodsContainer.goodsAdapter.setNewData(lstGoods);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void search(String keyWord) {
        List<GoodsInfo2> searchGoodsInfo = new ArrayList<>();
        for (GoodsInfo2 goodsInfo : lstGoods) {
            if (!TextUtils.isEmpty(goodsInfo.getTitle()) && goodsInfo.getTitle().contains(keyWord)) {
                searchGoodsInfo.add(goodsInfo);
            }
        }
        shopGoodsContainer.goodsAdapter.setNewData(searchGoodsInfo);
    }

    public void updateMenuNum(List<GoodsInfo2> lstSelectGoods) {
        for (CateInfo cateInfo : lstCate) {
            cateInfo.setBuy_num(0);
        }
        for (GoodsInfo2 goodsInfo : lstSelectGoods) {
            for (CateInfo cateInfo : lstCate) {
                if (!TextUtils.isEmpty(goodsInfo.getShopcate_id()) && goodsInfo.getShopcate_id().equals(cateInfo.getCate_id())) {
                    if (goodsInfo.getHmBuyNum().keySet().iterator().hasNext()) {
                        cateInfo.setBuy_num(cateInfo.getBuy_num() + goodsInfo.getHmBuyNum().get(goodsInfo.getHmBuyNum().keySet().iterator().next()));
                    }
                    break;
                }
            }
        }
        shopGoodsContainer.typeAdapter.notifyDataSetChanged();
    }

    public void updateShopCartGoods(List<GoodsInfo2> lstShopCartGoods) {
        for (GoodsInfo2 lstShopCartGood : lstShopCartGoods) {
            //避免单规格单属性的情况下由于lstGoods中商品与lstShopCartGoods中的对应商品是同一个对象，所以清除数量时导致lstShopCartGoods中对应的对象的数量也清除
            lstShopCartGood.getCacheHmBuyNum().putAll(lstShopCartGood.getHmBuyNum());
        }
        for (GoodsInfo2 goodsInfo : lstGoods) {
            goodsInfo.getHmBuyNum().clear();
            for (GoodsInfo2 shopCartGoods : lstShopCartGoods) {
                if (!TextUtils.isEmpty(goodsInfo.getGoods_id()) && goodsInfo.getGoods_id().equals(shopCartGoods.getGoods_id())) {
                    HashMap<String, Integer> hmBuyNum = goodsInfo.getHmBuyNum();
                    hmBuyNum.putAll(shopCartGoods.getCacheHmBuyNum());
                }
            }
        }
        shopGoodsContainer.goodsAdapter.notifyDataSetChanged();
    }
}
