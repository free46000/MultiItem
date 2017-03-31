package com.freelib.multiitem.demo;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.demo.bean.ImageBean;
import com.freelib.multiitem.demo.bean.ImageTextBean;
import com.freelib.multiitem.demo.bean.TextBean;
import com.freelib.multiitem.demo.viewholder.ImageAndTextManager;
import com.freelib.multiitem.demo.viewholder.ImageViewManager;
import com.freelib.multiitem.demo.viewholder.LoadMoreHolderManager;
import com.freelib.multiitem.demo.viewholder.TextViewManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.layout_recycler_refresh)
public class LoadMoreActivity extends AppCompatActivity {
    @ViewById(R.id.recyclerView)
    protected RecyclerView recyclerView;
    @ViewById(R.id.swipeRefreshLayout)
    protected SwipeRefreshLayout swipeRefreshLayout;

    private BaseItemAdapter adapter;
    private int currPage = 1;
    private int pageSize = 20;
    private int failTimes;

    public static void startActivity(Context context) {
        LoadMoreActivity_.intent(context).start();
    }

    @AfterViews
    protected void initViews() {
        setTitle(R.string.load_more_title);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //初始化adapter
        adapter = new BaseItemAdapter();
        //为XXBean数据源注册XXManager管理类
        adapter.register(TextBean.class, new TextViewManager());
        adapter.addHeadItem(new TextBean("我是Head View"));
        adapter.addFootItem(new TextBean("我是Foot View"));
        recyclerView.setAdapter(adapter);
        //开启加载更多视图
        adapter.enableLoadMore(new LoadMoreHolderManager(this::loadData));
        //下拉刷新视图，此处采用SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(() -> refresh());
        refresh();
    }

    private void refresh() {
        swipeRefreshLayout.setRefreshing(true);
        currPage = 1;
        adapter.clearData();
        adapter.notifyDataSetChanged();
        loadData();
    }

    /**
     * 模拟加载数据
     * 数据加载时间模拟延时1秒
     * 前两次成功
     * 第三次加载失败
     * 第四次加载成功，并加载数据完成
     */
    private void loadData() {
        new Handler().postDelayed(() -> {
            if (currPage < 3) {
                fillData();
                adapter.setLoadCompleted(false);
                currPage++;
            } else if (currPage == 3 && failTimes == 0) {
                adapter.setLoadFailed();
                failTimes++;
            } else {
                fillData();
                adapter.setLoadCompleted(true);
            }
            swipeRefreshLayout.setRefreshing(false);
        }, 1000);
    }

    private void fillData() {
        for (int i = pageSize * (currPage - 1); i < pageSize * currPage; i++) {
            adapter.addDataItem(new TextBean("数据" + i));
        }
    }


}


