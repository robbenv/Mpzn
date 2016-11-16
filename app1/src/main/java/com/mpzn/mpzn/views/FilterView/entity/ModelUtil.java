package com.mpzn.mpzn.views.FilterView.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 好吧，让你找到了，这是数据源
 *
 * Created by sunfusheng on 16/4/22.
 */
public class ModelUtil {

    public static List<BuildingEntity> buildingList=new ArrayList<>() ;

    public static List<BuildingEntity> defultbuildingList;

    public static final String type_all = "全部";
    public static final String type_zhuzhai = "住宅";
    public static final String type_bieshu = "别墅";
    public static final String type_gongyu = "公寓";
    public static final String type_shangpuloupan = "商铺";
    public static final String type_xiezilouloupan = "写字楼";



    // ListView数据
    public  static void setBuildingData(List<BuildingEntity> buildingdata) {
        defultbuildingList=buildingdata;

        buildingList.clear();
        buildingList.addAll(buildingdata);
    }



    // ListView数据
    public static List<BuildingEntity> getBuildingData() {

    return buildingList;
  }

    // 分类数据
    public static List<FilterTwoEntity> getCategoryData() {
        List<FilterTwoEntity> list = new ArrayList<>();
        List<FilterEntity> filterData = getCategory2rdData();
        list.add(new FilterTwoEntity(type_all, filterData));
        list.add(new FilterTwoEntity(type_zhuzhai, filterData));
        list.add(new FilterTwoEntity(type_bieshu, filterData));
        list.add(new FilterTwoEntity(type_gongyu, filterData));
        list.add(new FilterTwoEntity(type_shangpuloupan, filterData));
        list.add(new FilterTwoEntity(type_xiezilouloupan, filterData));
        return list;
    }

    // 排序数据
    public static List<FilterEntity> getSortData() {
        List<FilterEntity> list = new ArrayList<>();
        list.add(new FilterEntity("智能排序", "1"));
        list.add(new FilterEntity("价格从低到高", "2"));
        list.add(new FilterEntity("价格从高到低", "3"));
        list.add(new FilterEntity("浏览量从高到低", "4"));



        return list;
    }

    // 分类二级数据
    public static List<FilterEntity> getCategory2rdData() {
        List<FilterEntity> list = new ArrayList<>();

        list.add(new FilterEntity("不限", "1"));
        list.add(new FilterEntity("徐汇", "2"));
        list.add(new FilterEntity("长宁", "3"));
        list.add(new FilterEntity("闵行", "4"));
        list.add(new FilterEntity("宝山", "5"));
        list.add(new FilterEntity("青浦", "6"));
        list.add(new FilterEntity("崇明", "7"));
        list.add(new FilterEntity("闸北", "8"));
        list.add(new FilterEntity("奉贤", "9"));
        list.add(new FilterEntity("杨浦", "10"));
        list.add(new FilterEntity("嘉定", "11"));
        list.add(new FilterEntity("普陀", "12"));
        list.add(new FilterEntity("金山", "13"));
        list.add(new FilterEntity("浦东", "14"));
        list.add(new FilterEntity("松江", "15"));
        list.add(new FilterEntity("虹口", "16"));
        list.add(new FilterEntity("黄浦", "17"));
        list.add(new FilterEntity("静安", "18"));
        list.add(new FilterEntity("卢湾", "19"));
        list.add(new FilterEntity("上海周边", "20"));
        list.add(new FilterEntity("其他", "21"));

        return list;
    }

    // 筛选数据
    public static List<FilterEntity> getFilterData() {
        List<FilterEntity> list = new ArrayList<>();

        list.add(new FilterEntity("不限", "1"));
        list.add(new FilterEntity("现房", "2"));
        list.add(new FilterEntity("期房", "3"));
        list.add(new FilterEntity("待售", "4"));


        return list;
    }

    // ListView分类数据
    public static List<BuildingEntity> getCategoryBuildingData(FilterTwoEntity entity) {
        List<BuildingEntity> list = getBuildingData();
        List<BuildingEntity> BuildingList = new ArrayList<>();
        int size = list.size();
        if(entity.getType().equals("全部")){
            if(entity.getSelectedFilterEntity().getKey().equals("不限")){
                BuildingList.addAll(list);
            }else {
                for (int i = 0; i < size; i++) {
                    if (list.get(i).getDiqu().equals(entity.getSelectedFilterEntity().getKey())) {
                        BuildingList.add(list.get(i));
                    }
                }
            }

        }else{
            if(entity.getSelectedFilterEntity().getKey().equals("不限")){
                for (int i = 0; i < size; i++) {
                    if (list.get(i).getTag().getWylxarray().equals(entity.getType())) {
                        BuildingList.add(list.get(i));
                    }

                }
            }else {
                for (int i = 0; i < size; i++) {
                    if (list.get(i).getTag().getWylxarray().equals(entity.getType()) &&
                            list.get(i).getDiqu().equals(entity.getSelectedFilterEntity().getKey())) {
                        BuildingList.add(list.get(i));
                    }

                }
            }

        }
        return BuildingList;
    }

    // ListView排序数据
    public static List<BuildingEntity> getSortBuildingData(FilterEntity entity) {
        List<BuildingEntity> list = getBuildingData();
        Comparator<BuildingEntity> ascComparator = new BuildingEntityPriceComparator();
        
        Comparator<BuildingEntity> ascComparator2 = new BuildingEntityClickComparator();

        if(entity.getKey().equals("智能排序")){
            list = defultbuildingList;
        } else if (entity.getKey().equals("价格从低到高")) {
            Collections.sort(list);
        } else if(entity.getKey().equals("价格从高到低")){
            Collections.sort(list, ascComparator);
        } else if(entity.getKey().equals("浏览量从高到低")){
            Collections.sort(list, ascComparator2);
        }
        return list;
    }
    // ListView筛选数据
    public static List<BuildingEntity> getFilterBuildingData(FilterEntity entity) {
        List<BuildingEntity> list = getBuildingData();
        List<BuildingEntity> buildingList = new ArrayList<>();
        if(!"不限".equals(entity.getKey())) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (list.get(i).getTitle().equals(entity.getKey())) {
                    buildingList.add(list.get(i));
                }
            }
        }else{
            buildingList.addAll(list);
        }
        return buildingList;
    }


    // 暂无数据
    public static List<BuildingEntity> getNoDataEntity(int height) {
        List<BuildingEntity> list = new ArrayList<>();
        BuildingEntity entity = new BuildingEntity();
        entity.setNoData(true);
        entity.setHeight(height);
        list.add(entity);
        return list;
    }

}
