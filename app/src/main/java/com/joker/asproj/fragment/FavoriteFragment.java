package com.joker.asproj.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.joker.asproj.R;
import com.joker.common.ui.component.HiBaseFragment;

import org.devio.hi.ui.tab.refresh.HiRefresh;
import org.devio.hi.ui.tab.refresh.HiRefreshLayout;
import org.devio.hi.ui.tab.refresh.HiTextOverView;

public class FavoriteFragment extends HiBaseFragment {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_favorite;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        Activity activity = activityWeakReference.get();
        HiRefreshLayout hiRefreshLayout = activity.findViewById(R.id.refresh_layout);
        hiRefreshLayout.setRefreshOverView(new HiTextOverView(this.getContext()));
        hiRefreshLayout.setRefreshListener(new HiRefresh.HiRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(() -> hiRefreshLayout.refreshFinished(), 3000);
            }

            @Override
            public boolean enableRefresh() {
                return true;
            }
        });
    }
}
