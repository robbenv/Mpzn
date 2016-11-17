package com.mpzn.mpzn.entity;

import com.mpzn.mpzn.views.FilterView.entity.BuildingEntity;

import java.util.List;

/**
 * Created by Icy on 2016/9/5.
 */
public class BuildingList {


    /**
     * code : 200
     * message : success
     * data : [{"diqu":"青浦","title":"现房","jdjj":0,"ccid":3,"subject":"万科有山","aid":5,"thumb":"http://mpzn8.mpzn.com/userfiles/image/20151222/2214182047e2c4ae860730.jpg#640#480","dj":40000,"tslp":"2\t5","zxcd":4,"clicks":851,"address":"青浦 置旺路288弄（近嘉松中路）","url":"http://mpzn8.mpzn.com/thinkphp/index.php/home/building/buildingdetail/aid/5","tag":{"zxcdarray":"全装修","tslparray":["小户型投资","宜居生态"],"wylxarray":"住宅"}},{"diqu":"青浦","title":"期房","jdjj":0,"ccid":3,"subject":"鑫塔水尚","aid":35,"thumb":"http://mpzn8.mpzn.com/userfiles/image/20151225/25164045ceb902ebcc3061.jpg#770#578","dj":25000,"tslp":"5","zxcd":4,"clicks":766,"address":"上海市青浦区沪青平公路5000弄","url":"http://mpzn8.mpzn.com/thinkphp/index.php/home/building/buildingdetail/aid/35","tag":{"zxcdarray":"全装修","tslparray":["宜居生态"],"wylxarray":"住宅"}},{"diqu":"青浦","title":"期房","jdjj":24000,"ccid":3,"subject":"融信铂湾","aid":30,"thumb":"http://mpzn8.mpzn.com/userfiles/image/20160111/11110331d17111e9597107.jpg","dj":33000,"tslp":"5","zxcd":1,"clicks":1005,"address":"上海市青浦区赵巷置鼎路889弄","url":"http://mpzn8.mpzn.com/thinkphp/index.php/home/building/buildingdetail/aid/30","tag":{"zxcdarray":"毛坯","tslparray":["宜居生态"],"wylxarray":"住宅"}},{"diqu":"松江","title":"期房","jdjj":0,"ccid":3,"subject":"贝尚湾","aid":44,"thumb":"http://mpzn8.mpzn.com/userfiles/image/20151225/2517073591d6298df52002.jpg#770#578","dj":35000,"tslp":"3\t5","zxcd":4,"clicks":507,"address":"上海市松江沪亭北路488号（涞寅路口）","url":"http://mpzn8.mpzn.com/thinkphp/index.php/home/building/buildingdetail/aid/44","tag":{"zxcdarray":"全装修","tslparray":["教育地产","宜居生态"],"wylxarray":"住宅"}},{"diqu":"松江","title":"期房","jdjj":0,"ccid":3,"subject":"恒大佘山首府","aid":54,"thumb":"http://mpzn8.mpzn.com/userfiles/image/20151225/25173056a4d8f5e1e16031.jpg#867#578","dj":32000,"tslp":"","zxcd":1,"clicks":515,"address":"上海市松江区勋业东路300弄","url":"http://mpzn8.mpzn.com/thinkphp/index.php/home/building/buildingdetail/aid/54","tag":{"zxcdarray":"毛坯","tslparray":[null],"wylxarray":"住宅"}},{"diqu":"奉贤","title":"现房","jdjj":0,"ccid":3,"subject":"绿地小米公社","aid":62,"thumb":"http://mpzn8.mpzn.com/userfiles/image/20151229/290929526cf4a6691b0217.jpg#867#578","dj":14000,"tslp":"","zxcd":1,"clicks":386,"address":"奉贤奉城镇兰博路1189弄(近兰博路)","url":"http://mpzn8.mpzn.com/thinkphp/index.php/home/building/buildingdetail/aid/62","tag":{"zxcdarray":"毛坯","tslparray":[null],"wylxarray":"住宅"}},{"diqu":"浦东","title":"现房","jdjj":0,"ccid":3,"subject":"仁恒东郊花园","aid":66,"thumb":"http://mpzn8.mpzn.com/userfiles/image/20151229/29165528ffb98697c48146.jpg#770#578","dj":55000,"tslp":"","zxcd":4,"clicks":445,"address":"上海市浦东新区唐龙路1399号（近顾唐路","url":"http://mpzn8.mpzn.com/thinkphp/index.php/home/building/buildingdetail/aid/66","tag":{"zxcdarray":"全装修","tslparray":[null],"wylxarray":"住宅"}},{"diqu":"虹口","title":"待售","jdjj":0,"ccid":3,"subject":"瑞虹新城怡庭","aid":68,"thumb":"http://mpzn8.mpzn.com/userfiles/image/20151229/291539309c8e6b36649381.png#485#500","dj":80000,"tslp":"","zxcd":4,"clicks":462,"address":"虹口临平路255号（靠近瑞虹路）","url":"http://mpzn8.mpzn.com/thinkphp/index.php/home/building/buildingdetail/aid/68","tag":{"zxcdarray":"全装修","tslparray":[null],"wylxarray":"住宅"}},{"diqu":"浦东","title":"现房","jdjj":0,"ccid":3,"subject":"陆家嘴滨江公馆","aid":70,"thumb":"http://mpzn8.mpzn.com/userfiles/image/20151229/2916454170126b4d069372.jpg#867#578","dj":49305,"tslp":"","zxcd":1,"clicks":358,"address":"浦东新区浦东大道1638号","url":"http://mpzn8.mpzn.com/thinkphp/index.php/home/building/buildingdetail/aid/70","tag":{"zxcdarray":"毛坯","tslparray":[null],"wylxarray":"住宅"}},{"diqu":"浦东","title":"待售","jdjj":0,"ccid":3,"subject":"凯德新视界","aid":76,"thumb":"http://mpzn8.mpzn.com/userfiles/image/20151229/291741088f750ca2803742.jpg#426#320","dj":14000,"tslp":"","zxcd":1,"clicks":358,"address":"浦东新区祥安路233弄","url":"http://mpzn8.mpzn.com/thinkphp/index.php/home/building/buildingdetail/aid/76","tag":{"zxcdarray":"毛坯","tslparray":[null],"wylxarray":"住宅"}}]
     */

    private int code;
    private String message;
    /**
     * diqu : 青浦
     * title : 现房
     * jdjj : 0
     * ccid : 3
     * subject : 万科有山
     * aid : 5
     * thumb : http://mpzn8.mpzn.com/userfiles/image/20151222/2214182047e2c4ae860730.jpg#640#480
     * dj : 40000
     * tslp : 2	5
     * zxcd : 4
     * clicks : 851
     * address : 青浦 置旺路288弄（近嘉松中路）
     * url : http://mpzn8.mpzn.com/thinkphp/index.php/home/building/buildingdetail/aid/5
     * tag : {"zxcdarray":"全装修","tslparray":["小户型投资","宜居生态"],"wylxarray":"住宅"}
     */

    private List<BuildingEntity> data;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(List<BuildingEntity> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<BuildingEntity> getData() {
        return data;
    }


}
