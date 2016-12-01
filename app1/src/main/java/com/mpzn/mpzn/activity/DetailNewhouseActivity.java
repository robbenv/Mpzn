package com.mpzn.mpzn.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.dalong.francyconverflow.FancyCoverFlow;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.adapter.BuildingDetailTopVpAdapter;
import com.mpzn.mpzn.adapter.MyFancyCoverFlowAdapter;
import com.mpzn.mpzn.adapter.RvMoreHouseAdapter;
import com.mpzn.mpzn.adapter.VpHouseDatailContentAdapter;
import com.mpzn.mpzn.adapter.VpPhotoViewAdapter;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.AbstractEntity;
import com.mpzn.mpzn.entity.BuildingDetailEntity;
import com.mpzn.mpzn.entity.CheckStarEntity;
import com.mpzn.mpzn.entity.MessageEntity;
import com.mpzn.mpzn.entity.SimpleEntity;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.listener.OnRecyclerItemClickListener;
import com.mpzn.mpzn.utils.PermissionsChecker;
import com.mpzn.mpzn.views.MyActionBar;
import com.mpzn.mpzn.views.MyDialog;
import com.mpzn.mpzn.views.MyProgressDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.Call;

import static com.mpzn.mpzn.application.MyApplication.token;
import static com.mpzn.mpzn.utils.Constant.RESCODE_JINGJICOM;
import static com.mpzn.mpzn.utils.Constant.RESCODE_JINGJIREN;
import static com.mpzn.mpzn.utils.ViewUtils.loadedDismissProgressDialog;
import static com.mpzn.mpzn.utils.ViewUtils.showCustomProgressDialog;

public class DetailNewhouseActivity extends BaseActivity {


    private static final String TAG = "DetailNewhouseActivity";
    @Bind(R.id.vp_detail_house_top)
    ViewPager vpDetailHouseTop;
    @Bind(R.id.tv_detail_house_des)
    TextView tvDetailHouseDes;
    @Bind(R.id.tv_detail_house_price)
    TextView tvDetailHousePrice;
    @Bind(R.id.tv_detail_house_area)
    TextView tvDetailHouseArea;
    @Bind(R.id.tv_detail_house_weifang)
    TextView tvDetailHouseWeifang;
    @Bind(R.id.tv_detail_house_type)
    TextView tvDetailHouseType;
    @Bind(R.id.tv_detail_zxcd_tag3)
    TextView tvDetailZxcdTag3;
    @Bind(R.id.tv_detail_ts_tag1)
    TextView tvDetailTsTag1;
    @Bind(R.id.tv_detail_ts_tag2)
    TextView tvDetailTsTag2;
    @Bind(R.id.tv_kprq)
    TextView tvKprq;
    @Bind(R.id.tv_jfrq)
    TextView tvJfrq;
    @Bind(R.id.ll_hxjs)
    LinearLayout llHxjs;
    @Bind(R.id.fc_house_type)
    FancyCoverFlow fcHouseType;
    @Bind(R.id.tab_vp)
    TabLayout tabVp;
    @Bind(R.id.vp_house_detail_content)
    ViewPager vpHouseDetailContent;
    @Bind(R.id.rv_horizontal_more_house)
    RecyclerView rvHorizontalMoreHouse;
    @Bind(R.id.tv_detail_bottom_left)
    TextView tvDetailBottomLeft;
    @Bind(R.id.tv_detail_bottom_right)
    TextView tvDetailBottomRight;
    @Bind(R.id.detail_house_bottom)
    LinearLayout detailHouseBottom;
    @Bind(R.id.vp_photoView)
    ViewPager vpPhotoView;
    @Bind(R.id.cb_star)
    CheckBox cbStar;
    @Bind(R.id.tv_add_star)
    TextView tvAddStar;
    @Bind(R.id.my_action_bar)
    MyActionBar myActionBar;
    @Bind(R.id.sv_building_detail)
    ScrollView svBuildingDetail;

    private MyFancyCoverFlowAdapter myFancyCoverFlowAdapter;
    private VpPhotoViewAdapter vpPhotoViewAdapter;
    private VpHouseDatailContentAdapter vpHouseDatailContentAdapter;
    private Info mRectF;
    private PhotoView vpPrimaryItem;
    private int mPosition = 1;
    private int aid;
    private BuildingDetailEntity.DataBean buildingEntitydata;
    private int cid = 0;


    private List<String> toppics = new ArrayList<>();

    private BuildingDetailTopVpAdapter buildingDetailTopVpAdapter;
    private MessageEntity messageEntity;

    static final String[] mPermissionList = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.GET_ACCOUNTS};

    // 权限检测器
    private PermissionsChecker mPermissionsChecker;

    private static final int REQUEST_CODE = 0; // 请求码


//    private UMShareAPI mShareAPI;

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(DetailNewhouseActivity.this, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DetailNewhouseActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(DetailNewhouseActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(DetailNewhouseActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mShareAPI = UMShareAPI.get(this);
    }

    @Override
    public int getLayoutId() {

        return R.layout.activity_detail_new_house;
    }

    @Override
    public void initHolder() {
        buildingDetailTopVpAdapter = new BuildingDetailTopVpAdapter(mContext, toppics);
        vpDetailHouseTop.setAdapter(buildingDetailTopVpAdapter);


    }

    @Override
    public void initLayoutParams() {

    }

    public void updataView() {
        Toast.makeText(DetailNewhouseActivity.this, "aid" + aid, Toast.LENGTH_SHORT).show();
        if (aid != -1) {
            OkHttpUtils
                    .get()
                    .url(API.BUILDINGDETAIL_GET)
                    .addParams("aid", aid + "")
                    .addParams("fromtype", "androidapp")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Toast.makeText(mContext, "获取数据失败...", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            String buildDetailJson = response;
                            BuildingDetailEntity buildingDetailEntity = new Gson().fromJson(buildDetailJson, BuildingDetailEntity.class);

                            if ("success".equals(buildingDetailEntity.getMessage())) {
                                buildingEntitydata = buildingDetailEntity.getData().get(0);


                                buildingDetailTopVpAdapter.updata(buildingEntitydata.getToppic());

                                tvDetailHouseDes.setText(buildingEntitydata.getAbstractX());

                                tvDetailHousePrice.setText(buildingEntitydata.getDj() + "");

                                initTag(tvDetailHouseArea, buildingEntitydata.getDiqu());

                                initTag(tvDetailHouseWeifang, buildingEntitydata.getTitle());

                                initTag(tvDetailHouseType, buildingEntitydata.getTag().get(0));

                                initTag(tvDetailTsTag1, buildingEntitydata.getTag().get(1));

                                initTag(tvDetailTsTag2, buildingEntitydata.getTag().get(2));

                                initTag(tvDetailZxcdTag3, buildingEntitydata.getTag().get(3));


                                tvKprq.setText(buildingEntitydata.getKprq());
                                tvJfrq.setText(buildingEntitydata.getJfrq());

                                List<String> pingmiantu = buildingEntitydata.getLppmt();
                                if (pingmiantu.size() != 0) {
                                    vpPhotoViewAdapter = new VpPhotoViewAdapter(mContext, pingmiantu);
                                    vpPhotoView.setAdapter(vpPhotoViewAdapter);
                                    vpPhotoView.setCurrentItem(mPosition);

                                    vpPhotoViewAdapter.setOnPageSelectedListener(new VpPhotoViewAdapter.OnPageSelectedListener() {

                                        @Override
                                        public void setOnClickListenerForPv(PhotoView pv) {
                                            setOnClickHideVpPhotoView(pv);
                                        }


                                    });


                                    myFancyCoverFlowAdapter = new MyFancyCoverFlowAdapter(mContext, buildingEntitydata.getLppmt());
                                    fcHouseType.setMaxRotation(0);//设置最大旋转角度
                                    fcHouseType.setAdapter(myFancyCoverFlowAdapter);
                                    fcHouseType.setSelection(mPosition);
                                } else {
                                    llHxjs.setVisibility(View.GONE);
                                }

                                vpHouseDatailContentAdapter = new VpHouseDatailContentAdapter(mContext, buildingEntitydata);
                                vpHouseDetailContent.setAdapter(vpHouseDatailContentAdapter);
                                tabVp.setupWithViewPager(vpHouseDetailContent);


                                View mapimg = vpHouseDatailContentAdapter.getPage(2);
                                mapimg.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent();
                                        intent.setClass(mContext, MapActivity.class);
                                        intent.putExtra("lpdetail", buildingEntitydata);
                                        startActivity(intent);
                                    }
                                });


                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailNewhouseActivity.this);
                                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                rvHorizontalMoreHouse.setLayoutManager(linearLayoutManager);
                                rvHorizontalMoreHouse.setAdapter(new RvMoreHouseAdapter(mContext, buildingEntitydata.getMorebuilding()));
                            }

                        }
                    });

        }

    }

    public void initTag(TextView tv, String tag) {
        if (tag == null || tag == "") {
            tv.setVisibility(View.GONE);

        } else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(tag);
        }
    }

    @Override
    public void initData() {
        mPermissionsChecker = new PermissionsChecker(this);
        Intent intent = getIntent();
        aid = intent.getIntExtra("Aid", -1);
        Log.i("Proxy_test34", "initData()__aid = " + aid);
        Log.i(TAG, "initData()__aid = " + aid);
        initAbtract();
        updataView();
        initStarCheckBox();
        initTwoBtn();
        addBrowseList();

    }

    private void addBrowseList() {
        if (token == null || "".equals(token)) {
            return;
        }
        OkHttpUtils.post()
                .url(API.ADDBROWSE_POST)
                .addParams("token", token)
                .addParams("aid", aid + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }
                });
    }

    private void initAbtract() {
        OkHttpUtils.get()
                .url(API.BUILDINGABTRACT_GET)
                .addParams("aid", aid + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("Proxy_test34", "onError()__");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        AbstractEntity abstractEntity = new Gson().fromJson(response, AbstractEntity.class);
                        if (abstractEntity.getCode() == 200) {
                            Log.i("Proxy_test34", "onResponse()__200");
                            messageEntity = abstractEntity.getData();
                            Log.i("Proxy_test34", "onResponse()__messageEntity = " + messageEntity.toString());
                            Log.i(TAG, "onResponse()__messageEntity = " + messageEntity);
                            myActionBar.init(messageEntity.getSubject(), R.drawable.return_red, R.drawable.share);
                        }

                    }
                });

    }

    private void initStarCheckBox() {
        if (MyApplication.isLogined) {

            OkHttpUtils.get()
                    .url(API.CHECKSTAR_GET)
                    .addParams("token", MyApplication.getInstance().token)
                    .addParams("aid", aid + "")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            playShortToast("请检查网络...");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            CheckStarEntity checkStarEntity = new Gson().fromJson(response, CheckStarEntity.class);
                            if (checkStarEntity.getCode() == 200) {
                                cbStar.setChecked(true);
                                cid = checkStarEntity.getData().getCid();
                            } else {
                                cbStar.setChecked(false);
                            }

                        }
                    });

        }

    }

    private void initTwoBtn() {
        if (MyApplication.isLogined) {
            if (MyApplication.getInstance().mUserMsg.getmChild() == RESCODE_JINGJIREN) {

                tvDetailBottomLeft.setEnabled(false);
                tvDetailBottomRight.setEnabled(true);

            } else if (MyApplication.getInstance().mUserMsg.getmChild() == RESCODE_JINGJICOM) {

                tvDetailBottomLeft.setEnabled(true);
                tvDetailBottomRight.setEnabled(true);

            } else {
                tvDetailBottomLeft.setEnabled(false);
                tvDetailBottomRight.setEnabled(false);

            }
        } else {
            tvDetailBottomLeft.setEnabled(false);
            tvDetailBottomRight.setEnabled(false);
        }
    }

    @Override
    public void bindListener() {
        myActionBar.setLROnClickListener(null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //6.0版本需要检查权限
                if (messageEntity != null) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
                        ActivityCompat.requestPermissions(DetailNewhouseActivity.this, mPermissionList, 123);
                    }

                    String imgUrl = messageEntity.getThumb();
                    if (imgUrl == null || imgUrl == "") {
                        imgUrl = "http://www.mpzn.com/userfiles/image/20160216/16153634da8575901d4313.png";
                    }


                    Log.i("bug_browse", "onClick()__id = " + messageEntity.getAid());
                    new ShareAction(DetailNewhouseActivity.this).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                            .withTitle(messageEntity.getSubject())
                            .withText(messageEntity.getAbstractX())
                            .withMedia(new UMImage(DetailNewhouseActivity.this, imgUrl))
//                            .withTargetUrl(messageEntity.getUrl())
                            .withTargetUrl("http://www.mpzn.com/mobile/archive.php?aid=" + messageEntity.getAid())
                            .setCallback(umShareListener)
                            .open();
                }

            }

        });


        fcHouseType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mPosition) {

                    String selectedItem = (String) fcHouseType.getSelectedItem();

                    if (selectedItem != null) {
                        vpPhotoView.setVisibility(View.VISIBLE);
                        vpPhotoView.setCurrentItem(position, false);

                        //获取img1的信息

                        mRectF = ((PhotoView) view.findViewById(R.id.iv_gallery)).getInfo();

                        Log.e("TAG", "mRectF" + mRectF);
                        //让img2从img1的位置变换到他本身的位置
                        vpPrimaryItem = (PhotoView) vpPhotoViewAdapter.getPrimaryItem();
                        if (vpPrimaryItem != null) {
//                            setOnClickHideVpPhotoView(vpPrimaryItem);
                            vpPrimaryItem.animaFrom(mRectF);
                        } else {
                            Log.e("TAG", "vpPrimaryItem=null");
                        }


                    }
                }


                mPosition = position;

            }

        });
        fcHouseType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        vpPhotoView.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            public void onPageSelected(int position) {
                Log.e("TAG", "onPageSelected:" + position);
                mPosition = position;
//                vpPrimaryItem = (PhotoView) vpPhotoViewAdapter.getPrimaryItem();
//                setOnClickHideVpPhotoView(vpPrimaryItem);
                fcHouseType.setSelection(position);

            }
        });


        rvHorizontalMoreHouse.addOnItemTouchListener(new OnRecyclerItemClickListener(rvHorizontalMoreHouse) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                aid = buildingEntitydata.getMorebuilding().get(vh.getLayoutPosition()).getAid();
                Intent intent = new Intent();
                intent.putExtra("Aid", aid);
                intent.setClass(mContext, DetailNewhouseActivity.class);
                startActivity(intent);

            }
        });

        cbStar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!MyApplication.isLogined) {
                    showCustomProgressDialog(DetailNewhouseActivity.this, "请先登录", R.drawable.toast_error);
                }

                if (isChecked) {
                    tvAddStar.setText("取消收藏 ");
                    playShortToast("收藏成功");
                } else {
                    tvAddStar.setText("添加收藏");
                    playShortToast("取消收藏成功");
                }
            }
        });
        tvDetailBottomLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final MyProgressDialog myProgressDialog = new MyProgressDialog(mContext);
                myProgressDialog.show();
                myProgressDialog.setContent("正在申请代销");
                OkHttpUtils.get()
                        .url(API.CHECK_SELL_GET)
                        .addParams("aid", aid + "")
                        .addParams("token", MyApplication.getInstance().token)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                myProgressDialog.afterprogress("联网失败");

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                SimpleEntity simpleEntity = new Gson().fromJson(response, SimpleEntity.class);
                                if (simpleEntity.getCode() == 1300) {
                                    myProgressDialog.dismiss();

                                    final MyDialog myDialog = new MyDialog(mContext);
                                    myDialog.setTitle("申请代销");
                                    myDialog.setContent("是否申请代销?");
                                    myDialog.setCommit("申请");
                                    myDialog.setCommitListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            myDialog.dismiss();

                                            final MyProgressDialog myProgressDialog1 = new MyProgressDialog(mContext);
                                            myProgressDialog1.show();
                                            myProgressDialog1.setContent("正在申请代销...");

                                            OkHttpUtils.post()
                                                    .url(API.APPLYBUILDING_POST)
                                                    .addParams("token", MyApplication.getInstance().token)
                                                    .addParams("aid", aid + "")
                                                    .build()
                                                    .execute(new StringCallback() {
                                                        @Override
                                                        public void onError(Call call, Exception e, int id) {
                                                            myProgressDialog1.afterprogress("服务器未响应");
                                                        }

                                                        @Override
                                                        public void onResponse(String response, int id) {
                                                            CheckStarEntity checkStarEntity = new Gson().fromJson(response, CheckStarEntity.class);
                                                            if (checkStarEntity.getCode() == 200) {
                                                                myProgressDialog1.afterprogress("申请代销成功!");

                                                            } else {
                                                                myProgressDialog1.afterprogress("申请代销失败," + checkStarEntity.getMessage());

                                                            }
                                                        }
                                                    });

                                            myProgressDialog.afterprogress("申请成功");
                                        }
                                    });
                                    myDialog.show();

                                } else {
                                    myProgressDialog.afterprogress(simpleEntity.getMessage());
                                }

                            }
                        });

            }
        });
        tvDetailBottomRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyProgressDialog myProgressDialog = new MyProgressDialog(mContext);
                myProgressDialog.show();
                myProgressDialog.setContent("正在检测是否可添加报备");
                OkHttpUtils.get()
                        .url(API.CHECK_IS_ADD_GET)
                        .addParams("token", token)
                        .addParams("aid", aid + "")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                myProgressDialog.afterprogress("服务器未响应");
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                SimpleEntity simpleEntity = new Gson().fromJson(response, SimpleEntity.class);
                                if (simpleEntity.getCode() == 200) {
                                    myProgressDialog.dismiss();
                                    Intent intent = new Intent();
                                    intent.putExtra("Aid", aid);
                                    intent.setClass(mContext, AddBBActivity.class);
                                    startActivity(intent);
                                } else {
                                    myProgressDialog.afterprogress(simpleEntity.getMessage());
                                }
                            }
                        });


            }
        });

    }

    private void starBuilding() {

        OkHttpUtils.post()
                .url(API.STAR_POST)
                .addParams("token", MyApplication.getInstance().token)
                .addParams("aid", aid + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        playShortToast("请检查网络...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "starBuilding_response" + response);
                        CheckStarEntity checkStarEntity = new Gson().fromJson(response, CheckStarEntity.class);
//                        if (checkStarEntity.getCode() == 200) {
//
//                        } else {
//                            playShortToast("收藏失败," + checkStarEntity.getMessage());
//                        }

                    }
                });


    }

    private void disStarBuilding() {

        OkHttpUtils.post()
                .url(API.CANCELSTAR_POST)
                .addParams("token", MyApplication.getInstance().token)
                .addParams("cid", cid + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        playShortToast("请检查网络...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "disStarBuilding_response" + response);
                        CheckStarEntity checkStarEntity = new Gson().fromJson(response, CheckStarEntity.class);
//                        if (checkStarEntity.getCode() == 200) {
//                        } else {
//                            playShortToast("取消收藏失败" + checkStarEntity.getMessage());
//                        }

                    }
                });

    }


    //隐藏全屏PhotoView
    private void setOnClickHideVpPhotoView(final PhotoView pv) {
        pv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pv.animaTo(mRectF, new Runnable() {
                    @Override
                    public void run() {
                        vpPhotoView.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (vpPhotoView.getVisibility() == View.VISIBLE) {
            ((PhotoView) vpPhotoViewAdapter.getPrimaryItem()).animaTo(mRectF, new Runnable() {
                @Override
                public void run() {
                    vpPhotoView.setVisibility(View.GONE);
                }
            });

        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cbStar.isChecked() && cid == 0) {
            starBuilding();
        } else if ((!cbStar.isChecked()) && cid != 0) {
            disStarBuilding();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(mPermissionList)) {
            startPermissionsActivity();
        }

    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, mPermissionList);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }
}
