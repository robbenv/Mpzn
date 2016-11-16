package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.bumptech.glide.Glide;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.activity.DetailNewhouseActivity;
import com.mpzn.mpzn.activity.MapActivity;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.entity.BuildingDetailEntity;
import com.mpzn.mpzn.http.API;

import java.util.ArrayList;
import java.util.List;

import static com.mpzn.mpzn.utils.ViewUtils.dip2px;

/**
 * Created by Icy on 2016/8/15.
 */
public class VpHouseDatailContentAdapter extends PagerAdapter {
    private  BuildingDetailEntity.DataBean lpdetail;

    private Context mContext;
    private String[] titles={"特色","详情","周边配套"};//,"周边配套"
    private List<View> views=new ArrayList<>();
    private List<String> imgUrls=new ArrayList<>();
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private float latx ;
    private float laty ;



    public VpHouseDatailContentAdapter(Context mContext, BuildingDetailEntity.DataBean lpdetail) {
        this.mContext=mContext;
        this.lpdetail=lpdetail;
        for(int i=0;i<titles.length;i++) {
            initPage(i);
        }

    }



    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=views.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public View getPage(int position){
        return views.get(position);

    }

    private View initPage(int position){
        View view=null;
        switch (position) {
            case  0:
//                for(int i=01;i<10;i++){
//                    String imgUrl="http://pan.guyun18.com/img/houseDetail/xiangqing_0"+i+".jpg";
//                    imgUrls.add(imgUrl);
//                }
//                for(int i=10;i<15;i++){
//                    String imgUrl="http://pan.guyun18.com/img/houseDetail/xiangqing_"+i+".jpg";
//                    imgUrls.add(imgUrl);
//                }
//                view=new ListView(mContext);
//                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//                ((ListView)view).setDivider(null);
//                MyBaseAdapter myBaseAdapter = new MyBaseAdapter(imgUrls);
//                ((ListView)view).setAdapter(myBaseAdapter);

                view = new LinearLayout(mContext);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ((LinearLayout)view).setPadding(dip2px(10),dip2px(10),dip2px(10),dip2px(10));
                TextView textview  = new TextView(mContext);
                textview.setTextSize(16);
                textview.setLineSpacing(0,1.2f);
                textview.setText("\u3000\u3000"+lpdetail.getContent());
                ((LinearLayout)view).addView(textview);
                break;
            case  1:
                List<String>  houseAttr=new ArrayList<>();
                houseAttr.add("开盘时间："+lpdetail.getKprq()+"已开盘");
                houseAttr.add("交房时间：预计"+lpdetail.getJfrq()+"交房");
                houseAttr.add("物业类型："+lpdetail.getTag().get(3));
                houseAttr.add("物 业 费："+lpdetail.getWyf());
                houseAttr.add("物业公司："+lpdetail.getWygs());
                houseAttr.add("装修状况："+lpdetail.getTag().get(0));
                houseAttr.add("绿 化 率："+lpdetail.getLhl());
                houseAttr.add("售楼地址："+lpdetail.getSldz());

                view=new ListView(mContext);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                view.setPadding(dip2px( 20), 0, 0, 0);
                view.setEnabled(false);
                ((ListView)view).setAdapter(new ArrayAdapter<String>(mContext, R.layout.item_lv_zixuntoutiao, houseAttr));
                ((ListView)view).setDivider(null);
                break;
            case  2:
                view=new ImageView(mContext);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                latx= (float) lpdetail.getDt_0();
                laty= (float) lpdetail.getDt_1();
                String mapimg="http://api.map.baidu.com/staticimage?zoom=16&markerStyles=l"+",,0xee4433"+"&scale=2&center="+laty+","+latx+"&markers="+laty+","+latx;
                ((DetailNewhouseActivity)mContext).mImageManager.loadUrlImage(mapimg,(ImageView) view);

                break;
        }
        views.add(view);
        return view;
    }



    class MyBaseAdapter extends BaseAdapter{

        private  List<String> imgUrls;

        public MyBaseAdapter(List<String> imgUrls) {
            this.imgUrls=imgUrls;
        }

        @Override
        public int getCount() {
            return imgUrls.size();
        }

        @Override
        public Object getItem(int position) {
            return imgUrls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            if(convertView==null){
                convertView=new ImageView(mContext);
            }
            Glide.with(mContext).load(imgUrls.get(position))
                    .override(MyApplication.mScreenWidth, dip2px(300))
                    .into((ImageView)convertView);
            return convertView;
        }

    }


}
