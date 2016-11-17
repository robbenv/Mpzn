package com.mpzn.mpzn.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Icy on 2016/9/5.
 */
public class BuildingFilterEntity implements Parcelable {

    /**
     * code : 200
     * data : {"qyarray":[{"ccid":"5012","title":"徐汇"},{"ccid":"5013","title":"长宁"},{"ccid":"5014","title":"闵行"},{"ccid":"5011","title":"宝山"},{"ccid":"5010","title":"青浦"},{"ccid":"5009","title":"崇明"},{"ccid":"5007","title":"闸北"},{"ccid":"5008","title":"奉贤"},{"ccid":"5006","title":"杨浦"},{"ccid":"5005","title":"嘉定"},{"ccid":"5004","title":"普陀"},{"ccid":"5002","title":"金山"},{"ccid":"5003","title":"浦东"},{"ccid":"5001","title":"松江"},{"ccid":"5015","title":"虹口"},{"ccid":"5016","title":"黄浦"},{"ccid":"5017","title":"静安"},{"ccid":"5018","title":"卢湾"},{"ccid":"5021","title":"上海周边"},{"ccid":"5022","title":"其他"}],"tslparray":[{"tslp":"打折优惠","tslpid":1},{"tslp":"小户型投资","tslpid":2},{"tslp":"教育地产","tslpid":3},{"tslp":"旅游地产","tslpid":4},{"tslp":"宜居生态","tslpid":5},{"tslp":"低密居所","tslpid":6},{"tslp":"公园地产","tslpid":7},{"tslp":"小户型投资","tslpid":8},{"tslp":"综合体","tslpid":9}],"wylxarray":[{"tslp":"毛坯","tslpid":3},{"tslp":"别墅","tslpid":4},{"tslp":"商住","tslpid":3032},{"tslp":"写字楼","tslpid":5001},{"tslp":"商铺","tslpid":5002}],"zxcdarray":[{"tslp":"住宅","tslpid":1},{"tslp":"简易装修","tslpid":2},{"tslp":"中档装修","tslpid":3},{"tslp":"全装修","tslpid":4},{"tslp":"豪华装修","tslpid":5}]}
     * message : success
     */

    private int code;
    private DataEntity data;
    private String message;

    protected BuildingFilterEntity(Parcel in) {
        code = in.readInt();
        message = in.readString();
    }

    public static final Creator<BuildingFilterEntity> CREATOR = new Creator<BuildingFilterEntity>() {
        @Override
        public BuildingFilterEntity createFromParcel(Parcel in) {
            return new BuildingFilterEntity(in);
        }

        @Override
        public BuildingFilterEntity[] newArray(int size) {
            return new BuildingFilterEntity[size];
        }
    };

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMessage() {
        return message;
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

    public static class DataEntity implements Parcelable{
        /**
         * ccid : 5012
         * title : 徐汇
         */

        private List<QyarrayEntity> qyarray;
        /**
         * tslp : 打折优惠
         * tslpid : 1
         */

        private List<TslparrayEntity> tslparray;
        /**
         * tslp : 毛坯
         * tslpid : 3
         */

        private List<WylxarrayEntity> wylxarray;
        /**
         * tslp : 住宅
         * tslpid : 1
         */

        private List<ZxcdarrayEntity> zxcdarray;

        protected DataEntity(Parcel in) {
        }

        public static final Creator<DataEntity> CREATOR = new Creator<DataEntity>() {
            @Override
            public DataEntity createFromParcel(Parcel in) {
                return new DataEntity(in);
            }

            @Override
            public DataEntity[] newArray(int size) {
                return new DataEntity[size];
            }
        };

        public void setQyarray(List<QyarrayEntity> qyarray) {
            this.qyarray = qyarray;
        }

        public void setTslparray(List<TslparrayEntity> tslparray) {
            this.tslparray = tslparray;
        }

        public void setWylxarray(List<WylxarrayEntity> wylxarray) {
            this.wylxarray = wylxarray;
        }

        public void setZxcdarray(List<ZxcdarrayEntity> zxcdarray) {
            this.zxcdarray = zxcdarray;
        }

        public List<QyarrayEntity> getQyarray() {
            return qyarray;
        }

        public List<TslparrayEntity> getTslparray() {
            return tslparray;
        }

        public List<WylxarrayEntity> getWylxarray() {
            return wylxarray;
        }

        public List<ZxcdarrayEntity> getZxcdarray() {
            return zxcdarray;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }

        public static class QyarrayEntity implements Parcelable{
            private String ccid;
            private String title;

            protected QyarrayEntity(Parcel in) {
                ccid = in.readString();
                title = in.readString();
            }

            public static final Creator<QyarrayEntity> CREATOR = new Creator<QyarrayEntity>() {
                @Override
                public QyarrayEntity createFromParcel(Parcel in) {
                    return new QyarrayEntity(in);
                }

                @Override
                public QyarrayEntity[] newArray(int size) {
                    return new QyarrayEntity[size];
                }
            };

            public void setCcid(String ccid) {
                this.ccid = ccid;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCcid() {
                return ccid;
            }

            public String getTitle() {
                return title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(ccid);
                dest.writeString(title);
            }
        }

        public static class TslparrayEntity implements Parcelable{
            private String tslp;
            private int tslpid;

            protected TslparrayEntity(Parcel in) {
                tslp = in.readString();
                tslpid = in.readInt();
            }

            public static final Creator<TslparrayEntity> CREATOR = new Creator<TslparrayEntity>() {
                @Override
                public TslparrayEntity createFromParcel(Parcel in) {
                    return new TslparrayEntity(in);
                }

                @Override
                public TslparrayEntity[] newArray(int size) {
                    return new TslparrayEntity[size];
                }
            };

            public void setTslp(String tslp) {
                this.tslp = tslp;
            }

            public void setTslpid(int tslpid) {
                this.tslpid = tslpid;
            }

            public String getTslp() {
                return tslp;
            }

            public int getTslpid() {
                return tslpid;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(tslp);
                dest.writeInt(tslpid);
            }
        }

        public static class WylxarrayEntity implements Parcelable{
            private String tslp;
            private int tslpid;

            protected WylxarrayEntity(Parcel in) {
                tslp = in.readString();
                tslpid = in.readInt();
            }

            public static final Creator<WylxarrayEntity> CREATOR = new Creator<WylxarrayEntity>() {
                @Override
                public WylxarrayEntity createFromParcel(Parcel in) {
                    return new WylxarrayEntity(in);
                }

                @Override
                public WylxarrayEntity[] newArray(int size) {
                    return new WylxarrayEntity[size];
                }
            };

            public void setTslp(String tslp) {
                this.tslp = tslp;
            }

            public void setTslpid(int tslpid) {
                this.tslpid = tslpid;
            }

            public String getTslp() {
                return tslp;
            }

            public int getTslpid() {
                return tslpid;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(tslp);
                dest.writeInt(tslpid);
            }
        }

        public static class ZxcdarrayEntity implements Parcelable{
            private String tslp;
            private int tslpid;

            protected ZxcdarrayEntity(Parcel in) {
                tslp = in.readString();
                tslpid = in.readInt();
            }

            public static final Creator<ZxcdarrayEntity> CREATOR = new Creator<ZxcdarrayEntity>() {
                @Override
                public ZxcdarrayEntity createFromParcel(Parcel in) {
                    return new ZxcdarrayEntity(in);
                }

                @Override
                public ZxcdarrayEntity[] newArray(int size) {
                    return new ZxcdarrayEntity[size];
                }
            };

            public void setTslp(String tslp) {
                this.tslp = tslp;
            }

            public void setTslpid(int tslpid) {
                this.tslpid = tslpid;
            }

            public String getTslp() {
                return tslp;
            }

            public int getTslpid() {
                return tslpid;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(tslp);
                dest.writeInt(tslpid);
            }
        }
    }
}