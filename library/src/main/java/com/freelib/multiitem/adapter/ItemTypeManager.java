package com.freelib.multiitem.adapter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


/**
 * item类型管理类
 * 管理不同数据源和ViewHolder的关系，为RecyclerView多类型视图提供支持
 *
 * @author free46000  2017/03/15
 * @version v1.0
 */
public class ItemTypeManager {
    protected List<String> itemClassNames = new ArrayList<>();
    protected List<ViewHolderManager> viewHolderManagers = new ArrayList<>();

    public void register(@NonNull Class<?> cls, @NonNull ViewHolderManager provider) {
        String className = getClassName(cls);
        if (itemClassNames.contains(className)) {
            viewHolderManagers.set(itemClassNames.indexOf(className), provider);
        } else {
            itemClassNames.add(className);
            viewHolderManagers.add(provider);
        }
    }

    /**
     * @param cls data item class
     * @return -1 如果没找到；
     */
    public int getItemType(@NonNull Class<?> cls) {
        return itemClassNames.indexOf(getClassName(cls));
    }

    public List<ViewHolderManager> getViewHolderManagers() {
        return viewHolderManagers;
    }

    public List<String> getItemClassNames() {
        return itemClassNames;
    }

    public ViewHolderManager getProvider(int type) {
        if (type < 0 || type > viewHolderManagers.size() - 1)
            return null;

        return viewHolderManagers.get(type);
    }

    public ViewHolderManager getProvider(@NonNull Class<?> cls) {
        return getProvider(getItemType(cls));
    }

    private String getClassName(Class<?> cls) {
        return cls.getName();
    }

}
