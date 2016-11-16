package com.mpzn.mpzn.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.activity.DetailNewhouseActivity;
import com.mpzn.mpzn.activity.DetailNewsActivity;
import com.mpzn.mpzn.activity.WebViewActivity;
import com.mpzn.mpzn.adapter.RvMessageAdapter;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseFragment;
import com.mpzn.mpzn.entity.AbstractEntity;
import com.mpzn.mpzn.entity.JPushNotificationEntity;
import com.mpzn.mpzn.entity.MessageEntity;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.listener.OnRecyclerItemClickListener;
import com.mpzn.mpzn.utils.CacheUtils;
import com.mpzn.mpzn.utils.ViewUtils;
import com.mpzn.mpzn.views.MyActionBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import okhttp3.Call;

import static com.mpzn.mpzn.receiver.JPushReceiver.TYPE_BUILDING;
import static com.mpzn.mpzn.receiver.JPushReceiver.TYPE_HTML;
import static com.mpzn.mpzn.receiver.JPushReceiver.TYPE_NEWS;

/**
 * Created by Icy on 2016/7/13.
 * larryaaaaaaaaaaa
 */
public class MessageFragment extends BaseFragment {
    public static String TAG = "tag_building_fragment";
    @Bind(R.id.state_bar)
    View stateBar;
    @Bind(R.id.my_action_bar)
    MyActionBar myActionBar;
    @Bind(R.id.rv_Message)
    RecyclerView rvMessage;
    List<MessageEntity> messageEntityList = new ArrayList<>();
    @Bind(R.id.ll_no_data)
    LinearLayout llNoData;

    private ViewGroup fragment_building;
    private RvMessageAdapter rvMessageAdapter;
    private LinearLayoutManager linearLayoutManager;


    public MessageFragment() {
        //注册EventBus
        EventBus.getDefault().register(this);
        Log.e("TAG", "fm注册Eventbus");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
        Log.e("TAG", "fm解注册Eventbus");
    }


    @Override
    public View getLayourView(LayoutInflater inflater, ViewGroup container) {
        fragment_building = (ViewGroup) inflater.inflate(R.layout.fragment_building, null);
        return fragment_building;
    }

    @Override
    public void initHolder(View view) {

        myActionBar.init("消息", 0, R.drawable.search_white);

        linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvMessageAdapter = new RvMessageAdapter(mContext, messageEntityList);
        rvMessage.setAdapter(rvMessageAdapter);
        rvMessage.setLayoutManager(linearLayoutManager);
        rvMessage.setItemAnimator(new DefaultItemAnimator());


    }

    @Override
    public void initLayoutParams() {
        if (MyApplication.getInstance().isInfect) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ViewUtils.getStatusBarHeight());
            stateBar.setLayoutParams(layoutParams);
        } else {
            stateBar.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventBackgroudThread(final JPushNotificationEntity jPushNotificationEntity) {
        Log.e("TAG", "接到消息" + jPushNotificationEntity.toString());

        if (jPushNotificationEntity.getType().equals(TYPE_HTML)) {

        } else {
            String url = null;
            if (jPushNotificationEntity.getType().equals(TYPE_BUILDING)) {
                url = API.BUILDINGABTRACT_GET;

            } else if (jPushNotificationEntity.getType().equals(TYPE_NEWS)) {
                url = API.NEWSABTRACT_GET;
            }
            Log.e("TAG", "联网" + url + jPushNotificationEntity.getId());
            OkHttpUtils.get()
                    .url(url)
                    .addParams("aid", jPushNotificationEntity.getId())
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            AbstractEntity abstractEntity = new Gson().fromJson(response, AbstractEntity.class);
                            Log.e("TAG", "获取详情" + response);

                            if (abstractEntity.getCode() == 200) {
                                MessageEntity messageEntity = abstractEntity.getData();
                                messageEntity.setCreatDate(System.currentTimeMillis());
                                messageEntity.setType(jPushNotificationEntity.getType());

                                if (rvMessageAdapter != null) {
                                    if(llNoData.getVisibility()==View.VISIBLE){
                                        llNoData.setVisibility(View.GONE);
                                    }
                                    messageEntityList.add(messageEntity);
                                    rvMessageAdapter.notifyDataSetChanged();
                                    rvMessage.scrollToPosition(messageEntityList.size() - 1);
                                }
                                CacheUtils.putObject(mContext, "MessageList", messageEntityList);
                            }

                        }
                    });

        }

    }

    @Override
    public void initData() {

        List<MessageEntity> messageList = (List<MessageEntity>) CacheUtils.getObject(mContext, "MessageList");
        if (messageList != null && messageList.size() != 0) {
            messageEntityList.addAll(messageList);
            rvMessageAdapter.notifyDataSetChanged();
            if(messageList.size()==0){
                llNoData.setVisibility(View.VISIBLE);
            }
            rvMessage.scrollToPosition(messageEntityList.size() - 1);

        }else{
            llNoData.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void bindListener() {
        myActionBar.setLROnClickListener(null, null);
        rvMessage.addOnItemTouchListener(new OnRecyclerItemClickListener(rvMessage) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                MessageEntity messageEntity = messageEntityList.get(vh.getLayoutPosition());
                String type = messageEntity.getType();
                Intent intent = new Intent();
                if ("news".equals(type)) {
                    intent.putExtra("NewsAid", messageEntity.getAid());
                    intent.setClass(mContext, DetailNewsActivity.class);
                    startActivity(intent);

                } else if ("building".equals(type)) {
                    intent.putExtra("Aid", messageEntity.getAid());
                    intent.setClass(mContext, DetailNewhouseActivity.class);
                    startActivity(intent);
                } else if ("html".equals(type)) {
                    intent.putExtra("title", messageEntity.getSubject());
                    intent.putExtra("url", messageEntity.getUrl());
                    intent.setClass(mContext, WebViewActivity.class);
                    startActivity(intent);

                }

            }
        });

    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.e("TAG", "requestCode+building" + requestCode);
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // TODO: inflate a fragment view
//        View rootView = super.onCreateView(inflater, container, savedInstanceState);
//        ButterKnife.bind(this, rootView);
//        return rootView;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        ButterKnife.unbind(this);
//    }
}
