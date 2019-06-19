package com.wbx.mall.utils;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wbx.mall.bean.IndexInfo;
import com.youth.banner.loader.ImageLoaderInterface;

/**
 * Created by wushenghui on 2018/1/10.
 */

public class MyImageLoader implements ImageLoaderInterface<ImageView> {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        IndexInfo.AdBean path1 = (IndexInfo.AdBean) path;
        Glide.with(context.getApplicationContext())
                .load(path1.getPhoto())
                .into(imageView);
    }

    @Override
    public ImageView createImageView(Context context) {
        return new ImageView(context);
    }
}
