package com.freelib.multiitem.item;

/**
 * 数据源Item拖动接口，实现一些move change等定制化的通用控制
 * Created by free46000 on 2017/4/3.
 */
public interface ItemDrag {
    /**
     * 是否可以在自己的Recycler中move
     *
     * @return boolean
     */
    boolean isCanMove();

    /**
     * 是否可以切换到其他Recycler
     *
     * @return boolean
     */
    boolean isCanChangeRecycler();

    /**
     * 是否可以开启拖动
     *
     * @return boolean
     */
    boolean isCanDrag();
}
