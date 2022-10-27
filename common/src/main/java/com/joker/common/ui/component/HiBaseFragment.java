package com.joker.common.ui.component;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.lang.ref.WeakReference;

/**
 * @ProjectName: ASProj
 * @Package: com.joker.common
 * @ClassName: HiBaseFragment
 * @Description:
 * @Author: xiayd
 * @CreateDate: 2022/10/27 16:59
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/10/27 16:59
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public abstract class HiBaseFragment extends Fragment {
    protected View layoutView;
    protected WeakReference<Activity> activityWeakReference;

    @LayoutRes
    public abstract int getLayoutId();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activityWeakReference = new WeakReference<>(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutView = inflater.inflate(getLayoutId(),container,false);
        return layoutView;
    }
}
