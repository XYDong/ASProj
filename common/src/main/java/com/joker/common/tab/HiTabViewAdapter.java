package com.joker.common.tab;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.devio.hi.ui.tab.bottom.HiTabBottomInfo;

import java.util.List;

/**
 * @ProjectName: ASProj
 * @Package: com.joker.common.tab
 * @ClassName: HiTabViewAdapter
 * @Description:
 * @Author: xiayd
 * @CreateDate: 2022/10/27 17:08
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/10/27 17:08
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HiTabViewAdapter {

    private List<HiTabBottomInfo<?>> mInfoList;
    private Fragment mCurFragment;
    private FragmentManager mFragmentManager;

    public HiTabViewAdapter(FragmentManager fragmentManager, List<HiTabBottomInfo<?>> infoList) {
        this.mInfoList = infoList;
        this.mFragmentManager = fragmentManager;
    }

    /**
     * 实例化以及显示指定位置的 Fragment
     *
     * @param container
     * @param position
     */
    public void instantiateItem(View container, int position){
        FragmentTransaction mCurTransaction = mFragmentManager.beginTransaction();
        if (mCurTransaction != null&& mCurFragment != null) {
            mCurTransaction.hide(mCurFragment);
            // mCurFragment == null 时，报错：NullPointerException: Attempt to invoke virtual method 'void android.app.Fragment.setNextAnim(int)
            // 当 hide / show 空对象时，会报上面的错误 https://blog.csdn.net/qq_34882418/article/details/80973298
        }
        String tag = container.getId() + ":" + position;
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            mCurTransaction.show(fragment);
        } else {
            fragment = getItem(position);
            if (fragment != null){
                if (!fragment.isAdded()) {
                    mCurTransaction.add(container.getId(),fragment,tag);
                }
            }
        }
        mCurFragment = fragment;
        mCurTransaction.commitNowAllowingStateLoss();
        // commit() 和 commitAllowingStateLoss() 区别就是从字面理解，第二个允许页面状态丢失（因为其不进行状态检查）
        // 【Android commit 和 commitAllowingStateLoss 的区别】https://www.jianshu.com/p/83e673c453f9

    }

    public Fragment getItem(int position){
        try {
            return mInfoList.get(position).fragment.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Fragment getCurrentFragment() {
        return mCurFragment;
    }
    public int getCount(){
        return mInfoList!= null ? mInfoList.size() : 0;
    }
}
