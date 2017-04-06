package com.freelib.multiitem.demo.viewholder;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.ViewHolderManager;
import com.freelib.multiitem.demo.viewholder.DataBindViewHolderManager.DataBindViewHolder;

/**
 * Created by free46000 on 2017/4/6.
 */
public class DataBindViewHolderManager<T> extends ViewHolderManager<T, DataBindViewHolder> {
    @LayoutRes
    private int itemLayoutId;
    private ItemBindView<T> itemBindView;

    /**
     * @param itemLayoutId item 布局文件资源id
     * @param itemBindView item数据绑定回调
     */
    public DataBindViewHolderManager(@LayoutRes int itemLayoutId, @NonNull ItemBindView<T> itemBindView) {
        this.itemLayoutId = itemLayoutId;
        this.itemBindView = itemBindView;
    }

    @NonNull
    @Override
    public DataBindViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new DataBindViewHolder(getItemViewBinding(parent));
    }

    /**
     * 生成ViewDataBinding
     *
     * @param viewGroup ViewGroup
     * @return ViewDataBinding
     */
    protected ViewDataBinding getItemViewBinding(ViewGroup viewGroup) {
        return DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                getItemLayoutId(), viewGroup, false);
    }

    @Override
    public void onBindViewHolder(@NonNull DataBindViewHolder holder, @NonNull T data) {
        onBindViewHolder(holder.dataBinding, data);
        holder.dataBinding.executePendingBindings();
    }

    protected void onBindViewHolder(ViewDataBinding dataBinding, T data) {
        itemBindView.onBindViewHolder(dataBinding, data);
    }

    @Override
    protected int getItemLayoutId() {
        return itemLayoutId;
    }

    static class DataBindViewHolder extends BaseViewHolder {
        public ViewDataBinding dataBinding;

        public DataBindViewHolder(ViewDataBinding dataBinding) {
            super(dataBinding.getRoot());
            this.dataBinding = dataBinding;
        }
    }

    @FunctionalInterface
    public interface ItemBindView<T> {
        void onBindViewHolder(ViewDataBinding dataBinding, T data);
    }
}
