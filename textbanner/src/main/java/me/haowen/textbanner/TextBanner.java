package me.haowen.textbanner;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Adapter;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 文字轮播
 */
public class TextBanner extends FrameLayout {

    /**
     * 当前的位置索引
     */
    private int currentPosition = 0;
    /**
     * 上次动画开始时间戳
     */
    private long lastTimeMillis;
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
    private View viewItemOut, viewItemIn;
    /**
     * 动画
     */
    private Animation animOut, animIn;

    /**
     * 数据适配器
     */
    private Adapter mAdapter;
    /**
     * 交替的View个数
     */
    private static final int SIZE = 2;

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
            a.recycle();
        }
        initAnimation();
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
        animOut = newAnimation(0, -1);
        animIn = newAnimation(1, 0);
        animIn.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                updateTipAndPlayAnimationWithCheck();
            }
        });
    }

    /**
     * 生成动画
     *
     * @param fromYValue 起始值
     * @param toYValue   结束值
     * @return 动画
     */
    private Animation newAnimation(float fromYValue, float toYValue) {
        Animation anim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, fromYValue, Animation.RELATIVE_TO_SELF, toYValue);
        anim.setDuration(mDuration);
        anim.setStartOffset(mDelayTime);
        anim.setInterpolator(new DecelerateInterpolator());
        return anim;
    }

    /**
     * 开始下一次动画（并做时间校验）
     */
    private void updateTipAndPlayAnimationWithCheck() {
        // （代码的耗时，保证动画结束的时间<动画时长和停留时长）
        if (System.currentTimeMillis() - lastTimeMillis < mDuration + mDelayTime) {
            return;
        }
        updateTipAndPlayAnimation();
    }

    /**
     * 动画替换下一个View
     */
    private void updateTipAndPlayAnimation() {
        currentPosition++;
        if (currentPosition % 2 == 0) {
            bindViewData(viewItemOut, currentPosition % mAdapter.getCount());
            viewItemIn.startAnimation(animOut);
            viewItemOut.startAnimation(animIn);
            this.bringChildToFront(viewItemIn);
        } else {
            bindViewData(viewItemIn, currentPosition % mAdapter.getCount());
            viewItemOut.startAnimation(animOut);
            viewItemIn.startAnimation(animIn);
            this.bringChildToFront(viewItemOut);
        }
        // 记录动画的开始时间
        lastTimeMillis = System.currentTimeMillis();
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
        bindViewData(viewItemOut, currentPosition);
        if (mAdapter.getCount() < SIZE) {
            return;
        }
        updateTipAndPlayAnimation();
    }

    /**
     * 动画清除，View去除
     */
    private void reset() {
        if (viewItemIn != null) {
            viewItemIn.clearAnimation();
        }
        if (viewItemOut != null) {
            viewItemOut.clearAnimation();
        }
        clearAnimation();
        removeAllViews();
        currentPosition = 0;
    }

    /**
     * 生成View并添加容器
     */
    private void createViews() {
        viewItemOut = mAdapter.onCreateView(this);
        viewItemIn = mAdapter.onCreateView(this);
        addView(viewItemIn);
        addView(viewItemOut);
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

    /**
     * 数据观察
     */
    private interface Observable {
        /**
         * 数据改变
         */
        void onChange();
    }
}