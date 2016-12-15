package com.mpzn.mpzn.activity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.adapter.TrackListAdapter;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.ProxySellListEntity;
import com.mpzn.mpzn.entity.UserTrackEntity;
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

public class UserTrackActivity extends BaseActivity {


    @Bind(R.id.aciton_bar)
    MyActionBar acitonBar;
    @Bind(R.id.lv_my_bb)
    ListView lvMyBb;
    @Bind(R.id.et_search)
    EditText etSearch;
    private TrackListAdapter adapter;
    private List<UserTrackEntity.DataBean> datalist = new ArrayList<UserTrackEntity.DataBean>();
    private String keyWords = "";


    @Override
    public int getLayoutId() {
        return R.layout.activity_user_track;
    }

    @Override
    public void initHolder() {
        initSystemBar(this, R.color.fafafa);
        acitonBar.init("客户信息", R.drawable.return_gray, 0);

        adapter = new TrackListAdapter(mContext, datalist);
        lvMyBb.setAdapter(adapter);
    }

    @Override
    public void initLayoutParams() {

    }

    @Override
    public void initData() {
        getData();
    }
    //user_genzong

    @Override
    public void bindListener() {
        acitonBar.setLROnClickListener(null, null);

        lvMyBb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent();
                intent.setClass(mContext, WebViewActivity.class);
                intent.putExtra("title","用户信息追踪");
                intent.putExtra("token", MyApplication.getInstance().token);
                Log.i("track", "onItemClick()__cid = "+datalist.get(position).getCid());
                intent.putExtra("cid", datalist.get(position).getCid()+"");
                int id = datalist.get(position).getCid();
                String url = API.TRACK_SETUP;
                intent.putExtra("url",url);
                mContext.startActivity(intent);
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                datalist.clear();
                keyWords = charSequence.toString();
                getData();
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

    private void getData() {
        OkHttpUtils.get()
                .url(API.TRACK_USER_LIST)
                .addParams("token", MyApplication.getInstance().token)
                .addParams("keywords", keyWords)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(UserTrackActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i("Proxy_test34", "onResponse()__");
                        UserTrackEntity userTrackEntity = new Gson().fromJson(response, UserTrackEntity.class);
                        Log.e("TAG", userTrackEntity.getData().toString() + "");
                        if (userTrackEntity.getCode() == 200) {
                            datalist.addAll(userTrackEntity.getData());
                            Log.i("Proxy_test1", "onResponse()__uncheckList.size ="+datalist.size() );

                            adapter.updata(datalist);
//                            rvAdapter.changeEditable(true, false);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(UserTrackActivity.this, userTrackEntity.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}
