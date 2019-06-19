package com.wbx.mall.utils;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.util.Util;
import com.wbx.mall.R;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Description : 图片加载工具类 使用glide框架封装
 */
public class GlideUtils {

    public static void showSmallPic(Context context, ImageView imageView, String url) {
        if (imageView == null || TextUtils.isEmpty(url)) {
            return;
        }
        if (url.startsWith("http://imgs.wbx365.com/")) {
            url = url + "?imageView2/0/w/100/h/100";
        }
        if (Util.isOnMainThread()) {
            Glide.with(context).load(url).error(R.drawable.loading_logo).centerCrop().into(imageView);
        }

    }

    public static void showRoundSmallPic(Context context, ImageView imageView, String url) {
        if (imageView == null || TextUtils.isEmpty(url)) {
            return;
        }
        if (url.startsWith("http://imgs.wbx365.com/")) {
            url = url + "?imageView2/0/w/100/h/100";
        }
        if (Util.isOnMainThread()) {
            Glide.with(context).load(url).error(R.drawable.loading_logo).centerCrop().transform(new GlideRoundImage(context)).into(imageView);
        }

    }

    public static void showMediumPic(Context context, ImageView imageView, String url) {
        if (imageView == null || TextUtils.isEmpty(url)) {
            return;
        }
        if (url.startsWith("http://imgs.wbx365.com/")) {
            url = url + "?imageView2/0/w/200/h/200";
        }
        if (Util.isOnMainThread()) {
            Glide.with(context).load(url).error(R.drawable.loading_logo).centerCrop().into(imageView);
        }
    }

    public static void showRoundMediumPic(Context context, ImageView imageView, String url) {
        if (imageView == null || TextUtils.isEmpty(url)) {
            return;
        }
        if (url.startsWith("http://imgs.wbx365.com/")) {
            url = url + "?imageView2/0/w/200/h/200";
        }
        if (Util.isOnMainThread()) {
            Glide.with(context).load(url).error(R.drawable.loading_logo).centerCrop().transform(new GlideRoundImage(context)).into(imageView);
        }
    }

    public static void showBigPic(Context context, ImageView imageView, String url) {
        if (imageView == null || TextUtils.isEmpty(url)) {
            return;
        }
        if (url.startsWith("http://imgs.wbx365.com/")) {
            url = url + "?imageView2/0/w/800/h/800";
        }
        if (Util.isOnMainThread()) {
            Glide.with(context).load(url).error(R.drawable.loading_logo).centerCrop().into(imageView);

        }
    }

    public static void showRoundBigPic(Context context, ImageView imageView, String url) {
        if (imageView == null || TextUtils.isEmpty(url)) {
            return;
        }
        if (url.startsWith("http://imgs.wbx365.com/")) {
            url = url + "?imageView2/0/w/800/h/800";
        }
        if (Util.isOnMainThread()) {
            Glide.with(context).load(url).error(R.drawable.loading_logo).centerCrop().transform(new GlideRoundImage(context)).into(imageView);

        }
    }

    public static void displayUri(Context context, ImageView imageView, Uri uri) {
        if (imageView == null) {
            return;
        }
        if (Util.isOnMainThread()) {
            Glide.with(context).load(uri).error(R.drawable.loading_logo).centerCrop().into(imageView);

        }
    }

    public static void showBlurBigPic(Context context, ImageView imageView, String url) {
        if (imageView == null || TextUtils.isEmpty(url)) {
            return;
        }
        if (Util.isOnMainThread()) {
            Glide.with(context).load(url).error(R.drawable.loading_logo).bitmapTransform(new BlurTransformation(context, 15), new CenterCrop(context))
                    .into(imageView);
        }

    }
}
