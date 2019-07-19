package com.wbx.mall.module.mine.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.base.BaseApplication;
import com.wbx.mall.utils.APPUtil;
import com.wbx.mall.utils.GlideUtils;
import com.wbx.mall.utils.KeyBordUtil;
import com.wbx.mall.utils.PictureUtil;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.utils.UpLoadPicUtils;
import com.wbx.mall.widget.LoadingDialog;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;

/**
 * Created by wushenghui on 2017/7/18.
 */

public class AccountInfoActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.activity_personal_nick_name_tv)
    TextView nickNameTv;
    @Bind(R.id.head_pic_im)
    ImageView headPicIm;
    private Dialog dialog;
    private EditText nickNameEdit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_account_info;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void fillData() {
        nickNameTv.setText(userInfo.getNickname());
        GlideUtils.showSmallPic(mActivity, headPicIm, userInfo.getFace());
    }

    @Override
    public void setListener() {

    }

    @OnClick({R.id.personal_reset_psw_ll, R.id.activity_personal_edit_nice_name_layout,
            R.id.activity_personal_bind_phone_layout, R.id.reset_pay_psw_ll, R.id.balance_pay_layout, R.id.address_manager_layout,
            R.id.personal_head_layout, R.id.log_layout, R.id.about_us_layout, R.id.bind_pay_account_layout, R.id.agent_layout, R.id.comment_to_us})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.personal_reset_psw_ll:
                startActivity(new Intent(mActivity, ResetPswActivity.class));
                break;
            case R.id.activity_personal_edit_nice_name_layout:
                showNickNameDialog();
                break;
            case R.id.activity_personal_bind_phone_layout:

                break;
            case R.id.reset_pay_psw_ll:
                startActivity(new Intent(mActivity, ResetPayPswActivity.class));
                break;
            case R.id.balance_pay_layout:
                FinanceOperationActivity.rechargeStart(mActivity);
                break;
            case R.id.address_manager_layout:
                startActivity(new Intent(mActivity, AddressManagerActivity.class));
                break;
            case R.id.personal_head_layout:
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setPreviewEnabled(false)
                        .start(mActivity, AppConfig.TAKE_PHOTO_CODE);
                break;
            case R.id.log_layout:
                Intent intent = new Intent(mActivity, BalanceLogActivity.class);
                startActivity(intent);
                break;
            case R.id.about_us_layout:
                startActivity(new Intent(mContext, AboutUsActivity.class));
                break;
            case R.id.bind_pay_account_layout:
                startActivity(new Intent(mContext, ChooseBindActivity.class));
                break;
            case R.id.agent_layout:
                enterAgent();
                break;
            case R.id.comment_to_us:
                APPUtil.openAppMarket(this);
                break;
            default:
                break;
        }
    }

    private void enterAgent() {
        if (userInfo.getRank() > 0 && userInfo.getIs_rank_pay() == 1 && userInfo.getRank_audit() == 1) {//是星伙
            startActivity(new Intent(mContext, AgentServiceActivity.class));
        } else if (userInfo.getRank() > 0 && userInfo.getRank() < 5 && userInfo.getIs_rank_pay() == 1 && userInfo.getRank_audit() == 0) {//星伙已付款，审核中
            AuditResultActivity.actionStart(this, true);
        } else if (userInfo.getRank() == 5 && userInfo.getRank_audit() == 0) {//创业领袖审核中
            AuditResultActivity.actionStart(this, true);
        } else {//不是星伙（包含未填资料和已填资料未签名或者已签名但未支付）
            ToBeAgentActivity.actionStart(mContext);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == AppConfig.TAKE_PHOTO_CODE) {
            if (null == data) {
                return;
            }
            ArrayList<String> pics = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            upLoadPic(pics.get(0));
        }
    }

    private void upLoadPic(String pic) {
        String tmepName = "";
        try {
            tmepName = PictureUtil.bitmapToPath(pic);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoadingDialog.showDialogForLoading(mActivity, "上传中...", true);
        UpLoadPicUtils.upOnePic(tmepName, new UpLoadPicUtils.UpLoadPicListener() {
            @Override
            public void success(String qiNiuPath) {
                upDateHeadPic(qiNiuPath);

            }

            @Override
            public void error() {

            }
        });

    }

    private void upDateHeadPic(final String qiNiuPath) {
        new MyHttp().doPost(Api.getDefault().updateFace(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), qiNiuPath), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                GlideUtils.showMediumPic(mContext, headPicIm, qiNiuPath);
            }

            @Override
            public void onError(int code) {

            }
        });

    }

    /**
     * 弹出填写昵称的方法
     */
    private void showNickNameDialog() {
        View inflate = getLayoutInflater().inflate(R.layout.dialog_edit_nick_name_view, null);
        if (null == dialog) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setView(inflate);
            dialog = builder.create();
            TextView nickName = inflate.findViewById(R.id.user_nick_name_tv);
            nickName.setText(userInfo.getNickname());
            inflate.findViewById(R.id.dialog_cancel_btn).setOnClickListener(this);
            inflate.findViewById(R.id.dialog_complete_btn).setOnClickListener(this);
            nickNameEdit = inflate.findViewById(R.id.dialog_input_nick_name_edit);
        }
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_cancel_btn:
                dialog.dismiss();
                break;
            case R.id.dialog_complete_btn:
                new MyHttp().doPost(Api.getDefault().upDateNickName(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), nickNameEdit.getText().toString()), new HttpListener() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        KeyBordUtil.hideSoftKeyboard(nickNameEdit);
                        nickNameTv.setText(nickNameEdit.getText().toString());
                        userInfo.setNickname(nickNameEdit.getText().toString());
                        BaseApplication.getInstance().saveObject(userInfo, AppConfig.USER_DATA);
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(int code) {

                    }
                });
                break;
        }
    }
}
