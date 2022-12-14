package org.devio.hi.ui.tab.binner.core;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;


import org.devio.hi.ui.tab.binner.indicator.HiIndicator;

import java.util.List;

public interface IHiBanner {
    void setBannerData(@LayoutRes int layoutResId, @NonNull List<? extends HiBannerMo> models);

    void setBannerData(@NonNull List<? extends HiBannerMo> models);

    void setHiIndicator(HiIndicator hiIndicator);

    void setAutoPlay(boolean autoPlay);

    void setLoop(boolean loop);

    void setIntervalTime(int intervalTime);

    void setBindAdapter(IBindAdapter bindAdapter);

    void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener);

    void setOnBannerClickListener(IHiBanner.OnBannerClickListener onBannerClickListener);

    void setScrollDuration(int duration);

    interface OnBannerClickListener {
        void onBannerClick(@NonNull HiBannerAdapter.HiBannerViewHolder viewHolder, @NonNull HiBannerMo bannerMo, int position);
    }
}
