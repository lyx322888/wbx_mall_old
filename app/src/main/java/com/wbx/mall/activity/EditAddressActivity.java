package com.wbx.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.PoiItem;
import com.kyleduo.switchbutton.SwitchButton;
import com.wbx.mall.R;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.AddressInfo;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.LoadingDialog;
import com.wbx.mall.widget.iosdialog.AlertDialog;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wushenghui on 2017/7/18.
 */

public class EditAddressActivity extends BaseActivity {
    @Bind(R.id.edit_address_user_name_edit)
    EditText mUserNameEdit;
    @Bind(R.id.edit_address_user_phone_edit)
    EditText mUserPhoneEdit;
    @Bind(R.id.address_tv)
    TextView addressTv;
    @Bind(R.id.address_title_name)
    TextView mTitleNameTv;
    @Bind(R.id.activity_address_del_add)
    Button delAddressBtn;
    @Bind(R.id.address_detail_info_tv)
    EditText addressDetailEdit;
    @Bind(R.id.address_is_default_switch_view)
    SwitchButton mIsDefaultView;
    @Bind(R.id.tv_home)
    TextView tvHome;
    @Bind(R.id.tv_company)
    TextView tvCompany;
    @Bind(R.id.tv_school)
    TextView tvSchool;
    private boolean isEdit;
    private int isDefault = AppConfig.addressIsDefault.unDefault;
    private AddressInfo addressInfo;
    private HashMap<String, Object> mParams = new HashMap<>();
    private PoiItem selectPoi;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, EditAddressActivity.class);
        context.startActivity(intent);
    }

    public static void actionStart(Context context, AddressInfo addressInfo) {
        Intent intent = new Intent(context, EditAddressActivity.class);
        intent.putExtra("isEdit", true);
        intent.putExtra("address", addressInfo);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_address;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void fillData() {
        isEdit = getIntent().getBooleanExtra("isEdit", false);
        addressInfo = (AddressInfo) getIntent().getSerializableExtra("address");
        if (!isEdit) {
            mTitleNameTv.setText("添加收货地址");
            tvHome.setSelected(true);
        } else {
            mTitleNameTv.setText("编辑收货地址");
            delAddressBtn.setVisibility(View.VISIBLE);
            mUserNameEdit.setText(addressInfo.getXm());
            mUserPhoneEdit.setText(addressInfo.getTel());
            addressTv.setText(addressInfo.getArea_str());
            addressDetailEdit.setText(addressInfo.getInfo());
            isDefault = addressInfo.getDefaultX();
            mIsDefaultView.setChecked(addressInfo.getDefaultX() == AppConfig.addressIsDefault.isDefault);
            if (!TextUtils.isEmpty(addressInfo.getTag())) {
                if ("家".equals(addressInfo.getTag())) {
                    tvHome.setSelected(true);
                } else if ("公司".equals(addressInfo.getTag())) {
                    tvCompany.setSelected(true);
                } else if ("学校".equals(addressInfo.getTag())) {
                    tvSchool.setSelected(true);
                }
            }
        }
    }

    @Override
    public void setListener() {
        mIsDefaultView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    isDefault = AppConfig.addressIsDefault.isDefault;
                } else {
                    isDefault = AppConfig.addressIsDefault.unDefault;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConfig.REQUEST_CONTACT) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    return;
                }
                Uri conatctData = data.getData();
                Cursor cursor = managedQuery(conatctData, null, null, null, null);
                if (cursor.moveToFirst()) {
                    String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                    String phonenum = "此联系人暂未输入电话号码";
                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
                    if (phones.moveToFirst()) {
                        phonenum = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    mUserNameEdit.setText(name);
                    mUserPhoneEdit.setText(phonenum.replaceAll("(?:'-'|' ')", ""));
                    if (Build.VERSION.SDK_INT < 14) {
                        phones.close();
                    }
                }
                if (Build.VERSION.SDK_INT < 14) {//不添加的话Android4.0以上系统运行会报错
                    cursor.close();
                }
            }
        } else if (requestCode == AppConfig.PERMISSIONS_CODE) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setData(ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, AppConfig.REQUEST_CONTACT);
        } else if (requestCode == SelectReceivingAddressActivity.REQUEST_SELECT_ADDRESS && resultCode == RESULT_OK) {
            selectPoi = data.getParcelableExtra("address");
            addressTv.setText(selectPoi.getProvinceName() + selectPoi.getCityName() + selectPoi.getAdName());
            addressDetailEdit.setText(selectPoi.getSnippet() + selectPoi.getTitle());
        }
    }

    private void deleteAddress() {
        new AlertDialog(mActivity).builder()
                .setTitle("提示")
                .setMsg("删除收货地址？")
                .setNegativeButton("再想想", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        delAddress();
                    }
                }).show();
    }

    private void delAddress() {
        new MyHttp().doPost(Api.getDefault().deleteAddress(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), addressInfo.getId()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                showShortToast(result.getString("msg"));
                finish();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void addAddress() {
        if ("".equals(mUserNameEdit.getText().toString())) {
            showShortToast("请填写收货人姓名");
            return;
        }
        String phone = mUserPhoneEdit.getText().toString();
        if ("".equals(phone)) {
            showShortToast("请填写收货人电话");
            return;
        }
        if (phone.length() != 11) {
            showShortToast("请输入正确的手机号");
            return;
        }
        if (selectPoi == null) {
            showShortToast("请选择收货地址");
            return;
        }
        if ("".equals(addressDetailEdit.getText().toString())) {
            showShortToast("请填写详细地址");
            return;
        }
        LoadingDialog.showDialogForLoading(mActivity, "信息提交中...", true);
        mParams.put("login_token", SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN));
        mParams.put("default", isDefault);
        mParams.put("xm", mUserNameEdit.getText().toString());
        mParams.put("tel", phone);
        mParams.put("province_name", selectPoi.getProvinceName());
        mParams.put("city_name", selectPoi.getCityName());
        mParams.put("area_name", selectPoi.getAdName());
        mParams.put("lat", selectPoi.getLatLonPoint().getLatitude());
        mParams.put("lng", selectPoi.getLatLonPoint().getLongitude());
        mParams.put("info", addressDetailEdit.getText().toString());
        String tag = null;
        if (tvHome.isSelected()) {
            tag = "家";
        } else if (tvCompany.isSelected()) {
            tag = "公司";
        } else if (tvSchool.isSelected()) {
            tag = "学校";
        }
        mParams.put("tag", tag);
        new MyHttp().doPost(Api.getDefault().addAddress(mParams), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                showShortToast(result.getString("msg"));
                finish();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void editTextAddress() {
        if ("".equals(mUserNameEdit.getText().toString())) {
            showShortToast("请填写收货人姓名");
            return;
        }
        String phone = mUserPhoneEdit.getText().toString();
        if ("".equals(phone)) {
            showShortToast("请填写收货人电话");
            return;
        }
        if (phone.length() != 11) {
            showShortToast("请输入正确的手机号");
            return;
        }
        if (addressInfo.getLat() == 0 && selectPoi == null) {//直接手动输入的旧数据
            showShortToast("请重新选择收货地址");
            return;
        }
        if ("".equals(addressDetailEdit.getText().toString())) {
            showShortToast("请填写详细地址");
            return;
        }
        if (selectPoi != null) {
            addressInfo.setProvince_name(selectPoi.getProvinceName());
            addressInfo.setCity_name(selectPoi.getCityName());
            addressInfo.setArea_name(selectPoi.getAdName());
            addressInfo.setLat(String.valueOf(selectPoi.getLatLonPoint().getLatitude()));
            addressInfo.setLng(String.valueOf(selectPoi.getLatLonPoint().getLongitude()));
        }
        LoadingDialog.showDialogForLoading(mActivity, "信息提交中...", true);
        mParams.put("login_token", SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN));
        mParams.put("id", addressInfo.getId());
        mParams.put("default", isDefault);
        mParams.put("xm", mUserNameEdit.getText().toString());
        mParams.put("tel", mUserPhoneEdit.getText().toString());
        mParams.put("province_name", addressInfo.getProvince_name());
        mParams.put("city_name", addressInfo.getCity_name());
        mParams.put("area_name", addressInfo.getArea_name());
        mParams.put("lat", addressInfo.getLat());
        mParams.put("lng", addressInfo.getLng());
        mParams.put("info", addressDetailEdit.getText().toString());
        String tag = null;
        if (tvHome.isSelected()) {
            tag = "家";
        } else if (tvCompany.isSelected()) {
            tag = "公司";
        } else if (tvSchool.isSelected()) {
            tag = "学校";
        }
        mParams.put("tag", tag);
        new MyHttp().doPost(Api.getDefault().editAddress(mParams), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                finish();
                showShortToast(result.getString("msg"));
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void chooseAddress() {
        if (selectPoi != null) {
            SelectReceivingAddressActivity.actionStart(this, new LatLng(selectPoi.getLatLonPoint().getLatitude(), selectPoi.getLatLonPoint().getLongitude()));
        } else {
            if (isEdit) {
                if (addressInfo.getLat() != 0) {
                    SelectReceivingAddressActivity.actionStart(this, new LatLng(addressInfo.getLat(), addressInfo.getLng()));
                } else {
                    SelectReceivingAddressActivity.actionStart(this, addressTv.getText().toString() + addressDetailEdit.getText().toString());
                }
            } else {
                SelectReceivingAddressActivity.actionStart(this);
            }
        }
    }

    @OnClick({R.id.open_contact_im, R.id.activity_edit_choose_address_layout, R.id.address_save_btn, R.id.activity_address_del_add, R.id.tv_home, R.id.tv_company, R.id.tv_school})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.open_contact_im:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setData(ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, AppConfig.REQUEST_CONTACT);
                break;
            case R.id.activity_edit_choose_address_layout:
                chooseAddress();
                break;
            case R.id.address_save_btn:
                if (isEdit) {
                    //编辑
                    editTextAddress();
                } else {
                    //添加
                    addAddress();
                }
                break;
            case R.id.activity_address_del_add:
                deleteAddress();
                break;
            case R.id.tv_home:
                switchLabelState(R.id.tv_home);
                break;
            case R.id.tv_company:
                switchLabelState(R.id.tv_company);
                break;
            case R.id.tv_school:
                switchLabelState(R.id.tv_school);
                break;
        }
    }

    private void switchLabelState(int textViewId) {
        switch (textViewId) {
            case R.id.tv_home:
                tvSchool.setSelected(false);
                tvCompany.setSelected(false);
                tvHome.setSelected(!tvHome.isSelected());
                break;
            case R.id.tv_school:
                tvHome.setSelected(false);
                tvCompany.setSelected(false);
                tvSchool.setSelected(!tvSchool.isSelected());
                break;
            case R.id.tv_company:
                tvSchool.setSelected(false);
                tvHome.setSelected(false);
                tvCompany.setSelected(!tvCompany.isSelected());
                break;
        }
    }
}