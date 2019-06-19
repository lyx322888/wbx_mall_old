package com.wbx.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wbx.mall.R;
import com.wbx.mall.activity.agent.CredentialActivity;
import com.wbx.mall.activity.agent.MyCustomerActivity;
import com.wbx.mall.adapter.TypeAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.bean.AgentInfo;
import com.wbx.mall.bean.TypeInfo;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.utils.GlideUtils;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.CircleImageView;
import com.wbx.mall.widget.LoadingDialog;
import com.wbx.mall.widget.WaveViewBySinCos;
import com.wbx.mall.widget.decoration.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * Created by wushenghui on 2017/5/13.
 * 代理业务
 */

public class AgentServiceActivity extends BaseActivity {
    @Bind(R.id.wave_view)
    WaveViewBySinCos waveViewBySinCos;
    @Bind(R.id.agent_rv)
    RecyclerView agentRv;
    @Bind(R.id.user_pic_im)
    CircleImageView userPicIm;
    @Bind(R.id.agent_name_tv)
    TextView agentNameTv;
    @Bind(R.id.grade_tv)
    TextView gradeTv;
    @Bind(R.id.operation_money_tv)
    TextView operationMoneyTv;
    @Bind(R.id.commision_tv)
    TextView commisionTv;
    TypeAdapter mAdapter;
    private List<TypeInfo> typeInfoList = new ArrayList<>();
    private HashMap<String, Object> mParams = new HashMap<>();
    private AgentInfo data;
    private PopupWindow popupWindow;

    @Override
    public int getLayoutId() {
        return R.layout.activity_agent_service;
    }

    @Override
    public void initPresenter() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public void initView() {
        agentRv.addItemDecoration(new SpacesItemDecoration(3));
        agentRv.setLayoutManager(new GridLayoutManager(mContext, 3));
        waveViewBySinCos.startAnimation();
    }

    @Override
    public void fillData() {
        getAgentInfo();
    }

    private void getAgentInfo() {
        LoadingDialog.showDialogForLoading(this, "获取信息中...", true);
        mParams.put("login_token", SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN));
        new MyHttp().doPost(Api.getDefault().getAgentInfo(mParams), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                data = result.getObject("data", AgentInfo.class);
                pullData();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void pullData() {
        if (null != data) {
            GlideUtils.showMediumPic(mContext, userPicIm, userInfo.getFace());
            agentNameTv.setText(data.getName());
            operationMoneyTv.setText(String.format("¥ %.2f", data.getOperation_money() / 100.00));
            commisionTv.setText(String.format("¥ %.2f", data.getCommision() / 100.00));
            if (data.getRank() == 5) {
                gradeTv.setText("创业领袖");
            } else if (data.getRank() == 6) {
                gradeTv.setText("战略合作伙伴");
            } else if (data.getRank() == 7) {
                gradeTv.setText("城市代理");
            }
        }
        TypedArray ar = getResources().obtainTypedArray(R.array.type_agent_src);
        int len = ar.length();
        int[] resIds = new int[len];
        for (int i = 0; i < len; i++) {
            resIds[i] = ar.getResourceId(i, 0);
        }
        ar.recycle();
        String[] stringArray = getResources().getStringArray(R.array.type_agent_str);

        for (int i = 0; i < stringArray.length; i++) {
            TypeInfo type = new TypeInfo();
            type.setName(stringArray[i]);
            type.setSrcScore(resIds[i]);
            typeInfoList.add(type);
        }
        mAdapter = new TypeAdapter(typeInfoList, mContext);
        agentRv.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(R.id.root_view, new BaseAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent;
                switch (position) {
                    case 0:
                        //业务提现
                        FinanceOperationActivity.withdrawStart(mContext, data == null ? 0 : data.getOperation_money(), AppConfig.CashType.operation_money);
                        break;
//                    case 1:
//                        //我的押金
//                        intent = new Intent(mContext, MyDepositActivity.class);
//                        intent.putExtra("deposit", data == null ? 0 : data.getDeposit());
//                        startActivity(intent);
//                        break;
                    case 1:
                        //我的客户
                        intent = new Intent(mContext, MyClientActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        //授权证书
                        if (null != data) {
                            intent = new Intent(mContext, CredentialActivity.class);
                            intent.putExtra("rank", data.getRank());
                            startActivity(intent);
                        }
                        break;
                    case 3:
                        //余额日志
                        intent = new Intent(mContext, BalanceLogActivity.class);
                        startActivity(intent);
                        break;
//                    case 4:
//                        sharePop();
//                        break;
                    case 4:
                        intent = new Intent(mContext, MyCustomerActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        DataAnalysisActivity.actionStart(mContext, data.getRank());
                        break;
                    case 6:
                        //邀请商家入驻
                        InviteActivity.actionStart(mContext, "http://www.wbx365.com/Wbxwaphome/apply/activity2.html", true);
                        break;
                    case 7:
                        intent = new Intent(mContext, MerchantDataActivity.class);
                        startActivity(intent);
                        break;
                    case 8:
                        intent = new Intent(mContext, MySoftwareActivity.class);
                        startActivity(intent);
                        break;
                    case 9:
                        Log.e("TAG",LoginUtil.getLoginToken());
                        intent=new Intent(mContext,SalesDetailActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }

            }
        });
    }

    @Override
    public void setListener() {


    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * 分享
     */
    public void sharePop() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = inflater.inflate(R.layout.share_pop_view, null);
        popupWindow = new PopupWindow();
        popupWindow.setContentView(inflate);
        popupWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation);
        popupWindow.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        popupWindow.showAtLocation(findViewById(R.id.store_root_view), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        backgroundAlpha(0.6f);
        popOnclikListener(inflate);
    }

    /**
     * 分享点击事件
     *
     * @param inflate
     */

    private void popOnclikListener(View inflate) {
        inflate.findViewById(R.id.poop_share_cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != popupWindow) {
                    popupWindow.dismiss();
                }
            }
        });
        inflate.findViewById(R.id.share_wechat_friends_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareWx(1);
            }
        });
        inflate.findViewById(R.id.share_wechat_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareWx(0);
            }
        });
        inflate.findViewById(R.id.share_wechat_collection_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareWx(2);
            }
        });
    }

    private void shareWx(int flag) {
        IWXAPI wxapi = WXAPIFactory.createWXAPI(this, AppConfig.WX_APP_ID);
        wxapi.registerApp(AppConfig.WX_APP_ID);
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = data.getXh_share_link();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "微百姓邀请您一起加入星伙计划";
        msg.description = "";
        //这里替换一张自己工程里的图片资源
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.app_logo);

        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag;
        wxapi.sendReq(req);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }
}
