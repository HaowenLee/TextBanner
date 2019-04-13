package me.haowen.sample.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.List;

import me.haowen.textbanner.TextBanner;

public abstract class BaseAdapter<T> extends TextBanner.Adapter {

    protected List<T> data;
    protected LayoutInflater inflater;

    public BaseAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public BaseAdapter(Context context, List<T> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }
}