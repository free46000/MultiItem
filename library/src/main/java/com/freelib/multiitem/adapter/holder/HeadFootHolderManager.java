package com.freelib.multiitem.adapter.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

/**
 * Head Foot 的ViewHolderManager
 * <p>
 * Created by free46000 on 2017/3/25.
 */
public class HeadFootHolderManager<T> extends ViewHolderManager<T, BaseViewHolder> {
    private View itemView;

    public HeadFootHolderManager(View view) {
        this.itemView = view;
    }

    public HeadFootHolderManager() {

    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        BaseViewHolder viewHolder = new BaseViewHolder(getItemView(parent));
        return viewHolder;
    }

    @Override
    protected View getItemView(ViewGroup parent) {
        if (itemView != null) {
            return itemView;
        } else {
            return super.getItemView(parent);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, T t) {

    }

    @Override
    protected int getItemLayoutId() {
        return 0;
    }

    @Override
    public boolean isFullSpan() {
        return true;
    }
}
