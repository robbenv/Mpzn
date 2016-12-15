
package com.mpzn.mpzn.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.activity.AddBBActivity;
import com.mpzn.mpzn.activity.DetailNewhouseActivity;
import com.mpzn.mpzn.activity.DetailNewsActivity;
import com.mpzn.mpzn.activity.LoginActivity;
import com.mpzn.mpzn.activity.SearchActivity;
import com.mpzn.mpzn.activity.WebViewActivity;
import com.mpzn.mpzn.adapter.HomeTopBannerViewHolder;
import com.mpzn.mpzn.adapter.MainHomeBuildingLvAdapter;
import com.mpzn.mpzn.adapter.MainHomeGvAdapter;
import com.mpzn.mpzn.adapter.MainHomeTopViewPagerAdapter;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseFragment;
import com.mpzn.mpzn.entity.BuildingFilterEntity;
import com.mpzn.mpzn.entity.BuildingList;
import com.mpzn.mpzn.entity.BuildingTJEntity;
import com.mpzn.mpzn.entity.HomeEntity;
import com.mpzn.mpzn.entity.HomeNewsEntity;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.utils.ACache;
import com.mpzn.mpzn.utils.CacheUtils;
import com.mpzn.mpzn.utils.ViewUtils;
import com.mpzn.mpzn.views.ADTextView;
import com.mpzn.mpzn.views.ActionBarForHome;
import com.mpzn.mpzn.views.FilterView.FilterView;
import com.mpzn.mpzn.views.FilterView.HeaderFilterViewView;
import com.mpzn.mpzn.views.FilterView.entity.BuildingEntity;
import com.mpzn.mpzn.views.FilterView.entity.FilterData;
import com.mpzn.mpzn.views.FilterView.entity.FilterEntity;
import com.mpzn.mpzn.views.FilterView.entity.FilterTwoEntity;
import com.mpzn.mpzn.views.FilterView.entity.ModelUtil;
import com.mpzn.mpzn.views.circleindicator.CircleIndicator;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.Call;

import static com.mpzn.mpzn.R.id.filterView_home;
import static com.mpzn.mpzn.utils.Constant.RESCODE_JINGJICOM;
import static com.mpzn.mpzn.utils.Constant.RESCODE_JINGJIREN;
import static com.mpzn.mpzn.utils.DateUtil.formatData;
import static com.mpzn.mpzn.utils.ViewUtils.dip2px;
import static com.mpzn.mpzn.utils.ViewUtils.getStatusBarHeight;
import static com.mpzn.mpzn.utils.ViewUtils.px2dip;
import static com.mpzn.mpzn.utils.ViewUtils.setListViewHeightBasedOnChildren;
import static com.mpzn.mpzn.views.FilterView.entity.ModelUtil.getCategory2rdData;
import static com.mpzn.mpzn.views.FilterView.entity.ModelUtil.getCategoryBuildingData;

/**
 * Created by Icy on 2016/7/13.
 */
public class HomeFragment extends BaseFragment {
    public static final int FV_HEIGHT = 92;
    public static final int STATUSBAR_HEIGHT = 24;
    public static String TAG = "tag_home_fragment";

    @Bind(R.id.lv_building)
    ListView lvBuilding;
    @Bind(R.id.action_bar_home)
    ActionBarForHome actionBarHome;
    @Bind(filterView_home)
    FilterView filterViewHome;
    @Bind(R.id.top_shadow)
    View topShadow;

    private View fragment_home;
    private GridView gv_home;
    private View piece_top_vp;
    private View piece_gv;
    private View piece_bbxx;
    private View piece_loupantuijian;
    private View piece_zx_lv;

    private MainHomeGvAdapter mainHomeGvAdapter;

    private List<View> items = new ArrayList<>();
    private ListView lv_zixuntoutiao;

    private ConvenientBanner banner_home_top;
    private ADTextView autoScrollView;
    private TextView text_search_hint;
    private LayoutInflater mInflater;

    private HomeEntity.DataBean homeEntityData;  //首页信息
    private List<HomeEntity.DataBean.BaobeilistBean> baobeilist=new ArrayList<>(); //首页信息中的报备信息
    private List<HomeEntity.DataBean.RecommendlistBean> recommendlist = new ArrayList<>(); //首页信息中的推荐楼盘
    private List<HomeEntity.DataBean.TopnewslistBean> topnewslist=new ArrayList<>(); //首页信息中的顶部滚动
    private List<HomeEntity.DataBean.NewsBean> homeNewslist=new ArrayList<>();
    private List<BuildingEntity> buildingData = new ArrayList<>();




    private String buildingFilterJson;


    private FilterData filterData;
    private PopupWindow switchSearchPopupWindow;
    private View switchSearchPopupWindowView;
    private TextView tv1_popupwindow;
    private TextView tv2_popupwindow;


    private ArrayAdapter<String> lv_zx_Adapter;
    private List<String> zxs =new ArrayList<>();



    private HeaderFilterViewView headerFilterViewView;
    private BuildingFilterEntity.DataEntity buildingfilterdata;
    private MainHomeBuildingLvAdapter mainHomeBuildingLvAdapter;


    private View itemHeaderAdView; // 从ListView获取的广告子View
    private FilterView itemHeaderFilterView; // 从ListView获取的筛选子View
    private boolean isScrollIdle = true; // ListView是否在滑动
    private boolean isStickyTop = false; // 是否吸附在顶部
    private boolean isSmooth = false; // 没有吸附的前提下，是否在滑动
    private int titleViewHeight = 0; // 标题栏的高度
    private int titleViewHeight_v19 = 50;
    private int filterPosition = -1; // 点击FilterView的位置：分类(0)、排序(1)、筛选(2)

    private int adViewHeight = 200; // 广告视图的高度
    private int adViewTopSpace; // 广告视图距离顶部的距离

    private int filterViewPosition = 5; // 筛选视图的位置
    private int filterViewTopSpace; // 筛选视图距离顶部的距离
    private int filterHeight;
    private boolean isClick = false;



    @Override
    public View getLayourView(LayoutInflater inflater, ViewGroup container) {
        mInflater = inflater;
        fragment_home = inflater.inflate(R.layout.fragment_home, null);
        return fragment_home;
    }

    @Override
    public void initHolder(View view) {
        text_search_hint = (TextView) actionBarHome.findViewById(R.id.text_search_hint);
        //顶部轮播
        piece_top_vp = mInflater.inflate(R.layout.home_piece_vp_top, null);
        banner_home_top = (ConvenientBanner)piece_top_vp.findViewById(R.id.banner_home_top);

        lvBuilding.addHeaderView(piece_top_vp);

        //圆形按钮组
        piece_gv = mInflater.inflate(R.layout.home_piece_gv, null);
        gv_home = (GridView) piece_gv.findViewById(R.id.gv_home);
        mainHomeGvAdapter = new MainHomeGvAdapter(mContext);
        gv_home.setAdapter(mainHomeGvAdapter);
        lvBuilding.addHeaderView(piece_gv);

        //报备信息
        piece_bbxx = mInflater.inflate(R.layout.home_piece_bbxx, null);
        autoScrollView = (ADTextView) piece_bbxx.findViewById(R.id.autoScrollTextView);

        lvBuilding.addHeaderView(piece_bbxx);

        //推荐楼盘
        piece_loupantuijian = mInflater.inflate(R.layout.home_piece_loupantuijian, null);
        View item1 = piece_loupantuijian.findViewById(R.id.item1);
        View item2 = piece_loupantuijian.findViewById(R.id.item2);
        View item3 = piece_loupantuijian.findViewById(R.id.item3);
        items.add(item3);
        items.add(item2);
        items.add(item1);
        lvBuilding.addHeaderView(piece_loupantuijian);

        //资讯头条
        piece_zx_lv = mInflater.inflate(R.layout.home_piece_zixuntoutiao, null);
        lv_zixuntoutiao = (ListView) piece_zx_lv.findViewById(R.id.lv_zixuntoutiao);
        lv_zx_Adapter = new ArrayAdapter<String>(mContext, R.layout.item_lv_zixuntoutiao, zxs);
        lv_zixuntoutiao.setAdapter(lv_zx_Adapter);
        lvBuilding.addHeaderView(piece_zx_lv);


        //假的筛选
        headerFilterViewView = new HeaderFilterViewView(getActivity());
        headerFilterViewView.fillView(new Object(), lvBuilding);

        //楼盘列表
        mainHomeBuildingLvAdapter = new MainHomeBuildingLvAdapter(mContext, buildingData);
        lvBuilding.setAdapter(mainHomeBuildingLvAdapter);
        fillAdapter(buildingData);


        if (switchSearchPopupWindow == null) {
            switchSearchPopupWindow = new PopupWindow(mContext);
            switchSearchPopupWindow.setHeight(dip2px(70));
            switchSearchPopupWindow.setWidth(dip2px(80));
            switchSearchPopupWindowView = mInflater.inflate(R.layout.switch_search_popup_window, null);
            tv1_popupwindow = (TextView) switchSearchPopupWindowView.findViewById(R.id.tv1_popupwindow);
            tv2_popupwindow = (TextView) switchSearchPopupWindowView.findViewById(R.id.tv2_popupwindow);
            ColorDrawable dw = new ColorDrawable(0x00ffffff);
            switchSearchPopupWindow.setBackgroundDrawable(dw);
            switchSearchPopupWindow.setContentView(switchSearchPopupWindowView);
            switchSearchPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        }

    }


    @Override
    public void initLayoutParams() {

        //后面加的那个值我也不知道咋来的，别问我。。。反正这个值是对的
        if(MyApplication.isInfect){
            titleViewHeight=px2dip(ViewUtils.getStatusBarHeight() + FV_HEIGHT) + 7;
            Logger.d("initLayoutParams()__isInfect __"+titleViewHeight);
        }else{

            titleViewHeight = 45;
            Logger.d("initLayoutParams()__noInfect __"+titleViewHeight);
        }
        filterViewHome.setVisibility(View.GONE);
    }

    //更新信息
    public void updata(HomeEntity.DataBean homeEntityData){
        if(homeEntityData==null){
            return;
        }

        baobeilist = homeEntityData.getBaobeilist();
        recommendlist = homeEntityData.getRecommendlist();
        topnewslist = homeEntityData.getTopnewslist();
        homeNewslist = homeEntityData.getNews();


        initTopBanner(topnewslist);
        initBaobeiXinxi(baobeilist);
        initPieceTuijian(recommendlist);
        initZixuntoutiao(homeNewslist);

    }



    @Override
    public void initData() {

        OkHttpUtils.get()
                .url(API.HOME_GET)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        homeEntityData= (HomeEntity.DataBean) CacheUtils.getObject(mContext, "HomeData");
                        updata(homeEntityData);
                        Logger.d(e.getMessage());
                        Toast.makeText(mContext, "首页信息获取失败...", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        HomeEntity homeEntity = new Gson().fromJson(response, HomeEntity.class);
                        if(homeEntity.getCode()==200){
                            homeEntityData = homeEntity.getData();
                            updata(homeEntityData);
                            Logger.d("200");
                            CacheUtils.putObject(mContext,"HomeData",homeEntityData);
                        }else{
                            Logger.d("不是200");
                            homeEntityData= (HomeEntity.DataBean) CacheUtils.getObject(mContext, "HomeData");
                            updata(homeEntityData);
                        }

                    }
                });

//        //请求楼盘筛选参数
//        OkHttpUtils
//                .get()
//                .url(API.BUILDINGINIT_GET)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        Toast.makeText(mContext, "获取数据失败...", Toast.LENGTH_SHORT).show();
//
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//
//                        buildingFilterJson = response;
//                        initBuildingFilter();
//
//                    }
//                });

        OkHttpUtils
                .get()
                .url(API.BUILDINGLIST_GET)
                .addParams("page","total")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Logger.d(e.getMessage());
                        Toast.makeText(mContext, "楼盘列表获取数据失败...", Toast.LENGTH_SHORT).show();
                        buildingData= (List<BuildingEntity>) CacheUtils.getObject(mContext,"BuildingData");
                        initBuildingList(buildingData);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG+"test1", "onResponse()__");

                        BuildingList buildingList = new Gson().fromJson(response, BuildingList.class);
                        if(buildingList.getCode()==200) {
                            buildingData = buildingList.getData();
                            Logger.d("onResponse()__data = " + buildingList);
                            initBuildingList(buildingData);
                            CacheUtils.putObject(mContext,"BuildingData",buildingData);
                        }else{
                            Logger.d("onResponse()__buildingList.getCode()");
                            buildingData= (List<BuildingEntity>) CacheUtils.getObject(mContext,"BuildingData");
                            initBuildingList(buildingData);
                        }
                    }
                });

        // 筛选数据
        filterData = new FilterData();
        filterData.setCategory(ModelUtil.getCategoryData());
        filterData.setSorts(ModelUtil.getSortData());
        filterData.setFilters(ModelUtil.getFilterData());

        // 设置筛选数据
        filterViewHome.setFilterData(getActivity(), filterData);


    }


    //初始化首页顶部栏
    private void initTopBanner(List<HomeEntity.DataBean.TopnewslistBean> topnewslist) {
        List<String> imgurls=new ArrayList<>();
        for(HomeEntity.DataBean.TopnewslistBean data:topnewslist){
            imgurls.add(data.getThumb());
        }
        banner_home_top.setPages(
                new CBViewHolderCreator<HomeTopBannerViewHolder>() {
                    @Override
                    public HomeTopBannerViewHolder createHolder() {
                        return new HomeTopBannerViewHolder();
                    }
                }, imgurls)
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

    }


    //初始化报备信息
    private void initBaobeiXinxi(List<HomeEntity.DataBean.BaobeilistBean> baobeilist) {
        List<String> baobeis = new ArrayList<>();
        for(int i=0;i<baobeilist.size();i++){
            HomeEntity.DataBean.BaobeilistBean baobeilistBean = baobeilist.get(i);
            String msg = baobeilistBean.getXingming()+"于"+ formatData("MM-dd HH:mm",baobeilistBean.getBaobeitime())+"对"+baobeilistBean.getSubject()+"报备成功!";
            baobeis.add(msg);
        }

        autoScrollView.setmTexts(baobeis);
    }
   //初始化 推荐楼盘
    private void initPieceTuijian(List<HomeEntity.DataBean.RecommendlistBean> recommendlist) {


            for (int i = 0; i < 3; i++) {
                TextView tv1 = (TextView) items.get(i).findViewById(R.id.tv1);
                TextView tv2 = (TextView) items.get(i).findViewById(R.id.tv2);
                TextView tv3 = (TextView) items.get(i).findViewById(R.id.tv3);
                ImageView img = (ImageView) items.get(i).findViewById(R.id.img);
                tv1.setText(recommendlist.get(i).getSubject());
                tv3.setText(recommendlist.get(i).getDj()+"元/㎡");
                if(i==2){
                    img.setMinimumHeight(dip2px(310));
                }else{
                    img.setMinimumHeight(dip2px(150));
                }
                mImageManager.loadUrlImage(recommendlist.get(i).getThumb(), img);

            }


    }
    //初始化 首页新闻
    private void initZixuntoutiao(List<HomeEntity.DataBean.NewsBean> homeNewslist) {

            for (int i = 0; i < homeNewslist.size(); i++) {
                zxs.add("[" + homeNewslist.get(i).getTitle() + "]" + homeNewslist.get(i).getSubject());
            }

            lv_zx_Adapter.notifyDataSetChanged();

            setListViewHeightBasedOnChildren(lv_zixuntoutiao);



    }


    private void initBuildingFilter() {
        BuildingFilterEntity buildingFilterEntity = new Gson().fromJson(buildingFilterJson, BuildingFilterEntity.class);
        if ("success".equals(buildingFilterEntity.getMessage())) {
            buildingfilterdata = buildingFilterEntity.getData();
            try {
                ACache.get(getActivity()).put("buildingfilterdata", buildingfilterdata);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }


    //初始化 楼盘数据
    private void initBuildingList(List<BuildingEntity> buildingListData) {
        if(buildingListData==null||buildingListData.size()==0){
            return;
        }
        ModelUtil.setBuildingData(buildingListData);
        fillAdapter(buildingListData);
    }

    /**
     * 获取控件的高度或者宽度  isHeight=true则为测量该控件的高度，isHeight=false则为测量该控件的宽度
     * @param view
     * @param isHeight
     * @return
     */
    public int getViewHeight(View view, boolean isHeight){
        int result;
        if(view==null)return 0;
        if(isHeight){
            int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
            view.measure(h,0);
            result =view.getMeasuredHeight();
        }else{
            int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
            view.measure(0,w);
            result =view.getMeasuredWidth();
        }
        return result;
    }


    @Override
    public void bindListener() {

//        ViewTreeObserver observer = filterViewHome.getViewTreeObserver();
//        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                filterViewHome.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                filterHeight = filterViewHome.getMeasuredHeight();
//
//                if(MyApplication.isInfect){
//                    titleViewHeight=px2dip(ViewUtils.getStatusBarHeight() + filterHeight);
//                    Logger.d("initLayoutParams()__isInfect __"+titleViewHeight);
//                }else{
//
//                    titleViewHeight = 40 + px2dip(ViewUtils.getStatusBarHeight());
//                    Logger.d("initLayoutParams()__noInfect __"+titleViewHeight);
//                }
//            }
//
//        });

        banner_home_top.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent();
                intent.putExtra("Aid", topnewslist.get(position).getAid());
                intent.setClass(getActivity(), DetailNewhouseActivity.class);
                getActivity().startActivity(intent);

            }
        });

        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = mainHomeGvAdapter.getItem(position);
                if("住宅".equals(item)){
                    isClick = true;
                    Log.i(TAG, "onItemClick()__住宅");
                    filterData.getCategory().get(1).setSelected(true);
                    filterData.getCategory().get(1).setSelectedFilterEntity(new FilterEntity("不限","1"));
                    fillAdapter(getCategoryBuildingData(filterData.getCategory().get(1)));
                    smoothScrollToPositionFromTop(lvBuilding, filterViewPosition + 1, 0);

                }else if("商铺".equals(item)){
                    isClick = true;
                    Log.i(TAG, "onItemClick()__商铺");
                    filterData.getCategory().get(4).setSelected(true);
                    filterData.getCategory().get(4).setSelectedFilterEntity(new FilterEntity("不限","1"));
                    fillAdapter(getCategoryBuildingData(filterData.getCategory().get(4)));
                    smoothScrollToPositionFromTop(lvBuilding, filterViewPosition + 1, 0);

                }else if("写字楼".equals(item)){
                    isClick = true;
                    Log.i(TAG, "onItemClick()__写字楼");
                    filterData.getCategory().get(5).setSelected(true);
                    filterData.getCategory().get(5).setSelectedFilterEntity(new FilterEntity("不限","1"));
                    fillAdapter(getCategoryBuildingData(filterData.getCategory().get(5)));
                    smoothScrollToPositionFromTop(lvBuilding, filterViewPosition + 1, 0);

                }else if("地图找房".equals(item)){
                    Intent intent = new Intent();
                    intent.setClass(mContext, WebViewActivity.class);
                    intent.putExtra("title",item);
                    intent.putExtra("url",API.MAP_SEARCH);
                    getActivity().startActivity(intent);

                }else if("新房团购".equals(item)){
                    Toast.makeText(mContext, "暂未开放，敬请期待", Toast.LENGTH_SHORT).show();

                }else if("楼盘报备".equals(item)){
                    if(MyApplication.getInstance().isLogined) {
                        int userType = MyApplication.getInstance().mUserMsg.getmChild();
                        if(userType==RESCODE_JINGJIREN||userType==RESCODE_JINGJICOM) {
                            Intent intent = new Intent();
                            intent.setClass(mContext, AddBBActivity.class);
                            getActivity().startActivity(intent);
                        }else{
                            Toast.makeText(mContext, "开发商用户没有权限报备", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setClass(mContext, LoginActivity.class);
                        getActivity().startActivity(intent);
                    }

                }else if("房价走势".equals(item)){
                    Intent intent = new Intent();
                    intent.setClass(mContext, WebViewActivity.class);
                    intent.putExtra("title",item);
                    intent.putExtra("url",API.BUILDING_PRICE);
                    getActivity().startActivity(intent);

                }else if("购房工具".equals(item)){
//                    Toast.makeText(mContext, "暂未开放，敬请期待", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    intent.setClass(mContext, WebViewActivity.class);
                    intent.putExtra("title",item);
                    intent.putExtra("url",API.GFTOOL);
                    getActivity().startActivity(intent);

                }
            }
        });

        lv_zixuntoutiao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailNewsActivity.class);
                intent.putExtra("NewsAid", homeNewslist.get(position).getAid());
                intent.putExtra("NewsTitle", homeNewslist.get(position).getTitle());
                getActivity().startActivity(intent);
            }
        });



        switchSearchPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
                params.alpha = 1f;
                getActivity().getWindow().setAttributes(params);
            }
        });

//        tv1_popupwindow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                actionBarHome.leftTitle.setText(tv1_popupwindow.getText().toString());
//                switchSearchPopupWindow.dismiss();
//            }
//        });
//        tv2_popupwindow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                actionBarHome.leftTitle.setText(tv2_popupwindow.getText().toString());
//                switchSearchPopupWindow.dismiss();
//            }
//        });

        View.OnClickListener toSearchListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SearchActivity.class);
                intent.putExtra("type", actionBarHome.leftTitle.getText());
                startActivity(intent);
            }
        };
        actionBarHome.leftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "暂只开放上海及周边地区", Toast.LENGTH_SHORT).show();
            }
        });
        actionBarHome.btn_right.setOnClickListener(toSearchListener);
        text_search_hint.setOnClickListener(toSearchListener);
        actionBarHome.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getAlpha()!=0){
                    lvBuilding.smoothScrollToPosition(0);
                }else{
                    Intent intent = new Intent(mContext, SearchActivity.class);
                    intent.putExtra("type", actionBarHome.leftTitle.getText());
                    startActivity(intent);
                }
            }
        });

//        actionBarHome.leftTitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (actionBarHome.getIsAtTop()) {
//                    tv1_popupwindow.setTextColor(getResources().getColor(R.color.hint));
//                    tv2_popupwindow.setTextColor(getResources().getColor(R.color.hint));
//                    switchSearchPopupWindowView.setBackgroundResource(R.drawable.conner5dp_stroke9e);
//                } else {
//                    tv1_popupwindow.setTextColor(Color.WHITE);
//                    tv2_popupwindow.setTextColor(Color.WHITE);
//
//                }
//                if (!switchSearchPopupWindow.isShowing()) {
//                    WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
//                    params.alpha = 0.5f;
//                    getActivity().getWindow().setAttributes(params);
//
//                    switchSearchPopupWindow.setFocusable(true);
//                    switchSearchPopupWindow.showAsDropDown(v, dip2px(-10), 0);
//                } else {
//                    switchSearchPopupWindow.dismiss();
//
//                }
//
//            }
//        });


        for (int i = 0; i < items.size(); i++) {
            final int finalI = i;
            items.get(finalI).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeEntity.DataBean.RecommendlistBean recommendlistBean = recommendlist.get(finalI);
                    Intent intent = new Intent();
                    intent.putExtra("Aid", recommendlistBean.getAid());
                    intent.setClass(getActivity(), DetailNewhouseActivity.class);
                    getActivity().startActivity(intent);

                }
            });
        }

        lvBuilding.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position - filterViewPosition -1<0){
                    return;
                }
                BuildingEntity dataEntity = mainHomeBuildingLvAdapter.getData().get(position - filterViewPosition -1);
                if(dataEntity.isNoData()){
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("Aid", dataEntity.getAid());
                intent.putExtra("Name",dataEntity.getSubject());
                intent.setClass(getActivity(), DetailNewhouseActivity.class);
                getActivity().startActivity(intent);
            }
        });

        // 分类Item点击
        filterViewHome.setOnItemCategoryClickListener(new FilterView.OnItemCategoryClickListener() {
            @Override
            public void onItemCategoryClick(FilterTwoEntity entity) {
                fillAdapter(getCategoryBuildingData(entity));
            }
        });

        // 排序Item点击
        filterViewHome.setOnItemSortClickListener(new FilterView.OnItemSortClickListener() {
            @Override
            public void onItemSortClick(FilterEntity entity) {
                fillAdapter(ModelUtil.getSortBuildingData(entity));
            }
        });

        // 筛选Item点击
        filterViewHome.setOnItemFilterClickListener(new FilterView.OnItemFilterClickListener() {
            @Override
            public void onItemFilterClick(FilterEntity entity) {
                fillAdapter(ModelUtil.getFilterBuildingData(entity));
            }
        });



        // (真正的)筛选视图点击
        filterViewHome.setOnFilterClickListener(new FilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                if (filterViewHome.getVisibility() == View.VISIBLE) {
                    filterPosition = position;
                    filterViewHome.showFilterLayout(position);
                }
            }
        });


        // (假的ListView头部展示的)筛选视图点击
        headerFilterViewView.setOnFilterClickListener(new HeaderFilterViewView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                filterPosition = position;
                isSmooth = true;
                smoothScrollToPositionFromTop(lvBuilding, filterViewPosition + 1 , 0);

            }
        });

        lvBuilding.setOnScrollListener(onScrollListener);


    }



    AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(final AbsListView view, int scrollState) {
            isScrollIdle = (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE);

            if (isScrollIdle && isClick) {
            Log.i("Proxy_test34", "onScrollStateChanged()__titleViewHeight = "+titleViewHeight + "   filterViewTopSpace = "+filterViewTopSpace);

                Log.i(TAG+"test", "onScrollStateChanged()__scrollState == SCROLL_STATE_IDLE && isClick");
//                view.setOnScrollListener(null);

                // Fix for scrolling bug
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        //不要问我为什么偏移量是这个值，我也不知道，反正这个值就对了
//                          view.smoothScrollToPositionFromTop(filterViewPosition + 1, dip2px(titleViewHeight) + FV_HEIGHT + 40);
                        view.smoothScrollToPositionFromTop(filterViewPosition, dip2px(titleViewHeight));
                        isClick = false;
                    }
                });
            }

        }



        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (isScrollIdle && adViewTopSpace < 0) {
                Log.i(TAG+"test", "onScroll()__return");
                return;
            }

            // 获取广告头部View、自身的高度、距离顶部的高度
            if (itemHeaderAdView == null) {
                itemHeaderAdView = lvBuilding.getChildAt(1 - firstVisibleItem);
            }
            if (itemHeaderAdView != null) {
                adViewTopSpace = px2dip(itemHeaderAdView.getTop());
                adViewHeight = px2dip(itemHeaderAdView.getHeight());
            }

            // 获取筛选View
            if (itemHeaderFilterView == null) {
                itemHeaderFilterView = (FilterView) lvBuilding.getChildAt(filterViewPosition - firstVisibleItem);
            }
            //获取筛选view距离顶部的高度
            if (itemHeaderFilterView != null) {
                filterViewTopSpace = px2dip(itemHeaderFilterView.getTop());
            }

            // 处理筛选是否吸附在顶部
            Log.d("larry", "onScroll()__titleViewHeight = " + titleViewHeight + "     filterViewTopSpace = " + filterViewTopSpace);
            if (filterViewTopSpace > titleViewHeight) {
                isStickyTop = false; // 没有吸附在顶部
                filterViewHome.setVisibility(View.GONE);
            } else if(itemHeaderFilterView != null){
                isStickyTop = true; // 吸附在顶部
                filterViewHome.setVisibility(View.VISIBLE);
            }
            //已经到顶部之后，再往下滑动，仍需要显示
            if (firstVisibleItem > filterViewPosition) {
                isStickyTop = true;
                filterViewHome.setVisibility(View.VISIBLE);

            }

            if (isSmooth && isStickyTop) {
                isSmooth = false;
                filterViewHome.showFilterLayout(filterPosition);
            }

            filterViewHome.setStickyTop(isStickyTop);

            // 处理标题栏颜色渐变
            //adViewTopSpace实际上是轮拨图底部的距离
            actionBarHome.setChangeWithScroll(200 - adViewTopSpace, topShadow);


        }


    };


    public void smoothScrollToPositionFromTop(final AbsListView view, final int position, final int offset) {
        View child = getChildAtPosition(view, position);
        // There's no need to scroll if child is already at top or view is already scrolled to its end
        if ((child != null) && ((child.getTop() == 0) || ((child.getTop() > 0) && !view.canScrollVertically(1)))) {
            return;
        }

        view.setOnScrollListener(onScrollListener);


        // Perform scrolling to position
        //dip2px(titleViewHeight) + FV_HEIGHT
        new Handler().post(new Runnable() {
            @Override
            public void run() {
//                view.smoothScrollToPositionFromTop(filterViewPosition + 1, dip2px(titleViewHeight) + FV_HEIGHT + 40);
                view.smoothScrollToPositionFromTop(filterViewPosition, dip2px(titleViewHeight));
            }
        });
    }

    public static View getChildAtPosition(final AdapterView view, final int position) {
        final int index = position - view.getFirstVisiblePosition();
        if ((index >= 0) && (index < view.getChildCount())) {
            return view.getChildAt(index);
        } else {
            return null;
        }
    }


    // 填充数据
    private void fillAdapter(List<BuildingEntity> list) {

        if (list == null || list.size() == 0) {
            Log.i("Proxy_test344", "fillAdapter()__list为null");
            int height = MyApplication.mScreenHeight-dip2px(titleViewHeight + 50 + 45); // 95 = 标题栏高度 ＋ FilterView+底部bar的高度
            mainHomeBuildingLvAdapter.setData(ModelUtil.getNoDataEntity(height));
        } else {
            Log.i("Proxy_test344", "fillAdapter()__list.size = "+list.size());
            mainHomeBuildingLvAdapter.setData(list);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        banner_home_top.stopTurning();

    }

    @Override
    public void onResume() {
        super.onResume();
        banner_home_top.startTurning(2000);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }



}
