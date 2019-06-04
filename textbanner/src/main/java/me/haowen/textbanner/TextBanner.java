package me.haowen.textbanner;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 文字轮播
 */
public class TextBanner extends FrameLayout {

    /**
     * 交替的View个数
     */
    private static final int SIZE = 2;
    /**
     * 当前的位置索引
     */
    private int mCurrentPosition = 0;
    /**
     * 停留时长
     */
    private int mDelayTime = 5 * 1000;
    /**
     * 动画持续时长
     */
    private int mDuration = 800;
    /**
     * 两个View交替出现
     */
    private View viewFirst, viewSecond;
    /**
     * 动画（出现、消失）
     */
    private Animation mAnimIn, mAnimOut;
    /**
     * 数据适配器
     */
    private Adapter mAdapter;

    private WeakHandler mHandler = new WeakHandler();
    /**
     * 轮播的定时任务：当页数大于1时轮播
     */
    private Runnable task = new Runnable() {
        @Override
        public void run() {
            updateTipAndPlayAnimation();
            mHandler.postDelayed(this, mDelayTime);
        }
    };

    public TextBanner(Context context) {
        this(context, null);
    }

    public TextBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextBanner, defStyleAttr, 0);
            mDuration = a.getInteger(R.styleable.TextBanner_duration, mDuration);
            mDelayTime = a.getInteger(R.styleable.TextBanner_delayTime, mDelayTime);
            int animInResId = a.getResourceId(R.styleable.TextBanner_animIn, R.anim.com_haowen_textbanner_view_anim_in);
            int animOutResId = a.getResourceId(R.styleable.TextBanner_animOut, R.anim.com_haowen_textbanner_view_anim_out);
            a.recycle();
            // 获取动画
            mAnimIn = AnimationUtils.loadAnimation(getContext(), animInResId);
            mAnimOut = AnimationUtils.loadAnimation(getContext(), animOutResId);
            // 默认的动画资源ID
            if (animInResId == R.anim.com_haowen_textbanner_view_anim_in &&
                    animOutResId == R.anim.com_haowen_textbanner_view_anim_out) {
                mAnimIn.setDuration(mDuration);
                mAnimOut.setDuration(mDuration);
            }
        }
    }

    /**
     * 设置数据适配器
     *
     * @param adapter 数据适配器
     */
    public void setAdapter(@Nullable Adapter adapter) {
        if (adapter == null) {
            return;
        }
        mAdapter = adapter;
        mAdapter.registerObservable(new Observable() {
            @Override
            public void onChange() {
                setData();
            }
        });
        setData();
    }

    /**
     * 重置，数据设置并开始动画
     */
    private void setData() {
        reset();
        // 没有数据
        if (mAdapter.getCount() == 0) {
            return;
        }
        createViews();
        bindViewData(viewFirst, mCurrentPosition);
        if (mAdapter.getCount() < SIZE) {
            return;
        }
        startAutoPlay();
    }

    /**
     * 动画清除，View去除,定时任务去除
     */
    private void reset() {
        if (viewSecond != null) {
            viewSecond.clearAnimation();
        }
        if (viewFirst != null) {
            viewFirst.clearAnimation();
        }
        clearAnimation();
        removeAllViews();
        stopAutoPlay();
        mCurrentPosition = 0;
    }

    /**
     * 生成View并添加容器
     */
    private void createViews() {
        viewFirst = mAdapter.onCreateView(this);
        viewSecond = mAdapter.onCreateView(this);
        addView(viewSecond);
        addView(viewFirst);
    }

    /**
     * 数据绑定
     *
     * @param convertView View
     * @param position    位置
     */
    private void bindViewData(View convertView, int position) {
        mAdapter.onBindViewData(convertView, position);
    }

    /**
     * 启动自动轮播
     */
    public void startAutoPlay() {
        mHandler.removeCallbacks(task);
        mHandler.postDelayed(task, mDelayTime);
    }

    /**
     * 停止自动轮播
     */
    public void stopAutoPlay() {
        mHandler.removeCallbacks(task);
    }

    /**
     * 动画替换下一个View
     */
    private void updateTipAndPlayAnimation() {
        checkAdapterNotNull();
        if (mAdapter.getCount() == 0) {
            return;
        }
        mCurrentPosition++;
        if (mCurrentPosition % SIZE == 0) {
            bindViewData(viewFirst, mCurrentPosition % mAdapter.getCount());
            startAnimation(viewFirst, viewSecond);
            this.bringChildToFront(viewSecond);
        } else {
            bindViewData(viewSecond, mCurrentPosition % mAdapter.getCount());
            startAnimation(viewSecond, viewFirst);
            this.bringChildToFront(viewFirst);
        }
    }

    /**
     * 检查是否设置了 {@link Adapter}
     */
    private void checkAdapterNotNull() {
        if (mAdapter == null) {
            throw new NullPointerException("TextBanner has no adapter.");
        }
    }

    /**
     * 动画
     *
     * @param appearView    出现的View
     * @param disappearView 隐藏的View
     */
    private void startAnimation(final View appearView, final View disappearView) {
        // 出现
        appearView.startAnimation(mAnimIn);
        appearView.setVisibility(VISIBLE);

        // 消失
        disappearView.startAnimation(mAnimOut);
        disappearView.setVisibility(VISIBLE);
        mAnimIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                disappearView.setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    /**
     * 数据观察
     */
    private interface Observable {
        /**
         * 数据改变
         */
        void onChange();
    }

    /**
     * 数据适配器
     */
    public abstract static class Adapter {

        /**
         * 数据更新观察这
         */
        private Observable mObservable;

        /**
         * 注册数据更新观察
         *
         * @param observable 数据更新观察
         */
        private void registerObservable(Observable observable) {
            this.mObservable = observable;
        }

        /**
         * 通知数据更新
         */
        public void notifyDataChange() {
            if (mObservable != null) {
                mObservable.onChange();
            }
        }

        /**
         * Item个数
         *
         * @return Item个数
         */
        public abstract int getCount();

        /**
         * View生成
         *
         * @param parent 父容器
         * @return Item的View
         */
        public abstract View onCreateView(@NonNull ViewGroup parent);

        /**
         * 数据绑定View
         *
         * @param convertView 内容View
         * @param position    位置
         */
        public abstract void onBindViewData(@NonNull View convertView, int position);
    }
}