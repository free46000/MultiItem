package com.freelib.multiitem.adapter.type;

import android.support.annotation.NonNull;

import com.freelib.multiitem.adapter.holder.ViewHolderManager;
import com.freelib.multiitem.adapter.holder.ViewHolderManagerGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * item类型管理类
 * 管理不同数据源和ViewHolder的关系，为RecyclerView多类型视图提供支持
 *
 * @author free46000  2017/03/15
 * @version v1.0
 */
public class ItemTypeManager {
    protected Map<String, ViewHolderManagerGroup> itemClassNameGroupMap = new HashMap<>();
    protected List<String> itemClassNames = new ArrayList<>();
    protected List<ViewHolderManager> viewHolderManagers = new ArrayList<>();

    /**
     * 通过数据源`className List`和`viewHolderManager List`两组集合对类型进行管理
     *
     * @param cls     数据源class
     * @param manager ViewHolderManager
     * @see com.freelib.multiitem.adapter.BaseItemAdapter#register(Class, ViewHolderManager)
     */
    public void register(Class<?> cls, ViewHolderManager manager) {
        register(getClassName(cls), manager);
    }

    /**
     * 通过group获取一组ViewHolderManager循环注册，并生成不同的className作为标识<br>
     * 其他类似{@link #register(Class, ViewHolderManager)}
     *
     * @param cls   数据源class
     * @param group 多个ViewHolderManager的组合
     * @see com.freelib.multiitem.adapter.BaseItemAdapter#register(Class, ViewHolderManagerGroup)
     */
    public void register(Class<?> cls, ViewHolderManagerGroup group) {
        ViewHolderManager[] managers = group.getViewHolderManagers();
        for (int i = 0, length = managers.length; i < length; i++) {
            register(getClassNameFromGroup(cls, group, managers[i]), managers[i]);
        }
        itemClassNameGroupMap.put(getClassName(cls), group);
    }

    private void register(String className, ViewHolderManager manager) {
        if (itemClassNames.contains(className)) {
            viewHolderManagers.set(itemClassNames.indexOf(className), manager);
        } else {
            itemClassNames.add(className);
            viewHolderManagers.add(manager);
        }
    }

    /**
     * @param itemData data item
     * @return -1 如果没找到；
     */
    public int getItemType(@NonNull Object itemData) {
        String key = getClassName(itemData.getClass());
        //如果含有证明此className注册了组合的对应关系，需要取出实际的className
        if (itemClassNameGroupMap.containsKey(key)) {
            ViewHolderManager manager = itemClassNameGroupMap.get(key).getViewHolderManager(itemData);
            key = getClassNameFromGroup(itemData.getClass(), itemClassNameGroupMap.get(key), manager);
        }
        return itemClassNames.indexOf(key);
    }

    /**
     * 通过item type获取对应的ViewHolderManager
     *
     * @param type
     * @return
     */
    public ViewHolderManager getViewHolderManager(int type) {
        if (type < 0 || type > viewHolderManagers.size() - 1)
            return null;

        return viewHolderManagers.get(type);
    }

    public ViewHolderManager getViewHolderManager(Object itemData) {
        return getViewHolderManager(getItemType(itemData));
    }

    public List<ViewHolderManager> getViewHolderManagers() {
        return viewHolderManagers;
    }

    public List<String> getItemClassNames() {
        return itemClassNames;
    }

    private String getClassName(Class<?> cls) {
        return cls.getName();
    }

    private String getClassNameFromGroup(Class<?> cls, ViewHolderManagerGroup group, ViewHolderManager manager) {
        return getClassName(cls) + group.getViewHolderManagerTag(manager);
    }
}
