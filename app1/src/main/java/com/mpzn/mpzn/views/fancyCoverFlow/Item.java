package com.mpzn.mpzn.views.fancyCoverFlow;

import com.bm.library.PhotoView;

/**
 * Created by Icy on 2016/8/12.
 */
public class Item {
    private PhotoView img;
    private boolean isSelected;

    public Item(PhotoView imgUrl,boolean isSelected) {
        this.img = imgUrl;
        this.isSelected=isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setimg(PhotoView img) {
        this.img = img;
    }

    public PhotoView getimg() {
        return img;
    }
}