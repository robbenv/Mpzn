package com.mpzn.mpzn.views.FilterView.entity;

import java.util.Comparator;

/**
 * Created by sunfusheng on 16/4/25.
 */
public class BuildingEntityPriceComparator implements Comparator<BuildingEntity> {

    @Override
    public int compare(BuildingEntity lhs, BuildingEntity rhs) {
        return rhs.getDj() - lhs.getDj();
    }
}
