package com.wbx.mall.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.wbx.mall.R;
import com.wbx.mall.adapter.IndexCouponAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.ApiService;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseApplication;
import com.wbx.mall.bean.HomeCouponBean;
import com.wbx.mall.bean.LocationInfo;
import com.wbx.mall.bean.ScanOrderPayInfoBean;
import com.wbx.mall.presenter.HomeCouponPresenterImp;
import com.wbx.mall.utils.ToastUitl;
import com.wbx.mall.view.HomeCouponView;

import junit.framework.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        RatingBar ratingBar=findViewById(R.id.rb);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });
    }

}

