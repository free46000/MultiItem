package com.freelib.multiitem.adapter.holder;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

/**
 * ViewHolder的管理类，默认采用{@link BaseViewHolder}的实现
 * <p>
 * Created by free46000 on 2017/3/16.
 */
public abstract class BaseViewHolderManager<T> extends ViewHolderManager<T, BaseViewHolder> {
    @Override
    public abstract void onBindViewHolder(@NonNull BaseViewHolder holder, @NonNull T t);

    @Override
    protected abstract int getItemLayoutId();

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        BaseViewHolder viewHolder = new BaseViewHolder(getItemView(parent));
        onCreateViewHolder(viewHolder);
        return viewHolder;
    }

    /**
     * {@link #onCreateViewHolder}
     */
    protected void onCreateViewHolder(@NonNull BaseViewHolder holder) {
    }

}
