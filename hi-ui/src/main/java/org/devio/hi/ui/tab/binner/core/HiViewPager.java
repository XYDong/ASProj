package org.devio.hi.ui.tab.binner.core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import org.devio.hi.ui.tab.binner.core.HiBannerAdapter;

import java.lang.reflect.Field;

/**
 * @ProjectName: ASProj
 * @Package: org.devio.hi.ui.tab.binner
 * @ClassName: HiViewPager
 * @Description:
 * @Author: xiayd
 * @CreateDate: 2022/10/28 11:44
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/10/28 11:44
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HiViewPager extends ViewPager {

    /**
     *  滚动时间间隔
     */
    private int mIntervalTime;
    /**
     * 是否开启自动轮播
     */
    private boolean mAutoPlay = true;
    /**
     * 标志是否有调用过onlayout
     */
    private boolean isLayout;
    private final Handler mHandler = new Handler();
    private final Runnable mRunnable = new Runnable() {

        public void run() {
            next();
            mHandler.postDelayed(this, mIntervalTime);//延时一定时间执行下一次
        }

    };


    public HiViewPager(@NonNull Context context) {
        super(context);
    }

    public HiViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAutoPlay(boolean autoPlay){
        this.mAutoPlay = autoPlay;
        if (!mAutoPlay) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    /**
     * 用户手动点击和手动滑动的时候，站厅自动播放
     * @param ev
     * @return
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            // 用户松开手，继续自动播放
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                start();
                break;
            default:
                stop();
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        isLayout = true;
    }

    /**
     * //fix 使用RecyclerView + ViewPager bug https://blog.csdn.net/u011002668/article/details/72884893
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //fix 使用RecyclerView + ViewPager bug https://blog.csdn.net/u011002668/article/details/72884893
        if (isLayout && getAdapter() != null && getAdapter().getCount() > 0) {
            try {
                Field field = ViewPager.class.getDeclaredField("mFirstLayout");
                field.setAccessible(true);
                field.set(this,false);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        start();
    }

    @Override
    protected void onDetachedFromWindow() {
        //fix 使用RecyclerView + ViewPager bug
        // 解决滑动到一半，卡住的问题
        if (((Activity)getContext()).isFinishing()){
            super.onDetachedFromWindow();
        }
        stop();
    }

    /**
     * 设置间隔时间
     * @param mIntervalTime
     */
    public void setIntervalTime(int mIntervalTime) {
        this.mIntervalTime = mIntervalTime;
    }

    private void start() {
        mHandler.removeCallbacksAndMessages(mRunnable);
        if (mAutoPlay) {
            // 如果当前有在执行的任务，则不新添加
            mHandler.postDelayed(mRunnable,mIntervalTime);
        }
    }

    /**
     * 停止滚动
     */
    private void stop() {
        // 传null是为了清空所有消息，包括正在运行的
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 设置下一个要显示的item，并返回item的pos
     *
     * @return 下一个要显示item的pos
     */
    private int next() {
        int nextPosition = -1;

        if (getAdapter() == null || getAdapter().getCount() <= 1) {
            stop();
            return nextPosition;
        }
        nextPosition = getCurrentItem() + 1;
        //下一个索引大于adapter的view的最大数量时重新开始
        if (nextPosition >= getAdapter().getCount()) {
            nextPosition = ((HiBannerAdapter) getAdapter()).getFirstItem();
        }
        setCurrentItem(nextPosition, true);
        return nextPosition;
    }


    /**
     * 设置viewpager的滚动速度
     * @param duration
     */
    public void setScrollDuration(int duration) {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            scrollerField.set(this, new HiBannerScroller(getContext(), duration));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
