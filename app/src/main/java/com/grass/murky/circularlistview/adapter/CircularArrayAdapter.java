package com.grass.murky.circularlistview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.grass.murky.circularlistview.R;

import java.util.List;
import java.util.Locale;

/**
 * 参考 http://stackoverflow.com/a/4940073/4710864
 */
public class CircularArrayAdapter extends ArrayAdapter<String> {
    public static final String TAG = CircularArrayAdapter.class.getSimpleName();
    public static final int HALF_MAX_VALUE = Integer.MAX_VALUE / 2;
    public final int MIDDLE;

    private Context mContext;
    private int mResourceId;
    private List<String> mList;

    public CircularArrayAdapter(Context context,int resourceId, List<String> strings) {
        super(context, resourceId, strings);

        mContext = context;
        mResourceId = resourceId;
        mList = strings;
        if (strings != null && !strings.isEmpty()) {
            MIDDLE = HALF_MAX_VALUE - HALF_MAX_VALUE % strings.size();
        } else {
            MIDDLE = 0;
        }
    }

    @Override
    public int getCount() {
        // 循环列表其实是虚构一个非常非常长的列表给ListView来扩展显示列表的长度，并不是真正意义上的
        // 循环。然后通过getItem以一种循环的方式获取数据列表的值
        return (mList == null || mList.isEmpty()) ? 0 : Integer.MAX_VALUE;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        // 取余保证了listView显示的周期就是列表的长度
        // 因为循环列表的最后一个元素逻辑上是先于第一个元素的，比如10个元素，那么1之前的应该是10
        // 所以position是正数时取余保证了列表的顺序不变，没有问题
        // 如果position是负数时也没有问题，比如19 items从下往上滑到列表底端，这时index为0没有问题
        // 再往下滑index为-1，java中-1%19=18，代表list中的最后一个元素，根据循环列表的显示效果来说也没有问题
        // 或者可以说这种策略，正序和倒序的显示都可以hold住
        return mList.get(position % mList.size());
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResourceId, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.image.setImageResource(android.support.v7.appcompat.R.drawable.abc_seekbar_thumb_material);
        if (mList != null) {
            holder.text.setText(getItem(position));
        } else {
            holder.text.setText(String.format(Locale.CHINA, "数据加载失败：%d", position));
        }
        return convertView;
    }

    private static class ViewHolder {
        ImageView image;
        TextView text;
    }
}
