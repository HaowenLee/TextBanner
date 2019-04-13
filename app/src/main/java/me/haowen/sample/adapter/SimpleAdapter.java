package me.haowen.sample.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import me.haowen.sample.R;

public class SimpleAdapter extends BaseAdapter<String> {

    public SimpleAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    public View onCreateView(@NonNull ViewGroup parent) {
        return inflater.inflate(R.layout.item_text_banner_simple, parent, false);
    }

    @Override
    public void onBindViewData(@NonNull View convertView, int position) {
        ((TextView) convertView).setText(data.get(position));
    }
}