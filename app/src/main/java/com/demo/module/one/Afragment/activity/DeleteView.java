package com.demo.module.one.Afragment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.demo.R;
import com.demo.common.view.SlideCutListView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wanghongliang on 16/3/29.
 */
public class DeleteView extends Activity implements SlideCutListView.RemoveListener {
    private SlideCutListView slideCutListView ;
    private ArrayAdapter<String> adapter;
    private List<String> dataSourceList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detlete);
        init();
    }

    private void init() {
        slideCutListView = (SlideCutListView)findViewById(R.id.slideCutListView);
        slideCutListView.setRemoveListener(this);

        for(int i=0; i<20; i++){
            dataSourceList.add("滑动删除" + i);
        }

        adapter = new ArrayAdapter<String>(this, R.layout.item_detlete, R.id.list_item, dataSourceList);
        slideCutListView.setAdapter(adapter);

        slideCutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(DeleteView.this, dataSourceList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //滑动删除之后的回调方法
    @Override
    public void removeItem(SlideCutListView.RemoveDirection direction, int position) {
        adapter.remove(adapter.getItem(position));
        switch (direction) {
            case RIGHT:
                Toast.makeText(this, "向右删除  "+ position, Toast.LENGTH_SHORT).show();
                break;
            case LEFT:
                Toast.makeText(this, "向左删除  "+ position, Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }

    }
}
