package com.wbx.mall.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hedgehog.ratingbar.RatingBar;
import com.wbx.mall.R;
import com.wbx.mall.adapter.TakePhotoAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.utils.UpLoadPicUtils;
import com.wbx.mall.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

/**
 * Created by wushenghui on 2017/7/14.
 */

public class CommentActivity extends BaseActivity {
    @Bind(R.id.comment_photo_rv)
    RecyclerView photoRv;
    @Bind(R.id.select_count_tv)
    TextView selectCountTv;
    @Bind(R.id.rb_score)
    RatingBar mRatingBar;
    @Bind(R.id.comment_content_edit)
    EditText messageEdit;
    private int mScore;

    private ArrayList<String> picList = new ArrayList<>();
    private ArrayList<String> lstQiNiuPath = new ArrayList<>();
    private TakePhotoAdapter mAdapter;
    private HashMap<String, Object> mParams = new HashMap<>();
    private boolean physical;

    @Override
    public int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        picList.add("");
        mAdapter = new TakePhotoAdapter(picList, this);
        photoRv.setLayoutManager(new GridLayoutManager(mContext, 3));
        photoRv.setAdapter(mAdapter);

        mRatingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                mScore = (int) RatingCount;
            }
        });
    }

    @Override
    public void fillData() {
        mParams.put("order_id", getIntent().getStringExtra("orderId"));
        physical = getIntent().getBooleanExtra("physical", false);
        mParams.put("type", physical ? 2 : 1);
        mParams.put("login_token", SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN));
    }

    @Override
    public void setListener() {
        mAdapter.setOnItemClickListener(R.id.root_view, new BaseAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                if (position == 0) {
                    if (picList.size() - 1 >= AppConfig.MAX_COUNT) {
                        showShortToast("最多只能选6张哦！");
                        return;
                    }
                    PhotoPicker.builder()
                            .setPhotoCount(AppConfig.MAX_COUNT - (picList.size() - 1))
                            .setShowCamera(true)
                            .setPreviewEnabled(false)
                            .start(mActivity, PhotoPicker.REQUEST_CODE);
                } else {
                    ArrayList lstPic = new ArrayList();
                    lstPic.addAll(picList);
                    lstPic.remove(0);
                    PhotoPreview.builder()
                            .setPhotos(lstPic)
                            .setCurrentItem(position - 1)
                            .setShowDeleteButton(false)
                            .start(mActivity);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> stringArrayListExtra = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                picList.addAll(stringArrayListExtra);
                updateCount();
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    public void updateCount() {
        selectCountTv.setText(picList.size() - 1 + "/6");
    }

    @OnClick({R.id.submit_comment_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_comment_btn:
                if (TextUtils.isEmpty(messageEdit.getText())) {
                    showShortToast("说点什么吧？");
                    return;
                }
                LoadingDialog.showDialogForLoading(mActivity, "提交中...", true);
                if (picList.size() > 1) {
                    upLoadPic();
                } else {
                    submit();
                }
                break;
        }
    }

    private void upLoadPic() {
        lstQiNiuPath.clear();
        for (int i = 1; i < picList.size(); i++) {
            UpLoadPicUtils.upOnePic(picList.get(i), new UpLoadPicUtils.UpLoadPicListener() {
                @Override
                public void success(String qiNiuPath) {
                    LoadingDialog.cancelDialogForLoading();
                    lstQiNiuPath.add(qiNiuPath);
                    if (picList.size() - 1 == lstQiNiuPath.size()) {
                        mParams.put("pics", JSONArray.toJSONString(lstQiNiuPath));
                        submit();
                    }
                }

                @Override
                public void error() {
                    LoadingDialog.cancelDialogForLoading();
                    showShortToast("图片上传失败，请重试。");
                }
            });
        }
    }

    private void submit() {
        mParams.put("grade", mScore);
        mParams.put("message", messageEdit.getText().toString());
        new MyHttp().doPost(Api.getDefault().publishComment(mParams), new HttpListener() {
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
}
