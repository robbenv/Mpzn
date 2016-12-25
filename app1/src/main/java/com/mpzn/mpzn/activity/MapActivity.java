package com.mpzn.mpzn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.BuildingDetailEntity;
import com.mpzn.mpzn.views.MyActionBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Icy on 2016/9/19.
 */
public class MapActivity extends BaseActivity {
    @Bind(R.id.bmapView)
    MapView bmapView;
    @Bind(R.id.action_bar)
    MyActionBar actionBar;
    
    private BaiduMap mBaiduMap;

    private BuildingDetailEntity.DataBean lpdetail;
    private float latx ;
    private float laty ;

    @Override
    public int getLayoutId() {
        SDKInitializer.initialize(MyApplication.mContext);
        return R.layout.mapview_housedetail;
    }

    @Override
    public void initHolder(){
        mBaiduMap = bmapView.getMap();
    }

    private void initMyLocation() {
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(100)
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(90.0f)
                .latitude(latx)
                .longitude(laty).build();

        float f = mBaiduMap.getMaxZoomLevel();//19.0 最小比例尺
        //float m = mBaiduMap.getMinZoomLevel();//3.0 最大比例尺
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//普通地图
        mBaiduMap.setMyLocationData(locData);
        mBaiduMap.setMyLocationEnabled(true);
        LatLng ll = new LatLng(latx,laty);
        //MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll,f);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, f - 2);//设置缩放比例
        mBaiduMap.animateMapStatus(u);
    }

    @Override
    public void initLayoutParams() {
        
    }
    

    @Override
    public void initData() {
        Intent intent = getIntent();
        lpdetail = intent.getParcelableExtra("lpdetail");
        latx= (float) lpdetail.getDt_0();
        laty= (float) lpdetail.getDt_1();
        String name = lpdetail.getSubject();

        actionBar.init(name,R.drawable.return_white,0);

        initMyLocation();

    }

    @Override
    public void bindListener() {
        actionBar.setLROnClickListener(null,null);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        bmapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        if (bmapView != null) {
            bmapView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        bmapView.onPause();
    }







}
