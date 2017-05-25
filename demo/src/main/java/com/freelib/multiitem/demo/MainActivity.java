package com.freelib.multiitem.demo;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.demo.bean.MainBean;
import com.freelib.multiitem.demo.viewholder.TextViewManager;
import com.freelib.multiitem.listener.OnItemClickListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.layout_recycler)
public class MainActivity extends AppCompatActivity {
    @ViewById(R.id.recyclerView)
    protected RecyclerView recyclerView;


    @AfterViews
    protected void initViews() {
        setTitle(R.string.main_title);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BaseItemAdapter adapter = new BaseItemAdapter();
        //为TextBean数据源注册TextViewManager管理类
        adapter.register(MainBean.class, new TextViewManager());
        recyclerView.setAdapter(adapter);
        adapter.addDataItem(new MainBean(getString(R.string.item_click_title),
                () -> ItemClickActivity.startActivity(this)));
        adapter.addDataItem(new MainBean(getString(R.string.multi_item_title),
                () -> MultiItemActivity.startActivity(this)));
        adapter.addDataItem(new MainBean(getString(R.string.chat_title),
                () -> ChatActivity.startActivity(this)));
        adapter.addDataItem(new MainBean(getString(R.string.head_foot_title),
                () -> HeadFootActivity.startActivity(this)));
        adapter.addDataItem(new MainBean(getString(R.string.head_foot_grid_title),
                () -> FullSpanGridActivity.startActivity(this)));
        adapter.addDataItem(new MainBean(getString(R.string.load_more_title),
                () -> LoadMoreActivity.startActivity(this)));
        adapter.addDataItem(new MainBean(getString(R.string.panel_title),
                () -> PanelActivity.startActivity(this)));
        adapter.addDataItem(new MainBean(getString(R.string.data_bind_title),
                () -> DataBindActivity.startActivity(this)));
        adapter.addDataItem(new MainBean(getString(R.string.input_title),
                () -> InputActivity.startActivity(this)));
        adapter.addDataItem(new MainBean(getString(R.string.user_info_title),
                () -> UserInfoActivity.startActivity(this)));
        adapter.addDataItem(new MainBean(getString(R.string.empty_error_title),
                () -> EmptyAndErrorActivity.startActivity(this)));
        adapter.addDataItem(new MainBean(getString(R.string.animation_title),
                () -> AnimationActivity.startActivity(this)));

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder viewHolder) {
                //通过viewHolder获取需要的数据
                if (viewHolder.getItemData() instanceof MainBean) {
                    ((MainBean) viewHolder.getItemData()).onItemClick();
                }
            }
        });

    }
}
