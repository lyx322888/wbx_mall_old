package com.wbx.mall.adapter;

import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbx.mall.R;
import com.wbx.mall.bean.MyFreeOrderBean;
import com.wbx.mall.utils.DateUtil;
import com.wbx.mall.utils.GlideUtils;

import java.util.List;

public class MyFreeOrderAdapter extends BaseQuickAdapter<MyFreeOrderBean, BaseViewHolder> {
    private SparseArray<CountDownTimer> countDownTimerSparseArray = new SparseArray<>();

    public MyFreeOrderAdapter(@Nullable List<MyFreeOrderBean> data) {
        super(R.layout.item_my_free_order, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MyFreeOrderBean item) {
        TextView tvType = helper.getView(R.id.tv_type);
        if (item.getActivity_type() == 1) {
            //消费免单
            tvType.setText("消费免单");
            tvType.setBackgroundResource(R.drawable.bg_btn_consume_free_2);
        } else {
            //分享免单
            tvType.setText("分享免单");
            tvType.setBackgroundResource(R.drawable.bg_btn_share_free_2);
        }
        GlideUtils.showMediumPic(mContext, (ImageView) helper.getView(R.id.iv_goods), item.getPhoto());
        helper.setText(R.id.tv_name, item.getTitle())
                .setText(R.id.tv_participate_num, String.valueOf(item.getCount_activity_users()))
                .setText(R.id.tv_remain_num, String.valueOf(item.getSurplus_users()));
        TextView tvFreeGain = helper.getView(R.id.tv_free_gain);
        TextView tvFail = helper.getView(R.id.tv_fail);
        TextView tvFreeAgain = helper.getView(R.id.tv_free_again);
        LinearLayout llCountDownTime = helper.getView(R.id.ll_count_down_time);
        final TextView tvHour = helper.getView(R.id.tv_hour);
        final TextView tvMinute = helper.getView(R.id.tv_minute);
        final TextView tvSecond = helper.getView(R.id.tv_second);
        TextView tvInvite = helper.getView(R.id.tv_invite);
        TextView tvComment = helper.getView(R.id.tv_comment);
        tvFreeGain.setVisibility(View.GONE);
        tvFail.setVisibility(View.GONE);
        tvFreeAgain.setVisibility(View.GONE);
        llCountDownTime.setVisibility(View.GONE);
        tvInvite.setVisibility(View.GONE);
        tvComment.setVisibility(View.GONE);
        CountDownTimer countDownTimer = (CountDownTimer) tvHour.getTag();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        ImageView ivStatus = helper.getView(R.id.iv_status);
        switch (item.getStatus()) {
            case 0:
                //进行中
                ivStatus.setImageResource(R.drawable.icon_free_activity_continu);
                tvInvite.setVisibility(View.VISIBLE);
                if (item.getForever() != 1) {
                    llCountDownTime.setVisibility(View.VISIBLE);
                    tvHour.setText(DateUtil.getHourNoDay((int) item.getSurplus_time()));
                    tvMinute.setText(DateUtil.getMinute((int) item.getSurplus_time()));
                    tvSecond.setText(DateUtil.getSecond((int) item.getSurplus_time()));
                    countDownTimer = new CountDownTimer(item.getSurplus_time() * 1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            tvHour.setText(DateUtil.getHourNoDay((int) (millisUntilFinished / 1000)));
                            tvMinute.setText(DateUtil.getMinute((int) (millisUntilFinished / 1000)));
                            tvSecond.setText(DateUtil.getSecond((int) (millisUntilFinished / 1000)));
                        }

                        @Override
                        public void onFinish() {
                            tvHour.setText("00");
                            tvMinute.setText("00");
                            tvSecond.setText("00");
                        }
                    };
                    countDownTimer.start();
                    tvHour.setTag(countDownTimer);
                    countDownTimerSparseArray.put(tvHour.hashCode(), countDownTimer);
                }
                break;
            case 1:
                //成功
                if (item.getActivity_type() == 1) {
                    //消费免单
                    if (item.getIs_lottery() == 1) {
                        //中奖
                        ivStatus.setImageResource(R.drawable.icon_free_activity_win);
                        tvFreeGain.setVisibility(View.VISIBLE);
                        if (item.getIs_gain() == 1) {
                            tvFreeGain.setText("已领取");
                        } else {
                            tvFreeGain.setText("免费领");
                        }
                        tvComment.setVisibility(View.VISIBLE);
                    } else {
                        ivStatus.setImageResource(R.drawable.icon_free_activity_success);
                    }
                } else {
                    ivStatus.setImageResource(R.drawable.icon_free_activity_success);
                    tvFreeGain.setVisibility(View.VISIBLE);
                    if (item.getIs_gain() == 1) {
                        tvFreeGain.setText("已领取");
                    } else {
                        tvFreeGain.setText("免费领");
                    }
                    tvComment.setVisibility(View.VISIBLE);
                }
                tvFreeAgain.setVisibility(View.VISIBLE);
                break;
            case 2:
                //失败
                ivStatus.setImageResource(R.drawable.icon_free_activity_fail);
                tvFail.setVisibility(View.VISIBLE);
                tvFreeAgain.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

        helper.addOnClickListener(R.id.tv_free_gain)
                .addOnClickListener(R.id.rl_goods)
                .addOnClickListener(R.id.tv_free_again)
                .addOnClickListener(R.id.tv_comment)
                .addOnClickListener(R.id.tv_invite);
    }

    public void cancelAllTimer() {
        if (countDownTimerSparseArray != null) {
            for (int i = 0; i < countDownTimerSparseArray.size(); i++) {
                CountDownTimer countDownTimer = countDownTimerSparseArray.get(countDownTimerSparseArray.keyAt(i));
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
            }
        }
    }

}
