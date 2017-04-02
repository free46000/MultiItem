package com.freelib.multiitem.item;

import android.view.View;

/**
 * Created by free46000 on 2017/4/3.
 */
public interface ItemData {

    /**
     * {@link View#setVisibility(int)}
     *
     * @param visibility @Visibility
     */
    void setVisibility(int visibility);

    /**
     * {@link View#getVisibility()}
     *
     * @return @Visibility
     */
    int getVisibility();
}
