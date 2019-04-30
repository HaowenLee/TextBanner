package me.haowen.textbanner.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import me.haowen.textbanner.R;

/**
 * 简单的数据适配器（TextView）
 */
public class SimpleTextBannerAdapter extends BaseAdapter<String> {

    public SimpleTextBannerAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    public View onCreateView(@NonNull ViewGroup parent) {
        return mInflater.inflate(R.layout.item_text_banner_simple, parent, false);
    }

    @Override
    public void onBindViewData(@NonNull View convertView, int position) {
        ((TextView) convertView).setText(mData.get(position));
    }
}