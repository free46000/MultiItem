package com.freelib.multiitem.listener;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.helper.ViewScaleHelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 拖动监听
 * 包括拖动流程(拖动前 是否拖动) 滚动相关(距离 范围) 悬浮视图 缩放等回调。
 * 基本都有默认实现，可根据具体业务继承重写方法
 * <p>
 * Created by free46000 on 2017/4/1.
 */
public abstract class OnItemDragListener {
    protected Object currItemData;

    protected int horizontalScrollMaxSpeed = 15;
    protected int verticalScrollMaxSpeed = 10;
    protected float horizontalLimit = 100;
    protected float verticalLimit = 200;

    /**
     * 拖拽结束时回调
     * todo 是否考虑此处参数中返回开始和结束对应的所有数据，
     * todo 并且考虑是否把参数抽成参数对象，其他方法也存在此问题
     */
    public abstract void onDragFinish(RecyclerView recyclerView, int itemPos, int itemHorizontalPos);

    /**
     * 拖拽开始时回调
     */
    public void onDragStart() {
    }

    /**
     * 每次拖动时候都要设置Item数据源，方便Recycler切换时候添加到新的Recycler
     *
     * @param currItemData 当前拖动的Item数据源
     */
    public void setItemData(@NonNull Object currItemData) {
        this.currItemData = currItemData;
    }

    /**
     * 第一次被选中的时候回调
     *
     * @param selectedView 选中的RecyclerView
     * @param selectedPos  选中的位置
     * @return 是否允许RecyclerView切换，目前返回值无效
     */
    public boolean onRecyclerSelected(RecyclerView selectedView, int selectedPos) {
        return true;
    }

    /**
     * 触摸切换RecyclerView的时候回调
     *
     * @param fromView            上个RecyclerView
     * @param toView              当前RecyclerView
     * @param itemFromPos         上个item选中的位置
     * @param itemToPos           当前item选中的位置
     * @param recyclerViewFromPos 上个RecyclerView选中的位置
     * @param recyclerViewToPos   当前RecyclerView选中的位置
     * @return 是否允许RecyclerView切换，如果false，则此次切换无效
     */
    public boolean onRecyclerChanged(RecyclerView fromView, RecyclerView toView, int itemFromPos, int itemToPos,
                                     int recyclerViewFromPos, int recyclerViewToPos) {
        BaseItemAdapter adapter = (BaseItemAdapter) fromView.getAdapter();
        adapter.removeDataItem(itemFromPos);
        adapter = (BaseItemAdapter) toView.getAdapter();
        adapter.addDataItem(itemToPos, currItemData);
        return true;
    }

    /**
     * 第一次被选中的时候回调
     *
     * @param selectedView 选中的RecyclerView
     * @param selectedPos  选中的位置
     * @return 是否允许Item选中拖动，如果false，则此次开启拖动无效
     */
    public boolean onItemSelected(View selectedView, int selectedPos) {
        return true;
    }

    /**
     * 触摸时切换Item的时候回调
     *
     * @param recyclerView    包含Item的RecyclerView
     * @param fromPos         上个选中的位置
     * @param toPos           当前选中的位置
     * @param recyclerViewPos 当前包含Item的RecyclerView选中的位置
     * @return 是否允许Item切换 如果false，则此次切换无效
     */
    public boolean onItemChanged(RecyclerView recyclerView, int fromPos, int toPos, int recyclerViewPos) {
        BaseItemAdapter adapter = (BaseItemAdapter) recyclerView.getAdapter();
        adapter.moveDataItem(fromPos, toPos);
        return true;
    }

    /**
     * 获取缩放比例，主要配合recycler缩放时{@link ViewScaleHelper}使用，需要复写返回正确的缩放比例
     *
     * @return 缩放比例 如0.5
     * @see ViewScaleHelper
     */
    public float getScale() {
        return 1;
    }

    /**
     * Recycler切换前回调此方法获取替换的目标Recycler<br>
     * 如果只是是否允许此Recycler被替换使用{@link #onRecyclerChanged}
     *
     * @param recyclerViewToPos 目标RecyclerViewPosition
     * @param itemToPos         目标ItemPosition
     * @return 默认null[目标RecyclerViewPosition, 目标ItemPosition]
     */
    public int onRecyclerChangedRecyclerPosition(RecyclerView fromView, RecyclerView toView, int itemFromPos, int itemToPos,
                                                 int recyclerViewFromPos, int recyclerViewToPos) {
        return recyclerViewToPos;
    }


    /**
     * Recycler切换前回调此方法获取替换的目标Item<br>
     * 有的Recycler不管touch在什么位置都要替换指定的item位置<br>
     * 如果只是是否允许此Recycler被替换使用{@link #onRecyclerChanged}
     *
     * @param recyclerViewToPos 目标RecyclerViewPosition
     * @param itemToPos         目标ItemPosition
     * @return 默认目标ItemPosition
     */
    public int onRecyclerChangedItemPosition(RecyclerView fromView, RecyclerView toView, int itemFromPos, int itemToPos,
                                             int recyclerViewFromPos, int recyclerViewToPos) {
        return itemToPos;
    }

    /**
     * Item切换前回调此方法获取替换的目标Item<br>
     * 有时需要指定要替换的Item位置，使用此方法<br>
     * 如果只是是否允许此Item被替换使用{@link #onItemChanged}
     *
     * @param toPos 目标ItemPosition
     * @return 默认返回 toPos
     */
    public int onItemChangedPosition(RecyclerView recyclerView, int fromPos, int toPos, int recyclerViewPos) {
        return toPos;
    }

    /**
     * @return 最大水平滚动速度
     */
    public int getHorizontalScrollMaxSpeed() {
        return (int) (horizontalScrollMaxSpeed * getScale());
    }

    /**
     * @return 最大垂直滚动速度
     */
    public int getVerticalScrollMaxSpeed() {
        return (int) (verticalScrollMaxSpeed * getScale());
    }

    /**
     * 在范围内才可以触发滚动
     *
     * @return 水平滚动临界范围
     */
    public float getHorizontalLimit() {
        return horizontalLimit * getScale();
    }

    /**
     * 在范围内才可以触发滚动
     *
     * @return 垂直滚动临界范围
     */
    public float getVerticalLimit() {
        return verticalLimit * getScale();
    }

    /**
     * 计算垂直水平距离
     * <p>
     * todo 可参考ItemTouchHelper改造成加速器形式
     *
     * @return 水平滚动距离
     */
    public int calcHorizontalScrollDistance(View view, int touchX, int touchY) {
        int direct = calcScrollHorizontalDirect(touchX, view.getWidth());
        int scrollDistance = 0;
        if (direct < 0) {
            float level = (getHorizontalLimit() - touchX) / getHorizontalLimit();
            scrollDistance = -calcScrollDistance(level, getHorizontalScrollMaxSpeed());
        } else if (direct > 0) {
            float level = (touchX - view.getWidth() + getHorizontalLimit()) / getHorizontalLimit();
            scrollDistance = calcScrollDistance(level, getHorizontalScrollMaxSpeed());
        }
        return scrollDistance;
    }

    /**
     * 计算垂直滚动距离
     * <p>
     * todo 可参考ItemTouchHelper改造成加速器形式
     *
     * @return 垂直滚动距离
     */
    public int calcVerticalScrollDistance(View view, int touchX, int touchY) {
        int direct = calcScrollVerticalDirect(touchY, view.getHeight());
        int scrollDistance = 0;
        if (direct < 0) {
            float level = (getVerticalLimit() - touchY) / getVerticalLimit();
            scrollDistance = -calcScrollDistance(level, getVerticalScrollMaxSpeed());
        } else if (direct > 0) {
            float level = (touchY - view.getHeight() + getVerticalLimit()) / getVerticalLimit();
            scrollDistance = calcScrollDistance(level, getVerticalScrollMaxSpeed());
        }
        return scrollDistance;
    }

    /**
     * 计算滚动距离
     *
     * @param touchLevel 触摸滑动速度等级
     * @param maxSpeed   最大速度
     * @return 滚动距离 touchLevel*maxSpeed
     */
    protected int calcScrollDistance(float touchLevel, int maxSpeed) {
        touchLevel = touchLevel > 1 ? 1f : touchLevel;
        return (int) (touchLevel * maxSpeed);
    }

    /**
     * 计算水平滚动指向
     * 可以对touchX的值进行过滤  例:滑动超出view(touchX<0||touchX>viewWidth)
     *
     * @return -1:像左滚动 0:不滚动 1:像右滚动
     */
    protected int calcScrollHorizontalDirect(int touchX, int viewWidth) {
        if (touchX < getHorizontalLimit()) {
            return -1;
        } else if (touchX > viewWidth - getHorizontalLimit()) {
            return 1;
        }
        return 0;
    }

    /**
     * 计算垂直滚动指向
     * 可以对touchY的值进行过滤  例:滑动超出view(touchY<0||touchY>viewHeight)
     *
     * @return -1:像上滑 0:不滑动 1:像下滑
     */
    protected int calcScrollVerticalDirect(int touchY, int viewHeight) {
        if (touchY < getVerticalLimit()) {
            return -1;
        } else if (touchY > viewHeight - getVerticalLimit()) {
            return 1;
        }
        return 0;
    }

    /**
     * 对浮动view进行处理 如：动画的处理
     *
     * @param floatView 浮动的view
     */
    public void onDrawFloatView(View floatView) {
        floatView.setScaleX(0.95f);
        floatView.setScaleY(0.95f);
        floatView.setRotation(0.9f);
        floatView.setAlpha(0.8f);
    }

    public float getMoveLimit() {
        return 0.5f;
    }
}
