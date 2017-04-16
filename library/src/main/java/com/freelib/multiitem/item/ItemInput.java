package com.freelib.multiitem.item;

import com.freelib.multiitem.adapter.holder.InputHolderManager;

/**
 * 录入Input Item接口
 * Created by free46000 on 2017/4/10.
 */
public interface ItemInput extends ItemManager {
    /**
     * 返回InputHolderManager
     *
     * @return InputHolderManager
     * @see InputHolderManager
     */
    @Override
    InputHolderManager getViewHolderManager();

}
