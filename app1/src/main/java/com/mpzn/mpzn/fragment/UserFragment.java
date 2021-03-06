package com.mpzn.mpzn.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.activity.AddBBActivity;
import com.mpzn.mpzn.activity.ApplyForSellActivity;
import com.mpzn.mpzn.activity.BBStaticsForJJComActivity;
import com.mpzn.mpzn.activity.BBStatisticsActivity;
import com.mpzn.mpzn.activity.BBstaticForKfsAcitvity;
import com.mpzn.mpzn.activity.ChangIconActivity;
import com.mpzn.mpzn.activity.CheckBBActivity;
import com.mpzn.mpzn.activity.JingjiRenManageActivity;
import com.mpzn.mpzn.activity.LoginActivity;
import com.mpzn.mpzn.activity.MyBBActivity;
import com.mpzn.mpzn.activity.MyDataActivity;
import com.mpzn.mpzn.activity.MySellActivity;
import com.mpzn.mpzn.activity.ProxySellManageActivity;
import com.mpzn.mpzn.activity.RegForUserTypeActivity;
import com.mpzn.mpzn.activity.SetupActivity;
import com.mpzn.mpzn.activity.StarAndBrowseActivity;
import com.mpzn.mpzn.activity.TestActivity;
import com.mpzn.mpzn.activity.UserTrackActivity;
import com.mpzn.mpzn.activity.WebViewActivity;
import com.mpzn.mpzn.adapter.MainUserRecyclerViewAdapter;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseFragment;
import com.mpzn.mpzn.entity.ItemUserTool;
import com.mpzn.mpzn.entity.UserMsg;
import com.mpzn.mpzn.helper.MyItemTouchCallback;
import com.mpzn.mpzn.listener.OnRecyclerItemClickListener;
import com.mpzn.mpzn.utils.ACache;
import com.mpzn.mpzn.utils.CacheUtils;
import com.mpzn.mpzn.utils.GlideCircleTransform;
import com.mpzn.mpzn.utils.VibratorUtil;
import com.mpzn.mpzn.utils.ViewUtils;
import com.mpzn.mpzn.views.DividerItemDecoration;
import com.mpzn.mpzn.views.SmoothListView.DividerGridItemDecoration;
import com.mpzn.mpzn.views.WaveView3;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;


import okhttp3.Cache;

import static com.mpzn.mpzn.utils.CacheUtils.putObject;
import static com.mpzn.mpzn.utils.Constant.REQCODE_CHANGICON;
import static com.mpzn.mpzn.utils.Constant.REQCODE_LOGIN;
import static com.mpzn.mpzn.utils.Constant.REQCODE_MYDATA;
import static com.mpzn.mpzn.utils.Constant.REQCODE_SETTING;
import static com.mpzn.mpzn.utils.Constant.RESCODE_JINGJICOM;
import static com.mpzn.mpzn.utils.Constant.RESCODE_JINGJIREN;
import static com.mpzn.mpzn.utils.Constant.RESCODE_KAIFASHANG;
import static com.mpzn.mpzn.utils.Constant.USERTOOLIMGS;
import static com.mpzn.mpzn.utils.Constant.USERTOOLS;
import static com.mpzn.mpzn.utils.Constant.USERTOOL_DEFAULT;
import static com.mpzn.mpzn.utils.Constant.USERTOOL_JINGJICOM;
import static com.mpzn.mpzn.utils.Constant.USERTOOL_JINGJIREN;
import static com.mpzn.mpzn.utils.Constant.USERTOOL_KAIFASHANG;

/**
 * Created by Icy on 2016/7/13.
 */
public class UserFragment extends BaseFragment implements MyItemTouchCallback.OnDragListener{

    public static String TAG="tag_user_fragment";




    private View fragment_user;
    private View state_bar;
    private ImageView user_icon;
    private ImageView iv_user_type;
    private ImageButton iv_setting;
    private TextView tv_login;
    private RecyclerView rv_user;
    private Button tv_calculator;
    private RelativeLayout rl_user_content;
    private RelativeLayout user_top;
    private WaveView3 wave_view;
    private MainUserRecyclerViewAdapter userRecyclerViewAdapter;
    private List<ItemUserTool> results=new ArrayList<>();
    private ItemTouchHelper itemTouchHelper;
    private UserMsg userMsg;


    private android.os.Handler handler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1) {

            }
        }
    };



    @Override
    public View getLayourView(LayoutInflater inflater, ViewGroup container) {
        fragment_user = inflater.inflate(R.layout.fragment_user, null);
        return fragment_user;
    }


    @Override
    public void initHolder(View view) {
        state_bar = (View)fragment_user.findViewById(R.id.state_bar);
        user_icon = (ImageView)fragment_user.findViewById(R.id.user_icon);
        tv_login = (TextView)fragment_user.findViewById(R.id.tv_login);
        iv_user_type = (ImageView)fragment_user.findViewById(R.id.iv_user_type);
        rv_user = (RecyclerView)fragment_user.findViewById(R.id.rv_user);
        tv_calculator = (Button)fragment_user.findViewById(R.id.tv_calculator);
        iv_setting = (ImageButton)fragment_user.findViewById(R.id.iv_setting);
        rl_user_content= (RelativeLayout)fragment_user.findViewById(R.id.rl_user_content);
        user_top = (RelativeLayout) fragment_user.findViewById(R.id.user_top);
        wave_view = (WaveView3) fragment_user.findViewById(R.id.wave_view);
    }

    @Override
    public void initLayoutParams() {
        if(MyApplication.getInstance().isInfect) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewUtils.getStatusBarHeight());
            state_bar.setLayoutParams(layoutParams);
        }else{
            state_bar.setVisibility(View.GONE);
        }

    }

    @Override
    public void initData() {
        if(MyApplication.isLogined) {
//            ArrayList<ItemUserTool> userToolItems =
//                    (ArrayList<ItemUserTool>) ACache.get(getActivity()).getAsObject("userToolItems");
            int[] usertool = (int[]) CacheUtils.getObject(mContext, "usertool");

            if(usertool!=null){
                for (int i = 0; i < usertool.length; i++) {
                    results.add(new ItemUserTool(USERTOOLS[usertool[i]], USERTOOLIMGS[usertool[i]],usertool[i]));
                }
            }

        }else{
            int[] usertool = USERTOOL_DEFAULT;
            for (int i = 0; i < usertool.length; i++) {
                results.add(new ItemUserTool(USERTOOLS[usertool[i]], USERTOOLIMGS[usertool[i]],usertool[i]));

            }
        }


        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
        rv_user.setLayoutManager(gridLayoutManager);
        rv_user.setHasFixedSize(true);
        userRecyclerViewAdapter =  new MainUserRecyclerViewAdapter(results);
        rv_user.setAdapter(userRecyclerViewAdapter);
        rv_user.addItemDecoration(new DividerGridItemDecoration(mContext));


        if(MyApplication.isLogined){
            userMsg=MyApplication.getInstance().mUserMsg;
            upDataView();
        }else{
            initViewForLogout();
        }
        
    }

    private void initViewForLogout(){

        results = new ArrayList<>();
        int[] usertool=USERTOOL_DEFAULT;
        for (int i = 0; i < usertool.length; i++) {
            results.add(new ItemUserTool(USERTOOLS[usertool[i]], USERTOOLIMGS[usertool[i]],usertool[i]));

        }
        userRecyclerViewAdapter.updata(results);


        tv_login.setText("立即登录");
        Log.e("TAG", "tv_login"+tv_login.getText());
        iv_user_type.setImageDrawable(null);
        mImageManager.loadCircleResImage(R.drawable.logo_gray,user_icon);

        CacheUtils.putObject(mContext,"usertool",null);

    }

    @Override
    public void bindListener() {
        user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogined) {

                    Intent intent = new Intent(mContext, ChangIconActivity.class);
                    startActivityForResult(intent, REQCODE_CHANGICON);

                } else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    Log.e("TAG", "主页面回调启动登录页");
                    startActivityForResult(intent, REQCODE_LOGIN);

                }
            }
        });

        rl_user_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogined) {

                    Intent intent = new Intent(mContext, MyDataActivity.class);
                    startActivityForResult(intent, REQCODE_MYDATA);

                } else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent, REQCODE_LOGIN);
                }
            }
        });

        itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallback(userRecyclerViewAdapter).setOnDragListener(this));
        itemTouchHelper.attachToRecyclerView(rv_user);

        rv_user.addOnItemTouchListener(new OnRecyclerItemClickListener(rv_user) {
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
                if (vh.getLayoutPosition() != results.size() - 1) {
                    itemTouchHelper.startDrag(vh);
                    VibratorUtil.Vibrate(getActivity(), 70);   //震动70ms
                }
            }

            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                String itemName = results.get(vh.getLayoutPosition()).getName().toString();
                if((itemName).equals("添加报备")){

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

                }else if((itemName).equals("浏览历史")){
                    if(MyApplication.getInstance().isLogined) {
                        Intent intent = new Intent();
                        intent.putExtra("type",1);
                        intent.setClass(mContext,StarAndBrowseActivity.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setClass(mContext, LoginActivity.class);
                        startActivityForResult(intent, REQCODE_LOGIN);
                    }


                }else if((itemName).equals("我的收藏")){
                    if(MyApplication.getInstance().isLogined) {
                        Intent intent = new Intent();
                        intent.putExtra("type",0);
                        intent.setClass(mContext,StarAndBrowseActivity.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setClass(mContext, LoginActivity.class);
                        startActivityForResult(intent, REQCODE_LOGIN);
                    }


                }else if((itemName).equals("我的报备")){
                    Intent intent = new Intent();
                    intent.setClass(mContext,MyBBActivity.class);
                    startActivity(intent);

                }else if((itemName).equals("报备管理")){
                    Intent intent = new Intent();
                    intent.setClass(mContext,MyBBActivity.class);
                    startActivity(intent);

                }else if((itemName).equals("报备统计")){
                    Intent intent = new Intent();
                    if(MyApplication.getInstance().mUserMsg.getmChild()==RESCODE_JINGJIREN) {
                        intent.setClass(mContext, BBStatisticsActivity.class);
                    }else if(MyApplication.getInstance().mUserMsg.getmChild()==RESCODE_JINGJICOM){
                        intent.setClass(mContext, BBStaticsForJJComActivity.class);
                    } else if(MyApplication.getInstance().mUserMsg.getmChild()==RESCODE_KAIFASHANG){
                        intent.setClass(mContext,BBstaticForKfsAcitvity.class);
                    }

                    startActivity(intent);

                }else if((itemName).equals("楼盘代销")){
                    Intent intent = new Intent();
                    intent.setClass(mContext, ApplyForSellActivity.class);
                    startActivity(intent);

                }else if((itemName).equals("我的代销")){
                    Intent intent = new Intent();
                    intent.setClass(mContext,MySellActivity.class);
                    startActivity(intent);
                }else if((itemName).equals("经纪人管理")){
                    Intent intent = new Intent();
                    intent.setClass(mContext,JingjiRenManageActivity.class);
                    startActivity(intent);
                }else if((itemName).equals("驻场核验")){
                    Intent intent = new Intent();
                    intent.setClass(mContext,CheckBBActivity.class);
                    startActivity(intent);
                } else if ((itemName).equals("代销管理")) {
                    Intent intent = new Intent();
                    intent.setClass(mContext,ProxySellManageActivity.class);
                    startActivity(intent);
                } else if((itemName).equals("更多")){
                    if(MyApplication.getInstance().isLogined) {
//                        Toast.makeText(mContext, "暂无更多", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent();
//                        intent.setClass(mContext,TestActivity.class);
//                        startActivity(intent);
                        Toast.makeText(mContext, "更多功能，敬请期待", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(mContext, "查看更多，请先登录", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setClass(mContext, LoginActivity.class);
                        getActivity().startActivity(intent);
                    }
                } else if ((itemName).equals("客户信息")) {
                    Intent intent = new Intent();
                    intent.setClass(mContext,UserTrackActivity.class);
                    startActivity(intent);
                }
            }
        });
        userRecyclerViewAdapter.setOnItemClickListener(new MainUserRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, ItemUserTool data) {
                Toast.makeText(mContext, data.getName(), Toast.LENGTH_SHORT).show();

            }
        });
        tv_calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "暂未开放，敬请期待", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent();
//                intent.putExtra("title", "房贷计算器");
//                intent.putExtra("url", "http://pan.guyun18.com/calculator.html");
//                intent.setClass(mContext, WebViewActivity.class);
//                getActivity().startActivity(intent);
            }
        });
        iv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, SetupActivity.class);
                startActivityForResult(intent, REQCODE_SETTING);
            }
        });

        final RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewUtils.dip2px(80),ViewUtils.dip2px(80));
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        wave_view.setOnWaveAnimationListener(new WaveView3.OnWaveAnimationListener() {
            @Override
            public void OnWaveAnimation(float y) {
                lp.setMargins(ViewUtils.dip2px(20),0,0,(int)y+ViewUtils.dip2px(5));
                user_icon.setLayoutParams(lp);
            }
        });



    }
    public void upDataView(){
        int[] usertool;
        Drawable drawableB = null;

        switch (userMsg.getmChild()) {
            case RESCODE_JINGJIREN:
                usertool=USERTOOL_JINGJIREN;
                drawableB = getResources().getDrawable(R.drawable.logo_jingjiren);
                break;
            case RESCODE_JINGJICOM:
                usertool=USERTOOL_JINGJICOM;
                drawableB = getResources().getDrawable(R.drawable.logo_jingjicom);


                break;
            case RESCODE_KAIFASHANG:
                usertool=USERTOOL_KAIFASHANG;
                drawableB = getResources().getDrawable(R.drawable.logo_kaifashang);


                break;
            default:
                usertool=USERTOOL_DEFAULT;
                break;
        }

           if(drawableB!=null) {
               iv_user_type.setImageDrawable(drawableB);
           }

        mImageManager.loadCircleImageWithWhite(userMsg.getmIconUrl(),user_icon);
        if (userMsg.getName() == null || "".equals(userMsg.getName())) {
            //如果没有用户姓名，就用电话，如果有用户姓名，就用用户姓名
            tv_login.setText(userMsg.getmName());
        } else {
            tv_login.setText(userMsg.getName());
        }


        results = new ArrayList<>();


        int[] usertoolCache = (int[]) CacheUtils.getObject(mContext, "usertool");

        if(usertoolCache!=null&&usertoolCache.length!=0){
            for (int i = 0; i < usertoolCache.length;i++) {
                results.add(new ItemUserTool(USERTOOLS[usertoolCache[i]], USERTOOLIMGS[usertoolCache[i]],usertoolCache[i]));
            }
        }else{
            for (int i = 0; i < usertool.length; i++) {
                results.add(new ItemUserTool(USERTOOLS[usertool[i]], USERTOOLIMGS[usertool[i]],usertool[i]));

            }
        }

        userRecyclerViewAdapter.updata(results);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("TAG", "data"+data);
        Log.e("TAG", "requestCode"+requestCode+"resultCode"+resultCode);

        switch (requestCode) {
            case REQCODE_LOGIN:
                Logger.d("onActivityResult()__REQCODE_LOGIN");
                if(resultCode!=0) {
                    Log.e("TAG", "resultCode"+resultCode);
                    userMsg= data.getParcelableExtra("userMsg");
                    Logger.d("onActivityResult()__name = " + userMsg.getmName());
                    upDataView();

                }

                break;
            case REQCODE_SETTING:
                Logger.d("onActivityResult()__REQCODE_SETTING");
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        initViewForLogout();
                        break;

                }

                break;
            case REQCODE_MYDATA:
                Logger.d("onActivityResult()__REQCODE_MYDATA");
                switch (resultCode) {
                    case  Activity.RESULT_OK:

                        tv_login.setText(data.getStringExtra("Name"));
                        mImageManager.loadCircleImageWithWhite(MyApplication.getInstance().mUserMsg.getmIconUrl(),user_icon);
                        break;
                }


                break;
            case REQCODE_CHANGICON:
                Logger.d("onActivityResult()__REQCODE_CHANGICON");
                switch (resultCode) {
                    case  Activity.RESULT_OK:
                        mImageManager.loadCircleImageWithWhite(MyApplication.getInstance().mUserMsg.getmIconUrl(),user_icon);
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                }

                break;
        }

    }


    @Override
    public void onFinishDrag() {
        //存入缓存
        CacheUtils.putObject(mContext,"usertool",userRecyclerViewAdapter.getItems());


    }
}
