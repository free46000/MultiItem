package com.freelib.multiitem.demo;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.demo.bean.ImageBean;
import com.freelib.multiitem.demo.bean.ImageTextBean;
import com.freelib.multiitem.demo.bean.TextBean;
import com.freelib.multiitem.demo.viewholder.ImageAndTextManager;
import com.freelib.multiitem.demo.viewholder.ImageViewManager;
import com.freelib.multiitem.demo.viewholder.LoadMoreHolderManager;
import com.freelib.multiitem.demo.viewholder.TextViewManager;
import com.freelib.multiitem.listener.OnItemClickListener;
import com.freelib.multiitem.listener.OnLoadMoreListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.Y;

@EActivity(R.layout.layout_recycler)
public class HeadFootActivity extends AppCompatActivity {
    @ViewById(R.id.recyclerView)
    protected RecyclerView recyclerView;

    public static void startActivity(Context context) {
        HeadFootActivity_.intent(context).start();
    }

    @AfterViews
    protected void initViews() {
        setTitle(R.string.head_foot_title);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //初始化adapter
        BaseItemAdapter adapter = new BaseItemAdapter();
        //为XXBean数据源注册XXManager管理类
        adapter.register(TextBean.class, new TextViewManager());
        adapter.register(ImageTextBean.class, new ImageAndTextManager());
        adapter.register(ImageBean.class, new ImageViewManager());
        //添加header
        TextView headView = new TextView(this);
        headView.setText("通过addHeadView增加的head1");
        //方式一：方便实际业务使用
        adapter.addHeadView(headView);
        //方式二：这种方式和直接addDataItem添加数据源原理一样
        adapter.addHeadItem(new TextBean("通过addHeadItem增加的head2"));
        //添加footer，方式同添加header
        TextView footView = new TextView(this);
        footView.setText("通过addFootView增加的foot1");
        adapter.addFootView(footView);
        adapter.addFootItem(new TextBean("通过addFootItem增加的foot2"));

        recyclerView.setAdapter(adapter);
        List<Object> list = new ArrayList<>();
        list.add(new TextBean("AAA"));
        list.add(new ImageBean(R.drawable.img1));
        list.add(new ImageTextBean(R.drawable.img2, "BBB"));
        adapter.setDataItems(list);
    }
}


