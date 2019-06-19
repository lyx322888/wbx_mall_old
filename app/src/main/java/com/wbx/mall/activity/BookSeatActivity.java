package com.wbx.mall.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.TimePickerView;
import com.wbx.mall.R;
import com.wbx.mall.adapter.SelectUseFoodNumAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.utils.FormatUtil;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.HorizontalselectedView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wushenghui on 2017/12/20.
 * 预定座位
 */

public class BookSeatActivity extends BaseActivity {
    @Bind(R.id.auto_location_horizontal_view)
    HorizontalselectedView horizontalselectedView;
    List<String> dataIntList = new ArrayList<>();
    private SelectUseFoodNumAdapter mAdapter;
    @Bind(R.id.date_tv)
    TextView dateTv;
    @Bind(R.id.hour_time_tv)
    TextView hourTimeTv;
    @Bind(R.id.minute_time_tv)
    TextView minuteTimeTv;
    @Bind(R.id.contacts_name_tv)
    EditText contactsNameTv;
    @Bind(R.id.contacts_phone_tv)
    EditText contactsPhoneTv;
    private Dialog chooseDialog;
    private View inflate;
    @Bind(R.id.remark_edit)
    EditText remarkEdit;
    private HashMap<String, Object> mParams = new HashMap<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_seat;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        for (int i = 1; i < 100; i++) {
            dataIntList.add(i + "");
        }
        horizontalselectedView.setData(dataIntList);
        horizontalselectedView.setSelectItem(4);

    }

    @Override
    public void fillData() {
        mParams.put("shop_id", getIntent().getIntExtra("shop_id", 0));
        mParams.put("login_token", SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN));
    }

    @Override
    public void setListener() {

    }

    @OnClick({R.id.select_left_im, R.id.select_right_im, R.id.date_tv, R.id.hour_time_tv, R.id.minute_time_tv, R.id.submit_btn})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.select_left_im:
                horizontalselectedView.setAnRightOffset();
                break;
            case R.id.select_right_im:
                horizontalselectedView.setAnLeftOffset();
                break;
            case R.id.date_tv:
                Calendar instance = Calendar.getInstance();
                instance.add(Calendar.DATE, 6);
                TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调

                        dateTv.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
                    }
                })
                        //年月日时分秒 的显示与否，不设置则默认全部显示
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .setLabel("", "", "", "", "", "")
                        .isCenterLabel(false)
                        .setDividerColor(Color.GRAY)
                        .setContentSize(17)
                        .setRangDate(Calendar.getInstance(), instance)
                        .setDate(Calendar.getInstance())
                        .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                        .setDecorView(null)
                        .build();
                pvTime.show();
                break;
            case R.id.hour_time_tv:
                TimePickerView pvTimeHour = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调

                        hourTimeTv.setText(new SimpleDateFormat("HH").format(date));
                    }
                })
                        //年月日时分秒 的显示与否，不设置则默认全部显示
                        .setType(new boolean[]{false, false, false, true, false, false})
                        .setLabel("", "", "", "", "", "")
                        .isCenterLabel(false)
                        .setDividerColor(Color.GRAY)
                        .setContentSize(17)
                        .setDate(Calendar.getInstance())
                        .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                        .setDecorView(null)
                        .build();
                pvTimeHour.show();
                break;
            case R.id.minute_time_tv:
                TimePickerView pvTimeMinute = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调

                        minuteTimeTv.setText(new SimpleDateFormat("mm").format(date));
                    }
                })
                        //年月日时分秒 的显示与否，不设置则默认全部显示
                        .setType(new boolean[]{false, false, false, false, true, false})
                        .setLabel("", "", "", "", "", "")
                        .isCenterLabel(false)
                        .setDividerColor(Color.GRAY)
                        .setContentSize(17)
                        .setDate(Calendar.getInstance())
                        .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                        .setDecorView(null)
                        .build();
                pvTimeMinute.show();
                break;
            case R.id.submit_btn:
                if (!canSubmit()) return;
                submit();
                break;
        }
    }

    private void submit() {
        if (null == chooseDialog) {
            inflate = getLayoutInflater().inflate(R.layout.dialog_choose_foot, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.Dialog_FS);
            builder.setView(inflate);
            chooseDialog = builder.create();
        }
        if (!chooseDialog.isShowing()) chooseDialog.show();
        inflate.findViewById(R.id.now_order_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //立即选菜
                mParams.put("type", AppConfig.BOOK_SEAT_STYLE.NOW);
                bookSeat();
            }
        });
        inflate.findViewById(R.id.to_order_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //到店点菜
                mParams.put("type", AppConfig.BOOK_SEAT_STYLE.TO);
                bookSeat();
            }
        });
    }

    //预定座位
    private void bookSeat() {
        String time = "";
        try {
            time = FormatUtil.mydateToStamp2(dateTv.getText().toString() + " " + hourTimeTv.getText().toString() + ":" + minuteTimeTv.getText().toString(), "yyyy-MM-dd HH:mm");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mParams.put("number", horizontalselectedView.getSelectedString());
        mParams.put("name", contactsNameTv.getText().toString());
        mParams.put("mobile", contactsPhoneTv.getText().toString());
        if (!TextUtils.isEmpty(remarkEdit.getText().toString()))
            mParams.put("note", remarkEdit.getText().toString());
        mParams.put("reserve_time", time);
        new MyHttp().doPost(Api.getDefault().bookSeat(mParams), new HttpListener() {
            @Override
            public void onSuccess(final JSONObject result) {
                chooseDialog.dismiss();
                if ((int) mParams.get("type") == AppConfig.BOOK_SEAT_STYLE.NOW) {
                    //立即点菜
                    new com.wbx.mall.widget.iosdialog.AlertDialog(BookSeatActivity.this).builder()
                            .setTitle("提示")
                            .setMsg("预定成功！返回店铺点菜下单")
                            .setCancelable(false)
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.putExtra("bookSeatId", result.getJSONObject("data").getString("reserve_table_id"));
                                    setResult(1008, intent);
                                    finish();
                                }
                            }).show();
                } else {
                    //到店点菜
                    Intent intent = new Intent(mContext, BookSeatPayActivity.class);
                    intent.putExtra("bookId", result.getJSONObject("data").getString("reserve_table_id"));
                    startActivity(intent);
                }
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private boolean canSubmit() {
        if (TextUtils.isEmpty(dateTv.getText().toString())) {
            showShortToast("请选择预定日期");
            return false;
        }
        if (TextUtils.isEmpty(contactsNameTv.getText().toString().trim())) {
            showShortToast("请填写联系人姓名");
            return false;
        }
        if (TextUtils.isEmpty(contactsPhoneTv.getText().toString())) {
            showShortToast("请填写联系人电话");
            return false;
        }
        return true;
    }
}
