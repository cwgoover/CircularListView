package com.grass.murky.circularlistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.grass.murky.circularlistview.adapter.CircularAdapter;
import com.grass.murky.circularlistview.adapter.CircularArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> list = new ArrayList<>();
//        list.add("TextView 0");
        list.add("TextView 1");
        list.add("TextView 2");
        list.add("TextView 3");
        list.add("TextView 4");
        list.add("TextView 5");
        list.add("TextView 6");
        list.add("TextView 7");
        list.add("TextView 8");
        list.add("TextView 9");
        list.add("TextView 10");

        ListView listView = (ListView) findViewById(R.id.circularList);
        // ArrayAdapter
//        CircularArrayAdapter adapter = new CircularArrayAdapter(this,
//                R.layout.list_item, list);
//        CircularArrayAdapter adapter = new CircularArrayAdapter(this,
//                android.R.layout.simple_expandable_list_item_1,
//                null);

        // BaseAdapter
        CircularAdapter adapter = new CircularAdapter(this, list);
        listView.setAdapter(adapter);
        // http://souly.cn/%E6%8A%80%E6%9C%AF%E5%8D%9A%E6%96%87/2015/07/11/%E6%B7%B1%E5%85%A5%E7%90%86%E8%A7%A3listView%E7%9A%84setSelectionFromTop/
        // 上面链接还有一种方式可以保存滚动位置和坐标
        // 用于保存当前索引和y坐标，和setSelectionFromTop配套使用，保存当前用户的浏览状态
        // 等再次进入后恢复原先状态
//        int pos = listView.getFirstVisiblePosition();
//        View view = listView.getChildAt(0);
//        int top = (view == null) ? 0 : view.getTop();
//        Log.d(TAG, "onCreate: getFirstVisiblePosition=" + pos + ", v.getTop=" + top);
        /**
         *  position: 指定的item在listView中的索引，注意如果有Header存在的情况下，索引是从Header就开始算的
         *  y: 到ListView可见范围内最上边边缘的距离
         *  设定指定item在listView中的某个y轴坐标位置展示
         */
        listView.setSelectionFromTop(adapter.MIDDLE, 0); // 这里将listView一开始设置到中间位置，这样向上或者向下滚动都可以"实现"循环
    }
}
