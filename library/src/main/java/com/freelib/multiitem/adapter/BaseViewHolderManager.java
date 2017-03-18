package com.freelib.multiitem.adapter;

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

    /**
     * @return layout资源id
     */
    @LayoutRes
    protected abstract int getItemLayoutId();

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        View itemView = getItemView(getItemLayoutId(), parent);
        BaseViewHolder viewHolder = new BaseViewHolder(itemView);
        onCreateViewHolder(viewHolder);
        return viewHolder;
    }

    /**
     * {@link #onCreateViewHolder}
     */
    protected void onCreateViewHolder(@NonNull BaseViewHolder holder) {
    }

}
