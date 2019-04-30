package me.haowen.textbanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.List;

import me.haowen.textbanner.TextBanner;

/**
 * 数据适配器基类
 *
 * @param <T> 数据泛型
 */
public abstract class BaseAdapter<T> extends TextBanner.Adapter {

    /**
     * 数据
     */
    protected List<T> mData;
    protected LayoutInflater mInflater;

    /**
     * 设置数据并通知更新
     *
     * @param data 数据
     */
    public void setData(List<T> data) {
        this.mData = data;
        notifyDataChange();
    }

    /**
     * 获取Item数据
     *
     * @param position 位置
     * @return 数据
     */
    public T getItem(int position) {
        return mData.get(position);
    }

    public BaseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public BaseAdapter(Context context, List<T> data) {
        mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }
}