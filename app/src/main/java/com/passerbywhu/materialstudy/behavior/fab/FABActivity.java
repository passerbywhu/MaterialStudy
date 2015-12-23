package com.passerbywhu.materialstudy.behavior.fab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.passerbywhu.materialstudy.MyAdapter;
import com.passerbywhu.materialstudy.R;

/**
 * Created by hzwuwenchao on 2015/12/22.
 */
public class FABActivity extends AppCompatActivity {
    private RecyclerView mToDoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab);
        mToDoList = (RecyclerView) findViewById(R.id.toDoList);
        mToDoList.setLayoutManager(new LinearLayoutManager(this));
        mToDoList.setAdapter(new MyAdapter(this));
    }
}
