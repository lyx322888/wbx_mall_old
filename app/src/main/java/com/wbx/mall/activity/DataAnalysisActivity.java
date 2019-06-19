package com.wbx.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.wbx.mall.R;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.SalesManDataBean;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.LineChart;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 数据分析
 *
 * @author Zero
 * @date 2018/4/2
 */
public class DataAnalysisActivity extends BaseActivity {
    @Bind(R.id.tv_money_widthdraw)
    TextView tvMoneyWidthdraw;
    @Bind(R.id.tv_money_share_out_bouns)
    TextView tvMoneyShareOutBouns;
    @Bind(R.id.tv_sell_num_xinghuo)
    TextView tvSellNumXinghuo;
    @Bind(R.id.tv_money_xinghuo)
    TextView tvMoneyXinghuo;
    @Bind(R.id.tv_people_num_xinghuo)
    TextView tvPeopleNumXinghuo;
    @Bind(R.id.ll_xinghuo)
    LinearLayout llXinghuo;
    @Bind(R.id.tv_sell_num_leader)
    TextView tvSellNumLeader;
    @Bind(R.id.tv_money_leader)
    TextView tvMoneyLeader;
    @Bind(R.id.tv_people_num_leader)
    TextView tvPeopleNumLeader;
    @Bind(R.id.ll_leader)
    LinearLayout llLeader;
    @Bind(R.id.tv_sell_num_total)
    TextView tvSellNumTotal;
    @Bind(R.id.tv_money_total)
    TextView tvMoneyTotal;
    @Bind(R.id.ll_city)
    LinearLayout llCity;
    @Bind(R.id.ll_total)
    LinearLayout llTotal;
    @Bind(R.id.tv_num_city)
    TextView tvNumCity;
    @Bind(R.id.line_chart)
    LineChart lineChart;
    private int rank;
    private SalesManDataBean data;
    private Gson gson = new Gson();

    public static void actionStart(Context context, int rank) {
        Intent intent = new Intent(context, DataAnalysisActivity.class);
        intent.putExtra("rank", rank);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_data_analysis;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void fillData() {
        rank = getIntent().getIntExtra("rank", 1);
        new MyHttp().doPost(Api.getDefault().getSalesManData(SPUtils.getSharedStringData(this, AppConfig.LOGIN_TOKEN)), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                data = gson.fromJson(result.getString("data"), SalesManDataBean.class);
                updateUI(data);
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void updateUI(SalesManDataBean data) {
        tvMoneyWidthdraw.setText(String.format("%.2f", data.getOperation_money() / 100.00));
        tvMoneyShareOutBouns.setText(String.format("%.2f", data.getRecommend_bonus() / 100.00));
        List<Float> lstOperationMoney = new ArrayList<>();
        for (Integer integer : data.getReckon_seven_data().getList_operation_money()) {
            lstOperationMoney.add(integer / 100.00f);
        }
        List<Float> lstRecommendMoney = new ArrayList<>();
        for (Integer integer : data.getReckon_seven_data().getList_recommend_money()) {
            lstRecommendMoney.add(integer / 100.00f);
        }
        lineChart.setData(lstOperationMoney, lstRecommendMoney, data.getReckon_seven_data().getList_days());
        SalesManDataBean.RecommendStarArrBean recommendStarArr = data.getRecommend_star_arr();
        SalesManDataBean.RecommendLeaderArrBean recommendLeaderArr = data.getRecommend_leader_arr();
        SalesManDataBean.RecommendAllArrBean recommendAllArr = data.getRecommend_all_arr();
        switch (rank) {
            //一星
            case 1:
                //二星
            case 2:
                //领袖
            case 5:
                llLeader.setVisibility(View.INVISIBLE);
                ((View) llCity.getParent()).setVisibility(View.GONE);
                if (recommendStarArr != null) {
                    tvSellNumXinghuo.setText(recommendStarArr.getChild_salesmans_shop_today() + "套");
                    tvMoneyXinghuo.setText(String.format("%.2f元", recommendStarArr.getAll_recommend_today() / 100.00));
                    tvPeopleNumXinghuo.setText(recommendStarArr.getChild_salesmans_count() + "人");
                }
                break;
            //战略伙伴
            case 6:
                llCity.setVisibility(View.INVISIBLE);
                if (recommendStarArr != null) {
                    tvSellNumXinghuo.setText(recommendStarArr.getChild_salesmans_shop_today() + "套");
                    tvMoneyXinghuo.setText(String.format("%.2f元", recommendStarArr.getAll_recommend_today() / 100.00));
                    tvPeopleNumXinghuo.setText(recommendStarArr.getChild_salesmans_count() + "人");
                }
                if (recommendLeaderArr != null) {
                    tvSellNumLeader.setText(recommendLeaderArr.getChild_salesmans_shop_today() + "套");
                    tvMoneyLeader.setText(String.format("%.2f元", recommendLeaderArr.getAll_recommend_today() / 100.00));
                    tvPeopleNumLeader.setText(recommendLeaderArr.getChild_salesmans_count() + "人");
                }
                if (recommendAllArr != null) {
                    tvSellNumTotal.setText(recommendAllArr.getChild_salesmans_shop_today() + "套");
                    tvMoneyTotal.setText(String.format("%.2f元", recommendAllArr.getAll_recommend() / 100.00));
                }
                break;
            //城市代理
            case 7:
                if (recommendStarArr != null) {
                    tvSellNumXinghuo.setText(recommendStarArr.getChild_salesmans_shop_today() + "套");
                    tvMoneyXinghuo.setText(String.format("%.2f元", recommendStarArr.getAll_recommend_today() / 100.00));
                    tvPeopleNumXinghuo.setText(recommendStarArr.getChild_salesmans_count() + "人");
                }
                if (recommendLeaderArr != null) {
                    tvSellNumLeader.setText(recommendLeaderArr.getChild_salesmans_shop_today() + "套");
                    tvMoneyLeader.setText(String.format("%.2f元", recommendLeaderArr.getAll_recommend_today() / 100.00));
                    tvPeopleNumLeader.setText(recommendLeaderArr.getChild_salesmans_count() + "人");
                }
                if (recommendAllArr != null) {
                    tvSellNumTotal.setText(recommendAllArr.getChild_salesmans_shop_today() + "套");
                    tvMoneyTotal.setText(String.format("%.2f元", recommendAllArr.getAll_recommend() / 100.00));
                    tvNumCity.setText(recommendAllArr.getCity_shop_count());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void setListener() {

    }

    @OnClick({R.id.tv_widthdraw, R.id.tv_widthdraw_share_out_bouns})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_widthdraw:
                FinanceOperationActivity.withdrawStart(mContext, data.getOperation_money(), AppConfig.CashType.operation_money);
                break;
            case R.id.tv_widthdraw_share_out_bouns:
                FinanceOperationActivity.withdrawStart(mContext, data.getRecommend_bonus(), AppConfig.CashType.recommend_bonus);
                break;
            default:
                break;
        }
    }
}
