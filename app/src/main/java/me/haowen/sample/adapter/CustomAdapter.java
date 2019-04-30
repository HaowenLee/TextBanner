package me.haowen.sample.adapter;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import me.haowen.sample.R;
import me.haowen.textbanner.adapter.BaseAdapter;

/**
 * 自定义的适配器
 */
public class CustomAdapter extends BaseAdapter<Pair<String, String>> {

    public CustomAdapter(Context context, List<Pair<String, String>> data) {
        super(context, data);
    }

    @Override
    public View onCreateView(@NonNull ViewGroup parent) {
        return mInflater.inflate(R.layout.item_text_banner_custom, parent, false);
    }

    @Override
    public void onBindViewData(@NonNull View convertView, int position) {
        TextView tvTag = convertView.findViewById(R.id.tvTag);
        TextView tvTitle = convertView.findViewById(R.id.tvTitle);

        Pair<String, String> itemModel = getItem(position);

        tvTag.setText(itemModel.first);
        tvTitle.setText(itemModel.second);
    }
}