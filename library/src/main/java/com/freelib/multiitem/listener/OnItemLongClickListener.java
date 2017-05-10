package com.freelib.multiitem.listener;

import android.view.View;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;


/**
 * Item长按监听类
 *
 * @author free46000
 */
public abstract class OnItemLongClickListener implements View.OnLongClickListener {

    @Override
    public boolean onLongClick(View v) {
        BaseViewHolder viewHolder = ListenerUtil.getViewHolderByItemView(v);
        if (viewHolder == null) {
            return false;
        }

        onItemLongClick(viewHolder);
        return true;
    }

    /**
     * 点击回调 可以通过viewHolder get到需要的数据
     */
    protected abstract void onItemLongClick(BaseViewHolder viewHolder);

}
