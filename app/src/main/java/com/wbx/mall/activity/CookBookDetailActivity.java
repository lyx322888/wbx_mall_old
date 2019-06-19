package com.wbx.mall.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.adapter.CookIntroduceAdapter;
import com.wbx.mall.adapter.CookingNeedAdapter;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.CookBookDetailInfo;
import com.wbx.mall.utils.GlideUtils;

import butterknife.Bind;

/**
 * Created by wushenghui on 2017/8/24.
 */

public class CookBookDetailActivity extends BaseActivity {

    private CookBookDetailInfo data;
    @Bind(R.id.dining_tv)
    TextView diningTv;
    @Bind(R.id.cooking_tv)
    TextView cookingTv;
    @Bind(R.id.number_tv)
    TextView numberTv;
    @Bind(R.id.content_tv)
    TextView contentTv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.pic_im)
    ImageView picIm;
    @Bind(R.id.introduce_rv)
    RecyclerView introduceRv;
    @Bind(R.id.need_rv)
    RecyclerView needRv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cook_book_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        needRv.setLayoutManager(new LinearLayoutManager(mContext));
        introduceRv.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    public void fillData() {
        data = (CookBookDetailInfo) getIntent().getSerializableExtra("data");
        if (null != data) {
            diningTv.setText(data.getPeoplenum());
            cookingTv.setText(data.getCookingtime());
            numberTv.setText(data.getPreparetime());
            if (!TextUtils.isEmpty(data.getContent())) {
                contentTv.setText(data.getContent().replaceAll("<br/>", "").replaceAll("<br />", ""));
            }
            nameTv.setText(data.getName());
            GlideUtils.showBigPic(mContext, picIm, data.getPic());
            CookingNeedAdapter cookingNeedAdapter = new CookingNeedAdapter(data.getMaterial(), mContext);
            needRv.setAdapter(cookingNeedAdapter);
            CookIntroduceAdapter cookIntroduceAdapter = new CookIntroduceAdapter(data.getProcess(), mContext);
            introduceRv.setAdapter(cookIntroduceAdapter);
        } else {
            showShortToast("数据出错，无法访问！");
            finish();
        }

    }

    @Override
    public void setListener() {

    }
}
