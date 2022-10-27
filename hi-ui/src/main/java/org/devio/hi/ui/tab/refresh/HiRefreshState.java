package org.devio.hi.ui.tab.refresh;

/**
 * @ProjectName: ASProj
 * @Package: org.devio.hi.ui.tab.refresh
 * @ClassName: HiRefreshState
 * @Description: 刷新的状态
 * @Author: xiayd
 * @CreateDate: 2022/10/27 22:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/10/27 22:47
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public enum HiRefreshState {
    /**
     * 初始态
     */
    STATE_INIT,
    /**
     * Header展示的状态
     */
    STATE_VISIBLE,
    /**
     * 超出可刷新距离的状态
     */
    STATE_OVER,
    /**
     * 刷新中的状态
     */
    STATE_REFRESH,
    /**
     * 超出刷新位置松开手后的状态
     */
    STATE_OVER_RELEASE
}
