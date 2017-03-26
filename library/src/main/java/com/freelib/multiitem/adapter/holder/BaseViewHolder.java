package com.freelib.multiitem.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * RecyclerView的view holder
 * 增加一些属性和方法，方便使用
 *
 * @author free46000  2017/03/16
 * @version v1.0
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    public ViewHolderManager viewHolderManager;
    public Object itemData;


    public BaseViewHolder(View itemView) {
        super(itemView);
    }


    public Object getItemData() {
        return itemData;
    }

    /**
     * header和footer的个数也计算在内
     * {@link #getAdapterPosition()}
     */
    public int getItemPosition() {
        return getAdapterPosition();
    }

    public ViewHolderManager getViewHolderManager() {
        return viewHolderManager;
    }

}
