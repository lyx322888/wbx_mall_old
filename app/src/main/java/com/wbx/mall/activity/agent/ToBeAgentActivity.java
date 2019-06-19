package com.wbx.mall.activity.agent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.activity.ChooseCityActivity;
import com.wbx.mall.activity.ContractSignActivity;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.base.BaseApplication;
import com.wbx.mall.base.Constants;
import com.wbx.mall.bean.LocationInfo;
import com.wbx.mall.utils.GlideUtils;
import com.wbx.mall.utils.PictureUtil;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.utils.UpLoadPicUtils;
import com.wbx.mall.widget.LoadingDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;

/**
 * 成为合伙人
 *
 * @author Zero
 * @date 2017/9/5
 */
public class ToBeAgentActivity extends BaseActivity {
    public static final int REQUEST_PHOTO_HEAD = 1000;
    public static final int REQUEST_PHOTO_BACK = 1001;
    public static final int REQUEST_CITY = 1002;
    public static final int RANK_STAR_MAN = 2;
    public static final int RANK_LEADER = 5;
    @Bind(R.id.iv_star_man)
    ImageView ivStarMan;
    @Bind(R.id.tv_star_man)
    TextView tvStarMan;
    @Bind(R.id.iv_leader)
    ImageView ivLeader;
    @Bind(R.id.tv_leader)
    TextView tvLeader;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_id_card)
    EditText etIdCard;
    @Bind(R.id.et_referee_phone)
    EditText etRefereePhone;
    @Bind(R.id.iv_referee)
    ImageView ivReferee;
    @Bind(R.id.tv_referee)
    TextView tvReferee;
    @Bind(R.id.tv_city)
    TextView tvCity;
    @Bind(R.id.iv_card_head)
    ImageView ivCardHead;
    @Bind(R.id.iv_card_back)
    ImageView ivCardBack;
    private int rank = RANK_STAR_MAN;
    private String headImgPath;
    private String backImgPath;
    private boolean isRightStarMan = false;
    private LocationInfo selectCity;
    private MyHttp myHttp;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ToBeAgentActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tobe_agent;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void fillData() {
        myHttp = new MyHttp();
        selectCity = (LocationInfo) BaseApplication.getInstance().readObject(AppConfig.LOCATION_DATA);
        tvCity.setText(selectCity.getName());
    }

    @Override
    public void setListener() {
        etRefereePhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == Constants.PHONE_NUM_LENGTH) {
                    checkIsStarMan(s.toString());
                } else {
                    if (isRightStarMan) {
                        ivReferee.setImageResource(R.drawable.icon_isnt_star_man);
                        tvReferee.setTextColor(Color.parseColor("#d3d3d3"));
                    }
                    isRightStarMan = false;
                }
            }
        });
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#¥%……&*（）——+|{}【】‘；：”“’。，、？ \n]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.matches()) {
                    return "";
                } else {
                    return source;
                }
            }
        };
        etName.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(10)});
        InputFilter inputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String allowStr = "[1234567890X]";
                Pattern pattern = Pattern.compile(allowStr);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.matches()) {
                    return source;
                } else {
                    return "";
                }
            }
        };
        etIdCard.setFilters(new InputFilter[]{inputFilter, new InputFilter.LengthFilter(18)});
    }

    private void checkIsStarMan(String phone) {
        myHttp.doPost(Api.getDefault().isStarMan(SPUtils.getSharedStringData(this, AppConfig.LOGIN_TOKEN), phone), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                isRightStarMan = true;
                ivReferee.setImageResource(R.drawable.icon_is_star_man);
                tvReferee.setTextColor(getResources().getColor(R.color.app_color));
            }

            @Override
            public void onError(int code) {
                isRightStarMan = false;
                if (code == AppConfig.ERROR_STATE.NO_STAR_MAN) {
                    ivReferee.setImageResource(R.drawable.icon_isnt_star_man);
                    tvReferee.setTextColor(Color.parseColor("#d3d3d3"));
                }
            }
        });
    }

    @OnClick({R.id.ll_star_man, R.id.ll_leader, R.id.ll_city, R.id.iv_card_head, R.id.iv_card_back, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_star_man:
                applyType(RANK_STAR_MAN);
                break;
            case R.id.ll_leader:
                applyType(RANK_LEADER);
                break;
            case R.id.ll_city:
                chooseCity();
                break;
            case R.id.iv_card_head:
                selectPic(true);
                break;
            case R.id.iv_card_back:
                selectPic(false);
                break;
            case R.id.btn_next:
                nextStep();
                break;
            default:
                break;
        }
    }

    private void chooseCity() {
        Intent intent = new Intent(mContext, ChooseCityActivity.class);
        intent.putExtra("isAllCity", true);
        startActivityForResult(intent, REQUEST_CITY);
    }

    private void selectPic(boolean isHead) {
        PhotoPicker.builder()
                .setPhotoCount(1)
                .setShowCamera(true)
                .setPreviewEnabled(false)
                .start(mActivity, isHead ? REQUEST_PHOTO_HEAD : REQUEST_PHOTO_BACK);
    }

    private void applyType(int i) {
        rank = i;
        if (i == RANK_STAR_MAN) {
            ivStarMan.setImageResource(R.drawable.selected);
            tvStarMan.setTextColor(getResources().getColor(R.color.app_color));
            ivLeader.setImageResource(R.drawable.uncheck);
            tvLeader.setTextColor(Color.parseColor("#d3d3d3"));
        } else {
            ivStarMan.setImageResource(R.drawable.uncheck);
            tvStarMan.setTextColor(Color.parseColor("#d3d3d3"));
            ivLeader.setImageResource(R.drawable.selected);
            tvLeader.setTextColor(getResources().getColor(R.color.app_color));
        }
    }

    private void nextStep() {
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String idCardNum = etIdCard.getText().toString();
        if (TextUtils.isEmpty(name)) {
            showShortToast("请填写您的姓名");
            return;
        }
        if (TextUtils.isEmpty(phone) || phone.length() != Constants.PHONE_NUM_LENGTH) {
            showShortToast("请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(idCardNum) || idCardNum.length() != Constants.ID_CARD_NUM_LENGTH) {
            showShortToast("请输入正确的身份证号");
            return;
        }
        if (!isRightStarMan) {
            showShortToast("请输入正确的推荐人手机号");
            return;
        }
        if (TextUtils.isEmpty(headImgPath)) {
            showShortToast("请上传身份证正面照");
            return;
        }
        if (TextUtils.isEmpty(backImgPath)) {
            showShortToast("请上传身份证反面照");
            return;
        }
        LoadingDialog.showDialogForLoading(mActivity, "信息提交中...", true);
        myHttp.doPost(Api.getDefault().tobeAgent(rank, name, phone, idCardNum, etRefereePhone.getText().toString(), String.valueOf(selectCity.getCity_id()), headImgPath, backImgPath, SPUtils.getSharedStringData(this, AppConfig.LOGIN_TOKEN)), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                ContractSignActivity.actionStart(ToBeAgentActivity.this, rank);
            }

            @Override
            public void onError(int code) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_PHOTO_HEAD) {
                ArrayList<String> pics = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                upLoadPic(true, pics.get(0));
            } else if (requestCode == REQUEST_PHOTO_BACK) {
                ArrayList<String> pics = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                upLoadPic(false, pics.get(0));
            }
        }
        if (requestCode == REQUEST_CITY && data != null) {
            selectCity = (LocationInfo) data.getSerializableExtra("selectCity");
            tvCity.setText(selectCity.getName());
        }
        if (resultCode == RESULT_OK && requestCode == 0) {
            finish();
        }
    }

    private void upLoadPic(final boolean isHead, final String path) {
        String tmepName = "";
        try {
            tmepName = PictureUtil.bitmapToPath(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoadingDialog.showDialogForLoading(mActivity, "上传中...", true);
        UpLoadPicUtils.upOnePic(tmepName, new UpLoadPicUtils.UpLoadPicListener() {
            @Override
            public void success(String qiNiuPath) {
                LoadingDialog.cancelDialogForLoading();
                if (isHead) {
                    headImgPath = qiNiuPath;
                    GlideUtils.showMediumPic(mContext, ivCardHead, path);
                } else {
                    backImgPath = qiNiuPath;
                    GlideUtils.showMediumPic(mContext, ivCardBack, path);
                }
            }

            @Override
            public void error() {
                showShortToast("上传失败，请重新尝试！");
                LoadingDialog.cancelDialogForLoading();
            }
        });
    }
}
