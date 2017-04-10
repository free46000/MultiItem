package com.freelib.multiitem.demo.input;

import android.support.annotation.NonNull;
import android.view.View;

import com.freelib.multiitem.adapter.holder.ViewHolderManager;
import com.freelib.multiitem.item.ItemManager;

import java.util.Map;

/**
 * Created by free46000 on 2017/4/10.
 */
public interface ItemInput extends ItemManager {

    @Override
    InputHolderManager getViewHolderManager();

}
