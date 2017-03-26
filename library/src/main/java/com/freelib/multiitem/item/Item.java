package com.freelib.multiitem.item;

import com.freelib.multiitem.adapter.holder.ViewHolderManager;

/**
 * 自定义Item类
 * Created by free46000 on 2017/3/26.
 */
public interface Item {
    String getItemTypeName();

    ViewHolderManager getViewHolderManager();
}
