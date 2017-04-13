package com.freelib.multiitem.demo;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.InputItemAdapter;
import com.freelib.multiitem.demo.bean.ImageBean;
import com.freelib.multiitem.demo.bean.ImageTextBean;
import com.freelib.multiitem.demo.bean.TextBean;
import com.freelib.multiitem.demo.input.ItemEdit;
import com.freelib.multiitem.demo.input.ItemNameAndSex;
import com.freelib.multiitem.demo.viewholder.ImageAndTextManager;
import com.freelib.multiitem.demo.viewholder.ImageViewManager;
import com.freelib.multiitem.demo.viewholder.TextViewManager;
import com.freelib.multiitem.item.HiddenItemInput;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * todo 是否增加验证Rule，怎样增加更灵活
 * todo 测试输入法与输入框之间覆盖问题
 * todo 考虑支持DataBinding
 * todo checkbox 一行对应多个key特殊用法
 */
@EActivity(R.layout.activity_input)
public class InputActivity extends AppCompatActivity {
    @ViewById(R.id.recyclerView)
    protected RecyclerView recyclerView;

    private InputItemAdapter adapter;

    public static void startActivity(Context context) {
        InputActivity_.intent(context).start();
    }

    @AfterViews
    protected void initViews() {
        setTitle(R.string.input_title);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //初始化adapter
        adapter = new InputItemAdapter();
        recyclerView.setAdapter(adapter);
        List<Object> list = new ArrayList<>();
        //姓名和性别录入Item，一个录入item对应多个提交的{"name":"","sex":""}
        list.add(new ItemNameAndSex());
        for (int i = 0; i < 20; i++) {
            list.add(new ItemEdit("key" + i).setName("Name:" + i).setDefValue(i == 9 ? "默认值" : null));
        }
        adapter.addHiddenItem(new HiddenItemInput("hidden", "隐藏域的值"));
        adapter.setDataItems(list);
    }

    @Click(R.id.submit_btn)
    public void submit() {
        String changeTxt = "表单内容" + (adapter.isValueChange() ? "已改变\n" : "未变动\n");
        String valueTxt = adapter.getInputJson().toString();
        new AlertDialog.Builder(this).setTitle("自动组装表单内容").setMessage(changeTxt + valueTxt)
                .setPositiveButton(R.string.confirm, null).show();
    }

}
