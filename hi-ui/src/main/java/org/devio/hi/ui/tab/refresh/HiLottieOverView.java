package org.devio.hi.ui.tab.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.airbnb.lottie.LottieAnimationView;

import org.devio.hi.ui.R;

/**
 * @ProjectName: ASProj
 * @Package: org.devio.hi.ui.tab.refresh
 * @ClassName: HiLottieOverView
 * @Description: 动画刷新组件
 * @Author: xiayd
 * @CreateDate: 2022/10/28 9:48
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/10/28 9:48
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HiLottieOverView extends HiOverView {


    private LottieAnimationView mAnimationView;

    public HiLottieOverView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public HiLottieOverView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HiLottieOverView(Context context) {
        super(context);
    }

    @Override
    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.hi_refresh_lottie_overview, this, true);
        mAnimationView = findViewById(R.id.animation_view);
        mAnimationView.setAnimation("data.json");
    }


    @Override
    protected void onScroll(int scrollY, int pullRefreshHeight) {

    }

    @Override
    protected void onVisible() {

    }

    @Override
    public void onOver() {

    }

    @Override
    public void onRefresh() {
        mAnimationView.playAnimation();
    }

    @Override
    public void onFinish() {
        mAnimationView.cancelAnimation();
    }
}
