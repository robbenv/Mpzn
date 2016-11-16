package com.mpzn.mpzn.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.code19.library.NetUtils;
import com.mpzn.mpzn.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by sunfusheng on 16/4/6.
 */
public class ImageManager {

    private Context mContext;
    private static int loadImageQuality;

    public static final int SELT_ADAPTE_LOAD_URL_IMAMGE = 0;
    public static final int HIGH_QUALITY_LOAD_URL_IMAMGE = 1;
    public static final int COMMON_QUALITY_LOAD_URL_IMAMGE = 2;
    public static final int NO_LOAD_URL_IMAMGE = 3;

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";


    public ImageManager(Context context) {
        this.mContext = context;
        loadImageQuality = CacheUtils.getInt(context, "LoadImageQuality");
    }

    //设置图片质量参数
    public static void setLoadImageQuality(int imageQuality){
        loadImageQuality=imageQuality;
    }

    //根据图片质量设置改变URL

    public String getUrlFromImageQualitySet(String url){
        switch (loadImageQuality) {
            case  SELT_ADAPTE_LOAD_URL_IMAMGE:
                 if(NetUtils.isWiFi(mContext)){
                     url=url;
                 }else{
                     url=url;
                 }
                break;
            case  HIGH_QUALITY_LOAD_URL_IMAMGE:
                url=url;
                break;
            case  COMMON_QUALITY_LOAD_URL_IMAMGE:
                url=url;
                break;
            case  NO_LOAD_URL_IMAMGE:
                url="";
                break;
        }
        return url;
    }

    // 将资源ID转为Uri
    public Uri resourceIdToUri(int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + mContext.getPackageName() + FOREWARD_SLASH + resourceId);
    }

    // 加载网络图片
    public void loadUrlImage(String url, ImageView imageView) {
        Glide.with(mContext)
                .load(getUrlFromImageQualitySet(url))
                .placeholder(R.drawable.default_img)
                .error(R.drawable.default_img)
                .crossFade()
                .into(imageView);
    }

    // 加载drawable图片
    public void loadResImage(int resId, ImageView imageView) {
        Glide.with(mContext)
                .load(resourceIdToUri(resId))
                .error(R.drawable.default_img)
                .crossFade()
                .into(imageView);
    }

    // 加载本地图片
    public void loadLocalImage(String path, ImageView imageView) {
        Glide.with(mContext)
                .load("file://" + path)
                .placeholder(R.drawable.default_img)
                .error(R.drawable.default_img)
                .crossFade()
                .into(imageView);
    }


    // 加载网络圆型图片
    public void loadCircleImage(String url, ImageView imageView) {
        Glide.with(mContext)
                .load(url)
                .placeholder(R.drawable.icon_default_circle)
                .error(R.drawable.icon_default_circle)
                .crossFade()
                .transform(new GlideCircleTransform(mContext))
                .into(imageView);
    }

    // 加载网络圆型图片
    public void loadCircleImageWithWhite(String url, ImageView imageView) {
        Glide.with(mContext)
                .load(url)
                .placeholder(R.drawable.icon_default_circle)
                .error(R.drawable.icon_default_circle)
                .crossFade()
                .transform(new GlideCircleTransform(mContext,2,mContext.getResources().getColor(R.color.white)))
                .into(imageView);
    }

    // 加载bitmap圆型图片
    public void loadCircleBitmapImage(Bitmap bitmap, ImageView imageView) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();

        Glide.with(mContext)
                .load(bytes)
                .placeholder(R.drawable.icon_default_circle)
                .error(R.drawable.icon_default_circle)
                .crossFade()
                .transform(new GlideCircleTransform(mContext))
                .into(imageView);
    }

    // 加载drawable圆型图片
    public void loadCircleResImage(int resId, ImageView imageView) {
        Glide.with(mContext)
                .load(resourceIdToUri(resId))
                .placeholder(R.drawable.icon_default_circle)
                .error(R.drawable.icon_default_circle)
                .crossFade()
                .transform(new GlideCircleTransform(mContext))
                .into(imageView);
    }
    // 加载drawable圆型图片
    public void loadCircleResImageWithWhite(int resId, ImageView imageView) {
        Glide.with(mContext)
                .load(resourceIdToUri(resId))
                .placeholder(R.drawable.icon_default_circle)
                .error(R.drawable.icon_default_circle)
                .crossFade()
                .transform(new GlideCircleTransform(mContext,2,mContext.getResources().getColor(R.color.white)))
                .into(imageView);
    }


    // 加载本地圆型图片
    public void loadCircleLocalImage(String path, ImageView imageView) {
        Glide.with(mContext)
                .load("file://" + path)
                .placeholder(R.drawable.icon_default_circle)
                .error(R.drawable.icon_default_circle)
                .crossFade()
                .transform(new GlideCircleTransform(mContext))
                .into(imageView);
    }

}
