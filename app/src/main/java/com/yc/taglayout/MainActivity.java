package com.yc.taglayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.yc.library.listener.OnFlexSelectListener;
import com.yc.library.widget.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private List<String> mDatas;
    private List<String> mSelectDatas;
    private StringTagAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initViews();
    }

    private void initData() {
        mDatas = new ArrayList<>();
        mDatas.add("全部");
        mDatas.add("奇幻玄幻");
        mDatas.add("武侠仙侠");
        mDatas.add("历史军事");
        mDatas.add("都市娱乐");
        mDatas.add("竞技同人");
        mDatas.add("科幻游戏");
        mDatas.add("花语女生");

        mSelectDatas = new ArrayList<>();
        mSelectDatas.add("全部");
    }

    private void initViews() {
        TagFlowLayout flowLayout = findViewById(R.id.flow_content);
        adapter = new StringTagAdapter(this, mDatas, mSelectDatas);
        adapter.setOnFlexSelectListener(new OnFlexSelectListener<String>() {
            @Override
            public void onSelect(List<String> selectItems) {
                for (String str : selectItems) {
                    Toast.makeText(MainActivity.this, "选中" + str, Toast.LENGTH_SHORT).show();
                }
            }
        });
        flowLayout.setAdapter(adapter);
        findViewById(R.id.change_data).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mDatas.clear();
        mDatas.add("全部");
        mDatas.add("武侠仙侠");
        mDatas.add("历史军事");
        mDatas.add("竞技同人");

        mSelectDatas.clear();
        mSelectDatas.add("全部");
        adapter.setItems(mDatas);
        adapter.setSelectItems(mSelectDatas);
        adapter.notifyDataSetChanged();
    }
}