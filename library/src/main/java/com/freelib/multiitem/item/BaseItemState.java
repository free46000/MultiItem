package com.freelib.multiitem.item;

import android.support.annotation.NonNull;

import com.freelib.multiitem.adapter.holder.BindViewHolderManager;
import com.freelib.multiitem.adapter.holder.ViewHolderManager;
import com.freelib.multiitem.listener.OnStateClickListener;

/**
 * 基础的状态页Item（如：空白页 错误页...）
 * Created by free46000 on 2017/4/23.
 */
public abstract class BaseItemState<T extends BaseItemState> extends BindViewHolderManager<T> implements ItemManager {
    protected OnStateClickListener onStateClickListener;


    public OnStateClickListener getOnStateClickListener() {
        return onStateClickListener;
    }

    /**
     * 设置状态页面中按钮的点击监听
     *
     * @param onStateClickListener OnStateClickListener
     */
    public void setOnStateClickListener(OnStateClickListener onStateClickListener) {
        this.onStateClickListener = onStateClickListener;
    }

    @Override
    public ViewHolderManager getViewHolderManager() {
        return this;
    }


    @NonNull
    @Override
    public String getItemTypeName() {
        return toString();
    }


}
