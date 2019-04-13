package me.haowen.textbanner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TextBanner extends FrameLayout {

    private int currentPosition = 0;
    private long lastTimeMillis;
    private static final int ANIM_DELAYED_MILLIONS = 5 * 1000;
    /**
     * 动画持续时长
     */
    private static final int ANIM_DURATION = 800;
    private View viewItemOut, viewItemIn;
    private Animation animOut, animIn;

    private static final int SIZE = 2;

    public TextBanner(Context context) {
        super(context);
        initAnimation();
    }

    public TextBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAnimation();
    }

    public TextBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAnimation();
    }

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

    private Animation newAnimation(float fromYValue, float toYValue) {
        Animation anim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, fromYValue, Animation.RELATIVE_TO_SELF, toYValue);
        anim.setDuration(ANIM_DURATION);
        anim.setStartOffset(ANIM_DELAYED_MILLIONS);
        anim.setInterpolator(new DecelerateInterpolator());
        return anim;
    }

    private void updateTipAndPlayAnimationWithCheck() {
        if (System.currentTimeMillis() - lastTimeMillis < ANIM_DURATION) {
            return;
        }
        lastTimeMillis = System.currentTimeMillis();
        updateTipAndPlayAnimation();
    }

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
    }

    @SuppressWarnings("unchecked")
    private void bindViewData(View convertView, int position) {
        mAdapter.onBindViewData(convertView, position);
    }

    Adapter mAdapter;

    public void setAdapter(@Nullable Adapter adapter) {
        if (adapter == null) {
            return;
        }
        mAdapter = adapter;
        reset();
        createViews();
        bindViewData(viewItemOut, currentPosition);
        if (mAdapter.getCount() > SIZE) {
            updateTipAndPlayAnimation();
        }
    }

    private void reset() {
        removeAllViews();
        clearAnimation();
        currentPosition = 0;
    }

    private void createViews() {
        viewItemOut = mAdapter.onCreateView(this);
        viewItemIn = mAdapter.onCreateView(this);
        addView(viewItemOut);
        addView(viewItemIn);
    }

    public abstract static class Adapter {

        public abstract int getCount();

        public abstract View onCreateView(@NonNull ViewGroup parent);

        public abstract void onBindViewData(@NonNull View convertView, int position);
    }
}