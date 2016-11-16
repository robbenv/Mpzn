package com.mpzn.mpzn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.adapter.MainHomeBuildingLvAdapter;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.BuildingList;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.utils.CacheUtils;
import com.mpzn.mpzn.utils.Constant;
import com.mpzn.mpzn.views.FilterView.FilterView;
import com.mpzn.mpzn.views.FilterView.entity.BuildingEntity;
import com.mpzn.mpzn.views.FilterView.entity.FilterData;
import com.mpzn.mpzn.views.FilterView.entity.FilterEntity;
import com.mpzn.mpzn.views.FilterView.entity.FilterTwoEntity;
import com.mpzn.mpzn.views.FilterView.entity.ModelUtil;
import com.mpzn.mpzn.views.SmoothListView.SmoothListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.mpzn.mpzn.utils.ViewUtils.dip2px;

public class SearchResultActivity extends BaseActivity {

    public static final int STOP_REFRESH=111;

    @Bind(R.id.filterView_search)
    FilterView filterViewSearch;
    @Bind(R.id.lv_search_result)
    SmoothListView lvSearchResult;
    @Bind(R.id.btn_left)
    ImageButton btnLeft;
    @Bind(R.id.et_search)
    TextView etSearch;

    private int titleViewHeight = 55; // 标题栏的高度
    private String BuildinglistJson;
    private MainHomeBuildingLvAdapter BuildingLvAdapter;

    private FilterData filterData;

    private List<BuildingEntity> buildingData = new ArrayList<>();

    private android.os.Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case  STOP_REFRESH:
                    lvSearchResult.stopRefresh();
                    break;
            }
        }
    };


    @Override
    public int getLayoutId() {
        return R.layout.activity_search_result;
    }

    @Override
    public void initHolder() {
        initSystemBar(this, R.color.searchbar_bg);

        //楼盘列表
        BuildingLvAdapter = new MainHomeBuildingLvAdapter(mContext, buildingData);
        lvSearchResult.setAdapter(BuildingLvAdapter);
        lvSearchResult.setRefreshEnable(true);
        lvSearchResult.setLoadMoreEnable(false);
        fillAdapter(buildingData);

        // 筛选数据
        filterData = new FilterData();
        filterData.setCategory(ModelUtil.getCategoryData());
        filterData.setSorts(ModelUtil.getSortData());
        filterData.setFilters(ModelUtil.getFilterData());
        // 设置筛选数据
        filterViewSearch.setFilterData(this, filterData);


    }

    @Override
    public void initLayoutParams() {

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        String searchContent = intent.getStringExtra("searchContent");
        etSearch.setText(searchContent);

        OkHttpUtils.get()
                .url(API.BUILDINGLIST_GET)
                .addParams("page","total")
                .addParams("subject", searchContent)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        handler.sendEmptyMessageDelayed(STOP_REFRESH,3000);

                        Toast.makeText(mContext, "楼盘列表获取数据失败...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        handler.sendEmptyMessageDelayed(STOP_REFRESH,3000);
                        BuildinglistJson = response;
                        initBuildingList();
                    }
                });
    }

    private void initBuildingList() {
        BuildingList buildingList = new Gson().fromJson(BuildinglistJson, BuildingList.class);
        if ("success".equals(buildingList.getMessage())) {
            buildingData = buildingList.getData();
            ModelUtil.setBuildingData(buildingData);
            fillAdapter(buildingData);
        }
    }

    // 填充数据
    private void fillAdapter(List<BuildingEntity> list) {

        if (list == null || list.size() == 0) {
            int height = MyApplication.mScreenHeight - dip2px(titleViewHeight + 50 + 45); // 95 = 标题栏高度 ＋ FilterView+底部bar的高度
            BuildingLvAdapter.setData(ModelUtil.getNoDataEntity(height));
        } else {
            BuildingLvAdapter.setData(list);
        }
    }

    @Override
    public void bindListener() {

        // 筛选视图点击
        filterViewSearch.setOnFilterClickListener(new FilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                filterViewSearch.showFilterLayout(position);
            }
        });
        // 分类Item点击
        filterViewSearch.setOnItemCategoryClickListener(new FilterView.OnItemCategoryClickListener() {
            @Override
            public void onItemCategoryClick(FilterTwoEntity entity) {
                fillAdapter(ModelUtil.getCategoryBuildingData(entity));
            }
        });

        // 排序Item点击
        filterViewSearch.setOnItemSortClickListener(new FilterView.OnItemSortClickListener() {
            @Override
            public void onItemSortClick(FilterEntity entity) {
                fillAdapter(ModelUtil.getSortBuildingData(entity));
            }
        });

        // 筛选Item点击
        filterViewSearch.setOnItemFilterClickListener(new FilterView.OnItemFilterClickListener() {
            @Override
            public void onItemFilterClick(FilterEntity entity) {
                fillAdapter(ModelUtil.getFilterBuildingData(entity));
            }
        });

        lvSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int mPosition;
                if(position>0) {
                    mPosition= position - 1;
                }else{
                    return;
                }
                BuildingEntity dataEntity = BuildingLvAdapter.getData().get(mPosition);
                Intent intent = new Intent();
                intent.putExtra("Aid", dataEntity.getAid());
                intent.putExtra("Name", dataEntity.getSubject());
                intent.setClass(mContext, DetailNewhouseActivity.class);
                startActivity(intent);
            }
        });
        lvSearchResult.setSmoothListViewListener(new SmoothListView.ISmoothListViewListener() {
            @Override
            public void onRefresh() {
                initData();
            }

            @Override
            public void onLoadMore() {
                Toast.makeText(SearchResultActivity.this, "没有更多了", Toast.LENGTH_SHORT).show();
            }
        });

        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra("SearchContent",etSearch.getText().toString());
                setResult(1,intent);
                finish();
            }
        });


    }


    @OnClick(R.id.btn_left)
    public void onClick() {
        finish();
    }



}
