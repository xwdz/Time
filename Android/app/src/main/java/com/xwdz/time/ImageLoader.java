package com.xwdz.time;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.xwdz.time.util.GlideRoundTransform;

/**
 * 图片下载器
 *
 * @author 黄兴伟 (xwd9989@gamil.com)
 * @since 2018/9/11
 */
public class ImageLoader {


    public synchronized static void load(Context context, final String url, ImageView imageView) {
        Log.i("Loader", "url:" + url);
        Glide.with(context)
                .load(url)
                .transform(new CenterCrop(context), new GlideRoundTransform(context))
                .into(new GlideDrawableImageViewTarget(imageView) {
                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        Log.i("Loader", "url:" + url + " error:" + e.toString());
                    }
                });
    }

}
