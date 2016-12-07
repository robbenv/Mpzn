package com.mpzn.mpzn.utils;

import com.mpzn.mpzn.R;

/**
 * Created by Icy on 2016/9/1.
 */
public class Constant  {



    public static String[] USERTOOLS={
            "浏览历史","我的收藏","我的报备","报备管理","添加报备",
            "报备统计","楼盘代销","楼盘意向","代销管理","我的代销","经纪人管理","驻场核验","客户信息", "更多"};

    public static int[] USERTOOLIMGS={R.drawable.look_his,R.drawable.favorite,
            R.drawable.baobei,R.drawable.bb_manage,R.drawable.add_bb,R.drawable.bb_statistics,
            R.drawable.house_manage,R.drawable.house_intention,R.drawable.apply_for_sell,
            R.drawable.my_sell, R.drawable.agent_manage,R.drawable.checkbaobei,R.drawable.user_genzong, R.drawable.user_tool_more};
    public static int[] USERTOOL_DEFAULT={0,1,13};
    public static int[] USERTOOL_JINGJIREN={0,1,2,4,5,13};
    public static int[] USERTOOL_JINGJICOM={0,1,3,4,5,6,9,10,13};
    public static int[] USERTOOL_KAIFASHANG={0,1,5,8,11,12,13};



    public static final int REQCODE_LOGIN_FAST=10;
    public static final int REQCODE_LOGIN=20;
    public static final int REQCODE_SETTING=30;
    public static final int REQCODE_MYDATA=40;
    public static final int REQCODE_REGISTER_SEND_CODE = 500;
    public static final int REQCODE_REGISTER_SET_PASS = 501;
    public static final int REQCODE_REGISTER_FOR_USER_TYPE = 502;
    public static final int REQCODE_SEARCHRESULT=102;
    public static final int REQCODE_CHANGICON=401;

    public static final int RESCODE_DEFAULT = 1;   //普通账户类型
    public static final int RESCODE_JINGJIREN = 2;   //经纪人账户类型
    public static final int RESCODE_JINGJICOM = 3;    //经纪公司
    public static final int RESCODE_KAIFASHANG = 13;    //开发商


    public static final String ACTION_DOWNLOAD_APK ="ACTION_DOWNLOAD_APK";
}
