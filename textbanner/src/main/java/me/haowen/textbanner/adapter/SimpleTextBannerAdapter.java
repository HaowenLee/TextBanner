package me.haowen.textbanner.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import java.util.List;

/**
 * 简单的数据适配器（TextView）
 */
public class SimpleTextBannerAdapter extends BaseAdapter<String> {

    @LayoutRes
    private int mLayoutResId;

    /**
     * 简单的TextBanner适配器（只含有一个TextView）
     *
     * @param context     上下文
     * @param layoutResId 布局资源ID
     * @param data        字符串列表数据源
     */
    public SimpleTextBannerAdapter(Context context, @LayoutRes int layoutResId, List<String> data) {
        super(context, data);
        this.mLayoutResId = layoutResId;
    }

    @Override
    public View onCreateView(@NonNull ViewGroup parent) {
        return mInflater.inflate(mLayoutResId, parent, false);
    }

    @Override
    public void onBindViewData(@NonNull View convertView, int position) {
        ((TextView) convertView).setText(mData.get(position));
    }
}