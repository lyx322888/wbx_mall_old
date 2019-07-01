package com.wbx.mall.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.text.Html;
import android.view.Display;
import android.widget.TextView;

import com.wbx.mall.R;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wushenghui on 2017/6/2.
 */

public class UilImageGetter implements Html.ImageGetter {
    private Activity mContext;
    private TextView mContentText;

    public UilImageGetter(Context c, TextView t) {
        this.mContext = (Activity) c;
        this.mContentText = t;
    }

    @Override
    public Drawable getDrawable(String source) {
        LevelListDrawable d = new LevelListDrawable();
        Drawable empty = mContext.getResources().getDrawable(R.drawable.loading_logo);
        d.addLevel(0, 0, empty);
//        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());
        new ImageGetterAsync(mContext, mContentText, d).execute(UrlUtils.getCompressUrl(source));
        return d;
    }


    /**
     * @author ThachNguyen
     */
    public static class ImageGetterAsync extends AsyncTask<Object, Void, Bitmap> {
        // 弱引用是允许被gc回收的;
        private WeakReference<Activity> mWeakReference;
        LevelListDrawable listDrawable;
        TextView textView;
        private int mWidth, mHeight;

        ImageGetterAsync(Activity activity, TextView tv, LevelListDrawable lv) {
            this.mWeakReference = new WeakReference<>(activity);
            this.textView = tv;
            this.listDrawable = lv;
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            this.mWidth = point.x;
            this.mHeight = mWidth / 2;
        }

        @Override
        protected Bitmap doInBackground(Object... params) {
            String source = (String) params[0];
            Bitmap bitmap;
            try {
                InputStream inputStream = new URL(source).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            Activity activity = mWeakReference.get();
            if (result != null || activity != null || !activity.isFinishing() || !activity.isDestroyed()) {
                listDrawable.addLevel(1, 1, new BitmapDrawable(activity.getResources(), result));
                listDrawable.setBounds(0, 0, mWidth, mHeight);
                listDrawable.setLevel(1);
                CharSequence text = textView.getText();
                textView.setText(text);
            }
        }
    }
}
