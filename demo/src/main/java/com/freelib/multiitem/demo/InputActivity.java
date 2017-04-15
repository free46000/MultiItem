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
import com.freelib.multiitem.demo.input.ItemInfo;
import com.freelib.multiitem.demo.input.ItemNameAndSex;
import com.freelib.multiitem.demo.viewholder.ImageAndTextManager;
import com.freelib.multiitem.demo.viewholder.ImageViewManager;
import com.freelib.multiitem.demo.viewholder.TextViewManager;
import com.freelib.multiitem.item.HiddenItemInput;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * todo 增加验证Rule，怎样增加更灵活
 * todo ItemInfo利用Data bind增加数字统计，可以查看之前写文章的例子
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
        //正常的EditText录入Item
        list.add(new ItemEdit("height").setName("身高:"));
        list.add(new ItemEdit("weight").setName("体重:"));
        list.add(new ItemEdit("age").setName("年龄:"));
        list.add(new ItemEdit("default").setName("国家:").setDefValue("中国"));
        list.add(new ItemInfo("info").setName("介绍:"));
        adapter.addHiddenItem("id", "隐藏域中携带id");
        adapter.setDataItems(list);
    }

    @Click(R.id.submit_btn)
    public void submit() {
        String tipTxt = "表单内容" + (adapter.isValueChange() ? "　已经　" : "　没有　") +
                "被用户改变！\n表单　　" + (adapter.isValueValid() ? "　已经　" : "　没有　") +
                "通过验证！\n自动组装的表单内容为：\n";
        String valueTxt = null;
        try {
            valueTxt = adapter.getInputJson().toString(4);
        } catch (JSONException e) {
            //do nothing
        }
        new AlertDialog.Builder(this).setTitle("提交").setMessage(tipTxt + valueTxt)
                .setPositiveButton(R.string.confirm, null).show();
    }

}
