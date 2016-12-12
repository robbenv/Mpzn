package com.mpzn.mpzn.http;

/**
 * Created by Icy on 2016/8/25.
 */
public class API {
    public static final String API="http://appi.mpzn.com/index.php";
    public static final String HOME_GET=API+"/home/get_index_info";
    public static final String NEWSLIST_GET=API+"/news/lists";  //参数offset
    public static final String NEWSTOP_GET=API+"/news/nav";
    public static final String HOMENEWSLIST_GET=API+"/home/news";//没用
    public static final String LOGIN_GET=API+"/user/login";  //首次登录参数: username pass 第二次登录参数：userid token
    public static final String LOGOUT_GET=API+"/user/logout";
    public static final String NEWDETAIL="http://pan.guyun18.com/android_news.html?aid="; //参数aid
    public static final String BUILDINGINIT_GET="/home/index";//没用
    public static final String BUILDINGLIST_GET=API+"/filter/tofilter";//参数：region="null",price="null",type="null",character="null",page=0
    public static final String BUILDINGDETAIL_GET=API+"/building/buildingdetail";//参数aid
    public static final String BUILDINGTJ_GET=API+"/recommend/lplist";//没用
    public static final String HOME_BBXX=API+"/baobei/baobeilist";//没用
    public static final String REG_CHECK_POST=API+"/Regist/checkunique";
    public static final String REG_SEND_SMS=API+"/SendMessage/sendSMS";
    public static final String REGIST_POST=API+"/Regist/toregist";
    public static final String SEARCH_TIPS=API+"/Filter/autocomplete";
    public static final String SEARCH_HOT=API+"/filter/HotSearch";
    public static final String VERIFYPASS=API+"/user/verifypass";
    public static final String ALTERPASS=API+"/user/alterpass";
    public static final String ADDBB=API+"/baobei/add";
    public static final String ADDBBMSG =API+"/baobei/add_info";
    public static final String USERMSG_GET = API+"/user/detail";
    public static final String COMMITUSERMSG_POST =API+ "/user/alterdetail";
    public static final String STARLIST_GET = API+"/collection/loupanlist";
    public static final String CHECKSTAR_GET = API+"/collection/checkloupan";
    public static final String STAR_POST = API+"/collection/addloupan";
    public static final String CANCELSTAR_POST = API+"/collection/cancelloupan";
    public static final String CANCELSTARLIST_POST = API+"/collection/batchdeleteloupan";
    public static final String MYBB_LIST = API+"/baobei/lists";
    public static final String UPLOADICON = API+"/user/uploadheadimage";
    public static final String BB_STATISTICS_GET = API+"/baobei/tongji";
    public static final String BROWSELIST_GET = API+"/browse/loupanlist";
    public static final String CANCELBROWSELIST_POST = API+"/browse/batchdeleteloupan";
    public static final String ADDBROWSE_POST = API+"/browse/addloupan";
    public static final String SEND_REVIEW_POST = API+"/comment/addnews";
    public static final String REVIEW_LIST_GET = API+"/comment/shownewslist";
    public static final String ZAN_REVIEW_POST = API+"/comment/addfavour";
    public static final String CANCEL_ZAN_REVIEW_POST = API+"/comment/cancelfavour";
    public static final String FORSELLBUILDLIST_GET = API+"/agent/applyloupandaixiaolist";
    public static final String APPLYBUILDING_POST = API+"/agent/applyloupandaixiao";
    public static final String MYSELL_LIST_GET = API+"/agent/loupandaixiaolist";
    public static final String JINGJIREN_OWN_GET = API+"/agent/brokermanage";
    public static final String MANAGEBROKERSLIST_POST = API+"/agent/managebrokers";
    public static final String CHECK_ADD_JINGJIREN_GET =API+"/agent/check_add" ;
    public static final String ADD_JINGJIREN_POST = API+"/agent/addbroker";
    public static final String WX_LOGIN_GET = API+"/user/weixinlogin";
    public static final String FEEDBACK_POST = API+"/UserRequire/suggest";
    public static final String CHECK_VERSION = API+"/Version/get_ver_update";
    public static final String REGIST_FAST = API+"/user/phone_rapid_login";
    public static final String CHECK_PHONE_ISEXSIST_POST = API+"/Regist/checkunique";
    public static final String CHECK_CODE_ISCORRECT_POST = API+"/regist/checkmessage";
    public static final String JJCOM_LIST_GET = API+"/broker/apply_search_list";
    public static final String APPLYJJCOM_POST = API+"/broker/apply_agent";
    public static final String FORGETPASS_POST = API+"/user/modifypass";
    public static final String CHECK_HAS_JJCOM = API+"/broker/check_has_agent";
    public static final String APPLYBUILDING_TIPS_GET = API+"/agent/applyloupandaixiaolist";
    public static final String BBRANK_GET = API+"/baobei/tongji";
    public static final String BUILDINGABTRACT_GET = API+"/Building/abstractbuilding" ;
    public static final String NEWSABTRACT_GET = API+"/news/abstractnews";
    public static final String CHECK_IS_ADD_GET = API+"/baobei/check_is_add";
    public static final String BUILDING_PRICE = "http://pan.guyun18.com/jiagezoushi.html";
    public static final String GFTOOL = "http://pan.guyun18.com/gfTool.html";
    public static final String MAP_SEARCH = "http://pan.guyun18.com/mapSearch.html";
    public static final String TRACK_SETUP = "http://pan.guyun18.com/shoppingYixiang.html?cid=";
    public static final String CHECK_SELL_GET = API+"/agent/check_loupan_is_daixiao";
    public static final String GET_ICON_GET = API+"/user/account_avatar";
    public static final String WX_REGIST = API+"/Regist/weixinregist";
    public static final String CHECKBB_POST = API+"/developers/check_baobei";
    public static final String KFS_OWN_BUILDING_GET = API+"/developers/ownloupan";
    public static final String BB_STATISTICS_FORKFS_GET = API+"/baobei/tongji";
    public static final String PROXY_SELL_GET = API+"/developers/loupandaixiaolist";
    public static final String PROXY_SELL_HANDLE = API+"/developers/manageloupandaixiao";
    public static final String BIND_WX = API+"/user/bind_weixin";
    public static final String TAKEPUSH="http://appi.mpzn.com/jpush/jpush/dopush.php";
    public static final String BBCOMPANY=API + "/baobei/agenttongji";
    public static final String FILTER_LOUPAN = API + "/baobei/tongji_loupan_list";
    public static final String TRACK_USER_LIST = API + "/baobei/tracking_list";
}
