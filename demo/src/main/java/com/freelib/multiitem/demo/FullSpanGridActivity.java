package com.freelib.multiitem.demo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.demo.bean.ImageBean;
import com.freelib.multiitem.demo.bean.ImageTextBean;
import com.freelib.multiitem.demo.bean.TextBean;
import com.freelib.multiitem.demo.viewholder.FullSpanTextViewManager;
import com.freelib.multiitem.demo.viewholder.ImageAndTextManager;
import com.freelib.multiitem.demo.viewholder.ImageViewManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * 在表格中充满宽度可以时任意ViewHolderManager
 * 详见{@link com.freelib.multiitem.adapter.holder.ViewHolderManager#isFullSpan}
 * {@link com.freelib.multiitem.adapter.holder.ViewHolderManager#getSpanSize(int)}
 */
@EActivity(R.layout.layout_recycler)
public class FullSpanGridActivity extends AppCompatActivity {
    @ViewById(R.id.recyclerView)
    protected RecyclerView recyclerView;

    public static void startActivity(Context context) {
        FullSpanGridActivity_.intent(context).start();
    }

    @AfterViews
    protected void initViews() {
        setTitle(R.string.head_foot_grid_title);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        //初始化adapter
        BaseItemAdapter adapter = new BaseItemAdapter();
        //为XXBean数据源注册XXManager管理类
        adapter.register(TextBean.class, new FullSpanTextViewManager());
        adapter.register(ImageTextBean.class, new ImageAndTextManager());
        adapter.register(ImageBean.class, new ImageViewManager());
        //充满宽度详见ViewHolderManager#isFullSpan返回true即可，其实不一定是head或者foot
        TextView headView1 = new TextView(this);
        headView1.setText("通过addHeadView增加的充满宽度的head");
        //使用HeadFootHolderManager已经实现isFullSpan方法，默认全屏
        adapter.addHeadView(headView1);
        TextView footView1 = new TextView(this);
        footView1.setText("通过addFootView增加充满宽度的foot1");
        adapter.addFootView(footView1);
        recyclerView.setAdapter(adapter);
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (i == 1) {
                list.add(new TextBean("FullSpanTextViewManager充满宽度Item"));
            }
            list.add(new ImageBean(R.drawable.img1));
            list.add(new ImageTextBean(R.drawable.img2, "BBB" + i));
        }
        adapter.setDataItems(list);
    }
}