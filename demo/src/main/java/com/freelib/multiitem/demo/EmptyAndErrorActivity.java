package com.freelib.multiitem.demo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.demo.bean.ImageBean;
import com.freelib.multiitem.demo.bean.ImageTextBean;
import com.freelib.multiitem.demo.bean.MainBean;
import com.freelib.multiitem.demo.bean.TextBean;
import com.freelib.multiitem.demo.state.ItemEmptyAndError;
import com.freelib.multiitem.demo.viewholder.ImageAndTextManager;
import com.freelib.multiitem.demo.viewholder.ImageViewManager;
import com.freelib.multiitem.demo.viewholder.TextViewManager;
import com.freelib.multiitem.helper.StateViewHelper;
import com.freelib.multiitem.item.BaseItemState;
import com.freelib.multiitem.listener.OnItemClickListener;
import com.freelib.multiitem.listener.OnStateClickListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.empty;
import static com.freelib.multiitem.demo.R.id.recyclerView;

@EActivity(R.layout.layout_recycler)
public class EmptyAndErrorActivity extends AppCompatActivity {
    @ViewById(R.id.recyclerView)
    protected RecyclerView recyclerView;
    private StateViewHelper emptyViewHelper;
    private StateViewHelper errorViewHelper;

    public static void startActivity(Context context) {
        EmptyAndErrorActivity_.intent(context).start();
    }

    @AfterViews
    protected void initViews() {
        setTitle(R.string.empty_error_title);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //初始化adapter
        BaseItemAdapter adapter = new BaseItemAdapter();
        //为XXBean数据源注册XXManager管理类
        adapter.register(TextBean.class, new TextViewManager());
        recyclerView.setAdapter(adapter);
        adapter.addDataItem(new TextBean("展示空白页"));
        adapter.addDataItem(new TextBean("展示错误页"));

        setOnItemClickListener(adapter);

        initEmptyHelper();
        initErrorHelper();
    }

    private void setOnItemClickListener(BaseItemAdapter adapter) {
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder viewHolder) {
                switch (viewHolder.getItemPosition()) {
                    case 0:
                        emptyViewHelper.show();
                        break;
                    case 1:
                        errorViewHelper.show();
                        break;
                }
            }
        });
    }

    private void initEmptyHelper() {
        BaseItemState emptyItem = new ItemEmptyAndError("列表数据为空");
        emptyViewHelper = new StateViewHelper(recyclerView, emptyItem);
        emptyItem.setOnStateClickListener(() -> emptyViewHelper.hide());
    }

    private void initErrorHelper() {
        BaseItemState emptyItem = new ItemEmptyAndError("数据加载错误");
        errorViewHelper = new StateViewHelper(recyclerView, emptyItem);
        emptyItem.setOnStateClickListener(() -> emptyViewHelper.hide());
    }

}
