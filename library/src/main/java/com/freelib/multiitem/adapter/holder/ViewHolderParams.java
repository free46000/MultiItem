package com.freelib.multiitem.adapter.holder;

import com.freelib.multiitem.listener.OnItemClickListener;
import com.freelib.multiitem.listener.OnItemLongClickListener;

/**
 * Created by free46000 on 2017/3/20.
 */

public class ViewHolderParams {
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;
    private int itemCount;
    private int listViewScrollState;

    public ViewHolderParams setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
        return this;
    }

    public ViewHolderParams setLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
        return this;
    }


    public ViewHolderParams setItemCount(int itemCount) {
        this.itemCount = itemCount;
        return this;
    }

    public ViewHolderParams setListViewScrollState(int listViewScrollState) {
        this.listViewScrollState = listViewScrollState;
        return this;
    }

    public OnItemClickListener getClickListener() {
        return clickListener;
    }

    public OnItemLongClickListener getLongClickListener() {
        return longClickListener;
    }

    public int getItemCount() {
        return itemCount;
    }

    public int getListViewScrollState() {
        return listViewScrollState;
    }


}
