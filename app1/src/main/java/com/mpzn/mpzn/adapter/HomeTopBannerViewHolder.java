package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.mpzn.mpzn.activity.MainActivity;
import com.mpzn.mpzn.base.BaseActivity;

/**
 * Created by Icy on 2016/11/4.
 */

public class HomeTopBannerViewHolder implements Holder<String> {
    private ImageView imageView;
    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, final int position, String data) {
        ((BaseActivity)context).mImageManager.loadUrlImage(data,imageView);
    }
}
