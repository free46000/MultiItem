package com.freelib.multiitem.item;

import android.support.annotation.NonNull;

import com.freelib.multiitem.adapter.holder.ViewHolderManager;

/**
 * 自定义Item管理类 可以获取item类型标识和ViewHolderManager
 * Created by free46000 on 2017/3/26.
 */
public interface ItemManager {
    @NonNull
    String getItemTypeName();

    @NonNull
    ViewHolderManager getViewHolderManager();
}
