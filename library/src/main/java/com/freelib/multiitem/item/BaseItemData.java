package com.freelib.multiitem.item;

import android.view.View;

/**
 * Created by free46000 on 2017/4/3.
 */
public class BaseItemData implements ItemData {
    protected int visibility = View.VISIBLE;

    @Override
    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    @Override
    public int getVisibility() {
        return visibility;
    }
}
