package com.mpzn.mpzn.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.adapter.MyBBlvadapter;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.MyBBEntity;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.views.MyActionBar;
import com.mpzn.mpzn.views.SmoothListView.SmoothListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by larry on 16-12-5.
 */
public class BBSearchActivity extends BaseActivity {


    private static final int STOP_REFRESH = 0;
    @Bind(R.id.aciton_bar)
    MyActionBar acitonBar;
    @Bind(R.id.lv_my_bb)
    SmoothListView lvMyBb;
    @Bind(R.id.et_search)
    EditText etSearch;

    private MyBBlvadapter lvMyBbAdapter;
    private List<MyBBEntity.DataBean> datalist = new ArrayList<>();
    private int newsOffsetAll;
    private String key;

    private android.os.Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case  STOP_REFRESH:
                    lvMyBb.stopRefresh();
                    break;
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_bbsearch;
    }

    @Override
    public void initHolder() {
        initSystemBar(this, R.color.fafafa);
        acitonBar.init("我的报备", R.drawable.return_gray, 0);

        lvMyBb.setRefreshEnable(false);
        lvMyBb.setLoadMoreEnable(true);
        lvMyBbAdapter = new MyBBlvadapter(mContext, datalist);
        lvMyBb.setAdapter(lvMyBbAdapter);
    }

    @Override
    public void initLayoutParams() {

    }

    @Override
    public void initData() {
//        getdata("", 0, "");

    }

    @Override
    public void bindListener() {

        lvMyBb.setSmoothListViewListener(new SmoothListView.ISmoothListViewListener() {

            @Override
            public void onRefresh() {
                newsOffsetAll = 0;

//                getdata("", 0);

            }

            @Override
            public void onLoadMore() {
                Log.i("fenye", "onLoadMore()__");
                getdata("", newsOffsetAll + 1, key);
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence keywords, int i, int i1, int i2) {
                Log.i("search1", "onTextChanged()__");
                key = keywords.toString();


                datalist.clear();
                getdata("", 0, keywords.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public void getdata(final String ischecked, final int offset, String keywords) {
        OkHttpUtils.get()
                .url(API.MYBB_LIST)
                .addParams("token", MyApplication.getInstance().token)
                .addParams("checked", ischecked)
                .addParams("offset", String.valueOf(offset))
                .addParams("keywords", keywords)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(mContext, "获取数据失败...", Toast.LENGTH_SHORT).show();
                        lvMyBb.stopLoadMore();
                        lvMyBb.stopRefresh();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        int offSetTemp;
                        MyBBEntity myBBEntity = new Gson().fromJson(response, MyBBEntity.class);
                        if (myBBEntity.getCode() == 200) {

                            if (ischecked == "") {
                                //解析数据
                                if (offset > newsOffsetAll) {
                                    //加载更多
                                    //具体的一页多少条，都是有服务器端决定的，这里只负责加载更多即可


                                    lvMyBb.stopLoadMore();
                                    if (myBBEntity.getData().size() != 0) {
                                        newsOffsetAll += 1;

                                    } else {
                                        Toast.makeText(mContext, "已加载全部内容", Toast.LENGTH_SHORT).show();
                                    }
                                } else {

                                    //刷新
                                    datalist.clear();
                                    handler.sendEmptyMessageDelayed(STOP_REFRESH,2800);
                                }
                                Log.i("search1", "onResponse()__myBBEntity.getData()="+myBBEntity.getData().toString());
                                datalist.addAll(myBBEntity.getData());
                                lvMyBbAdapter.updata(datalist);
                            }

                            Log.e("TAG", "报备数量" + ischecked + lvMyBbAdapter.getData().size());
                        }

                    }
                });




    }
}
