package com.mpzn.mpzn.views.FilterView.entity;

import java.util.Comparator;

/**
 * Created by Icy on 2016/9/7.
 */
public class BuildingEntityClickComparator implements Comparator<BuildingEntity> {

        @Override
        public int compare(BuildingEntity lhs, BuildingEntity rhs) {
            return rhs.getClicks()-lhs.getClicks();
        }

}
