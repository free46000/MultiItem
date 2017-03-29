package com.freelib.multiitem.demo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.freelib.multiitem.demo.bean.TextBean;
import com.freelib.multiitem.demo.viewholder.TextViewManager;
import com.freelib.multiitem.helper.PanelDragHelper;
import com.freelib.multiitem.helper.ViewScaleHelper;
import com.freelib.multiitem.item.ItemUnique;
import com.freelib.multiitem.listener.OnItemLongClickListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

// TODO: 之前使用Item的setVISIBLE考虑现在如何实现，思考是否把这个还有拖动时的是否回调一起封装到一个对象中
@EActivity(R.layout.activity_panel)
public class PanelActivity extends Activity {
    @ViewById(R.id.panel_content)
    protected View contentView;

    public static final int NONE = -1;
    private RecyclerView horizontalRecycler;
    private BaseItemAdapter adapter;
    private PanelDragHelper dragHelper;
    private ViewScaleHelper scaleHelper;

    public static void startActivity(Context context) {
        PanelActivity_.intent(context).start();
    }

    @AfterViews
    protected void initView() {
        contentView.getLayoutParams().width = getScreenWidth(this);
        contentView.getLayoutParams().height = getScreenHeight(this);
        horizontalRecycler = (RecyclerView) findViewById(R.id.recyclerView);
        horizontalRecycler.getLayoutParams().width = getScreenWidth(this);
        horizontalRecycler.getLayoutParams().height = getScreenHeight(this);

//        contentView.setScaleX(0.5f);
//        contentView.setScaleY(0.5f);
//        contentView.setPivotX(0f);
//        contentView.setPivotY(0f);

        adapter = new BaseItemAdapter();
        //此处不能复用，所以使用ItemUnique保证唯一，Item可以动态匹配ViewHolderManager所以不用注册
        adapter.addDataItem(new ItemUnique(new RecyclerViewManager(15)));
        adapter.addDataItem(new ItemUnique(new RecyclerViewManager(1)));
        adapter.addDataItem(new ItemUnique(new RecyclerViewManager(25)));
        adapter.addDataItem(new ItemUnique(new RecyclerViewManager(15)));
        adapter.addDataItem(new ItemUnique(new RecyclerViewManager(5)));
        adapter.addDataItem(new ItemUnique(new RecyclerViewManager(5)));
        horizontalRecycler.setLayoutManager(new Manager(this, LinearLayoutManager.HORIZONTAL, false));
        horizontalRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        horizontalRecycler.setClipToPadding(false);

//        touchHelper = new PanelTouchHelper(horizontalRecycler);
        dragHelper = new PanelDragHelper(horizontalRecycler);
        scaleHelper = new ViewScaleHelper(this);
        scaleHelper.setContentView(contentView);
        scaleHelper.setHorizontalView(horizontalRecycler);
        final GestureDetector doubleTapGesture = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                scaleHelper.toggleScaleModel();
                return super.onDoubleTap(e);
            }
        });
        horizontalRecycler.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return doubleTapGesture.onTouchEvent(event);
            }
        });
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealMetrics(outMetrics);
        } else {
            wm.getDefaultDisplay().getMetrics(outMetrics);
        }
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealMetrics(outMetrics);
        } else {
            wm.getDefaultDisplay().getMetrics(outMetrics);
        }
        return outMetrics.heightPixels - 300;
    }

    public static int getContentHeight(Activity context) {
        Rect outRect = new Rect();
        context.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect);
        return outRect.height();
    }

    public static int getContentWidth(Activity context) {
        Rect outRect = new Rect();
        context.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect);
        return outRect.width();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return dragHelper.onTouch(ev) || super.dispatchTouchEvent(ev);
    }

    class OnBaseDragListener extends PanelDragHelper.OnDragListener {
        private Object currItem;

        public OnBaseDragListener(Object currItem) {
            this.currItem = currItem;
        }

        public boolean onRecyclerSelected(RecyclerView recyclerView, int selectedPos) {
            return true;
        }

        @Override
        public float getScale() {
            return scaleHelper.isInScaleMode() ? scaleHelper.getScale() : super.getScale();
        }

        public boolean onRecyclerChanged(RecyclerView fromView, RecyclerView toView, int itemFromPos,
                                         int itemToPos, int i, int ii) {
            BaseItemAdapter adapter = (BaseItemAdapter) fromView.getAdapter();
            adapter.removeDataItem(itemFromPos);
            adapter = (BaseItemAdapter) toView.getAdapter();
            adapter.addDataItem(itemToPos, currItem);

            return true;
        }

        public boolean onItemSelected(View selectedView, int selectedPos) {
            return true;
        }

        public boolean onItemChanged(RecyclerView recyclerView, int fromPos, int toPos, int i) {
            BaseItemAdapter adapter = (BaseItemAdapter) recyclerView.getAdapter();
            adapter.moveDataItem(fromPos, toPos);
            return true;
        }

        public void onDragFinish(RecyclerView recyclerView, int itemPos, int itemHorizontalPos) {
//            ((MainActivity.ItemText) currItem).setGravity(View.VISIBLE);
            if (recyclerView != null)
                recyclerView.getAdapter().notifyDataSetChanged();
//            for (int i = 0; i < parentRecycler.getChildCount(); i++) {
//                View childView = parentRecycler.getChildAt(i);
//                if (childView instanceof RecyclerView) {
//                    ((RecyclerView) childView).getAdapter().notifyDataSetChanged();
//                }
//            }
        }

        public void onDragStart() {
//            if (currItem instanceof MainActivity.ItemText) {
//                ((MainActivity.ItemText) currItem).setGravity(View.INVISIBLE);
//                itemViewHolder.refreshView();
//            }
        }


    }

    class Manager extends LinearLayoutManager {

        public Manager(Context context) {
            super(context);
        }

        public Manager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public Manager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void setMeasuredDimension(int widthSize, int heightSize) {
//            widthSize = widthSize * 2;
            super.setMeasuredDimension(widthSize, heightSize);
        }
    }

    class RecyclerViewManager extends BaseViewHolderManager<ItemUnique> {
        private int length = 25;

        public RecyclerViewManager(int length) {
            this.length = length;
        }

        @Override
        protected void onCreateViewHolder(@NonNull BaseViewHolder holder) {
            super.onCreateViewHolder(holder);
            View view = holder.itemView;
            view.getLayoutParams().width = getScreenWidth(PanelActivity.this);
//            view.getLayoutParams().height = 1704;

            scaleHelper.addVerticalView(view);
            final RecyclerView recyclerView = getView(view, R.id.item_group_recycler);
//            horizontalRecycler.setClipToPadding(false);

            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            final BaseItemAdapter baseItemAdapter = new BaseItemAdapter();
            //为XXBean数据源注册XXManager管理类
            baseItemAdapter.register(TextBean.class, new TextViewManager());
            baseItemAdapter.setDataItems(getItemList(length));
            recyclerView.setAdapter(baseItemAdapter);

            baseItemAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
                @Override
                protected void onItemLongClick(BaseViewHolder viewHolder) {
                    View itemView = viewHolder.itemView;
                    int[] locArr = new int[2];
                    itemView.getLocationOnScreen(locArr);
                    dragHelper.setOnDragListener(new OnBaseDragListener(viewHolder.getItemData()));
                    dragHelper.startDrag(viewHolder);
//                    if (item instanceof MainActivity.ItemText) {
//                        ((MainActivity.ItemText) item).setGravity(View.INVISIBLE);
//                        itemViewHolder.refreshView();
//                    }
                }


            });
        }

        @Override
        public void onBindViewHolder(@NonNull BaseViewHolder holder, @NonNull ItemUnique data) {
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.item_recycler_view;
        }

        private List<Object> getItemList(int length) {
            List<Object> list = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                if (i == 1) {
                    list.add(new TextBean(i + "fsadfsa\nfdsafdsa\nfdsafdsa\nfdsafdasfd\nsafdsa\nfdsfdasf" + i));
                }
                String text = "fsadfsafdsafdsafdsafdsa\nfdsafdasfdsafdsafdsfdasf" + i;
                text = i > 9 ? text + "\nsdad" : text;
                list.add(new TextBean(i + text));
            }
            return list;
        }

    }

}
