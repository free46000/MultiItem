package com.freelib.multiitem.adapter.holder;

/**
 * 多个ViewHolder的管理类的组合
 * 主要为相同数据源根据内部属性的值会对应多个ViewHolderManager设计，常见的如聊天界面的消息
 * Created by free46000 on 2017/3/20.
 */
public abstract class ViewHolderManagerGroup<T> {
    private ViewHolderManager[] viewHolderManagers;

    /**
     * @param viewHolderManagers 相同数据源对应的所有ViewHolderManager
     */
    public ViewHolderManagerGroup(ViewHolderManager... viewHolderManagers) {
        if (viewHolderManagers == null || viewHolderManagers.length == 0) {
            throw new IllegalArgumentException("viewHolderManagers can not be null");
        }
        this.viewHolderManagers = viewHolderManagers;
    }

    /**
     * 根据item数据源中的属性判断应该返回的对应viewHolderManagers的index值
     *
     * @param itemData item数据源
     * @return index值应该是在viewHolderManagers数组有效范围内
     */
    public abstract int getViewHolderManagerIndex(T itemData);

    /**
     * 根据item数据源中的属性判断应该返回的对应viewHolderManager
     *
     * @param itemData item数据源
     * @return viewHolderManager
     */
    public ViewHolderManager getViewHolderManager(T itemData) {
        int index = getViewHolderManagerIndex(itemData);
        if (index < 0 || index > viewHolderManagers.length - 1) {
            throw new IllegalArgumentException("ViewHolderManagerGroup中的getViewHolderManagerIndex方法返回的index必须在有效范围内");
        }
        return viewHolderManagers[index];
    }

    /**
     * 通过viewHolderManager获取标识字符串，为了方便查找ViewHolderManager
     *
     * @param viewHolderManager ViewHolderManager
     * @return 获取viewHolderManager标识字符串
     */
    public String getViewHolderManagerTag(ViewHolderManager viewHolderManager) {
        return viewHolderManager.getClass().getName();
    }

    public ViewHolderManager[] getViewHolderManagers() {
        return viewHolderManagers;
    }
}
