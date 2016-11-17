package com.mpzn.mpzn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.adapter.RvCheckBuildingAdapter;
import com.mpzn.mpzn.adapter.VpRecyclerViewAdapter;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.CheckStarEntity;
import com.mpzn.mpzn.entity.StarBuildingEntity;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.listener.OnRecyclerItemClickListener;
import com.mpzn.mpzn.views.IsCanScrollViewPager;
import com.mpzn.mpzn.views.MyActionBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.Call;

import static com.mpzn.mpzn.utils.ViewUtils.dip2px;
import static com.mpzn.mpzn.utils.ViewUtils.loadedDismissProgressDialog;

public class StarAndBrowseActivity extends BaseActivity {


    @Bind(R.id.rb_star)
    RadioButton rbStar;
    @Bind(R.id.rb_browse)
    RadioButton rbBrowse;
    @Bind(R.id.btn_delete)
    Button btnDelete;
    @Bind(R.id.aciton_bar)
    MyActionBar acitonBar;
    @Bind(R.id.action_bar_edit)
    MyActionBar actionBarEdit;
    @Bind(R.id.vp_star_browse)
    IsCanScrollViewPager vpStarBrowse;
    @Bind(R.id.tv_delete_all)
    TextView tvDeleteAll;
    @Bind(R.id.cb_all)
    CheckBox cbAll;
    private RecyclerView rv_Star;
    private RecyclerView rv_Browse;
    private KProgressHUD loadProgressHUD;

    private RvCheckBuildingAdapter currentAdapter;

    public List<StarBuildingEntity.DataBean> starBuildingList = new ArrayList<>();
    private RvCheckBuildingAdapter rvStarAdapter;

    public List<StarBuildingEntity.DataBean> browseBuildingList = new ArrayList<>();
    private RvCheckBuildingAdapter rvBrowseAdapter;
    private VpRecyclerViewAdapter vpStarBrowseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        vpStarBrowse.setCurrentItem(type);
        if (type == 0) {
            rbStar.setChecked(true);
        } else if (type == 1) {
            rbBrowse.setChecked(true);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_star_and_browse;
    }

    @Override
    public void initHolder() {
        initSystemBar(this, R.color.fafafa);
        acitonBar.init("", R.drawable.return_gray, 0);
        acitonBar.setRightText("编辑");
        acitonBar.setRightTextColor(R.color.font_black_4);

        actionBarEdit.init("编辑", 0, 0);
        actionBarEdit.setRightText("完成");
        actionBarEdit.setRightTextColor(R.color.font_black_4);

        rv_Star = new RecyclerView(mContext);
        rv_Browse = new RecyclerView(mContext);


        rvStarAdapter = new RvCheckBuildingAdapter(mContext, starBuildingList,"收藏");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv_Star.setLayoutManager(linearLayoutManager);
        rv_Star.setAdapter(rvStarAdapter);

        rvBrowseAdapter = new RvCheckBuildingAdapter(mContext, browseBuildingList,"浏览");
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv_Browse.setLayoutManager(linearLayoutManager1);
        rv_Browse.setAdapter(rvBrowseAdapter);

        List<RecyclerView> recyclerViews = new ArrayList<>();
        recyclerViews.add(rv_Star);
        recyclerViews.add(rv_Browse);

        vpStarBrowseAdapter = new VpRecyclerViewAdapter(mContext,recyclerViews);
        vpStarBrowse.setAdapter(vpStarBrowseAdapter);

        currentAdapter=rvStarAdapter;


    }

    @Override
    public void initLayoutParams() {

    }

    @Override
    public void initData() {

        OkHttpUtils.get()
                .url(API.STARLIST_GET)
                .addParams("token", MyApplication.getInstance().token)
                .build()
                .execute(new StringCallback() {


                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        StarBuildingEntity starBuildingEntity = new Gson().fromJson(response, StarBuildingEntity.class);
                        List<StarBuildingEntity.DataBean> starBuildingEntityData = starBuildingEntity.getData();
                        if (starBuildingEntity.getCode() == 200) {
                            starBuildingList.addAll(starBuildingEntityData);
                            rvStarAdapter.updata(starBuildingList);
                        }else{

                        }


                    }
                });

        OkHttpUtils.get()
                .url(API.BROWSELIST_GET)
                .addParams("token", MyApplication.getInstance().token)
                .build()
                .execute(new StringCallback() {


                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        StarBuildingEntity starBuildingEntity = new Gson().fromJson(response, StarBuildingEntity.class);
                        List<StarBuildingEntity.DataBean> starBuildingEntityData = starBuildingEntity.getData();
                        if (starBuildingEntity.getCode() == 200) {
                            browseBuildingList.addAll(starBuildingEntityData);
                            rvBrowseAdapter.updata(browseBuildingList);

                        } else {

                        }


                    }
                });


    }

    @Override
    public void bindListener() {
        acitonBar.setLROnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (actionBarEdit.getVisibility() == View.GONE) {
                            finish();
                        }
                    }
                }
                ,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        actionBarEdit.setVisibility(View.VISIBLE);
                        btnDelete.setVisibility(View.VISIBLE);
                        tvDeleteAll.setVisibility(View.VISIBLE);
                        cbAll.setVisibility(View.VISIBLE);
                        cbAll.setChecked(false);
                        vpStarBrowse.setNoScroll(true);
                        currentAdapter.changeEditable(true,false);


                    }
                });

        actionBarEdit.setLROnClickListener(null,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        actionBarEdit.setVisibility(View.GONE);
                        btnDelete.setVisibility(View.GONE);
                        tvDeleteAll.setVisibility(View.GONE);
                        cbAll.setVisibility(View.GONE);
                        vpStarBrowse.setNoScroll(false);
                        currentAdapter.changeEditable(false,false);


                    }
                });
        cbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    currentAdapter.changeEditable(true,true);
                    tvDeleteAll.setText("取消全选");
                }else{
                    currentAdapter.changeEditable(true,false);
                    tvDeleteAll.setText("全选");
                }
            }
        });

        rbStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpStarBrowse.setCurrentItem(0);
            }
        });
        rbBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpStarBrowse.setCurrentItem(1);
            }
        });

        rv_Star.addOnItemTouchListener(new OnRecyclerItemClickListener(rv_Star){
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                if(starBuildingList.size()==0){
                  return;
                }
                StarBuildingEntity.DataBean dataBean = starBuildingList.get(vh.getLayoutPosition());
                Intent intent = new Intent();
                intent.putExtra("Aid",dataBean.getLoupanid());
                intent.setClass(mContext,DetailNewhouseActivity.class);
                startActivity(intent);
            }
        });
        rv_Browse.addOnItemTouchListener(new OnRecyclerItemClickListener(rv_Browse){
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                if(browseBuildingList.size()==0||actionBarEdit.getVisibility()==View.VISIBLE){
                    return ;
                }
                StarBuildingEntity.DataBean dataBean = browseBuildingList.get(vh.getLayoutPosition());
                Intent intent = new Intent();
                intent.putExtra("Aid",dataBean.getLoupanid());
                intent.setClass(mContext,DetailNewhouseActivity.class);
                startActivity(intent);
            }
        });

        vpStarBrowse.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    rbStar.setChecked(true);
                    currentAdapter=rvStarAdapter;
                } else if (position == 1) {
                    rbBrowse.setChecked(true);
                    currentAdapter=rvBrowseAdapter;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelStarOrBrowse();

            }
        });

    }

    private void cancelStarOrBrowse() {

        String LoadingMsg = null;
        String SuccussMsg = null;
        List<StarBuildingEntity.DataBean> BuildingList = null;
        String url=null;
        if(vpStarBrowse.getCurrentItem()==0){
            LoadingMsg="正在移除收藏...";
            SuccussMsg="移除收藏成功";
            BuildingList=starBuildingList;
            url=API.CANCELSTARLIST_POST;
        }else if(vpStarBrowse.getCurrentItem()==1){
            LoadingMsg="正在删除历史...";
            SuccussMsg="删除成功";
            BuildingList=browseBuildingList;
            url=API.CANCELBROWSELIST_POST;
        }

        loadProgressHUD= KProgressHUD.create(StarAndBrowseActivity.this).
                setSize(MyApplication.mScreenWidth/4, MyApplication.mScreenWidth/6).
                setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).
                setLabel(LoadingMsg).setCancellable(false).show();

        String str = "";
        final List<StarBuildingEntity.DataBean> deleteList = new ArrayList<>();
        for (StarBuildingEntity.DataBean data : BuildingList) {
            if (data.getCheck()) {
                deleteList.add(data);
                if(str==""){
                    if(vpStarBrowse.getCurrentItem()==0) {
                        str = str + data.getCollectid();
                    }else{
                        str = str + data.getCid();
                    }
                }else {

                    if(vpStarBrowse.getCurrentItem()==0) {
                        str = str + "," + data.getCollectid();
                    }else{
                        str = str + "," + data.getCid();
                    }

                }
            }
        }
        if(str==""){
            loadedDismissProgressDialog(StarAndBrowseActivity.this, false, loadProgressHUD, "请选择要移除的楼盘", false);
        }else {
            final String finalSuccussMsg = SuccussMsg;
            OkHttpUtils.post()
                    .url(url)
                    .addParams("token", MyApplication.getInstance().token)
                    .addParams("cids", str)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            loadedDismissProgressDialog(StarAndBrowseActivity.this, false, loadProgressHUD, "加载失败，请检查网络", false);

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Log.e("TAG", "response"+response);
                            CheckStarEntity checkStarEntity = new Gson().fromJson(response, CheckStarEntity.class);
                            if (checkStarEntity.getCode() == 200) {
                                loadedDismissProgressDialog(StarAndBrowseActivity.this, true, loadProgressHUD, finalSuccussMsg, false);
                                if(vpStarBrowse.getCurrentItem()==0) {
                                    starBuildingList.removeAll(deleteList);
                                    currentAdapter.updata(starBuildingList);
                                }else {
                                    browseBuildingList.removeAll(deleteList);
                                    currentAdapter.updata(browseBuildingList);
                                }


                            } else {
                                loadedDismissProgressDialog(StarAndBrowseActivity.this, false, loadProgressHUD, checkStarEntity.getMessage(), false);

                            }
                        }
                    });
        }
    }


}
