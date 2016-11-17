package com.mpzn.mpzn.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.google.gson.Gson;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.activity.DetailNewsActivity;
import com.mpzn.mpzn.activity.MainActivity;
import com.mpzn.mpzn.adapter.HomeTopBannerViewHolder;
import com.mpzn.mpzn.adapter.MainNewsListViewAdapter;
import com.mpzn.mpzn.adapter.MainNewsTopViewPagerAdapter;
import com.mpzn.mpzn.adapter.NewsTopBanerViewHolder;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseFragment;
import com.mpzn.mpzn.entity.Home;
import com.mpzn.mpzn.entity.NewsList;
import com.mpzn.mpzn.entity.NewsTopList;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.utils.ACache;
import com.mpzn.mpzn.utils.ViewUtils;
import com.mpzn.mpzn.views.MyActionBar;
import com.mpzn.mpzn.views.SmoothListView.SmoothListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Icy on 2016/7/13.
 */
public class NewsFragment extends BaseFragment{
    public static String TAG="tag_news_fragment";
    public static String NEWSLISTTOP="json_news_list_top";
    public static String NEWSLIST="json_news_list";
    public static final int STOP_REFRESH=111;
    private View state_bar;
    private View fragment_news;
    private View news_lv_head;
    private ConvenientBanner banner_home_top;
    private SmoothListView lv_news;
    private String newsListTopJson;
    private String newsListJson;
    private int newsOffset=0;    //获取到的页数 一页10个
    private List<NewsList.DataBean.NewsBean> data=new ArrayList<>();
    private MainNewsListViewAdapter mainNewsListViewAdapter;
    private MyActionBar action_bar;
    private List<NewsTopList.DataBean> newsTopListData;

    private android.os.Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
           switch (msg.what) {
               case  STOP_REFRESH:
                   lv_news.stopRefresh();
                   break;
           }
        }
    };

    @Override
    public View getLayourView(LayoutInflater inflater, ViewGroup container) {
        fragment_news = inflater.inflate(R.layout.fragment_news, null);
        return fragment_news;
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
    public void initHolder(View view) {
        state_bar = fragment_news.findViewById(R.id.state_bar);
        action_bar = (MyActionBar)fragment_news.findViewById(R.id.action_bar);
        action_bar.init("资讯",0,R.drawable.search_white);

        lv_news = (SmoothListView)fragment_news.findViewById(R.id.lv_news);
        lv_news.setRefreshEnable(true);
        lv_news.setLoadMoreEnable(true);

        news_lv_head=View.inflate(mContext, R.layout.news_lv_head, null);
        banner_home_top = (ConvenientBanner)news_lv_head.findViewById(R.id.banner_news_top);

        lv_news.addHeaderView(news_lv_head);

        mainNewsListViewAdapter = new MainNewsListViewAdapter(mContext, data);
        lv_news.setAdapter(mainNewsListViewAdapter);



    }

    @Override
    public void initLayoutParams() {
        if(MyApplication.getInstance().isInfect) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ViewUtils.getStatusBarHeight());
            state_bar.setLayoutParams(layoutParams);
        }else{
            state_bar.setVisibility(View.GONE);
        }

    }



    public void getNewsListJson(final int offset){


        if(offset==0) {
            OkHttpUtils
                    .get()
                    .url(API.NEWSTOP_GET)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Toast.makeText(mContext, "获取新闻顶部轮播数据失败...", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            newsListTopJson=response;
                            updataTopView();
                            try {
                                ACache.get(mContext).put(NEWSLISTTOP, new JSONObject(response));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
        }



        OkHttpUtils
                .get()
                .url(API.NEWSLIST_GET)
                .addParams("offset", offset+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(mContext, "获取数据失败...", Toast.LENGTH_SHORT).show();
                        lv_news.stopLoadMore();
                        lv_news.stopRefresh();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        newsListJson = response;
                        updataView(offset > newsOffset);
                        Log.e("TAG", "getNewsListJson"+response);

                        try {
                            ACache.get(mContext).put(NEWSLIST, new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        

                    }


                });


    }

    private void updataTopView(){
        NewsTopList newsTopList = new Gson().fromJson(newsListTopJson, NewsTopList.class);
        if ("success".equals(newsTopList.getMessage())) {
            newsTopListData = newsTopList.getData();

            banner_home_top.setPages(
                    new CBViewHolderCreator<NewsTopBanerViewHolder>() {
                        @Override
                        public NewsTopBanerViewHolder createHolder() {
                            return new NewsTopBanerViewHolder();
                        }
                    }, newsTopListData)
                    .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);

        }
    }



    private void updataView(boolean isAdd) {

        NewsList newsList = new Gson().fromJson(newsListJson, NewsList.class);

        if("success".equals(newsList.getMessage())) {
            if(!isAdd) {
                data = newsList.getData().getNews();
                mainNewsListViewAdapter.setData(data);
                handler.sendEmptyMessageDelayed(STOP_REFRESH,3000);


            }else{
                for(int i=0;i<newsList.getData().getNews().size();i++) {
                    data.add(newsList.getData().getNews().get(i));
                }
                mainNewsListViewAdapter.setData(data);
                newsOffset+=1;
                lv_news.stopLoadMore();


            }



        }else{
            Toast.makeText(mContext, "获取数据失败...", Toast.LENGTH_SHORT).show();

        }




    }


    @Override
    public void initData() {

        JSONObject json_top = ACache.get(mContext).getAsJSONObject(NEWSLISTTOP);
        newsListTopJson=String.valueOf(json_top);



        if(json_top!=null){
            updataTopView();
        }


        JSONObject json = ACache.get(mContext).getAsJSONObject(NEWSLIST);
        newsListJson=String.valueOf(json);

        if(json!=null){
            updataView(false);
        }

        getNewsListJson(0);


    }

    @Override
    public void bindListener() {


        lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position<2){
                   return;
                }
                int mPosition = position - 2;//ListView有头部  列表从2开始
                Intent intent = new Intent(getActivity(), DetailNewsActivity.class);
                int Aid = data.get(mPosition).getAid();
                String NewsTitle=data.get(mPosition).getSubject();
                String NewsAbstract=data.get(mPosition).getAbstractX();
                intent.putExtra("NewsAid", Aid);
                intent.putExtra("NewsTitle",NewsTitle);
                intent.putExtra("NewsAbstract",NewsAbstract);
                intent.putExtra("ImgUrl", API.API + data.get(mPosition).getThumb());

                getActivity().startActivity(intent);
            }
        });

        lv_news.setSmoothListViewListener(new SmoothListView.ISmoothListViewListener() {
            @Override
            public void onRefresh() {
                newsOffset=0;
                getNewsListJson(0);
            }

            @Override
            public void onLoadMore() {
                getNewsListJson(newsOffset+1);
            }
        });





    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("TAG", "requestCode+news" + requestCode);
    }


}
