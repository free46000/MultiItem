package com.freelib.multiitem.demo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.freelib.multiitem.demo.bean.ImageTextBean;
import com.freelib.multiitem.demo.bean.TextDragBean;
import com.freelib.multiitem.demo.viewholder.ImageAndTextManager;
import com.freelib.multiitem.demo.viewholder.TextViewDragManager;
import com.freelib.multiitem.helper.ItemDragHelper;
import com.freelib.multiitem.helper.ViewScaleHelper;
import com.freelib.multiitem.item.UniqueItemManager;
import com.freelib.multiitem.listener.OnItemDragListener;
import com.freelib.multiitem.listener.OnItemLongClickListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO: 2017/4/2 把第0个位置的数据源设置为不可拖动的，并在UI上通过文字体现出来
// TODO: 2017/4/3 增加拖拽时自动缩放选项
// TODO: 之前使用Item的setVISIBLE考虑现在如何实现，思考是否把这个还有拖动时的是否回调一起封装到一个对象中
@EActivity(R.layout.activity_panel)
public class PanelActivity extends AppCompatActivity {
    @ViewById(R.id.panel_content)
    protected View contentView;

    public static final int NONE = -1;
    private RecyclerView horizontalRecycler;
    private BaseItemAdapter adapter;
    private ItemDragHelper dragHelper;
    private ViewScaleHelper scaleHelper;

    public static void startActivity(Context context) {
        PanelActivity_.intent(context).start();
    }

    @AfterViews
    protected void initView() {
        horizontalRecycler = (RecyclerView) findViewById(R.id.recyclerView);

        adapter = new BaseItemAdapter();
        //此处不能复用，所以使用ItemUnique保证唯一，Item可以动态匹配ViewHolderManager所以不用注册
        adapter.addDataItems(Arrays.asList(new UniqueItemManager(new RecyclerViewManager(15)),
                new UniqueItemManager(new RecyclerViewManager(1)), new UniqueItemManager(new RecyclerViewManager(25)),
                new UniqueItemManager(new RecyclerViewManager(15)), new UniqueItemManager(new RecyclerViewManager(5))));
        //设置横向滚动LinearLayoutManager
        horizontalRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        horizontalRecycler.setAdapter(adapter);

        dragHelper = new ItemDragHelper(horizontalRecycler);
        scaleHelper = new ViewScaleHelper();
        scaleHelper.setContentView(contentView);
        scaleHelper.setHorizontalView(horizontalRecycler);

        //监听横向滚动RecyclerView双击事件，并开启关闭缩放模式
        doubleTapToggleScale();
    }

    private void doubleTapToggleScale() {
        GestureDetector doubleTapGesture = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                scaleHelper.toggleScaleModel();
                return super.onDoubleTap(e);
            }
        });
        horizontalRecycler.setOnTouchListener((v, event) -> doubleTapGesture.onTouchEvent(event));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //需要把touch事件传给dragHelper，true表示消耗掉事件
        //需要保证在Activity或者外层的ViewGroup或可以拦截Touch事件的地方回调都可以
        return dragHelper.onTouch(ev) || super.dispatchTouchEvent(ev);
    }

    class OnBaseDragListener extends OnItemDragListener {

        @Override
        public float getScale() {
            return scaleHelper.isInScaleMode() ? scaleHelper.getScale() : super.getScale();
        }

        public void onDragFinish(RecyclerView recyclerView, int itemPos, int itemHorizontalPos, Object itemData) {
//            ((MainActivity.ItemText) currItem).setGravity(View.VISIBLE);
//            if (recyclerView != null)
//                recyclerView.getAdapter().notifyDataSetChanged();
        }

    }

    class RecyclerViewManager extends BaseViewHolderManager<UniqueItemManager> {
        private int length = 25;

        public RecyclerViewManager(int length) {
            this.length = length;
        }

        @Override
        protected void onCreateViewHolder(@NonNull BaseViewHolder holder) {
            super.onCreateViewHolder(holder);
            View view = holder.itemView;
            view.getLayoutParams().width = -1;

            scaleHelper.addVerticalView(view);
            final RecyclerView recyclerView = getView(view, R.id.item_group_recycler);
//            horizontalRecycler.setClipToPadding(false);

            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            final BaseItemAdapter baseItemAdapter = new BaseItemAdapter();
            //为XXBean数据源注册Xager管理类
            baseItemAdapter.register(TextDragBean.class, new TextViewDragManager());
            baseItemAdapter.register(ImageTextBean.class, new ImageAndTextManager());
            baseItemAdapter.setDataItems(getItemList(length));
            recyclerView.setAdapter(baseItemAdapter);

            baseItemAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
                @Override
                protected void onItemLongClick(BaseViewHolder viewHolder) {
                    dragHelper.setOnItemDragListener(new OnBaseDragListener());
                    dragHelper.startDrag(viewHolder);
                }


            });
        }

        @Override
        public void onBindViewHolder(@NonNull BaseViewHolder holder, @NonNull UniqueItemManager data) {
            TextView groupTxt = getView(holder.itemView, R.id.item_group_name);
            groupTxt.setText("待办任务组" + holder.getItemPosition());
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.item_recycler_view;
        }

        private List<Object> getItemList(int length) {
            List<Object> list = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                if (i == 1) {
                    list.add(new TextDragBean(i + "标题\n内容A\n内容B\n内容C" + i));
                }
                String content = String.format("事项：%s\n事项内容：%s%s", i, i, i > 9 ? "\n更多内容" : "");
                list.add(i % 2 == 1 ? new ImageTextBean(R.drawable.img2, content) : new TextDragBean(content));
            }
            return list;
        }

    }
}