package com.freelib.multiitem.item;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.ViewHolderManager;

/**
 * 唯一Item
 * <p>
 * getItemTypeName时返回toString作为唯一标示，使得本item对应的ViewHolderManager不可复用
 * Created by free46000 on 2017/3/26.
 */
public class UniqueItemManager implements ItemManager {
    private ViewHolderManager<? extends UniqueItemManager, ? extends BaseViewHolder> manager;

    public UniqueItemManager(ViewHolderManager<? extends UniqueItemManager, ? extends BaseViewHolder> manager) {
        this.manager = manager;
    }

    @Override
    public String getItemTypeName() {
        return toString();
    }

    @Override
    public ViewHolderManager getViewHolderManager() {
        return manager;
    }
}
