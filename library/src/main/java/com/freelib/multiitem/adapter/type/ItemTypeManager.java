package com.freelib.multiitem.adapter.type;

import android.support.annotation.NonNull;

import com.freelib.multiitem.adapter.holder.ViewHolderManager;
import com.freelib.multiitem.adapter.holder.ViewHolderManagerGroup;
import com.freelib.multiitem.item.ItemManager;

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
    protected Map<String, ViewHolderManagerGroup> itemTypeNameGroupMap = new HashMap<>();
    protected List<String> itemTypeNames = new ArrayList<>();
    protected List<ViewHolderManager> viewHolderManagers = new ArrayList<>();

    /**
     * 通过数据源`typeName List`和`viewHolderManager List`两组集合对类型进行管理
     *
     * @param cls     数据源class
     * @param manager ViewHolderManager
     * @see com.freelib.multiitem.adapter.BaseItemAdapter#register(Class, ViewHolderManager)
     */
    public void register(Class<?> cls, ViewHolderManager manager) {
        register(getTypeName(cls), manager);
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
        itemTypeNameGroupMap.put(getTypeName(cls), group);
    }

    private void register(String typeName, ViewHolderManager manager) {
        if (itemTypeNames.contains(typeName)) {
            viewHolderManagers.set(itemTypeNames.indexOf(typeName), manager);
        } else {
            itemTypeNames.add(typeName);
            viewHolderManagers.add(manager);
        }
    }


    /**
     * @param itemData data item
     * @return -1 如果没找到；
     */
    public int getItemType(@NonNull Object itemData) {
        //如果是自定义Item类型，则直接从item中获取
        if (itemData instanceof ItemManager) {
            int type = getItemTypeFromItemManager((ItemManager) itemData);
            if (type >= 0) { //若正确查到type则返回，否则执行默认查找方法
                return type;
            }
        }

        String typeName = getTypeName(itemData.getClass());

        //如果含有证明此className注册了组合的对应关系，需要取出实际的className
        if (itemTypeNameGroupMap.containsKey(typeName)) {
            ViewHolderManager manager = itemTypeNameGroupMap.get(typeName).getViewHolderManager(itemData);
            typeName = getClassNameFromGroup(itemData.getClass(), itemTypeNameGroupMap.get(typeName), manager);
        }

        return itemTypeNames.indexOf(typeName);
    }

    private int getItemTypeFromItemManager(ItemManager itemManager) {
        String typeName = itemManager.getItemTypeName();
        int itemType = itemTypeNames.indexOf(typeName);
        if (itemType < 0) {
            ViewHolderManager holderManager = itemManager.getViewHolderManager();
            if (holderManager != null) {
                //自定义Item类型，事先不需要注册，若未查到对应关系，注册即可
                register(typeName, holderManager);
                itemType = itemTypeNames.size() - 1;
            }
        }
        return itemType;
    }

    /**
     * 通过item type获取对应的ViewHolderManager
     *
     * @param type
     * @return
     */
    public ViewHolderManager getViewHolderManager(int type) {
        if (type < 0 || type > viewHolderManagers.size() - 1) {
            return null;
        }

        return viewHolderManagers.get(type);
    }

    public ViewHolderManager getViewHolderManager(Object itemData) {
        return getViewHolderManager(getItemType(itemData));
    }

    public List<ViewHolderManager> getViewHolderManagers() {
        return viewHolderManagers;
    }

    public List<String> getItemTypeNames() {
        return itemTypeNames;
    }

    private String getTypeName(Class<?> cls) {
        return cls.getName();
    }

    private String getClassNameFromGroup(Class<?> cls, ViewHolderManagerGroup group, ViewHolderManager manager) {
        return getTypeName(cls) + group.getViewHolderManagerTag(manager);
    }
}
