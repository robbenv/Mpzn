package com.mpzn.mpzn.activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.utils.CacheUtils;
import com.mpzn.mpzn.utils.ImageManager;
import com.mpzn.mpzn.views.MyActionBar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageBrowseSetActivity extends BaseActivity {


    @Bind(R.id.my_action_bar)
    MyActionBar myActionBar;
    @Bind(R.id.rb_self_adapter)
    RadioButton rbSelfAdapter;
    @Bind(R.id.rb_high_quality)
    RadioButton rbHighQuality;
    @Bind(R.id.rb_common_quality)
    RadioButton rbCommonQuality;
    @Bind(R.id.rb_no_image)
    RadioButton rbNoImage;
    @Bind(R.id.rg_image_browse_set)
    RadioGroup rgImageBrowseSet;
    @Bind(R.id.activity_image_browse_set)
    LinearLayout activityImageBrowseSet;

    @Override
    public int getLayoutId() {
        return R.layout.activity_image_browse_set;
    }

    @Override
    public void initHolder() {
        initSystemBar(this, 0);
        myActionBar.init("图片浏览设置",R.drawable.return_white,0);


    }

    @Override
    public void initLayoutParams() {

    }

    @Override
    public void initData() {
        int loadImageQuality = CacheUtils.getInt(this, "LoadImageQuality");
        ((RadioButton)rgImageBrowseSet.getChildAt(loadImageQuality)).setChecked(true);

    }

    @Override
    public void bindListener() {
        myActionBar.setLROnClickListener(null,null);

        rgImageBrowseSet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int LoadImageQuality=0;
                switch (checkedId) {
                    case  R.id.rb_self_adapter:
                        LoadImageQuality=ImageManager.SELT_ADAPTE_LOAD_URL_IMAMGE;
                        break;
                    case  R.id.rb_high_quality:
                        LoadImageQuality=ImageManager.HIGH_QUALITY_LOAD_URL_IMAMGE;
                        break;
                    case  R.id.rb_common_quality:
                        LoadImageQuality=ImageManager.COMMON_QUALITY_LOAD_URL_IMAMGE;
                        break;
                    case  R.id.rb_no_image:
                        LoadImageQuality=ImageManager.NO_LOAD_URL_IMAMGE;
                        break;
                }
                ImageManager.setLoadImageQuality(LoadImageQuality);
                CacheUtils.putInt(mContext,"LoadImageQuality", LoadImageQuality);

            }
        });

    }


}
