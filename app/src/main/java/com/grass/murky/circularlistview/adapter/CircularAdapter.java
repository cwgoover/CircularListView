package com.grass.murky.circularlistview.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.grass.murky.circularlistview.R;

import java.util.List;

public class CircularAdapter extends BaseAdapter {
    private static final String TAG = CircularAdapter.class.getSimpleName();
    // TODO: change back
//    private static final int MAX_VALUE = Integer.MAX_VALUE;
    private static final int MAX_VALUE = 98;
    private static final int HALF_MAX_VALUE = MAX_VALUE / 2;

    public final int MIDDLE;

    private Context mContext;
    private List<String> mList;

    public CircularAdapter(Context ctx, List<String> list) {
        mContext = ctx;
        mList = list;
        if (list != null && !list.isEmpty()) {
            MIDDLE = HALF_MAX_VALUE - HALF_MAX_VALUE % list.size();
        } else {
            MIDDLE = 0;
        }
    }

    @Override
    public int getCount() {
        return (mList == null || mList.isEmpty()) ? 0 : MAX_VALUE;
    }

    @Override
    public String getItem(int position) {
//        Log.d(TAG, "getItem: position=" + position + " GET FORM: " + (position % mList.size()));
        return mList.get(position % mList.size());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // 如果return null，会造成 java.lang.NullPointerException:
        // Attempt to invoke virtual method 'int android.view.View.getImportantForAccessibility()'
        // on a null object reference
//        if (mList == null || mList.isEmpty()) {
//            return null;
//        }

        final ViewHolder holder;
        if (convertView == null) {
            // 这里attachToRoot一定要设置为false！因为此时并不会立刻将list_item addView到parent中，这个过程
            // 由ListView去控制，所以如果这里设置为true会crash，查看报错源码AdapterView：
            // throw new UnsupportedOperationException("addView(View, LayoutParams) "
            //      + "is not supported in AdapterView");
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
            ImageView image = (ImageView) convertView.findViewById(R.id.image);
            TextView text = (TextView) convertView.findViewById(R.id.text);
            holder = new ViewHolder();
            holder.image = image;
            holder.text = text;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.image.setImageResource(android.support.v7.appcompat.R.drawable.abc_seekbar_thumb_material);
        holder.text.setText(getItem(position));
        // tag里记录要显示的数据列表index
        holder.text.setTag(position % mList.size());
        ((LinearLayout) holder.text.getParent()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 这里要使用tag中记录的列表index，如果这里再计算"position % mList.size()"，因为这里的
                // position已经是view的position而不是设置text内容时的position所以显示会错误
                Log.d(TAG, "onClick: position=" + position + ", show: " + ((int) holder.text.getTag() + 1));
                Toast.makeText(mContext, "click " + ((int) holder.text.getTag() + 1), Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    private static class ViewHolder {
        ImageView image;
        TextView text;
    }
}
