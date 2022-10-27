package com.joker.common.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @ProjectName: ASProj
 * @Package: com.joker.common.tab
 * @ClassName: HiFragmentTabView
 * @Description:
 * Framgent 管理类
 * 1. 将 Fragment 的操作内聚
 * 2. 提供一些通用API
 * @Author: xiayd
 * @CreateDate: 2022/10/27 17:26
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/10/27 17:26
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HiFragmentTabView extends FrameLayout {

    private HiTabViewAdapter mAdapter;
    private int currentPosition;


    public HiFragmentTabView(@NonNull Context context) {
        this(context,null);
    }

    public HiFragmentTabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HiFragmentTabView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HiTabViewAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(HiTabViewAdapter adapter) {
        if (this.mAdapter != null || adapter == null) {
            return;
        }
        this.mAdapter = adapter;
        currentPosition = -1;
    }
    public void setCurrentItem(int position) {
        if (position < 0 || position >= mAdapter.getCount()) {
            return;
        }
        if (currentPosition != position){
            currentPosition = position;
            mAdapter.instantiateItem(this,position);
        }
    }

    public Fragment getCurrentFragment() {
        if (this.mAdapter == null) {
            throw new IllegalArgumentException("please call setAdapter first.");
        }
//        return mAdapter.getItem(currentPosition);
        return mAdapter.getCurrentFragment();
    }


}
