package com.mpzn.mpzn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.adapter.SearchHotRvAdapter;
import com.mpzn.mpzn.adapter.SearchLvTipsAdapter;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.SearchHotEntity;
import com.mpzn.mpzn.entity.SearchTipsList;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.listener.OnRecyclerItemClickListener;
import com.mpzn.mpzn.utils.CacheUtils;
import com.mpzn.mpzn.utils.Constant;
import com.mpzn.mpzn.views.MyActionBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class SearchActivity extends BaseActivity {

    private String typeForSearch;

    private ImageButton btn_left;
    private RelativeLayout btn_clear_search_history;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private static boolean isHaveHistory;
    private List<String> searchHistory;

    private EditText et_search;
    private Button btn_search;

    private ListView lv_search_tips;
    private List<SearchTipsList.DataEntity> tipsList=new ArrayList<>();
    private SearchLvTipsAdapter searchLvTipsAdapter;

    private RecyclerView rv_search_hot;
    private List<SearchHotEntity.DataBean> searchHotEntityData=new ArrayList<>();
    private SearchHotRvAdapter rvAdapter;

    private TextView history_search;
    private ListView lv_search_history;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initHolder() {
        initSystemBar(this,R.color.searchbar_bg);

        btn_left = (ImageButton)findViewById(R.id.btn_left);
        rv_search_hot =(RecyclerView) findViewById(R.id.rv_search_hot);
        btn_clear_search_history = (RelativeLayout)findViewById(R.id.btn_clear_search_history);
        et_search = (EditText)findViewById(R.id.et_search);
        lv_search_tips = (ListView)findViewById(R.id.lv_search_tips);
        searchLvTipsAdapter = new SearchLvTipsAdapter(mContext, tipsList);
        lv_search_tips.setAdapter(searchLvTipsAdapter);
        btn_search = (Button) findViewById(R.id.btn_search);
        lv_search_history = (ListView)findViewById(R.id.lv_search_history);
        history_search = (TextView)findViewById(R.id.history_search);

        rv_search_hot.setItemAnimator(new DefaultItemAnimator());
        rvAdapter=new SearchHotRvAdapter(mContext,searchHotEntityData);
        rv_search_hot.setAdapter(rvAdapter);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL);
        rv_search_hot.setLayoutManager(staggeredGridLayoutManager);


    }

    @Override
    public void initLayoutParams() {

    }

    @Override
    public void initData() {
        Intent intent=getIntent();
        typeForSearch=getIntent().getStringExtra("type");

        searchHistory = (List<String>) CacheUtils.getObject(mContext, "SearchHistory");



        if(searchHistory==null){
            isHaveHistory=false;
        }else{
            isHaveHistory=true;
        }

        if (isHaveHistory) {
            staggeredGridLayoutManager.setSpanCount(1);
            lv_search_history.setAdapter(new ArrayAdapter<String>(mContext, R.layout.item_lv_searchhis, searchHistory));
            lv_search_history.addFooterView(new ViewStub(this));

        } else {
            staggeredGridLayoutManager.setSpanCount(3);
            btn_clear_search_history.setVisibility(View.GONE);
            history_search.setVisibility(View.GONE);
        }


        OkHttpUtils.get()
                .url(API.SEARCH_HOT)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        SearchHotEntity searchHotEntity = new Gson().fromJson(response, SearchHotEntity.class);
                        if("success".equals(searchHotEntity.getMessage())) {
                           searchHotEntityData = searchHotEntity.getData();
                             rvAdapter.updata(searchHotEntityData);

                        }
                    }
                });



    }

    @Override
    public void bindListener() {
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_clear_search_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHaveHistory) {
                    searchHistory.clear();
                    lv_search_history.setVisibility(View.GONE);
                    btn_clear_search_history.setVisibility(View.GONE);
                    history_search.setVisibility(View.GONE);
                    CacheUtils.putObject(mContext, "SearchHistory",null);
                    staggeredGridLayoutManager.setSpanCount(3);
                    isHaveHistory = false;
                }

            }
        });
        et_search.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                et_search.setInputType(InputType.TYPE_CLASS_TEXT); //关闭软键盘
                return false;
            }
        });
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //软键盘上的搜索
                return true;
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()!=0){
                    lv_search_tips.setVisibility(View.VISIBLE);
                }else{
                    lv_search_tips.setVisibility(View.GONE);
                }
                OkHttpUtils.get()
                        .url(API.SEARCH_TIPS)
                        .addParams("subject",et_search.getText().toString().trim())
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(SearchActivity.this, "服务器请求错误", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                SearchTipsList searchTipsList = new Gson().fromJson(response, SearchTipsList.class);
                                if("success".equals(searchTipsList.getMessage())) {
                                    List<SearchTipsList.DataEntity> data = searchTipsList.getData();
                                    searchLvTipsAdapter.setData(data);

                                }else{
                                    SearchTipsList.DataEntity dataEntity = new SearchTipsList.DataEntity();
                                    dataEntity.setNoData(true);
                                    ArrayList<SearchTipsList.DataEntity> data = new ArrayList<>();
                                    data.add(dataEntity);
                                    searchLvTipsAdapter.setData(data);
                                }

                            }
                        });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rv_search_hot.addOnItemTouchListener(new OnRecyclerItemClickListener(rv_search_hot) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                SearchHotEntity.DataBean data = searchHotEntityData.get(vh.getLayoutPosition());
                Intent intent = new Intent();
                intent.setClass(mContext,DetailNewhouseActivity.class);
                intent.putExtra("Aid",data.getAid());
                intent.putExtra("Name",data.getSubject());
                startActivity(intent);
                if(searchHistory==null){
                    searchHistory=new ArrayList<String>();
                }
                searchHistory.add(0,data.getSubject());
                CacheUtils.putObject(mContext, "SearchHistory",searchHistory);
//                finish();
            }
        });

        lv_search_tips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchTipsList.DataEntity data = tipsList.get(position);
                if(data.isNoData()){
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(mContext,DetailNewhouseActivity.class);
                intent.putExtra("Aid",data.getAid());
                intent.putExtra("Name",data.getSubject());
                startActivity(intent);

                if(searchHistory==null){
                    searchHistory=new ArrayList<String>();
                }
                searchHistory.add(0,data.getSubject());
                CacheUtils.putObject(mContext, "SearchHistory",searchHistory);

//                finish();

            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext,SearchResultActivity.class);
                String subject = et_search.getText().toString().trim();
                intent.putExtra("searchContent",subject);
                startActivityForResult(intent, Constant.REQCODE_SEARCHRESULT);

                if(searchHistory==null){
                    searchHistory=new ArrayList<String>();
                }
                searchHistory.add(0,subject);
                CacheUtils.putObject(mContext, "SearchHistory",searchHistory);

            }
        });

        lv_search_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(mContext,SearchResultActivity.class);
                String subject = searchHistory.get(position);
                intent.putExtra("searchContent",subject);
                startActivityForResult(intent, Constant.REQCODE_SEARCHRESULT);

            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case  Constant.REQCODE_SEARCHRESULT:
                if(resultCode==0) {
//                    finish();
                }else{
                    String searchContent = data.getStringExtra("SearchContent");
                    et_search.setText(searchContent);
                    et_search.setSelection(searchContent.length());

                }
                break;
        }

    }
}
