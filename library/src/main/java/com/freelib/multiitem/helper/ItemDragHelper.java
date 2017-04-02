package com.freelib.multiitem.helper;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.listener.OnItemDragListener;


/**
 * Created by free46000 on 2016/8/19.
 * 面板拖动辅助类 -跨RecyclerView拖动
 * <p>
 * todo 注释要详细  完整流程的解读，各项数据计算最好带上 在readme中添加uml流程图（plantuml）
 * todo 通过#OnDragListener的切换回调boolean值处理是否可以替换的场景（有的Item是不允许被拖动的）
 */
public class ItemDragHelper {
    public static final int NONE = -1;

    private RecyclerView horizontalRecycler;
    private OnItemDragListener dragListener;
    private DragFloatViewHelper floatViewHelper;
    private boolean isDrag;

    private int lastRecyclerPos = NONE;
    private RecyclerView lastRecyclerView;
    private int lastItemPos = NONE;
    private float lastTouchRawX, lastTouchRawY;
    private int itemViewHeight;

    /**
     * 横向滚动的RecyclerView
     *
     * @param horizontalRecycler
     */
    public ItemDragHelper(RecyclerView horizontalRecycler) {
        this.horizontalRecycler = horizontalRecycler;
        floatViewHelper = new DragFloatViewHelper();
    }

    /**
     * 开始拖拽
     *
     * @param viewHolder 选中的Item的ViewHolder
     */
    public void startDrag(BaseViewHolder viewHolder) {
        startDrag(viewHolder.itemView, viewHolder.getItemPosition(), viewHolder);
    }

    /**
     * 开始拖拽
     *
     * @param itemView     选中的Item的View
     * @param itemPosition 选中的Item的position
     */
    private void startDrag(View itemView, int itemPosition, BaseViewHolder viewHolder) {
        dragListener.setItemViewHolder(viewHolder);
        if (!dragListener.onItemSelected(itemView, itemPosition)) {
            return;
        }

        isDrag = true;

        initParams();
        lastItemPos = itemPosition;
        dragListener.onDragStart();
        floatViewHelper.createView(itemView, lastTouchRawX, lastTouchRawY, dragListener.getScale());
        dragListener.onDrawFloatView(floatViewHelper.getFloatView());


        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        itemViewHeight = itemView.getHeight() + params.topMargin + params.bottomMargin;
    }

    private void initParams() {
        lastRecyclerPos = NONE;
        lastRecyclerView = null;
    }

    /**
     * 设置拖拽回调Listener
     *
     * @param onItemDragListener 回调Listener
     */
    public void setOnItemDragListener(OnItemDragListener onItemDragListener) {
        this.dragListener = onItemDragListener;
    }

    public RecyclerView getHorizontalRecycler() {
        return horizontalRecycler;
    }

    /**
     * 必须完整处理Touch事件，可以在Activity或者外层的ViewGroup或可以拦截Touch事件的地方回调都可以
     *
     * @param event touch的event
     * @return true表示消耗掉事件
     */
    public boolean onTouch(MotionEvent event) {
        lastTouchRawX = event.getRawX();
        lastTouchRawY = event.getRawY();

        if (!isDrag) {
            return false;
        }
        //如果drag过程没有MOVE事件，lastVerticalPos和lastRecyclerView是不能被正确初始化的，这样dragFinish回调就会有问题
//        if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
        floatViewHelper.updateView((int) lastTouchRawX, (int) lastTouchRawY);
        moveIfNecessary(lastTouchRawX, lastTouchRawY);
        scrollRunnableStart();
//        }

        if (event.getActionMasked() == MotionEvent.ACTION_UP ||
                event.getActionMasked() == MotionEvent.ACTION_CANCEL ||
                event.getActionMasked() == MotionEvent.ACTION_OUTSIDE) {
            stopDrag();
        }

        return true;
    }

    private void scrollRunnableStart() {
        if (lastRecyclerView != null) {
            lastRecyclerView.removeCallbacks(mScrollRunnable);
            mScrollRunnable.run();
            lastRecyclerView.invalidate();
        }
    }

    private void stopDrag() {
        if (isDrag) {
            dragListener.onDragFinish(lastRecyclerView, lastRecyclerPos, lastItemPos);
            floatViewHelper.removeView();
        }

        isDrag = false;
    }


    /**
     * 当用户拖动的时候，计算是否需要移动Item
     *
     * @param touchRawX float event.getRawX()
     * @param touchRawY float event.getRawY()
     */
    private boolean moveIfNecessary(float touchRawX, float touchRawY) {
        boolean result = true;

        //找到当前触摸点下的recyclerView
        float[] location = getInsideLocation(horizontalRecycler, touchRawX, touchRawY);
        View view = horizontalRecycler.findChildViewUnder(location[0], location[1]);
        int recyclerPos = getPositionByChildView(view);
//        System.out.println("find_parent_out:lastRecyclerPos:" + lastRecyclerPos + "-recyclerPos:" + recyclerPos + "==location[0]:" + location[0] + "==location[1]:" + location[1]);
        RecyclerView recyclerView = findRecyclerView(view);

        //没有找到所属位置或者目标RecyclerView，则不移动
        if (recyclerPos == NONE || recyclerView == null) {
            return false;
        }

        //找到当前触摸点下的itemView
        location = getInsideLocation(recyclerView, touchRawX, touchRawY);
        float itemX = location[0];
        float itemY = location[1];
        View itemView = recyclerView.findChildViewUnder(itemX, itemY);
        int itemPos = getTargetItemPos(itemView, itemY, lastRecyclerPos, recyclerPos);

//        System.out.println("find_parent_out:lastItemPos:" + lastItemPos + "==itemPos:" + itemPos + "==childX:" + itemX + "===childY:" + itemY);
        if (isSelectedRecyclerView(lastRecyclerPos, recyclerPos)) {
            dragListener.onRecyclerSelected(recyclerView, recyclerPos);
            lastRecyclerPos = recyclerPos;
            lastRecyclerView = recyclerView;
        } else if (isChangeRecyclerView(lastRecyclerPos, recyclerPos)) {
            itemPos = calcItemPositionWhenChangeRecycler(recyclerView, itemView, itemPos, itemX, itemY);
//            System.out.println("find_parent_inside:" + itemPos + "==" + lastRecyclerPos + "-" + recyclerPos + "==" + "childX:" + itemX + "childY:" + itemY);
            if (itemPos != NONE) {
                //当recycler change的时候，返回Recycler Position
                recyclerPos = dragListener.onRecyclerChangedRecyclerPosition(lastRecyclerView, recyclerView,
                        lastItemPos, itemPos, lastRecyclerPos, recyclerPos);
                //当recycler切换的时候，返回Item位置，有的Recycler不管touch在什么位置都要替换指定的item位置
                itemPos = dragListener.onRecyclerChangedItemPosition(lastRecyclerView, recyclerView,
                        lastItemPos, itemPos, lastRecyclerPos, recyclerPos);
                boolean isChanged = dragListener.onRecyclerChanged(lastRecyclerView, recyclerView,
                        lastItemPos, itemPos, lastRecyclerPos, recyclerPos);
                if (!isChanged) {
                    return result;
                }
//                System.out.println("find_parent:" + lastRecyclerPos + "-" + verticalPos);
                //在切换recycle view并且触摸到子recycle view的item的时候才真正去改变值
                lastRecyclerPos = recyclerPos;
                lastRecyclerView = recyclerView;
                //因为切换父控件，所以需要重置为当前itemPos，不然上个的最后位置有可能超过当前的大小抛出错误
                lastItemPos = itemPos;
            }

        }

        if (itemPos == NONE) {
            return result;
        }

        if (isItemNeedChange(itemView, lastItemPos, itemPos, itemY)) {
            itemPos = dragListener.onItemChangedPosition(recyclerView, lastItemPos, itemPos, lastRecyclerPos);

            boolean isChanged = dragListener.onItemChanged(recyclerView, lastItemPos, itemPos, lastRecyclerPos);
            if (!isChanged) {
                return result;
            }
            scrollToRightPositionWhenItemChanged(recyclerView, itemView, itemPos);
//            System.out.println("find:" + lastRecyclerPos + "-" + recyclerPos + "======" + lastItemPos + "-" + itemPos);
            lastItemPos = itemPos;
        }
        return result;
    }

    /**
     * 当item位置变换，滚动recycler到正确的位置
     * // TODO: 2017/2/21 0021 这个试试为何要这样做
     */
    private void scrollToRightPositionWhenItemChanged(RecyclerView recyclerView, View itemView, int itemPos) {
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof ItemTouchHelper.ViewDropHandler) {
            OrientationHelper helper = OrientationHelper.createVerticalHelper(layoutManager);
            int start = helper.getDecoratedStart(itemView);
            int end = helper.getDecoratedEnd(itemView);
            ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(
                    itemPos, lastItemPos > itemPos ? start : end - itemViewHeight);
//                System.out.println(lastItemPos + "-" + childPos + "OrientationHelperOrientationHelper:"
//                        + height + "==" + itemViewHeight + "=||=" + start + "===" + end + "||||||" + myStart + "===" + itemTargetView.getHeight() );
        }
        if (lastItemPos == 0 || itemPos == 0) {
            recyclerView.scrollToPosition(0);
        }
    }

    /**
     * 获取当前点击的位置在RecyclerView内部的坐标 (Y坐标范围0+padding到height-padding)?
     */
    private float[] getInsideLocation(RecyclerView recyclerView, float touchRawX, float touchRawY) {
        float[] result = new float[2];
        int[] location = new int[2];
        recyclerView.getLocationOnScreen(location);
        result[0] = touchRawX - location[0];
        result[1] = touchRawY - location[1];
//        System.out.println("getInsideLocation:" + result[0] + "-" + result[1] + "==" + "X:" + touchRawX + "Y:" + touchRawY);

        result[0] = result[0] / dragListener.getScale();
        result[1] = result[1] / dragListener.getScale();

        int minY = recyclerView.getPaddingTop();
        int maxY = recyclerView.getHeight() - recyclerView.getPaddingBottom();
        result[1] = Math.min(Math.max(result[1], minY), maxY);


        return result;
    }

    /**
     * 当在两个RecyclerView中切换时，计算目标RecyclerView中Child位置 (若目标RecyclerView为空返回0)
     */
    private int calcItemPositionWhenChangeRecycler(RecyclerView verticalRecycler, View itemView, int itemPos, float childX, float childY) {
        //若目标RecyclerView为空返回0
        if (itemPos == NONE && verticalRecycler.getAdapter().getItemCount() == 0) {
            itemPos = 0;
        } else if (itemView != null) {
            int top = itemView.getTop();
            if ((top + itemView.getHeight() * dragListener.getMoveLimit()) < childY) {
                //证明滑动位置在targetView的下部，所以要插入到当前位置+1
                itemPos++;
            }
        }

        return itemPos;
    }

    /**
     * 从view中获取需要操作的RecyclerView
     *
     * @param view View
     */
    protected RecyclerView findRecyclerView(View view) {
        if (view == null) {
            return null;
        }

        if (view instanceof RecyclerView) {
            return (RecyclerView) view;
        } else if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                // TODO: 2017/2/20 0020 这个地方是为了找出RecyclerView被包在其他view的情况。写法需要考虑
                if (((ViewGroup) view).getChildAt(i) instanceof RecyclerView) {
                    return (RecyclerView) ((ViewGroup) view).getChildAt(i);
                }
            }
        }
        return null;
    }


    /**
     * 滚动Runnable，为了可持续滚动
     */
    private final Runnable mScrollRunnable = new Runnable() {

        @Override
        public void run() {
            float[] horLocation = getInsideLocation(horizontalRecycler, lastTouchRawX, lastTouchRawY);
            float[] verLocation = getInsideLocation(lastRecyclerView, lastTouchRawX, lastTouchRawY);
            boolean isHorizontalScroll = scrollIfNecessary(horizontalRecycler, (int) horLocation[0], (int) horLocation[1]);
            boolean isVerticalScroll = scrollIfNecessary(lastRecyclerView, (int) verLocation[0], (int) verLocation[1]);
            if (isDrag && (isHorizontalScroll || isVerticalScroll)) {
                //it might be lost during scrolling
                moveIfNecessary(lastTouchRawX, lastTouchRawY);
                lastRecyclerView.removeCallbacks(mScrollRunnable);
                ViewCompat.postOnAnimation(lastRecyclerView, this);
            }
        }
    };

    /**
     * 当用户滚动到边缘的时候,计算是否需要滚动
     */
    private boolean scrollIfNecessary(RecyclerView recyclerView, int curX, int curY) {
        if (!isDrag) {
            return false;
        }

        RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
        int scrollX = 0;
        int scrollY = 0;
        if (lm.canScrollHorizontally()) {
            scrollX = dragListener.calcHorizontalScrollDistance(recyclerView, curX, curY);
        }
        if (lm.canScrollVertically()) {
            scrollY = dragListener.calcVerticalScrollDistance(recyclerView, curX, curY);
        }
//        System.out.println("scroll:::::" + scrollY + "=" + recyclerView.getScrollY() + "curY::" + curY);
//        System.out.println("scroll:::::" + scrollX + "=" + recyclerView.getScrollX() + "curX::" + curX);

        if (scrollX != 0 || scrollY != 0) {
            recyclerView.scrollBy(scrollX, scrollY);
        }
        return scrollX != 0 || scrollY != 0;
    }


    /**
     * 获取需要move的目标Position，即toPosition
     *
     * @return NONE为找不到
     */
    private int getTargetItemPos(View itemTargetView, float childY, int lastRecyclerPos, int currRecyclerPos) {
        int itemPos = getPositionByChildView(itemTargetView);

        if (itemPos == NONE) {
            return itemPos;
        }

        if ((itemPos != lastItemPos || lastRecyclerPos != currRecyclerPos)) {
            return itemPos;
        }

        return NONE;
    }


    /**
     * 两个Item是否需要move
     */
    private boolean isItemNeedChange(View itemView, int lastItemPos, int itemPos, float itemY) {
        if (itemView == null || lastItemPos == NONE || itemPos == NONE || lastItemPos == itemPos) {
            return false;
        }
        int top = itemView.getTop();

        int moveLimit = (int) (top + itemView.getHeight() * dragListener.getMoveLimit());
//        System.out.println("isNeedRemove-top:" + top + "==height:" + itemView.getHeight()
//                + "==touchY:" + itemY + "moveLimit==" + moveLimit + "lastItemPos > itemPos===" + (lastItemPos > itemPos));

        if (lastItemPos > itemPos) {
//            return touchY < moveLimit && top >= 0;
            return itemY < moveLimit;
        } else {
            return itemY > moveLimit;
        }
    }

    /**
     * touch的位置是否为当前view，防止两个item切换时的抖动问题
     */
    private boolean isCurrPosition(float childY, View itemView) {
//        System.out.println("isCurrPosition:" + (childY > itemView.getTop() && childY < itemView.getBottom()));
        return childY >= itemView.getTop() && childY <= itemView.getBottom();
    }


    /**
     * 查找当前view在RecyclerView中的位置 没有返回NONE
     */
    private int getPositionByChildView(View itemView) {
        if (itemView == null) {
            return NONE;
        }
        try {
            return ((RecyclerView.LayoutParams) itemView.getLayoutParams()).getViewAdapterPosition();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NONE;
    }

    /**
     * 是否真正切换了RecyclerView
     * 需要注意这里把没有切换后没有touch到Item当成不是真正切换
     */
    private boolean isChangeRecyclerView(int lastRecyclerPos, int currRecyclerPos) {
        return lastRecyclerPos != currRecyclerPos && lastRecyclerPos != NONE && currRecyclerPos != NONE;
    }

    /**
     * 是否为初次选中RecycleView
     */
    private boolean isSelectedRecyclerView(int lastRecyclerPos, int currRecyclerPos) {
        return lastRecyclerPos == NONE && currRecyclerPos != NONE;
    }

    /**
     * 是否为第一次选中子ItemView
     */
    private boolean isSelectedChildView(int lastChildPos, int currChildPos) {
        return lastChildPos == NONE && currChildPos != NONE;
    }
}
