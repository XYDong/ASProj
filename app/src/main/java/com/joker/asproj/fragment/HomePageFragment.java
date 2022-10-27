package com.joker.asproj.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.joker.asproj.R;
import com.joker.common.ui.component.HiBaseFragment;

import org.devio.hi.ui.tab.top.HiTabTopInfo;
import org.devio.hi.ui.tab.top.HiTabTopLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: ASProj
 * @Package: com.joker.asproj.fragment
 * @ClassName: HomePageFragment
 * @Description:
 * @Author: xiayd
 * @CreateDate: 2022/10/27 17:37
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/10/27 17:37
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HomePageFragment extends HiBaseFragment {


    String[] tabsStr = new String[]{
            "热门",
            "服装",
            "数码",
            "鞋子",
            "零食",
            "家电",
            "汽车",
            "百货",
            "家居",
            "装修",
            "运动"
    };

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        Activity activity = activityWeakReference.get();
        HiTabTopLayout hiTabTopLayout = activity.findViewById(R.id.tab_top_layout);
        List<HiTabTopInfo<?>> infoList = new ArrayList<>();
        int defaultColor = getResources().getColor(R.color.tabBottomDefaultColor);
        int tintColor = getResources().getColor(R.color.tabBottomTintColor);
        for (String s : tabsStr) {
            HiTabTopInfo<?> info = new HiTabTopInfo<>(s, defaultColor, tintColor);
            infoList.add(info);
        }
        hiTabTopLayout.inflateInfo(infoList);
        hiTabTopLayout.addTabSelectedChangeListener((index, prevInfo, nextInfo) -> Toast.makeText(activity, nextInfo.name, Toast.LENGTH_SHORT).show());
        hiTabTopLayout.defaultSelected(infoList.get(0));
    }
}
