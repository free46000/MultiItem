package com.freelib.multiitem.item;

import com.freelib.multiitem.adapter.holder.InputHolderManager;

/**
 * Created by free46000 on 2017/4/10.
 */
public interface ItemInput extends ItemManager {

    @Override
    InputHolderManager getViewHolderManager();

}
