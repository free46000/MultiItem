package com.freelib.multiitem.demo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.ViewHolderManagerGroup;
import com.freelib.multiitem.demo.bean.MessageBean;
import com.freelib.multiitem.demo.viewholder.ReceiveMessageManager;
import com.freelib.multiitem.demo.viewholder.SendMessageManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.layout_recycler)
public class ChatActivity extends AppCompatActivity {
    public static final String UID = "1";
    public static final String OTHER = "2";
    @ViewById(R.id.recyclerView)
    protected RecyclerView recyclerView;

    public static void startActivity(Context context) {
        ChatActivity_.intent(context).start();
    }

    @AfterViews
    protected void initViews() {
        setTitle(R.string.chat_title);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //初始化adapter
        BaseItemAdapter adapter = new BaseItemAdapter();
        //为XXBean数据源注册XXManager管理类组合
        adapter.register(MessageBean.class, new ViewHolderManagerGroup<MessageBean>(new SendMessageManager(), new ReceiveMessageManager()) {
            @Override
            public int getViewHolderManagerIndex(MessageBean itemData) {
                //根据message判断是否本人发送并返回对应ViewHolderManager的index值
                return itemData.getSender().equals(UID) ? 0 : 1;
            }
        });
        recyclerView.setAdapter(adapter);
        List<MessageBean> list = new ArrayList<>();
        list.add(new MessageBean("在吗？", OTHER));
        list.add(new MessageBean("在啊啊啊啊啊啊啊！", UID));
        list.add(new MessageBean("目前展示的是聊天界面中一个消息对应两种布局的情况，看看效果如何？", OTHER));
        list.add(new MessageBean("不错！", UID));

        adapter.setDataItems(list);
    }
}
