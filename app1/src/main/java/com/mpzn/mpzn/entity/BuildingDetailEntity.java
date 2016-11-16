package com.mpzn.mpzn.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Icy on 2016/9/5.
 */
public class BuildingDetailEntity implements Parcelable{


    /**
     * code : 200
     * data : [{"abstract":"虹桥天街---虹桥中岛特色商业街 虹桥天街地处虹桥商务区核心区一期核心的地段，紧邻区域内两大功能性项目\u2014\u2014虹桥高铁站西侧400米、国家会展中心东侧300米。 沃宝天街共有一站式中高端购物中心、临河5A甲级写字 ...","aid":3914,"ccid":5002,"cksm":"","content":"虹桥天街---虹桥中岛特色商业街虹桥天街地处虹桥商务区核心区一期核心的地段，紧邻区域内两大功能性项目\u2014\u2014虹桥高铁站西侧400米、国家会展中心东侧300米。虹桥天街共有一站式中高端购物中心、临河5A甲级写字楼、绿色生态景观商业街、奢华精品酒店等4种业态形式组成，总建筑面积达43万方。虹桥天街虹桥中岛特色商业街是总体量为8014.06平方米的商业综合体。","diqu":"闵行","diquid":5014,"dj":88000,"dt_0":31.197583,"dt_1":121.319334,"jdjj":82500,"jfrq":"2017年06月","jtxl":"","kfsname":"上海恒骏房地产有限公司","kprq":"2016年06月30日","leixing":"楼盘","lhl":"35%","lppmt":[""],"morebuilding":[{"aid":62,"ccid":3,"diqu":"奉贤","dj":"14000","subject":"绿地小米公社","thumb":"http://mpzn8.mpzn.com/userfiles/image/20151229/290929526cf4a6691b0217.jpg#867#578","wylx":"住宅"},{"aid":2135,"ccid":4,"diqu":"松江","dj":"","subject":"世茂佘山里","thumb":"http://mpzn8.mpzn.com/userfiles/image/20160129/2915394227fc08a7ab8640.png#769#482","wylx":"别墅"},{"aid":254,"ccid":3,"diqu":"嘉定","dj":"18000","subject":"佳兆业城市广场","thumb":"http://mpzn8.mpzn.com/userfiles/image/20160106/061739402155f1a2b33392.jpg#770#578","wylx":"住宅"},{"aid":1918,"ccid":3,"diqu":"浦东","dj":"40000","subject":"尼德兰花园二期","thumb":"http://mpzn8.mpzn.com/userfiles/image/20160127/27142004754c7cfd1a5346.png#764#510","wylx":"住宅"},{"aid":1766,"ccid":3,"diqu":"青浦","dj":"2350","subject":"富绅时代公馆","thumb":"http://mpzn8.mpzn.com/userfiles/image/20160121/211116130991e6d12b4248.png#792#517","wylx":"住宅"},{"aid":88,"ccid":3,"diqu":"普陀","dj":"50000","subject":"智富中环尚城","thumb":"http://mpzn8.mpzn.com/userfiles/image/20151230/3014395918111299651556.jpg#770#578","wylx":"住宅"}],"sldz":"甬虹路188弄1号楼122室","subject":"虹桥天街","tag":["毛坯","综合体",null,"商铺"],"thumb":"http://mpzn8.mpzn.com/userfiles/image/20160630/30104715a7f3b3cad86936.jpg","title":"商铺","toppic":["http://mpzn8.mpzn.com/userfiles/image/20160324/2409524602f8b14b6f6761.jpg#258#258","http://mpzn8.mpzn.com/userfiles/image/20160329/29171458471970501b0113.jpg#860#559","http://mpzn8.mpzn.com/userfiles/image/20160329/29171459d029154c431557.jpg#860#532","http://mpzn8.mpzn.com/userfiles/image/20160329/291715000afa8271336583.jpg#860#645","http://mpzn8.mpzn.com/userfiles/image/20160329/29171502b9b061cb238947.jpg#860#1032","http://mpzn8.mpzn.com/userfiles/image/20160329/291715033a88649ebc2034.jpg#860#581","http://mpzn8.mpzn.com/userfiles/image/20160329/291715047329dc7efc9458.jpg#860#552","http://mpzn8.mpzn.com/userfiles/image/20160329/29171505200ec918a04090.jpg#860#516","http://mpzn8.mpzn.com/userfiles/image/20160329/291715068c2aa09b828539.jpg#860#559","http://mpzn8.mpzn.com/userfiles/image/20160329/29171508fc35f603c88935.jpg#860#509","http://mpzn8.mpzn.com/userfiles/image/20160329/291715092fff98d01d9010.jpg#860#478","http://mpzn8.mpzn.com/userfiles/image/20160630/30104657f8db723ebf5213.jpg#4962#2791","http://mpzn8.mpzn.com/userfiles/image/20160630/30104704fc4d3aa9781792.jpg#2205#1433","http://mpzn8.mpzn.com/userfiles/image/20160630/30104708d8c98325d33814.jpg#2894#1447","http://mpzn8.mpzn.com/userfiles/image/20160630/30104715a7f3b3cad86936.jpg#2480#1910","http://mpzn8.mpzn.com/userfiles/image/20160630/3010471936f5674dec8764.jpg#1890#945"],"tslp":"9","wyf":"30 元/平方米/月 ","wygs":"龙湖物业","xkzh":"闵行房管（2016）预字0000212号","xqss":"","yjl":"3.19","zxcd":1}]
     * message : success
     */

    private int code;
    private String message;
    /**
     * abstract : 虹桥天街---虹桥中岛特色商业街 虹桥天街地处虹桥商务区核心区一期核心的地段，紧邻区域内两大功能性项目——虹桥高铁站西侧400米、国家会展中心东侧300米。 沃宝天街共有一站式中高端购物中心、临河5A甲级写字 ...
     * aid : 3914
     * ccid : 5002
     * cksm :
     * content : 虹桥天街---虹桥中岛特色商业街虹桥天街地处虹桥商务区核心区一期核心的地段，紧邻区域内两大功能性项目——虹桥高铁站西侧400米、国家会展中心东侧300米。虹桥天街共有一站式中高端购物中心、临河5A甲级写字楼、绿色生态景观商业街、奢华精品酒店等4种业态形式组成，总建筑面积达43万方。虹桥天街虹桥中岛特色商业街是总体量为8014.06平方米的商业综合体。
     * diqu : 闵行
     * diquid : 5014
     * dj : 88000
     * dt_0 : 31.197583
     * dt_1 : 121.319334
     * jdjj : 82500
     * jfrq : 2017年06月
     * jtxl :
     * kfsname : 上海恒骏房地产有限公司
     * kprq : 2016年06月30日
     * leixing : 楼盘
     * lhl : 35%
     * lppmt : [""]
     * morebuilding : [{"aid":62,"ccid":3,"diqu":"奉贤","dj":"14000","subject":"绿地小米公社","thumb":"http://mpzn8.mpzn.com/userfiles/image/20151229/290929526cf4a6691b0217.jpg#867#578","wylx":"住宅"},{"aid":2135,"ccid":4,"diqu":"松江","dj":"","subject":"世茂佘山里","thumb":"http://mpzn8.mpzn.com/userfiles/image/20160129/2915394227fc08a7ab8640.png#769#482","wylx":"别墅"},{"aid":254,"ccid":3,"diqu":"嘉定","dj":"18000","subject":"佳兆业城市广场","thumb":"http://mpzn8.mpzn.com/userfiles/image/20160106/061739402155f1a2b33392.jpg#770#578","wylx":"住宅"},{"aid":1918,"ccid":3,"diqu":"浦东","dj":"40000","subject":"尼德兰花园二期","thumb":"http://mpzn8.mpzn.com/userfiles/image/20160127/27142004754c7cfd1a5346.png#764#510","wylx":"住宅"},{"aid":1766,"ccid":3,"diqu":"青浦","dj":"2350","subject":"富绅时代公馆","thumb":"http://mpzn8.mpzn.com/userfiles/image/20160121/211116130991e6d12b4248.png#792#517","wylx":"住宅"},{"aid":88,"ccid":3,"diqu":"普陀","dj":"50000","subject":"智富中环尚城","thumb":"http://mpzn8.mpzn.com/userfiles/image/20151230/3014395918111299651556.jpg#770#578","wylx":"住宅"}]
     * sldz : 甬虹路188弄1号楼122室
     * subject : 虹桥天街
     * tag : ["毛坯","综合体",null,"商铺"]
     * thumb : http://mpzn8.mpzn.com/userfiles/image/20160630/30104715a7f3b3cad86936.jpg
     * title : 商铺
     * toppic : ["http://mpzn8.mpzn.com/userfiles/image/20160324/2409524602f8b14b6f6761.jpg#258#258","http://mpzn8.mpzn.com/userfiles/image/20160329/29171458471970501b0113.jpg#860#559","http://mpzn8.mpzn.com/userfiles/image/20160329/29171459d029154c431557.jpg#860#532","http://mpzn8.mpzn.com/userfiles/image/20160329/291715000afa8271336583.jpg#860#645","http://mpzn8.mpzn.com/userfiles/image/20160329/29171502b9b061cb238947.jpg#860#1032","http://mpzn8.mpzn.com/userfiles/image/20160329/291715033a88649ebc2034.jpg#860#581","http://mpzn8.mpzn.com/userfiles/image/20160329/291715047329dc7efc9458.jpg#860#552","http://mpzn8.mpzn.com/userfiles/image/20160329/29171505200ec918a04090.jpg#860#516","http://mpzn8.mpzn.com/userfiles/image/20160329/291715068c2aa09b828539.jpg#860#559","http://mpzn8.mpzn.com/userfiles/image/20160329/29171508fc35f603c88935.jpg#860#509","http://mpzn8.mpzn.com/userfiles/image/20160329/291715092fff98d01d9010.jpg#860#478","http://mpzn8.mpzn.com/userfiles/image/20160630/30104657f8db723ebf5213.jpg#4962#2791","http://mpzn8.mpzn.com/userfiles/image/20160630/30104704fc4d3aa9781792.jpg#2205#1433","http://mpzn8.mpzn.com/userfiles/image/20160630/30104708d8c98325d33814.jpg#2894#1447","http://mpzn8.mpzn.com/userfiles/image/20160630/30104715a7f3b3cad86936.jpg#2480#1910","http://mpzn8.mpzn.com/userfiles/image/20160630/3010471936f5674dec8764.jpg#1890#945"]
     * tslp : 9
     * wyf : 30 元/平方米/月
     * wygs : 龙湖物业
     * xkzh : 闵行房管（2016）预字0000212号
     * xqss :
     * yjl : 3.19
     * zxcd : 1
     */

    private List<DataBean> data;

    protected BuildingDetailEntity(Parcel in) {
        code = in.readInt();
        message = in.readString();
    }

    public static final Creator<BuildingDetailEntity> CREATOR = new Creator<BuildingDetailEntity>() {
        @Override
        public BuildingDetailEntity createFromParcel(Parcel in) {
            return new BuildingDetailEntity(in);
        }

        @Override
        public BuildingDetailEntity[] newArray(int size) {
            return new BuildingDetailEntity[size];
        }
    };

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(message);
    }

    public static class DataBean  implements Parcelable{
        @SerializedName("abstract")
        private String abstractX;
        private int aid;
        private int ccid;
        private String cksm;
        private String content;
        private String diqu;
        private int diquid;
        private int dj;
        private double dt_0;
        private double dt_1;
        private int jdjj;
        private String jfrq;
        private String jtxl;
        private String kfsname;
        private String kprq;
        private String leixing;
        private String lhl;
        private String sldz;
        private String subject;
        private String thumb;
        private String title;
        private String tslp;
        private String wyf;
        private String wygs;
        private String xkzh;
        private String xqss;
        private String yjl;
        private int zxcd;
        private List<String> lppmt;
        /**
         * aid : 62
         * ccid : 3
         * diqu : 奉贤
         * dj : 14000
         * subject : 绿地小米公社
         * thumb : http://mpzn8.mpzn.com/userfiles/image/20151229/290929526cf4a6691b0217.jpg#867#578
         * wylx : 住宅
         */

        private List<MorebuildingBean> morebuilding;
        private List<String> tag;
        private List<String> toppic;

        protected DataBean(Parcel in) {
            abstractX = in.readString();
            aid = in.readInt();
            ccid = in.readInt();
            cksm = in.readString();
            content = in.readString();
            diqu = in.readString();
            diquid = in.readInt();
            dj = in.readInt();
            dt_0 = in.readDouble();
            dt_1 = in.readDouble();
            jdjj = in.readInt();
            jfrq = in.readString();
            jtxl = in.readString();
            kfsname = in.readString();
            kprq = in.readString();
            leixing = in.readString();
            lhl = in.readString();
            sldz = in.readString();
            subject = in.readString();
            thumb = in.readString();
            title = in.readString();
            tslp = in.readString();
            wyf = in.readString();
            wygs = in.readString();
            xkzh = in.readString();
            xqss = in.readString();
            yjl = in.readString();
            zxcd = in.readInt();
            lppmt = in.createStringArrayList();
            tag = in.createStringArrayList();
            toppic = in.createStringArrayList();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public String getAbstractX() {
            return abstractX;
        }

        public void setAbstractX(String abstractX) {
            this.abstractX = abstractX;
        }

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public int getCcid() {
            return ccid;
        }

        public void setCcid(int ccid) {
            this.ccid = ccid;
        }

        public String getCksm() {
            return cksm;
        }

        public void setCksm(String cksm) {
            this.cksm = cksm;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDiqu() {
            return diqu;
        }

        public void setDiqu(String diqu) {
            this.diqu = diqu;
        }

        public int getDiquid() {
            return diquid;
        }

        public void setDiquid(int diquid) {
            this.diquid = diquid;
        }

        public int getDj() {
            return dj;
        }

        public void setDj(int dj) {
            this.dj = dj;
        }

        public double getDt_0() {
            return dt_0;
        }

        public void setDt_0(double dt_0) {
            this.dt_0 = dt_0;
        }

        public double getDt_1() {
            return dt_1;
        }

        public void setDt_1(double dt_1) {
            this.dt_1 = dt_1;
        }

        public int getJdjj() {
            return jdjj;
        }

        public void setJdjj(int jdjj) {
            this.jdjj = jdjj;
        }

        public String getJfrq() {
            return jfrq;
        }

        public void setJfrq(String jfrq) {
            this.jfrq = jfrq;
        }

        public String getJtxl() {
            return jtxl;
        }

        public void setJtxl(String jtxl) {
            this.jtxl = jtxl;
        }

        public String getKfsname() {
            return kfsname;
        }

        public void setKfsname(String kfsname) {
            this.kfsname = kfsname;
        }

        public String getKprq() {
            return kprq;
        }

        public void setKprq(String kprq) {
            this.kprq = kprq;
        }

        public String getLeixing() {
            return leixing;
        }

        public void setLeixing(String leixing) {
            this.leixing = leixing;
        }

        public String getLhl() {
            return lhl;
        }

        public void setLhl(String lhl) {
            this.lhl = lhl;
        }

        public String getSldz() {
            return sldz;
        }

        public void setSldz(String sldz) {
            this.sldz = sldz;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTslp() {
            return tslp;
        }

        public void setTslp(String tslp) {
            this.tslp = tslp;
        }

        public String getWyf() {
            return wyf;
        }

        public void setWyf(String wyf) {
            this.wyf = wyf;
        }

        public String getWygs() {
            return wygs;
        }

        public void setWygs(String wygs) {
            this.wygs = wygs;
        }

        public String getXkzh() {
            return xkzh;
        }

        public void setXkzh(String xkzh) {
            this.xkzh = xkzh;
        }

        public String getXqss() {
            return xqss;
        }

        public void setXqss(String xqss) {
            this.xqss = xqss;
        }

        public String getYjl() {
            return yjl;
        }

        public void setYjl(String yjl) {
            this.yjl = yjl;
        }

        public int getZxcd() {
            return zxcd;
        }

        public void setZxcd(int zxcd) {
            this.zxcd = zxcd;
        }

        public List<String> getLppmt() {
            return lppmt;
        }

        public void setLppmt(List<String> lppmt) {
            this.lppmt = lppmt;
        }

        public List<MorebuildingBean> getMorebuilding() {
            return morebuilding;
        }

        public void setMorebuilding(List<MorebuildingBean> morebuilding) {
            this.morebuilding = morebuilding;
        }

        public List<String> getTag() {
            return tag;
        }

        public void setTag(List<String> tag) {
            this.tag = tag;
        }

        public List<String> getToppic() {
            return toppic;
        }

        public void setToppic(List<String> toppic) {
            this.toppic = toppic;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(abstractX);
            dest.writeInt(aid);
            dest.writeInt(ccid);
            dest.writeString(cksm);
            dest.writeString(content);
            dest.writeString(diqu);
            dest.writeInt(diquid);
            dest.writeInt(dj);
            dest.writeDouble(dt_0);
            dest.writeDouble(dt_1);
            dest.writeInt(jdjj);
            dest.writeString(jfrq);
            dest.writeString(jtxl);
            dest.writeString(kfsname);
            dest.writeString(kprq);
            dest.writeString(leixing);
            dest.writeString(lhl);
            dest.writeString(sldz);
            dest.writeString(subject);
            dest.writeString(thumb);
            dest.writeString(title);
            dest.writeString(tslp);
            dest.writeString(wyf);
            dest.writeString(wygs);
            dest.writeString(xkzh);
            dest.writeString(xqss);
            dest.writeString(yjl);
            dest.writeInt(zxcd);
            dest.writeStringList(lppmt);
            dest.writeStringList(tag);
            dest.writeStringList(toppic);
        }

        public static class MorebuildingBean {
            private int aid;
            private int ccid;
            private String diqu;
            private String dj;
            private String subject;
            private String thumb;
            private String wylx;

            public int getAid() {
                return aid;
            }

            public void setAid(int aid) {
                this.aid = aid;
            }

            public int getCcid() {
                return ccid;
            }

            public void setCcid(int ccid) {
                this.ccid = ccid;
            }

            public String getDiqu() {
                return diqu;
            }

            public void setDiqu(String diqu) {
                this.diqu = diqu;
            }

            public String getDj() {
                return dj;
            }

            public void setDj(String dj) {
                this.dj = dj;
            }

            public String getSubject() {
                return subject;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public String getWylx() {
                return wylx;
            }

            public void setWylx(String wylx) {
                this.wylx = wylx;
            }
        }
    }
}
