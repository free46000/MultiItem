/*
 * Copyright 2016 drakeet. https://github.com/drakeet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.freelib.multiitem.adapter.holder;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freelib.multiitem.listener.OnItemClickListener;
import com.freelib.multiitem.listener.OnItemLongClickListener;

/**
 * ViewHolder的管理类
 * 需要继承此类，实现ViewHolder的创建{@link #onCreateViewHolder}与绑定{@link #onBindViewHolder}
 *
 * @author free46000
 */
public abstract class ViewHolderManager<T, V extends BaseViewHolder> {
    /**
     * 创建ViewHolder
     * {@link android.support.v7.widget.RecyclerView.Adapter#onCreateViewHolder}
     */
    @NonNull
    public abstract V onCreateViewHolder(@NonNull ViewGroup parent);

    /**
     * 为ViewHolder绑定数据
     * {@link android.support.v7.widget.RecyclerView.Adapter#onBindViewHolder}
     *
     * @param t 数据源
     */
    public abstract void onBindViewHolder(@NonNull V holder, @NonNull T t);

    /**
     * 为ViewHolder绑定数据，并根据params做出相应设置
     *
     * @param t      数据源
     * @param params {@link ViewHolderParams}
     */
    public void onBindViewHolder(@NonNull V holder, @NonNull T t, @NonNull ViewHolderParams params) {
        // TODO 如果以后有需要不直接在item view上设置Click事件，在MultiViewHolder增加itemHandlerView属性即可
        holder.itemView.setOnClickListener(params.getClickListener());
        holder.itemView.setOnLongClickListener(params.getLongClickListener());
        onBindViewHolder(holder, t);
    }


    public final int getPosition(@NonNull final ViewHolder holder) {
        return holder.getAdapterPosition();
    }

    /**
     * 通过资源id生成item view
     *
     * @param layoutId layout id
     * @param parent   onCreateViewHolder中的参数
     * @return 返回item view
     */
    protected View getItemView(@LayoutRes int layoutId, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
    }

    /**
     * 在指定view中获取控件为id的view
     *
     * @param view 外层view
     * @param id   需要获取view的控件id
     * @return view
     */
    protected <T extends View> T getView(View view, int id) {
        return (T) view.findViewById(id);
    }

    /**
     * 在指定viewHolder中获取控件为id的view
     *
     * @return view
     */
    protected <T extends View> T getView(ViewHolder viewHolder, int id) {
        return getView(viewHolder.itemView, id);
    }

}